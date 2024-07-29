package com.qiu.paper_management.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qiu.paper_management.mapper.ArticleMapper;
import com.qiu.paper_management.mapper.UserMapper;
import com.qiu.paper_management.pojo.*;
import com.qiu.paper_management.service.ArticleService;
import com.qiu.paper_management.utils.JiebaUtil;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JiebaUtil jiebaUtil;

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
    public void update(Article article) {
        articleMapper.update(article);
    }

    @Override
    public List<String> getLeaderByArticleId(Integer id) {
        List<Integer> leaders = articleMapper.getLeaderByArticleId(id);
        List<String> leadersName = new ArrayList<>();
        for (Integer leaderId : leaders){
            String LeaderId = articleMapper.getAuthorNameById(leaderId);
            leadersName.add(LeaderId);
        }
        return leadersName;
    }

    @Override
    public List<String> getCorrByArticleId(Integer id) {
        List<Integer> coors = articleMapper.getCoorsByArticleId(id);
        List<String> usernames = new ArrayList<>();
        for (Integer userId : coors){
            String username = articleMapper.getAuthorNameById(userId);
            usernames.add(username);
        }
        return usernames;
    }

    @Override
    public List<String> getOthersByArticleId(Integer id) {
        List<Integer> others = articleMapper.getOthersByArticleId(id);
        List<String> usernames = new ArrayList<>();
        for (Integer userId : others){
            String username = articleMapper.getAuthorNameById(userId);
            usernames.add(username);
        }
        return usernames;
    }

    private void injectCategory(List<Article> articles){
        for (Article article : articles){
            article.setCategoryId(articleMapper.findArticleCategory(article.getId()));
        }
    }
    @Override
    public List<Article> listMine(Integer categoryId, Integer userId) {
        List<Integer> asId = articleMapper.findArticleByAuthor(userId, categoryId);

        List<Article> as =  new ArrayList<>();
        for (Integer articleId : asId){
            as.add(articleMapper.findById(articleId));
        }
        injectCategory(as);
        return as;
    }

    @Override
    public PageBean<Article> search(Integer pageNum, Integer pageSize, String q, Integer threshold, Integer categoryId, Integer userId) {
        // 1. 创建pageBean对象
        PageBean pb = new PageBean<Article>();

        // 2. 分页查询start，将自动拼接pn与ps
        PageHelper.startPage(pageNum, pageSize);

        // 3. 开启mapper查询
        q = jiebaUtil.parse(q);
        System.out.println(q);
        List<Article> as =  articleMapper.search(q, threshold, categoryId, userId);
//        for (Article article : as){
//            article.setCategoryId(articleMapper.findArticleCategory(article.getId()));
//        }
        injectCategory(as);
        Page<Article> articles = (Page<Article>) as;

        pb.setTotal(articles.getTotal());
        pb.setItems(articles.getResult());
        return pb;
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
//        for (Article article : as){
//            article.setCategoryId(articleMapper.findArticleCategory(article.getId()));
//        }
        injectCategory(as);
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
