package tangyuan.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tangyuan.exception.InternalServerException;
import com.tangyuan.exception.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tangyuan.client.KubernetesService;
import tangyuan.domain.Instance;
import tangyuan.repository.InstanceRepository;

import java.util.List;

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

        String deploymentInfo;
        deploymentInfo = kubernetesService.addDeployment(JSON.toJSONString(instance));

        //这里捕获与否根据需求，不显示捕获的话，addDeployment接口会直接抛出InternalServerException，此时注意接口一定不要声明
        /*try
        {
           // deploymentInfo = kubernetesService.getDeployment(instance.getId());
        }
        catch (Exception e)
        {
            throw new InternalServerException(e.getMessage());
        }*/

        JSONObject jsonObject = JSONObject.parseObject(deploymentInfo);
        String ip = jsonObject.getString("ip");
        instance.setIp(ip);
        instance.setStatus("running");

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
        return instanceRepository.save(instance);
    }

    public Instance partialUpdateInstance(String id, Instance instance) throws NotFoundException
    {
        Instance currentInstance = instanceRepository.findOne(id);
        if (currentInstance == null)
        {
            throw new NotFoundException("instance " + id + "not found!");
        }

        instance.setId(id);
        BeanUtils.copyProperties(instance, currentInstance);
        return instanceRepository.save(instance);
    }
}
