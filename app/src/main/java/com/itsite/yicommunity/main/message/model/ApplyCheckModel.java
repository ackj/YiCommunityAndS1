package com.itsite.yicommunity.main.message.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import com.itsite.yicommunity.common.ApiService;
import com.itsite.yicommunity.common.Params;
import com.itsite.yicommunity.entity.bean.BaseBean;
import com.itsite.yicommunity.main.message.contract.ApplyCheckContract;

import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author: LiuJia on 2017/5/26 0026 17:04.
 * Email: liujia95me@126.com
 */

public class ApplyCheckModel extends BaseModel implements ApplyCheckContract.Model {

    private static final String TAG = ApplyCheckModel.class.getSimpleName();

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
