package com.qiu.paper_management.utils;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnsjUtil {

//    分词，只保留实义词，并且组织成 like ...的形式
    public static String parse(String s){
        //只关注这些词性的词
        Set<String> expectedNature = new HashSet<String>() {{
            add("n");add("v");add("a");
        }};

        Result result = BaseAnalysis.parse(s);
        List<Term> terms = result.getTerms();
        System.out.println(terms);
        List<String> res = new ArrayList<>();
        for (Term term : terms){
            if (expectedNature.contains(term.getNatureStr()))
                res.add(term.getName());
        }

        return "%" + String.join("%", res) + "%";
    }
}
