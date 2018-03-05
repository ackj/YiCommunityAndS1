package com.itsite.yicommunity.main.propery.view;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import com.itsite.yicommunity.R;
import com.itsite.yicommunity.entity.bean.PropertyPayBean;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by leguang on 2017/4/29 0029.
 * Email：langmanleguang@qq.com
 */

public class PropertyPayRVAdapter extends BaseRecyclerViewAdapter<PropertyPayBean.DataBean.BillListBean, BaseViewHolder> {

    public PropertyPayRVAdapter() {
        super(R.layout.item_rv_property_pay);
    }

    @Override
    protected void convert(BaseViewHolder helper, PropertyPayBean.DataBean.BillListBean item) {
        helper.setText(R.id.tv_price_item_property_pay, item.getStatus() == 1 ? item.getAmount() + "元" : "-" + item.getAmount() + "元")
                .setText(R.id.tv_house_name_item_property_pay, item.getBillName())
                .setText(R.id.tv_state_item_property_pay, item.getStatus() == 1 ? "已缴费" : "未缴费")
                .setTextColor(R.id.tv_state_item_property_pay, item.getStatus() == 1 ? 0XBB00CC00 : 0XBBFF0000)
                .setText(R.id.tv_date_item_property_pay, item.getSettlementStartDate().substring(0, 7));//只需要显示月份，所以截取一下。
    }
}
