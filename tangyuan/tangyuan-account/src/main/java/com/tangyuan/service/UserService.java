package com.tangyuan.service;

import com.alibaba.fastjson.JSONObject;
import com.tangyuan.domain.User;
import com.tangyuan.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;


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

    public void addUser(User user)
    {
        Example<User> example = Example.of(user);

        /*Optional<User> opt = userRepository.findOne(example);
        if (opt.isPresent())
        {
            logger.info("the user has already exist!" , user.getUsername());
        }
        else
        {
            BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword().trim()));
            userRepository.save(user);
        }*/
    }

    public void deleteUser(User user)
    {
        Example<User> example = Example.of(user);
        /*Optional<User> opt = userRepository.findOne(example);
        if (opt.isPresent())
        {
            userRepository.delete(user);
        }
        else
        {
            logger.info("the user is not exist!" , user.getUsername());
        }*/
    }

    public User findUser(String userName)
    {
        User user = new User();
        user.setUsername(userName);
        /*Example<User> example = Example.of(user);
        Optional<User> opt = userRepository.findOne(example);
        if (opt.isPresent())
        {
            return opt.get();
        }
        else
        {
            logger.info("the user is not exist!" , user.getUsername());
            return null;
        }*/
        return null;
    }



    public void updateUser(User user)
    {
        userRepository.save(user);
    }

    public String login(String code)
    {
        JSONObject jsonObject = JSONObject.parseObject(code);
        String url = "https://api.weixin.qq.com/sns/jscode2session?"
                        + "appid=" + APPID
                        + "&secret=" + AppSecret
                        + "&js_code=" +  jsonObject.getString("code")
                        + "&grant_type=authorization_code";

        String ret = restTemplate.getForEntity(url, String.class).getBody();

        jsonObject = JSONObject.parseObject(ret);

        String sessionKey = jsonObject.getString("session_key");
        String openid = jsonObject.getString("openid");

        String thirdSessionId = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(thirdSessionId, sessionKey + openid);


        jsonObject = new JSONObject();
        jsonObject.put("thirdSessionId", thirdSessionId);
        jsonObject.put("expires", EXPIRES);

        return jsonObject.toJSONString();
    }
}
