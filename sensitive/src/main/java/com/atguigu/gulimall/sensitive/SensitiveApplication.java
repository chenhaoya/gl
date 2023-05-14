package com.atguigu.gulimall.sensitive;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.atguigu.gulimall.sensitive.**")
public class SensitiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(SensitiveApplication.class, args);
    }

}
