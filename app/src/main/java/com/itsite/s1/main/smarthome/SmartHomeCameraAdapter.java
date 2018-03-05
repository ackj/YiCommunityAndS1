package com.itsite.s1.main.smarthome;

import android.widget.ImageView;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import com.itsite.s1.App;
import com.itsite.yicommunity.R;
import com.itsite.yicommunity.entity.bean.MainDeviceListBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Author： Administrator on 2017/9/29 0029.
 * Email： liujia95me@126.com
 */
public class SmartHomeCameraAdapter  extends BaseRecyclerViewAdapter<MainDeviceListBean.DataBean, BaseViewHolder> {

    public SmartHomeCameraAdapter() {
        super(R.layout.s1_item_smart_home_grid_item);
    }

    @Override
    protected void convert(BaseViewHolder helper,MainDeviceListBean.DataBean item) {
        helper.setText(R.id.tv_name, item.getName());
        ImageView ivLogo = helper.getView(R.id.iv_logo);
        Glide.with(App.mContext)
                .load(item.getName().equals("添加监控") ? R.drawable.ic_add_house_red_140px : R.drawable.ic_jiankong_220px)
                .into(ivLogo);
    }
}
