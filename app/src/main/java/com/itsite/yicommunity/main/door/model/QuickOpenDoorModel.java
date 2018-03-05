package com.itsite.yicommunity.main.door.model;


import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import com.itsite.yicommunity.entity.bean.BaseBean;
import com.itsite.yicommunity.entity.bean.DoorListBean;
import com.itsite.yicommunity.common.ApiService;
import com.itsite.yicommunity.common.Params;
import com.itsite.yicommunity.main.door.contract.QuickOpenDoorContract;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 负责房屋模块的Model层内容。
 */

public class QuickOpenDoorModel extends BaseModel implements QuickOpenDoorContract.Model {

    private final String TAG = QuickOpenDoorModel.class.getSimpleName();

    @Override
    public void start(Object request) {

    }

    @Override
    public Observable<DoorListBean> requestDoors(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestDoors(ApiService.requestDoors,
                        params.token,
                        params.cmnt_c)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestSetQuickOpenDoor(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestSetQuickOpenDoor(ApiService.requestSetQuickOpenDoor,
                        params.token,
                        params.directory,
                        params.deviceName)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestResetOneKeyOpenDoor(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestResetOneKeyOpenDoor(ApiService.requestResetOneKeyOpenDoor,
                        params.token,
                        params.cmnt_c,
                        params.directories)
                .subscribeOn(Schedulers.io());
    }


}