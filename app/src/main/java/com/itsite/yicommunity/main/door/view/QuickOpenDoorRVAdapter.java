package com.itsite.yicommunity.main.door.view;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import com.itsite.yicommunity.R;
import com.itsite.yicommunity.entity.bean.DoorListBean;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Author: LiuJia on 2017/4/23 0023.
 * Email: liujia95me@126.com
 */

public class QuickOpenDoorRVAdapter extends BaseRecyclerViewAdapter<DoorListBean.DataBean, BaseViewHolder> {
    public int prePosition = 0;

    public QuickOpenDoorRVAdapter() {
        super(R.layout.item_quick_open_door);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoorListBean.DataBean item) {
        if (item.isQuickopen()) {
            prePosition = helper.getLayoutPosition();
        }

        helper.setText(R.id.tv_door_name, item.getName())
                .setChecked(R.id.checkbox, item.isQuickopen());
    }

    public void setSelectedItem(int position) {
        mData.get(position).setQuickopen(!mData.get(position).isQuickopen());

//        mData.get(prePosition).setQuickopen(false);
//        mData.get(position).setQuickopen(true);
//        notifyItemChanged(prePosition);
        notifyItemChanged(position);
//        prePosition = position;
    }
}