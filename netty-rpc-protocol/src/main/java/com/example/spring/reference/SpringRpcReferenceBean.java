package com.example.spring.reference;

import com.example.IRegistryService;
import com.example.RegistryFactory;
import com.example.RegistryType;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class SpringRpcReferenceBean implements FactoryBean<Object> {

    private Class<?> interfaceClass;
    private Object object;
   /* private String serviceAddress;
    private int servicePort;*/
    //修改增加注册中心
    private byte registryType;
    private String registryAddress;

    @Override
    public Object getObject() throws Exception {
        return object;
    }

    public void init(){
        //修改增加注册中心
        IRegistryService registryService= RegistryFactory.createRegistryService(this.registryAddress, RegistryType.findByCode(this.registryType));
        this.object= Proxy.newProxyInstance(this.interfaceClass.getClassLoader(),
                new Class<?>[]{this.interfaceClass},
                new RpcInvokerProxy(registryService));
    }

    @Override
    public Class<?> getObjectType() {
        return this.interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

   /* public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }*/

    public void setRegistryType(byte registryType) {
        this.registryType = registryType;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }
}
