package tangyuan.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 作者：sunna
 * 时间: 2018/5/28 17:09
 */
@FeignClient(name="manage", url = "${tangyuan-kubernetes.url}/tangyuan/kubernetes")
public interface KubernetesService
{
    @RequestMapping(method = RequestMethod.GET, value = "/pods")
    String getPodList();

    @RequestMapping(method = RequestMethod.GET, value = "/pods/{name}")
    String getPod(@PathVariable("name") String name);

    @RequestMapping(method = RequestMethod.POST, value = "/deployments")
    void addDeployment(@RequestBody String deployment);

    @RequestMapping(method = RequestMethod.GET, value = "/deployments/{name}")
    String getDeployment(@PathVariable("name")String name);


}