package com.aglhz.s1.room.contract;

import com.aglhz.abase.mvp.contract.base.BaseContract;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.entity.bean.DeviceListBean;
import com.aglhz.s1.entity.bean.RoomsBean;

import java.util.List;

import rx.Observable;


/**
 * Author: LiuJia on 2017/8/20 0020 18:10.
 * Email: liujia95me@126.com
 */

public interface RoomDeviceListContract{


    interface View extends BaseContract.View {
        void responseDeviceList(List<DeviceListBean.DataBean.SubDevicesBean> data);
        void responseHouseList(List<RoomsBean.DataBean.RoomListBean> data);
        void responseDevicectrl(BaseBean bean);
        void responseNewDeviceConfirm(BaseBean bean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestDeviceList(Params params);
        void requestHouseList(Params params);
        void requestDevicectrl(Params params);
        void requestNewDeviceConfirm(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<DeviceListBean> requestDeviceList(Params params);
        Observable<RoomsBean> requestHouseList(Params params);
        Observable<BaseBean> requestDevicectrl(Params params);
        Observable<BaseBean> requestNewDeviceConfirm(Params params);
    }
}
