package com.eonliu.apollo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

import com.eonliu.apollo.util.ClassUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.eonliu.apollo.Apollo.APOLLO_ENVIRONMENT_FILE;
import static com.eonliu.apollo.Apollo.APOLLO_ENVIRONMENT_KEY;
import static com.eonliu.apollo.Apollo.APOLLO_LOG_SWITCH_FILE;
import static com.eonliu.apollo.Apollo.APOLLO_LOG_SWITCH_KEY;

/**
 * 环境配置相关操作
 *
 * @author Eon Liu
 */
public final class Environments {

    private static final Environments sInstance = new Environments();
    /**
     * log开关
     */
    private boolean logSwitch = false;
    SharedPreferences mLogPreferences;
    SharedPreferences mEnvPreferences;

    private Environments() {
    }

    public static Environments getInstance() {
        return sInstance;
    }

    public void init(@NonNull Context context) {
        mLogPreferences = context.getSharedPreferences(APOLLO_LOG_SWITCH_FILE, Context.MODE_PRIVATE);
        mEnvPreferences = context.getSharedPreferences(APOLLO_ENVIRONMENT_FILE, Context.MODE_PRIVATE);
        initEnvironmentRegistry(context);
        readEnvironmentsFromSharedPreferences(context);
        writeEnvironmentsToSharedPreferences(context);
    }

    private void initEnvironmentRegistry(Context context) {
        try {
            Set<String> modules = ClassUtils.getFileNameByPackageName(context, Registry.PACKAGE);
            for (String module : modules) {
                Class.forName(module).getConstructor().newInstance();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void readEnvironmentsFromSharedPreferences(Context context) {
        if (!logSwitch) {
            // 初始Log开关状态
            logSwitch = mLogPreferences.getBoolean(APOLLO_LOG_SWITCH_KEY, false);
        }
        // 初始默认环境
        String currentGroup = mEnvPreferences.getString(APOLLO_ENVIRONMENT_KEY, "");
        List<Environment> allEnvironment = Registry.getAllEnvironment();
        Map<String, Environment> defaultEnvironment = Registry.getDefaultEnvironment();
        defaultEnvironment.clear();

        for (Environment env : allEnvironment) {
            if (env == null) continue;
            Environment result = null;

            // 当前环境集合
            if (env.isDefault()) {
                result = env;
            }

            if (env.getGroup().equals(currentGroup)) {
                result = env;
            }
            if (result != null) {
                Registry.updateDefaultEnvironment(result.getClassName(), result.getFieldName(), result);
            }
        }
    }

    private void writeEnvironmentsToSharedPreferences(Context context) {

    }

    /**
     * log开关
     */
    public boolean isLogSwitch() {
        return logSwitch;
    }

    /**
     * 设置log开关
     */
    public void setLogSwitch(boolean logSwitch) {
        this.logSwitch = logSwitch;
        SharedPreferences.Editor logEdit = mLogPreferences.edit();
        logEdit.putBoolean(APOLLO_LOG_SWITCH_KEY, logSwitch);
        logEdit.apply();
    }
}
