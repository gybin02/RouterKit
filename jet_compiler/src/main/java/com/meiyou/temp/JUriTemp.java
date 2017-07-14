package com.meiyou.temp;

import java.util.HashMap;

import com.meiyou.router.model.RouteBean;
import com.meiyou.router.model.RouteType;

/**
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/7/13
 */

public class JUriTemp {

    public static final HashMap<String, RouteBean> map = new HashMap<>();

    static {
        RouteBean.createBean(map, "/home", "com.seeker.tony.myapplication.IntentActivity", RouteType.UI);
        RouteBean.createBean(map, "/home/action", "com.seeker.tony.myapplication.action.TestAction", RouteType.METHOD);
    }
    

}
