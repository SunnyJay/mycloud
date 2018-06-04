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

    public Instance addInstance(Instance instance)
    {
        kubernetesService.addDeployment(JSON.toJSONString(instance));

        String deploymentInfo = kubernetesService.getDeployment(instance.getId());
        JSONObject jsonObject = JSONObject.parseObject(deploymentInfo);
        String ip = jsonObject.getString("ip");
        instance.setIp(ip);
        instance.setStatus("running");

        return instanceRepository.save(instance);
    }

    public String getInstanceOne(String id)
    {
        Instance instance = instanceRepository.findOne(id);
        return JSONObject.toJSONString(instance);
    }

    public void deleteInstance(String id)
    {
        instanceRepository.delete(id);
    }

    public Instance updateInstance(String id, Instance instance)
    {
        instance.setId(id);
        return instanceRepository.save(instance);
    }
}
