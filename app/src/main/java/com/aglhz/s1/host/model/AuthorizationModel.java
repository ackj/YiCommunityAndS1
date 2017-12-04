package com.aglhz.s1.host.model;

import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.s1.common.ApiService;
import com.aglhz.s1.common.Constants;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.AuthorizationBean;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.host.contract.AuthorizationContract;

import com.aglhz.abase.mvp.model.base.BaseModel;

import rx.Observable;
import rx.schedulers.Schedulers;

public class AuthorizationModel extends BaseModel implements AuthorizationContract.Model {

    @Override
    public Observable<AuthorizationBean> requestgatewayAuthList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestGatewayAuthList(ApiService.requestGatewayAuthList,
                        params.token,
                        Constants.FC,
                        params.gateway,
                        params.page,
                        params.pageSize)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestGatewayAuth(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestGatewayAuth(ApiService.requestGatewayAuth,
                        params.token,
                        Constants.FC,
                        params.gateway,
                        params.mobile)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestGatewayUnAuth(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestGatewayUnAuth(ApiService.requestGatewayUnAuth,
                        params.token,
                        Constants.FC,
                        params.gateway,
                        params.fid)
                .subscribeOn(Schedulers.io());
    }
}