package com.aglhz.yicommunity.main.parking.presenter;

import android.support.annotation.NonNull;

import com.aglhz.abase.mvp.presenter.base.BasePresenter;
import com.aglhz.yicommunity.common.Constants;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.entity.bean.ParkingChargeBean;
import com.aglhz.yicommunity.main.parking.contract.TempParkContract;
import com.aglhz.yicommunity.main.parking.model.TempParkModel;

import rx.android.schedulers.AndroidSchedulers;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 临时停车场模块Presenter。
 */
public class TempParkPresenter extends BasePresenter<TempParkContract.View, TempParkContract.Model> implements TempParkContract.Presenter {
    private final String TAG = TempParkPresenter.class.getSimpleName();

    public TempParkPresenter(TempParkContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected TempParkContract.Model createModel() {
        return new TempParkModel();
    }


    @Override
    public void requestParkingCharge(Params params) {
        mRxManager.add(mModel.requestParkingCharge(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<ParkingChargeBean>() {
                    @Override
                    public void _onNext(ParkingChargeBean bean) {
                        if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                            getView().responseParkingCharge(bean);
                        } else {
                            getView().error(bean.getOther().getMessage());
                        }
                    }
                }));
    }

    @Override
    public void requestParkBill(Params params) {

    }
}