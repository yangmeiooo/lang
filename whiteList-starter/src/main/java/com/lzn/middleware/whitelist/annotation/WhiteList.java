package com.lzn.middleware.whitelist.annotation;

import java.lang.annotation.*;

/**
 * @description:
 * @author：lzn
 * @date: 2021/12/5
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
@Inherited
public @interface WhiteList {

    String key() default "";

    String returnJson() default "";
}
