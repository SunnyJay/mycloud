package com.tangyuan.controller;

import com.tangyuan.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 作者：sunna
 * 时间: 2018/5/11 11:22
 */
@RequestMapping("tangyuan/monitor")
@RestController
public class MonitorContorller
{
    @Autowired
    MonitorService monitorService;

    @GetMapping(value = "/pods/{name}")
    public String getPod(@PathVariable("name")String name)
    {
        return monitorService.getPod(name);
    }

    @GetMapping(value = "/test")
    public String test()
    {
        return "test ok!";
    }
}
