package com.aglhz.s1.security.model;

import com.aglhz.abase.mvp.model.base.BaseModel;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.s1.common.ApiService;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.entity.bean.DevicesBean;
import com.aglhz.s1.security.contract.AddDetectorContract;

import okhttp3.MultipartBody;
import rx.Observable;
import rx.schedulers.Schedulers;

public class AddDetectorModel extends BaseModel implements AddDetectorContract.Model {
    private static final String TAG = AddDetectorModel.class.getSimpleName();

    @Override
    public Observable<DevicesBean> requestDetectorList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestSensorTypeList(ApiService.requestSensorTypeList,
                        params.token,
                        params.page,
                        params.pageSize)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestAddDetector(Params params) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("token", params.token);
        builder.addFormDataPart("sensorType", params.sensorType);
        builder.addFormDataPart("name", params.name);
        builder.addFormDataPart("gateway", params.deviceSn);
        return HttpHelper.getService(ApiService.class)
                .reqeuestNewsensor(ApiService.reqeuestNewsensor,
                        builder.build())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> reqeuestCancellationOfSensorLearning(Params params) {
        return HttpHelper.getService(ApiService.class)
                .reqeuestCancellationOfSensorLearning(ApiService.reqeuestCancellationOfSensorLearning,
                        params.token,params.deviceSn)
                .subscribeOn(Schedulers.io());
    }
}