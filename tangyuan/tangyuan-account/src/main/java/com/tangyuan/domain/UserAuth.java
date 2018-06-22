package com.tangyuan.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * 作者：sunna
 * 时间: 2018/6/22 15:26
 */
@Entity
public class UserAuth
{
    @Id
    @NotNull
    private String id;

    @NotNull
    private String userId;

    @NotNull
    private String identifier;

    @NotNull
    private Timestamp createTime;

    @NotNull
    private int identityType;

    private String credential;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getIdentifier()
    {
        return identifier;
    }

    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
    }

    public Timestamp getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime)
    {
        this.createTime = createTime;
    }

    public int getIdentityType()
    {
        return identityType;
    }

    public void setIdentityType(int identityType)
    {
        this.identityType = identityType;
    }

    public String getCredential()
    {
        return credential;
    }

    public void setCredential(String credential)
    {
        this.credential = credential;
    }
}
