package com.aglhz.s1.camera.contract;

import com.aglhz.abase.mvp.contract.base.BaseContract;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;

import rx.Observable;


/**
 * Author: LiuJia on 2017/9/19 0019 08:51.
 * Email: liujia95me@126.com
 */

public interface CameraSettingContract {
    interface View extends BaseContract.View {

        void responseSuccess(BaseBean baseBean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestModCamera(Params params);

    }

    interface Model extends BaseContract.Model {
        Observable<BaseBean> requestModCamera (Params params);
    }
}
