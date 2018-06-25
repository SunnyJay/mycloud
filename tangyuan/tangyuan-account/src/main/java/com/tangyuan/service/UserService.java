package com.tangyuan.service;

import com.tangyuan.domain.IdentityType;
import com.tangyuan.domain.Session;
import com.tangyuan.domain.User;
import com.tangyuan.domain.UserAuth;
import com.tangyuan.exception.NotFoundException;
import com.tangyuan.exception.UnauthorizedException;
import com.tangyuan.repository.UserAuthRepository;
import com.tangyuan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * 作者：sunna
 * 时间: 2018/6/25 17:28
 */
@Service
public class UserService
{
    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private UserRepository userRepository;

    public User getUser(String id) throws NotFoundException
    {
        User user = userRepository.findOne(id);
        if (user == null)
        {
            throw new NotFoundException("user " + id + " not found!");
        }
        return user;
    }



    public void addUser(Session session) throws UnauthorizedException
    {
        this.checkSmsAuthCodeIsValid(session.getSmsAuthCode(), session.getSmsCreateTime());

        User user = new User();
        String userId = UUID.randomUUID().toString().replace("-", "");
        user.setId(userId);
        user.setNickName("Jack");
        user.setCreateTime(new Timestamp(System.currentTimeMillis()));

        userRepository.save(user);

        UserAuth userAuth = new UserAuth();
        userAuth.setId(UUID.randomUUID().toString().replace("-", ""));
        userAuth.setUserId(userId);

        //TODO 常量
        userAuth.setIdentityType(IdentityType.SMS_CODE.getType());
        userAuth.setIdentifier(session.getPhone());
        userAuth.setCreateTime(new Timestamp(System.currentTimeMillis()));

        userAuthRepository.save(userAuth);

    }

}
