package com.qiu.paper_management.service;

import com.qiu.paper_management.pojo.Comment;

import java.util.List;

public interface OtherService {
    void uploadFile(Integer id, String url);

    // 找出原先文件在服务器上的pdf名字，注意只需要名字!
    String findObjById(Integer id);

    void postComment(Comment comment);


    List<Comment> getComment(Integer articleId, Integer categoryId, boolean timeOrder);


    void deleteComment(Integer commentId);

    Comment getCommentById(Integer commentId, Integer userId);

    void likeComment(Integer commentId);
}
