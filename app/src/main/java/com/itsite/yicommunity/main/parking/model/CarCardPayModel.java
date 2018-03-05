package com.itsite.yicommunity.main.parking.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import com.itsite.yicommunity.common.ApiService;
import com.itsite.yicommunity.common.Params;
import com.itsite.yicommunity.entity.bean.BaseBean;
import com.itsite.yicommunity.entity.bean.MonthlyPayRulesBean;
import com.itsite.yicommunity.main.parking.contract.CarCardPayContract;

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