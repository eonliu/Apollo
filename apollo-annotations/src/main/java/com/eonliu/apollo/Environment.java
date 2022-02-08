package com.eonliu.apollo;

import java.io.Serializable;

/**
 * 用于保存单个环境配置信息
 *
 * @author Eon Liu
 */
public class Environment implements Serializable {

    private static final long serialVersionUID = 42L;

    /**
     * 定义环境的类名
     */
    private String className;
    /**
     * 定义环境的字段名
     */
    private String fieldName;
    /**
     * 环境地址
     */
    private String url;
    /**
     * 环境描述
     */
    private String desc;
    /**
     * 环境类型
     */
    private String group;
    /**
     * 环境所在模块名称，例如"视频模块"、"音频模块"、"购物车模块"等。
     */
    private String moduleName;
    /**
     * 是否是默认环境
     */
    private boolean isDefault;

    public Environment(String className, String fieldName, String url, String desc, String group, String moduleName, boolean isDefault) {
        this.className = className;
        this.fieldName = fieldName;
        this.url = url;
        this.desc = desc;
        this.group = group;
        this.moduleName = moduleName;
        this.isDefault = isDefault;
    }

    public Environment(Environment environment) {
        this.className = environment.className;
        this.fieldName = environment.fieldName;
        this.url = environment.url;
        this.desc = environment.desc;
        this.group = environment.group;
        this.moduleName = environment.moduleName;
        this.isDefault = environment.isDefault;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Environment that = (Environment) o;

        if (isDefault() != that.isDefault()) return false;
        if (getClassName() != null ? !getClassName().equals(that.getClassName()) : that.getClassName() != null)
            return false;
        if (getFieldName() != null ? !getFieldName().equals(that.getFieldName()) : that.getFieldName() != null)
            return false;
        if (getUrl() != null ? !getUrl().equals(that.getUrl()) : that.getUrl() != null)
            return false;
        if (getDesc() != null ? !getDesc().equals(that.getDesc()) : that.getDesc() != null)
            return false;
        if (getGroup() != null ? !getGroup().equals(that.getGroup()) : that.getGroup() != null)
            return false;
        return getModuleName() != null ? getModuleName().equals(that.getModuleName()) : that.getModuleName() == null;
    }

    @Override
    public int hashCode() {
        int result = getClassName() != null ? getClassName().hashCode() : 0;
        result = 31 * result + (getFieldName() != null ? getFieldName().hashCode() : 0);
        result = 31 * result + (getUrl() != null ? getUrl().hashCode() : 0);
        result = 31 * result + (getDesc() != null ? getDesc().hashCode() : 0);
        result = 31 * result + (getGroup() != null ? getGroup().hashCode() : 0);
        result = 31 * result + (getModuleName() != null ? getModuleName().hashCode() : 0);
        result = 31 * result + (isDefault() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Environment{" +
                "className='" + className + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", url='" + url + '\'' +
                ", desc='" + desc + '\'' +
                ", group='" + group + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}
