package com.tangyuan.client;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.Service;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;
import io.fabric8.kubernetes.api.model.extensions.Deployment;

/**
 * 作者：sunna
 * 时间: 2018/5/28 17:09
 */
@FeignClient(name="manage", url = "${tangyuan-kubernetes.url}/tangyuan/kubernetes")
public interface KubernetesService
{
    @GetMapping(value = "/pods")
    String getPodList();

    @GetMapping(value = "/pods/{name}")
    String getPod(@PathVariable("name") String name);

    @GetMapping(value = "/namespaces/{name}")
    Namespace getNameSpace(@PathVariable("name") String name);

    @GetMapping(value = "/namespaces")
    Namespace addNameSpace(@RequestBody String namespace);

    @GetMapping(value = "/deployments")
    Deployment addDeployment(@RequestBody String deployment);

    @GetMapping(value = "/services")
    Service addService(@RequestBody String service);

    @GetMapping(value = "/deployments/{name}")
    Deployment getDeployment(@PathVariable("name")String name);
}