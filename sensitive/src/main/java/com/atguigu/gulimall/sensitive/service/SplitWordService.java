package com.atguigu.gulimall.sensitive.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shumengjiao
 */
@Slf4j
@Service("splitWordService")
public class SplitWordService {

    /**
     * 对字符串进行分词
     * @param content 分词内容
     * @param useSmart 是否使用智能分词 true-使用只能分词 false-使用最细粒度分词
     * @return 分词结果
     * @throws Exception
     */
    public List<String> wordAnalyzer(String content, boolean useSmart){
        List<String> stringList = new ArrayList<>();
        try {
            StringReader sr = new StringReader(content);
            IKSegmenter ik = new IKSegmenter(sr,useSmart);
            Lexeme lex = null;
            while ((lex=ik.next())!=null){
                stringList.add(lex.getLexemeText());
//                System.out.print(lex.getLexemeText()+" ");
            }
        } catch (Exception e) {
            log.info("分词失败");
        }
        return stringList;
    }
}
