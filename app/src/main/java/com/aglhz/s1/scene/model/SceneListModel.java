package com.aglhz.s1.scene.model;

import com.aglhz.abase.mvp.model.base.BaseModel;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.s1.common.ApiService;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.entity.bean.SceneBean;
import com.aglhz.s1.scene.contract.SceneListContract;

import rx.Observable;
import rx.schedulers.Schedulers;


public class SceneListModel extends BaseModel implements SceneListContract.Model {

    @Override
    public Observable<SceneBean> requestSceneList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestSceneList(ApiService.requestSceneList,
                        params.pageSize,
                        params.page,
                        params.token,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestStartScene(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestStartScene(ApiService.requestStartScene,
                        params.token,
                        params.index,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestDeleteScene(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestDeleteScene(ApiService.requestDeleteScene,
                        params.token,
                        params.index,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }
}