package com.tangyuan.service;

import com.tangyuan.client.KubernetesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 作者：sunna
 * 时间: 2018/5/29 10:02
 */
@Service
public class MonitorService
{
    @Autowired
    KubernetesService kubernetesService;

    public String getPod(String podName)
    {
        return kubernetesService.getPod(podName);
    }
}
