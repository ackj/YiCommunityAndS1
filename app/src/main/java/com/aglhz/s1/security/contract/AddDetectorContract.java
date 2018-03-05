package com.aglhz.s1.security.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;

import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.DevicesBean;
import com.aglhz.s1.entity.bean.BaseBean;

import java.util.List;

import rx.Observable;


public interface AddDetectorContract {

    interface View extends BaseContract.View {
        void responseDetectorList(List<DevicesBean.DataBean.DeviceTypeListBean> bean);

        void responseAddDetector(BaseBean baseBean);

        void responseCancellationOfSensorLearning(BaseBean baseBean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestDetectorList(Params params);

        void requestAddDetector(Params params);

        void reqeuestCancellationOfSensorLearning(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<DevicesBean> requestDetectorList(Params params);

        Observable<BaseBean> requestAddDetector(Params params);

        Observable<BaseBean> reqeuestCancellationOfSensorLearning(Params params);
    }
}