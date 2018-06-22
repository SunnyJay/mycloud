package com.tangyuan.service;

import com.alibaba.fastjson.JSONObject;
import com.tangyuan.domain.IdentityType;
import com.tangyuan.domain.Session;
import com.tangyuan.domain.User;
import com.tangyuan.domain.UserAuth;
import com.tangyuan.exception.NotFoundException;
import com.tangyuan.exception.UnauthorizedException;
import com.tangyuan.repository.UserAuthRepository;
import com.tangyuan.repository.UserRepository;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * 作者：sunna
 * 时间: 2018/4/8 13:55
 */
@Service
public class AccountService
{
    private Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    private static final String APPID = "wxe5ce229b3dc95005";
    private static final String APPSECRET= "1daec9b7417944df453492cf819f7fe0";
    private static final long SMS_AUTH_CODE_TIMEOUT = 10;

    /**
     * 服务器第三方session有效时间，单位秒, 默认1天
     */
    private static final Long EXPIRES = 86400L;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    StringRedisTemplate redisTemplate;


    private String getSmsAuthCode()
    {
        return "1111";
    }


    public void addAcount(User account)
    {
        //Example<User> example = Example.of(account);

        /*Optional<User> opt = accountRepository.findOne(example);
        if (opt.isPresent())
        {
            logger.info("the account has already exist!" , account.getUsername());
        }
        else
        {
            BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
            account.setPassword(encoder.encode(account.getPassword().trim()));
            accountRepository.save(account);
        }*/
    }


    public void addAccount(Session session) throws UnauthorizedException
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

    public void deleteUser(User account)
    {
        Example<User> example = Example.of(account);
        /*Optional<User> opt = accountRepository.findOne(example);
        if (opt.isPresent())
        {
            accountRepository.delete(account);
        }
        else
        {
            logger.info("the account is not exist!" , account.getUsername());
        }*/
    }

    public User getUser(String id) throws NotFoundException
    {
        User user = userRepository.findOne(id);
        if (user == null)
        {
            throw new NotFoundException("user " + id + " not found!");
        }
        return user;
    }


//    public void updateUser(User account)
//    {
//        accountRepository.save(account);
//    }


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

    public Session addSession(Session session) throws UnauthorizedException
    {
        if (StringUtils.isNotEmpty(session.getPhone())
                &&  StringUtils.isNotEmpty(session.getSmsAuthCode())
                && session.getSmsCreateTime() != null)
        {
            this.addAccount(session);
        }

        String url = "https://api.weixin.qq.com/sns/jscode2session?"
                + "appid=" + APPID
                + "&secret=" + APPSECRET
                + "&js_code=" + session.getCode()
                + "&grant_type=authorization_code";

        JSONObject jsonObject = JSONObject.parseObject(restTemplate.getForEntity(url, String.class).getBody());

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

    public void addUser(User user)
    {
    }
}
