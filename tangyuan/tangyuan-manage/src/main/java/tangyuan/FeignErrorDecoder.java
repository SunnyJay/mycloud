package tangyuan;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tangyuan.exception.InternalServerException;
import com.tangyuan.exception.NotFoundException;
import com.tangyuan.exception.Result;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * 作者：sunna
 * 时间: 2018/6/5 16:53
 */
@Configuration
public class FeignErrorDecoder implements ErrorDecoder
{
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String s, Response response)
    {
        if (response.body() != null)
        {
            int errorCode = 0;
            String message = null;
            try
            {
                Result result = this.objectMapper.readValue(Util.toString(response.body().asReader()), Result.class);
                errorCode = result.getCode();
                message = result.getMessage();

            } catch (IOException e)
            {
                e.printStackTrace();
            }

            if (errorCode == Result.ErrorCode.INTERNAL_SERVER_ERROR.getCode())
            {
                return JSON.parseObject(message, InternalServerException.class);
            }
            else if (errorCode == Result.ErrorCode.NOT_FOUND_ERROR.getCode())
            {
                return JSON.parseObject(message, NotFoundException.class);
            }

        }

        return new Exception("未知错误");
    }
}
