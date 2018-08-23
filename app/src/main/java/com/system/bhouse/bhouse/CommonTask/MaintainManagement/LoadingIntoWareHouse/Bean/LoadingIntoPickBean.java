package com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouse.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018-08-22.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouse.Bean
 */

public class LoadingIntoPickBean {

    /**
     * 备注 :
     * 层 : 4
     * 录入人 : 管理员
     * 录入时间 : 2018/7/4 17:36:48
     * 栋 : 1
     * 装柜日期 : 2018/9/4 17:35:19
     * 装车编号 : ZCDD-7-201807-0010
     * 订单ID : 224d3c3d9f55412a95ec7e9b880b695b
     * 货柜ID : 4f27210ad359472589490e0852034b59
     * 货柜名称 : 2号货柜
     * 项目 : 麓谷一期项目
     */

    @SerializedName("备注")
    private String beizhu;
    @SerializedName("层")
    private String ceng;
    @SerializedName("录入人")
    private String entryPeople;
    @SerializedName("录入时间")
    private String entryTime;
    @SerializedName("栋")
    private String dong;
    @SerializedName("装柜日期")
    private String containerDate;
    @SerializedName("装车编号")
    private String loadingCarNumber;
    @SerializedName("订单ID")
    private String orderID;
    @SerializedName("货柜ID")
    private String loadingCarID;
    @SerializedName("货柜名称")
    private String containerName;
    @SerializedName("项目")
    private String projectName;

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getCeng() {
        return ceng;
    }

    public void setCeng(String ceng) {
        this.ceng = ceng;
    }

    public String getEntryPeople() {
        return entryPeople;
    }

    public void setEntryPeople(String entryPeople) {
        this.entryPeople = entryPeople;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getDong() {
        return dong;
    }

    public void setDong(String dong) {
        this.dong = dong;
    }

    public String getContainerDate() {
        return containerDate;
    }

    public void setContainerDate(String containerDate) {
        this.containerDate = containerDate;
    }

    public String getLoadingCarNumber() {
        return loadingCarNumber;
    }

    public void setLoadingCarNumber(String loadingCarNumber) {
        this.loadingCarNumber = loadingCarNumber;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getLoadingCarID() {
        return loadingCarID;
    }

    public void setLoadingCarID(String loadingCarID) {
        this.loadingCarID = loadingCarID;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
