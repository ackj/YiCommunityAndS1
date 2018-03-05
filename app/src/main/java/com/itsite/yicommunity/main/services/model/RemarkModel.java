package com.itsite.yicommunity.main.services.model;


import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import com.itsite.yicommunity.common.ApiService;
import com.itsite.yicommunity.common.Params;
import com.itsite.yicommunity.entity.bean.RemarkListBean;
import com.itsite.yicommunity.main.services.contract.RemarkContract;

import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 社区服务模块的Model层内容。
 */

public class RemarkModel extends BaseModel implements RemarkContract.Model {
    private final String TAG = RemarkModel.class.getSimpleName();

    @Override
    public void start(Object request) {
    }

    @Override
    public Observable<RemarkListBean> requestRemarkList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestRemarkList(ApiService.requestRemarkList,
                        params.page,
                        params.pageSize,
                        params.commodityFid)
                .subscribeOn(Schedulers.io());
    }
}