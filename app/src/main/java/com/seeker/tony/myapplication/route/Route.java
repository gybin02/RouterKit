package com.seeker.tony.myapplication.route;

/**
 * 实现Route功能，
 * 代码抽取出一个新包
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/7/13
 */

public class Route {
    private static Route instance;

    public static Route getInstance() {
        if (instance == null) {
            instance = new Route();
        }
        return instance;
    }
    
    public  void run(String uri){
    
    }
}
