package com.tangyuan.exception;

/**
 * 作者：sunna
 * 时间: 2018/6/5 10:58
 */
public class NotFoundException extends GlobalException
{
    public NotFoundException(String message)
    {
        super(message, Result.ErrorCode.NOT_FOUND_ERROR.getCode());
    }
}