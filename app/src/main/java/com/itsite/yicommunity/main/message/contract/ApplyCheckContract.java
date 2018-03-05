package com.itsite.yicommunity.main.message.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import com.itsite.yicommunity.entity.bean.BaseBean;
import com.itsite.yicommunity.common.Params;

import rx.Observable;


/**
 * Author: LiuJia on 2017/5/26 0026 16:57.
 * Email: liujia95me@126.com
 */

public interface ApplyCheckContract {

    interface View extends BaseContract.View {
        void responseApplySuccess(BaseBean bean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestApplyCheck(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<BaseBean> requestApplyCheck(Params params);
    }

}
