package com.aglhz.s1.entity.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.aglhz.s1.room.view.RoomDeviceListRVAdapter;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: LiuJia on 2017/8/28 0028 17:13.
 * Email: liujia95me@126.com
 */

public class DeviceListBean extends BaseBean implements Parcelable{

    /**
     * data : {"subDevices":[{"actions":[{"cmd":1,"code":"Open","name":"打开"},{"cmd":0,"code":"Close","name":"关闭"}],"alarmDelay":0,"category":"device_ctrl","defenseLevel":"24hour","extInfo":{"index":0,"name":"","node":1,"roomId":0},"icon":"http://hygn.image.alimmdn.com/subDeviceType/equipment_smartsocket.png","index":0,"isOline":1,"name":"一路插座"},{"actions":[{"cmd":1,"code":"Open","name":"打开"},{"cmd":0,"code":"Close","name":"关闭"}],"alarmDelay":0,"category":"device_ctrl","defenseLevel":"24hour","extInfo":{"index":2,"name":"","node":2,"roomId":0},"icon":"http://hygn.image.alimmdn.com/subDeviceType/equipment_switch.png","index":2,"isOline":1,"name":"二路开关"},{"actions":[{"cmd":1,"code":"Open","name":"打开"},{"cmd":0,"code":"Close","name":"关闭"}],"alarmDelay":0,"category":"device_ctrl","defenseLevel":"24hour","extInfo":{"index":7,"name":"","node":4,"roomId":0},"icon":"http://hygn.image.alimmdn.com/subDeviceType/equipment_switch.png","index":7,"isOline":1,"name":"四路开关"}]}
     */

    private DataBean data;

