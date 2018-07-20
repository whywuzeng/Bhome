package com.system.bhouse.bhouse.CommonTask.TechnologyExecution.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018-07-11.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution.entity
 */

public class TechnologyBean {

    /**
     * 人员定额 : 2.0
     * 关联单据 : true
     * 备注 :
     * 工序ID : 5a878c251d68483480d210126d485a84
     * 工序名称 : 布模
     * 工序状态 : 执行中
     * 工序编号 : 001
     * 工序路线分录ID : 8271764d618b4a518009a365ffc59356
     * 工序顺序号 : 1
     * 工艺路线ID : bf60c6abe2fc4c208e04d027776a9137
     * 开始时间 : 2018-07-04 17:41:04
     * 构件二维码 : DZXQ-7-201805-0003.1002.1084.0200.003.2
     * 物料名称 : 外墙板
     * 物料编码 : 1002.1084.0200.003
     * 结束时间 :
     * 耗时定额 : 15.0
     * 规格型号 : 5*90
     * 计量单位 : 块
     */

    @SerializedName("人员定额")
    public double personStandard;
    @SerializedName("备注")
    public String beizhu;
    @SerializedName("工序ID")
    public String workOrderID;
    @SerializedName("工序名称")
    public String workOrderName;
    @SerializedName("工序状态")
    public String workOrderStatus;
    @SerializedName("工序编号")
    public String workOrderNumber;
    @SerializedName("工序路线分录ID")
    public String workOrdersubDirectoryID;
    @SerializedName("工序顺序号")
    public int workOrderSequence;
    @SerializedName("工艺路线ID")
    public String workRouteID;
    @SerializedName("开始时间")
    public String startTime;
    @SerializedName("构件二维码")
    public String componentQrcode;
    @SerializedName("物料名称")
    public String materialName;
    @SerializedName("物料编码")
    public String materialCoding;
    @SerializedName("结束时间")
    public String endTime;
    @SerializedName("耗时定额")
    public double useTimeStandard;
    @SerializedName("规格型号")
    public String specification;
    @SerializedName("计量单位")
    public String measureUnit;

    @SerializedName("关联单据")
    public boolean isRelateForm;

    @SerializedName("工序挂起")
    public boolean isHang;

    public double getPersonStandard() {
        return personStandard;
    }

    public void setPersonStandard(double personStandard) {
        this.personStandard = personStandard;
    }


    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getWorkOrderID() {
        return workOrderID;
    }

    public void setWorkOrderID(String workOrderID) {
        this.workOrderID = workOrderID;
    }

    public String getWorkOrderName() {
        return workOrderName;
    }

    public void setWorkOrderName(String workOrderName) {
        this.workOrderName = workOrderName;
    }

    public String getWorkOrderStatus() {
        return workOrderStatus;
    }

    public void setWorkOrderStatus(String workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
    }

    public String getWorkOrderNumber() {
        return workOrderNumber;
    }

    public void setWorkOrderNumber(String workOrderNumber) {
        this.workOrderNumber = workOrderNumber;
    }

    public String getWorkOrdersubDirectoryID() {
        return workOrdersubDirectoryID;
    }

    public void setWorkOrdersubDirectoryID(String workOrdersubDirectoryID) {
        this.workOrdersubDirectoryID = workOrdersubDirectoryID;
    }

    public int getWorkOrderSequence() {
        return workOrderSequence;
    }

    public void setWorkOrderSequence(int workOrderSequence) {
        this.workOrderSequence = workOrderSequence;
    }

    public String getWorkRouteID() {
        return workRouteID;
    }

    public void setWorkRouteID(String workRouteID) {
        this.workRouteID = workRouteID;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getComponentQrcode() {
        return componentQrcode;
    }

    public void setComponentQrcode(String componentQrcode) {
        this.componentQrcode = componentQrcode;
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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getUseTimeStandard() {
        return useTimeStandard;
    }

    public void setUseTimeStandard(double useTimeStandard) {
        this.useTimeStandard = useTimeStandard;
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
}
