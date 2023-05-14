package com.atguigu.gulimall.product.horseIdService.segment;

import com.atguigu.gulimall.product.horseIdService.model.entity.SegmentIdDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liao
 */
@Slf4j
@Component
public class BusinessCosIdSegmentCache {
    /**
     * 业务号段map，号段采用链表形式往下面接
     */
    private final Map<String, BusinessCosIdSegmentChain> idGeneratorAutoIncrement = new ConcurrentHashMap<>();

    /**
     * 默认安全距离
     */
    private static final long DEFAULT_SAFE_DISTANCE = 5L;

    /**
     * 默认预取号段数量
     */
    private static final long DEFAULT_PREFETCHING_COUNT = 100;

    /**
     * 饥饿阈值，单位秒
     */
    private static final int HUNGER_THRESHOLD = 30;

    private static final long MAX_PREFETCH_DISTANCE = Long.MAX_VALUE;

    @Resource
    private IdGeneratorDubboService idGeneratorDubboService;

    /**
     * 初始化，并获取号段链
     *
     * @param businessId 业务标识
     * @return 号段链
     */
    public BusinessCosIdSegmentChain getBusinessCosIdGenerator(String businessId) {
        return idGeneratorAutoIncrement.computeIfAbsent(businessId, s ->
                new BusinessCosIdSegmentChain(businessId, DEFAULT_SAFE_DISTANCE, DEFAULT_PREFETCHING_COUNT, this::generatorSegmentChain));
    }

    /**
     * 生成号段链
     */
    public BusinessCosIdSegmentChain.BusinessCosIdGenerator generatorSegmentChain(BusinessCosIdSegmentChain businessCosIdSegmentChain) {

        String businessId = businessCosIdSegmentChain.getBusinessId();

        LocalDateTime putSegmentChainTime = businessCosIdSegmentChain.getPutSegmentChainTime();

        LocalDateTime hungerTime = businessCosIdSegmentChain.getHungerTime();

        long prefetchingCount;

        // 当前与上次取id的时间间隔
        long wakeupTimeGap = Optional.ofNullable(putSegmentChainTime)
                .map(localDateTime -> Duration.between(putSegmentChainTime, hungerTime).toMillis() / 1000)
                .orElse((long) HUNGER_THRESHOLD);

        // 更具间隔判断是否需要对本次预取id数量扩容
        if (wakeupTimeGap < HUNGER_THRESHOLD) {
            log.info("饥饿了");
            // *2
            prefetchingCount = Math.min(Math.multiplyExact(businessCosIdSegmentChain.getLastPrefetchingCount(), Math.max(Math.floorDiv(HUNGER_THRESHOLD, Math.max(wakeupTimeGap, 1)), 2)), MAX_PREFETCH_DISTANCE);
        } else {
            log.info("不饿");
            prefetchingCount = Math.max(Math.floorDiv(businessCosIdSegmentChain.getLastPrefetchingCount(), 2), businessCosIdSegmentChain.getSafeDistance());
        }

        log.info("本次预取号段 {}", prefetchingCount);
        businessCosIdSegmentChain.setLastPrefetchingCount(prefetchingCount);
        businessCosIdSegmentChain.setSafeDistance(Math.max(Math.floorDiv(prefetchingCount, 2), DEFAULT_SAFE_DISTANCE));

        SegmentIdDO segmentIdDO = idGeneratorDubboService.generateSegmentId(businessId, prefetchingCount);

        return new BusinessCosIdSegmentChain.BusinessCosIdGenerator(segmentIdDO.getBusinessId(), segmentIdDO.getStartId(), segmentIdDO.getEndId());
    }
}
