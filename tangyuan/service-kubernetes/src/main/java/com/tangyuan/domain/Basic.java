package com.tangyuan.domain;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：sunna
 * 时间: 2018/4/9 10:59
 */

public class Basic
{
    @SerializedName("cid")
    private String cid;

    @SerializedName("location")
    private String location;

    @SerializedName("parent_city")
    private String parentCity;

    @SerializedName("admin_area")
    private String adminArea;

    @SerializedName("cnty")
    private String cnty;

    @SerializedName("lat")
    private String lat;

    @SerializedName("lon")
    private String lon;

    @SerializedName("tz")
    private String tz;

    public String getCid()
    {
        return cid;
    }

    public void setCid(String cid)
    {
        this.cid = cid;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getParentCity()
    {
        return parentCity;
    }

    public void setParentCity(String parentCity)
    {
        this.parentCity = parentCity;
    }

    public String getAdminArea()
    {
        return adminArea;
    }

    public void setAdminArea(String adminArea)
    {
        this.adminArea = adminArea;
    }

    public String getCnty()
    {
        return cnty;
    }

    public void setCnty(String cnty)
    {
        this.cnty = cnty;
    }

    public String getLat()
    {
        return lat;
    }

    public void setLat(String lat)
    {
        this.lat = lat;
    }

    public String getLon()
    {
        return lon;
    }

    public void setLon(String lon)
    {
        this.lon = lon;
    }

    public String getTz()
    {
        return tz;
    }

    public void setTz(String tz)
    {
        this.tz = tz;
    }

    @Override
    public String toString()
    {
        return "Basic{" +
                "cid='" + cid + '\'' +
                ", location='" + location + '\'' +
                ", parentCity='" + parentCity + '\'' +
                ", adminArea='" + adminArea + '\'' +
                ", cnty='" + cnty + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", tz='" + tz + '\'' +
                '}';
    }
}
