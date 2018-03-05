package com.aglhz.yicommunity.main.parking.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;

import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.common.ApiService;
import com.aglhz.yicommunity.entity.bean.BaseBean;
import com.aglhz.yicommunity.main.parking.contract.ApplyContract;

import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 车位模块Model。
 */
public class ApplyModel extends BaseModel implements ApplyContract.Model {
    public static final String TAG = ApplyModel.class.getSimpleName();

    @Override
    public Observable<BaseBean> requestApplyCard(Params params) {
        if (params.type == 0) {
            return HttpHelper.getService(ApiService.class)
                    .requestApplyMonthCard(ApiService.requestApplyMonthCard,
                            params.token,
                            params.parkPlaceFid,
                            params.carNo,
                            params.name,
                            params.phoneNo)
                    .subscribeOn(Schedulers.io());
        } else if (params.type == 1) {
            return HttpHelper.getService(ApiService.class)
                    .requestCarportCard(ApiService.requestCarportCard,
                            params.token,
                            params.parkPlaceFid,
                            params.carNo,
                            params.annexCarNo,
                            params.name,
                            params.phoneNo)
                    .subscribeOn(Schedulers.io());
        }
        return null;
    }
}