package com.seeker.tony.myapplication.action;

import android.widget.Toast;

import com.meiyou.annotation.JUriAction;
import com.seeker.tony.myapplication.route.action.Action;

import java.util.Map;

/**
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/7/13
 */

@JUriAction("meiyou:///home/action")
public class TestAction extends Action {

    @Override
    public void run(Map queryMap) {
        super.run(queryMap);
        Toast.makeText(context, "Test Action", Toast.LENGTH_SHORT).show();
    }
}
