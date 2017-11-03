package com.aglhz.yicommunity.main.supermarket.store;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Author: LiuJia on 2017/11/1 0001 16:46.
 * Email: liujia95me@126.com
 */

public class GoodsTypeVPAdapter extends FragmentPagerAdapter {

    private String[] titles = {"美味零食", "缤纷饮料", "日用百货", "清爽酒水"};

    public GoodsTypeVPAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return  GoodsGridFragment.newInstance();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
