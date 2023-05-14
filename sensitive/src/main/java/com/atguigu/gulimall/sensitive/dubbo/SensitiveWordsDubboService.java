package com.atguigu.gulimall.sensitive.dubbo;


import org.springframework.stereotype.Component;

/**
 * @author 86151
 */
public interface SensitiveWordsDubboService {

    /**
     * 检查敏感词并替换
     * @param content 内容
     * @return SensitiveWordsDO
     */
    SensitiveWordsDO checkSensitiveWordsAndReplace(String content);

    /**
     * 检查是否包含敏感词
     * @param content 内容
     * @return 是否包含
     */
    Boolean checkIfContainSensitiveWords(String content);

}
