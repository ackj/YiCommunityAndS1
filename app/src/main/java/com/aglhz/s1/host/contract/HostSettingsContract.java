package com.aglhz.s1.host.contract;


import com.aglhz.abase.mvp.contract.base.BaseContract;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.entity.bean.HostSettingsBean;

import rx.Observable;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 主机模块所对应的各层对象应有的接口。
 */
public interface HostSettingsContract {

    interface View extends BaseContract.View {

        void responseSetHost(BaseBean baseBean);

        void responseHostSettings(HostSettingsBean baseBean);

    }

    interface Presenter extends BaseContract.Presenter {
        void requestSetHost(Params params);

        void requestHostSettings(Params params);
    }

    interface Model extends BaseContract.Model {

        Observable<BaseBean> requestSetHost(Params params);

        Observable<HostSettingsBean> requestHostSettings(Params params);
    }
}