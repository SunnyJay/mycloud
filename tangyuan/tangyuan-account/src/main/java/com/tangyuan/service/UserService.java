package com.tangyuan.service;

import com.alibaba.fastjson.JSONObject;
import com.tangyuan.domain.Account;
import com.tangyuan.exception.UnauthorizedException;
import com.tangyuan.repository.UserRepository;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
public class UserService
{
    private Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final String APPID = "wxe5ce229b3dc95005";
    private static final String AppSecret = "1daec9b7417944df453492cf819f7fe0";

    /**
     * 服务器第三方session有效时间，单位秒, 默认1天
     */
    private static final Long EXPIRES = 86400L;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    StringRedisTemplate redisTemplate;


    private String getSmsCode()
    {
        return "1111";
    }


    public void addUser(Account account)
    {
        Example<Account> example = Example.of(account);

        /*Optional<Account> opt = userRepository.findOne(example);
        if (opt.isPresent())
        {
            logger.info("the account has already exist!" , account.getUsername());
        }
        else
        {
            BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
            account.setPassword(encoder.encode(account.getPassword().trim()));
            userRepository.save(account);
        }*/
    }

    public void deleteUser(Account account)
    {
        Example<Account> example = Example.of(account);
        /*Optional<Account> opt = userRepository.findOne(example);
        if (opt.isPresent())
        {
            userRepository.delete(account);
        }
        else
        {
            logger.info("the account is not exist!" , account.getUsername());
        }*/
    }

    public Account findUser(String userName)
    {
        Account account = new Account();
        account.setUsername(userName);
        /*Example<Account> example = Example.of(account);
        Optional<Account> opt = userRepository.findOne(example);
        if (opt.isPresent())
        {
            return opt.get();
        }
        else
        {
            logger.info("the account is not exist!" , account.getUsername());
            return null;
        }*/
        return null;
    }


    public void updateUser(Account account)
    {
        userRepository.save(account);
    }

    public String addSession(String sessionInfo) throws UnauthorizedException
    {
        JSONObject jsonObject = JSONObject.parseObject(sessionInfo);
        String phone = jsonObject.getString("phone");
        String smscode = jsonObject.getString("smscode");
        String code = jsonObject.getString("code");
        long smsGetTime = jsonObject.getLong("smsGetTime");


        if (!StringUtils.equals(smscode, getSmsCode()))
        {
            //验证码错误
            throw new UnauthorizedException("验证码错误");
        }

        long currentTime = System.currentTimeMillis();
        System.out.println(currentTime);
        long diffMinutes = ( currentTime - smsGetTime) / 1000;
        if (diffMinutes > 10)
        {
            //验证码过期
            throw new UnauthorizedException("验证码过期");
        }

        String url = "https://api.weixin.qq.com/sns/jscode2session?"
                + "appid=" + APPID
                + "&secret=" + AppSecret
                + "&js_code=" + jsonObject.getString("code")
                + "&grant_type=authorization_code";

        String ret = restTemplate.getForEntity(url, String.class).getBody();

        jsonObject = JSONObject.parseObject(ret);

        String sessionKey = jsonObject.getString("session_key");
        String openid = jsonObject.getString("openid");
        String expireIn = jsonObject.getString("expires_in");

        String thirdSessionId = UUID.randomUUID().toString();

        //重新时删除旧数据
        if (redisTemplate.hasKey(openid))
        {
            redisTemplate.delete(redisTemplate.opsForValue().get(openid));
            redisTemplate.delete(openid);
        }

        redisTemplate.opsForValue().set(thirdSessionId, sessionKey + openid, 1, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(openid, thirdSessionId, 1, TimeUnit.MINUTES);

        jsonObject = new JSONObject();
        jsonObject.put("thirdSessionId", thirdSessionId);
        jsonObject.put("expires", EXPIRES);

        return jsonObject.toJSONString();
    }
}
