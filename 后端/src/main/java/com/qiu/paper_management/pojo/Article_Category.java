package com.qiu.paper_management.pojo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Article_Category {
    private Integer articleId;
    private Integer categoryId;
}
