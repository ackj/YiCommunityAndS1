package com.aglhz.yicommunity.propery.model;

import com.aglhz.abase.mvp.model.base.BaseModel;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.yicommunity.bean.NoticeBean;
import com.aglhz.yicommunity.common.ApiService;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.propery.contract.NoticeListContract;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: LiuJia on 2017/5/9 0009 22:34.
 * Email: liujia95me@126.com
 */

public class NoticeListModel extends BaseModel implements NoticeListContract.Model {


    @Override
    public void start(Object request) {

    }

    @Override
    public Observable<NoticeBean> getNoticeList(Params params) {
        return HttpHelper.getService(ApiService.class).getNoticeList(params.token, params.cmnt_c, params.summerable, params.timeable, params.page, params.pageSize)
                .subscribeOn(Schedulers.io());
    }
}
