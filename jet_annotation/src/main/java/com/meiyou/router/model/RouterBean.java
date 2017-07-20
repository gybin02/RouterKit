package com.meiyou.router.model;

/**
 * 数据
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/7/13
 */

public class RouterBean {
    public String uri;
    public String target;
    public RouterType type;

    private RouterBean(String uri, String target, RouterType type) {
        this.uri = uri;
        this.target = target;
        this.type = type;
    }

    /**
     * 初始化
     *
     * @param uri
     * @param target
     */
    public RouterBean(String uri, String target) {
        RouterType type = getType(target);
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
    private static RouterType getType(String target) {
        RouterType type = RouterType.METHOD;
        // TODO: 17/7/14  需要更严格的判断
        if (target.contains("Activity")) {
            type = RouterType.UI;
        }
        return type;
    }
}
