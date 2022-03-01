package com.nwu.extra;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.nwu.extra.mapper")
@ComponentScan("com.nwu")
public class ExtraApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExtraApplication.class,args);
    }
}
