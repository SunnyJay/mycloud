package com.tangyuan.Filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * 作者：sunna
 * 时间: 2018/6/28 17:02
 */
public class AccessFilter extends ZuulFilter
{
    private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);
    @Override
    public String filterType()
    {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder()
    {
        return 0;
    }

    @Override
    public boolean shouldFilter()
    {
        RequestContext context = RequestContext.getCurrentContext();
        return !StringUtils.equals(context.getRequest().getRequestURI(), "/tangyuan/api/account/login");
    }

    @Override
    public Object run()
    {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        Object token = request.getParameter("token");

        //校验token
        if (token == null) {
            logger.info("token为空，禁止访问!");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);

            JSONObject ret = new JSONObject();
            ret.put("message", "权限不够");
            return ret;
        } else {
            //TODO 根据token获取相应的登录信息，进行校验（略）
            return null;
        }
    }
}
