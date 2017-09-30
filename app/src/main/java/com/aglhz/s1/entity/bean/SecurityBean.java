package com.aglhz.s1.entity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author: LiuJia on 2017/4/27 0027 09:03.
 * Email: liujia95me@126.com
 */

public class SecurityBean extends BaseBean{


    /**
     * data : {"gateway":{"defenseStatus":"cancel","defenseStatusDes":"当前处于撤防状态","fid":"qbfvfdvd","isCurrent":1,"isManager":1,"isOnline":0,"name":"主机364","residence":{"addr":"惠州江北凯宾斯基C座","fid":"irifirkfk","name":"凯宾斯基"},"status":0},"subDevices":[{"defenseLevel":"24hour","extInfo":{"index":2,"name":"","node":1,"roomId":0,"subType":1,"type":140},"icon":"","index":2,"isOline":1,"name":""}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * gateway : {"defenseStatus":"cancel","defenseStatusDes":"当前处于撤防状态","fid":"qbfvfdvd","isCurrent":1,"isManager":1,"isOnline":0,"name":"主机364","residence":{"addr":"惠州江北凯宾斯基C座","fid":"irifirkfk","name":"凯宾斯基"},"status":0}
         * subDevices : [{"defenseLevel":"24hour","extInfo":{"index":2,"name":"","node":1,"roomId":0,"subType":1,"type":140},"icon":"","index":2,"isOline":1,"name":""}]
         */

        private GatewayBean gateway;
        private List<SubDevicesBean> subDevices;

        public GatewayBean getGateway() {
            return gateway;
        }

        public void setGateway(GatewayBean gateway) {
            this.gateway = gateway;
        }

        public List<SubDevicesBean> getSubDevices() {
            return subDevices;
        }

        public void setSubDevices(List<SubDevicesBean> subDevices) {
            this.subDevices = subDevices;
        }

        public static class GatewayBean {
            /**
             * defenseStatus : cancel
             * defenseStatusDes : 当前处于撤防状态
             * fid : qbfvfdvd
             * isCurrent : 1
             * isManager : 1
             * isOnline : 0
             * name : 主机364
             * residence : {"addr":"惠州江北凯宾斯基C座","fid":"irifirkfk","name":"凯宾斯基"}
             * status : 0
             */

            private String defenseStatus;
            private String defenseStatusDes;
            private String fid;
            private int isCurrent;
            private int isManager;
            private int isOnline;
            private String name;
            private ResidenceBean residence;
            private int status;

            public String getDefenseStatus() {
                return defenseStatus;
            }

            public void setDefenseStatus(String defenseStatus) {
                this.defenseStatus = defenseStatus;
            }

            public String getDefenseStatusDes() {
                return defenseStatusDes;
            }

            public void setDefenseStatusDes(String defenseStatusDes) {
                this.defenseStatusDes = defenseStatusDes;
            }

            public String getFid() {
                return fid;
            }

            public void setFid(String fid) {
                this.fid = fid;
            }

            public int getIsCurrent() {
                return isCurrent;
            }

            public void setIsCurrent(int isCurrent) {
                this.isCurrent = isCurrent;
            }

            public int getIsManager() {
                return isManager;
            }

            public void setIsManager(int isManager) {
                this.isManager = isManager;
            }

            public int getIsOnline() {
                return isOnline;
            }

            public void setIsOnline(int isOnline) {
                this.isOnline = isOnline;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public ResidenceBean getResidence() {
                return residence;
            }

            public void setResidence(ResidenceBean residence) {
                this.residence = residence;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public static class ResidenceBean {
                /**
                 * addr : 惠州江北凯宾斯基C座
                 * fid : irifirkfk
                 * name : 凯宾斯基
                 */

                private String addr;
                private String fid;
                private String name;

                public String getAddr() {
                    return addr;
                }

                public void setAddr(String addr) {
                    this.addr = addr;
                }

                public String getFid() {
                    return fid;
                }

                public void setFid(String fid) {
                    this.fid = fid;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }

        public static class SubDevicesBean implements Serializable{
            /**
             * defenseLevel : 24hour
             * extInfo : {"index":2,"name":"","node":1,"roomId":0,"subType":1,"type":140}
             * icon :
             * index : 2
             * isOline : 1
             * name :
             */
            private String category;
            private String defenseLevel;
            private ExtInfoBean extInfo;
            private String icon;
            private int index;
            private int isOline;
            private String name;

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

            public static class ExtInfoBean implements Serializable{
                /**
                 * index : 2
                 * name :
                 * node : 1
                 * roomId : 0
                 * subType : 1
                 * type : 140
                 */
                private int index;
                private String name;
                private int node;
                private int roomId;
                private int subType;
                private int type;

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

                public int getSubType() {
                    return subType;
                }

                public void setSubType(int subType) {
                    this.subType = subType;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }
            }
        }
    }
}
