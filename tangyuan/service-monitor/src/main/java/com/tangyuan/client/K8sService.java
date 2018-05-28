package com.tangyuan.client;

import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 作者：sunna
 * 时间: 2018/5/28 17:09
 */
@FeignClient(url = "http://service-k8s:2222")
public interface K8sService
{

    @RequestMapping(method = RequestMethod.GET, value = "/get_pod_list")
    List<JSONPObject> getPodList();

    @RequestMapping(method = RequestMethod.GET, value = "/get_pod_info/{pod_id}")
    String getPodInfo(@PathVariable("pod_id")String podId);

}