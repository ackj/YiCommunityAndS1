package com.aglhz.yicommunity.main.message.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;

import com.aglhz.yicommunity.common.ApiService;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.entity.bean.BaseBean;
import com.aglhz.yicommunity.entity.bean.MessageCenterBean;
import com.aglhz.yicommunity.main.message.contract.MessageCenterContract;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 负责邻里模块的Model层内容。
 */

public class MessageCenterModel extends BaseModel implements MessageCenterContract.Model {
    private final String TAG = MessageCenterModel.class.getSimpleName();

    @Override
    public void start(Object request) {

    }

    @Override
    public Observable<MessageCenterBean> requestMessages(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestMessages(ApiService.requestMessages,
                        params.token,
                        params.pageSize + "",
                        params.page + "")
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestMessageRead(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestMessageRead(ApiService.requestMessageRead, params.token, params.fid)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requsetDeleteMessage(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestDeleteMessages(ApiService.requestDeleteMessages, params.token,params.isCleanAll,params.fid)
                .subscribeOn(Schedulers.io());
    }
}