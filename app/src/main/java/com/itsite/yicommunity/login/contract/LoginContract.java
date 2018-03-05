package com.itsite.yicommunity.login.contract;


import cn.itsite.abase.mvp.contract.base.BaseContract;
import com.itsite.yicommunity.common.Params;
import com.itsite.yicommunity.entity.bean.SipBean;
import com.itsite.yicommunity.entity.bean.UserBean;

import rx.Observable;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 */
public interface LoginContract {

    interface View extends BaseContract.View {
    }

    interface Presenter extends BaseContract.Presenter {
    }

    interface Model extends BaseContract.Model {
        Observable<UserBean> requestLogin(Params params);

        Observable<SipBean> requestSip(Params params);

        void registerPush();
    }
}