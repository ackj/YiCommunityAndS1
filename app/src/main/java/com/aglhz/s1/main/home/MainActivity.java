package com.aglhz.s1.main.home;

import android.os.Bundle;

import cn.itsite.abase.mvp.view.base.BaseActivity;
import com.aglhz.s1.main.home.view.MainFragment;
import com.aglhz.yicommunity.R;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_main_activity, MainFragment.newInstance());
        }
    }

    @Override
    public boolean swipeBackPriority() {
        return false;
    }
}
