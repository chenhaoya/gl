package com.atguigu.gulimall.sensitive.controller;


import com.atguigu.gulimall.sensitive.model.dto.SensitiveWordsModifyDTO;
import com.atguigu.gulimall.sensitive.model.dto.SensitiveWordsQueryDTO;
import com.atguigu.gulimall.sensitive.model.vo.SensitiveWordsVO;
import com.atguigu.gulimall.sensitive.service.SensitiveWordsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 敏感词库(SensitiveWords)表控制层
 *
 * @author shumengjiao
 * @since 2022-06-15 15:14:08
 */
@Api(tags = "后管-敏感词")
@RestController
@RequestMapping("admin/sensitiveWords")
public class SensitiveWordsController {
    /**
     * 服务对象
     */
    @Resource
    private SensitiveWordsService sensitiveWordsService;

    @ApiOperation("保存敏感词")
    @PostMapping
    private Boolean save(@RequestBody SensitiveWordsModifyDTO sensitiveWordsModifyDTO) {
        return sensitiveWordsService.save(sensitiveWordsModifyDTO);
    }

    @ApiOperation("分页查询敏感词")
    @GetMapping
    public IPage<SensitiveWordsVO> page(SensitiveWordsQueryDTO sensitiveWordsQueryDTO) {
        return sensitiveWordsService.page(sensitiveWordsQueryDTO);
    }

    @ApiOperation("更新敏感词")
    @PutMapping
    public Boolean update(SensitiveWordsModifyDTO sensitiveWordsModifyDTO) {
        return sensitiveWordsService.update(sensitiveWordsModifyDTO);
    }

}

