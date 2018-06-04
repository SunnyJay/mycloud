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
@FeignClient(name="demo1", url = "${tangyuan-kubernetes.url}/tangyuan/kubernetes")
public interface KubernetesService
{
    @RequestMapping(method = RequestMethod.GET, value = "/get_pod_list")
    String getPodList();

    @RequestMapping(method = RequestMethod.GET, value = "/get_pod_info/{pod_name}")
    String getPodInfo(@PathVariable("pod_name") String podName);

    @RequestMapping(method = RequestMethod.POST, value = "/add_deployment/")
    void addDeployment(@RequestBody String deploymentInfo);

    @RequestMapping(method = RequestMethod.GET, value = "/get_deployment_info/{deployment_name}")
    String getDeploymentInfo(@PathVariable("deployment_name")String deploymentName);
}