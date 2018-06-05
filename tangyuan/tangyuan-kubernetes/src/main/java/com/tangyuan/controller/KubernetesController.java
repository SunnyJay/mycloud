package com.tangyuan.controller;

import com.tangyuan.exception.InternalServerException;
import com.tangyuan.exception.Result;
import com.tangyuan.service.KubernetesService;
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

    @GetMapping(value = "/pods/{name}")
    public String getPod(@PathVariable("name")String name)
    {
        return kubernetesService.getPod(name);
    }

    @GetMapping(value = "/pods")
    public String getPodList()
    {
        return kubernetesService.getPodList();
    }

    @PostMapping(value = "/namespaces")
    public String addNamespace(@RequestBody String nameSpace)
    {
        return kubernetesService.addNamespace(nameSpace);
    }

    @GetMapping(value = "/namespaces")
    public String getNameSpaceList()
    {
        return kubernetesService.getNameSpaceList();
    }

    @GetMapping(value = "/namespaces/{name}")
    public String getNameSpace(@PathVariable("name")String name)
    {
        return kubernetesService.getNameSpace(name);
    }

    @GetMapping(value = "/test")
    public String test()
    {
        return "test ok!";
    }

    @GetMapping(value = "/node_status")
    public String getNodeStatusList()
    {
        return kubernetesService.getNodeStatusList();
    }

    @GetMapping(value = "/deployments")
    public String getDeploymentList()
    {
        return kubernetesService.getDeploymentList();
    }

    @GetMapping(value = "/services")
    public String getServiceList()
    {
        return kubernetesService.getServiceList();
    }

    //重要，JSON不能作为PathVariable，只能作为body中的内容
    @PostMapping(value = "/deployments")
    public Result addDeployment(@RequestBody String deployment) throws InternalServerException
    {
        return Result.get(kubernetesService.addDeployment(deployment));
    }

    @GetMapping(value = "/deployments/{name}")
    public String getDeployment(@PathVariable("name")String name)
    {
        return kubernetesService.getDeployment(name);
    }

    @DeleteMapping(value = "/deployments/{name}")
    public void deleteDeployment(@PathVariable("name")String name)
    {
        kubernetesService.deleteDeployment(name);
    }
}


