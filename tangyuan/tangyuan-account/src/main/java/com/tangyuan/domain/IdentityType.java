package com.tangyuan.domain;

/**
 * 作者：sunna
 * 时间: 2018/6/22 15:56
 */
public enum IdentityType
{
    PHONE_AND_PASS(1),
    EMAIL_AND_PASS(2),
    PHONE_AND_SMS_CODE(3),
    ;

    private int type;

    IdentityType(int type) {
        this.type = type;
    }

    public int getType()
    {
        return type;
    }
}
