package com.aglhz.yicommunity.main.picker.contract;

import com.aglhz.abase.mvp.contract.base.BaseContract;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.entity.bean.ParkSelectBean;

import java.util.List;

import rx.Observable;


/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/5 0005 13:01
 */
public interface ParkPickerContract {

    interface View extends BaseContract.View {
        void responseParkHistory(List<ParkSelectBean.DataBean.ParkPlaceListBean> bean);

        void responseParks(List<ParkSelectBean.DataBean.ParkPlaceListBean> bean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestParks(Params params);

        void cacheParkHistory(ParkSelectBean.DataBean.ParkPlaceListBean history);
    }

    interface Model extends BaseContract.Model {
        Observable<ParkSelectBean> requestParks(Params params);

        Observable<List<ParkSelectBean.DataBean.ParkPlaceListBean>> requestParkHistory();
    }
}
