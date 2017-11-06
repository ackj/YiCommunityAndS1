package com.aglhz.yicommunity.main.parking.view;

import com.aglhz.abase.mvp.view.base.BaseRecyclerViewAdapter;
import com.aglhz.yicommunity.R;
import com.aglhz.yicommunity.entity.bean.CarCardListBean;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 车卡管理模块。
 */
public class CarCardAdapter extends BaseRecyclerViewAdapter<CarCardListBean.DataBean.CardListBean, BaseViewHolder> {
    public static final String TAG = CarCardAdapter.class.getSimpleName();

    public CarCardAdapter() {
        super(R.layout.item_rv_car_card);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarCardListBean.DataBean.CardListBean item) {
//        helper.setBackgroundRes(R.id.cl_header_item_rv_car_card,);

        helper.setText(R.id.tv_plate_item_rv_car_card, item.getCarNo())
                .setText(R.id.tv_name_item_rv_car_card, item.getCustomerName())
                .setText(R.id.tv_time_item_rv_car_card, "到期时间：" + item.getEndTime())
                .setText(R.id.tv_park_item_rv_car_card, item.getParkPlace().getName())
                .addOnClickListener(R.id.tv_action_item_rv_car_card);


//        LinearLayout llLayout = helper.getView(R.id.ll_view_group);
//        TextView tvSurplusDays = helper.getView(R.id.tv_surplus_days);
//        TextView tvTermOfValidity = helper.getView(R.id.tv_term_of_validity);
//        ImageView ivDeleteCard = helper.getView(R.id.iv_delete_card);
//
//        //-------------- 初始化 -------------
//        tvTermOfValidity.setText("有效期：暂无有效期");
//
//        if ("月租卡".equals(item.getCardType())) {
//            //-------------- 月租卡 -------------
//            llLayout.setBackgroundResource(R.drawable.bg_month_card_1017px_657px);
//            tvSurplusDays.setVisibility(View.VISIBLE);
//            ivDeleteCard.setVisibility(View.GONE);
//            tvTermOfValidity.setVisibility(View.VISIBLE);
//
//            if (item.getApproveState() == 0) {
//                //-------------- 未审核-------------
//                tvSurplusDays.setText("正在审核中");
//            } else if (item.getApproveState() == 1) {
//                //-------------- 审核通过 -------------
//                if (item.getNeedToPayType() > 1) {
//                    //-------------- 立即续费 -------------
//                    if (item.getSurplusDays() <= 0) {
//                        //-------------- 已过期 -------------
//                        llLayout.setBackgroundResource(R.drawable.bg_expired_month_card_1017px_657px);
//                        tvSurplusDays.setVisibility(View.GONE);
//                        ivDeleteCard.setVisibility(View.VISIBLE);
//                    } else {
//                        //-------------- 未过期 -------------
//                        String surplusDays = String.format(getStringRes(R.string.surplus_days), String.valueOf(item.getSurplusDays()));
//                        Spannable span = new SpannableString(surplusDays);
//                        span.setSpan(new AbsoluteSizeSpan(DensityUtils.sp2px(App.mContext, 30)), 2, surplusDays.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                        tvSurplusDays.setText(span);
//                    }
//                    tvTermOfValidity.setText(String.format(getStringRes(R.string.term_of_validity), item.getEndTime()));
//                } else {
//                    //-------------- 立即缴费 -------------
//                    tvSurplusDays.setText("审核已通过，请缴费");
//                }
//            } else {
//                //-------------- 被拒绝 -------------
//                tvSurplusDays.setText("审核不通过");
//            }
//        } else {
//            //-------------- 业主卡-------------
//            llLayout.setBackgroundResource(R.drawable.bg_proprietor_card_1017px_657px);
//            tvSurplusDays.setVisibility(View.VISIBLE);
//            ivDeleteCard.setVisibility(View.GONE);
//            tvTermOfValidity.setVisibility(View.GONE);
//            ALog.e("业主卡 item.getApproveState()：" + item.getApproveState());
//            if (item.getApproveState() == 0) {
//                tvSurplusDays.setText("正在审核中");
//            } else if (item.getApproveState() == 1) {
//                tvSurplusDays.setVisibility(View.GONE);
//            } else {
//                tvSurplusDays.setText("审核不通过");
//            }
//
//        }
    }
}
