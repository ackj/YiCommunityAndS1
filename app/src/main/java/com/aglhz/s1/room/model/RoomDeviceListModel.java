package com.aglhz.s1.room.model;

import com.aglhz.abase.mvp.model.base.BaseModel;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.s1.common.ApiService;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.entity.bean.DeviceListBean;
import com.aglhz.s1.entity.bean.RoomsBean;
import com.aglhz.s1.room.contract.RoomDeviceListContract;

import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author: LiuJia on 2017/8/20 0020 18:12.
 * Email: liujia95me@126.com
 */

public class RoomDeviceListModel extends BaseModel implements RoomDeviceListContract.Model {

    @Override
    public Observable<DeviceListBean> requestDeviceList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestSubDeviceList(ApiService.requestSubDeviceList,
                        params.token,
                        params.page,
                        params.pageSize,
                        params.roomId,
                        params.category,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<RoomsBean> requestHouseList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestRoomList(ApiService.requestRoomList,
                        params.token,
                        params.page,
                        params.pageSize,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestDevicectrl(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestDevicectrl(ApiService.requestDevicectrl,
                        params.token,
                        params.index,
                        params.nodeId,
                        params.status,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestNewDeviceConfirm(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestNewDeviceConfirm(ApiService.requestNewDeviceConfirm,
                        params.token,
                        params.status,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestNewDevice24(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestNewDevice24(ApiService.requestNewDevice24,
                        params.token,
                        params.roomId,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestDelDevice(Params params) {
        return HttpHelper.getService(ApiService.class).requestDelDevice(ApiService.requestDelDevice
                , params.token, params.index,params.deviceSn)
                .subscribeOn(Schedulers.io());
    }
}
