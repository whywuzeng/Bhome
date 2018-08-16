package com.system.bhouse.bhouse.CommonTask.TechnologyExecution.entity;

import com.google.gson.annotations.SerializedName;
import com.system.bhouse.bean.BaseBean;

/**
 * Created by Administrator on 2018-07-31.
 * <p>
 * by author wz
 * 针对 工艺执行 bean
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution.entity
 */

public class TechnologyBaseBean extends BaseBean {
    @SerializedName(value = "ID",alternate = {"单据ID"})
    public String ID;
    @SerializedName(value = "二维码",alternate = {"构件二维码","物料二维码"})
    public String Qrcode;
    @SerializedName("分录ID")
    public String subDirectoryID;
    @SerializedName(value = "单据编号",alternate = {"台车模具分离单编号"})
    public String hNumbe="";
    @SerializedName(value = "开始日期" ,alternate = {""})
    public String startTime;
    @SerializedName("结束日期")
    public String endTime;
    @SerializedName("订单ID")
    public String orderId;
    @SerializedName(value = "计划开始日期",alternate = {"计划开始时间"})
    public String plannedStartDate;
    @SerializedName(value = "计划结束日期",alternate = {"计划结束时间"})
    public String plannedEndDate;
    @SerializedName("定额时间")
    public String quotaTime;
    @SerializedName("数量")
    public double number;
    @SerializedName(value = "源单ID",alternate = {"来源单据ID"})
    public String sourceID;
    @SerializedName("规格型号")
    public String specification;


    @SerializedName("计量单位")

    public String measureUnit;
    @SerializedName("计量单位ID")
    public String measureUnitID;

    /**
     *
     */
    @SerializedName("物料ID")
    public String materialID;
    @SerializedName("物料名称")
    public String materialName;
    @SerializedName("物料编码")
    public String materialCoding;

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public String getSourceID() {
        return sourceID;
    }

    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public String getMeasureUnitID() {
        return measureUnitID;
    }

    public void setMeasureUnitID(String measureUnitID) {
        this.measureUnitID = measureUnitID;
    }

    public String getMaterialID() {
        return materialID;
    }

    public void setMaterialID(String materialID) {
        this.materialID = materialID;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialCoding() {
        return materialCoding;
    }

    public void setMaterialCoding(String materialCoding) {
        this.materialCoding = materialCoding;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getQrcode() {
        return Qrcode;
    }

    public void setQrcode(String qrcode) {
        Qrcode = qrcode;
    }

    public String getSubDirectoryID() {
        return subDirectoryID;
    }

    public void setSubDirectoryID(String subDirectoryID) {
        this.subDirectoryID = subDirectoryID;
    }

    public String gethNumbe() {
        return hNumbe;
    }

    public void sethNumbe(String hNumbe) {
        this.hNumbe = hNumbe;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPlannedStartDate() {
        return plannedStartDate;
    }

    public void setPlannedStartDate(String plannedStartDate) {
        this.plannedStartDate = plannedStartDate;
    }

    public String getPlannedEndDate() {
        return plannedEndDate;
    }

    public void setPlannedEndDate(String plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
    }

    public String getQuotaTime() {
        return quotaTime;
    }

    public void setQuotaTime(String quotaTime) {
        this.quotaTime = quotaTime;
    }

}
