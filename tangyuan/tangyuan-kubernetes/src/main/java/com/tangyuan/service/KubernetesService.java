package com.tangyuan.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tangyuan.exception.InternalServerException;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.api.model.extensions.Deployment;
import io.fabric8.kubernetes.api.model.extensions.DeploymentBuilder;
import io.fabric8.kubernetes.api.model.extensions.DeploymentList;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 作者：sunna
 * 时间: 2018/4/9 10:33
 */
@Service
public class KubernetesService
{
    @Autowired
    private KubernetesClient client;

    public String getPod(String podId)
    {

        Pod pod = client.pods().inNamespace("default").withName(podId).get();

        //Pod可直接转换为JSON
        return new Gson().toJson(pod);
    }

    public String getPodList()
    {
        List<Pod> podList = ((DefaultKubernetesClient) client).inAnyNamespace().pods().list().getItems();
        List<Object> list = Lists.newArrayList();

        for (Pod item : podList)
        {
            Map<String, String> map = Maps.newHashMap();
            map.put("name", item.getMetadata().getName());
            map.put("creationTimestamp", item.getMetadata().getCreationTimestamp());
            PodStatus podStatus = item.getStatus();
            map.put("startTime", item.getStatus().getStartTime());
            map.put("namespace", item.getMetadata().getNamespace());
            map.put("phase", item.getStatus().getPhase());
            map.put("nodeName", item.getSpec().getNodeName());

            list.add(map);
        }

        return new Gson().toJson(list);
    }

    public Namespace addNamespace(String nameSpace)
    {
        JSONObject jsonObject = JSONObject.parseObject(nameSpace);
        Namespace namespace = client.namespaces().createNew()
                .withNewMetadata()
                .withName(jsonObject.getString("namespaceName"))
                //.addToLabels("a", "label")
                .endMetadata()
                .done();
        return namespace;
    }

    public io.fabric8.kubernetes.api.model.Service addService(String serviceInfo)
    {
        JSONObject jsonObject = JSONObject.parseObject(serviceInfo);

        String serviceName = jsonObject.getString("serviceName");
        String namespace = jsonObject.getString("namespace");

        JSONObject labels = jsonObject.getJSONObject("labels");
        Map<String, String> labelsMap = JSONObject.parseObject(labels.toJSONString(),
                new TypeReference<Map<String, String>>(){});

        int port = JSON.parseObject(serviceInfo).getInteger("port");
        System.out.println(port);

        ServicePort servicePort = new ServicePortBuilder()
                .withName("port").withPort(port).withProtocol("TCP").build();
        IntOrString targetPort = new IntOrString();
        targetPort.setIntVal(port);
        servicePort.setTargetPort(targetPort);


        return client.services().inNamespace(namespace).createNew()
                .withNewMetadata()
                .withName(serviceName)
                .addToLabels(labels.getString("key"), labels.getString("value"))
                .endMetadata()
                .withNewSpec()
                .withSelector(labelsMap)
                .withPorts(servicePort)
                .endSpec()
                .done();
    }

    public String getNameSpaceList()
    {
        NamespaceList namespace = client.namespaces().list();
        List<Object> list = Lists.newArrayList();

        for (Namespace item : namespace.getItems())
        {
            Map<String, String> map = Maps.newHashMap();
            map.put("name", item.getMetadata().getName());
            map.put("creationTimestamp", item.getMetadata().getCreationTimestamp());
            map.put("generateName", item.getMetadata().getGenerateName());

            list.add(map);
        }

        return new Gson().toJson(list);
    }

    public Namespace getNameSpace(String namespaceName)
    {
        return client.namespaces().withName(namespaceName).get();
    }

    public String getNodeStatusList()
    {
        List<Map<String, String>> nodeStatusList = Lists.newArrayList();
        List<Node> nodeList = client.nodes().list().getItems();
        for (Node node : nodeList)
        {
            Map<String, Quantity> allocMap = node.getStatus().getAllocatable();
            String cpu = allocMap.get("cpu").getAmount();
            String memory = allocMap.get("memory").getAmount();
            String pods = allocMap.get("pods").getAmount();
            String nodeName = node.getMetadata().getName();
            String timeStamp = node.getMetadata().getCreationTimestamp();

            Map<String, String> nodeInfoMap = Maps.newHashMap();
            nodeInfoMap.put("cpu", cpu);
            nodeInfoMap.put("memory", memory);
            nodeInfoMap.put("pods", pods);
            nodeInfoMap.put("nodeName", nodeName);
            nodeInfoMap.put("timeStamp", timeStamp);

            nodeStatusList.add(nodeInfoMap);
        }
        return new Gson().toJson(nodeStatusList);
    }

    public DeploymentList getDeploymentList()
    {
        return client.extensions().deployments().inAnyNamespace().list();
    }

    public String getServiceList()
    {
        List<Object> list = Lists.newArrayList();
        List<io.fabric8.kubernetes.api.model.Service> serviceList = client.services().list().getItems();
        for (io.fabric8.kubernetes.api.model.Service item : serviceList)
        {
            Map<String, Object> map = Maps.newHashMap();
            map.put("name", item.getMetadata().getName());
            map.put("creationTimestamp", item.getMetadata().getCreationTimestamp());
            map.put("generateName", item.getMetadata().getGenerateName());
            map.put("labels", item.getMetadata().getLabels());
            map.put("clusterIp", item.getSpec().getClusterIP());
            map.put("ports", item.getSpec().getPorts());
            list.add(map);
        }

        return new Gson().toJson(list);
    }

    public Deployment addDeployment(String deploymentInfo)
    {
        JSONObject jsonObject = JSON.parseObject(deploymentInfo);
        String deploymentName = jsonObject.getString("deploymentName");
        String imageName = jsonObject.getString("imageName");
        String namespace = jsonObject.getString("namespace");
        int containerPort = jsonObject.getInteger("containerPort");
        JSONObject labels = JSON.parseObject(deploymentInfo).getJSONObject("labels");
        Deployment deployment = new DeploymentBuilder()
                .withNewMetadata()
                .withName(deploymentName)
                .endMetadata()
                .withNewSpec()
                .withReplicas(jsonObject.getInteger("replicas"))
                .withNewTemplate()
                .withNewMetadata()
                .addToLabels(labels.getString("key"), labels.getString("value"))
                .endMetadata()
                .withNewSpec()
                .addNewContainer()
                .withName(imageName)
                .withImage(imageName)
                .addNewPort()
                .withContainerPort(containerPort)
                .endPort()
                .endContainer()
                .endSpec()
                .endTemplate()
                .endSpec()
                .build();

        return client.extensions().deployments().inNamespace(namespace).create(deployment);
    }


    public Deployment getDeployment(String deploymentName)
    {
        return client.extensions().deployments().inNamespace("default").withName(deploymentName).get();
    }

    public void deleteDeployment(String deploymentName)
    {
        Deployment deployment =
                client.extensions().deployments().inNamespace("default").withName(deploymentName).get();
        client.resource(deployment).delete();
    }


    public Endpoints getEndpoints(String resourceName) {

        Endpoints endpoints = client.endpoints().inNamespace("default").withName(resourceName).get();
        System.out.println(endpoints);
        return endpoints;
    }

    public EndpointsList getEndpointsList() {
       return client.endpoints().list();
    }
}
