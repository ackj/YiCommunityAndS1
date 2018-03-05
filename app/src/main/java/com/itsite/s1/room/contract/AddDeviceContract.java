package com.itsite.s1.room.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import com.itsite.s1.common.Params;
import com.itsite.s1.entity.bean.BaseBean;
import com.itsite.s1.entity.bean.RoomsBean;

import java.util.List;

import rx.Observable;


public interface AddDeviceContract {

    interface View extends BaseContract.View {
        void responseSuccess(BaseBean bean);
        void responseHouseList(List<RoomsBean.DataBean.RoomListBean> data);

    }

    interface Presenter extends BaseContract.Presenter {
        void requestnewDevice(Params params);
        void requestModDevice(Params params);
        void requestDelDevice(Params params);
        void requestHouseList(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<BaseBean> requestnewDevice(Params params);
        Observable<BaseBean> requestModDevice(Params params);
        Observable<BaseBean> requestDelDevice(Params params);
        Observable<RoomsBean> requestHouseList(Params params);
    }
}