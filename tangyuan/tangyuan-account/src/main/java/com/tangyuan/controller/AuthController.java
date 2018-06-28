package com.tangyuan.controller;

import com.tangyuan.annotation.IgnoreAuth;
import com.tangyuan.annotation.LoginUser;
import com.tangyuan.domain.LoginInfo;
import com.tangyuan.domain.User;
import com.tangyuan.exception.InternalServerException;
import com.tangyuan.exception.NotFoundException;
import com.tangyuan.exception.ParamInvalidException;
import com.tangyuan.exception.UnauthorizedException;
import com.tangyuan.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作者：sunna
 * 时间: 2018/4/8 13:48
 */
@RequestMapping("tangyuan/account")
@RestController
public class AuthController
{
    @Autowired
    private AuthService authService;


    @IgnoreAuth
    @PostMapping("/login")
    public String login(@RequestBody LoginInfo loginInfo) throws UnauthorizedException, ParamInvalidException, InternalServerException, NotFoundException
    {
        System.out.println("zzzzzzzzz");
        return authService.login(loginInfo);
    }

    @PostMapping("/logout")
    public String logout(@LoginUser User user) throws InternalServerException
    {
        return authService.logout(user);
    }

}
