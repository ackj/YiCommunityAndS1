package com.itsite.yicommunity.main.message.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import com.itsite.yicommunity.entity.bean.RepairDetailBean;
import com.itsite.yicommunity.common.Params;

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
