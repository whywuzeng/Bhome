package com.system.bhouse.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018-04-26.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bean
 */

public class TransportSendGoodsBean extends BaseBean implements Parcelable {

    /**
     * ID : 269d5c858c0f4bfeb64f3f1b5445b264
     * 业务日期 : 2018/3/15 8:46:09
     * 二维码 : DZXQ-7-201803-0001.1002.1084.0100.003.18
     * 仓库ID : c7762521eb4e466a9ca517aca0bb881b
     * 仓库名称 : 1号库位
     * 分录ID : 2d57eee36bb44c1d9230c2113a9f3f5d
     * 单据编号 : YSFH-7-201803-0001
     * 备注 :
     * 审核人 : 管理员
     * 审核时间 : 2018-03-10 08:55:29
     * 层 : 2
     * 层ID : 998db5feddb8445e91421425f67d19a1
     * 录入人 : 管理员
     * 录入时间 : 2018/3/10 8:47:22
     * 数量 : 1
     * 栋 : 1
     * 栋ID : ffa460bb439740a395ca805b5b6f4ee8
     * 源单ID : 5784c66b5d3e44128f857de9887ccb4b
     * 物料ID : acb6fd62b2f0405292fe8c0de0737f2f
     * 物料名称 : PC构件
     * 物料编码 : 1002.1084.0100.003
     * 状态 : 审核
     * 规格型号 : 测试
     * 计量单位 : 块
     * 计量单位ID : c8e082b5f5f34d5f934f071e6b464238
     * 货柜ID : 9350b0e20c0742d2b0e1913124a87a11
     * 货柜名称 : 1号货柜
     * 车次 : CHE-7-201801-0001
     * 车牌号 : 测试车牌
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
    @SerializedName(value = "单据编号",alternate = {"发货单编号"})
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
    public String measureUnit;
    @SerializedName("计量单位ID")
    public String measureUnitID;
    @SerializedName("货柜ID")
    public String containerID;
    @SerializedName("货柜名称")
    public String containerName;
    @SerializedName("车次")
    public String cartrips="";
    @SerializedName("车牌号")
    public String Licenseplate="";
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

    public String getContainerID() {
        return containerID;
    }

    public void setContainerID(String containerID) {
        this.containerID = containerID;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public String getCartrips() {
        return cartrips;
    }

    public void setCartrips(String cartrips) {
        this.cartrips = cartrips;
    }

    public String getLicenseplate() {
        return Licenseplate;
    }

    public void setLicenseplate(String Licenseplate) {
        this.Licenseplate = Licenseplate;
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
        dest.writeString(this.hNumbe);
        dest.writeString(this.description);
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
        dest.writeString(this.Specification);
        dest.writeString(this.measureUnit);
        dest.writeString(this.measureUnitID);
        dest.writeString(this.containerID);
        dest.writeString(this.containerName);
        dest.writeString(this.cartrips);
        dest.writeString(this.Licenseplate);
        dest.writeString(this.projectName);
        dest.writeString(this.projectID);
    }

    public TransportSendGoodsBean() {
    }

    protected TransportSendGoodsBean(Parcel in) {
        this.ID = in.readString();
        this.requireDate = in.readString();
        this.Qrcode = in.readString();
        this.wareHouseID = in.readString();
        this.wareHouseName = in.readString();
        this.subDirectoryID = in.readString();
        this.hNumbe = in.readString();
        this.description = in.readString();
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
        this.Specification = in.readString();
        this.measureUnit = in.readString();
        this.measureUnitID = in.readString();
        this.containerID = in.readString();
        this.containerName = in.readString();
        this.cartrips = in.readString();
        this.Licenseplate = in.readString();
        this.projectName = in.readString();
        this.projectID = in.readString();
    }

    public static final Parcelable.Creator<TransportSendGoodsBean> CREATOR = new Parcelable.Creator<TransportSendGoodsBean>() {
        @Override
        public TransportSendGoodsBean createFromParcel(Parcel source) {
            return new TransportSendGoodsBean(source);
        }

        @Override
        public TransportSendGoodsBean[] newArray(int size) {
            return new TransportSendGoodsBean[size];
        }
    };
}
