package com.tangyuan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 作者：sunna
 * 时间: 2018/4/9 11:18
 */
@Configuration
public class AccountConfig
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

    @Bean
    public RedisConnectionFactory jedisConnectionFactory()
    {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(50);
        poolConfig.setMaxWaitMillis(150);
        poolConfig.setMaxTotal(10000);

        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName("39.107.102.175");
        factory.setPort(6679);
        factory.afterPropertiesSet();
        factory.setPoolConfig(poolConfig);
        //factory.setPassword("jack");

        return factory;
    }

    @Bean
    public StringRedisTemplate redisTemplate(RedisConnectionFactory factory)
    {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }
}
