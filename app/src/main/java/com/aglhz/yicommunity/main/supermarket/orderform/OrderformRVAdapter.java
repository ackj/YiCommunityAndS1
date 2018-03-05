package com.aglhz.yicommunity.main.supermarket.orderform;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import com.aglhz.yicommunity.R;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Author: LiuJia on 2017/11/2 0002 15:41.
 * Email: liujia95me@126.com
 */

public class OrderformRVAdapter extends BaseRecyclerViewAdapter<String,BaseViewHolder> {

    public OrderformRVAdapter() {
        super(R.layout.item_rv_order_status);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
