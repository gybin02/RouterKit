package com.meiyou.jet.module_b.action;

import android.util.Log;

import com.meiyou.annotation.JUri;
import com.meiyou.router.action.Action;

import java.util.Map;

/**
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/7/17
 */

@JUri("/moduleb/action")
public class TestModuleAction extends Action {
    @Override
    public void run(Map queryMap) {
        super.run(queryMap);
        Log.e("Router", "TestModuleAction Run:");
    }
}
