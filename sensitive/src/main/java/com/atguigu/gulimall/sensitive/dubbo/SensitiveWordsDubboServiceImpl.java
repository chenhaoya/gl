package com.atguigu.gulimall.sensitive.dubbo;



import com.atguigu.gulimall.sensitive.service.SensitiveWordsService;
import com.atguigu.gulimall.sensitive.service.SensitiveWordsUtilService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 86151
 */
@Component
public class SensitiveWordsDubboServiceImpl implements SensitiveWordsDubboService {

    @Resource
    private SensitiveWordsService sensitiveWordsService;

    @Resource
    private SensitiveWordsUtilService sensitiveWordsUtilService;

    @Override
    public SensitiveWordsDO checkSensitiveWordsAndReplace(String content) {
        return sensitiveWordsService.checkIfContainSensitive(content);
    }

    @Override
    public Boolean checkIfContainSensitiveWords(String content) {
        return sensitiveWordsUtilService.contains(content);
    }
}
