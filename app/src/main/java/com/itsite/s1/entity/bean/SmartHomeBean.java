package com.itsite.s1.entity.bean;

import com.itsite.yicommunity.entity.bean.MainDeviceListBean;

import java.util.List;

/**
 * Author： Administrator on 2017/9/29 0029.
 * Email： liujia95me@126.com
 */
public class SmartHomeBean {

    public static final int TYPE_EQUIPMENT = 1;

    public static final int TYPE_CAMERA = 2;

    public List<MainDeviceListBean.DataBean> equipmentList;

    public List<MainDeviceListBean.DataBean> cameraList;

    public int type;

    public SmartHomeBean(int type,List<MainDeviceListBean.DataBean> equipmentList,List<MainDeviceListBean.DataBean> cameraList){
        this.type = type;
        this.equipmentList = equipmentList;
        this.cameraList = cameraList;
    }

}
