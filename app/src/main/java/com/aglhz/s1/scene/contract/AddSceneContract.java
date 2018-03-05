package com.aglhz.s1.scene.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;

import rx.Observable;


public interface AddSceneContract {

    interface View extends BaseContract.View {

        void responseAddScene(BaseBean bean);
    }

    interface Presenter extends BaseContract.Presenter {

        void requestAddScene(Params params);
    }

    interface Model extends BaseContract.Model {

        Observable<BaseBean> requestAddScene(Params params);
    }
}