package com.nwu.recommend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.nwu.recommend.mapper")
@ComponentScan({"com.nwu"})
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
public class RecommendApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecommendApplication.class,args);
    }
}
