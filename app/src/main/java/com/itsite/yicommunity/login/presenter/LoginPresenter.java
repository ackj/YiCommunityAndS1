package com.itsite.yicommunity.login.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import com.itsite.yicommunity.common.Constants;
import com.itsite.yicommunity.common.DoorManager;
import com.itsite.yicommunity.common.Params;
import com.itsite.yicommunity.common.UserHelper;
import com.itsite.yicommunity.event.EventCommunity;
import com.itsite.yicommunity.login.contract.LoginContract;
import com.itsite.yicommunity.login.model.LoginModel;
import com.sipphone.sdk.access.WebReponse;

import org.greenrobot.eventbus.EventBus;

import rx.android.schedulers.AndroidSchedulers;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 */

public class LoginPresenter extends BasePresenter<LoginContract.View, LoginContract.Model> implements LoginContract.Presenter {
    private static final String TAG = LoginPresenter.class.getSimpleName();

    public LoginPresenter(LoginContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected LoginContract.Model createModel() {
        return new LoginModel();
    }

    @Override
    public void start(Object request) {
        Params params = (Params) request;
        mRxManager.add(mModel.requestLogin((Params) request)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(userBean -> {
                            if (userBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {

                                //保存用户信息
                                UserHelper.setAccount(params.user, params.pwd);//setAccount要先于setUserInfo调用，不然无法切换SP文件。
                                UserHelper.setUserInfo(userBean.getData().getMemberInfo());

                                Params.token = UserHelper.token;//必须赋值一次。
                                com.itsite.s1.common.Params.token = UserHelper.token;
                                //注册友盟
//                        mModel.registerPush(params.user);
                                //注册阿里云推送。
                                mModel.registerPush();
                                //注册Sip到全视通服务器
                                requestSip(Params.getInstance());
                                //登录成功后，通知相关页面刷新。
                                EventBus.getDefault().post(new EventCommunity(null));
                            } else {
                                getView().error(userBean.getOther().getMessage());
                            }
                        }, this::error)
        );
    }

    private void requestSip(Params params) {
        mModel.requestSip(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sipBean -> {
                    if (sipBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        UserHelper.setSip(sipBean.getData().getAccount());
                        ALog.e("UserHelper.sip-->" + UserHelper.sip);
                        DoorManager.getInstance().initWebUserApi(UserHelper.sip, new DoorManager.AccessCallBack() {

                            @Override
                            public void onPreAccessToken() {
                                getView().start(null);
                            }

                            @Override
                            public void onPostAccessToken(WebReponse webReponse) {
                                DoorManager.getInstance().startService();
                                getView().start(null);
                            }
                        });
                    } else {
                        getView().start(null);
                    }
                }, throwable -> getView().start(null));
    }
}