package com.system.bhouse.bhouse.produce.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017-9-5.
 */

public class ProductionOrderDetail {

    /**
     * ID : 16e4cf8dd69c47d294628dba8d05b519
     * 订单编号 : SCDD-201708-0001
     * 计划开始日期 : 2017/8/1 0:00:00
     * 计划完工日期 : 2017/8/4 0:00:00
     * 单据状态 : 审核
     * 制单人 : 管理员
     * 审核人 : 管理员
     * 分录ID : e6d5e7c65afe48c29fde0620c92bc15c
     * 物料编码 : 100001001
     * 物料名称 : 测试物料
     * 规格型号 : 1x1
     * 计量单位 : 根
     * 数量 : 2
     */

    @SerializedName("ID")
    private String saomaoID;
    @SerializedName("订单编号")
    private String DanCode;
    @SerializedName("计划开始日期")
    private String planstartdate;
    @SerializedName("计划完工日期")
    private String planclosedate;
    @SerializedName("单据状态")
    private String danStatus;
    @SerializedName("制单人")
    private String zhidanpeople;
    @SerializedName("审核人")
    private String shenhepeople;
    @SerializedName("分录ID")
    private String fenluID;
    @SerializedName("物料编码")
    private String wuliaoCode;
    @SerializedName("物料名称")
    private String wuliaoName;
    @SerializedName("规格型号")
    private String guigexinghao;
    @SerializedName("计量单位")
    private String jiliangdanwei;
    @SerializedName("数量")
    private int sum;

    public String getSaomaoID() {
        return saomaoID;
    }

    public void setSaomaoID(String saomaoID) {
        this.saomaoID = saomaoID;
    }

    public String getDanCode() {
        return DanCode;
    }

    public void setDanCode(String DanCode) {
        this.DanCode = DanCode;
    }

    public String getPlanstartdate() {
        return planstartdate;
    }

    public void setPlanstartdate(String planstartdate) {
        this.planstartdate = planstartdate;
    }

    public String getPlanclosedate() {
        return planclosedate;
    }

    public void setPlanclosedate(String planclosedate) {
        this.planclosedate = planclosedate;
    }

    public String getDanStatus() {
        return danStatus;
    }

    public void setDanStatus(String danStatus) {
        this.danStatus = danStatus;
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

    public String getFenluID() {
        return fenluID;
    }

    public void setFenluID(String fenluID) {
        this.fenluID = fenluID;
    }

    public String getWuliaoCode() {
        return wuliaoCode;
    }

    public void setWuliaoCode(String wuliaoCode) {
        this.wuliaoCode = wuliaoCode;
    }

    public String getWuliaoName() {
        return wuliaoName;
    }

    public void setWuliaoName(String wuliaoName) {
        this.wuliaoName = wuliaoName;
    }

    public String getGuigexinghao() {
        return guigexinghao;
    }

    public void setGuigexinghao(String guigexinghao) {
        this.guigexinghao = guigexinghao;
    }

    public String getJiliangdanwei() {
        return jiliangdanwei;
    }

    public void setJiliangdanwei(String jiliangdanwei) {
        this.jiliangdanwei = jiliangdanwei;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
