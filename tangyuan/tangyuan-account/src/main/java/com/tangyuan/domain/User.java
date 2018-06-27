package com.tangyuan.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * 作者：sunna
 * 时间: 2018/4/8 13:36
 */
@Entity
public class User
{
    @Id
    @NotNull
    private String id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 创建时间
     */
    @NotNull
    private Timestamp createTime;

    /**
     * 微信OpenId
     */
    private String wxOpenId;

    /**
     * 性别
     */
    private int gender;

    /**
     * 最近登录Ip
     */
    private String lastLoginIp;

    /**
     * 最近登录时间
     */
    private Timestamp lastLoginTime;

    /**
     * 最近登出时间
     */
    private Timestamp lastLogoutTime;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 国家
     */
    private String country;

    /**
     * 手机号码
     */
    private String phone;

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public Timestamp getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime)
    {
        this.createTime = createTime;
    }

    public String getWxOpenId()
    {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId)
    {
        this.wxOpenId = wxOpenId;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public int getGender()
    {
        return gender;
    }

    public void setGender(int gender)
    {
        this.gender = gender;
    }

    public String getLastLoginIp()
    {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp)
    {
        this.lastLoginIp = lastLoginIp;
    }

    public Timestamp getLastLoginTime()
    {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime)
    {
        this.lastLoginTime = lastLoginTime;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Timestamp getLastLogoutTime()
    {
        return lastLogoutTime;
    }

    public void setLastLogoutTime(Timestamp lastLogoutTime)
    {
        this.lastLogoutTime = lastLogoutTime;
    }
}
