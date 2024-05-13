package com.qiu.paper_management.mapper;

import com.qiu.paper_management.pojo.Article;
import com.qiu.paper_management.pojo.Article_Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Insert("insert into paper_management.article(title, content, state, create_time, update_time, paper_abstract, paper_pdf, keywords) " +
            "VALUES (#{title}, #{content}, #{state}, now(), now(), #{paperAbstract}, #{paperPdf}, #{keywords})")
    void addArticle(Article article);


    @Insert("insert into paper_management.article_author(article_id, author_id, is_leader, is_corresponding) " +
            "VALUES (#{articleId}, #{authorId}, #{isLeader}, #{isCorresponding})")
    void addArticleAuthor(Integer articleId, Integer authorId, boolean isLeader, boolean isCorresponding);


    @Select("SELECT id from paper_management.article where title=#{title}")
    Integer findIdByTitle(String title);

    @Select("select * from paper_management.article where id=#{id}")
    Article findById(Integer id);

    List<Article> list(Integer categoryId);


    List<Integer> findArticleCategory(Integer articleId);

    @Select("select author_id from paper_management.article_author where article_id=#{articleId}")
    List<Integer> findAuthorsById(Integer articleId);



    void deleteArticle(Integer id);

}
