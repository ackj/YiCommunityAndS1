package com.aglhz.yicommunity.main.parking.model;

import com.aglhz.abase.mvp.model.base.BaseModel;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.yicommunity.common.ApiService;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.entity.bean.BaseBean;
import com.aglhz.yicommunity.entity.bean.MonthlyPayRulesBean;
import com.aglhz.yicommunity.main.parking.contract.CarCardPayContract;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 负责邻里模块的Model层内容。
 */

public class CarCardPayModel extends BaseModel implements CarCardPayContract.Model {
    public static final String TAG = CarCardPayModel.class.getSimpleName();

    @Override
    public Observable<BaseBean> requestDeleteCarCard(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestDeleteCarCard(ApiService.requestDeleteCarCard,
                        params.token,
                        params.parkCardFid)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<MonthlyPayRulesBean> requestMonthlyPayRules(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestMonthlyPayRules(ApiService.requestMonthlyPayRules,
                        params.token,
                        params.parkCardFid)
                .subscribeOn(Schedulers.io());
    }

//    @Override
//    public Observable<ResponseBody> requestCarCardBill(Params params) {
//        return HttpHelper.getService(ApiService.class)
//                .requestCarCardBill(ApiService.requestCarCardBill,
//                        params.token,
//                        params.parkCardFid,
//                        params.monthName,
//                        params.monthCount,
//                        params.payMethod)
//                .subscribeOn(Schedulers.io());
//    }
}