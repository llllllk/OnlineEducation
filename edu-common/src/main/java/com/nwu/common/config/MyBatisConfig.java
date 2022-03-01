package com.nwu.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {
    @Bean  //分页
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
