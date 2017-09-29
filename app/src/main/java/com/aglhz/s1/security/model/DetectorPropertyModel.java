package com.aglhz.s1.security.model;

import com.aglhz.abase.mvp.model.base.BaseModel;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.s1.common.ApiService;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.entity.bean.SubDeviceDetBean;
import com.aglhz.s1.security.contract.DetectorPropertyContract;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author: LiuJia on 2017/7/4 0004 15:04.
 * Email: liujia95me@126.com
 */

public class DetectorPropertyModel extends BaseModel implements DetectorPropertyContract.Model {

    @Override
    public Observable<BaseBean> requestNotifProperty(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestModsensor(ApiService.requestModsensor,
                        params.token,
                        params.file,
                        params.index,
                        params.name,
                        params.defenseLevel,
                        params.alarmDelay,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestDelsensor(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestDelsensor(ApiService.requestDelsensor,
                        params.token,
                        params.index,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<SubDeviceDetBean> requestSubDeviceDet(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestSubDeviceDet(ApiService.requestSubDeviceDet,
                        params.token,
                        params.category,
                        params.index,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestModsensor(Params params) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if(params.file!=null){
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), params.file);
            builder.addFormDataPart("file", params.file.getName(), requestBody);
        }
        builder.addFormDataPart("index", params.index+"");
        builder.addFormDataPart("name", params.name);
        builder.addFormDataPart("defenseLevel", params.defenseLevel);
        builder.addFormDataPart("alarmDelay", params.alarmDelay+"");
        builder.addFormDataPart("gateway", params.deviceSn);

        return HttpHelper.getService(ApiService.class)
                .requestModsensor(ApiService.requestModsensor,
                        params.token,
                        builder.build())
                .subscribeOn(Schedulers.io());
    }


}
