package com.tangyuan.service;

import com.tangyuan.domain.*;
import com.tangyuan.exception.NotFoundException;
import com.tangyuan.repository.UserCredentialRepository;
import com.tangyuan.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

import static com.tangyuan.util.Util.getNullPropertyNames;

/**
 * 作者：sunna
 * 时间: 2018/6/25 17:28
 */
@Service
public class UserService
{
    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private UserRepository userRepository;

    public UserInfo getUser(String id) throws NotFoundException
    {
        User user = userRepository.findOne(id);
        if (user == null)
        {
            throw new NotFoundException("user " + id + " not found");
        }

        user = userRepository.findOne(id);
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setCreateTime(user.getCreateTime());

        StringBuilder sb = new StringBuilder(user.getPhone());
        sb.replace(3, 7, "****");
        userInfo.setPhone(sb.toString());
        return userInfo;
    }


    public User getUserByOpenId(String openId)
    {
        return userRepository.findByWxOpenId(openId);
    }

    public User updateUser(User user)
    {
        return userRepository.save(user);
    }


    public User addUser(String openId, LoginInfo loginInfo)
    {
        User user = new User();
        String userId = UUID.randomUUID().toString().replace("-", "");
        user.setId(userId);
        user.setWxOpenId(openId);
        user.setCreateTime(new Timestamp(System.currentTimeMillis()));
        user.setPhone(loginInfo.getIdentifier());

        userRepository.save(user);

        UserCredential userCredential = new UserCredential();
        userCredential.setId(UUID.randomUUID().toString().replace("-", ""));
        userCredential.setUserId(userId);
        userCredential.setCreateTime(new Timestamp(System.currentTimeMillis()));
        userCredential.setIdentityType(loginInfo.getIdentityType());
        userCredential.setIdentifier(loginInfo.getIdentifier());

        if (loginInfo.getIdentityType() != IdentityType.PHONE_AND_SMS_CODE.getType())
        {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(loginInfo.getCredential());
            userCredential.setCredential(encodedPassword);
        }

        userCredentialRepository.save(userCredential);
        return user;
    }

    public User updateUser(String id, User user) throws NotFoundException
    {
        User currentUser = userRepository.findOne(id);
        if (currentUser == null)
        {
            throw new NotFoundException("user " + id + "not found!");
        }

        //支持部分更新
        String[] nullPropertyNames = getNullPropertyNames(user);
        BeanUtils.copyProperties(user, currentUser, nullPropertyNames);

        return userRepository.save(currentUser);
    }
}
