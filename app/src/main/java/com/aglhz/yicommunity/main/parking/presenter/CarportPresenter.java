package com.aglhz.yicommunity.main.parking.presenter;

import android.support.annotation.NonNull;

import com.aglhz.abase.mvp.presenter.base.BasePresenter;
import com.aglhz.yicommunity.common.Constants;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.common.payment.WxPayHelper;
import com.aglhz.yicommunity.entity.bean.ParkingChargeBean;
import com.aglhz.yicommunity.main.parking.contract.CarportContract;
import com.aglhz.yicommunity.main.parking.contract.TempParkContract;
import com.aglhz.yicommunity.main.parking.model.CarportModel;
import com.aglhz.yicommunity.main.parking.model.TempParkModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 车位模块Presenter。
 */
public class CarportPresenter extends BasePresenter<CarportContract.View, CarportContract.Model> implements CarportContract.Presenter {
    private final String TAG = CarportPresenter.class.getSimpleName();

    public CarportPresenter(CarportContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected CarportContract.Model createModel() {
        return new CarportModel() {
        };
    }


    @Override
    public void requestCarports(Params params) {

    }
}