package com.system.bhouse.bhouse.CommonTask.MaterialControl.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.system.bhouse.bean.BaseBean;
import com.system.bhouse.utils.TenUtils.DataFormatUtils;

/**
 * Created by Administrator on 2018-06-28.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.MaterialControl.entity
 */

public class PlatematerialBean extends BaseBean implements Parcelable {

    /**
     * ID : fe6e3bb77d4a4b01a1b3426083a8e7e7
     * 分录ID : 5691bf24635c4d45800638825a44c0a6
     * 单据编号 : TPFP-7-201806-0004
     * 台车ID : 71d35dc9de594b209a6365a6f7da27bc
     * 台车名称 : 10号台车
     * 审核人 :
     * 审核时间 :
     * 开始日期 : 2018/6/28 16:50:33
     * 录入人 : 管理员
     * 录入时间 : 2018/6/28 16:50:44
     * 托盘ID : 0aea156d7a494628a2f8759f1b047bf5
     * 托盘名称 : 托盘3
     * 数量 : 2.0
     * 来源单据ID : 44ac0177c8c64388bcd081b914893ab0
     * 构件二维码 : DZXQ-7-201806-0009.1002.1084.0100.003.1
     * 标记号 : DZXQ-7-201806-0009.1002.1084.0100.003.13000402149
     * 模具ID : 0d041a0b8d604eda8eef762bb4a9e8d5
     * 模具名称 : PC构件004
     * 物料ID : 5fdd52b4b7c246a7a3e3ca95cb23fb35
     * 物料名称 : SC钢管锁扣
     * 物料编码 : 3000402149
     * 状态 : 保存
     * 结束日期 : 2018/6/29 16:50:33
     * 规格型号 : φ20
     * 计量单位 : 根
     * 计量单位ID : acd006b1fe8d43b4bb24c27071a88503
     * 订单ID : 808f2a114a354e4b956d0208b3cceacf
     */

    public String ID;
    @SerializedName("分录ID")
    public String subDirectoryID;
    @SerializedName(value = "单据编号",alternate = {"托盘配料单编号"})
    public String hNumbe;
    @SerializedName("台车ID")
    public String stationCarID;
    @SerializedName("台车名称")
    public String stationCarName;
    @SerializedName("开始日期")
    public String planStartDate;
    @SerializedName("托盘ID")
    public String plateID;
    @SerializedName(value = "托盘名称",alternate = {"托盘"})
    public String plateName;
    @SerializedName("数量")
    public double amount;
    @SerializedName("来源单据ID")
    public String sourceTypeID;
    @SerializedName("构件二维码")
    public String componentQrcode;
    @SerializedName("标记号")
    public String tagNumber;
    @SerializedName("模具ID")
    public String moduleID;
    @SerializedName("模具名称")
    public String moduleName;
    @SerializedName("物料ID")
    public String materialsID;
    @SerializedName("物料名称")
    public String materialsNames;
    @SerializedName("物料编码")
    public String materialsNumber;
    @SerializedName("结束日期")
    public String planendDate;
    @SerializedName("规格型号")
    public String Specification;
    @SerializedName("计量单位")
    public String measureUnit;
    @SerializedName("计量单位ID")
    public String measureUnitID;
    @SerializedName("订单ID")
    public String oriderID;

    @SerializedName("生产订单号")
    public String producationOrderNumber;


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public String getStationCarID() {
        return stationCarID;
    }

    public void setStationCarID(String stationCarID) {
        this.stationCarID = stationCarID;
    }

    public String getStationCarName() {
        return stationCarName;
    }

    public void setStationCarName(String stationCarName) {
        this.stationCarName = stationCarName;
    }

    public String getPlanStartDate() {

        if (TextUtils.isEmpty(planStartDate)) {
            return DataFormatUtils.getCurrentTime();
        }
        return planStartDate;
    }

    public void setPlanStartDate(String planStartDate) {
        this.planStartDate = planStartDate;
    }

