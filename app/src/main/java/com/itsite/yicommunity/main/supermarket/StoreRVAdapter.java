package com.itsite.yicommunity.main.supermarket;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import com.itsite.yicommunity.R;
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
