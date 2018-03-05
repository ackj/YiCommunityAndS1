package com.aglhz.yicommunity.main.parking.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;

import com.aglhz.yicommunity.common.ApiService;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.entity.bean.MonthCardBillListBean;
import com.aglhz.yicommunity.main.parking.contract.RechargeRecordContract;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author: LiuJia on 2017/5/27 0027 15:38.
 * Email: liujia95me@126.com
 */
public class RechargeRecordModel extends BaseModel implements RechargeRecordContract.Model {
    public static final String TAG = RechargeRecordModel.class.getSimpleName();

    @Override
    public Observable<MonthCardBillListBean> requsetMonthCardBillList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestRechargeRecord(ApiService.requestRechargeRecord,
                        params.token,
                        params.page,
                        params.pageSize)
                .subscribeOn(Schedulers.io());
    }
}
