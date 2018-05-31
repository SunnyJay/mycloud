package com.tangyuan;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 作者：sunna
 * 时间: 2018/4/9 11:18
 */
@Configuration
public class KubernetesConfig
{
    @Value("${k8s.url}")
    private String master;

    @Bean
    public KubernetesClient kubernetesClient()
    {
        Config config = new ConfigBuilder()
                .withNamespace("default")
                .withMasterUrl(master)
                .build();

       return new DefaultKubernetesClient(config);
    }
}
