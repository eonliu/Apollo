package com.eonliu.apollo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 环境配置注解,此注解只能用于字段上指定其环境地址。
 *
 * @author Eon Liu
 */
@Target(ElementType.FIELD)
@Repeatable(Environments.class)
@Retention(RetentionPolicy.SOURCE)
public @interface Environment {

    /**
     * 指定当前环境的地址，必填项。
     *
     * @return 服务器地址
     */
    String url();

    /**
     * <p>对{@link Environment#url()}的描述。</p>
     * 选填，推荐填写，这个值会在Apollo的页面中展示。
     *
     * @return 服务器地址描述
     */
    String desc() default "";

    /**
     * <p>必填项，此值用于描述当前环境地址属于哪一组。会在Apollo页面中展示。</p>
     * 例如：
     * <p>RELEASE</p>
     * <p>DEBUG</p>
     * <p>DEVELOP</p>
     * <p>CUSTOM</p>
     * <p>...</p>
     * @return 环境类型
     */
    String group();

    /**
     * <p>模块名，选填，用于描述当前环境属于哪个模块。</p>
     * 例如在一个URL字段上会同时有多个{@link Environment}注解，
     * Apollo会将这些环境归为一组展示切换页面中，其组的描述信息就是{@link Environment#moduleName()}。
     * 默认是会使用第一个不为""的值作为描述，在同一字段上有多个注解的情况下可以只设置一个即可，
     * 如果每个注解都设置，则推荐使用相同的名字。
     * @return 模块名
     */
    String moduleName() default "";

    /**
     * 标识当前环境是否是正式环境，默认优先使用此值为true的环境，
     * 每个字段上的{@link Environment}注解中必须有一个设置isRelease=true的环境。
     *
     * @return 默认环境
     */
    boolean isRelease() default false;
}