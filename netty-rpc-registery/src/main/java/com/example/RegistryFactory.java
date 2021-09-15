package com.example;

import com.example.zookeeper.ZookeeperRegistryService;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class RegistryFactory {


    public static IRegistryService createRegistryService(String address,RegistryType registryType){
        IRegistryService registryService=null;
        try {
            switch (registryType) {
                case ZOOKEEPER:
                    registryService = new ZookeeperRegistryService(address);
                    break;
                case EUREKA:
                    //TODO
                    break;
                default:
                    registryService = new ZookeeperRegistryService(address);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return registryService;
    }
}
