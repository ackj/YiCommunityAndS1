package com.itsite.yicommunity.event;

import com.itsite.yicommunity.entity.bean.CommunitySelectBean;

/**
 * Author: LiuJia on 2017/5/8 0008 14:02.
 * Email: liujia95me@126.com
 */

public class EventCommunity {

    public CommunitySelectBean.DataBean.CommunitiesBean bean;

    public EventCommunity(CommunitySelectBean.DataBean.CommunitiesBean bean) {
        this.bean = bean;
    }

}