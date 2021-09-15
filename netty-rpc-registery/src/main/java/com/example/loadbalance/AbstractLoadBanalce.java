package com.example.loadbalance;

import com.example.ServiceInfo;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public abstract class AbstractLoadBanalce implements ILoadBalance<ServiceInstance<ServiceInfo>> {

    @Override
    public ServiceInstance<ServiceInfo> select(List<ServiceInstance<ServiceInfo>> servers){
        if(servers==null||servers.size()==0){
            return null;
        }
        if(servers.size()==1){
            return servers.get(0);
        }
        return doSelect(servers);
    }

    protected abstract ServiceInstance<ServiceInfo> doSelect(List<ServiceInstance<ServiceInfo>> servers);
}
