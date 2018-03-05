package com.aglhz.yicommunity.main.home.model;


import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.HttpHelper;

import com.aglhz.s1.common.Constants;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.entity.bean.NoticeBean;
import com.aglhz.yicommunity.entity.bean.ServicesTypesBean;
import com.aglhz.yicommunity.main.home.contract.HomeContract;
import com.aglhz.yicommunity.common.ApiService;
import com.aglhz.yicommunity.entity.bean.BannerBean;
import com.aglhz.yicommunity.entity.bean.BaseBean;
import com.aglhz.yicommunity.entity.bean.FirstLevelBean;
import com.aglhz.yicommunity.entity.bean.MainDeviceListBean;
import com.aglhz.yicommunity.entity.bean.OneKeyDoorBean;
import com.aglhz.yicommunity.entity.bean.SubCategoryBean;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 负责管家模块的Model层内容。
 */

public class HomeModel extends BaseModel implements HomeContract.Model {
    private final String TAG = HomeModel.class.getSimpleName();

    @Override
    public Observable<BannerBean> requestBanners(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestBanners(ApiService.requestBanners,
                        params.cmnt_c)
                .subscribeOn(Schedulers.io());
    }

    public Observable<NoticeBean> requestHomeNotices(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestHomeNotices(ApiService.requestHomeNotices,
                        params.token,
                        params.cmnt_c,
                        params.topnum)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestOpenDoor(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestOpenDoor(ApiService.requestOpenDoor,
                        params.token,
                        params.dir)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<ServicesTypesBean> requestServiceTypes(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestServiceClassifyList(ApiService.requestServiceClassifyList,
                        params.page,
                        params.pageSize,
                        Params.cmnt_c)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<OneKeyDoorBean> requestOneKeyOpenDoorDeviceList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestOneKeyOpenDoorDeviceList(ApiService.requestOneKeyOpenDoorDeviceList,
                        params.token,
                        Params.cmnt_c)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<FirstLevelBean> requestFirstLevel(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestFirstLevel(ApiService.requestFirstLevel,
                        params.keywords,
                        2,
                        Constants.fromPoint)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<SubCategoryBean> requestSubCategoryList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestSubCategoryLevel(ApiService.requestSubCategoryLevel,
                        params.token,
                        params.appType,
                        params.id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<MainDeviceListBean> requestCommEquipmentList(Params params) {
        return HttpHelper.getService(com.aglhz.s1.common.ApiService.class)
                .requestMainDeviceList(
                        com.aglhz.s1.common.ApiService.requestMainDeviceList,
                        params.token,
                        Constants.SMART_GATEWAY.concat(",").concat(Constants.SMART_GATEWAY_GSW3),
                        params.cmnt_dir,
                        params.roomDir,
                        params.page,
                        params.pageSize)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestScanOpenDoor(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestScanOpenDoor(ApiService.requestScanOpenDoor,
                        params.token,
                        params.acsStoreDeviceFid,
                        params.accessKey)
                .subscribeOn(Schedulers.io());
    }
}