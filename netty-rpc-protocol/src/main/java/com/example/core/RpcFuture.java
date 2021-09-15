package com.example.core;

import io.netty.util.concurrent.Promise;
import lombok.Data;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Data
public class RpcFuture<T> {
    //Promise是可写的 Future, Future自身并没有写操作相关的接口,
    // Netty通过 Promise对 Future进行扩展,用于设置IO操作的结果
    private Promise<T> promise;

    public RpcFuture(Promise<T> promise) {
        this.promise = promise;
    }
}
