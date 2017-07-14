package com.meiyou.temp;

import java.util.HashMap;

import model.RouteBean;
import model.RouteType;

/**
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/7/13
 */

public class JUriTemp {
    public static final HashMap<String, RouteBean> map = new HashMap();

    static {
        createBean(map,"/home","com.seeker.tony.myapplication.IntentActivity",RouteType.UI);
        createBean(map,"/home/action","com.seeker.tony.myapplication.action.TestAction",RouteType.METHOD);
    }

    private static void createBean(HashMap<String, RouteBean> map, String uri, String target, RouteType type) {
        RouteBean bean = new RouteBean(uri, target, type);
        map.put(uri, bean);
    }

}
