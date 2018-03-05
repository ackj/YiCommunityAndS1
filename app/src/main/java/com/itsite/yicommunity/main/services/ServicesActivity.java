package com.itsite.yicommunity.main.services;

import android.os.Bundle;

import cn.itsite.abase.mvp.view.base.BaseActivity;
import com.itsite.yicommunity.R;
import com.itsite.yicommunity.common.Constants;
import com.itsite.yicommunity.main.services.view.ServicesListFragment;

/**
 * Created by leguang on 2017/6/26 0026.
 * Email：langmanleguang@qq.com
 */

public class ServicesActivity extends BaseActivity {
    public static final String TAG = ServicesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String servicesFid = getIntent().getStringExtra(Constants.SERVICE_FID);
        String servicesName = getIntent().getStringExtra(Constants.SERVICE_NAME);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_main_activity, ServicesListFragment.newInstance(servicesFid, servicesName));
        }
    }
}
