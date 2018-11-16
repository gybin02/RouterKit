package com.jet.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;
@Deprecated
@Retention(CLASS)
@Target(METHOD)
public @interface Test {
    String value();
}   