    public String getPlateID() {
        return plateID;
    }

    public void setPlateID(String plateID) {
        this.plateID = plateID;
    }

    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSourceTypeID() {
        return sourceTypeID;
    }

    public void setSourceTypeID(String sourceTypeID) {
        this.sourceTypeID = sourceTypeID;
    }

    public String getComponentQrcode() {
        return componentQrcode;
    }

    public void setComponentQrcode(String componentQrcode) {
        this.componentQrcode = componentQrcode;
    }

    public String getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(String tagNumber) {
        this.tagNumber = tagNumber;
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

    public String getPlanendDate() {

        if (TextUtils.isEmpty(planendDate)) {
            return DataFormatUtils.getCurrentTimeAddOneDay();
        }
        return planendDate;
    }

    public void setPlanendDate(String planendDate) {
        this.planendDate = planendDate;
    }

    public String getSpecification() {
        return Specification;
    }

    public void setSpecification(String Specification) {
        this.Specification = Specification;
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

    public String getOriderID() {
        return oriderID;
    }

    public void setOriderID(String oriderID) {
        this.oriderID = oriderID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.subDirectoryID);
        dest.writeString(this.hNumbe);
        dest.writeString(this.stationCarID);
        dest.writeString(this.stationCarName);
        dest.writeString(this.planStartDate);
        dest.writeString(this.plateID);
        dest.writeString(this.plateName);
        dest.writeDouble(this.amount);
        dest.writeString(this.sourceTypeID);
        dest.writeString(this.componentQrcode);
        dest.writeString(this.tagNumber);
        dest.writeString(this.moduleID);
        dest.writeString(this.moduleName);
        dest.writeString(this.materialsID);
        dest.writeString(this.materialsNames);
        dest.writeString(this.materialsNumber);
        dest.writeString(this.planendDate);
        dest.writeString(this.Specification);
        dest.writeString(this.measureUnit);
        dest.writeString(this.measureUnitID);
        dest.writeString(this.oriderID);
        dest.writeString(this.producationOrderNumber);
        dest.writeByte(this.disableDelete ? (byte) 1 : (byte) 0);
        dest.writeString(this.requireDate);
        dest.writeString(this.description);
        dest.writeString(this.checkPeople);
        dest.writeString(this.checkTime);
        dest.writeString(this.entryPeople);
        dest.writeString(this.entryTime);
        dest.writeString(this.status);
    }

    public PlatematerialBean() {
    }

    protected PlatematerialBean(Parcel in) {
        this.ID = in.readString();
        this.subDirectoryID = in.readString();
        this.hNumbe = in.readString();
        this.stationCarID = in.readString();
        this.stationCarName = in.readString();
        this.planStartDate = in.readString();
        this.plateID = in.readString();
        this.plateName = in.readString();
        this.amount = in.readDouble();
        this.sourceTypeID = in.readString();
        this.componentQrcode = in.readString();
        this.tagNumber = in.readString();
        this.moduleID = in.readString();
        this.moduleName = in.readString();
        this.materialsID = in.readString();
        this.materialsNames = in.readString();
        this.materialsNumber = in.readString();
        this.planendDate = in.readString();
        this.Specification = in.readString();
        this.measureUnit = in.readString();
        this.measureUnitID = in.readString();
        this.oriderID = in.readString();
        this.producationOrderNumber = in.readString();
        this.disableDelete = in.readByte() != 0;
        this.requireDate = in.readString();
        this.description = in.readString();
        this.checkPeople = in.readString();
        this.checkTime = in.readString();
        this.entryPeople = in.readString();
        this.entryTime = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<PlatematerialBean> CREATOR = new Parcelable.Creator<PlatematerialBean>() {
        @Override
        public PlatematerialBean createFromParcel(Parcel source) {
            return new PlatematerialBean(source);
        }

        @Override
        public PlatematerialBean[] newArray(int size) {
            return new PlatematerialBean[size];
        }
    };
}
