package com.system.bhouse.bean;

/**
 * Created by Administrator on 2016-3-25.
 */
public class Kehu {

    @Override
    public String toString() {
        return "Kehu{" +
                "cid=" + cid +
                ", cnumber='" + cnumber + '\'' +
                ", cname='" + cname + '\'' +
                ", cper='" + cper + '\'' +
                ", cphoe='" + cphoe + '\'' +
                '}';
    }

    /**
     * cid : 1
     * cnumber : 010005
     * cname : 湖北建筑公司
     * cper : 黄先生
     * cphoe : 15773114241
     */

    private int cid;
    private String cnumber;
    private String cname;
    private String cper;
    private String cphoe;

    public void setCid(int cid) {
        this.cid = cid;
    }

    public void setCnumber(String cnumber) {
        this.cnumber = cnumber;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setCper(String cper) {
        this.cper = cper;
    }

    public void setCphoe(String cphoe) {
        this.cphoe = cphoe;
    }

    public int getCid() {
        return cid;
    }

    public String getCnumber() {
        return cnumber;
    }

    public String getCname() {
        return cname;
    }

    public String getCper() {
        return cper;
    }

    public String getCphoe() {
        return cphoe;
    }
}
