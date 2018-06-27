package com.tangyuan.Interceptor;

import com.tangyuan.annotation.LoginUser;
import com.tangyuan.domain.User;
import com.tangyuan.service.TokenService;
import com.tangyuan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 作者：sunna
 * 时间: 2018/6/26 14:49
 */
@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver
{
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;


    /**
     * 判定是否需要处理该参数
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter)
    {
        return parameter.getParameterType().isAssignableFrom(User.class) && parameter.hasParameterAnnotation(LoginUser.class);
    }

    /**
     * 用于将header中的token自动转换为用户信息
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception
    {

        String token = webRequest.getHeader(AuthorizationInterceptor.LOGIN_TOKEN_KEY);
        if (token == null)
        {
            //参数中无token
            return null;
        }

        String openId = tokenService.getOpenId(token);
        if (openId == null)
        {
            //token对应的openId不存在,或缓存中无token
            return null;
        }

        User user = userService.getUserByOpenId(openId);
        System.out.println(user);
        return user;
    }
}
