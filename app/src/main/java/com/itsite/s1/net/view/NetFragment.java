package com.itsite.s1.net.view;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import com.itsite.s1.App;
import com.itsite.yicommunity.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.tsvclient.ipc.WifiIpc.TSV_C_SendXmlCommand;


/**
 * Created by leguang on 2017/5/24 0029.
 * Email：langmanleguang@qq.com
 */
public class NetFragment extends BaseFragment {
    private static final String TAG = NetFragment.class.getSimpleName();
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_set_net_set_net_fragment)
    LinearLayout llSettingNet;
    @BindView(R.id.ll_update_password_set_net_fragment)
    LinearLayout llUpdatePassword;
    Unbinder unbinder;
    @BindView(R.id.tv_ssid_set_net_fragment)
    TextView tvSsid;

    public static final int SET_WIFI = 1001;

    public static NetFragment newInstance() {
        return new NetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_net, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
    }

    private void initData() {
        WifiManager wifiManager = (WifiManager) App.mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        tvSsid.setText(wifiInfo.getSSID());
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("主机热点设置");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_set_net_set_net_fragment, R.id.ll_update_password_set_net_fragment, R.id.ll_exithotspot})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_set_net_set_net_fragment:
                start(SetWifiFragment.newInstance());
//                IPC_DispatchText("10.10.10.250", 215, 215, "");
                break;
            case R.id.ll_update_password_set_net_fragment:
                start(HotPasswordFragment.newInstance());

//                IPC_DispatchText("10.10.10.250", 216, 216, "");
                break;
            case R.id.ll_exithotspot:
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivity(intent);
                break;
        }
    }

    private int IPC_DispatchText(final String paramString1, final int paramInt1, final int paramInt2, final String paramString2) {
        new Thread(new Runnable() {
            public void run() {
                String result = TSV_C_SendXmlCommand(paramString1, 12368, paramInt1, paramInt2, paramString2);
                ALog.e("result-->" + result);
            }
        }).start();
        return 0;
    }

    @Override
    public boolean onBackPressedSupport() {
        return true;
    }

}
