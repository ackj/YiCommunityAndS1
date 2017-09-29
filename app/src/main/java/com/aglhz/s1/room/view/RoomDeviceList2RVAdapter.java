package com.aglhz.s1.room.view;

import android.widget.ImageView;

import com.aglhz.abase.BaseApplication;
import com.aglhz.abase.mvp.view.base.BaseRecyclerViewAdapter;
import com.aglhz.s1.entity.bean.DeviceListBean;
import com.aglhz.yicommunity.R;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;


/**
 * Author: LiuJia on 2017/8/20 0020 20:00.
 * Email: liujia95me@126.com
 */

public class RoomDeviceList2RVAdapter extends BaseRecyclerViewAdapter<DeviceListBean.DataBean.SubDevicesBean, BaseViewHolder> {

    public RoomDeviceList2RVAdapter() {
        super(R.layout.item_device);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeviceListBean.DataBean.SubDevicesBean item) {
        helper.setText(R.id.tv_device_name, item.getName())
                .addOnClickListener(R.id.iv_setting)
                .addOnClickListener(R.id.ll_container);

        ImageView ivIcon = helper.getView(R.id.iv_icon);
        Glide.with(BaseApplication.mContext)
                .load(item.getIcon())
                .into(ivIcon);
    }
}
