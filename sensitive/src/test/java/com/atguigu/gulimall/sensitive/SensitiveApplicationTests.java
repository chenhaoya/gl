package com.atguigu.gulimall.sensitive;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;


@SpringBootTest
class SensitiveApplicationTests {

    @Test
    void contextLoads() {
        try {
            StringReader sr = new StringReader("你是个大傻逼还是个大傻狗");
            IKSegmenter ik = new IKSegmenter(sr,true);
            Lexeme lex = null;
            while ((lex=ik.next())!=null){
                System.out.print(lex.getLexemeText()+" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
