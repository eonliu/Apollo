package com.eonliu.apollo.compiler;

import com.eonliu.apollo.Environment;
import com.eonliu.apollo.Registry;
import com.eonliu.apollo.annotations.Environments;
import com.eonliu.apollo.annotations.Name;
import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import org.apache.commons.text.CaseUtils;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import static com.eonliu.apollo.Registry.PACKAGE;

/**
 * {@link com.eonliu.apollo.annotations.Environment} 注解处理器
 *
 * @author Eon Liu
 */
@AutoService(Processor.class)
public class EnvironmentProcessor extends AbstractProcessor {

    private final boolean log = false;
    private final char[] delimiters = {'`', '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '=', '+', ';', ':', '\'', '"', '/', '\\', ',', '.'};
    private Messager mMessager;
    private Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnvironment.getMessager();
        mFiler = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // 收集单个Environment信息
        for (Element element : roundEnvironment.getElementsAnnotatedWith(com.eonliu.apollo.annotations.Environment.class)) {
            // 获取环境信息
            com.eonliu.apollo.annotations.Environment annotation = element.getAnnotation(com.eonliu.apollo.annotations.Environment.class);
            if (registerEnvironment(element, annotation)) return true;
        }

        // 收集多个Environment信息
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Environments.class)) {
            Environments environments = element.getAnnotation(Environments.class);
            com.eonliu.apollo.annotations.Environment[] annotations = environments.value();
            for (com.eonliu.apollo.annotations.Environment annotation : annotations) {
                if (registerEnvironment(element, annotation)) return true;
            }
        }

        if (log) {
            mMessager.printMessage(Diagnostic.Kind.NOTE, "environment >>> " + Registry.getRegistryMap().toString());
        }

        generateEnvironmentClass();
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return ImmutableSet.of(com.eonliu.apollo.annotations.Environment.class.getName(), Environments.class.getName(), Name.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private boolean registerEnvironment(Element element, com.eonliu.apollo.annotations.Environment annotation) {
        // 获取被注解元素所在的类或接口
        Element typeElement = element.getEnclosingElement();
        // 模块名称注解
        Name name = typeElement.getAnnotation(Name.class);
        if (log) {
            mMessager.printMessage(Diagnostic.Kind.NOTE, "@Name >>> " + name);
        }

        // 检查类或者接口上是否指定了Name,如果不符合规范则不处理
        if (!checkName(name, typeElement)) return true;

        // 获取字段名字
        final String fieldName = element.getSimpleName().toString();
        if (log) {
            mMessager.printMessage(Diagnostic.Kind.NOTE, "fieldName >>> " + fieldName);
        }

        // 生成类的名字前缀
        final String envNamePrefix = CaseUtils.toCamelCase(name.value(), true, delimiters);
        // 生成类的完整名字
        String suffix = "Environments";
        final String className = envNamePrefix + suffix;
        if (log) {
            mMessager.printMessage(Diagnostic.Kind.NOTE, "prefix >>> " + envNamePrefix + ", className >>> " + className);
        }

        final String url = annotation.url();
        final String desc = annotation.desc();
        final String group = annotation.group();
        final String moduleName = annotation.moduleName();
        final boolean isRelease = annotation.isRelease();
        Environment holder = new Environment(className, fieldName, url, desc, group, moduleName, isRelease);
        if (log) {
            mMessager.printMessage(Diagnostic.Kind.NOTE, "@Environment >>> " + holder.toString());
            mMessager.printMessage(Diagnostic.Kind.NOTE, "==============================================================");
        }

        // 注册环境信息
        Registry.registerModule(className, fieldName, holder);
        return false;
    }

    private boolean checkName(Name name, Element typeElement) {
        if (name == null) {
            mMessager.printMessage(Diagnostic.Kind.ERROR, "必须使用@Name指定其模块名称。", typeElement);
            return false;
        }

        if (name.value().trim().isEmpty()) {
            mMessager.printMessage(Diagnostic.Kind.ERROR, "@Name不能为值\"\"", typeElement);
            return false;
        }
        return true;
    }

    private void generateEnvironmentClass() {
        // 遍历注册表，构造环境管理类
        Registry.getRegistryMap().forEach((className, fieldMap) -> {

            // 静态代码块，注册环境信息
            CodeBlock.Builder staticCodeBuilder = CodeBlock.builder();

            // 构造环境管理类
            TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(className)
                    .addJavadoc("@author Eon Liu")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

            // 遍历所有的字段，进行注册
            fieldMap.forEach((fieldName, environments) -> {
                environments.forEach(env -> {
                    // 注册
                    staticCodeBuilder.addStatement("Registry._registerModule($S, $S, new $T($S, $S, $S, $S, $S, $S, $L))",
                            className, fieldName, Environment.class, className, fieldName, env.getUrl(), env.getDesc(), env.getGroup(), env.getModuleName(), env.isDefault());
                });

                // 添加公共Api，用于获取环境配置信息
                MethodSpec apiSpec = MethodSpec.methodBuilder("get" + CaseUtils.toCamelCase(fieldName, true, delimiters) + "Url")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .returns(String.class)
                        .addCode("return $T.getDefaultEnvironment($S, $S);\n", Registry.class, className, fieldName)
                        .build();

                typeSpecBuilder.addMethod(apiSpec);
            });

            typeSpecBuilder.addStaticBlock(staticCodeBuilder.build());

            // 构造文件
            JavaFile javaFile = JavaFile.builder(PACKAGE, typeSpecBuilder.build())
                    .addFileComment("This codes are generated automatically. Do not modify!")
                    .build();

            try {
                // 生成环境管理类
                javaFile.writeTo(mFiler);
            } catch (IOException e) {
//                e.printStackTrace();
                if (log) {
                    mMessager.printMessage(Diagnostic.Kind.NOTE, "生成JavaFile异常 >>> " + e.getMessage());
                }
            }
        });
    }
}