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

    void update(Article article);

    List<String> getLeaderByArticleId(Integer id);

    List<String> getCorrByArticleId(Integer id);

    List<String> getOthersByArticleId(Integer id);

    List<Article> listMine(Integer categoryId, Integer userId);

    PageBean<Article> search(Integer pageNum, Integer pageSize, String q, Integer threshold, Integer categoryId, Integer userId);
}
