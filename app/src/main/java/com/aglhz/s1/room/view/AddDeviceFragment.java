package com.aglhz.s1.room.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aglhz.abase.common.DialogHelper;
import com.aglhz.abase.log.ALog;
import com.aglhz.abase.mvp.view.base.BaseFragment;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.common.clip.ClipActivity;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.entity.bean.DeviceListBean;
import com.aglhz.s1.entity.bean.RoomsBean;
import com.aglhz.s1.event.EventDeviceChanged;
import com.aglhz.s1.room.contract.AddDeviceContract;
import com.aglhz.s1.room.presenter.AddDevicePresenter;
import com.aglhz.yicommunity.R;
import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.bumptech.glide.Glide;
import com.dd.CircularProgressButton;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author： Administrator on 2017/5/2 0002.
 * Email： liujia95me@126.com
 */
public class AddDeviceFragment extends BaseFragment<AddDeviceContract.Presenter> implements AddDeviceContract.View {
    public static final String TAG = AddDeviceFragment.class.getSimpleName();
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cpb_delete)
    CircularProgressButton cpbDelete;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_room_name)
    TextView tvRoomName;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    private final static int RESULT_LOAD_IMAGE = 0x100;
    private final static int RESULT_IMAGE_COMPLETE = 0x101;
    Unbinder unbinder;
    private DeviceListBean.DataBean.SubDevicesBean bean;
    private RoomsBean.DataBean.RoomListBean selectRoom;
    private Params params = Params.getInstance();

    public static AddDeviceFragment newInstance(DeviceListBean.DataBean.SubDevicesBean bean, RoomsBean.DataBean.RoomListBean room) {
        AddDeviceFragment fragment = new AddDeviceFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        bundle.putSerializable("room", room);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    protected AddDeviceContract.Presenter createPresenter() {
        return new AddDevicePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_device, container, false);
        bean = (DeviceListBean.DataBean.SubDevicesBean) getArguments().getSerializable("bean");
        selectRoom = (RoomsBean.DataBean.RoomListBean) getArguments().getSerializable("room");
//        params.roomFid = getArguments().getString("roomFid");
        unbinder = ButterKnife.bind(this, view);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initListener();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("设备设置");
        toolbarMenu.setText("确定");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressedSupport());
    }

    private void initData() {
        cpbDelete.setVisibility(bean == null ? View.GONE : View.VISIBLE);
        if (bean == null) {
            cpbDelete.setVisibility(View.GONE);
        } else {
            //把数据赋值
            etName.setText(bean.getName());
            Glide.with(_mActivity)
                    .load(bean.getIcon())
                    .into(ivIcon);
        }
        if (selectRoom == null) {
            tvRoomName.setText("");
        } else {
            params.roomFid = selectRoom.getFid();
            tvRoomName.setText(selectRoom.getName());
        }
    }

    private void initListener() {
    }

    @OnClick({R.id.cpb_delete, R.id.toolbar_menu, R.id.tv_room_name, R.id.iv_change_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cpb_delete:
                cpbDelete.setIndeterminateProgressMode(true);
                if (bean == null) {
                    DialogHelper.warningSnackbar(getView(), "删除失败");
                    return;
                }
                new AlertDialog.Builder(_mActivity)
                        .setTitle("提示")
                        .setMessage("你确定要删除吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cpbDelete.setProgress(50);
                                params.index = bean.getIndex();
                                mPresenter.requestDelDevice(params);
                            }
                        }).setNegativeButton("取消", null)
                        .show();
                break;

            case R.id.toolbar_menu:
                if (bean == null) {
                    mPresenter.requestnewDevice(params);
                } else {
                    params.name = etName.getText().toString().trim();
                    if (TextUtils.isEmpty(params.name)) {
                        DialogHelper.warningSnackbar(getView(),"名字不能为空");
                        return;
                    }
                    if(TextUtils.isEmpty(params.roomFid)){
                        DialogHelper.warningSnackbar(getView(),"请选择房间");
                        return;
                    }
                    params.index = bean.getIndex();
                    mPresenter.requestModDevice(params);
                }
                break;
            case R.id.tv_room_name:
                mPresenter.requestHouseList(params);
                break;
            case R.id.iv_change_image:
                BoxingConfig config = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG); // Mode：Mode.SINGLE_IMG, Mode.MULTI_IMG, Mode.VIDEO
                config.needCamera(R.drawable.ic_boxing_camera_white) // 支持gif，相机，设置最大选图数
                        .withMediaPlaceHolderRes(R.drawable.ic_boxing_default_image); // 设置默认图片占位图，默认无
                Boxing.of(config).withIntent(_mActivity, BoxingActivity.class).start(this, RESULT_LOAD_IMAGE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ALog.d(TAG, "onActivityResult:" + requestCode + " --- :" + resultCode);
        if (resultCode == RESULT_OK && requestCode == RESULT_LOAD_IMAGE) {
            ArrayList<BaseMedia> medias = Boxing.getResult(data);
            if (medias.size() > 0) {
                File file = new File(medias.get(0).getPath());
                Intent intent = new Intent(_mActivity, ClipActivity.class);
                intent.putExtra("path", file.getPath());
                startActivityForResult(intent, RESULT_IMAGE_COMPLETE);
            }
        } else if (resultCode == RESULT_OK && requestCode == RESULT_IMAGE_COMPLETE) {
            String path = data.getStringExtra("path");
            ALog.e(TAG, "path------>" + path);
            params.file = new File(path);
            Glide.with(_mActivity)
                    .load(params.file)
                    .into(ivIcon);
        }
    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        ALog.e(TAG,"onActivityResult:");
//        if (resultCode != RESULT_OK) {
//            return;
//        }
//        switch (requestCode) {
//            case RESULT_LOAD_IMAGE:
//                if (data == null) {
//                    return;
//                }
//                Uri selectedImage = data.getData();
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                Cursor cursor = _mActivity.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                cursor.moveToFirst();
//
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                String picturePath = cursor.getString(columnIndex);
//                cursor.close();
//
//                //拿到从系统选择好的图片后跳转到图片裁剪界面
//                Intent intent = new Intent(_mActivity, ClipActivity.class);
//                intent.putExtra("path", picturePath);
//                startActivityForResult(intent, RESULT_IMAGE_COMPLETE);
//                break;
//            case RESULT_IMAGE_COMPLETE:
//                //裁剪好的本地图片
//                String path = data.getStringExtra("path");
//                ALog.e(TAG,"path------>"+path);
//
//                Glide.with(_mActivity)
//                        .load(new File(path))
//                        .into(ivIcon);
//                break;
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void error(String errorMessage) {
        cpbDelete.setProgress(0);
        DialogHelper.warningSnackbar(getView(), errorMessage);
    }

    @Override
    public void responseSuccess(BaseBean bean) {
        DialogHelper.successSnackbar(getView(), bean.getOther().getMessage());
        EventBus.getDefault().post(new EventDeviceChanged());
        cpbDelete.setProgress(100);
        pop();
    }

    @Override
    public void responseHouseList(List<RoomsBean.DataBean.RoomListBean> data) {
        String[] rooms = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            rooms[i] = data.get(i).getName();
        }
        new AlertDialog.Builder(_mActivity)
                .setTitle("房间列表：")
                .setItems(rooms, (dialog, which) -> {
                    tvRoomName.setText(data.get(which).getName());
                    params.roomFid = data.get(which).getFid();
                    params.roomId = data.get(which).getIndex();
                })
                .setNegativeButton("取消", null)
                .show();
    }
}
