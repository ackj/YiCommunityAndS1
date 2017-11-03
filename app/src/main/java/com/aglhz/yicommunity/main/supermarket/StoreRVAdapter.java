package com.aglhz.yicommunity.main.supermarket;

import com.aglhz.abase.mvp.view.base.BaseRecyclerViewAdapter;
import com.aglhz.yicommunity.R;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Author: LiuJia on 2017/10/31 0031 10:14.
 * Email: liujia95me@126.com
 */

public class StoreRVAdapter extends BaseRecyclerViewAdapter<String,BaseViewHolder> {

    public StoreRVAdapter() {
        super(R.layout.item_rv_store);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
    }
}
