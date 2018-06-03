package com.system.bhouse.bean;

/**
 * Created by Administrator on 2016-3-25.
 */
public class YunJu {

    /**
     * did : 1
     * Disnum : 50KM
     * Disfy : 1000.0
     */

    private int did;
    private String Disnum;
    private double Disfy;

    public void setDid(int did) {
        this.did = did;
    }

    public void setDisnum(String Disnum) {
        this.Disnum = Disnum;
    }

    public void setDisfy(double Disfy) {
        this.Disfy = Disfy;
    }

    public int getDid() {
        return did;
    }

    public String getDisnum() {
        return Disnum;
    }

    public double getDisfy() {
        return Disfy;
    }
}
