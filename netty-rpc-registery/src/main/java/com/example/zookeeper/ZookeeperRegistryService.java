package com.example.zookeeper;

import com.example.IRegistryService;
import com.example.ServiceInfo;
import com.example.loadbalance.ILoadBalance;
import com.example.loadbalance.RandomLoadBalance;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.util.Collection;
import java.util.List;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Slf4j
public class ZookeeperRegistryService implements IRegistryService {

    private static final String REGISTRY_PATH="/registry";
    //Curator中提供的服务注册与发现的组件封装，它对此抽象出了ServiceInstance、
    // ServiceProvider、ServiceDiscovery三个接口，通过它我们可以很轻易的实现Service Discovery
    private final ServiceDiscovery<ServiceInfo> serviceDiscovery;

    private ILoadBalance<ServiceInstance<ServiceInfo>> loadBalance;

    public ZookeeperRegistryService(String registryAddress) throws Exception {
        CuratorFramework client= CuratorFrameworkFactory
                .newClient(registryAddress,new ExponentialBackoffRetry(1000,3));
        client.start();
        JsonInstanceSerializer<ServiceInfo> serializer=new JsonInstanceSerializer<>(ServiceInfo.class);
        this.serviceDiscovery= ServiceDiscoveryBuilder.builder(ServiceInfo.class)
                .client(client)
                .serializer(serializer)
                .basePath(REGISTRY_PATH)
                .build();
        this.serviceDiscovery.start();
        loadBalance=new RandomLoadBalance();
    }

    @Override
    public void register(ServiceInfo serviceInfo) throws Exception {
        log.info("开始注册服务，{}",serviceInfo);
        ServiceInstance<ServiceInfo> serviceInstance=ServiceInstance
                .<ServiceInfo>builder().name(serviceInfo.getServiceName())
                .address(serviceInfo.getServiceAddress())
                .port(serviceInfo.getServicePort())
                .payload(serviceInfo)
                .build();
        serviceDiscovery.registerService(serviceInstance);
    }

    @Override
    public void unRegister(ServiceInfo serviceInfo) throws Exception {
        ServiceInstance<ServiceInfo> serviceInstance=ServiceInstance.<ServiceInfo>builder()
                .name(serviceInfo.getServiceName())
                .address(serviceInfo.getServiceAddress())
                .port(serviceInfo.getServicePort())
                .payload(serviceInfo)
                .build();
        serviceDiscovery.unregisterService(serviceInstance);
    }

    @Override
    public ServiceInfo discovery(String serviceName) throws Exception {
        Collection<ServiceInstance<ServiceInfo>> serviceInstances= serviceDiscovery
                .queryForInstances(serviceName);
        //通过负载均衡返回某个具体实例
        ServiceInstance<ServiceInfo> serviceInstance=loadBalance.select((List<ServiceInstance<ServiceInfo>>)serviceInstances);
        if(serviceInstance!=null){
            return serviceInstance.getPayload();
        }
        return null;
    }
}
