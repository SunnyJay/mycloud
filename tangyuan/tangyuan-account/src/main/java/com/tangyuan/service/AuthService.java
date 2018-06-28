package com.tangyuan.service;

import com.alibaba.fastjson.JSONObject;
import com.tangyuan.domain.*;
import com.tangyuan.exception.InternalServerException;
import com.tangyuan.exception.NotFoundException;
import com.tangyuan.exception.ParamInvalidException;
import com.tangyuan.exception.UnauthorizedException;
import com.tangyuan.repository.UserCredentialRepository;
import com.tangyuan.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;


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
    private WxProperties wxProperties;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserCredentialRepository userCredentialRepository;


    private String getSmsAuthCode()
    {
        return "1111";
    }


    private String getUrl(String code)
    {
        return String.format(wxProperties.getUrl(), wxProperties.getAppId(), wxProperties.getAppSecret(), code);
    }

    /**
     * 目前只支持使用手机验证码登录
     * @param loginInfo
     * @return
     * @throws UnauthorizedException
     * @throws ParamInvalidException
     * @throws InternalServerException
     * @throws NotFoundException
     */
    public String login(LoginInfo loginInfo) throws UnauthorizedException, ParamInvalidException, InternalServerException, NotFoundException
    {

        if (StringUtils.isEmpty(loginInfo.getCode())
                || StringUtils.isEmpty(loginInfo.getIdentifier())
                ||  StringUtils.isEmpty(loginInfo.getCredential())
                ||  loginInfo.getIdentityType() == 0 )
        {
            throw new ParamInvalidException("参数错误");
        }

        this.checkSmsCodeIsValid(loginInfo);

        JSONObject jsonObject = JSONObject.parseObject(Utils.httpRequest(this.getUrl(loginInfo.getCode())));
        if (jsonObject == null)
        {
            throw new InternalServerException("登录失败");
        }

        String openId = jsonObject.getString("openid");
        String sessionKey = jsonObject.getString("session_key");

        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(sessionKey))
        {
            throw new InternalServerException("登录失败");
        }

        User user = userService.getUserByOpenId(openId);

        //用户未注册
        if (user == null)
        {
            //判断手机号是否被绑定
            UserCredential credential = userCredentialRepository.findByIdentifierAndIdentityType(loginInfo.getIdentifier(),
                    loginInfo.getIdentityType());

            if (credential != null)
            {
                throw new UnauthorizedException("该手机号已被其他微信号绑定");
            }

            //第一次登录时直接注册（创建）用户  将手机号与微信号绑定
            user = userService.addUser(openId, loginInfo);
        }
        else
        {
            //用户已注册
            //判断手机号是否被绑定
            UserCredential credential = userCredentialRepository.findByIdentifierAndIdentityType(loginInfo.getIdentifier(),
                    loginInfo.getIdentityType());
            if (credential == null)
            {
                throw new UnauthorizedException("该手机号与本微信号尚未绑定");
            }
        }

        //更新登录信息
        user.setLastLoginIp(this.getLoginIp());
        user.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
        userService.updateUser(user.getId(), user);

        //thirdSessionId
        String token = tokenService.createToken(openId, sessionKey);

        JSONObject ret = new JSONObject();
        ret.put("token", token);
        ret.put("expiresIn", SMS_AUTH_CODE_TIMEOUT);
        ret.put("userId", user.getId());
        ret.put("phone", user.getPhone());

        return ret.toJSONString();
    }

    /**
     * 认证
     * @param loginInfo
     * @throws UnauthorizedException
     * @throws ParamInvalidException
     */
    private void checkSmsCodeIsValid(LoginInfo loginInfo) throws UnauthorizedException, ParamInvalidException
    {
        if (loginInfo.getIdentityType() == IdentityType.PHONE_AND_SMS_CODE.getType())
        {
            if (!StringUtils.equals(loginInfo.getCredential(), getSmsAuthCode()))
            {
                //验证码错误
                throw new UnauthorizedException("验证码错误");
            }

            // TODO
            long diffMinutes = (System.currentTimeMillis() - System.currentTimeMillis() ) / 1000;

            if (diffMinutes > SMS_AUTH_CODE_TIMEOUT)
            {
                //验证码过期
                throw new UnauthorizedException("验证码过期");
            }
        }
        else
        {
            throw new ParamInvalidException("登录类型错误");
        }

    }

    private String getLoginIp()
    {
        return "";
    }


    public String logout(User user) throws InternalServerException
    {
        /*
        三种情况：
        1.请求中无token参数
        2.缓存中token不存在
        3.token对应的用户不存在
         */
        if (user == null)
        {
            throw new InternalServerException("内部错误");
        }

        user.setLastLogoutTime(new Timestamp(System.currentTimeMillis()));
        userService.updateUser(user);
        tokenService.deleteToken(user.getWxOpenId());

        JSONObject ret = new JSONObject();
        ret.put("userId", user.getId());

        return ret.toJSONString();
    }
}
