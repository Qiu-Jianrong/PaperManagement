package com.qiu.paper_management.mapper;

import com.qiu.paper_management.pojo.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Mapper
public interface OtherMapper {
    @Update("update paper_management.article set paper_pdf=#{url}, update_time=now() where id=#{id}")
    void uploadFile(Integer id, String url);

    @Select("select paper_pdf from paper_management.article where id=#{id};")
    String findObjById(Integer id);

    @Insert("insert into paper_management.comment(article_id, category_id, critic_id, content, update_time, parent_id, score, likes) " +
            "VALUES (#{articleId}, #{categoryId}, #{criticId}, #{content}, now(), #{parentId}, #{score}, 0);")
    void postComment(Comment comment);


    List<Comment> getComment(Integer articleId, Integer categoryId, boolean timeOrder);


    @Delete("delete from paper_management.comment where comment_id=#{commentId}")
    void deleteComment(Integer commentId);


    Comment getCommentById(Integer commentId, Integer userId);

    @Update("update paper_management.comment set likes=#{likes} where comment_id=#{commentId}")
    void likeComment(Integer commentId, Integer likes);
}
