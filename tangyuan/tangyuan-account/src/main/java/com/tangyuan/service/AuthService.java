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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
     * 目前，小程序支持三种登录方式：
     *   1. 手机号+验证码
     *   2. 手机号+密码
     *   3. 邮箱+密码
     *  无论哪种，都需要通过weixin生成token.
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

        this.auth(loginInfo);

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
        if (user == null)
        {
            //第一次登录时直接注册（创建）用户
            user = userService.addUser(openId, loginInfo);
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
    private void auth(LoginInfo loginInfo) throws UnauthorizedException, ParamInvalidException
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
        else if (loginInfo.getIdentityType() == IdentityType.EMAIL_AND_PASS.getType()
                || loginInfo.getIdentityType() == IdentityType.PHONE_AND_PASS.getType())
        {
            UserCredential userCredential = userCredentialRepository.findByIdentifierAndIdentityType(loginInfo.getIdentifier(), loginInfo.getIdentityType());
            if (userCredential == null)
            {
                throw new UnauthorizedException("用户不存在或密码错误");
            }

            String password = loginInfo.getCredential();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (!encoder.matches(password, userCredential.getCredential()))
            {
                throw new UnauthorizedException("用户不存在或密码错误");
            }

            //TODO
            if (userCredential.getStatus() == 0)
            {
                throw new UnauthorizedException("用户被锁定");
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
