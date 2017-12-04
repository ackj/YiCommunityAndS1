package com.aglhz.s1.linkage.model;

import com.aglhz.abase.log.ALog;
import com.aglhz.abase.mvp.model.base.BaseModel;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.s1.common.ApiService;
import com.aglhz.s1.common.Constants;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.entity.bean.LinkageBean;
import com.aglhz.s1.linkage.contract.LinkageListContract;

import rx.Observable;
import rx.schedulers.Schedulers;

public class LinkageListModel extends BaseModel implements LinkageListContract.Model {

    private static final String TAG = LinkageListModel.class.getSimpleName();

    @Override
    public Observable<LinkageBean> requestLinkageList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestLinkageList(ApiService.requestLinkageList,
                        params.token,
                        Constants.FC,
                        params.page,
                        params.pageSize,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestModLinkage(Params params) {
        ALog.e(TAG, "token:-->" + params.token);
        ALog.e(TAG, "name:-->" + params.name);
        ALog.e(TAG, "triggerType:-->" + params.triggerType);
        ALog.e(TAG, "cdt_sensorId:-->" + params.cdt_sensorId);
        ALog.e(TAG, "cdt_sensorAct:-->" + params.cdt_sensorAct);
        ALog.e(TAG, "cdt_day:-->" + params.cdt_day);
        ALog.e(TAG, "cdt_time:-->" + params.cdt_time);
        ALog.e(TAG, "targetType:-->" + params.targetType);
        ALog.e(TAG, "targetId:-->" + params.targetId);
        ALog.e(TAG, "nodeId:-->" + params.nodeId);
        ALog.e(TAG, "act1:-->" + params.act1);
        ALog.e(TAG, "delay:-->" + params.delay);
        ALog.e(TAG, "act2:-->" + params.act2);
        ALog.e(TAG, "status:-->" + params.status);
        return HttpHelper.getService(ApiService.class)
                .requestModLinkage(ApiService.requestModLinkage,
                        params.token,
                        Constants.FC,
                        params.index,
                        params.name,
                        params.triggerType,
                        params.cdt_sensorId,
                        params.cdt_sensorAct,
                        params.cdt_day,
                        params.cdt_time,
                        params.targetType,
                        params.targetId,
                        params.nodeId,
                        params.act1,
                        params.delay,
                        params.act2,
                        params.status,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestDeleteLinkage(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestDellinkage(ApiService.requestDellinkage, params.token,Constants.FC, params.index,params.deviceSn)
                .subscribeOn(Schedulers.io());
    }
}