package com.qiu.paper_management.utils;

import com.huaban.analysis.jieba.JiebaSegmenter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JiebaUtilTest {

    @Autowired
    private JiebaUtil jieba;
    @Test
    void parse() {
        String q = "这个意大利面就应该拌42号混凝土，因为这个螺丝钉的长度会严重影响到挖掘机的扭矩...";
        System.out.println(jieba.parse(q));

    }
}