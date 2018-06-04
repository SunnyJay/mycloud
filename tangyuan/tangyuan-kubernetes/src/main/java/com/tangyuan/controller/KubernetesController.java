package com.tangyuan.controller;

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

    @RequestMapping(method = RequestMethod.GET, value = "/pods/{name}")
    public String getPod(@PathVariable("name")String name)
    {
        return kubernetesService.getPod(name);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pods")
    public String getPodList()
    {
        return kubernetesService.getPodList();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/namespaces")
    public void addNamespace(@RequestBody String nameSpace)
    {
        kubernetesService.addNamespace(nameSpace);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/namespaces")
    public String getNameSpaceList()
    {
        return kubernetesService.getNameSpaceList();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/namespaces/{name}")
    public String getNameSpace(@PathVariable("name")String name)
    {
        return kubernetesService.getNameSpace(name);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public String test()
    {
        return "test ok!";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/node_status")
    public String getNodeStatusList()
    {
        return kubernetesService.getNodeStatusList();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deployments")
    public String getDeploymentList()
    {
        return kubernetesService.getDeploymentList();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/services")
    public String getServiceList()
    {
        return kubernetesService.getServiceList();
    }

    //重要，JSON不能作为PathVariable，只能作为body中的内容
    @RequestMapping(method = RequestMethod.POST, value = "/deployments")
    public void addDeployment(@RequestBody String deployment)
    {
        kubernetesService.addDeployment(deployment);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deployments/{name}")
    public String getDeployment(@PathVariable("name")String name)
    {
        return kubernetesService.getDeployment(name);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deployments/{name}")
    public void deleteDeployment(@PathVariable("name")String name)
    {
        kubernetesService.deleteDeployment(name);
    }
}


