package com.aglhz.s1.entity.bean;

import java.util.List;

/**
 * Author： Administrator on 2017/9/29 0029.
 * Email： liujia95me@126.com
 */
public class SmartHomeBean {

    public static final int TYPE_EQUIPMENT = 1;

    public static final int TYPE_CAMERA = 2;

    public List<EquipmentBean.DataBean.DataListBean> equipmentList;

    public List<CameraBean.DataBean> cameraList;

    public int type;

    public SmartHomeBean(int type,List<EquipmentBean.DataBean.DataListBean> equipmentList,List<CameraBean.DataBean> cameraList){
        this.type = type;
        this.equipmentList = equipmentList;
        this.cameraList = cameraList;
    }

}
