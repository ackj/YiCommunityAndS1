package com.itsite.yicommunity.main.services.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import com.itsite.yicommunity.common.ApiService;
import com.itsite.yicommunity.common.Params;
import com.itsite.yicommunity.entity.bean.ServiceDetailBean;
import com.itsite.yicommunity.main.services.contract.ServicesDetailContract;

import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author: LiuJia on 2017/6/30 0030 17:33.
 * Email: liujia95me@126.com
 */

public class ServicesDetailModel extends BaseModel implements ServicesDetailContract.Model {

    @Override
    public void start(Object request) {

    }

    @Override
    public Observable<ServiceDetailBean> requestServiceDetail(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestServiceDetail(ApiService.requestServiceDetail, params.fid)
                .subscribeOn(Schedulers.io());
    }
}
