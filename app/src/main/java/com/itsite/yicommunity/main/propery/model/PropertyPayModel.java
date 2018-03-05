package com.itsite.yicommunity.main.propery.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import com.itsite.yicommunity.common.ApiService;
import com.itsite.yicommunity.common.Params;
import com.itsite.yicommunity.entity.bean.PropertyPayBean;
import com.itsite.yicommunity.entity.bean.PropertyPayDetailBean;
import com.itsite.yicommunity.main.propery.contract.PropertyPayContract;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author: LiuJia on 2017/5/7 0007 21:41.
 * Email: liujia95me@126.com
 */

public class PropertyPayModel extends BaseModel implements PropertyPayContract.Model {

    @Override
    public Observable<PropertyPayBean> requestPropertyNotPay(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestPropertyNotPay(ApiService.requestPropertyNotPay,
                        params.token,
                        params.cmnt_c,
                        params.page)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<PropertyPayBean> requestPropertyPayed(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestPropertyPayed(ApiService.requestPropertyPayed,
                        params.token,
                        params.cmnt_c,
                        params.page)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<PropertyPayDetailBean> requestPropertyPayDetail(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestPropertyPayDetail(ApiService.requestPropertyPayDetail,
                        params.token,
                        params.fid)
                .subscribeOn(Schedulers.io());
    }

//    @Override
//    public Observable<ResponseBody> requestBill(Params params) {
//        return HttpHelper.getService(ApiService.class)
//                .requestPropertyOrder(ApiService.requestPropertyOrder,
//                        params.token,
//                        params.billFids,
//                        params.payMethod)
//                .subscribeOn(Schedulers.io());
//    }
}
