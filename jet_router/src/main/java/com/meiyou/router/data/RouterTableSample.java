package com.meiyou.router.data;

import com.meiyou.router.model.RouteBean;

/**
 * 生成的代码
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/7/14
 */

public class RouterTableSample {

    public static void register() {
        RouteBean bean = RouteBean.createBean("/action", "com.seeker.tony.myapplication.action.TestAction");
        RouterTable.registerRouter(bean.uri, bean);

        RouteBean.createBean("/home", "com.seeker.tony.myapplication.IntentActivity");
        RouterTable.registerRouter(bean.uri, bean);
    }
}
