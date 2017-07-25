package com.meiyou.router.meiyou;

import android.support.annotation.NonNull;

/**
 * 美柚特定的Path;
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/7/25
 */

public class UriMeiyou {

    public static UriMeiyou HOME = new UriMeiyou("/home");


    String path = "";

    /**
     * 构造方法，传入Path: "/home"
     *
     * @param path
     */
    public UriMeiyou(@NonNull String path) {
        this.path = path;
    }

    /**
     * 获取Meiyou Path;
     *
     * @return
     */
    public String getPath() {
        return "meiyou://" + path;
    }
}
