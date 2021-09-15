package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@ComponentScan(basePackages = {"com.example.spring.annotation","com.example.controller","com.example.spring.reference"})
@SpringBootApplication
public class NettyConsumerMain {
    public static void main(String[] args) {
        SpringApplication.run(NettyConsumerMain.class, args);
    }
}
