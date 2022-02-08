package com.example.apollo;

import com.eonliu.apollo.annotations.Environment;
import com.eonliu.apollo.annotations.Name;
import com.example.samples.lib.LibModule;

/**
 * 定义服务器地址信息，@Name注解定义所有地址属于哪一功能模块，最终会生成AppEnvironments类。</p>
 * 在代码中通过生成的类获取具体的服务器地址。</p>
 * 例如要获取MUSIC的地址则应该使用{@code AppEnvironments.getMusicUrl()}方法获取。</p>
 * 在Release或非Release包中默认获取isRelease为true的url。</p>
 * 但在非Release包中如果手动切换过group，则每次重新启动会使用已选group当做默认地址。</p>
 * 目前单个功能模块的切换只支持运行时，重启之后会恢复成已选group的默认值。</p>
 */
@Name("app")
public final class AppConstants {

    // 每个Environment表示一种服务器地址，每个字段中可以使用多个Environment，但是必须有一个是isRelease的。
    @Environment(url = "https://music.release.com", desc = "正式", group = LibModule.RELEASE, moduleName = "音乐", isRelease = true)
    @Environment(url = "https://music.debug.com", desc = "测试", group = LibModule.DEBUG, moduleName = "音乐")
    String MUSIC;

    @Environment(url = "https://video.release.com", desc = "正式", group = LibModule.RELEASE, moduleName = "博客", isRelease = true)
    @Environment(url = "https://video.debug.com", desc = "测试", group = LibModule.CUSTOM, moduleName = "博客")
    @Environment(url = "https://video.develop.com", desc = "开发", group = LibModule.CUSTOM, moduleName = "博客")
    @Environment(url = "https://video.custom.com", desc = "自定义", group = LibModule.CUSTOM, moduleName = "博客")
    private String VIDEO;

}
