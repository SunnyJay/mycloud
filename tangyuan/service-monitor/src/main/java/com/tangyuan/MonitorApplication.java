package com.tangyuan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * 作者：sunna
 * 时间: 2018/4/8 14:20
 */
@SpringBootApplication
@EnableFeignClients
public class MonitorApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(MonitorApplication.class, args);
    }
}
