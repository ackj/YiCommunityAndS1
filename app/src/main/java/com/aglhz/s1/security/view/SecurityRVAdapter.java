package com.aglhz.s1.security.view;

import android.widget.ImageView;

import com.aglhz.abase.BaseApplication;
import com.aglhz.abase.mvp.view.base.BaseRecyclerViewAdapter;
import com.aglhz.s1.common.Constants;
import com.aglhz.s1.common.DefenseLineLevel;
import com.aglhz.s1.entity.bean.SecurityBean;
import com.aglhz.yicommunity.R;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;


/**
 * Author: LiuJia on 2017/8/14 0014 15:48.
 * Email: liujia95me@126.com
 */

public class SecurityRVAdapter extends BaseRecyclerViewAdapter<SecurityBean.DataBean.SubDevicesBean, BaseViewHolder> {

    public SecurityRVAdapter() {
        super(R.layout.item_security);
    }

    private String dstatus = Constants.GATEWAY_STATE_CANCLE;

    //更新主机状态
    public void setHostState(String dstatus) {
        this.dstatus = dstatus;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, SecurityBean.DataBean.SubDevicesBean item) {
        boolean isOpen = false;
        if (DefenseLineLevel.DLL_24HOUR.equals(item.getDefenseLevel())) {
            isOpen = true;
        } else if (DefenseLineLevel.DLL_FIRST.equals(item.getDefenseLevel()) &&
                !Constants.GATEWAY_STATE_CANCLE.equals(dstatus)) {
            isOpen = true;
        } else if (DefenseLineLevel.DLL_SECOND.equals(item.getDefenseLevel()) &&
                Constants.GATEWAY_STATE_FARAWAY.equals(dstatus)) {
            isOpen = true;
        }
        helper.setText(R.id.tv_name_item_security, item.getName())
                .setVisible(R.id.iv_state, !"add_icon".equals(item.getIcon()))
                .setImageResource(R.id.iv_state, isOpen ? R.drawable.ic_state_green_24px : R.drawable.ic_state_red_24px);
        ImageView ivSecurity = helper.getView(R.id.iv_icon_item_security);
        Glide.with(BaseApplication.mContext)
                .load("add_icon".equals(item.getIcon()) ? R.drawable.ic_add_house_red_140px : item.getIcon())
                .error(R.mipmap.ic_logo)
                .into(ivSecurity);
    }
}
