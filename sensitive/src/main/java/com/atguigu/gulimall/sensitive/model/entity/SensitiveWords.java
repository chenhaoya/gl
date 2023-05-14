package com.atguigu.gulimall.sensitive.model.entity;


import com.atguigu.gulimall.sensitive.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * 敏感词库(SensitiveWords)表实体类
 *
 * @author shumengjiao
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sensitive_words")
public class SensitiveWords extends BaseEntity implements Serializable {

    /**
     * 敏感词ID 
     */
    @TableId(type = IdType.AUTO)
    private Long id;
  
    /**
     * 内容 
     */
    private String content;
  
    /**
     * 类型（1-反动词库,2-广告,3-政治类,4-敏感词,5-暴恐词,6-民生词库,7-涉枪涉爆违法信息关键词,8-色情词,9-other,10-广告高危词） 
     */
    private Integer wordsType;
  
 
 
 
 
    /**
     * 删除状态（0未删除，1已删除） 
     */
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Integer isDeleted;
  
    /**
     * 使用状态（0禁用，1启用） 
     */
    private Integer enabled;
  
}

