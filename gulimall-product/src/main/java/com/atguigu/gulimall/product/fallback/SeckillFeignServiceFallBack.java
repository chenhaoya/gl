package com.atguigu.gulimall.product.fallback;

import com.atguigu.gulimall.product.feign.SecKillFeignService;
import com.atguigu.common.exception.BizCodeEnume;
import com.atguigu.common.utils.R;
import org.springframework.stereotype.Component;


@Component(value = "seckillFeignServiceFallBack")
public class SeckillFeignServiceFallBack implements SecKillFeignService {
    @Override
    public R getSkuSeckilInfo(Long skuId) {
        return R.error(BizCodeEnume.TO_MANY_REQUEST.getCode(),BizCodeEnume.TO_MANY_REQUEST.getMsg());
    }
}
