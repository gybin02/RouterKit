package com.meiyou.router.data;

import com.meiyou.router.model.RouteBean;

import java.util.HashMap;

/**
 * 路由表
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/7/14
 */

public class RouterTable {
    public static HashMap<String, RouteBean> map = new HashMap<>();

    public static void registerRouter(String uri, RouteBean bean) {
        map.put(uri, bean);
    }

}
