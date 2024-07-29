package com.qiu.paper_management.service;

import com.qiu.paper_management.pojo.Article_Category;
import com.qiu.paper_management.pojo.Category;

import java.util.List;

public interface CategoryService {
    void addCategory(Category category);

    void collectArticle(Article_Category articleInCategory);

    List<Category> myCategory();

    boolean duplicate(String categoryName, Integer id);

    List<Category> allCategory();

    Category findCategoryById(Integer id);

    void updateCategory(Category category);

    void deleteCategory(Integer id);

    void removeArticle(Integer articleId, Integer categoryId);

    List<Category> search(String q, Integer threshold, Integer userId);

    Integer findOwnerById(Integer id);
}
