package com.yangk.demoproject.annotation;

import java.lang.annotation.*;

/**
 * 获取登录用户信息 作用于参数上
 *
 * @author fubb
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
}
