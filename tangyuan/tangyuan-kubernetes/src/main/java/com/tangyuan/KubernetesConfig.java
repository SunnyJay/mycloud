package com.tangyuan;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 作者：sunna
 * 时间: 2018/4/9 11:18
 */
@Configuration
public class KubernetesConfig
{
    @Bean
    public KubernetesClient kubernetesClient()
    {
        //TODO 提取配置
        String master = "http://17bf2f4c.ngrok.io";  // 配置为你的k8s集群的主节点地址
        Config config = new ConfigBuilder()
                .withMasterUrl(master)
                .build();

       return new DefaultKubernetesClient(config);
    }
}
