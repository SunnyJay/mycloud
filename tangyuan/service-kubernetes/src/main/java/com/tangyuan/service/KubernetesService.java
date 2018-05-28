package com.tangyuan.service;

import io.fabric8.kubernetes.api.model.DoneablePod;
import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.NonNamespaceOperation;
import io.fabric8.kubernetes.client.dsl.PodResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * 作者：sunna
 * 时间: 2018/4/9 10:33
 */
@Service
public class KubernetesService
{
    @Autowired
    private RestTemplate restTemplate;

    public void createInstance() throws FileNotFoundException
    {
        String namespace = "default";    // namespace名
        String master = "http://192.168.56.101:8001/";  // 配置为你的k8s集群的主节点地址
        Config config = new ConfigBuilder()
                .withMasterUrl(master)
                .withNamespace(namespace)
                .build();

        KubernetesClient client = new DefaultKubernetesClient(config);
        System.out.println(client.getApiVersion());

        String path =  ResourceUtils.getFile("classpath:test_create_pod.yaml").getAbsolutePath();
        List<HasMetadata> refreshed = client.load(new FileInputStream(path)).get();
        HasMetadata resource = refreshed.get(0);
        Pod pod = (Pod) resource;
        NonNamespaceOperation<Pod, PodList, DoneablePod, PodResource<Pod, DoneablePod>> pods = client.pods().inNamespace(namespace);

        Pod result = pods.create(pod);  // 此处创建pod资源！！！
        client.pods().inNamespace("default").withName("nginx").tailingLines(10).watchLog(System.out);

        client.close();
    }

    /*public Weather getWeather(String cityId)
    {
        String url =
                "https://free-api.heweather.com/s6/weather/forecast?location=" + cityId
                        + "&key=5ce8c4cde42148928c7dd51e49ccdadd";
        JsonObject jsonObject =
                new Gson().fromJson(restTemplate.getForEntity(url, String.class).getBody(), JsonObject.class);
        JsonObject weatherJson = jsonObject.getAsJsonArray("HeWeather6").get(0).getAsJsonObject();
        Weather weather = new Gson().fromJson(weatherJson, Weather.class);

        return weather;
    }*/
}
