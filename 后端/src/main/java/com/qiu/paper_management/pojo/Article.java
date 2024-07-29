package com.qiu.paper_management.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Article {
    @NotNull(groups = Update.class, message = "文章Id缺失")
    private Integer id;//主键ID
    @NotEmpty(groups = {Add.class})
    private String title;//文章标题
    @NotEmpty(groups = {Add.class})
    private String paperAbstract;
    @NotEmpty(groups = {Add.class})
    private String keywords;
    @URL
    private String paperPdf;
    @NotEmpty(groups = {Add.class})
    private String content;//文章内容
    @NotEmpty(groups = {Add.class})
    private String state;//发布状态 已发布|草稿
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间
    private float score;
    private Integer scoreAmount;// 打分人数

    // 应该可以额外定义属性，吧？
    private List<Integer> categoryId;

    public interface Add{

    }
    public interface Update{


    }
}
