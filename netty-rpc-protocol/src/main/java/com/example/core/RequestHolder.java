package com.example.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class RequestHolder {

    public static final AtomicLong REQUEST_ID=new AtomicLong();

    public static final Map<Long,RpcFuture> REQUEST_MAP=new ConcurrentHashMap<>();
}
