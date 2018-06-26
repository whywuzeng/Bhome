package com.system.bhouse.bhouse.CommonTask.MaterialControl.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.system.bhouse.bean.BaseBean;

/**
 * Created by Administrator on 2018-06-26.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.MaterialControl.entity
 */

public class FinishedStorageBean extends BaseBean implements Parcelable {

    /**
     * ID : fe4d011a135347ce96266e050cf44a36
     * 业务日期 : 2018/6/5 14:36:45
     * 仓库ID : c7762521eb4e466a9ca517aca0bb881b
     * 仓库名称 : 1号库位
     * 分录ID : e7b88e8ff24b4019b27cf5ae5dbff068
     * 单据编号 : SCDDRK-7-201806-0001
     * 审核人 : 管理员
     * 审核时间 : 2018-06-05 14:36:53
     * 录入人 : 管理员
     * 录入时间 : 2018/6/5 14:36:51
     * 数量 : 9
     * 来源单据ID : cc0161a8bdb344a2b7dfacd685180e24
     * 来源类型 : 生产订单
     * 来源类型ID : 62d7891cb1c84121ae72abb5015abc99
     * 来源类型表 : Production_order_r
     * 物料ID : 88e3a145827f4dccb526ab4859417d48
     * 物料二维码 : 2000101001
     * 物料名称 : 网片
     * 物料编码 : 2000101001
     * 状态 : 审核
     * 规格型号 : @8
     * 计量单位 : 根
     * 计量单位ID : acd006b1fe8d43b4bb24c27071a88503
     * 订单ID : 62d7891cb1c84121ae72abb5015abc99
     */

    public String ID;
    @SerializedName("仓库ID")
    public String wareHouseID;
    @SerializedName("仓库名称")
    public String wareHouseName;
    @SerializedName("分录ID")
    public String subDirectoryID;
    @SerializedName(value = "单据编号",alternate = {"入库单编号"})
    public String hNumbe;
    @SerializedName("数量")
    public int amount;
    @SerializedName("来源单据ID")
    public String sourceTableID;
    @SerializedName("来源类型")
    public String sourceType;
    @SerializedName("来源类型ID")
    public String sourceTypeID;
    @SerializedName("来源类型表")
    public String sourceTypeTableName;
    @SerializedName("物料ID")
    public String materialsID;
    @SerializedName("物料二维码")
    public String materialsQrcode;
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

    @SerializedName("生产订单号")
    public String oriderNumber;

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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getSourceTableID() {
        return sourceTableID;
    }

    public void setSourceTableID(String sourceTableID) {
        this.sourceTableID = sourceTableID;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceTypeID() {
        return sourceTypeID;
    }

    public void setSourceTypeID(String sourceTypeID) {
        this.sourceTypeID = sourceTypeID;
    }

    public String getSourceTypeTableName() {
        return sourceTypeTableName;
    }

    public void setSourceTypeTableName(String sourceTypeTableName) {
        this.sourceTypeTableName = sourceTypeTableName;
    }

    public String getMaterialsID() {
        return materialsID;
    }

    public void setMaterialsID(String materialsID) {
        this.materialsID = materialsID;
    }

    public String getMaterialsQrcode() {
        return materialsQrcode;
    }

    public void setMaterialsQrcode(String materialsQrcode) {
        this.materialsQrcode = materialsQrcode;
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
        dest.writeInt(this.amount);
        dest.writeString(this.sourceTableID);
        dest.writeString(this.sourceType);
        dest.writeString(this.sourceTypeID);
        dest.writeString(this.sourceTypeTableName);
        dest.writeString(this.materialsID);
        dest.writeString(this.materialsQrcode);
        dest.writeString(this.materialsNames);
        dest.writeString(this.materialsNumber);
        dest.writeString(this.Specification);
        dest.writeString(this.measureUnit);
        dest.writeString(this.measureUnitID);
        dest.writeString(this.oriderID);
        dest.writeString(this.oriderNumber);
        dest.writeByte(this.disableDelete ? (byte) 1 : (byte) 0);
        dest.writeString(this.requireDate);
        dest.writeString(this.description);
        dest.writeString(this.checkPeople);
        dest.writeString(this.checkTime);
        dest.writeString(this.entryPeople);
        dest.writeString(this.entryTime);
        dest.writeString(this.status);
    }

    public FinishedStorageBean() {
    }

    protected FinishedStorageBean(Parcel in) {
        this.ID = in.readString();
        this.wareHouseID = in.readString();
        this.wareHouseName = in.readString();
        this.subDirectoryID = in.readString();
        this.hNumbe = in.readString();
        this.amount = in.readInt();
        this.sourceTableID = in.readString();
        this.sourceType = in.readString();
        this.sourceTypeID = in.readString();
        this.sourceTypeTableName = in.readString();
        this.materialsID = in.readString();
        this.materialsQrcode = in.readString();
        this.materialsNames = in.readString();
        this.materialsNumber = in.readString();
        this.Specification = in.readString();
        this.measureUnit = in.readString();
        this.measureUnitID = in.readString();
        this.oriderID = in.readString();
        this.oriderNumber = in.readString();
        this.disableDelete = in.readByte() != 0;
        this.requireDate = in.readString();
        this.description = in.readString();
        this.checkPeople = in.readString();
        this.checkTime = in.readString();
        this.entryPeople = in.readString();
        this.entryTime = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<FinishedStorageBean> CREATOR = new Parcelable.Creator<FinishedStorageBean>() {
        @Override
        public FinishedStorageBean createFromParcel(Parcel source) {
            return new FinishedStorageBean(source);
        }

        @Override
        public FinishedStorageBean[] newArray(int size) {
            return new FinishedStorageBean[size];
        }
    };
}
