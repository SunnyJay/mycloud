package com.tangyuan.util;

import com.tangyuan.exception.InternalServerException;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 作者：sunna
 * 时间: 2018/6/27 9:38
 */
public class Utils
{
    public static String httpRequest(String url) throws InternalServerException
    {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(factory);

        String ret;
        try
        {
            ret = restTemplate.getForEntity(url, String.class).getBody();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new InternalServerException("网络请求异常");
        }

        return ret;
    }
}
