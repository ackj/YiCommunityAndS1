package com.aglhz.s1.room.model;

import com.aglhz.abase.log.ALog;
import com.aglhz.abase.mvp.model.base.BaseModel;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.s1.common.ApiService;
import com.aglhz.s1.common.Params;
import com.aglhz.s1.entity.bean.BaseBean;
import com.aglhz.s1.entity.bean.RoomsBean;
import com.aglhz.s1.room.contract.AddDeviceContract;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.schedulers.Schedulers;

public class AddDeviceModel extends BaseModel implements AddDeviceContract.Model {

    private static final String TAG = AddDeviceModel.class.getSimpleName();

    @Override
    public void start(Object request) {
    }

	@Override
    public Observable<BaseBean> requestnewDevice(Params params) {
        return HttpHelper.getService(ApiService.class).requestNewDevice(ApiService.requestNewDevice
                , params.token,params.deviceType,params.name,params.roomFid,params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestModDevice(Params params) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if(params.file!=null){
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), params.file);
            builder.addFormDataPart("file", params.file.getName(), requestBody);
        }
        builder.addFormDataPart("index", params.index+"");
        builder.addFormDataPart("name", params.name);
        builder.addFormDataPart("roomFid", params.roomFid);
        builder.addFormDataPart("gateway", params.deviceSn);
        ALog.e(TAG,"index：：：：：：："+params.index);
        ALog.e(TAG,"name：：：：：：："+params.name);
        ALog.e(TAG,"roomFid：：：：：：："+params.roomFid);
        ALog.e(TAG,"gateway：：：：：：："+params.deviceSn);

        return HttpHelper.getService(ApiService.class).requestModDevice(ApiService.requestModDevice
                , params.token,builder.build())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseBean> requestDelDevice(Params params) {
        return HttpHelper.getService(ApiService.class).requestDelDevice(ApiService.requestDelDevice
                , params.token,params.index,params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<RoomsBean> requestHouseList(Params params) {
        return HttpHelper.getService(ApiService.class)
                .requestRoomList(ApiService.requestRoomList,
                        params.token,
                        params.page,
                        params.pageSize,
                        params.deviceSn)
                .subscribeOn(Schedulers.io());
    }

}