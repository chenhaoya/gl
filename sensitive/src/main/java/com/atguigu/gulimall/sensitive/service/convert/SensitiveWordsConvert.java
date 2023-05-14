package com.atguigu.gulimall.sensitive.service.convert;


import com.atguigu.gulimall.sensitive.model.dto.SensitiveWordsDTO;
import com.atguigu.gulimall.sensitive.model.dto.SensitiveWordsModifyDTO;
import com.atguigu.gulimall.sensitive.model.entity.SensitiveWords;
import com.atguigu.gulimall.sensitive.model.vo.SensitiveWordsVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 敏感词库(SensitiveWords)表服务接口
 *
 * @author shumengjiao
 * @since 2022-06-15 15:14:11
 */
@Mapper(componentModel = "spring")
public interface SensitiveWordsConvert {

    SensitiveWordsVO toVo(SensitiveWords sensitiveWords);

    List<SensitiveWordsVO> toVo(List<SensitiveWords> sensitiveWords);

    Page<SensitiveWordsVO> toVo(Page<SensitiveWords> sensitiveWords);

    SensitiveWords toEntity(SensitiveWordsDTO sensitiveWordsDTO);

    SensitiveWords toEntity(SensitiveWordsModifyDTO sensitiveWordsModifyDTO);
}

