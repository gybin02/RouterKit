package com.meiyou.router;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.meiyou.router.action.Action;
import com.meiyou.router.data.RouterTable;
import com.meiyou.router.model.RouterBean;
import com.meiyou.router.model.RouterType;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import dalvik.system.DexFile;

/**
 * 实现Route功能，
 * 代码抽取出一个新包
 * 需要调用 init初始化
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/7/13
 */

public class Router {
    private static final String TAG = "Router";
    private static Router instance;
    /**
     * 路由表
     */
    private HashMap<String, RouterBean> uriTable = new HashMap<>();
    private Context context;

    public static Router getInstance() {
        if (instance == null) {
            instance = new Router();
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * 读取APT生成代码：
     * 1. 直接先编译代码，在读取类
     * 2. 反射读取类；
     * 3. APT数据保存到Asset里面，可以是JSon这样的，然后读取；（未实现）
     */
    private Router() {
        try {
//            Class<?> table = Class.forName(RouterConstant.PkgName + "." + RouterConstant.ClassName);
//            Field field = table.getDeclaredField("map");
//            Object instance = table.newInstance();
//
//            Object data = field.get(instance);
//            if (data instanceof HashMap) {
//                uriTable = (HashMap<String, RouteBean>) data;
//            }

            registerAll();
            uriTable = RouterTable.map;
            Log.d(TAG, "uriTable: size = " + uriTable.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run(Uri uri) {
        // TODO: 17/7/17  

    }


    /**
     * Main Method, 要不要改成静态的？
     *
     * @param uri
     */
    public void run(String uri) {

        if (context == null) {
            Log.e(TAG, "请先初始化JetRoute：");
            return;
        }

        try {
            Uri uriTemp = Uri.parse(uri);
            if (!checkUri(uriTemp)) {
                return;
            }
            String path = uriTemp.getPath();

            if (!uriTable.containsKey(path)) {
                Log.w(TAG, "未找到该路由：" + path);
                return;
            }

            handleRun(uriTemp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理运行Uri
     *
     * @param uri
     * @throws Exception
     */
    private void handleRun(Uri uri) throws Exception {
        String path = uri.getPath();
        RouterBean bean = uriTable.get(path);
        RouterType type = bean.type;
        Map<String, String> queryMap = getQuery(uri);
        //页面跳转
        if (type == RouterType.UI) {
            Class clazz = Class.forName(bean.target);
            Intent intent = new Intent(context, clazz);
            fillIntent(intent, queryMap);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            Class<?> clazz = Class.forName(bean.target);
            Action function = (Action) clazz.newInstance();
            //是否要使用Intent传递数据？
//            Intent intent = new Intent();
//            fillIntent(intent, queryMap);
            function.run(queryMap);
        }
    }

    /**
     * Check uri 是否有效
     * <p>
     * String mHost = uri.getHost();
     * String mScheme = uri.getScheme();
     * String mPath = uri.getPath();
     * String mQuery = uri.getQuery();
     * [scheme:][//authority][path][?query][#fragment]
     * URL:http://developer.android.com/referencejava/net/URL.html?s=a#getRef()
     * 那么它的各个属性的值就为：
     * Authority是:developer.android.com
     * Host是:developer.android.com
     * Port是：-1
     * File是/referencejava/net/ URL.html?s=a
     * Path是/referencejava/net/URL.html
     * Query是：s=a
     *
     * @param uri
     * @return true，有效，
     */
    private boolean checkUri(Uri uri) {
        return true;

    }

    /**
     * 获取URI path
     *
     * @param uri
     * @return
     */
    private String getUriPath(String uri) {
        // TODO: 17/7/13  
        return uri;
    }

    public Map getQuery(String uri) {
        // TODO: 17/7/13 使用Uri 解析HTTP的方法解析参数
        Map<String, String> query = new LinkedHashMap<>();
        return query;
    }


    public static Map<String, String> getQuery(Uri uri) {
        Map<String, String> queryPairs = new LinkedHashMap<>();
        try {
            String query = uri.getQuery();
            if (TextUtils.isEmpty(query)) {
                return queryPairs;
            }
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
                String value = pair.substring(idx + 1);
                //不使用Base64
//                value = base64UrlDecode(value);
                queryPairs.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryPairs;
    }


    /**
     * 填充Intent
     *
     * @param intent
     * @param queryMap
     */
    private void fillIntent(Intent intent, Map<String, String> queryMap) {
        try {
            if (queryMap != null) {
                for (Map.Entry<String, String> entry : queryMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    intent.putExtra(key, value);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void registerAll() throws Exception {
        HashMap routerMap = new HashMap();
        AssetManager assetManager = context.getResources().getAssets();
        String[] list = assetManager.list("/router");
        for (String path : list) {
            InputStream inputStream = assetManager.open(path);
            String s = InputStream2String(inputStream);
            Map<String, String> map = new Gson().fromJson(s, Map.class);
            routerMap.putAll(map);
//            for (Map.Entry<String, String> entry : map.entrySet()) {
//                String key = entry.getKey();
//                String value = entry.getValue();
//                RouterBean bean = new RouterBean(key, value);
//            }
        }

    }

    private static String InputStream2String(InputStream is) {
        String result = "";
        try {
            byte[] buffer = new byte[is.available()];
            is.read(buffer);//输出流
            result = new String(buffer, "utf-8");
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用反射读取,路由表
     *
     * @throws Exception
     */
    private void register() throws Exception {
        String[] classes = getClassesFromPackage(context, RouterConstant.PkgName);
        for (String clazzName : classes) {
            Class<?> clazz = Class.forName(clazzName);
            Method register = clazz.getMethod("register");
            register.invoke(null);
        }
    }

    /**
     * 获取包名下所有的类，小心性能
     *
     * @param context
     * @param packageName
     * @return
     */
    private static String[] getClassesFromPackage(Context context, String packageName) {
        ArrayList<String> classes = new ArrayList<String>();
        try {
            DexFile df = new DexFile(context.getPackageCodePath());
            Enumeration<String> entries = df.entries();
            while (entries.hasMoreElements()) {
                String className = (String) entries.nextElement();
                if (className.contains(packageName)) {
                    classes.add(className);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes.toArray(new String[]{});
    }


}
