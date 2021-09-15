package com.example;

import com.example.protocol.NettyServer;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@ComponentScan(basePackages = {"com.example.spring.annotation","com.example.spring.service","com.example.service"})
@SpringBootApplication
public class NettyRpcProviderMain {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(NettyRpcProviderMain.class, args);
    }
}
