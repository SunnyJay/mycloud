package com.tangyuan;

import com.tangyuan.exception.InternalServerException;
import com.tangyuan.exception.NotFoundException;
import com.tangyuan.exception.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 作者：sunna
 * 时间: 2018/6/5 10:37
 */
@ControllerAdvice
public class RestExceptionHandler
{
    private final static Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(value = InternalServerException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleInternalServerException(InternalServerException e)
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
}
