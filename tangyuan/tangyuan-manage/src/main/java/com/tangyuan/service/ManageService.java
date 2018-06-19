package com.tangyuan.service;

import com.alibaba.fastjson.JSONObject;
import com.tangyuan.client.KubernetesService;
import com.tangyuan.domain.Instance;
import com.tangyuan.exception.InternalServerException;
import com.tangyuan.exception.NotFoundException;
import com.tangyuan.repository.InstanceRepository;
import io.fabric8.kubernetes.api.model.Namespace;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.tangyuan.domain.ResourceDefaultConstant.*;
import static com.tangyuan.util.Util.getNullPropertyNames;

/**
 * 作者：sunna
 * 时间: 2018/5/28 17:12
 */
@Service
public class ManageService
{
    @Autowired
    private InstanceRepository instanceRepository;

    @Autowired
    private KubernetesService kubernetesService;


    public List<Instance> getInstanceList()
    {
        return instanceRepository.findAll();
    }

    private String getImageName(int baseOSNum) throws InternalServerException
    {
        String imageName;
        switch (baseOSNum)
        {
            case 0:
                imageName = CENTOS;
                break;
            case 1:
                imageName = UBUNTU;
                break;
            default:
                throw new InternalServerException("没有指定镜像");
        }
        return imageName;
    }

    public Instance addInstance(Instance instance) throws InternalServerException {

        //去掉横线
        String id = instance.getUserId() + "-" + UUID.randomUUID().toString().replace("-", "");
        instance.setId(id);

        Namespace namespace = kubernetesService.getNameSpace(INSTANCE_NAMESPACE);
        if(namespace == null)
        {
            JSONObject namespaceInfo = new JSONObject();
            namespaceInfo.put("namespaceName", INSTANCE_NAMESPACE);
            kubernetesService.addNameSpace(namespaceInfo.toJSONString());
        }

        JSONObject serviceInfo = new JSONObject();
        serviceInfo.put("serviceName", "service-" + id);
        serviceInfo.put("namespace", INSTANCE_NAMESPACE);
        serviceInfo.put("containerPort", INSTANCE_CONTAINER_PORT);
        serviceInfo.put("servicePort", INSTANCE_SERVICE_PORT);
        serviceInfo.put("type", INSTANCE_SERVICE_TYPE);
        JSONObject labels = new JSONObject();
        labels.put("key", INSTANCE_LABELS_KEY);
        labels.put("value", id);
        serviceInfo.put("labels", labels);

        io.fabric8.kubernetes.api.model.Service service = kubernetesService.addService(serviceInfo.toJSONString());

        JSONObject deploymentInfo = new JSONObject();
        deploymentInfo.put("deploymentName", "deployment-" + id);
        deploymentInfo.put("namespace", INSTANCE_NAMESPACE);
        deploymentInfo.put("imageName", getImageName(instance.getBaseOS())+ ":" + instance.getBaseOSVersion());
        deploymentInfo.put("containerPort", INSTANCE_CONTAINER_PORT );
        deploymentInfo.put("replicas", INSTANCE_REPLICAS_NUM );
        deploymentInfo.put("password", instance.getSshPassword());
        deploymentInfo.put("labels", labels);
        kubernetesService.addDeployment(deploymentInfo.toJSONString());

        instance.setIp(service.getSpec().getClusterIP());
        instance.setStatus(Instance.InstanceStatus.RUNING.getStatus());

        return instanceRepository.save(instance);
    }

    public Instance getInstance(String id) throws NotFoundException
    {
        Instance instance = instanceRepository.findOne(id);
        if (instance == null)
        {
            throw new NotFoundException("instance " + id + " not found!");
        }
        return instance;
    }

    public void deleteInstance(String id)
    {
        instanceRepository.delete(id);
    }

    public Instance updateInstance(String id, Instance instance) throws NotFoundException
    {
        Instance currentInstance = instanceRepository.findOne(id);
        if (currentInstance == null)
        {
            throw new NotFoundException("instance " + id + "not found!");
        }

        //支持部分更新
        String[] nullPropertyNames = getNullPropertyNames(instance);
        BeanUtils.copyProperties(instance, currentInstance, nullPropertyNames);

        return instanceRepository.save(currentInstance);
    }
}
