package com.tangyuan.exception;

/**
 * 作者：sunna
 * 时间: 2018/6/5 10:58
 */
public class UnauthorizedException extends GlobalException
{
    public UnauthorizedException(String message)
    {
        super(message, Result.ErrorCode.UNAUTHORIZED_ERROR.getCode());
    }
}
