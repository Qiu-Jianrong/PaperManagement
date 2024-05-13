package com.qiu.paper_management.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    private Integer commentId;
    private Integer articleId;
    private Integer categoryId;
    private Integer criticId;
    private String criticEmail;
    private String content;
    private LocalDateTime updateTime;
    private Integer parentId;
    private float score;
}
