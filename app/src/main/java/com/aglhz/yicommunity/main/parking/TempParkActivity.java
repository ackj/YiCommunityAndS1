package com.aglhz.yicommunity.main.parking;

import android.os.Bundle;

import com.aglhz.abase.mvp.view.base.BaseActivity;
import com.aglhz.yicommunity.R;
import com.aglhz.yicommunity.main.park.view.CarCardFragment;

/**
 * @author langmanleguang@qq.com
 *         临时停车场模块的容器Activity。
 */
public class TempParkActivity extends BaseActivity {
    private static final String TAG = TempParkActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_main_activity, CarCardFragment.newInstance());
        }
    }
}
