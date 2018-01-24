package com.aglhz.s1.camera;

import android.os.Bundle;

import com.aglhz.abase.mvp.view.base.BaseActivity;
import com.aglhz.s1.entity.bean.DeviceListBean;
import com.aglhz.yicommunity.R;

public class CameraSettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            DeviceListBean.DataBean.SubDevicesBean bean = (DeviceListBean.DataBean.SubDevicesBean) getIntent().getSerializableExtra("bean");
//            loadRootFragment(R.id.fl_main_activity, CameraSettingFragment.newInstance(bean));
        }
    }
}
