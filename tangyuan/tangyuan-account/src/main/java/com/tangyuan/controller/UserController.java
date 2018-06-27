package com.tangyuan.controller;

import com.tangyuan.domain.User;
import com.tangyuan.domain.UserInfo;
import com.tangyuan.exception.NotFoundException;
import com.tangyuan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 作者：sunna
 * 时间: 2018/6/25 16:57
 */
@RequestMapping("tangyuan/api/account")
@RestController
public class UserController
{
    @Autowired
    private UserService userService;

    @GetMapping("/users/{id}")
    public UserInfo getUser(@PathVariable(name = "id")String id) throws NotFoundException
    {
        return userService.getUser(id);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable(name = "id")String id, @RequestBody User user) throws NotFoundException
    {
        return userService.updateUser(id, user);
    }



}
