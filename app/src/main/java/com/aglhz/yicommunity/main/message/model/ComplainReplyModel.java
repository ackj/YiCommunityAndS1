package com.aglhz.yicommunity.main.message.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;

import com.aglhz.yicommunity.common.ApiService;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.entity.bean.ComplainReplyBean;
import com.aglhz.yicommunity.main.message.contract.ComplainReplyContract;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author: LiuJia on 2017/5/18 0018 18:03.
 * Email: liujia95me@126.com
 */

public class ComplainReplyModel extends BaseModel implements ComplainReplyContract.Model {

    @Override
    public void start(Object request) {

    }

    @Override
    public Observable<ComplainReplyBean> requestComplainReplies(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestComplainReplies(ApiService.requestComplainReplies,
                        params.token,
                        params.complaintFid)
                .subscribeOn(Schedulers.io());
    }
}
