package com.example.spring.reference;

import com.example.IRegistryService;
import com.example.constants.ReqType;
import com.example.constants.RpcConstant;
import com.example.constants.SerialType;
import com.example.core.*;
import com.example.protocol.NettyClient;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Slf4j
public class RpcInvokerProxy implements InvocationHandler {

   /* private String serviceAddress;
    private int servicePort;*/

    IRegistryService registryService;

    public RpcInvokerProxy(IRegistryService registryService) {
       /* this.serviceAddress = serviceAddress;
        this.servicePort = servicePort;*/
        this.registryService=registryService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("begin invoke target server");
        //组装参数
        RpcProtocol<RpcRequest> protocol=new RpcProtocol<>();
        long requestId= RequestHolder.REQUEST_ID.incrementAndGet();
        Header header=new Header(RpcConstant.MAGIC, SerialType.JSON_SERIAL.code(), ReqType.REQUEST.code(),requestId,0);
        protocol.setHeader(header);
        RpcRequest request=new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParams(args);
        protocol.setContent(request);
        //发送请求
        NettyClient nettyClient=new NettyClient();
        //构建异步数据处理
        RpcFuture<RpcResponse> future=new RpcFuture<>(new DefaultPromise<>(new DefaultEventLoop()));
        RequestHolder.REQUEST_MAP.put(requestId,future);
        nettyClient.sendRequest(protocol,this.registryService);
        return future.getPromise().get().getData();
    }
}
