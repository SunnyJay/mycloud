package com.tangyuan.exception;

/**
 * 作者：sunna
 * 时间: 2018/6/26 9:43
 */
public class ParamInvalidException  extends GlobalException
{
    public ParamInvalidException(String message)
    {
        super(message, Result.ErrorCode.UNAUTHORIZED_ERROR.getCode());
    }
}
