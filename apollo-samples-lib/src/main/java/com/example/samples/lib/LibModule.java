package com.example.samples.lib;

import com.eonliu.apollo.annotations.Environment;
import com.eonliu.apollo.annotations.Name;

/**
 * @author Eon Liu
 */
@Name("Lib")
public interface LibModule {

    // 预先定义好有几套服务器环境
    public static final String RELEASE = "Release";
    public static final String DEBUG = "Debug";
    public static final String DEVELOP = "Develop";
    public static final String CUSTOM = "Custom";

    @Environment(url = "https://www.apollo.release.com", desc = "正式", group = RELEASE, moduleName = "Apollo", isRelease = true)
    @Environment(url = "https://www.apollo.develop.com", desc = "开发", group = DEVELOP, moduleName = "Apollo")
    String APOLLO = "";

}
