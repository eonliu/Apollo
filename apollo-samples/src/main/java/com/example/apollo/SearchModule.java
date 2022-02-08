package com.example.apollo;

import com.eonliu.apollo.annotations.Environment;
import com.eonliu.apollo.annotations.Name;
import com.example.samples.lib.LibModule;


/**
 * 定义另一个功能模块的服务器地址。（也可以统一定义在一类或接口个中）</p>
 * 此功能模块会生成{@code SearchEnvironments},获取SEARCH的方式是{@code SearchEnvironments.getSearchUrl()}
 */
@Name("search")
public interface SearchModule {

    @Environment(url = "https://www.search.release.com", desc = "正式", group = LibModule.RELEASE, moduleName = "搜索", isRelease = true)
    @Environment(url = "https://www.search.debug.com", desc = "测试", group = LibModule.CUSTOM, moduleName = "搜索")
    @Environment(url = "https://www.search.develop.com", desc = "开发", group = LibModule.DEVELOP, moduleName = "搜索")
    String SEARCH = "";
}
