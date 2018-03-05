package com.aglhz.yicommunity.main.supermarket.orderform;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Author: LiuJia on 2017/11/1 0001 16:46.
 * Email: liujia95me@126.com
 */

public class MineOrderformVPAdapter extends FragmentPagerAdapter {

    private String[] titles = {"全部", "待付款", "待收获", "已完成", "退款/售后"};

    public MineOrderformVPAdapter(FragmentManager fm) {
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
        return OrderformListFragment.newInstance();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
