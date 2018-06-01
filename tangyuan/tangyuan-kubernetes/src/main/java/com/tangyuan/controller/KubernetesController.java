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


    @RequestMapping(method = RequestMethod.GET, value = "/get_pod_info/{pod_name}")
    public String getPodInfo(@PathVariable("pod_name")String podName)
    {
        return kubernetesService.getPodInfo(podName);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get_pod_list")
    public String getPodList()
    {
        return kubernetesService.getPodList();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create_namespace/{namespace_info}")
    public void createNamespace(@PathVariable("namespace_info")String nameSpaceInfo)
    {
        kubernetesService.createNamespace(nameSpaceInfo);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get_namespace_list")
    public String getNameSpaceList()
    {
        return kubernetesService.getNameSpaceList();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get_namespace_info/{namespace_name}")
    public String getNameSpaceInfo(@PathVariable("namespace_name")String namespaceName)
    {
        return kubernetesService.getNameSpaceInfo(namespaceName);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public String test()
    {
        return "test ok!";
    }
}
