package com.czl.annotation;

import com.czl.constants.OdinConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作用：该注解用于强校验Odin平台token
 * 使用条件：作用方法的入参必须存在HttpServletRequest参数
 * 使用范围：注解标记在 {@link com.czl.service} 包下的方法上才生效，范围是否调整需看切面类逻辑
 * 注：处理该注解切面类为 {@link com.czl.core.OdinTokenAspectService}
 *
 * @author: caizelin
 * @date: 2021/10/14 10:49
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OdinToken {
    OdinConfig value();
}
