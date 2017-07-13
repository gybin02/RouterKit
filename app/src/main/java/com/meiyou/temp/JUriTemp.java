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
        bean.uri = "meiyou:///home";
        bean.target = "com.seeker.tony.myapplication.IntentActivity";
        bean.type = RouteType.UI;
        map.put(bean.uri, bean);
    }


}
