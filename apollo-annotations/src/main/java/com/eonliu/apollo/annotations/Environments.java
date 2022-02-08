package com.eonliu.apollo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link Environment}注解容器，在单个字段上可以重复使用{@link Environment}注解进行环境配置。
 *
 * @author Eon Liu
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface Environments {
    Environment[] value();
}

