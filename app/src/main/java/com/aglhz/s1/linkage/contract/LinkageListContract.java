package com.aglhz.s1.linkage.contract;

import com.aglhz.abase.mvp.contract.base.BaseContract;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.entity.bean.LinkageBean;

import java.util.List;

import rx.Observable;

public interface LinkageListContract {

    interface View extends BaseContract.View {
        void responseLinkageList(List<LinkageBean.DataBean> bean);
        void responseModLinkage(BaseBean bean);
        void responseDeleteLinkage(BaseBean bean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestLinkageList(Params params);
        void requestModLinkage(Params params);
        void requestDeleteLinkage(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<LinkageBean> requestLinkageList(Params params);
        Observable<BaseBean> requestModLinkage(Params params);
        Observable<BaseBean> requestDeleteLinkage(Params params);
    }
}