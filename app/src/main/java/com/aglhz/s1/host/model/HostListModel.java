package com.aglhz.s1.host.model;

import com.aglhz.abase.mvp.model.base.BaseModel;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.s1.common.ApiService;
import com.aglhz.s1.common.Constants;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.GatewaysBean;
import com.aglhz.s1.host.contract.HostListContract;

import rx.Observable;
import rx.schedulers.Schedulers;


public class HostListModel extends BaseModel implements HostListContract.Model {
    @Override
    public void start(Object request) {

    }

    public Observable<GatewaysBean> requestGateways(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestGateways(ApiService.requestGateways,
                        params.token,
                        Constants.FC,
                        params.page,
                        params.pageSize,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

}