package com.aglhz.s1.main.smarthome.model;

import com.aglhz.abase.mvp.model.base.BaseModel;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.s1.common.ApiService;
import com.aglhz.s1.common.Constants;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.main.smarthome.contract.SmartHomeContract;
import com.aglhz.yicommunity.entity.bean.MainDeviceListBean;

import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author: LiuJia on 2017/9/26 0026 10:17.
 * Email: liujia95me@126.com
 */

public class SmartHomeModel extends BaseModel implements SmartHomeContract.Model {

    @Override
    public Observable<MainDeviceListBean> requestEquipmentInfoList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestMainDeviceList(ApiService.requestMainDeviceList,
                        params.token,Constants.SMART_GATEWAY,"",params.roomDir,params.page,params.pageSize)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestDelGateway(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestDelGateway(ApiService.requestDelGateway,
                        params.token,Constants.FC,params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<MainDeviceListBean> requestCameraList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestMainDeviceList(ApiService.requestMainDeviceList,
                        params.token, Constants.SMART_CAMERA,"",params.roomDir,params.page,params.pageSize)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestNewCamera(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestNewcamera(ApiService.requestNewcamera,params.token,Constants.FC,params.deviceId,params.deviceName,params.devicePassword)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestDelCamera(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestDelcamera(ApiService.requestDelcamera,params.token,Constants.FC,params.fid)
                .subscribeOn(Schedulers.io());
    }

}
