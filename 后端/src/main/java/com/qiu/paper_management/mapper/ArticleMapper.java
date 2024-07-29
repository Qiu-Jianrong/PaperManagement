package com.qiu.paper_management.mapper;

import com.qiu.paper_management.pojo.Article;
import com.qiu.paper_management.pojo.Article_Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Select("select username from paper_management.user where id = #{id}")
    String getAuthorNameById(Integer id);
    @Insert("insert into paper_management.article(title, content, state, create_time, update_time, paper_abstract, paper_pdf, keywords, score, score_amount) " +
            "VALUES (#{title}, #{content}, #{state}, now(), now(), #{paperAbstract}, #{paperPdf}, #{keywords}, 0, 0)")
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

    void update(Article article);

    @Select("select author_id from paper_management.article_author where article_id=#{id} and is_leader=TRUE")
    List<Integer> getLeaderByArticleId(Integer id);

    @Select("select author_id from paper_management.article_author where article_id=#{id} and is_corresponding = TRUE")
    List<Integer> getCoorsByArticleId(Integer id);

    @Select("select author_id from paper_management.article_author where article_id=#{id} and is_corresponding = FALSE and is_leader=FALSE")
    List<Integer> getOthersByArticleId(Integer id);

    List<Integer> findArticleByAuthor(Integer authorId, Integer categoryId);


    List<Article> search(String q, Integer threshold, Integer categoryId, Integer userId);

}
