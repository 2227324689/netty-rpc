# 设计思想

基于Dubbo+Zookeeper的设计理念，实现一个简单版本的RPC框架

![NettyRpc-design](https://user-images.githubusercontent.com/52684867/142019353-23ac808d-ff82-4820-b6db-2fb16cff6989.png)


# 使用技术

基于Netty4.x版本实现RPC框架，主要包含的功能点

* RPC通信
* 负载均衡
* 反射
* Spring 扩展
* 基于Zookeeper注册中心
* 基于注解实现服务发布和远程服务引用

# 为什么要写?

平时我们在使用Dubbo或者一些其他的RPC框架时，并没有关心RPC框架底层的实现，仅仅停留在使用层面。
但是作为一个Java高级开发，必须要对底层实现有一定的了解，所以基于Netty实现了一个自己的RPC框架。
希望能够帮助大家更好的理解Netty以及RPC的原理。

# 项目结构说明

> 下面这两个项目，表示服务提供者，用来演示服务提供者和服务消费者通信的一个演示案例。

1. netty-rpc-api  ， 对外提供的公共契约

2. netty-rpc-provider ， 服务提供者接口的实现

3. netty-rpc-consumer， 服务消费者

provider发布服务，consumer通过自定义rpc协议实现和provider的通信。

> 下面两个项目模块是RPC的实现，netty-rpc-provider和netty-rpc-consumer分别依赖它来实现远程通信

1. netty-rpc-protocol ， Netty实现RPC框架的核心库
2. netty-rpc-registry ， 服务注册与发现


# 使用方法

## 发布远程服务

使用`@GpRemoteService`注解，表示发布一个具体的远程服务。

```java
@GpRemoteService //表示讲当前服务发布成远程服务
@Slf4j
public class UserServiceImpl implements IUserService {
    @Override
    public String saveUser(String name) {
        log.info("begin saveUser:"+name);
        return "Save User Success!";
    }
}
```
## 消费远程服务

消费服务，使用`@GpRemoteReference`注解，表示注入一个远程服务代理对象。

```java
@RestController
public class HelloController {

    @GpRemoteReference
    private IUserService userService;


    @GetMapping("/test")
    public String test(){
        return userService.saveUser("Mic");
    }

}

```

# 实现教程


# 联系方式

* 微信： mic4096

* 个人博客： https://istio.tech

* 微信公众号：

<img src="https://user-images.githubusercontent.com/52684867/142021673-5de16ad4-50f4-479b-8905-08f436cccebd.jpg" style="width:200px"/>


# 个人作品

1. 2020年出版[《Spring Cloud Alibaba微服务原理与实战》](https://item.jd.com/12848452.html)

![](https://mic-blob-bucket.oss-cn-beijing.aliyuncs.com/c11d945cd9351817.jpg)

2. 2021年出版[《Java并发编程深度解析与实战》](https://item.jd.com/12971665.html)

![](https://mic-blob-bucket.oss-cn-beijing.aliyuncs.com/5c9303318a52c860.jpg)
