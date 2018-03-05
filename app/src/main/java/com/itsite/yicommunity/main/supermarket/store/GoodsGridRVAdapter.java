package com.itsite.yicommunity.main.supermarket.store;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import com.itsite.yicommunity.R;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Author: LiuJia on 2017/11/1 0001 16:56.
 * Email: liujia95me@126.com
 */

public class GoodsGridRVAdapter extends BaseRecyclerViewAdapter<String,BaseViewHolder> {

    public GoodsGridRVAdapter() {
        super(R.layout.item_store_goods);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
