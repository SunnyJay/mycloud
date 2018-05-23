package com.sunnanjun.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 作者：sunna
 * 时间: 2018/5/11 11:22
 */
@RestController
public class InstanceContorller
{
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/kubernetes/create_instance/")
    public void createInstance()
    {
        String url = "http://localhost:8898/kubernetes/create_instance/";
        JsonObject jsonObject =
                new Gson().fromJson(restTemplate.getForEntity(url, String.class).getBody(), JsonObject.class);
        //JsonObject weatherJson = jsonObject.getAsJsonArray("HeWeather6").get(0).getAsJsonObject();
        System.out.printf(jsonObject.toString());
    }
}
