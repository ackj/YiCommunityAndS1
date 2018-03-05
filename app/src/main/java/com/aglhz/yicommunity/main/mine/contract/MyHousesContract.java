package com.aglhz.yicommunity.main.mine.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;

import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.entity.bean.MyHousesBean;

import java.util.List;

import rx.Observable;

/**
 * Author: LiuJia on 2017/5/17 0017 16:03.
 * Email: liujia95me@126.com
 */

public interface MyHousesContract {
    interface View extends BaseContract.View {
        void responseHouses(List<MyHousesBean.DataBean.AuthBuildingsBean> beas);
    }

    interface Presenter extends BaseContract.Presenter {
        void requsetMyHouse(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<MyHousesBean> requsetMyHouse(Params params);
    }
}
