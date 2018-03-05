package com.itsite.yicommunity.main.smarthome.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import com.itsite.yicommunity.common.Params;
import com.itsite.yicommunity.entity.bean.FirstLevelBean;
import com.itsite.yicommunity.entity.bean.GoodsBean;
import com.itsite.yicommunity.entity.bean.SubCategoryBean;

import java.util.List;

import rx.Observable;


/**
 * Author: LiuJia on 2017/5/22 0022 09:06.
 * Email: liujia95me@126.com
 */

public interface SmartHomeMallContract {
    interface View extends BaseContract.View {
        void responseSubCategoryList(List<SubCategoryBean.DataBean> datas);

        void responseGoodsList(List<GoodsBean.DataBean> datas);

        void responseFirstLevel(List<FirstLevelBean.DataBean> datas);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestSubCategoryList(Params params);

        void requestGoodsList(Params params);

        //智慧商城一级列表
        void requestFirstLevel(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<SubCategoryBean> requestSubCategoryList(Params params);

        Observable<GoodsBean> requestGoodsList(Params params);

        //智慧商城一级列表
        Observable<FirstLevelBean> requestFirstLevel(Params params);
    }
}
