package com.aglhz.s1.main.smarthome;

import android.widget.ImageView;

import com.aglhz.abase.mvp.view.base.BaseRecyclerViewAdapter;
import com.aglhz.s1.App;
import com.aglhz.s1.entity.bean.EquipmentBean;
import com.aglhz.yicommunity.R;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Author: LiuJia on 2017/9/25 0025 10:17.
 * Email: liujia95me@126.com
 */

public class SmartHomeGridAdapter extends BaseRecyclerViewAdapter<EquipmentBean.DataBean.DataListBean, BaseViewHolder> {

    public SmartHomeGridAdapter() {
        super(R.layout.s1_item_smart_home_grid_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, EquipmentBean.DataBean.DataListBean item) {
        helper.setText(R.id.tv_name, item.getDeviceName());

        ImageView ivLogo = helper.getView(R.id.iv_logo);
        Glide.with(App.mContext)
                .load(item.getDeviceName().equals("添加中控") ? R.drawable.ic_add_house_red_140px : R.drawable.ic_zhuji_220px)
                .into(ivLogo);
    }
}
