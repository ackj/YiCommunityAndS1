package com.itsite.yicommunity.main.publish.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import com.itsite.yicommunity.App;
import com.itsite.yicommunity.common.Params;
import com.itsite.yicommunity.common.luban.Luban;
import com.itsite.yicommunity.main.publish.contract.PublishContract;
import com.itsite.yicommunity.main.publish.model.PublishNeighbourModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Author: LiuJia on 2017/5/12 0012 15:09.
 * Email: liujia95me@126.com
 */

public class PublishNeighbourPresenter extends BasePresenter<PublishContract.View, PublishContract.Model> implements PublishContract.Presenter {
    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public PublishNeighbourPresenter(PublishContract.View mView) {
        super(mView);
    }

    private static final String TAG = PublishNeighbourPresenter.class.getSimpleName();

    @Override
    public void start(Object request) {

    }

    @NonNull
    @Override
    protected PublishContract.Model createModel() {
        return new PublishNeighbourModel();
    }

    @Override
    public void requestSubmit(Params params) {
        if (params.type == 1) {
            compress(params);
        } else {
            upload(params);
        }
    }

    public void compress(Params params) {
        Luban.get(App.mContext)
                .load(params.files)
                .putGear(Luban.THIRD_GEAR)
                .asList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        files -> {
                            ALog.e(Thread.currentThread().getName());
                            for (int i = 0; i < files.size(); i++) {
                                ALog.d(TAG, files.get(i).getAbsoluteFile() + " --- length----" + files.get(i).length());
                            }
                            params.files = files;
                            upload(params);
                        });
    }

    public void upload(Params params) {
        mRxManager.add(mModel.requestSubmit(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseBean -> {
                    if (baseBean.getOther().getCode() == 200) {
                        getView().responseSuccess(baseBean);
                    } else {
                        getView().error(baseBean.getOther().getMessage());
                    }
                }, this::error));
    }
}
