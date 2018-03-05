package com.itsite.yicommunity.main.publish.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import com.itsite.yicommunity.entity.bean.BaseBean;
import com.itsite.yicommunity.common.Params;

import rx.Observable;


/**
 * Author: LiuJia on 2017/5/9 0009 10:30.
 * Email: liujia95me@126.com
 */

public interface PublishContract {

    interface View extends BaseContract.View {
        void responseSuccess(BaseBean bean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestSubmit(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<BaseBean> requestSubmit(Params params);
    }

}
