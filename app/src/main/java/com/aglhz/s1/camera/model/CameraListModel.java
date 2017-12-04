package com.aglhz.s1.camera.model;

import com.aglhz.abase.mvp.model.base.BaseModel;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.s1.camera.contract.CameraListContract;
import com.aglhz.s1.common.ApiService;
import com.aglhz.s1.common.Constants;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.entity.bean.CameraBean;

import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author: LiuJia on 2017/9/15 0015 18:18.
 * Email: liujia95me@126.com
 */

public class CameraListModel extends BaseModel implements CameraListContract.Model {

    @Override
    public Observable<CameraBean> requestCameraList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestCameraList(ApiService.requestCameraList,params.token, Constants.FC,params.page,params.pageSize)
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

    @Override
    public Observable<BaseBean> requestModCamera(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestModCamera(ApiService.requestModCamera,
                        params.token,Constants.FC,params.fid,params.deviceType,params.deviceName,params.devicePassword)
                .subscribeOn(Schedulers.io());
    }
}
