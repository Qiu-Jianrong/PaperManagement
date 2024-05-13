package com.qiu.paper_management.mapper;

import com.qiu.paper_management.pojo.Article_Category;
import com.qiu.paper_management.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Insert("insert into paper_management.category(category_name, category_alias, create_user, create_time, update_time, category_public) " +
            "VALUES (#{categoryName}, #{categoryAlias}, #{createUser}, #{createTime}, #{updateTime}, #{categoryPublic});")
    void addCategory(Category category);

    @Insert("insert into paper_management.category_article(category_id, article_id) values (#{categoryId}, #{articleId});")
    void collectArticle(Article_Category articleInCategory);

    @Select("select * from paper_management.category where create_user=#{id}")
    List<Category> myCategory(Integer id);

    @Select("select id from paper_management.category where category_name=#{categoryName} and create_user=#{createUser}")
    Integer findByNameAndUser(String categoryName, Integer createUser);

    @Select("select * from paper_management.category")
    List<Category> allCategory();

    @Select("select * from paper_management.category where id=#{id}")
    Category findCategoryById(Integer id);

    @Update("update paper_management.category set category_name=#{categoryName}, category_alias=#{categoryAlias}, category_public=#{categoryPublic},update_time=now() where id=#{id}")
    void updateCategory(Category category);

    @Delete("delete from paper_management.category where id=#{id}")
    void deleteCategory(Integer id);

    @Delete("delete from paper_management.category_article where article_id=#{articleId} and category_id=#{categoryId}")
    void removeArticle(Integer articleId, Integer categoryId);
}
