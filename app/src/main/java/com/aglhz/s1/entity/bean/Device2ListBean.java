//package com.aglhz.s1.entity.bean;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import com.aglhz.s1.room.view.RoomDeviceListRVAdapter;
//import com.chad.library.adapter.base.entity.AbstractExpandableItem;
//import com.chad.library.adapter.base.entity.MultiItemEntity;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Author: LiuJia on 2017/8/20 0020 18:06.
// * Email: liujia95me@126.com
// */
//
//public class DeviceListBean extends BaseBean implements Parcelable {
//
//    /**
//     * data : {"subDevices":[{"category":"device_ctrl","defenseLevel":"24hour","extInfo":{"index":1,"name":"relay4","node":4,"roomId":1,"subType":4,"type":143,"userFlag":0},"icon":"","index":1,"isOline":1,"name":"relay4"}]}
//     */
//
//    private DataBean data;
//
//    public DataBean getData() {
//        return data;
//    }
//
//    public void setData(DataBean data) {
//        this.data = data;
//    }
//
//    public static class DataBean implements Parcelable {
//        private List<SubDevicesBean> subDevices;
//
//        public List<SubDevicesBean> getSubDevices() {
//            return subDevices;
//        }
//
//        public void setSubDevices(List<SubDevicesBean> subDevices) {
//            this.subDevices = subDevices;
//        }
//
//        public static class SubDevicesBean extends AbstractExpandableItem<DeviceOnOffBean> implements MultiItemEntity,Serializable, Parcelable {
//
//            /**
//             * category : device_ctrl
//             * defenseLevel : 24hour
//             * extInfo : {"index":1,"name":"relay4","node":4,"roomId":1,"subType":4,"type":143,"userFlag":0}
//             * icon :
//             * index : 1
//             * isOline : 1
//             * name : relay4
//             */
//
//            private String category;
//            private String defenseLevel;
//            private ExtInfoBean extInfo;
//            private String icon;
//            private int index;
//            private int isOline;
//            private String name;
//
//            public String getCategory() {
//                return category;
//            }
//
//            public void setCategory(String category) {
//                this.category = category;
//            }
//
//            public String getDefenseLevel() {
//                return defenseLevel;
//            }
//
//            public void setDefenseLevel(String defenseLevel) {
//                this.defenseLevel = defenseLevel;
//            }
//
//            public ExtInfoBean getExtInfo() {
//                return extInfo;
//            }
//
//            public void setExtInfo(ExtInfoBean extInfo) {
//                this.extInfo = extInfo;
//            }
//
//            public String getIcon() {
//                return icon;
//            }
//
//            public void setIcon(String icon) {
//                this.icon = icon;
//            }
//
//            public int getIndex() {
//                return index;
//            }
//
//            public void setIndex(int index) {
//                this.index = index;
//            }
//
//            public int getIsOline() {
//                return isOline;
//            }
//
//            public void setIsOline(int isOline) {
//                this.isOline = isOline;
//            }
//
//            public String getName() {
//                return name;
//            }
//
//            public void setName(String name) {
//                this.name = name;
//            }
//
//            @Override
//            public int getItemType() {
//                return RoomDeviceListRVAdapter.TYPE_DEVICE;
//            }
//
//            @Override
//            public int getLevel() {
//                return 0;
//            }
//
//            public static class ExtInfoBean implements Serializable, Parcelable {
//                /**
//                 * index : 1
//                 * name : relay4
//                 * node : 4
//                 * roomId : 1
//                 * subType : 4
//                 * type : 143
//                 * userFlag : 0
//                 */
//
//                private int index;
//                private String name;
//                private int node;
//                private int roomId;
//                private int subType;
//                private int type;
//                private int userFlag;
//
//                public int getIndex() {
//                    return index;
//                }
//
//                public void setIndex(int index) {
//                    this.index = index;
//                }
//
//                public String getName() {
//                    return name;
//                }
//
//                public void setName(String name) {
//                    this.name = name;
//                }
//
//                public int getNode() {
//                    return node;
//                }
//
//                public void setNode(int node) {
//                    this.node = node;
//                }
//
//                public int getRoomId() {
//                    return roomId;
//                }
//
//                public void setRoomId(int roomId) {
//                    this.roomId = roomId;
//                }
//
//                public int getSubType() {
//                    return subType;
//                }
//
//                public void setSubType(int subType) {
//                    this.subType = subType;
//                }
//
//                public int getType() {
//                    return type;
//                }
//
//                public void setType(int type) {
//                    this.type = type;
//                }
//
//                public int getUserFlag() {
//                    return userFlag;
//                }
//
//                public void setUserFlag(int userFlag) {
//                    this.userFlag = userFlag;
//                }
//
//                @Override
//                public int describeContents() {
//                    return 0;
//                }
//
//                @Override
//                public void writeToParcel(Parcel dest, int flags) {
//                    dest.writeInt(this.index);
//                    dest.writeString(this.name);
//                    dest.writeInt(this.node);
//                    dest.writeInt(this.roomId);
//                    dest.writeInt(this.subType);
//                    dest.writeInt(this.type);
//                    dest.writeInt(this.userFlag);
//                }
//
//                public ExtInfoBean() {
//                }
//
//                protected ExtInfoBean(Parcel in) {
//                    this.index = in.readInt();
//                    this.name = in.readString();
//                    this.node = in.readInt();
//                    this.roomId = in.readInt();
//                    this.subType = in.readInt();
//                    this.type = in.readInt();
//                    this.userFlag = in.readInt();
//                }
//
//                public static final Creator<ExtInfoBean> CREATOR = new Creator<ExtInfoBean>() {
//                    @Override
//                    public ExtInfoBean createFromParcel(Parcel source) {
//                        return new ExtInfoBean(source);
//                    }
//
//                    @Override
//                    public ExtInfoBean[] newArray(int size) {
//                        return new ExtInfoBean[size];
//                    }
//                };
//            }
//
//            @Override
//            public int describeContents() {
//                return 0;
//            }
//
//            @Override
//            public void writeToParcel(Parcel dest, int flags) {
//                dest.writeString(this.category);
//                dest.writeString(this.defenseLevel);
//                dest.writeSerializable(this.extInfo);
//                dest.writeString(this.icon);
//                dest.writeInt(this.index);
//                dest.writeInt(this.isOline);
//                dest.writeString(this.name);
//            }
//
//            public SubDevicesBean() {
//            }
//
//            protected SubDevicesBean(Parcel in) {
//                this.category = in.readString();
//                this.defenseLevel = in.readString();
//                this.extInfo = (ExtInfoBean) in.readSerializable();
//                this.icon = in.readString();
//                this.index = in.readInt();
//                this.isOline = in.readInt();
//                this.name = in.readString();
//            }
//
//            public static final Creator<SubDevicesBean> CREATOR = new Creator<SubDevicesBean>() {
//                @Override
//                public SubDevicesBean createFromParcel(Parcel source) {
//                    return new SubDevicesBean(source);
//                }
//
//                @Override
//                public SubDevicesBean[] newArray(int size) {
//                    return new SubDevicesBean[size];
//                }
//            };
//        }
//
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//        @Override
//        public void writeToParcel(Parcel dest, int flags) {
//            dest.writeList(this.subDevices);
//        }
//
//        public DataBean() {
//        }
//
//        protected DataBean(Parcel in) {
//            this.subDevices = new ArrayList<SubDevicesBean>();
//            in.readList(this.subDevices, SubDevicesBean.class.getClassLoader());
//        }
//
//        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
//            @Override
//            public DataBean createFromParcel(Parcel source) {
//                return new DataBean(source);
//            }
//
//            @Override
//            public DataBean[] newArray(int size) {
//                return new DataBean[size];
//            }
//        };
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeParcelable(this.data, flags);
//    }
//
//    public DeviceListBean() {
//    }
//
//    protected DeviceListBean(Parcel in) {
//        this.data = in.readParcelable(DataBean.class.getClassLoader());
//    }
//
//    public static final Parcelable.Creator<DeviceListBean> CREATOR = new Parcelable.Creator<DeviceListBean>() {
//        @Override
//        public DeviceListBean createFromParcel(Parcel source) {
//            return new DeviceListBean(source);
//        }
//
//        @Override
//        public DeviceListBean[] newArray(int size) {
//            return new DeviceListBean[size];
//        }
//    };
//}
