package com.qiu.paper_management.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Article_Author {
//    private Integer articleId;
//    private Integer authorId;
//    private boolean isLeader;
//    private boolean isCorresponding;
    private Article article;
    private List<String> leadAuthors;
    private List<String> correspondingAuthors;
    private List<String> otherAuthors;
}
