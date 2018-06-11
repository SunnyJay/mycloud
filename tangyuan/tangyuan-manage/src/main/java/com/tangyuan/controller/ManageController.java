package com.tangyuan.controller;

import com.tangyuan.domain.Instance;
import com.tangyuan.exception.InternalServerException;
import com.tangyuan.exception.NotFoundException;
import com.tangyuan.service.ManageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 作者：sunna
 * 时间: 2018/5/11 11:22
 */
@RestController
@RequestMapping("tangyuan/manage")
public class ManageController
{
    @Autowired
    private ManageService manageService;

    @ApiOperation(value="获取实例列表", notes="获取实例列表")
    @GetMapping(value = "/instances")
    public List<Instance> getInstanceList()
    {
        return manageService.getInstanceList();
    }

    @ApiOperation(value="添加实例", notes="根据instance添加实例")
    @PostMapping(value = "/instances")
    public Instance addInstance(@RequestBody Instance instance) throws InternalServerException {
        return manageService.addInstance(instance);
    }

    @ApiOperation(value="获取实例信息", notes="根据id获取实例信息")
    @GetMapping(value = "/instances/{id}")
    public Instance getInstance(@PathVariable("id") String id) throws NotFoundException
    {
        return manageService.getInstance(id);
    }

    @ApiOperation(value="删除实例", notes="根据id删除实例")
    @DeleteMapping(value = "/instances/{id}")
    public void deleteInstance(@PathVariable("id") String id)
    {
        manageService.deleteInstance(id);
    }

    @ApiOperation(value="更新实例信息", notes="根据id和instance更新实例信息")
    @PutMapping(value = "/instances/{id}")
    public Instance updateInstance(@PathVariable String id, @RequestBody Instance instance) throws NotFoundException
    {
        return manageService.updateInstance(id, instance);
    }

    @ApiOperation(value="测试用接口", notes="测试用接口")
    @GetMapping(value = "/test")
    public String test()
    {
        return "tangyuan get!";
    }
}
