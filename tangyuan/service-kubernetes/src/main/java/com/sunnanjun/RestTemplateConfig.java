package com.sunnanjun;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 作者：sunna
 * 时间: 2018/4/9 11:18
 */
@Configuration
public class RestTemplateConfig
{
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory){
        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        //factory.setReadTimeout(5000); //单位为ms
       // factory.setConnectTimeout(5000); //单位为ms
        return factory;
    }
}
