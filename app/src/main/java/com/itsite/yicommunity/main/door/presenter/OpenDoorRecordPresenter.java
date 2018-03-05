package com.itsite.yicommunity.main.door.presenter;


import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import com.itsite.yicommunity.common.Params;
import com.itsite.yicommunity.main.door.contract.OpenDoorRecordContract;
import com.itsite.yicommunity.main.door.model.OpenDoorRecordModel;

import rx.android.schedulers.AndroidSchedulers;


/**
 * Author：leguang on 2016/10/9 0009 10:35
 * Email：langmanleguang@qq.com
 * <p>
 * 负责房屋模块Presenter层内容。
 */

public class OpenDoorRecordPresenter extends BasePresenter<OpenDoorRecordContract.View, OpenDoorRecordContract.Model> implements OpenDoorRecordContract.Presenter {
    private final String TAG = OpenDoorRecordPresenter.class.getSimpleName();

    public OpenDoorRecordPresenter(OpenDoorRecordContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected OpenDoorRecordContract.Model createModel() {
        return new OpenDoorRecordModel();
    }

    @Override
    public void requestRecord(Params params) {
        mRxManager.add(mModel.getOpenDoorRecord(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(openDoorRecordBean -> {
                    if (openDoorRecordBean.getOther().getCode() == 200) {
                        getView().responseRecord(openDoorRecordBean.getData());
                    } else {
                        getView().error(openDoorRecordBean.getOther().getMessage());
                    }
                }, this::error));
    }
}