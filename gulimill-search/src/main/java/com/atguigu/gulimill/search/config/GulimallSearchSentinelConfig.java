package com.atguigu.gulimill.search.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.alibaba.fastjson.JSON;
import com.atguigu.common.exception.BizCodeEnume;
import com.atguigu.common.utils.R;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GulimallSearchSentinelConfig {

    public GulimallSearchSentinelConfig() {

        WebCallbackManager.setUrlBlockHandler((request, response, ex) -> {
            R error = R.error(BizCodeEnume.TO_MANY_REQUEST.getCode(), BizCodeEnume.TO_MANY_REQUEST.getMsg());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(JSON.toJSONString(error));

        });

    }

}
