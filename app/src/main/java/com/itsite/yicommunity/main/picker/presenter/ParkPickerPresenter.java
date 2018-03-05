package com.itsite.yicommunity.main.picker.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import com.itsite.yicommunity.common.Constants;
import com.itsite.yicommunity.common.Params;
import com.itsite.yicommunity.entity.bean.ParkSelectBean;
import com.itsite.yicommunity.entity.bean.ParkSelectBean.DataBean.ParkPlaceListBean;
import com.itsite.yicommunity.entity.db.ParkHistoryData;
import com.itsite.yicommunity.main.picker.contract.ParkPickerContract;
import com.itsite.yicommunity.main.picker.model.ParkPickerModel;

import org.litepal.crud.DataSupport;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/5 0005 13:01
 */

public class ParkPickerPresenter extends BasePresenter<ParkPickerContract.View, ParkPickerContract.Model> implements ParkPickerContract.Presenter {
    public static final String TAG = ParkPickerPresenter.class.getSimpleName();

    public ParkPickerPresenter(ParkPickerContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected ParkPickerContract.Model createModel() {
        return new ParkPickerModel();
    }

    @Override
    public void requestParks(Params params) {
        mRxManager.add(mModel.requestParkHistory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<List<ParkSelectBean.DataBean.ParkPlaceListBean>>() {
                    @Override
                    public void _onNext(List<ParkSelectBean.DataBean.ParkPlaceListBean> parks) {
                        getView().responseParkHistory(parks);
                    }
                }));

        mRxManager.add(mModel.requestParks(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                        ParkPlaceListBean history = new ParkPlaceListBean();
                        history.setName("请选择停车场");
                        history.setItemType(1);
                        List<ParkPlaceListBean> parks = bean.getData().getParkPlaceList();
                        parks.add(0, history);
                        getView().responseParks(parks);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void cacheParkHistory(ParkSelectBean.DataBean.ParkPlaceListBean history) {
        int i = DataSupport.deleteAll(ParkHistoryData.class, "parkID = ?", history.getFid());
        ALog.e("delete--" + i);
        if (DataSupport.count(ParkHistoryData.class) >= Constants.HISTORY_SIZE) {
            ParkHistoryData first = DataSupport.findFirst(ParkHistoryData.class);
            first.delete();
        }
        ParkHistoryData historyData = new ParkHistoryData();
        historyData.setName(history.getName());
        historyData.setParkID(history.getFid());
        historyData.setAddress(history.getAddress());
        historyData.setCommunityName(history.getCommunityName());
        historyData.setCommunityID(history.getCommunityFid());
        historyData.setRegion(history.getRegionInfo());
        historyData.save();
    }
}
