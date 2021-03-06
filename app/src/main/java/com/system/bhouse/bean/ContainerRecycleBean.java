package com.system.bhouse.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.system.bhouse.base.App;
import com.system.bhouse.utils.TenUtils.DataFormatUtils;

/**
 * Created by Administrator on 2018-05-02.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bean
 */

public class ContainerRecycleBean implements Parcelable {

    /**
     * ID : d49ed508bed24675a9117a78373f1087
     * 业务日期 : 2018/5/2 16:28:03
     * 二维码 : DZXQ-7-201803-0001.1002.1084.0100.003.2
     * 分录ID : 413dca35f1554b858c6b23f3794097e7
     * 单据编号 : HGHS-7-201805-0001
     * 备注 :
     * 审核人 :
     * 审核时间 :
     * 层 : 2
     * 层ID : 998db5feddb8445e91421425f67d19a1
     * 录入人 : 管理员
     * 录入时间 : 2018/5/2 16:28:29
     * 数量 : 1
     * 栋 : 1
     * 栋ID : ffa460bb439740a395ca805b5b6f4ee8
     * 源单ID : 1a4f5c22e32f4bfa87a4bf195665f8d0
     * 物料ID : acb6fd62b2f0405292fe8c0de0737f2f
     * 物料名称 : PC构件
     * 物料编码 : 1002.1084.0100.003
     * 状态 : 提交
     * 规格型号 : 测试
     * 计量单位 : 块
     * 计量单位ID : c8e082b5f5f34d5f934f071e6b464238
     * 货柜ID : 9350b0e20c0742d2b0e1913124a87a11
     * 货柜名称 : 1号货柜
     * 车次 : CHE-7-201801-0001
     * 车牌号 : 2q2131231
     * 项目 : 麓谷一期项目
     * 项目ID : a3296cf71c1d46b0963f9f45bfd58382
     */

    public String ID;
    @SerializedName("业务日期")
    public String requireDate;
    @SerializedName("二维码")
    public String Qrcode;
    @SerializedName("分录ID")
    public String subDirectoryID;
    @SerializedName(value = "单据编号",alternate = {"货柜回收单编号"})
    public String hNumbe;
    @SerializedName("备注")
    public String description="";
    @SerializedName("审核人")
    public String checkPeople;
    @SerializedName("审核时间")
    public String checkTime;
    @SerializedName("层")
    public String ceng;
    @SerializedName("层ID")
    public String cengID;
    @SerializedName("库位")
    public String Wid;
    @SerializedName("录入人")
    public String entryPeople;
    @SerializedName("录入时间")
    public String entryTime;
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
    @SerializedName("状态")
    public String status;
    @SerializedName("规格型号")
    public String Specification;
    @SerializedName("计量单位")
    public String measureUnit;
    @SerializedName("计量单位ID")
    public String measureUnitID;
    @SerializedName("货柜ID")
    public String containerID;
    @SerializedName(value = "货柜名称",alternate = {"货柜"})
    public String container;
    @SerializedName(value = "车次",alternate = {"Car_No"})
    public String cartrips;
    @SerializedName("车牌号")
    public String licensePlate;
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

    public String getRequireDate() {
        if (TextUtils.isEmpty(requireDate))
        {
            return DataFormatUtils.getCurrentTime();
        }
        return requireDate;
    }

    public void setRequireDate(String requireDate) {
        this.requireDate = requireDate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCheckPeople() {
        return checkPeople;
    }

    public void setCheckPeople(String checkPeople) {
        this.checkPeople = checkPeople;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
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

    public String getEntryPeople() {
        if (TextUtils.isEmpty(entryPeople))
        {
            return App.menname;
        }
        return entryPeople;
    }

    public void setEntryPeople(String entryPeople) {
        this.entryPeople = entryPeople;
    }

    public String getEntryTime() {
        if (TextUtils.isEmpty(entryTime))
        {
            return DataFormatUtils.getCurrentTime();
        }
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
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

    public String getStatus() {
        if (TextUtils.isEmpty(status))
        {
            return "提交";
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getCartrips() {
        return cartrips;
    }

    public void setCartrips(String cartrips) {
        this.cartrips = cartrips;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
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
        dest.writeString(this.container);
        dest.writeString(this.cartrips);
        dest.writeString(this.licensePlate);
        dest.writeString(this.projectName);
        dest.writeString(this.projectID);
    }

    public ContainerRecycleBean() {
    }

    protected ContainerRecycleBean(Parcel in) {
        this.ID = in.readString();
        this.requireDate = in.readString();
        this.Qrcode = in.readString();
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
        this.container = in.readString();
        this.cartrips = in.readString();
        this.licensePlate = in.readString();
        this.projectName = in.readString();
        this.projectID = in.readString();
    }

    public static final Parcelable.Creator<ContainerRecycleBean> CREATOR = new Parcelable.Creator<ContainerRecycleBean>() {
        @Override
        public ContainerRecycleBean createFromParcel(Parcel source) {
            return new ContainerRecycleBean(source);
        }

        @Override
        public ContainerRecycleBean[] newArray(int size) {
            return new ContainerRecycleBean[size];
        }
    };
}
