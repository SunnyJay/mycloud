package com.tangyuan.annotation;

import java.lang.annotation.*;

/**
 * 作者：sunna
 * 时间: 2018/6/25 17:04
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreAuth
{
}
