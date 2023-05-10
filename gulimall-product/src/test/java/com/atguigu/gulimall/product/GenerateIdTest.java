package com.atguigu.gulimall.product;

import com.atguigu.gulimall.product.horseIdService.manager.CosIdManager;
import com.atguigu.gulimall.product.horseIdService.model.entity.SegmentIdDO;
import com.atguigu.gulimall.product.horseIdService.segment.IdGenerator;
import com.atguigu.gulimall.product.horseIdService.segment.SegmentIdGenerator;
import com.atguigu.gulimall.product.horseIdService.service.CosIdSegmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author ChenHao
 * @date 2023/5/10 22:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GenerateIdTest {

    @Resource
    private IdGenerator idGenerator;

    @Test
    public void testId() {
        Long longCosId = idGenerator.getLongCosId("order");
        System.out.println(longCosId);
    }
}