package com.example.spring.service;

import com.example.IRegistryService;
import com.example.RegistryFactory;
import com.example.RegistryType;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Configuration
@EnableConfigurationProperties(RpcServerProperties.class)
public class RpcProviderAutoConfiguration {

    @Bean
    public SpringRpcProviderBean rpcProviderBean(RpcServerProperties rpcServerProperties) throws UnknownHostException {
        //添加注册中心
        IRegistryService registryService=RegistryFactory.createRegistryService(rpcServerProperties.getRegistryAddress(), RegistryType.findByCode(rpcServerProperties.getRegisterType()));
        return new SpringRpcProviderBean(rpcServerProperties.getServicePort(),registryService);
    }
}
