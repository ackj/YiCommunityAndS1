package com.aglhz.s1.security.model;

import com.aglhz.abase.mvp.model.base.BaseModel;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.s1.common.ApiService;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.entity.bean.GatewaysBean;
import com.aglhz.s1.entity.bean.SecurityBean;
import com.aglhz.s1.security.contract.SecurityContract;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author: LiuJia on 2017/7/4 0004 09:19.
 * Email: liujia95me@126.com
 */

public class SecurityModel extends BaseModel implements SecurityContract.Model {
    public static final String TAG = SecurityModel.class.getSimpleName();

    @Override
    public void start(Object request) {

    }

    @Override
    public Observable<SecurityBean> requestSecurity(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestSecurity(ApiService.requestSecurity,
                        params.token,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<GatewaysBean> requestGateways(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestGateways(ApiService.requestGateways,
                        params.token,
                        params.page,
                        params.pageSize,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestSwichGateway(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestSwichGateway(ApiService.requestSwichGateway,
                        params.token,
                        params.gateway)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestSwichState(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestSwichState(ApiService.requestSwichState,
                        params.token,
                        params.dstatus,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestLeaveMassge(Params params) {
        // 创建 RequestBody，用于封装构建RequestBody
        MultipartBody.Builder builder = new MultipartBody.Builder();
        RequestBody requestBody = RequestBody.create(MediaType.parse("audio/amr"), params.file);
        builder.addFormDataPart("file", params.file.getName(), requestBody);
        builder.addFormDataPart("gateway", params.deviceSn);

        return HttpHelper.getService(ApiService.class)
                .requestLeaveMassge(ApiService.requestLeaveMassge,
                        params.token,
                        builder.build())
                .subscribeOn(Schedulers.io());
    }
}
