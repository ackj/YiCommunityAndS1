package com.aglhz.yicommunity.entity.bean;

import java.util.List;

/**
 * Author: LiuJia on 2017/5/24 0024 11:55.
 * Email: liujia95me@126.com
 */

public class MonthlyPayRulesBean extends BaseBean {

    /**
     * data : {"monthCardRuleList":[{"fid":"103ad16d-006a-4f4f-8fda-0b8d47c462d6","name":"一个月","monthCount":1,"money":0.1,"startDate":"2017-11-08","endDate":"2017-12-08","sequenceNum":1,"remark":""},{"fid":"c421ec88-82ba-48f5-8824-250be1ddfe57","name":"两个月","monthCount":2,"money":0.2,"startDate":"2017-11-08","endDate":"2018-01-08","sequenceNum":2,"remark":""},{"fid":"3b11a7e7-d186-4356-bfb1-b0f7c849399b","name":"三个月","monthCount":3,"money":0.3,"startDate":"2017-11-08","endDate":"2018-02-08","sequenceNum":3,"remark":""}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<MonthCardRuleListBean> monthCardRuleList;

        public List<MonthCardRuleListBean> getMonthCardRuleList() {
            return monthCardRuleList;
        }

        public void setMonthCardRuleList(List<MonthCardRuleListBean> monthCardRuleList) {
            this.monthCardRuleList = monthCardRuleList;
        }

        public static class MonthCardRuleListBean {
            /**
             * fid : 103ad16d-006a-4f4f-8fda-0b8d47c462d6
             * name : 一个月
             * monthCount : 1
             * money : 0.1
             * startDate : 2017-11-08
             * endDate : 2017-12-08
             * sequenceNum : 1
             * remark :
             */

            private String fid;
            private String name;
            private int monthCount;
            private double money;
            private String startDate;
            private String endDate;
            private int sequenceNum;
            private String remark;

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

            public int getMonthCount() {
                return monthCount;
            }

            public void setMonthCount(int monthCount) {
                this.monthCount = monthCount;
            }

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
                this.money = money;
            }

            public String getStartDate() {
                return startDate;
            }

            public void setStartDate(String startDate) {
                this.startDate = startDate;
            }

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
            }

            public int getSequenceNum() {
                return sequenceNum;
            }

            public void setSequenceNum(int sequenceNum) {
                this.sequenceNum = sequenceNum;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
