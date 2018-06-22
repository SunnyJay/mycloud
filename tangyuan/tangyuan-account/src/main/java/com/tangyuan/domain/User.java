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

    private String nickName;

    private Timestamp createTime;

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
}
