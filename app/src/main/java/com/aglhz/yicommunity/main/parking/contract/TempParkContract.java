package com.aglhz.yicommunity.main.parking.contract;


import com.aglhz.abase.mvp.contract.base.BaseContract;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.entity.bean.BaseBean;
import com.aglhz.yicommunity.entity.bean.CarCardListBean;
import com.aglhz.yicommunity.entity.bean.ParkingChargeBean;

import java.util.List;

import rx.Observable;


/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 临时停车场模块接口。
 */
public interface TempParkContract {

    interface View extends BaseContract.View {

        void responseParkingCharge(ParkingChargeBean data);

        void responseParkBill(BaseBean baseBean);
    }

    interface Presenter extends BaseContract.Presenter {

        void requestParkingCharge(Params params);

        void requestParkBill(Params params);
    }

    interface Model extends BaseContract.Model {

        Observable<ParkingChargeBean> requestParkingCharge(Params params);

        Observable<BaseBean> requestParkBill(Params params);
    }
}