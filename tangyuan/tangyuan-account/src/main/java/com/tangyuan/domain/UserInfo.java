package com.tangyuan.domain;

import java.sql.Timestamp;

/**
 * 作者：sunna
 * 时间: 2018/6/27 15:41
 */
public class UserInfo
{
    /**
     * 用户Id
     */
    private String id;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 手机号码
     */
    private String phone;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Timestamp getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime)
    {
        this.createTime = createTime;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }
}
