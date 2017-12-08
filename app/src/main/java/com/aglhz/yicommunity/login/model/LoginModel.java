package com.aglhz.yicommunity.login.model;

import com.aglhz.abase.log.ALog;
import com.aglhz.abase.mvp.model.base.BaseModel;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.yicommunity.App;
import com.aglhz.yicommunity.common.ApiService;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.common.UserHelper;
import com.aglhz.yicommunity.entity.bean.SipBean;
import com.aglhz.yicommunity.entity.bean.UserBean;
import com.aglhz.yicommunity.login.contract.LoginContract;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 */

public class LoginModel extends BaseModel implements LoginContract.Model {
    private static final String TAG = LoginModel.class.getSimpleName();

    @Override
    public Observable<UserBean> requestLogin(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestLogin(ApiService.requestLogin,
                        params.sc,
                        params.fc,
                        params.user,
                        params.pwd)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<SipBean> requestSip(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestSip(ApiService.requestSip, params.token)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void registerPush() {
        HttpHelper.getService(ApiService.class)
                .registerDevice(ApiService.registerDevice, UserHelper.token, App.deviceID, UserHelper.account, "userType")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseBean -> ALog.e(TAG, baseBean.getOther().getMessage()));
    }
}