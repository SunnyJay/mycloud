package com.tangyuan.controller;

import com.tangyuan.domain.Account;
import com.tangyuan.exception.UnauthorizedException;
import com.tangyuan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 作者：sunna
 * 时间: 2018/4/8 13:48
 */
@RequestMapping("tangyuan/account")
@RestController
public class AccountController
{
    @Autowired
    UserService userService;

    @RequestMapping("/add_user")
    public void addUser(@RequestBody Account account)
    {
        userService.addUser(account);
    }

    @RequestMapping("/delete_user")
    public void deleteUser(@RequestBody Account account)
    {
        userService.deleteUser(account);
    }

    @RequestMapping("/get_user/{id}")
    public Account getUser(@PathVariable(name = "id")String username)
    {
        return userService.findUser(username);
    }

//    @RequestMapping("/get_user_by_code/{code}")
//    public Account getUserByCode(@PathVariable(name = "code")String code)
//    {
//        return userService.findUserByCode(code);
//    }


    @RequestMapping("/update_user")
    public void updateUser(@RequestBody Account account)
    {
        userService.updateUser(account);
    }

    @PostMapping("/sessions")
    public String addSession(@RequestBody String sessionInfo) throws UnauthorizedException
    {
        return userService.addSession(sessionInfo);
    }
}
