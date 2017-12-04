package com.aglhz.s1.room.model;

import com.aglhz.abase.mvp.model.base.BaseModel;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.s1.common.ApiService;
import com.aglhz.s1.common.Constants;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.room.contract.DeviceOnOffContract;

import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author: LiuJia on 2017/8/30 0030 10:10.
 * Email: liujia95me@126.com
 */

public class DeviceOnOffModel extends BaseModel implements DeviceOnOffContract.Model {

    @Override
    public Observable<BaseBean> requestDeviceCtrl(Params params) {
        return HttpHelper.getService(ApiService.class).requestDevicectrl(ApiService.requestDevicectrl
                , params.token, Constants.FC,params.index,params.nodeId,params.status,params.deviceSn)
                .subscribeOn(Schedulers.io());
    }
}
