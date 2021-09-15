package com.example.core;

import lombok.Data;

import java.io.Serializable;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Data
public class RpcProtocol<T> implements Serializable {
    private Header header;
    private T content;
}
