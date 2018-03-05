package com.itsite.yicommunity.main.publish;

import android.os.Bundle;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseActivity;
import com.itsite.yicommunity.R;
import com.itsite.yicommunity.common.Constants;
import com.itsite.yicommunity.main.publish.view.ComplainFragment;
import com.itsite.yicommunity.main.propery.view.RepairRecordFragment;

/**
 * 物业模块的父容器
 */
public class PropertyActivity extends BaseActivity {
    private static final String TAG = PropertyActivity.class.getSimpleName();
    int intFromTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取上一个Activity传递过来的数据。
        initData();
        if (savedInstanceState == null) {
            switch (intFromTo) {
                case Constants.PROPERTY_REPAIR:
                    loadRootFragment(R.id.fl_main_activity, RepairRecordFragment.newInstance());
                    break;
                case Constants.COMPLAIN:
                    loadRootFragment(R.id.fl_main_activity, ComplainFragment.newInstance());
                    break;
            }
        }
    }

    private void initData() {
        intFromTo = getIntent().getIntExtra(Constants.KEY_FROM_TO, 0);
        ALog.e(intFromTo);
    }
}
