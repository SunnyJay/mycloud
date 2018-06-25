package com.tangyuan.service;

import com.alibaba.fastjson.JSONObject;
import com.tangyuan.domain.Session;
import com.tangyuan.domain.WxProperties;
import com.tangyuan.exception.UnauthorizedException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * 作者：sunna
 * 时间: 2018/4/8 13:55
 */
@Service
public class AuthService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);
    private static final long SMS_AUTH_CODE_TIMEOUT = 10;

    /**
     * 服务器第三方session有效时间，单位秒, 默认1天
     */
    private static final Long EXPIRES = 86400L;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private WxProperties wxProperties;

    @Autowired
    private UserService userService;


    private String getSmsAuthCode()
    {
        return "1111";
    }


    private void checkSmsAuthCodeIsValid(String smsAuthCode, long smsCreateTime) throws UnauthorizedException
    {
        if (!StringUtils.equals(smsAuthCode, getSmsAuthCode()))
        {
            //验证码错误
            throw new UnauthorizedException("验证码错误");
        }

        long diffMinutes = (System.currentTimeMillis() - smsCreateTime) / 1000;

        if (diffMinutes > SMS_AUTH_CODE_TIMEOUT)
        {
            //验证码过期
            throw new UnauthorizedException("验证码过期");
        }
    }


    private void cacheSession(String thirdSessionId, String openId, String sessionKey)
    {
        //删除旧数据
        if (redisTemplate.hasKey(openId))
        {
            redisTemplate.delete(redisTemplate.opsForValue().get(openId));
            redisTemplate.delete(openId);
        }

        redisTemplate.opsForValue().set(thirdSessionId, sessionKey + openId, 1, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(openId, thirdSessionId, 1, TimeUnit.MINUTES);
    }

    private String getUrl()
    {
        return String.format(
                wxProperties.getUrl(),
                wxProperties.getAppId(),
                wxProperties.getAppSecret());
    }

    public Session addSession(Session session) throws UnauthorizedException
    {
        //第一次登录同时注册
        if (StringUtils.isNotEmpty(session.getPhone())
                &&  StringUtils.isNotEmpty(session.getSmsAuthCode())
                && session.getSmsCreateTime() != null)
        {
            userService.addUser(session);
        }

        JSONObject jsonObject = JSONObject.parseObject(restTemplate.getForEntity(this.getUrl(), String.class).getBody());

        String thirdSessionId = UUID.randomUUID().toString();
        this.cacheSession(thirdSessionId, jsonObject.getString("openid"), jsonObject.getString("session_key"));

        session.setThirdSessionId(thirdSessionId);
        session.setExpiresIn(jsonObject.getString("expires_in"));

        return session;
    }

    public void checkSessionIsValid(String thirdSessionId) throws UnauthorizedException
    {

        if (!redisTemplate.hasKey(thirdSessionId))
        {
            throw new UnauthorizedException("未登录");
        }
    }

    public Session deleteSession(String sessionId)
    {
    }
}
