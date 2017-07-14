package com.meiyou.router.model;

import java.util.HashMap;

/**
 * 数据
 *
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

    /**
     * 初始化
     *
     * @param map
     * @param uri
     * @param target
     */
    public static void createBean(HashMap<String, RouteBean> map, String uri, String target) {
        RouteType type = getType(target);
        RouteBean bean = new RouteBean(uri, target, type);
        map.put(uri, bean);
    }

    /**
     * 获取URI类型
     *
     * @param target
     * @return
     */
    private static RouteType getType(String target) {
        RouteType type = RouteType.METHOD;
        // FIXME: 17/7/14  需要更严格的判断
        if (target.contains("Activity")) {
            type = RouteType.UI;
        }
        return type;
    }
}
