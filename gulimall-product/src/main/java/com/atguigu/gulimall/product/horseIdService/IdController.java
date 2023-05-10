package com.atguigu.gulimall.product.horseIdService;

import com.atguigu.gulimall.product.horseIdService.segment.IdGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ChenHao
 * @date 2023/5/10 23:23
 */
@RestController
public class IdController {

    @Resource
    private IdGenerator idGenerator;

    @GetMapping("/getId")
    public String getId(@RequestParam("id") String id) {
        Long longCosId = idGenerator.getLongCosId(id);
        return longCosId.toString();
    }

}