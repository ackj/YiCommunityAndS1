package com.itsite.yicommunity.main.door.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import com.itsite.yicommunity.common.ApiService;
import com.itsite.yicommunity.common.Params;
import com.itsite.yicommunity.entity.bean.BaseBean;
import com.itsite.yicommunity.main.door.contract.FamilyPhoneContract;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author: LiuJia on 2017/9/15 0015 11:11.
 * Email: liujia95me@126.com
 */

public class FamilyPhoneModel extends BaseModel implements FamilyPhoneContract.Model {
    public static final String TAG = FamilyPhoneModel.class.getSimpleName();

    @Override
    public Observable<BaseBean> requestSetFamilyPhone(Params params) {
        return HttpHelper.getService(ApiService.class).requestSetFamilyPhone(ApiService.requestSetFamilyPhone,
                params.token, params.phoneNo, params.roomDir)
                .subscribeOn(Schedulers.io());
    }
}
