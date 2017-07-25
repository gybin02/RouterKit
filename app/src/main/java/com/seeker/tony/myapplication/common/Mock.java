package com.seeker.tony.myapplication.common;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Mock 数据类；
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/6/9
 */

public class Mock {

    /**
     * 模拟Map 数据
     *
     * @return
     */
    public static HashMap getMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("key_int", 10000);
        map.put("key_string", "Hello");
        map.put("key_random", new Random().nextInt(100));
        MockObj value = new MockObj();
        map.put("key_obj", value);
        return map;
    }

    public static ArrayList getList() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("data" + i);
        }
        return list;
    }

    /**
     * 测试数据 Map,含有嵌套
     *
     * @return
     */
    public static HashMap<String, Object> getSampleMap() {
        HashMap<String,Object> map = new HashMap<>();
        map.put("event", "event11");
        map.put("event2", false);
        map.put("event3", 89);
        HashMap<String,Object> paramMap = new HashMap<>();
        paramMap.put("param1", "value1");
        paramMap.put("param2", 3);
        map.put("attribute", paramMap);
        return map;

    }


    /**
     * 测试数据 Json
     *
     * @return
     */
    public static JSONObject getSampleJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("event", "event11");
            object.put("event2", "event2");
            JSONObject param = new JSONObject();
            param.put("param1", "param1");
            param.put("param2", "param2");
            object.put("attribute", param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    private static class MockObj {
        String name = "Mike";
        int age = 39;
    }
}
