package com.sunnanjun.controller;

import com.sunnanjun.service.KubernetesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;


/**
 * 作者：sunna
 * 时间: 2018/4/9 10:29
 */
@RestController
public class KubernetesController
{
    @Autowired
    private KubernetesService kubernetesService;

    @RequestMapping("/kubernetes/create_instance/")
    public void createInstance()
    {
        try
        {
            kubernetesService.createInstance();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
