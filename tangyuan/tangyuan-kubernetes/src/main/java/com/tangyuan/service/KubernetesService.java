package com.tangyuan.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.tangyuan.exception.InternalServerException;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.api.model.extensions.Deployment;
import io.fabric8.kubernetes.api.model.extensions.DeploymentBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
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

    public String addNamespace(String nameSpace)
    {
        JSONObject obj = JSONObject.parseObject(nameSpace);
        Namespace namespace = client.namespaces().createNew()
                .withNewMetadata()
                .withName(obj.getString("name"))
                .addToLabels("a", "label")
                .endMetadata()
                .done();
        return JSONObject.toJSONString(namespace);
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

    public String getNameSpace(String namespaceName)
    {
        Namespace namespace = client.namespaces().withName(namespaceName).get();
        return new Gson().toJson(namespace);
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

    public String getDeploymentList()
    {
        List<Map<String, Object>> dbList = Lists.newArrayList();
        List<Deployment> list = client.extensions().deployments().inAnyNamespace().list().getItems();

        for (Deployment dp : list)
        {
            String dpName = dp.getMetadata().getName();
            String creationTimestamp = dp.getMetadata().getCreationTimestamp();
            Map<String, String> labels = dp.getMetadata().getLabels();
            int readyReplicas = dp.getStatus().getReadyReplicas();
            int replicas = dp.getStatus().getReplicas();

            Map<String, Object> dpInfoMap = Maps.newHashMap();
            dpInfoMap.put("dpName", dpName);
            dpInfoMap.put("creationTimestamp", creationTimestamp);
            dpInfoMap.put("labels", labels);
            dpInfoMap.put("readyReplicas", readyReplicas);
            dpInfoMap.put("replicas", replicas);

            dbList.add(dpInfoMap);
        }

        return new Gson().toJson(dbList);
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

    public String addDeployment(String deploymentInfo) throws InternalServerException
    {
        try
        {
        JSONObject jsonObject = JSON.parseObject(deploymentInfo);

        int replicas = 1;
        int port = 80;
        String deploymentName = jsonObject.getString("id");
        String imageName = jsonObject.getString("baseOS");
        String image = jsonObject.getString("baseOS");
        JSONObject labels = new JSONObject();
        labels.put("key", "app");
        labels.put("value", "nginx");


        Deployment deployment = new DeploymentBuilder()
                .withNewMetadata()
                .withName(deploymentName)
                .endMetadata()
                .withNewSpec()
                .withReplicas(replicas)
                .withNewTemplate()
                .withNewMetadata()
                .addToLabels(labels.get("key").toString(), labels.get("value").toString())
                .endMetadata()
                .withNewSpec()
                .addNewContainer()
                .withName(imageName)
                .withImage(image)
                .addNewPort()
                .withContainerPort(port)
                .endPort()
                .endContainer()
                .endSpec()
                .endTemplate()
                .endSpec()
                .build();


            Deployment ret = client.extensions().deployments().inNamespace("default").create(deployment);
            return JSONObject.toJSONString(ret);
        }
        catch (Exception e)
        {
            throw new InternalServerException("Deployment 创建失败");
        }
    }


    public String getDeployment(String deploymentName)
    {
        Deployment deployment =
                client.extensions().deployments().inNamespace("default").withName(deploymentName).get();
        return new Gson().toJson(deployment);
    }

    public void deleteDeployment(String deploymentName)
    {
        Deployment deployment =
                client.extensions().deployments().inNamespace("default").withName(deploymentName).get();
        client.resource(deployment).delete();
    }
}
