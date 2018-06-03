package com.system.bhouse.bean;

/**
 * Created by Administrator on 2016-3-25.
 */
public class Shengfen {

    @Override
    public String toString() {
        return "Shengfen{" +
                "aid=" + aid +
                ", area='" + area + '\'' +
                '}';
    }

    /**
     * aid : 1
     * area : 湖南
     */
    private int aid;
    private String area;

    public void setAid(int aid) {
        this.aid = aid;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getAid() {
        return aid;
    }

    public String getArea() {
        return area;
    }
}
