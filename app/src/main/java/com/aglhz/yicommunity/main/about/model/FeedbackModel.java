package com.aglhz.yicommunity.main.about.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;

import com.aglhz.yicommunity.common.ApiService;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.main.about.contract.FeedbackContract;
import com.aglhz.yicommunity.entity.bean.BaseBean;

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
                .requestSubmitFeedback(ApiService.requestSubmitFeedback,params.token, params.cmnt_c, params.des, params.contact)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void start(Object request) {

    }
}