package com.atguigu.gulimall.product.dao;

import com.atguigu.gulimall.product.horseIdService.model.entity.CosIdSegment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author liao
 */
@Mapper
public interface CosIdSegmentMapper extends BaseMapper<CosIdSegment> {


    /**
     * 自增增加号段
     * @param id 号段id
     * @param segmentNumber 号段数量
     * @return
     */
    @Update("update cosid_segment set auto_increment = auto_increment + #{segmentCount} where id = #{id} ")
    Boolean updateCosIdSegmentAutoIncrement(@Param("id") Long id, @Param("segmentCount") Long segmentCount);
}
