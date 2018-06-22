package com.tangyuan.controller;

import com.tangyuan.domain.User;
import com.tangyuan.domain.Session;
import com.tangyuan.exception.NotFoundException;
import com.tangyuan.exception.UnauthorizedException;
import com.tangyuan.service.AccountService;
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
    AccountService accountService;

    @RequestMapping("/users")
    public void addUser(@RequestBody User user, @RequestBody String thirdSessionId)
    {
        accountService.addUser(user);
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
        accountService.checkSessionIsValid(thirdSessionId);

        return accountService.getUser(id);
    }
//
//    @RequestMapping("/update_user")
//    public void updateUser(@RequestBody User account)
//    {
//        accountService.updateUser(account);
//    }

    @PostMapping("/sessions")
    public Session addSession(@RequestBody Session sessionInfo) throws UnauthorizedException
    {
        return accountService.addSession(sessionInfo);
    }
}
