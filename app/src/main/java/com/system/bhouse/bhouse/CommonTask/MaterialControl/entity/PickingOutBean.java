package com.system.bhouse.bhouse.CommonTask.MaterialControl.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.system.bhouse.bean.BaseBean;

/**
 * Created by Administrator on 2018-06-22.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.MaterialControl.entity
 */

public class PickingOutBean extends BaseBean implements Parcelable {

    /**
     * ID : b0b74d7f70874b3999781d0302a70d4e
     * 业务日期 : 2018/6/7 17:41:48
     * 仓库ID : c7762521eb4e466a9ca517aca0bb881b
     * 仓库名称 : 1号库位
     * 分录ID : 7ebad28671844bf18895fdee534d5cb8
     * 单据编号 : SCDDLL-7-201806-0003
     * 审核人 : 管理员
     * 审核时间 : 2018-06-07 17:42:01
     * 录入人 : 管理员
     * 录入时间 : 2018/6/7 17:41:58
     * 数量 : 6.0
     * 来源单据ID : 86e6285326b34318b87c1c3d3e1d6329
     * 物料ID : 5fdd52b4b7c246a7a3e3ca95cb23fb35
     * 物料名称 : SC钢管锁扣
     * 物料编码 : 3000402149
     * 状态 : 审核
     * 规格型号 : φ20
     * 计量单位 : 根
     * 计量单位ID : acd006b1fe8d43b4bb24c27071a88503
     * 订单ID : 16c5357b9c6f41198c783cf755e9cf4c
     */

    public String ID;
    @SerializedName("仓库ID")
    public String wareHouseID;
    @SerializedName("仓库名称")
    public String wareHouseName;
    @SerializedName("分录ID")
    public String subDirectoryID;
    @SerializedName("单据编号")
    public String hNumbe;
    @SerializedName("数量")
    public double amount;
    @SerializedName(value = "来源单据ID",alternate = {"源单ID"})
    public String sourceTableID;
    @SerializedName("物料ID")
    public String materialsID;
    @SerializedName("物料名称")
    public String materialsNames;
    @SerializedName("物料编码")
    public String materialsNumber;
    @SerializedName("规格型号")
    public String Specification;
    @SerializedName("计量单位")
    public String measureUnit;
    @SerializedName("计量单位ID")
    public String measureUnitID;
    @SerializedName("订单ID")
    public String oriderID;

    @SerializedName("领料单编号")
    public String pickingOriderID;

    @SerializedName("生产订单号")
    public String productionOriderID;

    public String getProductionOriderID() {
        return productionOriderID;
    }

    public void setProductionOriderID(String productionOriderID) {
        this.productionOriderID = productionOriderID;
    }

    public String getPickingOriderID() {
        return pickingOriderID;
    }

    public void setPickingOriderID(String pickingOriderID) {
        this.pickingOriderID = pickingOriderID;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getWareHouseID() {
        return wareHouseID;
    }

    public void setWareHouseID(String wareHouseID) {
        this.wareHouseID = wareHouseID;
    }

    public String getWareHouseName() {
        return wareHouseName;
    }

    public void setWareHouseName(String wareHouseName) {
        this.wareHouseName = wareHouseName;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSourceTableID() {
        return sourceTableID;
    }

    public void setSourceTableID(String sourceTableID) {
        this.sourceTableID = sourceTableID;
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
        dest.writeString(this.wareHouseID);
        dest.writeString(this.wareHouseName);
        dest.writeString(this.subDirectoryID);
        dest.writeString(this.hNumbe);
        dest.writeDouble(this.amount);
        dest.writeString(this.sourceTableID);
        dest.writeString(this.materialsID);
        dest.writeString(this.materialsNames);
        dest.writeString(this.materialsNumber);
        dest.writeString(this.Specification);
        dest.writeString(this.measureUnit);
        dest.writeString(this.measureUnitID);
        dest.writeString(this.oriderID);
        dest.writeString(this.productionOriderID);
        dest.writeString(this.pickingOriderID);
        dest.writeByte(this.disableDelete ? (byte) 1 : (byte) 0);
        dest.writeString(this.requireDate);
        dest.writeString(this.description);
        dest.writeString(this.checkPeople);
        dest.writeString(this.checkTime);
        dest.writeString(this.entryPeople);
        dest.writeString(this.entryTime);
        dest.writeString(this.status);
    }

    public PickingOutBean() {
    }

    protected PickingOutBean(Parcel in) {
        this.ID = in.readString();
        this.wareHouseID = in.readString();
        this.wareHouseName = in.readString();
        this.subDirectoryID = in.readString();
        this.hNumbe = in.readString();
        this.amount = in.readDouble();
        this.sourceTableID = in.readString();
        this.materialsID = in.readString();
        this.materialsNames = in.readString();
        this.materialsNumber = in.readString();
        this.Specification = in.readString();
        this.measureUnit = in.readString();
        this.measureUnitID = in.readString();
        this.oriderID = in.readString();
        this.productionOriderID = in.readString();
        this.pickingOriderID = in.readString();
        this.disableDelete = in.readByte() != 0;
        this.requireDate = in.readString();
        this.description = in.readString();
        this.checkPeople = in.readString();
        this.checkTime = in.readString();
        this.entryPeople = in.readString();
        this.entryTime = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<PickingOutBean> CREATOR = new Parcelable.Creator<PickingOutBean>() {
        @Override
        public PickingOutBean createFromParcel(Parcel source) {
            return new PickingOutBean(source);
        }

        @Override
        public PickingOutBean[] newArray(int size) {
            return new PickingOutBean[size];
        }
    };
}
