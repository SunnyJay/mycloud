package com.tangyuan;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 作者：sunna
 * 时间: 2018/5/31 9:33
 */
@Component
@ConfigurationProperties(prefix = "service")
@PropertySource("classpath:custom.properties")
public class Config
{

}
