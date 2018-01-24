package com.aglhz.yicommunity.main.parking.contract;


import com.aglhz.abase.mvp.contract.base.BaseContract;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.entity.bean.BaseBean;
import com.aglhz.yicommunity.entity.bean.ParkingChargeBean;
import com.aglhz.yicommunity.entity.db.PlateHistoryData;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
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

//        void responseTempParkBill(JSONObject bill);

        void responsePlateHistory(List<PlateHistoryData> plates);
    }

    interface Presenter extends BaseContract.Presenter {

        void requestParkingCharge(Params params);

//        void requestTempParkBill(Params params);

        void requestPlateHistory();

        void cachePlateHistory(PlateHistoryData plate);

    }

    interface Model extends BaseContract.Model {

        Observable<ParkingChargeBean> requestParkingCharge(Params params);

//        Observable<ResponseBody> requestTempParkBill(Params params);

        Observable<List<PlateHistoryData>> requestPlateHistory();
    }
}