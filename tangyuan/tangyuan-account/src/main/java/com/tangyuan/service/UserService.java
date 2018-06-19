package com.tangyuan.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tangyuan.domain.User;
import com.tangyuan.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


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


    @Autowired
    private UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;


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

    public User findUserByCode(String code)
    {
        System.out.println(code);
        String url =
                "https://api.weixin.qq.com/sns/jscode2session?" +
                        "appid="+APPID+"&secret="+AppSecret+"&js_code="+code+"&grant_type=authorization_code";
        JsonObject jsonObject =
                new Gson().fromJson(restTemplate.getForEntity(url, String.class).getBody(), JsonObject.class);
        //JsonObject weatherJson = jsonObject.getAsJsonArray("HeWeather6").get(0).getAsJsonObject();
        System.out.printf(jsonObject.toString());

        //return weather;

        return null;
    }

    public void updateUser(User user)
    {
        userRepository.save(user);
    }

    public void login(User user)
    {
        if (this.findUser(user.getPhone()) == null)
        {
            this.addUser(user);
        }
    }
}
