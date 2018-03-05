package com.itsite.yicommunity.main.picker.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import com.itsite.yicommunity.entity.bean.CommunitySelectBean;
import com.itsite.yicommunity.common.Params;

import java.util.List;

import rx.Observable;


/**
 * Author: LiuJia on 2017/5/8 0008 11:33.
 * Email: liujia95me@126.com
 */

public interface CommunityPickerContract {

    interface View extends BaseContract.View {
        void responseCommunitys(List<CommunitySelectBean.DataBean.CommunitiesBean> bean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestCommunitys(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<CommunitySelectBean> requestCommunitys(Params params);
    }
}
