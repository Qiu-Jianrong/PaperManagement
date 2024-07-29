package com.qiu.paper_management.utils;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JiebaUtil {
    private JiebaSegmenter jieba = new JiebaSegmenter();
    public String parse(String q){
        // 使用搜索引擎分词，但是似乎只有python的
        List<SegToken> segList= jieba.process(q, JiebaSegmenter.SegMode.SEARCH);
        List<String> resList = new ArrayList<>();
        for (SegToken segToken : segList){
            resList.add(segToken.word);
        }
        return "%" + String.join("%", resList) + "%";
    }
}
