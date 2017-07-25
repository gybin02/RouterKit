package com.meiyou.router;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.meiyou.router.action.Action;
import com.meiyou.router.intercept.InterceptorData;
import com.meiyou.router.intercept.UriInterceptor;
import com.meiyou.router.meiyou.UriMeiyou;
import com.meiyou.router.model.RouterBean;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import dalvik.system.DexFile;

/**
 * 实现路由功能 <br>
 * 需要调用 init初始化；<br>
 * run(String uri),执行路由<br>
 * 可以新增拦截器；<br>
 * 可以新增Scheme判断，是否有效URI；<br>
 * <p>
 * 路由表，要不要加密？
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/7/13
 */

public class Router {
    private static final String TAG = "Router";
    private static Router instance;

    private Context context;
    /**
     * 路由表
     */
    private HashMap<String, String> routerTable = new HashMap<>();

    /**
     * 拦截器列表
     */
    private ArrayList<UriInterceptor> interceptorList = new ArrayList<>();
    private ArrayList<String> schemeList = new ArrayList<>();

    public static Router getInstance() {
        // FIXME: 17/7/21  测试初始
        if (instance == null) {
            instance = new Router();
        }
        return instance;
    }

    public void init(Context context) {
        try {
            this.context = context.getApplicationContext();
            registerAll();
            Log.d(TAG, "routerTable: size = " + routerTable.size());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 读取APT生成代码：
     * 1. 直接先编译代码，在读取类
     * 2. 反射读取类；
     * 3. APT数据保存到Asset里面，可以是JSon这样的，然后读取；
     */
    private Router() {
    }

    /**
     * Main Method, 要不要改成静态的？
     * eg: "meiyou:///home/action"
     *
     * @param uri
     */
    public void run(String uri) {
        try {
            if (context == null) {
                Log.e(TAG, "请先初始化JetRoute：init()");
                return;
            }

            Uri uriTemp = Uri.parse(uri);
            run(uriTemp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Run；
     * @param uriMeiyou
     * @param param
     */
    public void run(UriMeiyou uriMeiyou, HashMap<String, Object> param) {
        String path = uriMeiyou.getPath();
        run(path, param);
    }
    
    /**
     * 跳转
     *
     * @param uri    具体的URI： "meiyou:///home"
     * @param param: Object支持基础数据类型，如果是对象，推荐转成String;
     */
    public void run(String uri, HashMap<String, Object> param) {
        Uri uriTemp = Uri.parse(uri);
        Uri.Builder builder = uriTemp.buildUpon();
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            builder.appendQueryParameter(key, value.toString());
        }
        Uri uriNew = builder.build();
        run(uriNew);
    }
    

    public void run(Uri uri) {
        try {
            if (context == null) {
                Log.e(TAG, "请先初始化JetRoute：init()");
                return;
            }

            if (!checkUri(uri)) {
                return;
            }
            String path = uri.getPath();

            if (!routerTable.containsKey(path)) {
                Log.e(TAG, "未找到该路由：" + path);
                return;
            }
            InterceptorData data = doIntercept(uri);
            doRun(data.mUri);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 新增拦截器
     *
     * @param interceptor
     */
    public void addInterceptor(UriInterceptor interceptor) {
        interceptorList.add(interceptor);
    }

    /**
     * 新增支持的Scheme；支持多Scheme
     * 不设置：全部允许；推荐设置;
     * eg: "meiyou"
     *
     * @param scheme
     */
    public void addScheme(String... scheme) {
        schemeList.addAll(Arrays.asList(scheme));
    }

/**************  private  Part ********/

    /**
     * 前置拦截
     *
     * @param uriTemp
     * @return
     */
    private InterceptorData doIntercept(Uri uriTemp) {
        InterceptorData data = new InterceptorData();
        data.mUri = uriTemp;
        for (UriInterceptor interceptor : interceptorList) {
            data = interceptor.beforeExecute(data);
        }
        return data;
    }

    /**
     * 处理运行Uri
     *
     * @param uri
     * @throws Exception
     */
    private void doRun(Uri uri) throws Exception {
        String path = uri.getPath();
        String target = routerTable.get(path);

        int type = RouterBean.getType(target);
        Map<String, String> queryMap = getQuery(uri);
        //页面跳转
        if (type == RouterBean.TYPE_UI) {
            Class clazz = Class.forName(target);
            Intent intent = new Intent(context, clazz);
            fillIntent(intent, queryMap);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            Class<?> clazz = Class.forName(target);
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
     * Scheme是：HTTP
     * Port是：-1
     * File是/referencejava/net/ URL.html?s=a
     * Path是/referencejava/net/URL.html
     * Query是：s=a
     *
     * @param uri
     * @return true，有效，
     */
    private boolean checkUri(Uri uri) {
        String scheme = uri.getScheme();
        if (schemeList.size() == 0) {
            return true;
        } else {
            if (schemeList.contains(scheme)) {
                return true;
            }
        }
        return false;

    }

//    /**
//     * 获取URI path
//     *
//     * @param uri
//     * @return
//     */
//    private String getUriPath(String uri) {
//        return uri;
//    }

//    public Map getQuery(String uri) {
//        Map<String, String> query = new LinkedHashMap<>();
//        return query;
//    }


    private static Map<String, String> getQuery(Uri uri) {
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
        AssetManager assetManager = context.getResources().getAssets();
        String root = "router";
        String[] list = assetManager.list(root);
        for (String path : list) {
            InputStream inputStream = assetManager.open(root + "/" + path);
            String s = InputStream2String(inputStream);
            Map map = new Gson().fromJson(s, Map.class);
            routerTable.putAll(map);
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
        return result;
    }

    /**
     * 反射读取map 数据；
     */
//    @Deprecated
//    private void register() {
    //            Class<?> table = Class.forName(RouterConstant.PkgName + "." + RouterConstant.ClassName);
//            Field field = table.getDeclaredField("map");
//            Object instance = table.newInstance();
//
//            Object data = field.get(instance);
//            if (data instanceof HashMap) {
//                routerTable = (HashMap<String, RouteBean>) data;
//            }
//    }

    /**
     * 使用反射读取,路由表
     *
     * @throws Exception
     */
    @Deprecated
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
    @Deprecated
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
