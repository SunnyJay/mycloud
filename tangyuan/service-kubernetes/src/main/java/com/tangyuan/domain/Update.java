package com.tangyuan.domain;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：sunna
 * 时间: 2018/4/9 11:00
 */
public class Update
{
    @SerializedName("loc")
    private String loc;

    @SerializedName("utc")
    private String utc;

    public String getLoc()
    {
        return loc;
    }

    public void setLoc(String loc)
    {
        this.loc = loc;
    }

    public String getUtc()
    {
        return utc;
    }

    public void setUtc(String utc)
    {
        this.utc = utc;
    }
}
