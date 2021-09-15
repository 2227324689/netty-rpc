package com.example.spring.service;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Data
public class BeanMethod {
    private Object bean;
    private Method method;
}
