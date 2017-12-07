package com.aglhz.s1.security.view;


import android.widget.ImageView;

import com.aglhz.abase.BaseApplication;
import com.aglhz.abase.mvp.view.base.BaseRecyclerViewAdapter;
import com.aglhz.s1.entity.bean.DevicesBean;
import com.aglhz.yicommunity.R;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;


/**
 * Author: LiuJia on 2017/4/27 0027 08:49.
 * Email: liujia95me@126.com
 */
public class AddDetectorRVAdapter extends BaseRecyclerViewAdapter<DevicesBean.DataBean.DeviceTypeListBean, BaseViewHolder> {

    private static final String TAG = AddDetectorRVAdapter.class.getSimpleName();

    public AddDetectorRVAdapter() {
        super(R.layout.item_security);
    }

    @Override
    protected void convert(BaseViewHolder helper, DevicesBean.DataBean.DeviceTypeListBean item) {
        helper.setText(R.id.tv_name_item_security, item.getName())
                .setVisible(R.id.iv_state, false);

        ImageView ivSecurity = helper.getView(R.id.iv_icon_item_security);

        Glide.with(BaseApplication.mContext)
                .load("add_icon".equals(item.getIcon()) ? R.drawable.ic_add_house_red_140px : item.getIcon())
                .error(R.mipmap.ic_launcher)
                .into(ivSecurity);
    }
}
