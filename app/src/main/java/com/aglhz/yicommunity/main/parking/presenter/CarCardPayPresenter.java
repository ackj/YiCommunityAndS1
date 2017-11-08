package com.aglhz.yicommunity.main.parking.presenter;

import android.support.annotation.NonNull;

import com.aglhz.abase.mvp.presenter.base.BasePresenter;
import com.aglhz.yicommunity.common.Constants;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.entity.bean.BaseBean;
import com.aglhz.yicommunity.entity.bean.MonthlyPayRulesBean;
import com.aglhz.yicommunity.main.parking.contract.CarCardPayContract;
import com.aglhz.yicommunity.main.parking.model.CarCardPayModel;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Author：leguang on 2016/10/9 0009 10:35
 * Email：langmanleguang@qq.com
 * <p>
 * 负责邻里模块Presenter层内容。
 */

public class CarCardPayPresenter extends BasePresenter<CarCardPayContract.View, CarCardPayContract.Model> implements CarCardPayContract.Presenter {
    private final String TAG = CarCardPayPresenter.class.getSimpleName();

    public CarCardPayPresenter(CarCardPayContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected CarCardPayContract.Model createModel() {
        return new CarCardPayModel();
    }

    @Override
    public void requestDeleteCarCard(Params params) {
        mRxManager.add(mModel.requestDeleteCarCard(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<BaseBean>() {
                    @Override
                    public void _onNext(BaseBean baseBean) {
                        if (baseBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                            getView().responseDeleteCarCard(baseBean);
                        } else {
                            getView().error(baseBean.getOther().getMessage());
                        }
                    }
                }));
    }

    @Override
    public void requestMonthlyPayRules(Params params) {
        mRxManager.add(mModel.requestMonthlyPayRules(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<MonthlyPayRulesBean>() {
                    @Override
                    public void _onNext(MonthlyPayRulesBean bean) {
                        if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                            getView().responseMonthlyPayRules(bean);
                        } else {
                            getView().error(bean.getOther().getMessage());
                        }
                    }
                }));
    }
}