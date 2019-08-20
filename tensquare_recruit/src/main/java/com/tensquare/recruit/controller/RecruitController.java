package com.tensquare.recruit.controller;

import com.tensquare.recruit.pojo.Recruit;
import com.tensquare.recruit.service.RecruitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/recruit")
public class RecruitController {
    @Autowired
    private RecruitService recruitService;

    /**
     * 查询全部
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", recruitService.findAll());
    }

    /**
     * 根据id 查询招聘信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", recruitService.findById(id));
    }

    /**
     * 根据id 删除招聘信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id) {
        recruitService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 新增招聘信息
     * @param recruit
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Recruit recruit) {
        recruitService.save(recruit);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    /**
     * 修改招聘信息
     * @param recruit
     * @param recruitId
     * @return
     */
    @RequestMapping(value = "/{recruitId}", method = RequestMethod.PUT)
    public Result update(@RequestBody Recruit recruit, @PathVariable String  recruitId) {
        recruit.setId(recruitId);
        recruitService.update(recruit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 根据条件进行查询
     * @param recruit
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result search(@RequestBody Recruit recruit) {
        List<Recruit> list = recruitService.search(recruit);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 分页查询
     * @param recruit
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result pageQuery(@RequestBody Recruit recruit, @PathVariable int page, @PathVariable int size) {
        Page pageList = recruitService.pageQuery(recruit, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 查询最新的4条推荐职位
     * @return
     */
    @RequestMapping(value = "/search/recommend", method = RequestMethod.GET)
    public Result recommend() {
        List<Recruit> list = recruitService.findTop4ByStateOrderByCreatetimeDesc("2");
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 查询最新的12条职位
     * @return
     */
    @RequestMapping(value = "/search/newlist", method = RequestMethod.GET)
    public Result newList () {
        List<Recruit> list = recruitService.findTop12ByStateNotOrderByCreatetimeDesc("0");
        return new Result(true, StatusCode.OK, "查询成功", list);
    }
}
