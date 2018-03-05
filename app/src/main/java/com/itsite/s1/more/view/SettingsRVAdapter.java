package com.itsite.s1.more.view;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import com.itsite.s1.entity.bean.SettingsBean;
import com.itsite.yicommunity.R;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


/**
 * Author: LiuJia on 2017/4/27 0027 17:30.
 * Email: liujia95me@126.com
 */

public class SettingsRVAdapter extends BaseRecyclerViewAdapter<SettingsBean, BaseViewHolder> {


    public SettingsRVAdapter(List<SettingsBean> data) {
        super(R.layout.item_settings, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SettingsBean item) {
        helper.setText(R.id.tv_title_item_settings, item.title)
                .setText(R.id.tv_value_item_settings, item.data)
                .setImageResource(R.id.iv_icon_item_settings, item.icon);
    }
}
