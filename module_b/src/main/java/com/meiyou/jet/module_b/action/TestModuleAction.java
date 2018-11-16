package com.jet.jet.module_b.action;

import android.util.Log;

import com.jet.annotation.JUri;
import com.jet.router.action.Action;

import java.util.Map;

/**
 * @author zhengxiaobin
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
