package com.tangyuan.exception;

/**
 * 作者：sunna
 * 时间: 2018/6/5 10:58
 */
public class InternalServerException extends GlobalException
{
    public InternalServerException(String message)
    {
        super(message, Result.ErrorCode.INTERNAL_SERVER_ERROR.getCode());
    }
}
