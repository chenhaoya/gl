package com.atguigu.gulimall.sensitive.mapper;


import com.atguigu.gulimall.sensitive.model.entity.SensitiveWords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * 敏感词库(SensitiveWords)表数据库访问层
 *
 * @author shumengjiao
 * @since 2022-06-15 15:14:09
 */
@Mapper
public interface SensitiveWordsMapper extends BaseMapper<SensitiveWords> {

}

