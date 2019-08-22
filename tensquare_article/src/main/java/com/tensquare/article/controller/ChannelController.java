package com.tensquare.article.controller;


import com.tensquare.article.pojo.Channel;
import com.tensquare.article.service.ChannelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/channel")
public class ChannelController {
    @Autowired
    private ChannelService channelService;

    /**
     * 查询全部
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", channelService.findAll());
    }

    @RequestMapping(value = "/{channelId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String channelId) {
        return new Result(true, StatusCode.OK, "查询成功", channelService.findById(channelId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Channel channel){
        channelService.add(channel);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    @RequestMapping(value = "/{channelId}", method = RequestMethod.PUT)
    public Result update (@RequestBody Channel channel, @PathVariable String channelId){
        channel.setId(channelId);
        channelService.update(channel);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @RequestMapping(value = "/{channelId}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String channelId) {
        channelService.deleteById(channelId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result search(@RequestBody Map map) {
        List<Channel> list = channelService.search(map);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result pageQuery(@RequestBody Map map, @PathVariable int page, @PathVariable int size) {
        Page pageList = channelService.pageQuery(map, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Channel>(pageList.getTotalElements(), pageList.getContent()));
    }
}
