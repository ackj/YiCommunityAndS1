package com.aglhz.s1.camera.model;

import com.aglhz.abase.mvp.model.base.BaseModel;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.s1.camera.contract.CameraSettingContract;
import com.aglhz.s1.common.ApiService;
import com.aglhz.s1.common.Constants;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;

import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author: LiuJia on 2017/9/19 0019 08:51.
 * Email: liujia95me@126.com
 */

public class CameraSettingModel extends BaseModel implements CameraSettingContract.Model{
    @Override
    public Observable<BaseBean> requestModCamera(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestModCamera(ApiService.requestModCamera,
                        params.token, Constants.FC,params.fid,params.deviceType,params.deviceName,params.devicePassword)
                .subscribeOn(Schedulers.io());
    }
}
