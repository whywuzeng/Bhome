package com.system.bhouse.bhouse.CommonTask.ProduceManagement.entity;

import com.google.gson.annotations.SerializedName;
import com.system.bhouse.bean.BaseBean;

/**
 * Created by Administrator on 2018-06-19.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.ProduceManagement.entity
 */

public class productionOrderBean extends BaseBean {

    /**
     * ID : 16c5357b9c6f41198c783cf755e9cf4c
     * 二维码 : DZXQ-7-201805-0002.1002.1084.0100.003.2
     * 分录ID : 6c563120ef4d46d9a75e8854200213bc
     * 单据编号 : SCDD-7-201806-0001
     * 审核人 : 管理员
     * 审核时间 : 2018-06-04 13:48:40
     * 录入人 : 管理员
     * 录入时间 : 2018/6/4 13:48:02
     * 数量 : 1.0
     * 来源类型 : MRP生成
     * 物料ID : acb6fd62b2f0405292fe8c0de0737f2f
     * 物料名称 : PC构件
     * 物料编码 : 1002.1084.0100.003
     * 状态 : 审核
     * 规格型号 : 测试
     * 计划开始日 : 2018/6/4 13:48:02
     * 计划结束日 : 2018/5/14 18:13:13
     * 计量单位 : 块
     * 计量单位ID : c8e082b5f5f34d5f934f071e6b464238
     */

    private String ID;
    @SerializedName("二维码")
    private String Qrcode;

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getMaterialsID() {
        return materialsID;
    }

    public void setMaterialsID(String materialsID) {
        this.materialsID = materialsID;
    }

    public String getMaterialsNames() {
        return materialsNames;
    }

    public void setMaterialsNames(String materialsNames) {
        this.materialsNames = materialsNames;
    }

    public String getMaterialsNumber() {
        return materialsNumber;
    }

    public void setMaterialsNumber(String materialsNumber) {
        this.materialsNumber = materialsNumber;
    }

    public String getSpecification() {
        return Specification;
    }

    public void setSpecification(String specification) {
        Specification = specification;
    }

    public String getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(String planStartDate) {
        this.planStartDate = planStartDate;
    }

    public String getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
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

    @SerializedName("分录ID")
    private String subDirectoryID;
    @SerializedName("单据编号")
    private String hNumbe;
    @SerializedName("数量")
    private double amount;
    @SerializedName("来源类型")
    private String sourceType;
    @SerializedName("物料ID")
    private String materialsID;
    @SerializedName("物料名称")
    private String materialsNames;
    @SerializedName("物料编码")
    private String materialsNumber;
    @SerializedName("规格型号")
    private String Specification;
    @SerializedName("计划开始日")
    private String planStartDate;
    @SerializedName("计划结束日")
    private String planEndDate;
    @SerializedName("计量单位")
    private String measureUnit;
    @SerializedName("计量单位ID")
    private String measureUnitID;


}
