package com.seeker.tony.myapplication.action;

import android.content.Context;
import android.widget.Toast;

import com.meiyou.annotation.JUri;
import com.meiyou.router.action.Action;
import com.seeker.tony.myapplication.MyApplication;

import java.util.Map;

/**
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/7/13
 */

@JUri("/home/action")
public class TestAction extends Action {
    Context context = MyApplication.getContext();

    @Override
    public void run(Map queryMap) {
        super.run(queryMap);
        String result = (String) queryMap.get("param");
        Toast.makeText(context, "Test Action: "+result, Toast.LENGTH_SHORT).show();
    }
}
