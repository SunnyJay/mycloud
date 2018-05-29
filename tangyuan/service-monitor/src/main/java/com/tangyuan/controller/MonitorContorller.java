package com.tangyuan.controller;

import com.tangyuan.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作者：sunna
 * 时间: 2018/5/11 11:22
 */
@RequestMapping("monitor")
@RestController
public class MonitorContorller
{
    @Autowired
    MonitorService monitorService;

    @RequestMapping(method = RequestMethod.GET, value = "/get_pod_info/{pod_name}")
    String getPodInfo(@PathVariable("pod_name")String podName)
    {
        return monitorService.getPodInfo(podName);
    }

}
