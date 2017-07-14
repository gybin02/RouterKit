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
        RouteBean bean = new RouteBean();
        bean.uri = "/home";
        bean.target = "com.seeker.tony.myapplication.IntentActivity";
        bean.type = RouteType.UI;
        map.put(bean.uri, bean);
        
        RouteBean bean2 = new RouteBean();
        bean2.uri = "/home/action";
        bean2.target = "com.seeker.tony.myapplication.action.TestAction";
        bean2.type = RouteType.METHOD;
        map.put(bean.uri, bean);
    }


}
