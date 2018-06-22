package com.tangyuan.domain;

import javax.validation.constraints.NotNull;

/**
 * 作者：sunna
 * 时间: 2018/6/22 10:06
 */
public class Session
{
    @NotNull(message = "code 不能为空")
    private String code;

    private String phone;

    private String smsAuthCode;

    private Long smsCreateTime;

    private String thirdSessionId;

    private String expiresIn;

    private String userId;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getSmsAuthCode()
    {
        return smsAuthCode;
    }

    public void setSmsAuthCode(String smsAuthCode)
    {
        this.smsAuthCode = smsAuthCode;
    }

    public Long getSmsCreateTime()
    {
        return smsCreateTime;
    }

    public void setSmsCreateTime(Long smsCreateTime)
    {
        this.smsCreateTime = smsCreateTime;
    }

    public String getThirdSessionId()
    {
        return thirdSessionId;
    }

    public void setThirdSessionId(String thirdSessionId)
    {
        this.thirdSessionId = thirdSessionId;
    }

    public String getExpiresIn()
    {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn)
    {
        this.expiresIn = expiresIn;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }
}
