package com.aglhz.yicommunity.common.Notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.aglhz.abase.common.ActivityHelper;
import com.aglhz.abase.log.ALog;
import com.aglhz.s1.event.EventLearnSensor;
import com.aglhz.s1.event.EventRefreshSecurity;
import com.aglhz.yicommunity.App;
import com.aglhz.yicommunity.R;
import com.aglhz.yicommunity.common.DoorManager;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import rx.Observable;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.media.RingtoneManager.getDefaultUri;

/**
 * Created by leguang on 2017/9/18 0018.
 * Email：langmanleguang@qq.com
 */

public class NoticeHelper {
    public static final String TAG = NoticeHelper.class.getSimpleName();
    //亿社区推送类型如下：
    public static final String HOUSE_OWNER_APPLY = "house_owner_apply";// 房屋认证-申请-业主
    public static final String HOUSE_MEMBER_APPLY = "house_member_apply";// 房屋认证-申请-成员
    public static final String HOUSE_RENTER_APPLY = "house_renter_apply";// 房屋认证-申请-租客
    public static final String HOUSE_OWNER_APPROVE = "house_owner_approve";// 房屋认证-审核-业主
    public static final String HOUSE_MEMBER_APPROVE = "house_member_approve";// 房屋认证-审核-成员
    public static final String HOUSE_RENTER_APPROVE = "house_renter_approve";// 房屋认证-审核-租客
    public static final String FEEDBACK_REPLY = "feedback_reply";// 信息反馈-回复
    public static final String REPAIR_REPLY = "repair_reply";// 物业报修-回复
    public static final String NOTICE_PUBLISH = "notice_publish";// 物业公告-发布
    public static final String PROPERTY_BILL_NOTIFY = "property_bill";// 物业账单-通知
    public static final String COMPLAINT_REPLY = "complaint_reply";// 物业投诉-回复
    public static final String REPAIR = "repair_publish";// 物业报修
    public static final String COMPLAINT = "complaint_publish";// 物业投诉
    public static final String SMART_DOOR_CALL_PUSH = "smartdoor_call_push";//智慧门禁呼叫
    public static final String LOGIN_DEVICE_CHG = "login_device_chg";//token发生变化，如：在被的设备上登录。

    //智能家居推送类型如下：
    public static final String SENSOR_LEARN = "sensor_learn";// 传感器学习
    public static final String DEVICE_LEARN = "device_learn";//设备学习
    public static final String GW_ALARM_GAS = "gw_alarm_gas";// 燃气报警
    public static final String GW_NOTIFIY_DEFENSE_ST = "gw_defense_status";// 主机布防状态变更
    public static final String GW_ALARM_SOS = "gw_alarm_sos";// sos报警
    public static final String ALARM_RED = "alarm_red";// 红外报警
    public static final String ALARM_DOOR = "alarm_door";// 门磁报警

    public static void notification(Context mContext, String message) {
        ALog.e("RingtoneManager-->" + getDefaultUri(RingtoneManager.TYPE_ALARM).toString());
        Notice notice = new Gson().fromJson(message, Notice.class);
        long when = TextUtils.isEmpty(notice.getWhen()) ? System.currentTimeMillis() : Long.parseLong(notice.getWhen());
        Uri uriSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        int priority = TextUtils.isDigitsOnly(notice.getPriority()) ? NotificationCompat.PRIORITY_MAX : Integer.parseInt(notice.getPriority());
        String vibrate = notice.getVibrate();
        PendingIntent pendingIntent = contentIntent(mContext, notice.getType());
        handleMessage(notice.getType());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(App.mContext)
                .setContentTitle(notice.getTitle())
                .setContentText(notice.getContent())
                .setWhen(when)
                .setSmallIcon(R.mipmap.ic_app_logo)
                .setLargeIcon(BitmapFactory.decodeResource(App.mContext.getResources(), R.mipmap.ic_app_logo))
                .setSound(uriSound)
                .setVibrate(new long[]{0, 1000, 1000, 1000})
                .setLights(Color.RED, 1000, 1000)
                .setPriority(priority)
                .setAutoCancel(true)
//                .setDefaults()
//                .setFullScreenIntent(pendingIntent, false)
                .setContentIntent(pendingIntent);

        Notification notification = builder.build();

        if (notice.getType().equals(SMART_DOOR_CALL_PUSH)) {
            if (ActivityHelper.getInstance().isEmpty()) {
                notification.flags |= Notification.FLAG_INSISTENT;
                notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                Observable.timer(20, TimeUnit.SECONDS)
                        .subscribe(aLong -> {
                            NotificationManager manager = (NotificationManager) App.mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//                        cancel(message.hashCode());
                            notification.flags = Notification.DEFAULT_SOUND;
                            notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            manager.notify(message.hashCode(), notification);
                        });
            } else {
                notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
        }

        notify(message, notification);
    }

    public static void notify(String message, Notification notification) {
        NotificationManager manager = (NotificationManager) App.mContext.getSystemService(NOTIFICATION_SERVICE);
        manager.notify(message.hashCode(), notification);
    }

    /**
     * 准备为后来的不同类型推送，进入不同页面的路由做准备。
     *
     * @param mContext
     * @param type
     * @return
     */
    private static PendingIntent contentIntent(Context mContext, String type) {
//        Intent intent = new Intent();

        switch (type) {
            case "0":
//                ComponentName component = new ComponentName(context.getPackageName(), "com.aglhz.yicommunity.main.message.MessageActivity");
//                intent.setComponent(component);

                break;
            case "1":
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                break;
        }

        Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
        intent.setPackage(null);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    /**
     * 依靠推送来刷新界面。
     *
     * @param type 友盟推送的json。
     */
    private static void handleMessage(String type) {

        PowerManager pm = (PowerManager) App.mContext.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "notice");
        wakeLock.acquire();
//        wakeLock.release();

        switch (type) {
            case NoticeHelper.SENSOR_LEARN:
                EventBus.getDefault().post(new EventLearnSensor());
            case NoticeHelper.GW_NOTIFIY_DEFENSE_ST:
                EventBus.getDefault().post(new EventRefreshSecurity());
                break;
            case NoticeHelper.DEVICE_LEARN:
                break;
            case NoticeHelper.GW_ALARM_GAS:
                break;
            case NoticeHelper.GW_ALARM_SOS:
                break;
            case NoticeHelper.ALARM_RED:
                break;
            case NoticeHelper.ALARM_DOOR:
                break;
            case NoticeHelper.LOGIN_DEVICE_CHG:
                DoorManager.getInstance().exit();// 停止SipService，用户明确的退出。
                Activity activity = ActivityHelper.getInstance().currentActivity();
                new AlertDialog.Builder(activity)
                        .setTitle("提醒")
                        .setMessage("当前账号已在其他设备上登录，是否重新登录？")
                        .setPositiveButton("是", (dialog, which) ->
                                activity.startActivity(new Intent("LoginActivity")))
                        .setNegativeButton("否", null)
                        .show();
                break;
        }
    }


    public static void openUrl(Context context, String url) {
        if (!TextUtils.isEmpty(url.trim())) {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
            context.startActivity(intent);
        }
    }

    public static void openActivity(Context context, String activity) {
        if (!TextUtils.isEmpty(activity.trim())) {
            Intent intent = new Intent();
            intent.setClassName(context, activity);
            context.startActivity(intent);
        }
    }

    public static void launchApp(Context context) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (intent == null) {
            ALog.e("cannot find app:" + context.getPackageName());
        } else {
            context.startActivity(intent);
        }
    }

    public static void cancel(int id) {
        NotificationManager manager = (NotificationManager) App.mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(id);
    }
}
