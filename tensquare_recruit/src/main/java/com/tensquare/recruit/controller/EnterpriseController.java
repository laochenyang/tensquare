package com.tensquare.recruit.controller;

import com.tensquare.recruit.pojo.Enterprise;
import com.tensquare.recruit.service.EnterpriseService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/enterprise")
public class EnterpriseController {
    @Autowired
    private EnterpriseService enterpriseService;

    /**
     * 查询全部企业信息
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", enterpriseService.findAll());
    }

    /**
     * 根据Id 查询企业信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", enterpriseService.findById(id));
    }

    /**
     * 保存企业信息
     * @param enterprise
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Enterprise enterprise) {
        enterpriseService.add(enterprise);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    /**
     * 修改企业信息
     * @param enterprise
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Enterprise enterprise, @PathVariable String id) {
        enterprise.setId(id);
        enterpriseService.update(enterprise);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 根据Id 删除企业信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id) {
        enterpriseService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 根据条件进行查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result search(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", enterpriseService.search(searchMap));
    }

    /**
     * 根据条件进行分页查询
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result pageQuery(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page pageList = enterpriseService.pageQuery(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Enterprise>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 查询热门机构
     * @return
     */
    @RequestMapping(value = "/search/hotList", method = RequestMethod.GET)
    public Result hotList () {
        List<Enterprise> list = enterpriseService.findByIsHot("1");
        return new Result(true, StatusCode.OK, "查询成功", list);
    }
}
