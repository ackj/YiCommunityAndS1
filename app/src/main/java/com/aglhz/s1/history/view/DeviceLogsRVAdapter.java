package com.aglhz.s1.history.view;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import com.aglhz.s1.entity.bean.DeviceLogBean;
import com.aglhz.yicommunity.R;
import com.chad.library.adapter.base.BaseViewHolder;


/**
 * Author: LiuJia on 2017/4/27 0027 16:16.
 * Email: liujia95me@126.com
 */

public class DeviceLogsRVAdapter extends BaseRecyclerViewAdapter<DeviceLogBean.DataBean.LogsBean, BaseViewHolder> {

    public DeviceLogsRVAdapter() {
        super(R.layout.item_history);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeviceLogBean.DataBean.LogsBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_content, item.getDes())
                .setText(R.id.tv_time, item.getLogtime());
    }
}
