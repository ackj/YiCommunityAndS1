package com.aglhz.yicommunity.main.parking.model;

import com.aglhz.abase.mvp.model.base.BaseModel;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.yicommunity.common.ApiService;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.entity.bean.ParkingChargeBean;
import com.aglhz.yicommunity.entity.db.PlateHistoryData;
import com.aglhz.yicommunity.main.parking.contract.TempParkContract;

import org.litepal.crud.DataSupport;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 临时停车场模块Model。
 */
public class TempParkModel extends BaseModel implements TempParkContract.Model {

    @Override
    public Observable<ParkingChargeBean> requestParkingCharge(Params params) {
        return HttpHelper.getService(ApiService.class).
                requestParkingCharge(ApiService.requestParkingCharge,
                        params.token,
                        params.parkPlaceFid,
                        params.carNo)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<ResponseBody> requestTempParkBill(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestTempParkBill(ApiService.requestTempParkBill,
                        params.parkPlaceFid,
                        params.carNo,
                        params.payType)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<PlateHistoryData>> requestPlateHistory() {
        return Observable.create((Observable.OnSubscribe<List<PlateHistoryData>>) subscriber -> {
            subscriber.onStart();
            try {
                List<PlateHistoryData> plates = DataSupport.order("id desc").find(PlateHistoryData.class);
                subscriber.onNext(plates);
            } catch (Exception e) {
                subscriber.onError(e);
            }
            subscriber.onCompleted();
        }).observeOn(Schedulers.computation());
    }
}