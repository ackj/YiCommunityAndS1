package com.aglhz.s1.more.model;


import com.aglhz.abase.mvp.model.base.BaseModel;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.s1.common.ApiService;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.more.contract.MoreContract;

import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 负责邻里模块的Model层内容。
 */

public class MoreModel extends BaseModel implements MoreContract.Model {
    private final String TAG = MoreModel.class.getSimpleName();

    @Override
    public Observable<BaseBean> requestLogout(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestLogout(ApiService.requestLogout, params.token)
                .subscribeOn(Schedulers.io());
    }
}