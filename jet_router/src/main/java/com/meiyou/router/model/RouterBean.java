package com.meiyou.router.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 数据
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/7/13
 */

public class RouterBean {
    /**
     * 需要处理的URI
     */
    public String uri;
    /**
     * 处理的类名： 需要 Java规范命名： com.test.action.TestAction
     */
    public String target;
    /**
     * target类型：
     */

    public static final int TYPE_UI = 1;
    public static final int TYPE_METHOD = 2;

    public
    @RouterType
    int type;

    @IntDef({TYPE_UI, TYPE_METHOD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RouterType {

    }


//    private RouterBean(String uri, String target, RouterType type) {
//        this.uri = uri;
//        this.target = target;
//        this.type = type;
//    }

    /**
     * 初始化
     *
     * @param uri
     * @param target
     */
    public RouterBean(String uri, String target) {
        int type = getType(target);
        this.uri = uri;
        this.target = target;
        this.type = type;
    }

    /**
     * 获取URI类型
     *
     * @param target
     * @return
     */
    public static
    @RouterBean.RouterType
    int getType(String target) {
        int type = RouterBean.TYPE_METHOD;
        // TODO: 17/7/14  需要更严格的判断
        if (target.contains("Activity")) {
            type = RouterBean.TYPE_UI;
        }
        return type;
    }
}
