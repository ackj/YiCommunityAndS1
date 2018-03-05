package com.aglhz.yicommunity.main.supermarket.shopcart;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import com.aglhz.yicommunity.R;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Author: LiuJia on 2017/11/2 0002 08:50.
 * Email: liujia95me@126.com
 */

public class ShopcartRVAdapter extends BaseRecyclerViewAdapter<String,BaseViewHolder> {
    public ShopcartRVAdapter() {
        super(R.layout.item_shopcart);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
