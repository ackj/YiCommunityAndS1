package com.aglhz.s1.camera;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aglhz.abase.common.DialogHelper;
import com.aglhz.abase.log.ALog;
import com.aglhz.abase.utils.ToastUtils;
import com.aglhz.s1.camera.contract.CameraSettingContract;
import com.aglhz.s1.camera.presenter.CameraSettingPresenter;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.entity.bean.CameraBean;
import com.aglhz.s1.event.EventCameraListRefresh;
import com.aglhz.yicommunity.R;
import com.p2p.core.BaseMonitorActivity;
import com.p2p.core.P2PHandler;
import com.p2p.core.P2PValue;
import com.p2p.core.P2PView;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.itsite.adialog.dialogfragment.BaseDialogFragment;
import rx.functions.Action1;

/**
 * Author: LiuJia on 2017/9/13 0013 17:53.
 * Email: liujia95me@126.com
 */

public class CameraPlayActivity extends BaseMonitorActivity implements CameraSettingContract.View {

    private static final String TAG = CameraPlayActivity.class.getSimpleName();

    private static final int VIDEO_MODE_SD = 0;
    private static final int VIDEO_MODE_HD = 1;
    private static final int VIDEO_MODE_LD = 2;
    public static String P2P_ACCEPT = "com.XXX.P2P_ACCEPT";
    public static String P2P_READY = "com.XXX.P2P_READY";
    public static String P2P_REJECT = "com.XXX.P2P_REJECT";

