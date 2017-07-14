package com.meiyou.temp;

import com.meiyou.router.model.RouteBean;

import java.util.HashMap;

/**
 * 生成的Java文件
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/7/13
 */
@Deprecated
public class RouterTableSample {

    public static final HashMap<String, RouteBean> map = new HashMap<>();

    static {
        RouteBean.createBean(map, "/home", "com.seeker.tony.myapplication.IntentActivity");
        RouteBean.createBean(map, "/home/action", "com.seeker.tony.myapplication.action.TestAction");
    }


}
