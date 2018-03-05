package com.aglhz.s1.host.view;

import android.graphics.Color;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;

import com.aglhz.s1.entity.bean.GatewaysBean;
import com.aglhz.yicommunity.R;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by leguang on 2017/6/22 0022.
 * Email：langmanleguang@qq.com
 */
public class HostListRVAdapter extends BaseRecyclerViewAdapter<GatewaysBean.DataBean, BaseViewHolder> {

    public HostListRVAdapter() {
        super(R.layout.item_rv_gateway);
    }

    @Override
    protected void convert(BaseViewHolder helper, GatewaysBean.DataBean item) {
        helper.setText(R.id.tv_people_type_item_rv_gateway, item.getIsManager() == 1 ? "管理员" : "成员")
                .setText(R.id.tv_current_item_rv_gateway, item.getIsCurrent() == 1 ? "当前主机" : "")
                .setText(R.id.tv_name_item_rv_gateway, "名称：" + item.getName() + (item.getIsOnline() == 1 ? "　(在线)" : "　(离线)"))
                .setText(R.id.tv_code_item_rv_gateway, "编号：" + item.getNo())
                .setTextColor(R.id.tv_name_item_rv_gateway,
                        item.getIsOnline() == 1 ? Color.parseColor("#32E232") : Color.parseColor("#999999"));
    }
}