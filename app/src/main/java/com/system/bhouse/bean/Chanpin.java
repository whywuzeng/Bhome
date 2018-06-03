package com.system.bhouse.bean;

/**
 * Created by Administrator on 2016-3-25.
 */
public class Chanpin {

    /**
     * pid : 1
     * pnumber : 03002
     * pmodel : A3
     * pname : 美式别墅
     */

    private int pid;
    private String pnumber;
    private String pmodel;
    private String pname;

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setPnumber(String pnumber) {
        this.pnumber = pnumber;
    }

    public void setPmodel(String pmodel) {
        this.pmodel = pmodel;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getPid() {
        return pid;
    }

    public String getPnumber() {
        return pnumber;
    }

    public String getPmodel() {
        return pmodel;
    }

    public String getPname() {
        return pname;
    }
}
