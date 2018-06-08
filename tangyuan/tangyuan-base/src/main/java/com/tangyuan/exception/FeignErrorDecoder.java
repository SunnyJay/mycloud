package com.tangyuan.exception;

import com.alibaba.fastjson.JSON;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;

import java.io.IOException;

/**
 * 作者：sunna
 * 时间: 2018/6/5 16:53
 */
public class FeignErrorDecoder implements ErrorDecoder
{
    //ErrorDecoder只有在响应为非200时自动调用，因此注意client使用ControllerAdvice自定义响应时，不要忘记改变ResponseStatus
    @Override
    public Exception decode(String s, Response response)
    {
        if (response.body() != null)
        {
            int errorCode = 0;
            String message = null;
            try
            {
                //使用JSON进行反序列化，注意Result一定要有默认构造函数
                Result result = JSON.toJavaObject(JSON.parseObject(Util.toString(response.body().asReader())), Result.class);
                errorCode = result.getCode();
                message = result.getMessage();

            } catch (IOException e)
            {
                e.printStackTrace();
            }

            if (errorCode == Result.ErrorCode.INTERNAL_SERVER_ERROR.getCode())
            {
                return new InternalServerException(message);
            }
            else if (errorCode == Result.ErrorCode.NOT_FOUND_ERROR.getCode())
            {
                return new NotFoundException(message);
            }
        }

        return new Exception("未知错误");
    }
}
