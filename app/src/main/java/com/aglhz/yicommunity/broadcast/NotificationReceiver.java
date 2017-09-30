package com.aglhz.yicommunity.broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import com.aglhz.abase.log.ALog;
import com.aglhz.s1.event.EventLearnSensor;
import com.aglhz.s1.event.EventRefreshSecurity;
import com.aglhz.yicommunity.App;
import com.aglhz.yicommunity.R;
import com.aglhz.yicommunity.common.Notification.NoticeHelper;
import com.aglhz.yicommunity.login.LoginActivity;
import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by leguang on 2017/9/22 0022.
 * Email：langmanleguang@qq.com
 */

public class NotificationReceiver extends MessageReceiver {
    public static final String TAG = NotificationReceiver.class.getSimpleName();

    @Override
    public void onNotification(Context context, final String title, final String summary, final Map<String, String> extraMap) {
        ALog.e("onNotification, title: " + title + ", summary: " + summary + ", extraMap: " + extraMap);
    }

    @Override
    public void onMessage(Context context, CPushMessage message) {
        ALog.e("onMessage, messageId: " + message.getMessageId() + ", title: " + message.getTitle() + ", content:" + message.getContent());
        NoticeHelper.notification(context, message.getContent());
    }

    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        ALog.e("onNotificationOpened, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
    }

    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        ALog.e("onNotificationClickedWithNoAction, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
    }

    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        ALog.e("onNotificationReceivedInApp, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap + ", openType:" + openType + ", openActivity:" + openActivity + ", openUrl:" + openUrl);
    }

    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
        ALog.e("onNotificationRemoved, messageId:" + messageId);
    }

    /**
     * 接受到对应消息后，消息的弹出处理。
     */
    public void buildNotification(Context context, CPushMessage message) {


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        Intent intent2 = new Intent(context, LoginActivity.class);
        Intent intent3 = new Intent(context, LoginActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivities(context, 1, new Intent[]{intent2, intent3}, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = new NotificationCompat.Builder(App.mContext)
                .setContentTitle(message.getTitle())
                .setContentText(message.getContent())
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_app_logo)
                .setLargeIcon(BitmapFactory.decodeResource(App.mContext.getResources(), R.mipmap.ic_app_logo))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                .setVibrate(new long[]{0, 1000, 1000, 1000})
                .setLights(Color.RED, 1000, 1000)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
//                .setFullScreenIntent(pendingIntent, false)
                .setContentIntent(pendingIntent)
                .build();
        notification.flags |= Notification.FLAG_INSISTENT;

//        notification.contentIntent = buildClickContent(context, message);
//        notification.deleteIntent = buildDeleteContent(context, message);
        notificationManager.notify(message.hashCode(), notification);
    }

//    public PendingIntent buildClickContent(Context context, CPushMessage message) {
//        Intent clickIntent = new Intent();
//        clickIntent.setAction("your notification click action");
//        //添加其他数据
//        clickIntent.putExtra("message key", message);//将message放入intent中，方便通知自建通知的点击事件
//        return PendingIntent.getService(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//    }
//
//    public PendingIntent buildDeleteContent(Context context, CPushMessage message) {
//        Intent deleteIntent = new Intent();
//        deleteIntent.setAction("your notification click action");
//        //添加其他数据
//        deleteIntent.putExtra("message key", message);//将message放入intent中，方便通知自建通知的点击事件
//        return PendingIntent.getService(context, 0, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//    }

    /**
     * 依靠推送来刷新界面。
     *
     * @param type 友盟推送的json。
     */
    private void handleMessage(String type) {
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
        }
    }
}