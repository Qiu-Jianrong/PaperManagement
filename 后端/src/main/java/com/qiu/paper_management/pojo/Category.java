package com.qiu.paper_management.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Category {
        @NotNull(groups = Update.class)
        private Integer id;//主键ID

        @NotEmpty(groups = {Update.class, Add.class})
        private String categoryName;//分类名称

        @NotEmpty(groups = {Update.class, Add.class})
        private String categoryAlias;//分类别名

        private Integer createUser;//创建人ID

        @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
        private LocalDateTime createTime;//创建时间
        @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
        private LocalDateTime updateTime;//更新时间

        @NotEmpty
        private boolean categoryPublic;

        public interface Update{

        }
        public interface Add{

        }
}
