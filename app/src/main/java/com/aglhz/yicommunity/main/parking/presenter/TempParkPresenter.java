package com.aglhz.yicommunity.main.parking.presenter;

import android.support.annotation.NonNull;

import com.aglhz.abase.mvp.presenter.base.BasePresenter;
import com.aglhz.yicommunity.common.Constants;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.entity.bean.ParkingChargeBean;
import com.aglhz.yicommunity.entity.db.PlateHistoryData;
import com.aglhz.yicommunity.main.parking.contract.TempParkContract;
import com.aglhz.yicommunity.main.parking.model.TempParkModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 临时停车场模块Presenter。
 */
public class TempParkPresenter extends BasePresenter<TempParkContract.View, TempParkContract.Model> implements TempParkContract.Presenter {
    private final String TAG = TempParkPresenter.class.getSimpleName();

    public TempParkPresenter(TempParkContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected TempParkContract.Model createModel() {
        return new TempParkModel();
    }

    @Override
    public void requestParkingCharge(Params params) {
        mRxManager.add(mModel.requestParkingCharge(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<ParkingChargeBean>() {
                    @Override
                    public void _onNext(ParkingChargeBean bean) {
                        if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                            getView().responseParkingCharge(bean);
                        } else {
                            getView().error(bean.getOther().getMessage());
                        }
                    }
                }));
    }

    @Override
    public void requestTempParkBill(Params params) {
        mRxManager.add(mModel.requestTempParkBill(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<ResponseBody>() {
                    @Override
                    public void _onNext(ResponseBody body) {
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(body.string());
                            JSONObject jsonOther = jsonObject.optJSONObject("other");
                            String code = jsonOther.optString("code");
                            if ("200".equals(code)) {
                                JSONObject jsonData = jsonObject.optJSONObject("data");
                                getView().responseTempParkBill(jsonData);
                            } else {
                                getView().error(jsonOther.optString("message"));
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                }));
    }

    @Override
    public void requestPlateHistory() {
        mRxManager.add(mModel.requestPlateHistory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<List<PlateHistoryData>>() {
                    @Override
                    public void _onNext(List<PlateHistoryData> plates) {
                        if (plates != null && !plates.isEmpty()) {
                            plates.add(0, new PlateHistoryData("历史记录"));
                            getView().responsePlateHistory(plates);
                        }
                    }
                }));
    }

    @Override
    public void cachePlateHistory(PlateHistoryData plate) {
        DataSupport.deleteAll(PlateHistoryData.class, "plate = ?", plate.getPlate());
        if (DataSupport.count(PlateHistoryData.class) >= Constants.HISTORY_SIZE) {
            PlateHistoryData first = DataSupport.findFirst(PlateHistoryData.class);
            first.delete();
        }
        plate.save();
        requestPlateHistory();//更新历史记录列表。
    }
}