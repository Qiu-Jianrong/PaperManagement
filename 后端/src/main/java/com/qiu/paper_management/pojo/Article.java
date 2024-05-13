package com.qiu.paper_management.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Article {
    private Integer id;//主键ID
    @NotEmpty
    private String title;//文章标题
    private String paperAbstract;
    private String keywords;
    @URL
    private String paperPdf;
    private String content;//文章内容
    private String state;//发布状态 已发布|草稿
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
    private float score;
    private Integer scoreAmount;// 打分人数

    // 应该可以额外定义属性，吧？
    private List<Integer> categoryId;
}
