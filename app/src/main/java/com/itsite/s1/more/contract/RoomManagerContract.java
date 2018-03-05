package com.itsite.s1.more.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import com.itsite.s1.common.Params;
import com.itsite.s1.entity.bean.BaseBean;
import com.itsite.s1.entity.bean.RoomTypesBean;
import com.itsite.s1.entity.bean.RoomsBean;

import java.util.List;

import rx.Observable;


public interface RoomManagerContract {

    interface View extends BaseContract.View {
        void responseHouseList(List<RoomsBean.DataBean.RoomListBean> data);

        void responseAddHouse(BaseBean bean);

        void responseRoomTypeList(List<RoomTypesBean.DataBean> data);

        void responseDelroom(BaseBean bean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestHouseList(Params params);

        void requestAddHouse(Params params);

        void requestRoomTypeList(Params params);

        void requestDelroom(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<RoomsBean> requestHouseList(Params params);

        Observable<BaseBean> requestAddHouse(Params params);

        Observable<BaseBean> requestDelroom(Params params);

        Observable<RoomTypesBean> requestRoomTypeList(Params params);
    }
}