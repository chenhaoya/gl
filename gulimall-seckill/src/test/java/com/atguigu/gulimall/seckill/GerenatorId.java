package com.atguigu.gulimall.seckill;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.client.RedisClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author ChenHao
 * @date 2023/5/10 22:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GerenatorId {

    @Test
    public void idTest() {
//        String aa = redisTemplate.opsForValue().get("aa");
        System.out.println("aaaa");
    }
}