package com.nwu.course;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.nwu.course.mapper")
@ComponentScan("com.nwu")
public class CourseApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseApplication.class,args);
    }
}
