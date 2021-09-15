package com.example.spring.service;

import com.example.IRegistryService;
import com.example.ServiceInfo;
import com.example.protocol.NettyServer;
import com.example.spring.annotation.GpRemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.registry.Registry;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Slf4j
public class SpringRpcProviderBean implements InitializingBean, BeanPostProcessor {

    private final int serverPort;
    private final String serverAddress;
    private final IRegistryService registryService; //修改部分,增加注册中心实现
    public SpringRpcProviderBean(int serverPort,IRegistryService registryService) throws UnknownHostException {
        this.serverPort = serverPort;
        InetAddress address=InetAddress.getLocalHost();
        this.serverAddress=address.getHostAddress();
        this.registryService=registryService; //修改部分,增加注册中心实现
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("begin deploy Netty Server to host {},on port {}",this.serverAddress,this.serverPort);
        new Thread(()->{
            try {
                new NettyServer(this.serverAddress,this.serverPort).startNettyServer();
            } catch (Exception e) {
                log.error("start Netty Server Occur Exception,",e);
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean.getClass().isAnnotationPresent(GpRemoteService.class)){ //针对存在该注解的服务进行发布
            Method[] methods=bean.getClass().getDeclaredMethods();
            for(Method method: methods){
                String serviceName=bean.getClass().getInterfaces()[0].getName();
                String key=serviceName+"."+method.getName();
                BeanMethod beanMethod=new BeanMethod();
                beanMethod.setBean(bean);
                beanMethod.setMethod(method);
                Mediator.beanMethodMap.put(key,beanMethod);
                try {
                    ServiceInfo serviceInfo = new ServiceInfo();
                    serviceInfo.setServiceAddress(this.serverAddress);
                    serviceInfo.setServicePort(this.serverPort);
                    serviceInfo.setServiceName(serviceName);
                    registryService.register(serviceInfo);
                }catch (Exception e){
                    log.error("register service {} faild",serviceName,e);
                }
            }
        }
        return bean;
    }
}
