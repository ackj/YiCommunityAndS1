package com.aglhz.yicommunity.main.parking.presenter;

import android.support.annotation.NonNull;

import com.aglhz.abase.mvp.presenter.base.BasePresenter;
import com.aglhz.yicommunity.common.Constants;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.entity.bean.BaseBean;
import com.aglhz.yicommunity.main.parking.contract.ApplyContract;
import com.aglhz.yicommunity.main.parking.model.ApplyModel;

import rx.android.schedulers.AndroidSchedulers;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 车位模块Presenter。
 */
public class ApplyPresenter extends BasePresenter<ApplyContract.View, ApplyContract.Model> implements ApplyContract.Presenter {
    public final String TAG = ApplyPresenter.class.getSimpleName();

    public ApplyPresenter(ApplyContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected ApplyContract.Model createModel() {
        return new ApplyModel();
    }

    @Override
    public void requestApplyCard(Params params) {
        mRxManager.add(mModel.requestApplyCard(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<BaseBean>() {
                    @Override
                    public void _onNext(BaseBean baseBean) {
                        if (baseBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                            getView().responseApplyCard(baseBean);
                        } else {
                            getView().error(baseBean.getOther().getMessage());
                        }
                    }
                }));
    }
}