package com.atguigu.gulimall.product.horseIdService.service;

import com.atguigu.gulimall.product.dao.CosIdSegmentMapper;
import com.atguigu.gulimall.product.horseIdService.model.constant.RedisKeysConstants;
import com.atguigu.gulimall.product.horseIdService.model.entity.CosIdSegment;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * @author liao
 */
@Slf4j
@Service
public class CosIdSegmentService extends ServiceImpl<CosIdSegmentMapper, CosIdSegment> {

    @Resource
    private Redisson redisson;

    public CosIdSegment saveOrUpdate(String businessId) {
        CosIdSegment cosIdSegment = this.lambdaQuery().eq(CosIdSegment::getBusinessId, businessId).one();
        if (cosIdSegment != null) {
            return cosIdSegment;
        }
        RLock lock = redisson.getLock(RedisKeysConstants.INIT_BUSINESS_COS_ID_LOCK.concat(businessId));
        try {
            lock.tryLock(20, 20, TimeUnit.MINUTES);
            cosIdSegment = this.lambdaQuery().eq(CosIdSegment::getBusinessId, businessId).one();
            if (Objects.isNull(cosIdSegment)) {
                cosIdSegment = new CosIdSegment()
                        .setBusinessId(businessId)
                        .setAutoIncrement(1L)
                        .setIsDeleted(false);
                cosIdSegment.setCreateTime(LocalDateTime.now());
                this.save(cosIdSegment);
            }
            return cosIdSegment;
        } catch (Exception e) {
            log.error("获取号段失败", e);
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
        return null;
    }


}
