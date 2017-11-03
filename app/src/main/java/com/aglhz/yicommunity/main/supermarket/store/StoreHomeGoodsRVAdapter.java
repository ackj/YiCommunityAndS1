package com.aglhz.yicommunity.main.supermarket.store;

import com.aglhz.abase.mvp.view.base.BaseRecyclerViewAdapter;
import com.aglhz.yicommunity.R;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Author: LiuJia on 2017/11/1 0001 15:15.
 * Email: liujia95me@126.com
 */

public class StoreHomeGoodsRVAdapter extends BaseRecyclerViewAdapter<String,BaseViewHolder>{
    public StoreHomeGoodsRVAdapter() {
        super(R.layout.item_store_home_goods);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
