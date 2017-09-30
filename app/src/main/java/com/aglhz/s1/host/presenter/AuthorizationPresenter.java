package com.aglhz.s1.host.presenter;

import android.support.annotation.NonNull;

import com.aglhz.abase.mvp.presenter.base.BasePresenter;
import com.aglhz.s1.common.Constants;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.host.contract.AuthorizationContract;
import com.aglhz.s1.host.model.AuthorizationModel;

import rx.android.schedulers.AndroidSchedulers;


public class AuthorizationPresenter extends BasePresenter<AuthorizationContract.View, AuthorizationContract.Model> implements AuthorizationContract.Presenter {
    private final String TAG = AuthorizationPresenter.class.getSimpleName();

    public AuthorizationPresenter(AuthorizationContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected AuthorizationContract.Model createModel() {
        return new AuthorizationModel();
    }

    @Override
    public void requestgatewayAuthList(Params params) {
        mRxManager.add(mModel.requestgatewayAuthList(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responsegatewayAuthList(bean.getData());
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestGatewayAuth(Params params) {
        mRxManager.add(mModel.requestGatewayAuth(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseGatewayAuthSuccesst(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestGatewayUnAuth(Params params) {
        mRxManager.add(mModel.requestGatewayUnAuth(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseGatewayUnAuthSuccesst(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }


}