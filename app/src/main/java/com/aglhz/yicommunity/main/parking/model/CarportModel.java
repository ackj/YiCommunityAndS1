package com.aglhz.yicommunity.main.parking.model;

import com.aglhz.abase.mvp.model.base.BaseModel;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.yicommunity.common.ApiService;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.entity.bean.BaseBean;
import com.aglhz.yicommunity.entity.bean.ParkingChargeBean;
import com.aglhz.yicommunity.main.parking.contract.CarportContract;
import com.aglhz.yicommunity.main.parking.contract.TempParkContract;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 车位模块Model。
 */
public class CarportModel extends BaseModel implements CarportContract.Model {

    @Override
    public Observable<BaseBean> requestCarports(Params params) {
        return null;
    }
}