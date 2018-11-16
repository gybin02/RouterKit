package com.meiyou.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhengxiaobin
 * @since 17/7/13
 */
 @Deprecated
@Target(ElementType.TYPE) // 代表在类级别上才能使用该注解
@Retention(RetentionPolicy.SOURCE)
public @interface JUriAction {
    String[] value();
}
