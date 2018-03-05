package com.itsite.yicommunity.main.parking.contract;


import cn.itsite.abase.mvp.contract.base.BaseContract;
import com.itsite.yicommunity.common.Params;
import com.itsite.yicommunity.entity.bean.CarportBeam;

import rx.Observable;


/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 车位模块接口。
 */
public interface CarportContract {

    interface View extends BaseContract.View {
        void responseCarports(CarportBeam data);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestCarports(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<CarportBeam> requestCarports(Params params);
    }
}