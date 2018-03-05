package com.itsite.yicommunity.main.propery.model;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;
import com.itsite.yicommunity.entity.bean.RepairApplyBean;
import com.itsite.yicommunity.common.ApiService;
import com.itsite.yicommunity.common.Params;
import com.itsite.yicommunity.main.propery.contract.RepairApplyContract;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author: LiuJia on 2017/5/8 0008 21:31.
 * Email: liujia95me@126.com
 */

public class RepairApplyModel extends BaseModel implements RepairApplyContract.Model {
    @Override
    public void start(Object request) {

    }

    @Override
    public Observable<RepairApplyBean> getRepairApply(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestRepairApply(ApiService.requestRepairApply,
                        params.token,
                        params.cmnt_c,
                        params.pageSize + "",
                        params.page + "")
                .subscribeOn(Schedulers.io());
    }
}
