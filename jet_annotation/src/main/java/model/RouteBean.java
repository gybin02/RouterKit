package model;

/**
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/7/13
 */

public class RouteBean {
   public String uri;
    public String target;
    public RouteType type;

    public RouteBean(String uri, String target, RouteType type) {
        this.uri = uri;
        this.target = target;
        this.type = type;
    }
}
