package com.tensquare.article.dao;

import com.tensquare.article.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ArticleDao extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article>{

    /**
     * 审核文章
     * @param articleId
     */
    @Modifying
    @Query("update Article set state = '1' where id = ?1")
    public void examine (String articleId);

    /**
     * 点赞
     * @param articleId
     */
    @Modifying
    @Query("update Article set thumbup = thumbup + 1 where id = ?1")
    public void updateThumbup(String articleId);
}
