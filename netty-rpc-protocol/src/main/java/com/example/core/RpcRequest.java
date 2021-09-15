package com.example.core;

import lombok.Data;

import java.io.Serializable;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Data
public class RpcRequest implements Serializable {
    private String className;
    private String methodName;
    private Object[] params;
    private Class<?>[] parameterTypes;
}
