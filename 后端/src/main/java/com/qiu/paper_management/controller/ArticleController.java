package com.qiu.paper_management.controller;

import com.qiu.paper_management.pojo.*;
import com.qiu.paper_management.service.ArticleService;
import com.qiu.paper_management.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @PostMapping
    public Result postArticle(@RequestBody @Validated(Article.Add.class) Article_Author articleWithAuthor){
        if (articleService.duplicate(articleWithAuthor.getArticle().getTitle()))
            throw new RuntimeException("同名文章已存在!");
        articleService.postArticle(articleWithAuthor);
        return Result.success();
    }

    @GetMapping("/search")
    public Result<PageBean<Article>> search(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam String q,
            @RequestParam Integer threshold,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer userId
    ){
        PageBean<Article> as = articleService.search(pageNum, pageSize, q, threshold, categoryId, userId);
        return Result.success(as);
    }
    @GetMapping
    public Result<PageBean<Article>> list(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) Integer categoryId
    )
    {
        PageBean<Article> pb = articleService.list(pageNum, pageSize, categoryId);
        return Result.success(pb);
    }

    @GetMapping("/mine")
    public Result<List<Article>> myArticle(
            @RequestParam(required = false) Integer categoryId
    ){
        List<Article> pb = articleService.listMine(categoryId, ThreadLocalUtil.getId());
        return Result.success(pb);
    }

    @GetMapping("/detail")
    public Result<Article_Author> getDetail(@RequestParam Integer id){
        Article_Author article_author = new Article_Author();

        // 1. 填充article本身
        Article article = articleService.getDetail(id);
        if(article == null)
            throw new RuntimeException("文章不存在或已经被删除");
        article_author.setArticle(article);

        // 2. 填充作者列表

        article_author.setLeadAuthors(articleService.getLeaderByArticleId(article.getId()));
        article_author.setCorrespondingAuthors(articleService.getCorrByArticleId(article.getId()));
        article_author.setOtherAuthors(articleService.getOthersByArticleId(article.getId()));

        return Result.success(article_author);
    }

    @PutMapping
    public Result updateArticle(@RequestBody @Validated(Article.Update.class) Article article){
        // 1. 确保是自己的文章
        authorization(article.getId());

        articleService.update(article);
        return Result.success();
    }

    // 确保文章存在且删除的是自己的文章
    void authorization(Integer id){
        if (articleService.getDetail(id) == null)
            throw new RuntimeException("文章不存在或已经被删除");
        Integer authorId = ThreadLocalUtil.getId();
        // 如果不是作者之一
        if (!articleService.findAuthorsById(id).contains(authorId)){
            throw new RuntimeException("您不是论文作者，无权删除该论文！");
        }

    }

    @DeleteMapping
    public Result deleteArticle(@RequestParam Integer id){
        this.authorization(id);
        articleService.deleteArticle(id);
        return Result.success();
    }

}
