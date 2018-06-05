package com.tangyuan.exception;

/**
 * 作者：sunna
 * 时间: 2018/6/5 10:59
 */
public class Result
{
    /**
     * 状态
     */
    private boolean status;

    /**
     * 错误码
     */
    private int code;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 具体内容
     */
    private Object data;

    public static Result get(Object data, String message, boolean status, int code)
    {
        return new Result(data, message, status, code);
    }

    public static Result get(Object data)
    {
        return new Result(data, null, true, ErrorCode.SUCCESS.getCode());
    }

    private Result(Object data, String message, boolean status, int code)
    {
        this.status = status;
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public Boolean getStatus()
    {
        return status;
    }

    public void setStatus(Boolean status)
    {
        this.status = status;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    public enum ErrorCode
    {
        NOT_FOUND_ERROR(400),

        SUCCESS(200),

        INTERNAL_SERVER_ERROR(500);

        private int code;

        public void setCode(int code)
        {
            this.code = code;
        }

        public int getCode()
        {
            return code;
        }

        ErrorCode(int code)
        {
            this.code = code;
        }
    }

}