package com.tangyuan.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 作者：sunna
 * 时间: 2018/5/28 17:09
 */
@FeignClient(name="demo1", url = "${tangyuan-kubernetes.url}/tangyuan/kubernetes")
public interface KubernetesService
{
    @GetMapping(value = "/pods")
    String getPodList();

    @GetMapping(value = "/pods/{name}")
    String getPod(@PathVariable("name")String name);
}