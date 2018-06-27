package com.tangyuan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 作者：sunna
 * 时间: 2018/6/26 11:04
 */
@Service
public class TokenService
{
    @Autowired
    private StringRedisTemplate redisTemplate;


    public String createToken(String openId, String sessionKey)
    {
        //也就是thirdSessionId
        String token = UUID.randomUUID().toString().replace("-", "");

        //删除旧Token
        if (redisTemplate.hasKey(openId))
        {
            redisTemplate.delete(redisTemplate.opsForValue().get(openId));
            redisTemplate.delete(openId);
        }

        //更新Token
        redisTemplate.opsForValue().set(token, sessionKey + ":" + openId, 5, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(openId, token, 5, TimeUnit.MINUTES);

        return token;
    }


    public String getOpenId(String token)
    {
        String openId = null;
        if (redisTemplate.hasKey(token))
        {
            openId = redisTemplate.opsForValue().get(token).split(":")[1];
        }

        return openId;
    }

    public void deleteToken(String openId)
    {
        if (redisTemplate.hasKey(openId))
        {
            redisTemplate.delete(redisTemplate.opsForValue().get(openId));
            redisTemplate.delete(openId);
        }
    }
}
