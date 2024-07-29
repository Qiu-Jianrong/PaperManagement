package com.qiu.paper_management.service.Impl;

import com.qiu.paper_management.mapper.ArticleMapper;
import com.qiu.paper_management.mapper.CategoryMapper;
import com.qiu.paper_management.mapper.OtherMapper;
import com.qiu.paper_management.pojo.Article;
import com.qiu.paper_management.pojo.Category;
import com.qiu.paper_management.pojo.Comment;
import com.qiu.paper_management.service.OtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OtherServiceImpl implements OtherService {
    @Autowired
    OtherMapper otherMapper;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public void uploadFile(Integer id, String url) {
        otherMapper.uploadFile(id, url);
    }

    @Override
    public String findObjById(Integer id) {
        String url = otherMapper.findObjById(id);
        return url.substring(url.lastIndexOf("/") + 1);
    }

    @Override
    public void postComment(Comment comment) {
//        System.out.println(comment);
        otherMapper.postComment(comment);
        // 对应地更新得分，\bar S = \bar S + (S - \bar S)/(score_amount + 1)
        if(comment.getArticleId() > 0){
            Article article = articleMapper.findById(comment.getArticleId());
            float score = (comment.getScore() - article.getScore()) / (article.getScoreAmount() + 1) + article.getScore();
            article.setScore(score);
            article.setScoreAmount(article.getScoreAmount() + 1);
            articleMapper.update(article);
        }
        else{
            Category category = categoryMapper.findCategoryById(comment.getCategoryId());
            float score = (comment.getScore() - category.getScore()) / (category.getScoreAmount() + 1) + category.getScore();
            category.setScore(score);
            category.setScoreAmount(category.getScoreAmount() + 1);
            categoryMapper.updateCategory(category);
        }

    }

    @Override
    public List<Comment> getComment(Integer articleId, Integer categoryId, boolean timeOrder) {
        return otherMapper.getComment(articleId, categoryId, timeOrder);
    }

    @Override
    public void deleteComment(Integer commentId) {
        otherMapper.deleteComment(commentId);
    }

    @Override
    public Comment getCommentById(Integer commentId, Integer userId) {
        return otherMapper.getCommentById(commentId, userId);
    }

    @Override
    public void likeComment(Integer commentId) {
        otherMapper.likeComment(commentId, otherMapper.getCommentById(commentId, null).getLikes()+1);
    }


}
