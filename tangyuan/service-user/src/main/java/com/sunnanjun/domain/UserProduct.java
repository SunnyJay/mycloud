package com.sunnanjun.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 作者：sunna
 * 时间: 2018/5/11 10:25
 */
@Entity
public class UserProduct
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private int type;

    private String spec;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getSpec()
    {
        return spec;
    }

    public void setSpec(String spec)
    {
        this.spec = spec;
    }
}
