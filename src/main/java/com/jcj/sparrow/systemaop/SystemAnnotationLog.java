package com.jcj.sparrow.systemaop;

import java.lang.annotation.*;

/**
 * @Description:  定义注解类，用于日志拦截
 * @Author: 江成军
 * @CreateDate: 2018/09/11 上午09:20
 */

@Retention(RetentionPolicy.RUNTIME) //元注解，定义注解被保留策略，一般有三种策略
                                    //1、RetentionPolicy.SOURCE 注解只保留在源文件中，在编译成class文件的时候被遗弃
                                    //2、RetentionPolicy.CLASS 注解被保留在class中，但是在jvm加载的时候北欧抛弃，这个是默认的声明周期
                                    //3、RetentionPolicy.RUNTIME 注解在jvm加载的时候仍被保留
@Target({ElementType.METHOD}) //定义了注解声明在哪些元素之前(这里是在方法之前)
public @interface SystemAnnotationLog
{
    String actiondesc() default "" ;//操作简述
}
