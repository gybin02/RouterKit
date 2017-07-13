package com.seeker.tony.myapplication.route;

import android.util.Log;

import com.meiyou.temp.JUriTemp;
import com.seeker.tony.myapplication.route.action.Action;

import java.util.HashMap;
import java.util.function.Function;

import model.RouteBean;
import model.RouteType;

/**
 * 实现Route功能，
 * 代码抽取出一个新包
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/7/13
 */

public class Route {
    private static final String TAG = "Route";
    private static Route instance;
    /**
     * 路由表
     */
    private HashMap<String, RouteBean> uriTable = new HashMap<>();

    public static Route getInstance() {
        if (instance == null) {
            instance = new Route();
        }
        return instance;
    }

    private Route() {
        uriTable = JUriTemp.map;
        Log.d(TAG, "uriTable: size = " + uriTable.size());
    }

    public void run(String uri) throws Exception {
        String path = getUriPath(uri);

        if (!uriTable.containsKey(path)) {
            Log.d(TAG, "未找到该路由：" + path);
            return;
        }

        RouteBean bean = uriTable.get(path);
        RouteType type = bean.type;
        if(type==RouteType.UI){
            
            
        }else{
            Class<?> clazz = Class.forName(bean.target);
            Action function = (Action) clazz.newInstance();
            function.run(uri);
        }
        
        
    }

    private String getUriPath(String uri) {
        // TODO: 17/7/13  
        return uri;
    }
}
