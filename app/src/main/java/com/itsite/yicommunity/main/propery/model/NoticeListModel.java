package com.itsite.yicommunity.main.propery.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import com.itsite.yicommunity.entity.bean.NoticeBean;
import com.itsite.yicommunity.common.ApiService;
import com.itsite.yicommunity.common.Params;
import com.itsite.yicommunity.main.propery.contract.NoticeListContract;

import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author: LiuJia on 2017/5/9 0009 22:34.
 * Email: liujia95me@126.com
 * <p>
 * Modify by leguang in 2017.05.11
 */

public class NoticeListModel extends BaseModel implements NoticeListContract.Model {


    @Override
    public void start(Object request) {

    }

    @Override
    public Observable<NoticeBean> requestNotices(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestNotices(
                        ApiService.requestNotices,
                        params.token,
                        params.cmnt_c,
                        params.page + "",
                        params.pageSize + "",
                        params.summerable,
                        params.timeable)
                .subscribeOn(Schedulers.io());
    }
}
