package com.aglhz.s1.main.smarthome.contract;

import com.aglhz.abase.mvp.contract.base.BaseContract;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.entity.bean.CameraBean;
import com.aglhz.s1.entity.bean.EquipmentBean;

import java.util.List;

import rx.Observable;


/**
 * Author: LiuJia on 2017/9/26 0026 10:16.
 * Email: liujia95me@126.com
 */

public interface SmartHomeContract {

    interface View extends BaseContract.View {
        void responseEquipmentInfoList(List<EquipmentBean.DataBean.DataListBean> data);

        void responseDelGatewaySuccess(BaseBean baseBean);

        void responseCameraList(List<CameraBean.DataBean> data);

        void responseAddAndDelCameraSuccess(BaseBean baseBean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestEquipmentInfoList(Params params);

        void requestDelGateway(Params params);

        void requestCameraList(Params params);

        void requestNewCamera(Params params);

        void requestDelCamera(Params params);

    }

    interface Model extends BaseContract.Model {
        Observable<EquipmentBean> requestEquipmentInfoList(Params params);

        Observable<BaseBean> requestDelGateway(Params params);

        Observable<CameraBean> requestCameraList(Params params);

        Observable<BaseBean> requestNewCamera(Params params);

        Observable<BaseBean> requestDelCamera(Params params);
    }

}
