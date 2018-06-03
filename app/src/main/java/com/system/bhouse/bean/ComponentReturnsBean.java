package com.system.bhouse.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018-05-02.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bean
 */

public class ComponentReturnsBean extends BaseBean implements Parcelable {

    /**
     * ID : cc5e17a438df48b8afc3f59dda268a14
     * 业务日期 : 2018/5/7 9:12:53
     * 二维码 : DZXQ-7-201803-0001.1002.1084.0100.003.2
     * 仓库ID : c7762521eb4e466a9ca517aca0bb881b
     * 仓库名称 : 1号库位
     * 分录ID : 52255a960e01490f8b1d9225d534f0a4
     * 分录备注 :
     * 单据编号 : THZJRK-7-201805-0001
     * 审核人 :
     * 审核时间 :
     * 层 : 2
     * 层ID : 998db5feddb8445e91421425f67d19a1
     * 录入人 : 管理员
     * 录入时间 : 2018/5/2 9:14:20
     * 数量 : 1
     * 栋 : 1
     * 栋ID : ffa460bb439740a395ca805b5b6f4ee8
     * 源单ID : dd792d264fbe4b308213d6dd244e2d6a
     * 物料ID : acb6fd62b2f0405292fe8c0de0737f2f
     * 物料名称 : PC构件
     * 物料编码 : 1002.1084.0100.003
     * 状态 : 提交
     * 表头备注 :
     * 规格型号 : 测试
     * 计量单位 : 块
     * 计量单位ID : c8e082b5f5f34d5f934f071e6b464238
     * 质检数量 : 1
     * 质检类型 : 报废
     * 项目 : 麓谷一期项目
     * 项目ID : a3296cf71c1d46b0963f9f45bfd58382
     */

    public String ID;
    @SerializedName("二维码")
    public String Qrcode;
    @SerializedName("仓库ID")
    public String wareHouseID;
    @SerializedName("仓库名称")
    public String wareHouseName;
    @SerializedName("分录ID")
    public String subDirectoryID;
    @SerializedName("分录备注")
    public String subDirectoryBeizhu="";
    @SerializedName(value = "单据编号",alternate = {"退回入库单编号"})
    public String hNumbe;
    @SerializedName("层")
    public String ceng;
    @SerializedName("层ID")
    public String cengID;
    @SerializedName("数量")
    public int amount;
    @SerializedName("栋")
    public String dong;
    @SerializedName("栋ID")
    public String dongID;
    @SerializedName("源单ID")
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
    public String measureUnit="块";
    @SerializedName("计量单位ID")
    public String measureUnitID;
    @SerializedName("质检数量")
    public int qualityNum;
    @SerializedName("质检类型")
    public String qualityType;
    @SerializedName("项目")
    public String projectName;
    @SerializedName("项目ID")
    public String projectID;

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

    public String getSubDirectoryBeizhu() {
        return subDirectoryBeizhu;
    }

    public void setSubDirectoryBeizhu(String subDirectoryBeizhu) {
        this.subDirectoryBeizhu = subDirectoryBeizhu;
    }

    public String getHNumbe() {
        return hNumbe;
    }

    public void setHNumbe(String hNumbe) {
        this.hNumbe = hNumbe;
    }

    public String getCeng() {
        return ceng;
    }

    public void setCeng(String ceng) {
        this.ceng = ceng;
    }

    public String getCengID() {
        return cengID;
    }

    public void setCengID(String cengID) {
        this.cengID = cengID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDong() {
        return dong;
    }

    public void setDong(String dong) {
        this.dong = dong;
    }

    public String getDongID() {
        return dongID;
    }

    public void setDongID(String dongID) {
        this.dongID = dongID;
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

    public int getQualityNum() {
        return qualityNum;
    }

    public void setQualityNum(int qualityNum) {
        this.qualityNum = qualityNum;
    }

    public String getQualityType() {
        return qualityType;
    }

    public void setQualityType(String qualityType) {
        this.qualityType = qualityType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.requireDate);
        dest.writeString(this.Qrcode);
        dest.writeString(this.wareHouseID);
        dest.writeString(this.wareHouseName);
        dest.writeString(this.subDirectoryID);
        dest.writeString(this.subDirectoryBeizhu);
        dest.writeString(this.hNumbe);
        dest.writeString(this.checkPeople);
        dest.writeString(this.checkTime);
        dest.writeString(this.ceng);
        dest.writeString(this.cengID);
        dest.writeString(this.entryPeople);
        dest.writeString(this.entryTime);
        dest.writeInt(this.amount);
        dest.writeString(this.dong);
        dest.writeString(this.dongID);
        dest.writeString(this.sourceTableID);
        dest.writeString(this.materialsID);
        dest.writeString(this.materialsNames);
        dest.writeString(this.materialsNumber);
        dest.writeString(this.status);
        dest.writeString(this.description);
        dest.writeString(this.Specification);
        dest.writeString(this.measureUnit);
        dest.writeString(this.measureUnitID);
        dest.writeInt(this.qualityNum);
        dest.writeString(this.qualityType);
        dest.writeString(this.projectName);
        dest.writeString(this.projectID);
    }

    public ComponentReturnsBean() {
    }

    protected ComponentReturnsBean(Parcel in) {
        this.ID = in.readString();
        this.requireDate = in.readString();
        this.Qrcode = in.readString();
        this.wareHouseID = in.readString();
        this.wareHouseName = in.readString();
        this.subDirectoryID = in.readString();
        this.subDirectoryBeizhu = in.readString();
        this.hNumbe = in.readString();
        this.checkPeople = in.readString();
        this.checkTime = in.readString();
        this.ceng = in.readString();
        this.cengID = in.readString();
        this.entryPeople = in.readString();
        this.entryTime = in.readString();
        this.amount = in.readInt();
        this.dong = in.readString();
        this.dongID = in.readString();
        this.sourceTableID = in.readString();
        this.materialsID = in.readString();
        this.materialsNames = in.readString();
        this.materialsNumber = in.readString();
        this.status = in.readString();
        this.description = in.readString();
        this.Specification = in.readString();
        this.measureUnit = in.readString();
        this.measureUnitID = in.readString();
        this.qualityNum = in.readInt();
        this.qualityType = in.readString();
        this.projectName = in.readString();
        this.projectID = in.readString();
    }

    public static final Parcelable.Creator<ComponentReturnsBean> CREATOR = new Parcelable.Creator<ComponentReturnsBean>() {
        @Override
        public ComponentReturnsBean createFromParcel(Parcel source) {
            return new ComponentReturnsBean(source);
        }

        @Override
        public ComponentReturnsBean[] newArray(int size) {
            return new ComponentReturnsBean[size];
        }
    };
}
