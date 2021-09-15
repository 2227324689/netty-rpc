package com.example.loadbalance;

import com.example.ServiceInfo;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;
import java.util.Random;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class RandomLoadBalance extends AbstractLoadBanalce {
    @Override
    protected ServiceInstance<ServiceInfo> doSelect(List<ServiceInstance<ServiceInfo>> servers) {
        int length=servers.size();
        Random random=new Random();
        return servers.get(random.nextInt(length));
    }
}
