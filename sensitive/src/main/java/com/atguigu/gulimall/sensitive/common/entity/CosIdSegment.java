package com.atguigu.gulimall.sensitive.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author liao
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("cosid_segment")
public class CosIdSegment extends BaseEntity {

    @TableId
    private Long id;
    private String businessId;
    private Long autoIncrement;
    @TableField(fill = FieldFill.INSERT)
    private Boolean isDeleted;
}
