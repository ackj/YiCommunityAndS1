package com.aglhz.s1.more.model;

import com.aglhz.abase.mvp.model.base.BaseModel;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.s1.common.ApiService;
import com.aglhz.s1.common.Constants;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.entity.bean.RoomTypesBean;
import com.aglhz.s1.entity.bean.RoomsBean;
import com.aglhz.s1.more.contract.RoomManagerContract;

import rx.Observable;
import rx.schedulers.Schedulers;


public class RoomManagerModel extends BaseModel implements RoomManagerContract.Model {
    @Override
    public void start(Object request) {

    }

    @Override
    public Observable<RoomsBean> requestHouseList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestRoomList(ApiService.requestRoomList,
                        params.token,
                        Constants.FC,
                        params.page,
                        params.pageSize,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestAddHouse(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestNewroom(ApiService.requestNewroom,
                        params.token,
                        Constants.FC,
                        params.roomName,
                        params.roomTypeFid,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestDelroom(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestDelroom(ApiService.requestDelroom,
                        params.token,
                       Constants.FC,
                        params.fid)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<RoomTypesBean> requestRoomTypeList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestRoomTypeList(ApiService.requestRoomTypeList,Constants.FC)
                .subscribeOn(Schedulers.io());
    }

}