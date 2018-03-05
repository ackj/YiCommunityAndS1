package com.itsite.yicommunity.main.door.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import com.itsite.yicommunity.common.Params;
import com.itsite.yicommunity.entity.bean.BaseBean;

import rx.Observable;

/**
 * Author: LiuJia on 2017/9/15 0015 11:10.
 * Email: liujia95me@126.com
 */

public interface FamilyPhoneContract {
    interface View extends BaseContract.View {
        void responseSetFamilyPhone(BaseBean bean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestSetFamilyPhone(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<BaseBean> requestSetFamilyPhone(Params params);
    }
}
