package com.aglhz.yicommunity.main.about;

import android.os.Bundle;

import cn.itsite.abase.mvp.view.base.BaseActivity;
import com.aglhz.yicommunity.R;
import com.aglhz.yicommunity.main.about.view.AboutUsFragment;

/**
 * [关于我们]的父容器。
 */
public class AboutActivity extends BaseActivity {
    private static final String TAG = AboutActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_main_activity, AboutUsFragment.newInstance());
        }
    }
}
