package com.aglhz.s1.main.smarthome.presenter;

import android.support.annotation.NonNull;

import com.aglhz.abase.mvp.presenter.base.BasePresenter;
import com.aglhz.s1.common.Constants;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.main.smarthome.contract.SmartHomeContract;
import com.aglhz.s1.main.smarthome.model.SmartHomeModel;

import rx.android.schedulers.AndroidSchedulers;


/**
 * Author: LiuJia on 2017/9/26 0026 10:20.
 * Email: liujia95me@126.com
 */

public class SmartHomePresenter extends BasePresenter<SmartHomeContract.View,SmartHomeContract.Model> implements SmartHomeContract.Presenter {

    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public SmartHomePresenter(SmartHomeContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected SmartHomeContract.Model createModel() {
        return new SmartHomeModel();
    }

    @Override
    public void requestEquipmentInfoList(Params params) {
        mRxManager.add(mModel.requestEquipmentInfoList(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseEquipmentInfoList(bean.getData().getDataList());
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestDelGateway(Params params) {
        mRxManager.add(mModel.requestDelGateway(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseDelGatewaySuccess(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }

}
