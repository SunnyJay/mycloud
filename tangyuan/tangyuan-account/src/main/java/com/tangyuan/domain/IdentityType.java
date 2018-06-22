package com.tangyuan.domain;

/**
 * 作者：sunna
 * 时间: 2018/6/22 15:56
 */
public enum IdentityType
{
    PHONE(1), EMAIL(2), SMS_CODE(3);

    private int type;

    IdentityType(int type) {
        this.type = type;
    }

    public int getType()
    {
        return type;
    }
}
