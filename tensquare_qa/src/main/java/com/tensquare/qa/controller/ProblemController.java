package com.tensquare.qa.controller;

import com.tensquare.qa.client.BaseClient;
import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/problem")
public class ProblemController {
    @Autowired
    private ProblemService problemService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private BaseClient baseClient;

    /**
     * 根据标签ID 查询最新的问题列表
     * @param labelId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/newlist/{labelId}/{page}/{size}", method = RequestMethod.GET)
    public Result findNewListByLabelId(@PathVariable String labelId, @PathVariable int page, @PathVariable int size) {
        Page pageList = problemService.findNewListByLabelId(labelId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据标签Id 查询热门问题题列表
     * @param labelId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/hotlist/{labelId}/{page}/{size}", method = RequestMethod.GET)
    public Result findHotListByLabelId(@PathVariable String labelId, @PathVariable int page, @PathVariable int size) {
        Page pageList = problemService.findHotListByLabelId(labelId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据标签ID 查询等待回答问题列表
     * @param labelId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/newhotlist/{labelId}/{page}/{size}", method = RequestMethod.GET)
    public Result findWaitListByLabelId(@PathVariable String labelId, @PathVariable int page, @PathVariable int size) {
        Page pageList = problemService.findWaitListByLabelId(labelId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()));
    }

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Problem problem) {
        String token = (String) request.getAttribute("claims_user");
        if (token.isEmpty()) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足！");
        }
        problemService.add(problem);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Problem problem, @PathVariable String id) {
        problem.setId(id);
        problemService.update(problem);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteById (@PathVariable String id) {
        problemService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result search(@RequestBody Map searchMap) {
        List<Problem> list = problemService.search(searchMap);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result pageQuery(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page pageList = problemService.pageQuery(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()));
    }

    @RequestMapping(value = "/label/{labelId}", method = RequestMethod.GET)
    public Result findByLabelId(@PathVariable String labelId) {
        Result result = baseClient.findById(labelId);
        return result;
    }
}
