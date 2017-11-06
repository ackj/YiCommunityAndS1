package com.aglhz.yicommunity.main.park.model;

import com.aglhz.abase.mvp.model.base.BaseModel;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.yicommunity.common.ApiService;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.entity.bean.BaseBean;
import com.aglhz.yicommunity.entity.bean.CarCardBean;
import com.aglhz.yicommunity.entity.bean.CardRechargeBean;
import com.aglhz.yicommunity.entity.bean.MonthCardRuleListBean;
import com.aglhz.yicommunity.main.park.contract.PublishMonthCardContract;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author: LiuJia on 2017/5/23 0023 16:12.
 * Email: liujia95me@126.com
 */

public class PublishMonthCardModel extends BaseModel implements PublishMonthCardContract.Model {

    @Override
    public Observable<BaseBean> requestSubmitMonthCard(Params params) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("token", params.token);
        builder.addFormDataPart("parkPlaceFid", params.fid);
        builder.addFormDataPart("carNo", params.carNo);
        builder.addFormDataPart("customerName", params.name);
        builder.addFormDataPart("phoneNo", params.phoneNo);
//        builder.addFormDataPart("monthName", params.monthName);
//        builder.addFormDataPart("monthCount", params.monthCount + "");
//        builder.addFormDataPart("money", params.price);

//        return HttpHelper.getService(ApiService.class).requestApplyMonthCard(ApiService.requestApplyMonthCard,
//                builder.build())
//                .subscribeOn(Schedulers.io());
        return null;
    }

    public Observable<MonthCardRuleListBean> requestMonthCardRule(Params params) {
        return HttpHelper.getService(ApiService.class).requestMonthCardRuleList(ApiService.requestMonthCardRuleList,
                params.token, params.fid)
                .subscribeOn(Schedulers.io());
    }

    public Observable<CarCardBean> requestCardPay(Params params) {
        return HttpHelper.getService(ApiService.class).requestCardPay(ApiService.requestCardPay,
                params.token, params.fid)
                .subscribeOn(Schedulers.io());
    }

    public Observable<CardRechargeBean> requestCardRecharge(Params params) {
        return HttpHelper.getService(ApiService.class).requestCardRecharge(ApiService.requestCardRecharge,
                params.token, params.fid)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<ResponseBody> requestCarCardOrder(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestCarCardOrder(ApiService.requestCarCardOrder,
                        params.token,
                        params.parkCardFid,
                        params.monthName,
                        params.monthCount,
                        params.payType)
                .subscribeOn(Schedulers.io());
    }
}
