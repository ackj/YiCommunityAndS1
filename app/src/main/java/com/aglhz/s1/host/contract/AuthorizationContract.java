package com.aglhz.s1.host.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.AuthorizationBean;
import com.aglhz.s1.entity.bean.BaseBean;

import java.util.List;

import rx.Observable;


public interface AuthorizationContract {

    interface View extends BaseContract.View {
        void responsegatewayAuthList(List<AuthorizationBean.DataBean> bean);
        void responseGatewayAuthSuccesst(BaseBean bean);
        void responseGatewayUnAuthSuccesst(BaseBean bean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestgatewayAuthList(Params params);
        void requestGatewayAuth(Params params);
        void requestGatewayUnAuth(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<AuthorizationBean> requestgatewayAuthList(Params params);
        Observable<BaseBean> requestGatewayAuth(Params params);
        Observable<BaseBean> requestGatewayUnAuth(Params params);
    }
}