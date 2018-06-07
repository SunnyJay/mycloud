package com.tangyuan.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.deploy.resources.Deployment;
import com.tangyuan.client.KubernetesService;
import com.tangyuan.domain.Instance;
import com.tangyuan.exception.NotFoundException;
import com.tangyuan.repository.InstanceRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public Instance addInstance(Instance instance) {

        //去掉横线
        String id = UUID.randomUUID().toString().replace("-", "");
        instance.setId(id);

        Deployment deploymentInfo = kubernetesService.addDeployment(JSON.toJSONString(instance));
        JSONObject jsonObject = JSONObject.parseObject(deploymentInfo);

        String ip = jsonObject.getString("ip");
        instance.setIp(ip);
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
