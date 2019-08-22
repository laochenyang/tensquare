package com.tensquare.gathering.controller;

import com.tensquare.gathering.pojo.Gathering;
import com.tensquare.gathering.service.GatheringService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/gathering")
public class GatheringController {
    @Autowired
    private GatheringService gatheringService;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", gatheringService.findAll());
    }

    @RequestMapping(value = "/{id}", method =  RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", gatheringService.findById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result add (@RequestBody Gathering gathering) {
        gatheringService.add(gathering);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update (@RequestBody Gathering gathering, @PathVariable String id) {
        gathering.setId(id);
        gatheringService.update(gathering);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id) {
        gatheringService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result search (@RequestBody Map map) {
        return new Result(true, StatusCode.OK, "查询成功", gatheringService.search(map));
    }

    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result pageQuery(@RequestBody Map map, @PathVariable int page, @PathVariable int size) {
        Page pageList = gatheringService.pageQuery(map, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Gathering>(pageList.getTotalElements(), pageList.getContent()));
    }
}
