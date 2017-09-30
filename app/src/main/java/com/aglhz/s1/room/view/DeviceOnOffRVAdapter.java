package com.aglhz.s1.room.view;

import com.aglhz.abase.mvp.view.base.BaseRecyclerViewAdapter;
import com.aglhz.yicommunity.R;
import com.chad.library.adapter.base.BaseViewHolder;


/**
 * Author: LiuJia on 2017/8/30 0030 09:50.
 * Email: liujia95me@126.com
 */

public class DeviceOnOffRVAdapter extends BaseRecyclerViewAdapter<Integer, BaseViewHolder> {

    public DeviceOnOffRVAdapter() {
        super(R.layout.item_device_on_off_2);
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        helper.setText(R.id.tv_way_no, "第 " + item + " 路")
                .addOnClickListener(R.id.ll_open)
                .addOnClickListener(R.id.ll_close);

    }
}
