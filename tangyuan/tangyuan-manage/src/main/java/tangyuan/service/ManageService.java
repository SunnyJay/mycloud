package tangyuan.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tangyuan.client.KubernetesService;
import tangyuan.domain.Instance;
import tangyuan.repository.InstanceRepository;

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


    public String getInstanceList()
    {
        return new Gson().toJson(instanceRepository.findAll());
    }

    public void addInstance(Instance instance)
    {
        kubernetesService.addDeployment(JSON.toJSONString(instance));

        String deploymentInfo = kubernetesService.getDeploymentInfo(instance.getId());
        JSONObject jsonObject = JSONObject.parseObject(deploymentInfo);
        String ip = jsonObject.getString("ip");
        instance.setIp(ip);
        instance.setStatus("running");

        instanceRepository.save(instance);
    }

    public String getInstanceInfo(String id)
    {
        Instance instance = instanceRepository.findOne(id);
        return JSONObject.toJSONString(instance);
    }

    public void deleteInstance(String id)
    {
        instanceRepository.delete(id);
    }

    public void updateInstance(String id, Instance instance)
    {
        instance.setId(id);
        instanceRepository.save(instance);
    }

    public void stopInstance(String id)
    {
        Instance instance = instanceRepository.findOne(id);
        instance.setStatus("stop");
        instanceRepository.save(instance);
    }
}
