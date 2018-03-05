package com.aglhz.yicommunity.main.supermarket.store;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import com.aglhz.yicommunity.App;
import com.aglhz.yicommunity.R;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: LiuJia on 2017/11/1 0001 11:27.
 * Email: liujia95me@126.com
 */

public class StoreHomeRVAdapter extends BaseRecyclerViewAdapter<String,BaseViewHolder> {

    public StoreHomeRVAdapter() {
        super(R.layout.item_store_home);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        StoreHomeGoodsRVAdapter adapter = new StoreHomeGoodsRVAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(App.mContext,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);

        List<String> datas = new ArrayList<>();
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        adapter.setNewData(datas);
    }
}
