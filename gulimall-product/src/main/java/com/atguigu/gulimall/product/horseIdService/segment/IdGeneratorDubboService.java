package com.atguigu.gulimall.product.horseIdService.segment;


import com.atguigu.gulimall.product.horseIdService.model.entity.SegmentIdDO;

/**
 * @author liao
 */
public interface IdGeneratorDubboService {

    /**
     * 生成号段
     * @return 号段实体
     */
    SegmentIdDO generateSegmentId(String businessId, Long segmentCount);
}
