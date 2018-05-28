package com.tangyuan.domain;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：sunna
 * 时间: 2018/4/9 10:59
 */
public class DailyForecast
{
    @SerializedName("cond_code_d")
    private String condCodeD;

    @SerializedName("cond_code_n")
    private String condCodeN;

    @SerializedName("cond_txt_d")
    private String condTxtD;

    @SerializedName("cond_txt_n")
    private String condTxtN;

    @SerializedName("date")
    private String date;

    @SerializedName("pop")
    private String pop;

    @SerializedName("pres")
    private String pres;

    @SerializedName("tmp_max")
    private String tmpMax;

    @SerializedName("tmp_min")
    private String tmpMin;

    @SerializedName("uv_index")
    private String uvIndex;

    @SerializedName("vis")
    private String vis;

    @SerializedName("wind_deg")
    private String windDeg;

    @SerializedName("wind_dir")
    private String windDir;

    @SerializedName("wind_sc")
    private String windSc;

    @SerializedName("wind_spd")
    private String windSpd;

    public String getCondCodeD()
    {
        return condCodeD;
    }

    public void setCondCodeD(String condCodeD)
    {
        this.condCodeD = condCodeD;
    }

    public String getCondCodeN()
    {
        return condCodeN;
    }

    public void setCondCodeN(String condCodeN)
    {
        this.condCodeN = condCodeN;
    }

    public String getCondTxtD()
    {
        return condTxtD;
    }

    public void setCondTxtD(String condTxtD)
    {
        this.condTxtD = condTxtD;
    }

    public String getCondTxtN()
    {
        return condTxtN;
    }

    public void setCondTxtN(String condTxtN)
    {
        this.condTxtN = condTxtN;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getPop()
    {
        return pop;
    }

    public void setPop(String pop)
    {
        this.pop = pop;
    }

    public String getPres()
    {
        return pres;
    }

    public void setPres(String pres)
    {
        this.pres = pres;
    }

    public String getTmpMax()
    {
        return tmpMax;
    }

    public void setTmpMax(String tmpMax)
    {
        this.tmpMax = tmpMax;
    }

    public String getTmpMin()
    {
        return tmpMin;
    }

    public void setTmpMin(String tmpMin)
    {
        this.tmpMin = tmpMin;
    }

    public String getUvIndex()
    {
        return uvIndex;
    }

    public void setUvIndex(String uvIndex)
    {
        this.uvIndex = uvIndex;
    }

    public String getVis()
    {
        return vis;
    }

    public void setVis(String vis)
    {
        this.vis = vis;
    }

    public String getWindDeg()
    {
        return windDeg;
    }

    public void setWindDeg(String windDeg)
    {
        this.windDeg = windDeg;
    }

    public String getWindDir()
    {
        return windDir;
    }

    public void setWindDir(String windDir)
    {
        this.windDir = windDir;
    }

    public String getWindSc()
    {
        return windSc;
    }

    public void setWindSc(String windSc)
    {
        this.windSc = windSc;
    }

    public String getWindSpd()
    {
        return windSpd;
    }

    public void setWindSpd(String windSpd)
    {
        this.windSpd = windSpd;
    }
}
