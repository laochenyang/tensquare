package com.tensquare.qa.controller;

import com.tensquare.qa.pojo.Reply;
import com.tensquare.qa.service.ReplyService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/reply")
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", replyService.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(String id) {
        return new Result(true, StatusCode.OK, "查询成功", replyService.findById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Reply reply) {
        replyService.add(reply);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Reply reply, @PathVariable String id) {
        reply.setId(id);
        replyService.update(reply);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteById (@PathVariable String id) {
        replyService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result search(@RequestBody Map searchMap) {
        List<Reply> list = replyService.search(searchMap);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result pageQuery (@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page pageList = replyService.pageQuery(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Reply>(pageList.getTotalElements(), pageList.getContent()));
    }
}
