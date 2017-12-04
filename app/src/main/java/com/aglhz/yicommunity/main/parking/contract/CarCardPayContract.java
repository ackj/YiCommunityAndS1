package com.aglhz.yicommunity.main.parking.contract;


import com.aglhz.abase.mvp.contract.base.BaseContract;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.entity.bean.BaseBean;
import com.aglhz.yicommunity.entity.bean.MonthlyPayRulesBean;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import rx.Observable;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 邻里模块所对应的各层对象应有的接口。
 */
public interface CarCardPayContract {

    interface View extends BaseContract.View {

        void responseDeleteCarCard(BaseBean baseBean);

        void responseMonthlyPayRules(MonthlyPayRulesBean bean);

        void responseCarCardBill(JSONObject bill);

    }

    interface Presenter extends BaseContract.Presenter {

        void requestDeleteCarCard(Params params);

        void requestMonthlyPayRules(Params params);

        void requestCarCardBill(Params params);

    }

    interface Model extends BaseContract.Model {

        Observable<BaseBean> requestDeleteCarCard(Params params);

        Observable<MonthlyPayRulesBean> requestMonthlyPayRules(Params params);

        Observable<ResponseBody> requestCarCardBill(Params params);

    }
}