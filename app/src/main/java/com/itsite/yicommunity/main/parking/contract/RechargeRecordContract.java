package com.itsite.yicommunity.main.parking.contract;


import cn.itsite.abase.mvp.contract.base.BaseContract;
import com.itsite.yicommunity.entity.bean.MonthCardBillListBean;
import com.itsite.yicommunity.common.Params;

import java.util.List;

import rx.Observable;

/**
 * Author: LiuJia on 2017/5/27 0027 15:31.
 * Email: liujia95me@126.com
 */

public interface RechargeRecordContract {

    interface View extends BaseContract.View {
        void responseRechargeRecord(List<MonthCardBillListBean.DataBean.MonthCardBillBean> datas);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestMonthCardBillList(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<MonthCardBillListBean> requsetMonthCardBillList(Params params);
    }

}
