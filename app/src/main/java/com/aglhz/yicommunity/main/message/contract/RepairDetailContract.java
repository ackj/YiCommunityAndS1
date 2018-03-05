package com.aglhz.yicommunity.main.message.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;

import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.entity.bean.RepairDetailBean;

import rx.Observable;

/**
 * Author: LiuJia on 2017/5/19 0019 09:38.
 * Email: liujia95me@126.com
 */

public interface RepairDetailContract {

    interface View extends BaseContract.View {
        void responseRepairDetail(RepairDetailBean bean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestRepairDetail(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<RepairDetailBean> requestRepairDetail(Params params);
    }

}
