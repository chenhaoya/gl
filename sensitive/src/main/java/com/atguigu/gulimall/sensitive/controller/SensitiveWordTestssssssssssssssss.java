package com.atguigu.gulimall.sensitive.controller;

import com.atguigu.gulimall.sensitive.dubbo.SensitiveWordsDO;
import com.atguigu.gulimall.sensitive.dubbo.SensitiveWordsDubboService;
import com.atguigu.gulimall.sensitive.dubbo.SensitiveWordsDubboServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ChenHao
 * @date 2023/5/11 21:54
 */
@Controller
public class SensitiveWordTestssssssssssssssss {

    @Resource
    private SensitiveWordsDubboServiceImpl service;

    @ResponseBody
    @GetMapping("/aaa")
    public String test(@RequestParam("text") String text) {
        SensitiveWordsDO sensitiveWordsDO = service.checkSensitiveWordsAndReplace(text);
        return sensitiveWordsDO.getContent();
    }
}