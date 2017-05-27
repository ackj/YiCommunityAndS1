package com.aglhz.yicommunity.message.model;

import com.aglhz.abase.mvp.model.base.BaseModel;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.yicommunity.bean.BaseBean;
import com.aglhz.yicommunity.common.ApiService;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.message.contract.ApplyCheckContract;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: LiuJia on 2017/5/26 0026 17:04.
 * Email: liujia95me@126.com
 */

public class ApplyCheckModel extends BaseModel implements ApplyCheckContract.Model {

    @Override
    public void start(Object request) {

    }

    @Override
    public Observable<BaseBean> requestApplyCheck(Params params) {
        return HttpHelper.getService(ApiService.class).requestAuthApprove(ApiService.requestAuthApprove,
                params.token, params.fid, params.authFid, params.status)
                .subscribeOn(Schedulers.io());
    }
}
