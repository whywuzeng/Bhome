package com.system.bhouse.bean;

/**
 * Created by Administrator on 2016-3-25.
 */
public class Coname {

    /**
     * coid : 1
     * coname : 已下单
     * bl : 0.3
     * cno : 1
     */

    private int coid;
    private String coname;
    private double bl;
    private int cno;

    public void setCoid(int coid) {
        this.coid = coid;
    }

    public void setConame(String coname) {
        this.coname = coname;
    }

    public void setBl(double bl) {
        this.bl = bl;
    }

    public void setCno(int cno) {
        this.cno = cno;
    }

    public int getCoid() {
        return coid;
    }

    public String getConame() {
        return coname;
    }

    public double getBl() {
        return bl;
    }

    public int getCno() {
        return cno;
    }
}
