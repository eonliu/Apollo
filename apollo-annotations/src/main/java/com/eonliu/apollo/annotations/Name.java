package com.eonliu.apollo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>当项目是模块化结构时，可以使用{@link Name}指定当前模块的名字,</p>
 * <p>Apollo会根据这个名字生成一个用于获取环境配置的类。</p>
 * <p>例如：</p>
 * {@code @Name("music")}
 * 则生成的类名为：com.eonliu.apollo.MusicEnvironments。
 * 通过这个你可以获取当前模块所需的环境配置信息。
 *
 * @author Eon Liu
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Name {
    /**
     * 指定当前模块名称。例如："music"、"share"等。
     * 不能为null、""。
     *
     * @return 模块名
     */
    String value();
}
