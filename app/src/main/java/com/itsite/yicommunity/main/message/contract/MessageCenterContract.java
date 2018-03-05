package com.itsite.yicommunity.main.message.contract;


import cn.itsite.abase.mvp.contract.base.BaseContract;
import com.itsite.yicommunity.entity.bean.BaseBean;
import com.itsite.yicommunity.entity.bean.MessageCenterBean;
import com.itsite.yicommunity.common.Params;

import rx.Observable;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 邻里模块所对应的各层对象应有的接口。
 */
public interface MessageCenterContract {

    interface View extends BaseContract.View {
        void responseReadSuccess(BaseBean bean);

        void responseDeleteSuccess(BaseBean bean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestMessageRead(Params params);

        void requestDeleteMessage(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<MessageCenterBean> requestMessages(Params params);

        Observable<BaseBean> requestMessageRead(Params params);

        Observable<BaseBean> requsetDeleteMessage(Params params);
    }
}