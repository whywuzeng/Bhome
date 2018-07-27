package com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ModuleAssignMent.Bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.system.bhouse.bean.BaseBean;

/**
 * Created by Administrator on 2018-07-25.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ModuleAssignMent.Bean
 */

public class ModuleAssignmentBean extends BaseBean implements Parcelable {

    /**
     * ID : 62a284e9de544d40919c9cc4f53892bc
     * 二维码 : DZXQ-7-201806-0009.1002.1084.0100.003.1
     * 分录ID : d0b4f64bb925467c89d920fd7ea3c996
     * 单据编号 : MJFP-7-201807-0004
     * 审核人 : 管理员
     * 审核时间 : 2018-07-13 09:56:14
     * 开始日期 : 2018/7/13 9:56:05
     * 录入人 : 管理员
     * 录入时间 : 2018/7/13 9:56:12
     * 数量 : 1.0
     * 模具ID : 11a5b5ccb081467ab80af1da6b05082f
     * 模具名称 : PC构件003
     * 源单ID : 85d0577ab174442380be9534839e521f
     * 物料ID : acb6fd62b2f0405292fe8c0de0737f2f
     * 物料名称 : PC构件
     * 物料编码 : 1002.1084.0100.003
     * 状态 : 审核
     * 结束日期 : 2018/7/13 10:16:05
     * 规格型号 : 测试
     * 计量单位 : 块
     * 计量单位ID : c8e082b5f5f34d5f934f071e6b464238
     * 订单ID : 808f2a114a354e4b956d0208b3cceacf
     */

    public String ID;
    @SerializedName("二维码")
    public String Qrcode;
    @SerializedName("分录ID")
    public String subDirectoryID;
    @SerializedName("单据编号")
    public String hNumbe;
    @SerializedName("开始日期")
    public String startTime;
    @SerializedName("数量")
    public double number;
    @SerializedName("模具ID")
    public String moduleID;
    @SerializedName("模具名称")
    public String moduleName;
    @SerializedName("源单ID")
    public String sourceID;
    @SerializedName("物料ID")
    public String materialID;
    @SerializedName("物料名称")
    public String materialName;
    @SerializedName("物料编码")
    public String materialCoding;
    @SerializedName("结束日期")
    public String endTime;
    @SerializedName("规格型号")
    public String specification;
    @SerializedName("计量单位")
    public String measureUnit;
    @SerializedName("计量单位ID")
    public String measureUnitID;
    @SerializedName("订单ID")
    public String orderID;

    @Override
    public String getStatus() {
        if (TextUtils.isEmpty(status)) {
            return "保存";
        }
        return status;
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

    public void setQrcode(String Qrcode) {
        this.Qrcode = Qrcode;
    }

    public String getSubDirectoryID() {
        return subDirectoryID;
    }

    public void setSubDirectoryID(String subDirectoryID) {
        this.subDirectoryID = subDirectoryID;
    }

    public String getHNumbe() {
        return hNumbe;
    }

    public void setHNumbe(String hNumbe) {
        this.hNumbe = hNumbe;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getSourceID() {
        return sourceID;
    }

    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.Qrcode);
        dest.writeString(this.subDirectoryID);
        dest.writeString(this.hNumbe);
        dest.writeString(this.startTime);
        dest.writeDouble(this.number);
        dest.writeString(this.moduleID);
        dest.writeString(this.moduleName);
        dest.writeString(this.sourceID);
        dest.writeString(this.materialID);
        dest.writeString(this.materialName);
        dest.writeString(this.materialCoding);
        dest.writeString(this.endTime);
        dest.writeString(this.specification);
        dest.writeString(this.measureUnit);
        dest.writeString(this.measureUnitID);
        dest.writeString(this.orderID);
        dest.writeByte(this.disableDelete ? (byte) 1 : (byte) 0);
        dest.writeString(this.requireDate);
        dest.writeString(this.description);
        dest.writeString(this.checkPeople);
        dest.writeString(this.checkTime);
        dest.writeString(this.entryPeople);
        dest.writeString(this.entryTime);
        dest.writeString(this.status);
    }

    public ModuleAssignmentBean() {
    }

    protected ModuleAssignmentBean(Parcel in) {
        this.ID = in.readString();
        this.Qrcode = in.readString();
        this.subDirectoryID = in.readString();
        this.hNumbe = in.readString();
        this.startTime = in.readString();
        this.number = in.readDouble();
        this.moduleID = in.readString();
        this.moduleName = in.readString();
        this.sourceID = in.readString();
        this.materialID = in.readString();
        this.materialName = in.readString();
        this.materialCoding = in.readString();
        this.endTime = in.readString();
        this.specification = in.readString();
        this.measureUnit = in.readString();
        this.measureUnitID = in.readString();
        this.orderID = in.readString();
        this.disableDelete = in.readByte() != 0;
        this.requireDate = in.readString();
        this.description = in.readString();
        this.checkPeople = in.readString();
        this.checkTime = in.readString();
        this.entryPeople = in.readString();
        this.entryTime = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<ModuleAssignmentBean> CREATOR = new Parcelable.Creator<ModuleAssignmentBean>() {
        @Override
        public ModuleAssignmentBean createFromParcel(Parcel source) {
            return new ModuleAssignmentBean(source);
        }

        @Override
        public ModuleAssignmentBean[] newArray(int size) {
            return new ModuleAssignmentBean[size];
        }
    };
}
