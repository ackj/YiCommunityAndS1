package com.itsite.yicommunity.main.parking;

import android.os.Bundle;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseActivity;
import com.itsite.yicommunity.R;
import com.itsite.yicommunity.common.Constants;
import com.itsite.yicommunity.main.parking.view.ApplyCardFragment;
import com.itsite.yicommunity.main.parking.view.CarCardFragment;
import com.itsite.yicommunity.main.parking.view.CarportFragment;

/**
 * 停车场模块的父容器
 */
public class ParkingActivity extends BaseActivity {
    private static final String TAG = ParkingActivity.class.getSimpleName();
    int intFromTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取上一个Activity传递过来的数据。
        initData();
        if (savedInstanceState == null) {
            switch (intFromTo) {
                case Constants.MY_CARD:
                    loadRootFragment(R.id.fl_main_activity, CarCardFragment.newInstance());
                    break;
                case Constants.CARPORT:
                    loadRootFragment(R.id.fl_main_activity, CarportFragment.newInstance());
                    break;
                case Constants.CARD_TRANSACT:
                    loadRootFragment(R.id.fl_main_activity, ApplyCardFragment.newInstance());
                    break;
                default:
            }
        }
    }

    private void initData() {
        intFromTo = getIntent().getIntExtra(Constants.KEY_FROM_TO, 0);
        ALog.e(intFromTo);
    }
}
