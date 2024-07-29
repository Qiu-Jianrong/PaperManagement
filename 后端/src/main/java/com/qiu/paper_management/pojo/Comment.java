package com.qiu.paper_management.pojo;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Data
public class Comment {
    private Integer commentId;
    private Integer articleId;
    private Integer categoryId;
    private Integer criticId;
    private String criticEmail;
    private String criticFace;
    private String content;
    private LocalDateTime updateTime;
    private Integer parentId;
    @Range(min = 0, max = 5)
    private float score;// 评论的同时要求用户评分
    private Integer likes;// 点赞数
}
