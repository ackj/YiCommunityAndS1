package com.aglhz.yicommunity.main.parking.contract;


import com.aglhz.abase.mvp.contract.base.BaseContract;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.entity.bean.BaseBean;
import com.aglhz.yicommunity.entity.bean.CarportBeam;

import rx.Observable;


/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 车位模块接口。
 */
public interface ApplyContract {

    interface View extends BaseContract.View {
        void responseApplyCard(BaseBean data);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestApplyCard(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<BaseBean> requestApplyCard(Params params);
    }
}