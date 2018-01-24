package com.aglhz.yicommunity.main.propery.contract;

import com.aglhz.abase.mvp.contract.base.BaseContract;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.entity.bean.PropertyPayBean;
import com.aglhz.yicommunity.entity.bean.PropertyPayDetailBean;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Author: LiuJia on 2017/5/7 0007 21:40.
 * Email: liujia95me@126.com
 */

public interface PropertyPayContract {
    interface View extends BaseContract.View {
        void responsePropertyNotPay(PropertyPayBean bean);

        void responsePropertyPayed(PropertyPayBean bean);

        void responsePropertyPayDetail(PropertyPayDetailBean bean);

//        void responseBill(JSONObject jsonData);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestPropertyNotPay(Params params);

        void requestPropertyPayed(Params params);

        void requestPropertyPayDetail(Params params);

//        void requestBill(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<PropertyPayBean> requestPropertyNotPay(Params params);

        Observable<PropertyPayBean> requestPropertyPayed(Params params);

        Observable<PropertyPayDetailBean> requestPropertyPayDetail(Params params);

//        Observable<ResponseBody> requestBill(Params params);
    }
}
