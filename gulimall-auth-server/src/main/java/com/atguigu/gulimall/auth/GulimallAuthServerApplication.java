package com.atguigu.gulimall.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


/**
 * 核心原理
 * 1）、@EnableRedisHttpSession导入RedisHttpSessionConfiguration配置
 *      1、给容器中添加了一个组件
 *          SessionRepository   ===》  RedisOperationsSessionRepository：Redis操作session，session的增删改查封装类
 *      2. springSessionRepositoryFilter ==》Filter  session存储过滤器  每个请求都会经过这个filter
 *          1、创建的时候，就从容器种获取到SessionRepository
 *          2、在OncePerRequestFilter.java 过滤器中包装原生request、response。
 *              那么在Controller中通过 HttpServletRequest .getSession 中获取的就是被包装后的session(SessionRepositoryRequestWrapper)
 *          3、SessionRepositoryRequestWrapper.getSession() ====>  SessionRepositoryFilter.this.sessionRepository.findById(sessionId);(SessionRepository<S>中获取到的)
 *                  就是1放置的  RedisOperationsSessionRepository
 *
 *      装饰着模式
 */

@EnableRedisHttpSession     //整合Redis作为session存储
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class GulimallAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallAuthServerApplication.class, args);
    }

}
