package com.example.handler;

import com.example.constants.ReqType;
import com.example.core.Header;
import com.example.core.RpcProtocol;
import com.example.core.RpcRequest;
import com.example.core.RpcResponse;
import com.example.spring.service.Mediator;
import com.example.spring.SpringBeansManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcRequest>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcRequest> msg) throws Exception {
        RpcProtocol resProtocol=new RpcProtocol<>();
        Header header=msg.getHeader();
        header.setReqType(ReqType.RESPONSE.code());
        Object result=Mediator.getInstance().processor(msg.getContent());
        resProtocol.setHeader(header);
        RpcResponse response=new RpcResponse();
        response.setData(result);
        response.setMsg("success");
        resProtocol.setContent(response);

        ctx.writeAndFlush(resProtocol);
    }

    @Deprecated
    private Object invoke(RpcRequest request){
        try {
            Class<?> clazz=Class.forName(request.getClassName());
            Object bean= SpringBeansManager.getBean(clazz); //获取实例对象
            Method declaredMethod=clazz.getDeclaredMethod(request.getMethodName(),request.getParameterTypes());
            return declaredMethod.invoke(bean,request.getParams());
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
