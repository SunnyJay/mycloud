package com.tangyuan;

import com.tangyuan.Interceptor.AuthorizationInterceptor;
import com.tangyuan.Interceptor.LoginUserHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * 作者：sunna
 * 时间: 2018/4/8 14:20
 */
@SpringBootApplication
public class AccountApplication extends WebMvcConfigurationSupport
{
    @Autowired
    LoginUserHandlerMethodArgumentResolver resolver;

    @Autowired
    AuthorizationInterceptor authorizationInterceptor;


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(resolver);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry)
    {
        super.addInterceptors(registry);
        registry.addInterceptor(authorizationInterceptor);
    }

    public static void main(String[] args)
    {
        SpringApplication.run(AccountApplication.class, args);
    }
}
