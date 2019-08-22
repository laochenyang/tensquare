package com.tensquare.article.controller;

import com.tensquare.article.pojo.Column;
import com.tensquare.article.service.ColumnService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/column")
public class ColumnController {
    @Autowired
    private ColumnService columnService;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", columnService.findAll());
    }

    @RequestMapping(value = "/{columnId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String columnId) {
        return new Result(true, StatusCode.OK, "查询成功", columnService.findById(columnId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result add (@RequestBody Column column) {
        columnService.add(column);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    @RequestMapping(value = "/{columnId}", method = RequestMethod.PUT)
    public Result update(@PathVariable String columnId, @RequestBody Column column) {
        column.setId(columnId);
        columnService.update(column);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @RequestMapping(value = "/{columnId}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String columnId) {
        columnService.deleteById(columnId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result search(@PathVariable Map map) {
        List<Column> list = columnService.search(map);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result pageQuery (@RequestBody Map map, @PathVariable int page, @PathVariable int size) {
        Page pageList = columnService.pageQuery(map, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Column>(pageList.getTotalElements(), pageList.getContent()));
    }
}
