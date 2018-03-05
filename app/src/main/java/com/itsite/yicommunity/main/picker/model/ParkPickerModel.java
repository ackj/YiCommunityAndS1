package com.itsite.yicommunity.main.picker.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import com.itsite.yicommunity.common.ApiService;
import com.itsite.yicommunity.common.Params;
import com.itsite.yicommunity.entity.bean.ParkSelectBean;
import com.itsite.yicommunity.entity.bean.ParkSelectBean.DataBean.ParkPlaceListBean;
import com.itsite.yicommunity.entity.db.ParkHistoryData;
import com.itsite.yicommunity.main.picker.contract.ParkPickerContract;

import org.litepal.crud.DataSupport;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/5 0005 13:01
 */

public class ParkPickerModel extends BaseModel implements ParkPickerContract.Model {
    public static final String TAG = ParkPickerModel.class.getSimpleName();

    @Override
    public Observable<ParkSelectBean> requestParks(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestParks(ApiService.requestParks,
                        params.pageSize,
                        params.page,
                        params.keywords,
                        params.regionKeywords,
                        params.naKeywords)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<ParkPlaceListBean>> requestParkHistory() {
        return Observable.create((Observable.OnSubscribe<List<ParkHistoryData>>) subscriber -> {
            subscriber.onStart();
            try {
                List<ParkHistoryData> data = DataSupport.order("id desc").find(ParkHistoryData.class);
//                for (ParkHistoryData parkHistoryData : data) {
//                    ALog.e("getParkID--" + parkHistoryData.getParkID());
//                }
                subscriber.onNext(data);
            } catch (Exception e) {
                subscriber.onError(e);
            }
            subscriber.onCompleted();
        }).flatMap(new Func1<List<ParkHistoryData>, Observable<ParkHistoryData>>() {
            @Override
            public Observable<ParkHistoryData> call(List<ParkHistoryData> parkHistory) {
                return Observable.from(parkHistory);
            }
        }).map(history -> {
            ParkPlaceListBean bean = new ParkPlaceListBean();
            bean.setName(history.getName());
            bean.setFid(history.getParkID());
            bean.setCommunityName(history.getCommunityName());
            bean.setCommunityFid(history.getCommunityID());
            bean.setAddress(history.getAddress());
            bean.setRegionInfo(history.getRegion());
            return bean;
        }).toList().subscribeOn(Schedulers.computation());
    }
}
