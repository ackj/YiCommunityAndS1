package com.aglhz.s1.main.smarthome;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aglhz.abase.mvp.view.base.BaseRecyclerViewAdapter;
import com.aglhz.s1.entity.bean.CameraBean;
import com.aglhz.s1.entity.bean.EquipmentBean;
import com.aglhz.s1.entity.bean.SmartHomeBean;
import com.aglhz.yicommunity.App;
import com.aglhz.yicommunity.R;
import com.chad.library.adapter.base.BaseViewHolder;


/**
 * Author: LiuJia on 2017/9/25 0025 09:49.
 * Email: liujia95me@126.com
 */

public class SmartHomeListAdapter extends BaseRecyclerViewAdapter<SmartHomeBean, BaseViewHolder> {
    public static final String TAG = SmartHomeListAdapter.class.getSimpleName();

    public SmartHomeListAdapter() {
        super(R.layout.s1_item_smart_home);
    }

    @Override
    protected void convert(BaseViewHolder helper, SmartHomeBean item) {
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(App.mContext, 3));
        if (item.type == SmartHomeBean.TYPE_EQUIPMENT) {
            helper.setText(R.id.tv_title, "智能中控");
            SmartHomeEquipementAdapter adapter = new SmartHomeEquipementAdapter();
            recyclerView.setAdapter(adapter);

            adapter.setNewData(item.equipmentList);
            adapter.setOnItemClickListener((adapter1, view, position) ->
                    equipmentClickListener.click(adapter, item.equipmentList.get(position), position));

            adapter.setOnItemLongClickListener((adapter1, view, position) -> {
                equipmentClickListener.longClick(adapter, item.equipmentList.get(position), position);
                return false;
            });
        } else if (item.type == SmartHomeBean.TYPE_CAMERA) {
            helper.setText(R.id.tv_title, "智能监控");
            SmartHomeCameraAdapter adapter = new SmartHomeCameraAdapter();
            recyclerView.setAdapter(adapter);

            adapter.setNewData(item.cameraList);
            adapter.setOnItemClickListener((adapter1, view, position) ->
                    cameraClickListener.click(adapter, item.cameraList.get(position), position));

            adapter.setOnItemLongClickListener((adapter1, view, position) -> {
                cameraClickListener.longClick(adapter, item.cameraList.get(position), position);
                return false;
            });
        }


    }

    OnItemEquipmentClickListener equipmentClickListener;

    public void setOnItemEquipmentClickListener(OnItemEquipmentClickListener listener) {
        this.equipmentClickListener = listener;
    }

    OnItemCameraClickListener cameraClickListener;

    public void setOnItemCameraClickListener(OnItemCameraClickListener listener) {
        cameraClickListener = listener;
    }

    public interface OnItemEquipmentClickListener {
        void click(BaseRecyclerViewAdapter adapter, EquipmentBean.DataBean.DataListBean item, int position);

        void longClick(BaseRecyclerViewAdapter adapter, EquipmentBean.DataBean.DataListBean item, int position);
    }

    public interface OnItemCameraClickListener {
        void click(BaseRecyclerViewAdapter adapter, CameraBean.DataBean item, int position);

        void longClick(BaseRecyclerViewAdapter adapter, CameraBean.DataBean item, int position);
    }
}
