package com.tangyuan.controller;

import com.tangyuan.client.K8sService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作者：sunna
 * 时间: 2018/5/11 11:22
 */
@RestController
public class MonitorContorller
{
    @Autowired
    K8sService k8sService;

    @RequestMapping(method = RequestMethod.GET, value = "/get_pod_info/{pod_id}")
    String getPodInfo(@PathVariable("pod_id")String podId)
    {
        return k8sService.getPodInfo(podId);
    }

}
