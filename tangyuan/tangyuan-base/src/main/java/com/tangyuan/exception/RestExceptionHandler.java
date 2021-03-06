package com.tangyuan.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * 作者：sunna
 * 时间: 2018/6/5 10:37
 */
@ControllerAdvice
public class RestExceptionHandler
{
    private final static Logger logger = LoggerFactory.getLogger(com.tangyuan.exception.RestExceptionHandler.class);

    @ExceptionHandler(value = InternalServerException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleInternalServerException(InternalServerException e)
    {
        logger.error(e.getMessage(), e);
        return Result.get(null, e.getMessage(), false, e.getCode());
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handleUnauthorizedException(UnauthorizedException e)
    {
        logger.error(e.getMessage(), e);
        return Result.get(null, e.getMessage(), false, e.getCode());
    }

    @ExceptionHandler(value = ParamInvalidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleParamInvalidException(ParamInvalidException e)
    {
        logger.error(e.getMessage(), e);
        return Result.get(null, e.getMessage(), false, e.getCode());
    }

    //一定要改变响应状态，而不只是body中的状态
    @ExceptionHandler(value = NotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result handleResourceNotFoundException(NotFoundException e)
    {
        logger.error(e.getMessage(), e);
        return Result.get(null, e.getMessage(), false, e.getCode());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleOtherException(Exception e)
    {
        //用于解决由FeignErrorDecoder转换来的异常为UndeclaredThrowableException的情况
        if (e.getMessage() == null)
        {
            e = (Exception) ((UndeclaredThrowableException) e).getUndeclaredThrowable();
        }
        logger.error(e.getMessage(), e);


        return Result.get(null, e.getMessage(), false, 500);
    }
}
