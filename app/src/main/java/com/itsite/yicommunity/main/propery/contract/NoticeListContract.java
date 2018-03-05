package com.itsite.yicommunity.main.propery.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import com.itsite.yicommunity.entity.bean.NoticeBean;
import com.itsite.yicommunity.common.Params;

import java.util.List;

import rx.Observable;


/**
 * Author: LiuJia on 2017/5/9 0009 22:33.
 * Email: liujia95me@126.com
 */

public interface NoticeListContract {

    interface View extends BaseContract.View {
        void responseNotices(List<NoticeBean.DataBean.NoticeListBean> datas);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestNotices(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<NoticeBean> requestNotices(Params params);
    }
}
