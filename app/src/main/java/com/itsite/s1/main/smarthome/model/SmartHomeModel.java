package com.itsite.s1.main.smarthome.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import com.itsite.s1.common.ApiService;
import com.itsite.s1.common.Constants;
import com.itsite.s1.common.Params;
import com.itsite.s1.entity.bean.BaseBean;
import com.itsite.s1.main.smarthome.contract.SmartHomeContract;
import com.itsite.yicommunity.entity.bean.MainDeviceListBean;

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
                        params.token,Constants.SMART_GATEWAY.concat(",").concat(Constants.SMART_GATEWAY_GSW3),"",params.roomDir,params.page,params.pageSize)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestDelGateway(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestDelMainDevice(ApiService.requestDelMainDevice,
                        params.token,
                        Constants.SMART_GATEWAY,
                        params.deviceSn)
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
                .requestNewCamera(ApiService.requestNewMainDevice,params.token, Constants.SMART_CAMERA,params.deviceId,params.deviceName,params.devicePassword,params.roomDir)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestDelCamera(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestDelMainDevice(ApiService.requestDelMainDevice,params.token,Constants.SMART_CAMERA,params.deviceId)
                .subscribeOn(Schedulers.io());
    }

}
