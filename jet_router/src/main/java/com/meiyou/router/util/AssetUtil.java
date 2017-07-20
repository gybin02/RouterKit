package com.meiyou.router.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Asset 帮助类
 * @author zhengxiaobin <gybin02@Gmail.com>
 * @since 2016/3/31
 */
public class AssetUtil {
    /**
     * 从Asset 中读出String
     * 如： "door/h5Resource.json"
     *
     * @param mContext
     * @param jsonFile
     * @return
     */
    public static String getStringFromAsset(Context mContext, String jsonFile) {
        String result = "";
        try {
            //读取文件数据
            InputStream is = mContext.getResources().getAssets().open(jsonFile);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);//输出流
            result = new String(buffer, "utf-8");
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}