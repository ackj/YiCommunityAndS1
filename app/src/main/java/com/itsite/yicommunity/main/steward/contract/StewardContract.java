package com.itsite.yicommunity.main.steward.contract;


import cn.itsite.abase.mvp.contract.base.BaseContract;
import com.itsite.yicommunity.common.Params;
import com.itsite.yicommunity.entity.bean.BaseBean;
import com.itsite.yicommunity.entity.bean.ContactBean;
import com.itsite.yicommunity.entity.bean.DoorListBean;
import com.itsite.yicommunity.entity.bean.HouseInfoBean;
import com.itsite.yicommunity.entity.bean.MyHousesBean;

import java.util.List;

import rx.Observable;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 管家模块所对应的各层对象应有的接口。
 */
public interface StewardContract {

    interface View extends BaseContract.View {

        void responseHouses(List<MyHousesBean.DataBean.AuthBuildingsBean> listIcons);

        void responseContact(String[] arrayPhones);

        void responseDoors(DoorListBean mDoorListBean);

        void responseCheckPermission(BaseBean mBaseBean);

        void responseHouseInfoList(List<HouseInfoBean.DataBean> datas);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestContact(Params params);

        void requestDoors(Params params);

        void requestCheckPermission(Params params);

        void requestHouseInfoList(Params params);

    }

    interface Model extends BaseContract.Model {
        Observable<ContactBean> requestContact(Params params);

        Observable<MyHousesBean> requestHouses(Params params);

        Observable<DoorListBean> requestDoors(Params params);

        Observable<BaseBean> requestCheckPermission(Params params);

        Observable<HouseInfoBean> requestHouseInfoList(Params params);

    }
}