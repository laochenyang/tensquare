package com.tensquare.article.controller;

import com.tensquare.article.pojo.Article;
import com.tensquare.article.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 审核文章
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/examine/{articleId}", method = RequestMethod.PUT)
    public Result examine(@PathVariable String articleId) {
        articleService.examine(articleId);
        return new Result(true, StatusCode.OK, "审核成功");
    }

    /**
     * 点赞功能
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/thumbup/{articleId}", method = RequestMethod.PUT)
    public Result updateThumbup (@PathVariable String articleId) {
        articleService.updateThumbup(articleId);
        return new Result(true, StatusCode.OK, "点赞成功");
    }

    /**
     * 查询文章全部内容
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", articleService.findAll());
    }

    /**
     * 根据文章id 查询文章内容
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/{articleId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String articleId) {
        return new Result(true, StatusCode.OK, "查询成功", articleService.findById(articleId));
    }

    /**
     * 新增文章
     * @param article
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Article article) {
        articleService.add(article);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    /**
     * 根据文章Id 修改文章内容
     * @param articleId
     * @param article
     * @return
     */
    @RequestMapping(value = "/{articleId}", method = RequestMethod.PUT)
    public Result update(@PathVariable String articleId, @RequestBody Article article) {
        article.setId(articleId);
        articleService.update(article);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    /**
     * 根据文章Id 删除文章内容
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/{articleId}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String articleId) {
        articleService.deleteById(articleId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result search(@RequestBody Map searchMap) {
        List<Article> list = articleService.search(searchMap);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 根据条件查询并分页
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result pageQuery(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page pageList = articleService.pageQuery(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Article>(pageList.getTotalElements(), pageList.getContent()));
    }
}
