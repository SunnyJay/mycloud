package com.tangyuan.Interceptor;

import com.tangyuan.annotation.IgnoreAuth;
import com.tangyuan.exception.UnauthorizedException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 作者：sunna
 * 时间: 2018/6/25 17:07
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter
{
    @Autowired
    private StringRedisTemplate redisTemplate;

    public static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";
    public static final String LOGIN_TOKEN_KEY = "X-tangyuan-Token";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        IgnoreAuth annotation; //重要
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
        } else {
            return true;
        }

        //如果有@IgnoreAuth注解，则不验证token
        if (annotation != null) {
            return true;
        }

        //从header中获取token 重要
        String token = request.getHeader(LOGIN_TOKEN_KEY);
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(LOGIN_TOKEN_KEY);
        }

        //token为空
        if (StringUtils.isBlank(token)) {
            throw new UnauthorizedException("未登录");
        }


        //TODO 封装
        String tokenInfo = redisTemplate.opsForValue().get(token);
        if (!redisTemplate.hasKey(token) || tokenInfo == null)
        {
            throw new UnauthorizedException("未登录");
        }

        //设置userId到request里，后续根据userId，获取用户信息
        //request.setAttribute(LOGIN_USER_KEY, tokenEntity.getUserId());

        return true;
    }
}
