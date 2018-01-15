package com.aglhz.s1.host.model;


import com.aglhz.abase.mvp.model.base.BaseModel;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.s1.common.ApiService;
import com.aglhz.s1.common.Constants;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.host.contract.AddHostContract;

import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 负责添加主机模块的Model层内容。
 */

public class AddHostModel extends BaseModel implements AddHostContract.Model {

    private final String TAG = AddHostModel.class.getSimpleName();

    @Override
    public Observable<BaseBean> requestAddHost(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestNewMainDevice(ApiService.requestNewMainDevice,
                        params.token,
                        Constants.SMART_GATEWAY,
                        params.deviceSn,
                        params.name,
                        params.roomDir,
                        "")
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestModGateway(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestModGateway(ApiService.requestAddHost,
                        params.token,
                        Constants.FC,
                        params.deviceSn,
                        params.name)
                .subscribeOn(Schedulers.io());
    }

}