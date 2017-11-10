package com.aglhz.yicommunity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.aglhz.abase.log.ALog;
import com.aglhz.abase.network.http.HttpHelper;
import com.aglhz.yicommunity.common.ApiService;
import com.aglhz.yicommunity.common.UserHelper;
import com.aglhz.yicommunity.common.boxing.BoxingGlideLoader;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.register.HuaWeiRegister;
import com.alibaba.sdk.android.push.register.MiPushRegister;
import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.loader.IBoxingMediaLoader;

import org.litepal.LitePal;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class App extends com.aglhz.s1.App implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = App.class.getSimpleName();
    public static App mApp;
    public static String deviceID;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initData();//数据的初始化要在友盟推送之前，因为要注册别名时，用到用户名。
        initPush();//初始化推送。
        initBoxing();//初始化图片选择器。
        LitePal.initialize(this);//初始化ORM。
    }

    private void initData() {
        UserHelper.init();
        registerActivityLifecycleCallbacks(this);
    }

    private void initBoxing() {
        IBoxingMediaLoader loader = new BoxingGlideLoader();
        BoxingMediaLoader.getInstance().init(loader);
//        BoxingCrop.getInstance().init(new BoxingUcrop());初始化图片裁剪（可选）
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ALog.e("onActivityCreated");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ALog.e("onActivityStarted");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        ALog.e("onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ALog.e("onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ALog.e("onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        ALog.e("onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ALog.e("onActivityDestroyed");
    }

    /**
     * 初始化云推送通道
     */
    public void initPush() {
        PushServiceFactory.init(mContext);
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(mContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                ALog.e(TAG, "init cloudchannel success");
                ALog.e(TAG, "getDeviceId-->" + pushService.getDeviceId());

                deviceID = "and_" + pushService.getDeviceId();

                HttpHelper.getService(ApiService.class)
                        .registerDevice(ApiService.registerDevice, UserHelper.token, deviceID, UserHelper.account, "userType")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(baseBean -> ALog.e(TAG, baseBean.getOther().getMessage()));
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                ALog.e(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });

        // 注册方法会自动判断是否支持小米系统推送，如不支持会跳过注册。
        MiPushRegister.register(mContext, "2882303761517621288", "5861762181288");

        // 注册方法会自动判断是否支持华为系统推送，如不支持会跳过注册。
        HuaWeiRegister.register(mContext);
    }
}
