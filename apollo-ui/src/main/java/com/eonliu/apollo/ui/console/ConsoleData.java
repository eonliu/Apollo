package com.eonliu.apollo.ui.console;

import com.eonliu.apollo.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eon Liu
 */
public final class ConsoleData {

    /**
     * 业务专区：环境切换
     */
    public static final int TYPE_ENVIRONMENT_SWITCHER = 100;

    /**
     * 常用工具：开发者选项
     */
    public static final int TYPE_DEVELOPMENT = 200;
    /**
     * 常用工具：APP信息
     */
    public static final int TYPE_APP_INFO = 201;
    /**
     * 常用工具：语言设置
     */
    public static final int TYPE_LANGUAGE = 202;
    /**
     * 常用工具：关于手机
     */
    public static final int TYPE_DEVICE_INFO = 203;


    public static List<ConsoleModuleItem> getConsoleData() {
        List<ConsoleModuleItem> moduleList = new ArrayList<>();
        // 业务专区
        List<ConsoleItem> businessList = new ArrayList<>();
        businessList.add(createConsoleItem(R.string.apollo_console_environment_switcher, R.drawable.apollo_switchers, TYPE_ENVIRONMENT_SWITCHER));  // 环境切换
        moduleList.add(createConsoleModule(R.string.apollo_module_business, businessList));

        // 常用工具
        List<ConsoleItem> toolList = new ArrayList<>();
        toolList.add(createConsoleItem(R.string.apollo_console_development, R.drawable.apollo_development, TYPE_DEVELOPMENT));  // 开发者选项
        toolList.add(createConsoleItem(R.string.apollo_console_app_info, R.drawable.apollo_app_details, TYPE_APP_INFO));  // App信息
        toolList.add(createConsoleItem(R.string.apollo_console_language, R.drawable.apollo_language, TYPE_LANGUAGE));  // 语言设置
        toolList.add(createConsoleItem(R.string.apollo_console_device_info, R.drawable.apollo_device_info, TYPE_DEVICE_INFO));  // 关于手机
        moduleList.add(createConsoleModule(R.string.apollo_module_tool, toolList));

        // 版本号
        moduleList.add(new ConsoleModuleItem(0, null, ConsoleAdapter.ITEM_TYPE_VERSION));
        return moduleList;
    }

    private static ConsoleModuleItem createConsoleModule(int moduleName, List<ConsoleItem> businessList) {
        return new ConsoleModuleItem(moduleName, businessList);
    }

    private static ConsoleItem createConsoleItem(int name, int drawable, int type) {
        return new ConsoleItem(name, drawable, type);
    }
}
