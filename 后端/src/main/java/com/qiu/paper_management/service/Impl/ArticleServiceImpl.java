package com.qiu.paper_management.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qiu.paper_management.mapper.ArticleMapper;
import com.qiu.paper_management.mapper.UserMapper;
import com.qiu.paper_management.pojo.*;
import com.qiu.paper_management.service.ArticleService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;

    // 新增文章（可以是直接发布或者草稿）
    @Override
    public void postArticle(Article_Author articleWithAuthor) {
        Article article = articleWithAuthor.getArticle();
        List<String> leadAuthors = articleWithAuthor.getLeadAuthors();
        List<String> correspondingAuthors = articleWithAuthor.getCorrespondingAuthors();
        List<String> otherAuthors = articleWithAuthor.getOtherAuthors();

        articleMapper.addArticle(article);
        Integer articleId = articleMapper.findIdByTitle(article.getTitle());

        // 加入第一作者
        for(String leadAuthor: leadAuthors){
            User author = userMapper.findByUsername(leadAuthor);
            articleMapper.addArticleAuthor(articleId, author.getId(), true, false);
        }
        // 加入通讯作者
        for (String correspondingAuthor:correspondingAuthors){
            User author = userMapper.findByUsername(correspondingAuthor);
            articleMapper.addArticleAuthor(articleId, author.getId(), false, true);
        }
        // 加入其他作者
        for (String otherAuthor:otherAuthors){
            User author = userMapper.findByUsername(otherAuthor);
            articleMapper.addArticleAuthor(articleId, author.getId(), false, false);
        }

    }

    @Override
    public List<Integer> findAuthorsById(Integer articleId){
        return articleMapper.findAuthorsById(articleId);
    }
    @Override
    public boolean duplicate(String title) {
        return articleMapper.findIdByTitle(title) != null;
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId) {
        // 1. 创建pageBean对象
        PageBean pb = new PageBean<Article>();

        // 2. 分页查询start，将自动拼接pn与ps
        PageHelper.startPage(pageNum, pageSize);

        // 3. 开启mapper查询
        List<Article> as =  articleMapper.list(categoryId);
        Page<Article> articles = (Page<Article>) as;

        pb.setTotal(articles.getTotal());
        pb.setItems(articles.getResult());
        return pb;
    }

    @Override
    public Article getDetail(Integer id) {
        Article article = articleMapper.findById(id);
        if (article == null)
            return null;
        article.setCategoryId(articleMapper.findArticleCategory(id));
        return article;
    }

    @Override
    public void deleteArticle(Integer id) {
        articleMapper.deleteArticle(id);
    }

}
