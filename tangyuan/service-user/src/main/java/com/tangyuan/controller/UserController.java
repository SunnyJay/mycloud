package com.tangyuan.controller;

import com.tangyuan.domain.User;
import com.tangyuan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作者：sunna
 * 时间: 2018/4/8 13:48
 */
@RestController
public class UserController
{
    @Autowired
    UserService userService;



    @RequestMapping("/add_user")
    public void addUser(@RequestBody User user)
    {
        userService.addUser(user);
    }


    @RequestMapping("/delete_user")
    public void deleteUser(@RequestBody User user)
    {
        userService.deleteUser(user);
    }

    @RequestMapping("/get_user/{id}")
    public User getUser(@PathVariable(name = "id")String username)
    {
        return userService.findUser(username);
    }

    @RequestMapping("/get_user_by_code/{code}")
    public User getUserByCode(@PathVariable(name = "code")String code)
    {
        return userService.findUserByCode(code);
    }

    @RequestMapping("/update_user")
    public void updateUser(@RequestBody User user)
    {
        userService.updateUser(user);
    }

    @RequestMapping("/login")
    public void login(@RequestBody User user)
    {
        userService.login(user);
    }
}
