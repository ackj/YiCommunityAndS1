package com.aglhz.s1.scene.contract;

import com.aglhz.abase.mvp.contract.base.BaseContract;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.entity.bean.SceneBean;

import rx.Observable;


public interface SceneListContract {

    interface View extends BaseContract.View {

        void responseSceneList(SceneBean bean);

        void responseStartScene(BaseBean bean);

        void responseDeleteScene(BaseBean bean);
    }

    interface Presenter extends BaseContract.Presenter {

        void requestSceneList(Params params);

        void requestStartScene(Params params);


        void requestDeleteScene(Params params);
    }

    interface Model extends BaseContract.Model {

        Observable<SceneBean> requestSceneList(Params params);

        Observable<BaseBean> requestStartScene(Params params);

        Observable<BaseBean> requestDeleteScene(Params params);
    }
}