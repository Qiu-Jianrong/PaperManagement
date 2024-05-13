package com.qiu.paper_management.controller;

import com.qiu.paper_management.pojo.Article_Category;
import com.qiu.paper_management.pojo.Category;
import com.qiu.paper_management.pojo.Result;
import com.qiu.paper_management.service.CategoryService;
import com.qiu.paper_management.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result addCategory(@RequestBody @Validated(Category.Add.class) Category category){
        // 同一个用户底下的文献库名不允许重复
        // 也就是说create_user+category_name构成一个主键
        if(categoryService.duplicate(category.getCategoryName(), ThreadLocalUtil.getId()))
            return Result.error("抱歉，您已创建过同名文献库，请重命名");

        // 2. 创建文献库
        categoryService.addCategory(category);
        return Result.success();
    }

    @PutMapping("/collect")
    public Result collectArticle(@RequestBody @Validated Article_Category articleInCategory){
        // 1. 检测是否操作的是本人的文献库
        this.authorization(articleInCategory.getCategoryId());
        categoryService.collectArticle(articleInCategory);
        return Result.success();
    }

    // 我管理的文献库
    @GetMapping("/mine")
    public Result<List<Category>> getCategory(){
        List<Category> categories = categoryService.myCategory();
        return Result.success(categories);
    }

    // 全平台文献库
    @GetMapping()
    public Result<List<Category>> allCategory(){
        return Result.success(categoryService.allCategory());
    }

    // 文献库详情
    @GetMapping("/detail")
    public Result<Category> categoryDetail(@RequestParam Integer id){
        Category category = categoryService.findCategoryById(id);
        if(category != null)
            return Result.success(category);
        throw new RuntimeException("抱歉，" + id + "号文献库不存在");
    }

    private void authorization(Integer id){
        Category originCategory = categoryService.findCategoryById(id);
        if(originCategory == null)
            throw new RuntimeException("抱歉，" + id + "号文献库不存在");
        if (!Objects.equals(originCategory.getCreateUser(), ThreadLocalUtil.getId()))
            throw new RuntimeException("您只能操作自己创建的文献库！");
    }

    @PutMapping()
    public Result updateCategory(@RequestBody @Validated(Category.Update.class) Category category){
        // 1. 校验文献库存在与否且能否修改
        this.authorization(category.getId());

        // 2. 正式修改
        categoryService.updateCategory(category);
        return Result.success();
    }

    @DeleteMapping()
    public Result deleteCategory(@RequestParam Integer id){
        // 1. 校验存在性和合法性
        this.authorization(id);

        // 2. 正式删除
        categoryService.deleteCategory(id);
        return Result.success();
    }

    @PutMapping("/remove")
    public Result removeArticle(@RequestBody @Validated Article_Category articleInCategory){
        // 1. 确保操作的是本人的文献库
        this.authorization(articleInCategory.getCategoryId());

        //2. 移除收藏
        categoryService.removeArticle(articleInCategory.getArticleId(), articleInCategory.getCategoryId());
        return Result.success();
    }
}
