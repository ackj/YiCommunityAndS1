package com.aglhz.yicommunity.main.mine.view;

import android.widget.ImageView;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;

import com.aglhz.yicommunity.entity.bean.MyHousesBean;
import com.aglhz.yicommunity.App;
import com.aglhz.yicommunity.R;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * Author: LiuJia on 2017/4/20 13:53.
 * Email: liujia95me@126.com
 */
public class MyHousesMembersRVAdapter extends BaseRecyclerViewAdapter<MyHousesBean.DataBean.AuthBuildingsBean.MembersBean, BaseViewHolder> {

    public MyHousesMembersRVAdapter() {
        super(R.layout.item_rv_member);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyHousesBean.DataBean.AuthBuildingsBean.MembersBean item) {

        Glide.with(App.mContext)
                .load(item.getIcon())
                .bitmapTransform(new CropCircleTransformation(App.mContext))
                .into((ImageView) helper.getView(R.id.iv_avatar));

        String memberType = "业主";
        switch (item.getIdentityType()) {
            case 2:
                memberType = "家属";
                break;
            case 3:
                memberType = "租客";
                break;
        }

        helper.setText(R.id.tv_name, item.getMname())
                .setText(R.id.tv_role, memberType);
    }
}
