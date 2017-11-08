package com.aglhz.yicommunity.main.park.contract;

import com.aglhz.abase.mvp.contract.base.BaseContract;
import com.aglhz.yicommunity.entity.bean.ParkingChargeBean;
import com.aglhz.yicommunity.common.Params;

import rx.Observable;


/**
 * Author: LiuJia on 2017/6/1 0001 09:17.
 * Email: liujia95me@126.com
 */

public interface ParkOrderContract {

    interface View extends BaseContract.View {
        void responseParkOrder(ParkingChargeBean.DataBean bean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestParkOrder(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<ParkingChargeBean> requestParkOrder(Params params);
    }

}
