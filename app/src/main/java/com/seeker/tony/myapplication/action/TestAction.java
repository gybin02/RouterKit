package com.seeker.tony.myapplication.action;

import android.content.Context;
import android.widget.Toast;

import com.jet.annotation.JUri;
import com.jet.router.action.Action;
import com.seeker.tony.myapplication.MyApplication;

import java.util.Map;

/**
 * 测试URI 使用Action
 *
 * @author zhengxiaobin
 * @since 17/7/13
 */

@JUri("/action")
public class TestAction extends Action {
    Context context = MyApplication.getContext();

    @Override
    public void run(Map queryMap) {
        super.run(queryMap);
        //Uri 里面的参数通过Map传递进来
        String result = (String) queryMap.get("param");
        Toast.makeText(context, "Test Action: " + result, Toast.LENGTH_SHORT).show();
    }
}
