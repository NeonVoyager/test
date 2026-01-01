package com.oneblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * OneBlog 博客系统启动类
 */
@SpringBootApplication
@MapperScan("com.oneblog.mapper")
@EnableAsync
@EnableScheduling
public class OneBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(OneBlogApplication.class, args);
    }
}

