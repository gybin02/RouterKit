package com.meiyou.router.model;

import java.util.HashMap;

/**
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/7/13
 */

public class RouteBean {
   public String uri;
    public String target;
    public RouteType type;

    public RouteBean(String uri, String target, RouteType type) {
        this.uri = uri;
        this.target = target;
        this.type = type;
    }

    public static void createBean(HashMap<String, RouteBean> map, String uri, String target, RouteType type) {
        RouteBean bean = new RouteBean(uri, target, type);
        map.put(uri, bean);
    }

}
