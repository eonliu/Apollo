package com.eonliu.apollo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>环境注册表,层次结构如下：</p>
 *
 * <pre>
 * Module                   (模块名字：AppEnvironments）
 *      Field1              (字段名字：SEARCH）
 *          Environment1    (环境信息：www.xxx.com)
 *          Environment2
 *          Environment3
 *          ...
 *      Field2
 *          Environment1
 *          Environment2
 *          Environment3
 *          ...
 *      ...
 * </pre>
 *
 * @author Eon Liu
 */
public class Registry {
    public static final String PACKAGE = "com.eonliu.apollo.environments";
    // 保存环境信息
    private static final Map<String, Map<String, List<Environment>>> sRegistryMap = new LinkedHashMap<>();
    // 保存当前正在使用的环境,key为className + fieldName。
    private static final Map<String, Environment> sDefaultEnvironment = new HashMap<>();

    public static List<Environment> getAllEnvironment() {
        List<Environment> allEnvironment = new ArrayList<>();
        for (String module : sRegistryMap.keySet()) {
            Map<String, List<Environment>> moduleMap = sRegistryMap.get(module);
            if (moduleMap != null) {
                for (String field : moduleMap.keySet()) {
                    List<Environment> environments = moduleMap.get(field);
                    if (environments != null) {
                        allEnvironment.addAll(environments);
                    }
                }
            }
        }
        return allEnvironment;
    }

    public static Map<String, Environment> getDefaultEnvironment() {
        return sDefaultEnvironment;
    }

    public static Map<String, Map<String, List<Environment>>> getRegistryMap() {
        return sRegistryMap;
    }

    /**
     * 注册环境信息，使用moduleName区分是否已经注册过。
     *
     * @param className 模块名称,生成的类名
     * @param fieldName 字段名字
     * @param holder    环境信息
     */
    public static void registerModule(String className, String fieldName, Environment holder) {
        boolean isModuleRegistered = getRegistryMap().containsKey(className);
        if (isModuleRegistered) { // moduleName已注册过
            Map<String, List<Environment>> environments = getRegistryMap().get(className);
            boolean isFieldRegistered = environments.containsKey(fieldName);
            if (isFieldRegistered) { // module中是否已经注册过field字段
                environments.get(fieldName).add(holder);
            } else {
                List<Environment> env = new LinkedList<>();
                env.add(holder);
                environments.put(fieldName, env);
            }
        } else { // moduleName未注册过
            List<Environment> env = new LinkedList<>();
            env.add(holder);
            Map<String, List<Environment>> moduleMap = new LinkedHashMap<>();
            moduleMap.put(fieldName, env);
            getRegistryMap().put(className, moduleMap);
        }
    }

    /**
     * 注册环境信息，使用moduleName区分是否已经注册过。
     *
     * @param className   模块名称,生成的类名
     * @param fieldName   字段名字
     * @param environment 环境信息
     */
    public static void _registerModule(String className, String fieldName, Environment environment) {
        registerModule(className, fieldName, environment);
        if (environment.isDefault()) {
            updateDefaultEnvironment(className, fieldName, environment);
        }
    }

    public static void updateDefaultEnvironment(String className, String fieldName, Environment environment) {
        sDefaultEnvironment.put(className + fieldName, environment);
    }

    /**
     * 获取默认环境URL
     *
     * @param className 类名
     * @param fieldName 字段名字
     * @return 默认环境url
     */
    public static String getDefaultEnvironment(String className, String fieldName) {
        Environment environment = sDefaultEnvironment.get(className + fieldName);
        if (environment != null) {
            return environment.getUrl();
        } else {
            try {
                throw new EnvironmentException(className + "中的" + fieldName + "字段必须包含一个@Environment(isDefault = true)注解。");
            } catch (EnvironmentException e) {
                e.printStackTrace();
            }
            return "";
        }
    }

    public static List<Environment> getEnvironments(String className, String fieldName) {
        Map<String, Map<String, List<Environment>>> registryMap = getRegistryMap();
        Map<String, List<Environment>> moduleMap = registryMap.get(className);
        if (moduleMap != null) {
            return moduleMap.get(fieldName);
        }
        return new ArrayList<>();
    }
}
