package com.meiyou.router.intercept;

/**
 * 抽象拦截器
 *
 * @param <T>
 */
public abstract class UriInterceptor<T> {

    public static final int LEVEL_LOW = 0;
    public static final int LEVEL_NORMAL = 1;
    public static final int LEVEL_HIGH = 2;


    /**
     * @param data 入参
     * @return data
     */
    public InterceptorData beforeExecute(InterceptorData data) {
        return data;
    }

//    /**
//     *
//     * @param data  data
//     * @param httpResult  result
//     * @return  result
//     */
//    public Result<T> afterExecute(final InterceptorData data ,Result<T> result) {
//        return httpResult;
//    }
//    
}