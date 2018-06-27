package com.tangyuan.domain;

import javax.validation.constraints.NotNull;

/**
 * 作者：sunna
 * 时间: 2018/6/22 10:06
 */
public class LoginInfo
{
    @NotNull(message = "code 不能为空")
    private String code;

    @NotNull(message = "identifier 不能为空")
    private String identifier;

    @NotNull(message = "credential 不能为空")
    private String credential;

    @NotNull(message = "identityType 不能为空")
    private int identityType;

//    @NotNull(message = "smsCreateTime 不能为空")
//    private Long smsCreateTime;


    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getIdentifier()
    {
        return identifier;
    }

    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
    }

    public String getCredential()
    {
        return credential;
    }

    public int getIdentityType()
    {
        return identityType;
    }

    public void setIdentityType(int identityType)
    {
        this.identityType = identityType;
    }

    public void setCredential(String credential)
    {
        this.credential = credential;
    }

//    public Long getSmsCreateTime()
//    {
//        return smsCreateTime;
//    }
//
//    public void setSmsCreateTime(Long smsCreateTime)
//    {
//        this.smsCreateTime = smsCreateTime;
//    }

}
