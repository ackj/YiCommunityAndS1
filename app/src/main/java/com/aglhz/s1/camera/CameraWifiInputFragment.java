package com.aglhz.s1.camera;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.aglhz.abase.mvp.view.base.BaseFragment;
import com.aglhz.abase.utils.ToastUtils;
import com.aglhz.yicommunity.R;
import com.mediatek.elian.ElianNative;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author: LiuJia on 2017/9/6 0006 10:12.
 * Email: liujia95me@126.com
 */

public class CameraWifiInputFragment extends BaseFragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_wifiname)
    TextView tvWifiname;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_receive)
    TextView tvReceive;

    Unbinder unbinder;

    String ssid;
    String pwd="";
    boolean isRegFilter = false;
    boolean is5GWifi = false;
    boolean isWifiEncrypt = false;
    boolean isSend = false;
    ElianNative elain;
    WifiManager wifiManager;
    public UDPHelper mHelper;

    public static CameraWifiInputFragment newInstance() {
        CameraWifiInputFragment fragment = new CameraWifiInputFragment();
        return fragment;
    }

    static {
        System.loadLibrary("elianjni");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_wifi, container, false);
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
        toolbarTitle.setText("准备配置");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressedSupport();
            }
        });
    }

    private void initData() {
        currenWifi();
        regFilter();
        //监听UDP广播
        mHelper = new UDPHelper(_mActivity,9988);
        mHelper.StartListen();
    }

    @SuppressLint("HandlerLeak")
    private void initListener() {
        mHelper.setCallBack(new Handler() {

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                switch (msg.what) {
                    case UDPHelper.HANDLER_MESSAGE_BIND_ERROR:
                        Log.e("my", "HANDLER_MESSAGE_BIND_ERROR");
                        break;
                    case UDPHelper.HANDLER_MESSAGE_RECEIVE_MSG:
                        Bundle bundle = msg.getData();
                        String deviceId = bundle.getString("contactId");//设备ID
                        String frag = bundle.getString("frag");//有无密码标记
                        String ip = bundle.getString("ip");//id地址
                        int type = bundle.getInt("type", 0);//设备主类型
                        int subType = bundle.getInt("subType", 0);//设备子类型
                        String deviceInfo = "deviceId=" + deviceId + " deviceType=" + type + " subType=" + subType + " ip=" + ip;
                        if (Integer.parseInt(frag) == 0) {
                            deviceInfo = deviceInfo + "无密码";
                        } else {
                            deviceInfo = deviceInfo + "有密码";
                        }
                        tvReceive.append(deviceInfo + "\n\n");
                        break;
                }
            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (isSend) {
            stopSendWifi();
            isSend = false;
        }
        if (isRegFilter) {
            _mActivity.unregisterReceiver(br);
            isRegFilter = false;
        }
        if (mHelper != null) {
            mHelper.StopListen();
        }
    }

    public void regFilter(){
        IntentFilter filter=new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        _mActivity.registerReceiver(br,filter);
        isRegFilter=true;
    }

    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                currenWifi();
            }
        }
    };

    private byte mAuthMode;
    private byte AuthModeAutoSwitch = 2;
    private byte AuthModeOpen = 0;
    private byte AuthModeShared = 1;
    private byte AuthModeWPA = 3;
    private byte AuthModeWPA1PSKWPA2PSK = 9;
    private byte AuthModeWPA1WPA2 = 8;
    private byte AuthModeWPA2 = 6;
    private byte AuthModeWPA2PSK = 7;
    private byte AuthModeWPANone = 5;
    private byte AuthModeWPAPSK = 4;

    //获取当前连接wifi
    public void currenWifi() {
        WifiInfo wifiInfo = getConnectWifiInfo();
        if (wifiInfo == null) {
            ssid = "";
            tvWifiname.setText("请先连接wifi");
            return;
        }
        ssid = wifiInfo.getSSID();
        if (ssid == null) {
            return;
        }
        if (ssid.equals("")) {
            return;
        }
        if (ssid.length() <= 0) {
            return;
        }
        int a = ssid.charAt(0);
        if (a == 34) {
            ssid = ssid.substring(1, ssid.length() - 1);
        }
        if (!ssid.equals("<unknown ssid>") && !ssid.equals("0x")) {
            tvWifiname.setText(ssid);
        }
        List<ScanResult> wifiList = getLists(_mActivity);
        if (wifiList == null) {
            return;
        }
        for (int i = 0; i < wifiList.size(); i++) {
            ScanResult result = wifiList.get(i);
            if (!result.SSID.equals(ssid)) {
                continue;
            }
            is5GWifi = is5GWifi(result.frequency);
            isWifiEncrypt = isWifiEncrypt(result);
            boolean bool1, bool2, bool3, bool4;
            bool1 = result.capabilities.contains("WPA-PSK");
            bool2 = result.capabilities.contains("WPA2-PSK");
            bool3 = result.capabilities.contains("WPA-EAP");
            bool4 = result.capabilities.contains("WPA2-EAP");
            if (result.capabilities.contains("WEP")) {
                this.mAuthMode = this.AuthModeOpen;
            }
            if ((bool1) && (bool2)) {
                mAuthMode = AuthModeWPA1PSKWPA2PSK;
            } else if (bool2) {
                this.mAuthMode = this.AuthModeWPA2PSK;
            } else if (bool1) {
                this.mAuthMode = this.AuthModeWPAPSK;
            } else if ((bool3) && (bool4)) {
                this.mAuthMode = this.AuthModeWPA1WPA2;
            } else if (bool4) {
                this.mAuthMode = this.AuthModeWPA2;
            } else {
                if (!bool3)
                    break;
                this.mAuthMode = this.AuthModeWPA;
            }
        }
    }

    //判断是否连接上wifi
    public boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) _mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    //获取当前连接wifi的WifiInfo
    public WifiInfo getConnectWifiInfo() {
        if (!isWifiConnected()) {
            return null;
        }
        WifiManager wifiManager = (WifiManager) _mActivity.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager == null) {
            return null;
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo;
    }

    //获取wifi列表
    public List<ScanResult> getLists(Context context) {
        wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        wifiManager.startScan();
        List<ScanResult> lists = wifiManager.getScanResults();
        return lists;
    }

    //判断是不是5Gwifi
    public static boolean is5GWifi(int frequency) {
        String str = String.valueOf(frequency);
        if (str.length() > 0) {
            char a = str.charAt(0);
            if (a == '5') {
                return true;
            }
        }
        return false;
    }

    //WiFi是否加密
    public static boolean isWifiEncrypt(ScanResult result) {
        return !(result.capabilities.toLowerCase().indexOf("wep") != -1
                || result.capabilities.toLowerCase().indexOf("wpa") != -1);
    }


    //开始发包
    private void sendWifi() {
        if (elain == null) {
            elain = new ElianNative();
        }
        if (null != ssid && !"".equals(ssid)) {
            elain.InitSmartConnection(null, 1, 1);
            //wifi名  wifi密码  加密方式
            elain.StartSmartConnection(ssid, pwd, "", mAuthMode);
        }
    }

    private void stopSendWifi() {
        if (elain != null) {
            elain.StopSmartConnection();
        }
    }

    @OnClick({R.id.btn_send, R.id.btn_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                pwd=etPassword.getText().toString().trim();
                if (!isWifiConnected()||ssid == null || ssid.equals("")||ssid.equals("<unknown ssid>")) {
                    ToastUtils.showToast(_mActivity,"请先将手机连接到WiFi");
                    return;
                }
                if(is5GWifi){
                    ToastUtils.showToast(_mActivity,"设备不支持5G网络");
                    return;
                }
                if (!isWifiEncrypt) {
                    if (TextUtils.isEmpty(pwd)) {
                        ToastUtils.showToast(_mActivity,"请输入WiFi密码");
                        return;
                    }
                }
                sendWifi();
                isSend=true;
                tvReceive.append("开始发包......\n");
                break;
            case R.id.btn_stop:
                if(!isSend){
                    return;
                }
                stopSendWifi();
                tvReceive.append("停止发包\n");
                isSend=false;
                break;
        }
    }
}
