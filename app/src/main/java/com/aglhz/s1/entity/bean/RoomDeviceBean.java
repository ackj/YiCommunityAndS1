package com.aglhz.s1.entity.bean;

import com.aglhz.s1.room.view.RoomDeviceListRVAdapter;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Author： Administrator on 2017/8/18 0018.
 * Email： liujia95me@126.com
 */
public class RoomDeviceBean extends AbstractExpandableItem<DeviceOnOffBean> implements MultiItemEntity {
    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return RoomDeviceListRVAdapter.TYPE_DEVICE;
    }
}
