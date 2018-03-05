package com.aglhz.s1.linkage.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;

import com.aglhz.s1.common.Constants;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.SceneBean;
import com.aglhz.s1.linkage.contract.AddLinkageContract;
import com.aglhz.s1.common.ApiService;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.entity.bean.DeviceListBean;

import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author: LiuJia on 2017/8/28 0028 10:34.
 * Email: liujia95me@126.com
 */

public class AddLinkageModel extends BaseModel implements AddLinkageContract.Model {

    private static final String TAG = AddLinkageModel.class.getSimpleName();

    @Override
    public Observable<BaseBean> requestNewLinkage(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestNewlinkage(ApiService.requestNewlinkage,
                        params.token,
                        Constants.FC,
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
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<SceneBean> requestSceneList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestSceneList(ApiService.requestSceneList,
                        params.pageSize,
                        params.page,
                        params.token,
                        Constants.FC,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<DeviceListBean> requestDeviceList(Params params) {
        return HttpHelper.getService(ApiService.class).requestSubDeviceList(ApiService.requestSubDeviceList
                , params.token, Constants.FC,params.page,params.pageSize,params.category,params.deviceSn)
                .subscribeOn(Schedulers.io());
    }
}
