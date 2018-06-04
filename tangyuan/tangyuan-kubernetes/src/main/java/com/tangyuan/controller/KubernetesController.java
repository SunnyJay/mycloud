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

    @RequestMapping(method = RequestMethod.GET, value = "/get_node_status")
    public String getNodeStatus()
    {
        return kubernetesService.getNodeStatus();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get_deployment_list")
    public String getDeploymentList()
    {
        return kubernetesService.getDeploymentList();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get_service_list")
    public String getServiceList()
    {
        return kubernetesService.getServiceList();
    }

    //重要，JSON不能作为PathVariable，只能作为body中的内容
    @RequestMapping(value = "/add_deployment")
    public void addDeployment(@RequestBody String deploymentInfo)
    {
        kubernetesService.addDeployment(deploymentInfo);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get_deployment_info/{deployment_name}")
    public String getDeploymentInfo(@PathVariable("deployment_name")String deploymentName)
    {
        return kubernetesService.getDeploymentInfo(deploymentName);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete_deployment/{deployment_name}")
    public void deleteDeployment(@PathVariable("deployment_name")String deploymentName)
    {
        kubernetesService.deleteDeployment(deploymentName);
    }
}


