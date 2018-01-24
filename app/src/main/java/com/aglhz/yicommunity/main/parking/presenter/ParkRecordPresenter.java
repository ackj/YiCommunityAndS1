package com.aglhz.yicommunity.main.parking.presenter;

import android.support.annotation.NonNull;

import com.aglhz.abase.mvp.presenter.base.BasePresenter;
import com.aglhz.yicommunity.common.Constants;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.main.parking.model.ParkRecordModel;
import com.aglhz.yicommunity.main.parking.contract.ParkRecordContract;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Author: LiuJia on 2017/5/23 0023 09:19.
 * Email: liujia95me@126.com
 */

public class ParkRecordPresenter extends BasePresenter<ParkRecordContract.View, ParkRecordContract.Model> implements ParkRecordContract.Presenter {
    public static final String TAG = ParkRecordPresenter.class.getSimpleName();

    public ParkRecordPresenter(ParkRecordContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected ParkRecordContract.Model createModel() {
        return new ParkRecordModel();
    }

    @Override
    public void requestParkReocrd(Params params) {
        mRxManager.add(mModel.requestParkRecord(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(parkRecordListBean -> {
                    if (parkRecordListBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        getView().responseParkRecord(parkRecordListBean.getData().getParkRecordList());
                    } else {
                        getView().error(parkRecordListBean.getOther().getMessage());
                    }
                }, this::error)
        );
    }
}