    @BindView(R.id.rl_p2pview)
    RelativeLayout rlP2pview;
    @BindView(R.id.ll_control)
    LinearLayout llControl;
    @BindView(R.id.ll_talkback)
    LinearLayout llTalkback;
    @BindView(R.id.tv_quality)
    TextView tvQuality;
    @BindView(R.id.tv_video)
    TextView tvVideo;
    @BindView(R.id.tv_sound_no_off)
    TextView tvSoundNoOff;
    @BindView(R.id.view_black)
    View viewBlack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.tv_talk)
    TextView tvTalk;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_video)
    ImageView ivVideo;
    @BindView(R.id.iv_photograph)
    ImageView ivPhotograph;
    @BindView(R.id.iv_mute)
    ImageView ivMute;
    @BindView(R.id.iv_microphone)
    ImageView ivMicrophone;
    @BindView(R.id.view_red_pointer)
    View viewRECPointer;
    @BindView(R.id.ll_rec)
    LinearLayout llREC;
    @BindView(R.id.tv_av_byte_per_sec)
    TextView tvAvBytesPerSec;

    private CameraSettingPresenter presenter = new CameraSettingPresenter(this);
    private AlertDialog.Builder pwdDialog;
    private ProgressDialog progressDialog;
    private Params params = Params.getInstance();
    private CameraBean.DataBean bean;
    private String[] qualityArr = {"标清", "高清", "流畅"};
    private int recordFlag = 0;
    private String pathName = "";
    private String callID;
    private String userId;
    private String password;

    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ALog.e(TAG, "onReceive action:" + intent.getAction());
            if (intent.getAction().equals(P2P_ACCEPT)) {
                int[] type = intent.getIntArrayExtra("type");
                P2PView.type = type[0];
                P2PView.scale = type[1];
                ALog.e(TAG, "\n 监控数据接收 type:" + P2PView.type + " scale:" + P2PView.scale);
                ALog.e(TAG, "监控数据接收:");
                P2PHandler.getInstance().openAudioAndStartPlaying(1);//打开音频并准备播放，calllType与call时type一致
            } else if (intent.getAction().equals(P2P_READY)) {
                progressDialog.dismiss();
                ALog.e(TAG, "\n 监控准备,开始监控");
                ALog.e(TAG, "监控准备,开始监控");
                ToastUtils.showToast(CameraPlayActivity.this, "开始监控");
                pView.sendStartBrod();
                viewBlack.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewBlack.setVisibility(View.GONE);
                    }
                }, 2000);
            } else if (intent.getAction().equals(P2P_REJECT)) {
                int reason_code = intent.getIntExtra("reason_code", -1);
                int code1 = intent.getIntExtra("exCode1", -1);//806363145
                int code2 = intent.getIntExtra("exCode2", -1);
                String reject = String.format("\n 监控挂断(reson:%d,code1:%d,code2:%d)", reason_code, code1, code2);
                ALog.e(TAG, reject);

                if (reason_code == 0) {
                    //0：密码错误
                    progressDialog.dismiss();
                    showInputDialog();
                } else if (reason_code == 9) {
                    //9：手动挂断
                    viewBlack.setVisibility(View.VISIBLE);
                } else if (reason_code == 10 || reason_code == 3 || reason_code == 6) {
                    //当reason_code为这些的时候，进行重连
                    connect(userId, callID, password);
                } else {
                    //未知因素，以询问方式进行重连
                    reConnect();
                }
            }
        }
    };

    /*else if (reason_code == 6) {
                    new AlertDialog.Builder(CameraPlayActivity.this)
                            .setMessage("请先配网")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CameraPlayActivity.this.finish();
                                }
                            })
                            .show();
                }*/

    private void reConnect() {
        progressDialog.dismiss();
        new AlertDialog.Builder(CameraPlayActivity.this)
                .setMessage("连接失败，是否重新连接？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.show();
                        connect(userId, callID, password);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CameraPlayActivity.this.finish();
                    }
                })
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_play);
        ButterKnife.bind(this);
        initView();
        initToolbar();
        regFilter();
        initAnimator();
        initData();
        initListener();
    }

    private void initView() {
        pView = (P2PView) findViewById(R.id.p2pview);
        initP2PView(7, P2PView.LAYOUTTYPE_TOGGEDER);//7是设备类型(技威定义的)

        //----------------------------- 初始化loading --------------------------------

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("正在加载...");
        progressDialog.setCancelable(false);
    }

    private void initToolbar() {
        toolbarTitle.setText("智能监控");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initAnimator() {
        AlphaAnimation animation = new AlphaAnimation(0, 1);
        animation.setDuration(500);
        animation.setRepeatMode(ValueAnimator.INFINITE);
        animation.setRepeatCount(-1);
        viewRECPointer.startAnimation(animation);
    }

    //检查摄像头权限
    private void checkCameraPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) { // 在android 6.0之前会默认返回true
                            // 已经获取权限
                            Log.e(TAG, "已授予CAMERA权限");
                        } else {
                            // 未获取权限
                            ToastUtils.showToast(CameraPlayActivity.this, "您没有授权CAMERA权限，请在设置中打开授权");
                        }
                    }
                });
    }

    //连接
    private boolean connect(String userId, String id, String pwd) {
        boolean call = P2PHandler.getInstance().call(userId, pwd, true, 1, id, "", "", 2, id);
        ALog.e(TAG, "正在连接:" + call);
//        toolbar.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (progressDialog.isShowing()){
//                    ToastUtils.showToast(CameraPlayActivity.this,"连接失败");
//                    CameraPlayActivity.this.finish();
//                }
//            }
//        }, 15 * 1000);
        return call;
    }

    private void initData() {
        checkCameraPermission();
        //-------------------- 获取数据 --------------------
        bean = (CameraBean.DataBean) getIntent().getSerializableExtra("bean");
        SharedPreferences sp = getSharedPreferences("Account", MODE_PRIVATE);
        userId = sp.getString("userId", "");
        password = P2PHandler.getInstance().EntryPassword(bean.getPassword());
        callID = bean.getNo();
        ALog.e(TAG, "id:" + callID + " -- password:" + bean.getPassword() + " -- userId:" + userId + " -- pwd:" + password);

        progressDialog.show();
        connect(userId, password, callID);
    }

    private void showInputDialog() {
        new BaseDialogFragment()
                .setLayoutId(R.layout.dialog_add_authorization)
                .setConvertListener((holder, dialog) -> {
                    EditText etInput = holder.getView(R.id.et_input_phone);
                    holder.setText(R.id.tv_title, "密码错误，请输入正确密码")
                            .setOnClickListener(R.id.tv_cancel, v -> {
                                dialog.dismiss();
                                finish();
                            })
                            .setOnClickListener(R.id.tv_comfirm, v -> {
                                String result = etInput.getText().toString().trim();
                                if (TextUtils.isEmpty(result)) {
                                    DialogHelper.warningSnackbar(toolbar, "请输入内容");
                                } else {
                                    progressDialog.show();
                                    params.fid = bean.getFid();
                                    params.deviceName = bean.getName();
                                    params.deviceType = "password";
                                    params.devicePassword = result;
                                    presenter.requestModCamera(params);
                                    dialog.dismiss();
                                }
                            });
                })
                .setMargin(40)
                .setDimAmount(0.3f)
                .setGravity(Gravity.CENTER)
                .show(getSupportFragmentManager());
    }

    private void initListener() {
        llTalkback.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    ivMicrophone.setSelected(true);
                    tvTalk.setText("正在对讲");
                    setMute(false);
                    return true;
                case MotionEvent.ACTION_UP:
                    ivMicrophone.setSelected(false);
                    tvTalk.setText("对讲");
                    setMute(true);
                    return true;
            }
            return false;
        });

        refreshAvBytesPerSec();

    }

    private void refreshAvBytesPerSec() {
        toolbar.postDelayed(new Runnable() {
            @Override
            public void run() {
                int avBytesPerSec = P2PHandler.getInstance().getAvBytesPerSec();
                ALog.d(TAG, "avBytesPerSec：" + avBytesPerSec);
                if (tvAvBytesPerSec != null) {
                    tvAvBytesPerSec.setText("码率：" + (avBytesPerSec / 1024) + "kb/s");
                }
                refreshAvBytesPerSec();
            }
        }, 1000);
    }

    @OnClick({R.id.ll_sound, R.id.tv_quality, R.id.ll_photograph, R.id.ll_video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_sound:
                clickSound();
                break;
            case R.id.tv_quality:
                clickQuality();
                break;
            case R.id.ll_photograph:
                clickPhotograph();
                break;
            case R.id.ll_video:
                clickVideo();
                break;
        }
    }

    //控制静音
    private void clickSound() {
        AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (manager != null) {
            if (ivMute.isSelected()) {
                manager.setStreamVolume(AudioManager.STREAM_MUSIC, manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
                tvSoundNoOff.setText("静音");
                ivMute.setSelected(false);
            } else {
                manager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                ivMute.setSelected(true);
                tvSoundNoOff.setText("取消静音");
            }
        }
    }

    //控制画面画质
    private void clickQuality() {
        new AlertDialog.Builder(this)
                .setItems(qualityArr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case VIDEO_MODE_SD:
                                P2PHandler.getInstance().setVideoMode(P2PValue.VideoMode.VIDEO_MODE_SD);
                                break;
                            case VIDEO_MODE_HD:
                                P2PHandler.getInstance().setVideoMode(P2PValue.VideoMode.VIDEO_MODE_HD);
                                break;
                            case VIDEO_MODE_LD:
                                P2PHandler.getInstance().setVideoMode(P2PValue.VideoMode.VIDEO_MODE_LD);
                                break;
                        }
                        tvQuality.setText(qualityArr[which]);
                    }
                }).show();
    }

    //控制录像
    private void clickVideo() {
        if (recordFlag == 0) {
            //开始录像
            startMoniterRecoding();
            recordFlag = 1;
            tvVideo.setText("停止录像");
            ivVideo.setSelected(true);
            llREC.setVisibility(View.VISIBLE);
        } else {
            //停止录像
            stopMoniterReocding();
            recordFlag = 0;
            tvVideo.setText("录像");
            ivVideo.setSelected(false);
            llREC.setVisibility(View.GONE);
        }
    }

    //控制拍照
    private void clickPhotograph() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) { // 在android 6.0之前会默认返回true
                            // 已经获取权限
                            int d = P2PHandler.getInstance().setScreenShotpath(Environment.getExternalStorageDirectory().getPath(), "123.jpg");
                            Log.e("dxsTest", "d:" + d);
                            captureScreen(-1);
                            Log.e("MonitoerActivity", "已授予STORAGE权限");
                        } else {
                            // 未获取权限
                            ToastUtils.showToast(CameraPlayActivity.this, "您没有授权STORAGE权限，请在设置中打开授权");
                        }
                    }
                });
    }

    //开始录像
    public void startMoniterRecoding() {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ||
                    Environment.getExternalStorageState().equals(Environment.MEDIA_SHARED)) {
                String path = Environment.getExternalStorageDirectory().getPath() + File.separator + "gwellvideorec" + File.separator + callID;
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();
                }
                long time = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault());// 制定日期的显示格式
                String name = "gwell" + "_" + sdf.format(new Date(time));
                pathName = file.getPath() + File.separator + name + ".mp4";
            } else {
                throw new NoSuchFieldException("sd卡");
            }
        } catch (NoSuchFieldException | NullPointerException e) {
            ToastUtils.showToast(CameraPlayActivity.this, " 没有内存卡");
            e.printStackTrace();
        }
        Log.e("dxsTest", "pathName:" + pathName);
        if (P2PHandler.getInstance().starRecoding(pathName)) {
            ToastUtils.showToast(CameraPlayActivity.this, " 正在录像");
        } else {
            //录像初始化失败
            ToastUtils.showToast(CameraPlayActivity.this, " 初始化录像失败");
        }
    }

    //停止录像
    public void stopMoniterReocding() {
        if (P2PHandler.getInstance().stopRecoding() == 0) {
            //录像不正常
            ToastUtils.showToast(CameraPlayActivity.this, " 视频片段时间太短了");
        } else {
            //正常停止
            ToastUtils.showToast(CameraPlayActivity.this, " 停止录像，已保存到" + pathName);
        }
    }

    @Override
    protected void onP2PViewSingleTap() {

    }

    @Override
    protected void onP2PViewFilling() {

    }

    @Override
    protected void onCaptureScreenResult(boolean isSuccess, int prePoint) {
        if (isSuccess) {
            ToastUtils.showToast(this, "拍照成功，照片已保存到系统相册");
        } else {
            ToastUtils.showToast(this, "拍照失败");
        }
    }

    @Override
    protected void onVideoPTS(long videoPTS) {

    }

    @Override
    public int getActivityInfo() {
        return 0;
    }

    @Override
    protected void onGoBack() {

    }

    @Override
    protected void onGoFront() {

    }

    @Override
    protected void onExit() {

    }

    public void regFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(P2P_REJECT);
        filter.addAction(P2P_ACCEPT);
        filter.addAction(P2P_READY);
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void start(Object response) {

    }

    @Override
    public void error(String errorMessage) {
        ToastUtils.showToast(this, "修改失败");
        progressDialog.dismiss();
    }

    @Override
    public void complete(Object response) {

    }

    @Override
    public void responseSuccess(BaseBean baseBean) {
        ToastUtils.showToast(this, "修改成功");
        EventBus.getDefault().post(new EventCameraListRefresh());
        progressDialog.dismiss();
        connect(userId, callID, P2PHandler.getInstance().EntryPassword(params.devicePassword));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        progressDialog.show();
        connect(userId, callID, password);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        P2PHandler.getInstance().finish();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        P2PHandler.getInstance().finish();
        super.onDestroy();
    }

}
