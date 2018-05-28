package com.tangyuan.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：sunna
 * 时间: 2018/4/9 10:32
 */
public class Weather
{
    private Basic basic;

    @SerializedName("daily_forecast")
    private List<DailyForecast> dailyForecastList;

    private Update update;

    private String status;

    public Basic getBasic()
    {
        return basic;
    }

    public void setBasic(Basic basic)
    {
        this.basic = basic;
    }

    public List<DailyForecast> getDailyForecastList()
    {
        return dailyForecastList;
    }

    public void setDailyForecastList(List<DailyForecast> dailyForecastList)
    {
        this.dailyForecastList = dailyForecastList;
    }

    public Update getUpdate()
    {
        return update;
    }

    public void setUpdate(Update update)
    {
        this.update = update;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}
