package com.example.spring.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Target(ElementType.TYPE)// Target说明了Annotation所修饰的对象范围, TYPE:用于描述类、接口(包括注解类型) 或enum声明
@Retention(RetentionPolicy.RUNTIME)// Reteniton的作用是定义被它所注解的注解保留多久，保留至运行时。所以我们可以通过反射去获取注解信息。
@Component
public @interface GpRemoteService {

}
