package com.system.bhouse.bhouse.produce.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017-9-4.
 */

public class ProductionOrder {

    /**
     * ID : 16e4cf8dd69c47d294628dba8d05b519
     * 订单编号 : SCDD-201708-0001
     * 计划开始日期 : 2017/8/1 0:00:00
     * 计划完工日期 : 2017/8/4 0:00:00
     * 单据状态 : 审核
     * 制单人 : 管理员
     * 审核人 : 管理员
     */

    @SerializedName("ID")
    private String idcode;
    @SerializedName("订单编号")
    private String dingdanbianhao;
    @SerializedName("计划开始日期")
    private String startriqi;
    @SerializedName("计划完工日期")
    private String closeriqi;
    @SerializedName("单据状态")
    private String danstatus;
    @SerializedName("制单人")
    private String zhidanpeople;
    @SerializedName("审核人")
    private String shenhepeople;

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getDingdanbianhao() {
        return dingdanbianhao;
    }

    public void setDingdanbianhao(String dingdanbianhao) {
        this.dingdanbianhao = dingdanbianhao;
    }

    public String getStartriqi() {
        return startriqi;
    }

    public void setStartriqi(String startriqi) {
        this.startriqi = startriqi;
    }

    public String getCloseriqi() {
        return closeriqi;
    }

    public void setCloseriqi(String closeriqi) {
        this.closeriqi = closeriqi;
    }

    public String getDanstatus() {
        return danstatus;
    }

    public void setDanstatus(String danstatus) {
        this.danstatus = danstatus;
    }

    public String getZhidanpeople() {
        return zhidanpeople;
    }

    public void setZhidanpeople(String zhidanpeople) {
        this.zhidanpeople = zhidanpeople;
    }

    public String getShenhepeople() {
        return shenhepeople;
    }

    public void setShenhepeople(String shenhepeople) {
        this.shenhepeople = shenhepeople;
    }
}
