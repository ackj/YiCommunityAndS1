package com.itsite.yicommunity.main.house.view;

import android.widget.ImageView;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import com.itsite.yicommunity.App;
import com.itsite.yicommunity.R;
import com.itsite.yicommunity.entity.bean.HouseRightsBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * Author: LiuJia on 2017/4/20 13:53.
 * Email: liujia95me@126.com
 */
public class MemberRVAdapter extends BaseRecyclerViewAdapter<HouseRightsBean.DataBean, BaseViewHolder> {

    public MemberRVAdapter() {
        super(R.layout.item_rv_member);
    }

    @Override
    protected void convert(BaseViewHolder helper, HouseRightsBean.DataBean item) {

        Glide.with(App.mContext)
                .load(item.getMember().getIcon())
                .bitmapTransform(new CropCircleTransformation(App.mContext))
                .into((ImageView) helper.getView(R.id.iv_avatar));

        String memberType = "业主";
        switch (item.getMember().getIdentityType()) {
            case 2:
                memberType = "家属";
                break;
            case 3:
                memberType = "租客";
                break;
        }

        helper.setText(R.id.tv_name, item.getMember().getMname())
                .setText(R.id.tv_role, memberType);
    }
}