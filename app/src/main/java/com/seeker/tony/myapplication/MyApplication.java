package com.seeker.tony.myapplication;

import android.app.Application;
import android.content.Context;

import com.seeker.tony.myapplication.route.Route;

/**
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/7/13
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        Route.getInstance().init(this);
    }

    public static Context getContext() {
        return context;
    }

}
