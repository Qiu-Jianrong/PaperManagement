package com.qiu.paper_management.service.Impl;

import com.qiu.paper_management.mapper.CategoryMapper;
import com.qiu.paper_management.pojo.Article_Category;
import com.qiu.paper_management.pojo.Category;
import com.qiu.paper_management.service.CategoryService;
import com.qiu.paper_management.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void addCategory(Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(ThreadLocalUtil.getId());

        categoryMapper.addCategory(category);
    }

    @Override
    public void collectArticle(Article_Category articleInCategory) {
        try {
            categoryMapper.collectArticle(articleInCategory);
        }catch (Exception e){
            throw new RuntimeException("待收藏该论文不存在或已经在文献库中！");
        }

    }

    // 返回该用户下的文献库
    @Override
    public List<Category> myCategory() {
        return categoryMapper.myCategory(ThreadLocalUtil.getId());
    }

    // 判断该用户是否已经有同名文献库
    @Override
    public boolean duplicate(String categoryName, Integer createUser) {
        return categoryMapper.findByNameAndUser(categoryName, createUser) != null;
    }

    @Override
    public List<Category> allCategory() {
        return categoryMapper.allCategory();
    }

    @Override
    public Category findCategoryById(Integer id) {
        return categoryMapper.findCategoryById(id);
    }

    @Override
    public void updateCategory(Category category) {
        categoryMapper.updateCategory(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        categoryMapper.deleteCategory(id);
    }

    @Override
    public void removeArticle(Integer articleId, Integer categoryId) {
        try {
            categoryMapper.removeArticle(articleId, categoryId);
        }catch (Exception e){
            // 删除不存在的记录是不会报错的
            throw new RuntimeException("不太懂如何delete会引发异常");
        }
    }
}
