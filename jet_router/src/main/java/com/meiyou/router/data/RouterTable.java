package com.jet.router.data;

import com.jet.router.model.RouterBean;

import java.util.HashMap;

/**
 * 路由表
 *
 * @author zhengxiaobin
 * @since 17/7/14
 */

public class RouterTable {
    public static HashMap<String, RouterBean> map = new HashMap<>();

    public static void registerRouter(String uri, RouterBean bean) {
        map.put(uri, bean);
    }

}
