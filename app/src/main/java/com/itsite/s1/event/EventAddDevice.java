package com.itsite.s1.event;

import com.itsite.s1.entity.bean.NotificationBean;

/**
 * Author: LiuJia on 2017/8/24 0024 17:24.
 * Email: liujia95me@126.com
 */

public class EventAddDevice {
    public NotificationBean notificationBean;

    public EventAddDevice(NotificationBean notificationBean) {
        this.notificationBean = notificationBean;
    }
}
