package com.aglhz.s1.main.smarthome;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aglhz.abase.mvp.view.base.BaseActivity;
import com.aglhz.abase.mvp.view.base.BaseRecyclerViewAdapter;
import com.aglhz.s1.entity.bean.EquipmentBean;
import com.aglhz.s1.main.home.MainActivity;
import com.aglhz.s1.qrcode.ScanQRCodeFragment;
import com.aglhz.yicommunity.App;
import com.aglhz.yicommunity.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


/**
 * Author: LiuJia on 2017/9/25 0025 09:49.
 * Email: liujia95me@126.com
 */

public class SmartHomeListAdapter extends BaseRecyclerViewAdapter<List<EquipmentBean.DataBean.DataListBean>,BaseViewHolder> {

    public SmartHomeListAdapter() {
        super(R.layout.s1_item_smart_home);
    }

    @Override
    protected void convert(BaseViewHolder helper, List<EquipmentBean.DataBean.DataListBean> item) {
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(App.mContext,3));
        SmartHomeGridAdapter adapter = new SmartHomeGridAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setNewData(item);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
                listener.click(adapter,item.get(position),position);
            }
        });

        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter1, View view, int position) {
                listener.longClick(adapter,item.get(position),position);
                return false;
            }
        });
    }

    OnItemGridClickListener listener;

    public void setOnItemGridClickListener(OnItemGridClickListener listener){
        this.listener = listener;
    }

    public interface OnItemGridClickListener{
        void click(BaseRecyclerViewAdapter adapter,EquipmentBean.DataBean.DataListBean item,int position);
        void longClick(BaseRecyclerViewAdapter adapter,EquipmentBean.DataBean.DataListBean item, int position);
    }
}
