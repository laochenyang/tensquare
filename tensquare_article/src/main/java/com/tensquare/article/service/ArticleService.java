package com.tensquare.article.service;

import com.tensquare.article.dao.ArticleDao;
import com.tensquare.article.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 审核文章
     * @param articleId
     */
    @Transactional
    public void examine(String articleId) {
        articleDao.examine(articleId);
    }

    /**
     * 点赞
     * @param articleId
     */
    @Transactional
    public void updateThumbup(String articleId) {
        articleDao.updateThumbup(articleId);
    }
    /**
     * 查询全部文章列表
     * @return
     */
    public List<Article> findAll() {
        return articleDao.findAll();
    }

    /**
     * 根据文章Id 查询文章内容
     * @param articleId
     * @return
     */
    public Article findById(String articleId) {
        Article article = (Article) redisTemplate.opsForValue().get("article_" + articleId);
        if (article == null ) {
            article = articleDao.findById(articleId).get();
            redisTemplate.opsForValue().set("article_" + articleId, article, 10, TimeUnit.SECONDS);
        }
        return article;
    }

    /**
     * 保存文章内容
     * @param article
     */
    public void add(Article article) {
        article.setId(idWorker.nextId() + "");
        articleDao.save(article);
    }

    /**
     * 根据文章id 修改文章内容
     * @param article
     */
    public void update(Article article){
        redisTemplate.delete("article_" + article.getId());
        articleDao.save(article);
    }

    /**
     * 根据文章ID 删除文章内容
     * @param articleId
     */
    public void deleteById(String articleId) {
        redisTemplate.delete("article_" + articleId);
        articleDao.deleteById(articleId);
    }

    /**
     * 根据输入的内容查询文章内容
     * @param searchMap
     * @return
     */
    public List<Article> search(Map searchMap) {
        Specification<Article> specification = createSpecification(searchMap);
        return articleDao.findAll(specification);
    }

    /**
     * 根据查找内容进行查询并分页
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    public Page<Article> pageQuery(Map searchMap, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Specification<Article> specification = createSpecification(searchMap);
        return articleDao.findAll(specification, pageable);
    }

    /**
     * 拼接查询内容
     * @param searchMap
     * @return
     */
    private Specification<Article> createSpecification(Map searchMap) {
        return new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 专栏ID
                if (searchMap.get("columnid")!=null && !"".equals(searchMap.get("columnid"))) {
                    predicateList.add(cb.like(root.get("columnid").as(String.class), "%"+(String)searchMap.get("columnid")+"%"));
                }
                // 用户ID
                if (searchMap.get("userid")!=null && !"".equals(searchMap.get("userid"))) {
                    predicateList.add(cb.like(root.get("userid").as(String.class), "%"+(String)searchMap.get("userid")+"%"));
                }
                // 标题
                if (searchMap.get("title")!=null && !"".equals(searchMap.get("title"))) {
                    predicateList.add(cb.like(root.get("title").as(String.class), "%"+(String)searchMap.get("title")+"%"));
                }
                // 文章正文
                if (searchMap.get("content")!=null && !"".equals(searchMap.get("content"))) {
                    predicateList.add(cb.like(root.get("content").as(String.class), "%"+(String)searchMap.get("content")+"%"));
                }
                // 文章封面
                if (searchMap.get("image")!=null && !"".equals(searchMap.get("image"))) {
                    predicateList.add(cb.like(root.get("image").as(String.class), "%"+(String)searchMap.get("image")+"%"));
                }
                // 是否公开
                if (searchMap.get("ispublic")!=null && !"".equals(searchMap.get("ispublic"))) {
                    predicateList.add(cb.like(root.get("ispublic").as(String.class), "%"+(String)searchMap.get("ispublic")+"%"));
                }
                // 是否置顶
                if (searchMap.get("istop")!=null && !"".equals(searchMap.get("istop"))) {
                    predicateList.add(cb.like(root.get("istop").as(String.class), "%"+(String)searchMap.get("istop")+"%"));
                }
                // 审核状态
                if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
                    predicateList.add(cb.like(root.get("state").as(String.class), "%"+(String)searchMap.get("state")+"%"));
                }
                // 所属频道
                if (searchMap.get("channelid")!=null && !"".equals(searchMap.get("channelid"))) {
                    predicateList.add(cb.like(root.get("channelid").as(String.class), "%"+(String)searchMap.get("channelid")+"%"));
                }
                // URL
                if (searchMap.get("url")!=null && !"".equals(searchMap.get("url"))) {
                    predicateList.add(cb.like(root.get("url").as(String.class), "%"+(String)searchMap.get("url")+"%"));
                }
                // 类型
                if (searchMap.get("type")!=null && !"".equals(searchMap.get("type"))) {
                    predicateList.add(cb.like(root.get("type").as(String.class), "%"+(String)searchMap.get("type")+"%"));
                }
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }
}
