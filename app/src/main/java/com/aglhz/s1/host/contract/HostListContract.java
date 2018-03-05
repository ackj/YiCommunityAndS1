package com.aglhz.s1.host.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;

import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.GatewaysBean;

import java.util.List;

import rx.Observable;

public interface HostListContract {

    interface View extends BaseContract.View {
        void responseGateways(List<GatewaysBean.DataBean> bean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestGateways(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<GatewaysBean> requestGateways(Params params);
    }
}