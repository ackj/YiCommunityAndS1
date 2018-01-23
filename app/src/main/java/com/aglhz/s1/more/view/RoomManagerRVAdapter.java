package com.aglhz.s1.more.view;

import android.widget.ImageView;

import com.aglhz.abase.BaseApp;
import com.aglhz.abase.mvp.view.base.BaseRecyclerViewAdapter;
import com.aglhz.s1.entity.bean.RoomsBean;
import com.aglhz.yicommunity.R;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;


/**
 * Author: LiuJia on 2017/5/31 0031 11:12.
 * Email: liujia95me@126.com
 */

public class RoomManagerRVAdapter extends BaseRecyclerViewAdapter<RoomsBean.DataBean.RoomListBean, BaseViewHolder> {

    public RoomManagerRVAdapter() {
        super(R.layout.item_room_type);
    }

    @Override
    protected void convert(BaseViewHolder helper, RoomsBean.DataBean.RoomListBean item) {
        ImageView ivIcon = helper.getView(R.id.iv_icon_item_room_type);
        helper.setText(R.id.tv_name_item_room_type, item.getName());
        int ivRes = R.drawable.room_dating_1242px_745px;
        switch (item.getName()) {
            case "厨房":
                ivRes = R.drawable.room_chufang_1242px_745px;
                break;
            case "浴室":
                ivRes = R.drawable.room_cesuo_1242px_745px;
                break;
            case "卧室":
                ivRes = R.drawable.room_room_1242px_745px;
                break;
            case "添加房间":
                ivRes = R.drawable.ic_add_house_red_140px;
                break;
        }
        Glide.with(BaseApp.mContext)
                .load(ivRes)
                .error(R.mipmap.ic_launcher)
                .into(ivIcon);
    }
}
