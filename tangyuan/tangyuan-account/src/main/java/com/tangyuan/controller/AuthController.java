package com.tangyuan.controller;

import com.tangyuan.annotation.IgnoreAuth;
import com.tangyuan.domain.Session;
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
    AuthService authService;


    @IgnoreAuth
    @PostMapping("/sessions")
    public Session addSession(@RequestBody Session sessionInfo) throws UnauthorizedException
    {
        return authService.addSession(sessionInfo);
    }

    public Session deleteSession(@RequestBody String sessionId) throws UnauthorizedException
    {
        return authService.deleteSession(sessionId);
    }
}
