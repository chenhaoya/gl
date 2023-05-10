package com.atguigu.gulimall.product.horseIdService.manager;

import com.atguigu.gulimall.product.horseIdService.model.entity.CosIdSegment;
import com.atguigu.gulimall.product.horseIdService.model.entity.SegmentIdDO;
import com.atguigu.gulimall.product.horseIdService.model.result.BizAssert;
import com.atguigu.gulimall.product.horseIdService.service.CosIdSegmentService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author liao
 */
@Component
public class CosIdManager {

    @Resource
    private CosIdSegmentService cosIdSegmentService;

    @Transactional(rollbackFor = Exception.class)
    public SegmentIdDO getSegment(String businessId, Long segmentCount) {
        CosIdSegment cosIdSegment = initBusinessSegment(businessId);
        Boolean result = cosIdSegmentService.getBaseMapper().updateCosIdSegmentAutoIncrement(cosIdSegment.getId(), segmentCount);
        BizAssert.isTrue(result, "获取号段异常");
        cosIdSegment = cosIdSegmentService.getById(cosIdSegment.getId());
        return new SegmentIdDO()
                .setStartId(cosIdSegment.getAutoIncrement() - segmentCount)
                .setEndId(cosIdSegment.getAutoIncrement())
                .setBusinessId(businessId);
    }

    public CosIdSegment initBusinessSegment(String businessId) {
        return cosIdSegmentService.saveOrUpdate(businessId);
    }
}
