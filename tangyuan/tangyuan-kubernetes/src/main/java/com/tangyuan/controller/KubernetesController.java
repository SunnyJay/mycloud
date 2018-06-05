package com.tangyuan.controller;

import com.tangyuan.exception.InternalServerException;
import com.tangyuan.exception.Result;
import com.tangyuan.service.KubernetesService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 作者：sunna
 * 时间: 2018/4/9 10:29
 */
@RequestMapping("tangyuan/kubernetes")
@RestController
public class KubernetesController
{
    @Autowired
    private KubernetesService kubernetesService;

    @ApiOperation(value="测试用接口", notes="测试用接口")
    @GetMapping(value = "/pods/{name}")
    public String getPod(@PathVariable("name")String name)
    {
        return kubernetesService.getPod(name);
    }

    @ApiOperation(value="测试用接口", notes="测试用接口")
    @GetMapping(value = "/pods")
    public String getPodList()
    {
        return kubernetesService.getPodList();
    }

    @ApiOperation(value="测试用接口", notes="测试用接口")
    @PostMapping(value = "/namespaces")
    public String addNamespace(@RequestBody String nameSpace)
    {
        return kubernetesService.addNamespace(nameSpace);
    }

    @ApiOperation(value="测试用接口", notes="测试用接口")
    @GetMapping(value = "/namespaces")
    public String getNameSpaceList()
    {
        return kubernetesService.getNameSpaceList();
    }

    @ApiOperation(value="测试用接口", notes="测试用接口")
    @GetMapping(value = "/namespaces/{name}")
    public String getNameSpace(@PathVariable("name")String name)
    {
        return kubernetesService.getNameSpace(name);
    }

    @ApiOperation(value="测试用接口", notes="测试用接口")
    @GetMapping(value = "/test")
    public String test()
    {
        return "test ok!";
    }

    @ApiOperation(value="测试用接口", notes="测试用接口")
    @GetMapping(value = "/node_status")
    public String getNodeStatusList()
    {
        return kubernetesService.getNodeStatusList();
    }

    @ApiOperation(value="测试用接口", notes="测试用接口")
    @GetMapping(value = "/deployments")
    public String getDeploymentList()
    {
        return kubernetesService.getDeploymentList();
    }

    @ApiOperation(value="测试用接口", notes="测试用接口")
    @GetMapping(value = "/services")
    public String getServiceList()
    {
        return kubernetesService.getServiceList();
    }

    //重要，JSON不能作为PathVariable，只能作为body中的内容
    @ApiOperation(value="添加实例", notes="添加实例")
    @PostMapping(value = "/deployments")
    public Result addDeployment(@RequestBody String deployment) throws InternalServerException
    {
        return Result.get(kubernetesService.addDeployment(deployment));
    }

    @ApiOperation(value="测试用接口", notes="测试用接口")
    @GetMapping(value = "/deployments/{name}")
    public String getDeployment(@PathVariable("name")String name)
    {
        return kubernetesService.getDeployment(name);
    }

    @ApiOperation(value="测试用接口", notes="测试用接口")
    @DeleteMapping(value = "/deployments/{name}")
    public void deleteDeployment(@PathVariable("name")String name)
    {
        kubernetesService.deleteDeployment(name);
    }
}


