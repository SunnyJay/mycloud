package com.tangyuan.annotation;

import java.lang.annotation.*;

/**
 * 作者：sunna
 * 时间: 2018/6/26 14:57
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginUser
{
}