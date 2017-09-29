package com.aglhz.s1.net.view;

import android.app.Dialog;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aglhz.abase.cache.SPCache;
import com.aglhz.abase.common.DialogHelper;
import com.aglhz.abase.log.ALog;
import com.aglhz.abase.mvp.view.base.BaseFragment;
import com.aglhz.s1.App;
import com.aglhz.s1.common.Constants;
import com.aglhz.yicommunity.R;
import com.tsvclient.ipc.WifiIpc;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by leguang on 2017/5/24 0029.
 * Email：langmanleguang@qq.com
 */
public class SetWifiFragment extends BaseFragment {
    private static final String TAG = SetWifiFragment.class.getSimpleName();
    private static final int SHOW_PROMPT_STR = 1;
    private static final int GET_APMODE_INFO = 2;
    private static final int GET_WIFI_STATE = 3;
    private static final int GET_AP_RESULT = 4;
    private static final int GET_AP_FAILED = 5;
    //状态
    static public final int E_WIFI_BOND_CONNECTING = 1;
    static public final int E_WIFI_BOND_SUCESS = 2;
    static public final int E_WIFI_BOND_SSID_NEXIST = 3;
    static public final int E_WIFI_BOND_PWD_WRONG = 4;
    static public final int E_WIFI_BOND_CONNECT_ERR = 5;
    static public final int E_WIFI_BOND_ERROR = 6;
    //指令
    static public final int E_SRV_APMODE_GET_INFO = 206;
    static public final int E_SRV_APMODE_BONDING = 207;
    static public final int E_SRV_GET_APLIST = 216;
    static public final int E_SRV_START_SCAN_APLIST = 215;
    static public final String IP = "10.10.10.250";
    static public final int PORT = 12368;
    public static final int TIMEOUT_SCAN = 3;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_wifi_name_set_net_fragment)
    EditText etWifiName;
    @BindView(R.id.et_wifi_password_set_net_fragment)
    EditText etWifiPassword;
    @BindView(R.id.bt_submit_set_net_fragment)
    Button btSubmit;
    @BindView(R.id.tv_current_wifi)
    TextView tvCurrentWifi;
    Unbinder unbinder;
    private Dialog loadingDialog;
    private MyHandler handler;
    private Timer timerScan;
    private Timer timerGetInfo;
    private int scan_time = 0;
    public static final int SET_WIFI = 1001;
    public static final String WIFI_NAME = "IWTAC_";

    public static SetWifiFragment newInstance() {
        return new SetWifiFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_wifi, container, false);
        unbinder = ButterKnife.bind(this, view);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("为主机配网");
        toolbarMenu.setText("扫描");
        toolbarMenu.setOnClickListener(v -> scan());
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressedSupport());
    }

    private void initData() {
        WifiManager wifiManager = (WifiManager) App.mContext.getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//        if (NetworkUtils.isWifiConnected(App.mContext)) {
//            tvCurrentWifi.setText(wifiInfo.getSSID());
//        } else {
//            ALog.e(NetworkUtils.getNetworkType(App.mContext).value);
//            ALog.e(NetworkUtils.getNetworkType(App.mContext));
//            if (NetworkUtils.getNetworkType(App.mContext) == NetworkUtils.NetWorkType.UnKnown) {
//                tvCurrentWifi.setText("未知网络");
//            } else {
//                tvCurrentWifi.setText(NetworkUtils.getNetworkType(App.mContext).value + "G网络");
//            }
//        }

        handler = new MyHandler(this);
        String name = (String) SPCache.get(App.mContext, Constants.WIFI_NAME, "");
        String password = (String) SPCache.get(App.mContext, Constants.WIFI_PASSWORD, "");
        etWifiName.setText(name);
        etWifiPassword.setText(password);

        if (wifiInfo.getSSID().contains(WIFI_NAME)) {
            tvCurrentWifi.setText(wifiInfo.getSSID());
            scan();
        } else {
            tvCurrentWifi.setText("非主机热点");
        }
    }

    private void scan() {
        if (loadingDialog == null) {
            loadingDialog = DialogHelper.loading(_mActivity);
        }
        loadingDialog.show();
        IPC_DispatchText(IP, E_SRV_START_SCAN_APLIST, E_SRV_START_SCAN_APLIST, "");
        Toast.makeText(App.mContext, "scaning AP.", Toast.LENGTH_SHORT).show();
        timerScan = new Timer();
        timerScan.schedule(createTimerTaskAP(), 3000, 2000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (timerScan != null) {
            timerScan.cancel();
            timerScan = null;
        }

        if (timerGetInfo != null) {
            timerGetInfo.cancel();
            timerGetInfo = null;
        }
    }

    @OnClick({R.id.bt_submit_set_net_fragment, R.id.ll_current_wifi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_submit_set_net_fragment:
                bindWifi(etWifiName.getText().toString().trim(), etWifiPassword.getText().toString().trim());
                break;
            case R.id.ll_current_wifi:
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivityForResult(intent, SET_WIFI);
                break;
        }
    }

    private int IPC_DispatchText(final String ipaddr, final int cmdId, final int cmdIndex, final String xmlData) {
        new Thread(new Runnable() {
            public void run() {
                String result = WifiIpc.TSV_C_SendXmlCommand(ipaddr, PORT, cmdId, cmdIndex, xmlData);
                ALog.e("IPC_DispatchText", "TSV_C_SendXmlCommand:" + cmdId + " answer:" + result);
                switch (cmdId) {
                    case E_SRV_APMODE_GET_INFO:
                        ALog.e("这段代码块是永远不会走的");
                        if (result == null || result.equals("fail")) {
                            Message mMsg = new Message();
                            mMsg.what = GET_APMODE_INFO;
                            mMsg.arg1 = -1;
                            handler.sendMessage(mMsg);
                        } else {
                            Message mMsg = new Message();
                            mMsg.what = GET_APMODE_INFO;
                            mMsg.arg1 = 0;
                            handler.sendMessage(mMsg);
                        }
                        break;

                    case E_SRV_APMODE_BONDING:
                        if (result == null || result.endsWith("fail")) {
                            Message msg = new Message();
                            msg.what = GET_APMODE_INFO;
                            msg.arg1 = -1;
                            handler.sendMessage(msg);
                        } else {
                            Message msg = new Message();
                            msg.what = GET_APMODE_INFO;
                            msg.arg1 = 0;
                            handler.sendMessage(msg);
                        }
                        break;
                }
            }
        }).start();
        return 0;
    }

    static class MyHandler extends Handler {

        WeakReference<SetWifiFragment> mFragment;

        MyHandler(SetWifiFragment fragment) {
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            SetWifiFragment fragment = mFragment.get();

            switch (msg.what) {

                case GET_APMODE_INFO: {
                    if (msg.arg1 == 0) {//send success

                        if (fragment.timerGetInfo != null) {
                            fragment.timerGetInfo.cancel();
                        }
                        fragment.timerGetInfo = new Timer();
                        fragment.timerGetInfo.schedule(fragment.createTimerTask(), 2000, 3000);

                    } else {//send fail
                        Toast.makeText(App.mContext, "send password Fail", Toast.LENGTH_SHORT).show();

                    }
                    break;
                }

                case GET_WIFI_STATE: {
                    switch (msg.arg1) {
                        case E_WIFI_BOND_CONNECTING: {
                            Toast.makeText(App.mContext, "connecting wifi", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case E_WIFI_BOND_SUCESS: {
                            Toast.makeText(App.mContext, "set Wifi OK!", Toast.LENGTH_SHORT).show();
                            if (fragment.timerGetInfo != null) {
                                fragment.timerGetInfo.cancel();
                                fragment.timerGetInfo = null;
                            }
                            break;
                        }
                        case E_WIFI_BOND_SSID_NEXIST: {
                            Toast.makeText(App.mContext, "wrong SSID", Toast.LENGTH_SHORT).show();
                            if (fragment.timerGetInfo != null) {
                                fragment.timerGetInfo.cancel();
                                fragment.timerGetInfo = null;
                            }
                            break;
                        }
                        case E_WIFI_BOND_PWD_WRONG: {
                            Toast.makeText(App.mContext, "Wifi wrong password", Toast.LENGTH_SHORT).show();
                            if (fragment.timerGetInfo != null) {
                                fragment.timerGetInfo.cancel();
                                fragment.timerGetInfo = null;
                            }
                            break;
                        }
                        case E_WIFI_BOND_CONNECT_ERR: {
                            Toast.makeText(App.mContext, "Wifi connect error", Toast.LENGTH_SHORT).show();
                            if (fragment.timerGetInfo != null) {
                                fragment.timerGetInfo.cancel();
                                fragment.timerGetInfo = null;
                            }
                            break;
                        }
                        case E_WIFI_BOND_ERROR: {
                            Toast.makeText(App.mContext, "Wifi error", Toast.LENGTH_SHORT).show();
                            if (fragment.timerGetInfo != null) {
                                fragment.timerGetInfo.cancel();
                                fragment.timerGetInfo = null;
                            }
                            break;
                        }
                        default:
                            Toast.makeText(App.mContext, "errorCode:" + msg.arg1, Toast.LENGTH_SHORT).show();
                            if (fragment.timerGetInfo != null) {
                                fragment.timerGetInfo.cancel();
                                fragment.timerGetInfo = null;
                            }
                            break;
                    }
                    break;
                }

                case GET_AP_RESULT: {
                    int number = msg.arg1;
                    if (number > 0) {

                        String[] arrayAP = msg.getData().getStringArray("aplist");
                        int[] arrayVal = msg.getData().getIntArray("aplistval");

                        if (fragment.timerScan != null) {
                            fragment.timerScan.cancel();
                            fragment.timerScan = null;
                        }

                        Toast.makeText(App.mContext, "got ap list size=" + arrayAP.length, Toast.LENGTH_SHORT).show();
                        //TODO: Here we get wifi info list data, please handle it.

                        fragment.showDialog(arrayAP);

                    } else {
                        if (fragment.scan_time == TIMEOUT_SCAN) {
                            Toast.makeText(App.mContext, "scan AP failed", Toast.LENGTH_SHORT).show();
                            if (fragment.timerScan != null) {
                                fragment.timerScan.cancel();
                                fragment.timerScan = null;
                            }
                        }
                    }
                    break;
                }
                case GET_AP_FAILED: {
                    Toast.makeText(App.mContext, "scan AP failed", Toast.LENGTH_SHORT).show();
                    if (fragment.timerScan != null) {
                        fragment.timerScan.cancel();
                        fragment.timerScan = null;
                    }
                }
                break;
            }

            if (fragment.loadingDialog != null) {
                fragment.loadingDialog.dismiss();
            }
        }
    }

    private TimerTask createTimerTask() {
        TimerTask timertask = new TimerTask() {

            @Override
            public void run() {
                ALog.e("Thread-->" + Thread.currentThread().getName());

                // TODO Auto-generated method stub
                String result = WifiIpc.TSV_C_SendXmlCommand(IP, PORT, E_SRV_APMODE_GET_INFO, E_SRV_APMODE_GET_INFO, "");
                ALog.e("aplist", "E_SRV_APMODE_GET_INFO result=" + result);
                if (result != null) {
                    JSONArray root;
                    try {
                        root = new JSONArray(result);
                        JSONArray data = root.getJSONArray(1);
                        int wifiStatus = data.getInt(9);
                        Message msg = new Message();
                        msg.what = GET_WIFI_STATE;
                        msg.arg1 = wifiStatus;
                        handler.sendMessage(msg);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

        };
        return timertask;
    }

    private TimerTask createTimerTaskAP() {
        TimerTask timertask = new TimerTask() {
            @Override
            public void run() {
                scan_time++;

                ALog.e(Thread.currentThread().getName());

                String result = WifiIpc.TSV_C_SendXmlCommand(IP, PORT, E_SRV_GET_APLIST, E_SRV_GET_APLIST, "");
                ALog.e("aplist", "Get AP LIST result=" + result);
                if (result != null) {
                    JSONArray root;
                    try {
                        root = new JSONArray(result);
                        JSONArray data = root.getJSONArray(1);
                        int number = data.getInt(0);

                        String[] arrayAP = new String[number];
                        int[] arrayVal = new int[number];

                        for (int i = 0; i < number; i++) {
                            arrayAP[i] = data.getJSONArray(i + 1).getString(0);
                            arrayVal[i] = data.getJSONArray(i + 1).getInt(1);
                        }
                        Bundle extra = new Bundle();
                        extra.putStringArray("aplist", arrayAP);
                        extra.putIntArray("aplistval", arrayVal);

                        Message msg = new Message();
                        msg.what = GET_AP_RESULT;
                        msg.arg1 = number;
                        msg.setData(extra);
                        handler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Message msg = new Message();
                        msg.what = GET_AP_FAILED;
                        handler.sendMessage(msg);
                    }
                } else {
                    Message msg = new Message();
                    msg.what = GET_AP_FAILED;
                    handler.sendMessage(msg);
                }
            }

        };
        return timertask;
    }

    private void showDialog(String[] ssids) {
        new AlertDialog.Builder(_mActivity)
                .setTitle("请选择联网Wifi")
                .setNegativeButton("取消", null)
                .setItems(ssids, (dialog, which) -> etWifiName.setText(ssids[which]))
                .show();
    }

    private void bindWifi(String ssid, String password) {
        // TODO Auto-generated method stub
        if (loadingDialog == null) {
            loadingDialog = DialogHelper.loading(_mActivity);
        }
        loadingDialog.show();
        JSONArray root = new JSONArray();
        root.put(E_SRV_APMODE_BONDING);
        JSONArray jsonMembers = new JSONArray();
        jsonMembers.put(ssid);
        jsonMembers.put(password);
        root.put(jsonMembers);
        String json = root.toString();
        IPC_DispatchText("10.10.10.250", E_SRV_APMODE_BONDING, E_SRV_APMODE_BONDING, json);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SET_WIFI) {
            WifiManager wifiManager = (WifiManager) App.mContext.getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo.getSSID().contains(WIFI_NAME)) {
                tvCurrentWifi.setText(wifiInfo.getSSID());
                scan();
            } else {
                tvCurrentWifi.setText("非主机热点");
            }
        }
    }
}
