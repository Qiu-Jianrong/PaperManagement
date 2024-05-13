package com.qiu.paper_management.service;

import com.qiu.paper_management.pojo.Article;
import com.qiu.paper_management.pojo.Article_Author;
import com.qiu.paper_management.pojo.Article_Category;
import com.qiu.paper_management.pojo.PageBean;

import java.util.List;

public interface ArticleService {
    void postArticle(Article_Author articleWithAuthor);


    boolean duplicate(String title);

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId);

    Article getDetail(Integer id);

    void deleteArticle(Integer id);
    List<Integer> findAuthorsById(Integer articleId);
}
