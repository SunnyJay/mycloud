package com.sunnanjun.service;

import com.sunnanjun.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;

/**
 * 作者：sunna
 * 时间: 2018/4/8 14:28
 */
@RunWith(SpringRunner.class)

//无需手动启动服务器，使用SpringBootTest本测试会自动启动一个真实的服务器
//无需配置classes,SpringBootTest会自动去找带有@Configuration的配置类，找不到再去找@SpringBootApplication的类
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT) //自动找到设置的端口
public class TestServiceWithMock
{
    /**
     * TestRestTemplate只能用于测试,只能在@SpringBootTest注解使用时才能启用
     */
    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    public void testAddUser()
    {
    }


    @Test
    public void testGetUser()
    {
        String id = "jacksun";
        User user = testRestTemplate.getForObject("http://localhost:8899/get_user/{id}", User.class, id);
        System.out.println(user);

        Assert.assertThat(user.getUsername(), is("jacksun"));
    }

}
