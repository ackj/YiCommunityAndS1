package com.itsite.s1.scene.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import com.itsite.s1.common.ApiService;
import com.itsite.s1.common.Constants;
import com.itsite.s1.common.Params;
import com.itsite.s1.entity.bean.BaseBean;
import com.itsite.s1.scene.contract.AddSceneContract;

import rx.Observable;
import rx.schedulers.Schedulers;


public class AddSceneModel extends BaseModel implements AddSceneContract.Model {


    @Override
    public Observable<BaseBean> requestAddScene(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestAddScene(ApiService.requestAddScene,
                        params.token,
                        Constants.FC,
                        params.name,
                        params.paramJson,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }
}