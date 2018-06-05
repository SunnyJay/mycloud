package com.tangyuan.exception;

/**
 * 作者：sunna
 * 时间: 2018/6/5 10:57
 */
public class GlobalException extends Exception {

    private int code;

    public GlobalException(String message)
    {
        super(message);
    }

    public GlobalException(String message, int code)
    {
        super(message);
        this.code = code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public int getCode()
    {
        return code;
    }
}

