package com.atguigu.gulimall.product.horseIdService.segment;


import com.atguigu.gulimall.product.horseIdService.manager.CosIdManager;
import com.atguigu.gulimall.product.horseIdService.model.entity.SegmentIdDO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author liao
 */
@Component
public class IdGeneratorDubboServiceImpl implements IdGeneratorDubboService {

    @Resource
    private CosIdManager cosIdManager;

    @Override
    public SegmentIdDO generateSegmentId(String businessId, Long segmentCount) {
        return cosIdManager.getSegment(businessId, segmentCount);
    }
}
