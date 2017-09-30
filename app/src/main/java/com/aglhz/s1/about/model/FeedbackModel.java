package com.aglhz.s1.about.model;


import com.aglhz.abase.mvp.model.base.BaseModel;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.s1.about.contract.FeedbackContract;
import com.aglhz.s1.common.ApiService;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;

import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 负责邻里模块的Model层内容。
 */

public class FeedbackModel extends BaseModel implements FeedbackContract.Model {

    private final String TAG = FeedbackModel.class.getSimpleName();

    @Override
    public Observable<BaseBean> requestSubmitFeedback(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestFeedback(ApiService.requestFeedback,
                        params.token, params.des, params.contact)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void start(Object request) {

    }
}