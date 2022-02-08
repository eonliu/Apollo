package com.eonliu.apollo;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * Apollo API
 *
 * @author Eon Liu
 */
public final class Apollo {

    public static final String APOLLO_ENVIRONMENT_FILE = "EnvironmentGroup";
    public static final String APOLLO_ENVIRONMENT_KEY = "EnvironmentGroupKey";
    public static final String APOLLO_LOG_SWITCH_FILE = "ApolloLog";
    public static final String APOLLO_LOG_SWITCH_KEY = "ApolloLogSwitch";

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    private Apollo() {
    }

    /**
     * 是否可以输入日志信息。
     */
    public static boolean logEnable() {
        return Environments.getInstance().isLogSwitch();
    }

    /**
     * 设置Log的默认状态，默认是关闭状态，可以使用BuildConfig.DEBUG进行初始化。这样当打debug包的时候就可以默认打印日志了。
     *
     * @param enable
     */
    public static void setLogEnable(boolean enable) {
        Environments.getInstance().setLogSwitch(enable);
    }

    public static Context getContext() {
        return sContext;
    }

    public static void setContext(Context context) {
        sContext = context;
    }
}
