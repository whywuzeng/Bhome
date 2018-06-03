package com.system.bhouse.bean;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * ClassName: WeatherInfo<p>
 * Author:oubowu<p>
 * Fuction: 天气查询信息<p>
 * CreateDate:2016/2/14 0:41<p>
 * UpdateUser:<p>
 * UpdateDate:<p>
 */
public class WeatherInfo {


    @SerializedName("desc")
    public String desc;
    @SerializedName("status")
    public int status;

    @SerializedName("data")
    public DataEntity data;

    public static class DataEntity {

        @Override
        public String toString() {
            return "DataEntity{" +
                    "wendu='" + wendu + '\'' +
                    ", ganmao='" + ganmao + '\'' +
                    ", yesterday=" + yesterday +
                    ", aqi='" + aqi + '\'' +
                    ", city='" + city + '\'' +
                    ", forecast=" + forecast +
                    '}';
        }

        @SerializedName("wendu")
        public String wendu;
        @SerializedName("ganmao")
        public String ganmao;

        @SerializedName("yesterday")
        public YesterdayEntity yesterday;
        @SerializedName("aqi")
        public String aqi;
        @SerializedName("city")
        public String city;

        @SerializedName("forecast")
        public List<ForecastEntity> forecast;

        public static class YesterdayEntity {

            @Override
            public String toString() {
                return "YesterdayEntity{" +
                        "fl='" + fl + '\'' +
                        ", fx='" + fx + '\'' +
                        ", high='" + high + '\'' +
                        ", type='" + type + '\'' +
                        ", low='" + low + '\'' +
                        ", date='" + date + '\'' +
                        '}';
            }

            @SerializedName("fl")
            public String fl;
            @SerializedName("fx")
            public String fx;
            @SerializedName("high")
            public String high;
            @SerializedName("type")
            public String type;
            @SerializedName("low")
            public String low;
            @SerializedName("date")
            public String date;
        }

        public static class ForecastEntity {

            @Override
            public String toString() {
                return "ForecastEntity{" +
                        "fengxiang='" + fengxiang + '\'' +
                        ", fengli='" + fengli + '\'' +
                        ", high='" + high + '\'' +
                        ", type='" + type + '\'' +
                        ", low='" + low + '\'' +
                        ", date='" + date + '\'' +
                        '}';
            }

            @SerializedName("fengxiang")
            public String fengxiang;
            @SerializedName("fengli")
            public String fengli;
            @SerializedName("high")
            public String high;
            @SerializedName("type")
            public String type;
            @SerializedName("low")
            public String low;
            @SerializedName("date")
            public String date;
        }
    }
}
