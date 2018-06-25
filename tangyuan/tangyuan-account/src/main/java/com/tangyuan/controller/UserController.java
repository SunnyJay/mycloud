package com.tangyuan.controller;

import com.tangyuan.domain.User;
import com.tangyuan.exception.NotFoundException;
import com.tangyuan.exception.UnauthorizedException;
import com.tangyuan.service.AuthService;
import com.tangyuan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 作者：sunna
 * 时间: 2018/6/25 16:57
 */
@RequestMapping("tangyuan/account")
@RestController
public class UserController
{
    @Autowired
    UserService userService;

    @RequestMapping("/users")
    public void addUser(@RequestBody User user, @RequestBody String thirdSessionId)
    {
        userService.addUser(user);
    }
    //
//    @RequestMapping("/delete_user")
//    public void deleteUser(@RequestBody User account)
//    {
//        accountService.deleteUser(account);
//    }
//
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable(name = "id")String id, @RequestBody String thirdSessionId) throws NotFoundException, UnauthorizedException
    {
        authService.checkSessionIsValid(thirdSessionId);

        return authService.getUser(id);
    }
//
//    @RequestMapping("/update_user")
//    public void updateUser(@RequestBody User account)
//    {
//        accountService.updateUser(account);
//    }

}