    public DeviceListBean(Parcel source) {
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data, flags);
    }
    public static final Parcelable.Creator<DeviceListBean> CREATOR = new Parcelable.Creator<DeviceListBean>() {
        @Override
        public DeviceListBean createFromParcel(Parcel source) {
            return new DeviceListBean(source);
        }

        @Override
        public DeviceListBean[] newArray(int size) {
            return new DeviceListBean[size];
        }
    };

    public static class DataBean implements Parcelable{
        private List<SubDevicesBean> subDevices;

        public List<SubDevicesBean> getSubDevices() {
            return subDevices;
        }

        public void setSubDevices(List<SubDevicesBean> subDevices) {
            this.subDevices = subDevices;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeList(this.subDevices);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.subDevices = new ArrayList<>();
            in.readList(this.subDevices, DeviceListBean.DataBean.SubDevicesBean.class.getClassLoader());
        }

        public static final Creator<DeviceListBean.DataBean> CREATOR = new Creator<DeviceListBean.DataBean>() {
            @Override
            public DeviceListBean.DataBean createFromParcel(Parcel source) {
                return new DeviceListBean.DataBean(source);
            }

            @Override
            public DeviceListBean.DataBean[] newArray(int size) {
                return new DeviceListBean.DataBean[size];
            }
        };

        public static class SubDevicesBean  extends AbstractExpandableItem<DeviceOnOffBean> implements MultiItemEntity,Serializable, Parcelable{
            /**
             * actions : [{"cmd":1,"code":"Open","name":"打开"},{"cmd":0,"code":"Close","name":"关闭"}]
             * alarmDelay : 0
             * category : device_ctrl
             * defenseLevel : 24hour
             * extInfo : {"index":0,"name":"","node":1,"roomId":0}
             * icon : http://hygn.image.alimmdn.com/subDeviceType/equipment_smartsocket.png
             * index : 0
             * isOline : 1
             * name : 一路插座
             */

            private int alarmDelay;
            private String category;
            private String defenseLevel;
            private ExtInfoBean extInfo;
            private String icon;
            private int index;
            private int isOline;
            private String name;
            private List<ActionsBean> actions;

            public int getAlarmDelay() {
                return alarmDelay;
            }

            public void setAlarmDelay(int alarmDelay) {
                this.alarmDelay = alarmDelay;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getDefenseLevel() {
                return defenseLevel;
            }

            public void setDefenseLevel(String defenseLevel) {
                this.defenseLevel = defenseLevel;
            }

            public ExtInfoBean getExtInfo() {
                return extInfo;
            }

            public void setExtInfo(ExtInfoBean extInfo) {
                this.extInfo = extInfo;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public int getIsOline() {
                return isOline;
            }

            public void setIsOline(int isOline) {
                this.isOline = isOline;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<ActionsBean> getActions() {
                return actions;
            }

            public void setActions(List<ActionsBean> actions) {
                this.actions = actions;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.category);
                dest.writeString(this.defenseLevel);
                dest.writeSerializable(this.extInfo);
                dest.writeString(this.icon);
                dest.writeInt(this.index);
                dest.writeInt(this.isOline);
                dest.writeString(this.name);
            }

            public SubDevicesBean() {
            }

            protected SubDevicesBean(Parcel in) {
                this.category = in.readString();
                this.defenseLevel = in.readString();
                this.extInfo = (ExtInfoBean) in.readSerializable();
                this.icon = in.readString();
                this.index = in.readInt();
                this.isOline = in.readInt();
                this.name = in.readString();
            }

            public static final Creator<DeviceListBean.DataBean.SubDevicesBean> CREATOR = new Creator<DeviceListBean.DataBean.SubDevicesBean>() {
                @Override
                public DeviceListBean.DataBean.SubDevicesBean createFromParcel(Parcel source) {
                    return new DeviceListBean.DataBean.SubDevicesBean(source);
                }

                @Override
                public DeviceListBean.DataBean.SubDevicesBean[] newArray(int size) {
                    return new DeviceListBean.DataBean.SubDevicesBean[size];
                }
            };

            @Override
            public int getLevel() {
                return 0;
            }

            @Override
            public int getItemType() {
                return RoomDeviceListRVAdapter.TYPE_DEVICE;
            }

            public static class ExtInfoBean implements Serializable, Parcelable {
                /**
                 * index : 0
                 * name :
                 * node : 1
                 * roomId : 0
                 */

                private int index;
                private String name;
                private int node;
                private int roomId;

                public int getIndex() {
                    return index;
                }

                public void setIndex(int index) {
                    this.index = index;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getNode() {
                    return node;
                }

                public void setNode(int node) {
                    this.node = node;
                }

                public int getRoomId() {
                    return roomId;
                }

                public void setRoomId(int roomId) {
                    this.roomId = roomId;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(this.index);
                    dest.writeString(this.name);
                    dest.writeInt(this.node);
                    dest.writeInt(this.roomId);
                }

                public ExtInfoBean() {
                }

                protected ExtInfoBean(Parcel in) {
                    this.index = in.readInt();
                    this.name = in.readString();
                    this.node = in.readInt();
                    this.roomId = in.readInt();
                }

                public static final Creator<DeviceListBean.DataBean.SubDevicesBean.ExtInfoBean> CREATOR = new Creator<DeviceListBean.DataBean.SubDevicesBean.ExtInfoBean>() {
                    @Override
                    public DeviceListBean.DataBean.SubDevicesBean.ExtInfoBean createFromParcel(Parcel source) {
                        return new DeviceListBean.DataBean.SubDevicesBean.ExtInfoBean(source);
                    }

                    @Override
                    public DeviceListBean.DataBean.SubDevicesBean.ExtInfoBean[] newArray(int size) {
                        return new DeviceListBean.DataBean.SubDevicesBean.ExtInfoBean[size];
                    }
                };
            }

            public static class ActionsBean implements Serializable{
                /**
                 * cmd : 1
                 * code : Open
                 * name : 打开
                 */

                private int cmd;
                private String code;
                private String name;

                public int getCmd() {
                    return cmd;
                }

                public void setCmd(int cmd) {
                    this.cmd = cmd;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }
}
