package com.tangyuan;

import com.tangyuan.Filter.AccessFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * 作者：sunna
 * 时间: 2018/4/8 14:20
 */
@SpringBootApplication
@EnableZuulProxy
public class ApiApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    public AccessFilter accessFilter() {
        return new AccessFilter();
    }

}
