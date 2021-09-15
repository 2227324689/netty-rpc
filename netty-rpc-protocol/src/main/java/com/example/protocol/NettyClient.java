package com.example.protocol;

import com.example.IRegistryService;
import com.example.ServiceInfo;
import com.example.core.RpcProtocol;
import com.example.core.RpcRequest;
import com.example.handler.RpcClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Slf4j
public class NettyClient {
    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
   /* private String serviceAddress;
    private int servicePort;*/
    public NettyClient(){
        log.info("begin init NettyClient");
        bootstrap=new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new RpcClientInitializer());
       /* this.serviceAddress=serviceAddress;
        this.servicePort=servicePort;*/
    }

    public void sendRequest(RpcProtocol<RpcRequest> protocol, IRegistryService registryService) throws Exception {
        ServiceInfo serviceInfo=registryService.discovery(protocol.getContent().getClassName());
        ChannelFuture future=bootstrap.connect(serviceInfo.getServiceAddress(),serviceInfo.getServicePort()).sync();
        future.addListener(listener->{
            if(future.isSuccess()){
                log.info("connect rpc server {} success.",serviceInfo.getServiceAddress());
            }else{
                log.error("connect rpc server {} failed .",serviceInfo.getServiceAddress());
                future.cause().printStackTrace();
                eventLoopGroup.shutdownGracefully();
            }
        });
        log.info("begin transfer data");
        future.channel().writeAndFlush(protocol);
    }
}
