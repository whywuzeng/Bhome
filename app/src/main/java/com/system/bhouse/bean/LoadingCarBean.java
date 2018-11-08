package com.system.bhouse.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.system.bhouse.utils.TenUtils.DataFormatUtils;

/**
 * Created by Administrator on 2018-04-12.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bean
 */

public class LoadingCarBean extends BaseBean implements Parcelable {

    /**
     * ID : cd7c443d7be24e7383576c772578f961
     * 业务日期 : 2018/3/8 11:11:40
     * 二维码 : DZXQ-7-201803-0001.1002.1084.0100.003.18
     * 分录ID : 5784c66b5d3e44128f857de9887ccb4b
     * 单据编号 : ZCDD-7-201803-0001
     * 备注 :
     * 审核人 : 管理员
     * 审核时间 : 2018-03-10 08:45:20
     * 层 : 2
     * 层ID : 998db5feddb8445e91421425f67d19a1
     * 录入人 : 管理员
     * 录入时间 : 2018/3/10 8:45:17
     * 数量 : 1
     * 是否生产 : true
     * 栋 : 1
     * 栋ID : ffa460bb439740a395ca805b5b6f4ee8
     * 源单ID : 01160fb733f743228e4a9b0791736a66
     * 源单类型 : 吊装需求
     * 物料ID : acb6fd62b2f0405292fe8c0de0737f2f
     * 物料名称 : PC构件
     * 物料编码 : 1002.1084.0100.003
     * 状态 : 审核
     * 装柜日期 : 2018/3/8 11:13:16
     * 规格型号 : 测试
     * 计量单位 : 块
     * 计量单位ID : c8e082b5f5f34d5f934f071e6b464238
     * 货柜ID : 9350b0e20c0742d2b0e1913124a87a11
     * 货柜名称 : 1号货柜
     * 车次 : CHE-7-201801-0001
     * 项目 : 麓谷一期项目
     * 项目ID : a3296cf71c1d46b0963f9f45bfd58382  订单编号
     */

    //需要文件夹
    public String fileName="附件查询";
    public boolean hasFile = true;

    public String ID;
    @SerializedName("二维码")
    public String Qrcode="";
    @SerializedName("分录ID")
    public String subDirectoryID="";
    @SerializedName(value = "单据编号",alternate = {"订单编号"})
    public String hNumbe="";
    @SerializedName("层")
    public String ceng="";
    @SerializedName("层ID")
    public String cengID="";
    @SerializedName("数量")
    public int amount=1;
    @SerializedName("是否生产")
    public boolean IsProduce=false;
    @SerializedName("栋")
    public String dong="";
    @SerializedName("栋ID")
    public String dongID="";
    @SerializedName("源单ID")
    public String sourceTableID="";
    @SerializedName("源单类型")
    public String sourceType="";
    @SerializedName("物料ID")
    public String materialsID="";
    @SerializedName("物料名称")
    public String materialsNames="";
    @SerializedName("物料编码")
    public String materialsNumber="";
    @SerializedName("装柜日期")
    public String LoadingContainerDate="";
    @SerializedName("规格型号")
    public String Specification="";
    @SerializedName("计量单位")
    public String measureUnit="";
    @SerializedName("计量单位ID")
    public String measureUnitID="";
    @SerializedName("货柜ID")
    public String containerID="";
    @SerializedName(value = "货柜名称",alternate = {"货柜"})
    public String containerName="";
    @SerializedName("车次")
    public String cartrips="";
    @SerializedName("项目")
    public String projectName="";
    @SerializedName("项目ID")
    public String projectID="";
    @SerializedName("质检类型")
    public String QualityType;
    @SerializedName("外包")
    public boolean Is_OutPur;


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public boolean isIsProduce() {
        if (!TextUtils.isEmpty(QualityType)&&QualityType.contains("报废"))
        {
            return true;
        }
        return IsProduce;
    }

    public void setIsProduce(boolean IsProduce) {
        this.IsProduce = IsProduce;
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

    public String getLoadingContainerDate() {
        if (TextUtils.isEmpty(LoadingContainerDate))
        {
            return  DataFormatUtils.getCurrentTime();
        }
        return LoadingContainerDate;
    }

    public void setLoadingContainerDate(String LoadingContainerDate) {
        this.LoadingContainerDate = LoadingContainerDate;
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
        dest.writeByte(this.IsProduce ? (byte) 1 : (byte) 0);
        dest.writeString(this.dong);
        dest.writeString(this.dongID);
        dest.writeString(this.sourceTableID);
        dest.writeString(this.sourceType);
        dest.writeString(this.materialsID);
        dest.writeString(this.materialsNames);
        dest.writeString(this.materialsNumber);
        dest.writeString(this.status);
        dest.writeString(this.LoadingContainerDate);
        dest.writeString(this.Specification);
        dest.writeString(this.measureUnit);
        dest.writeString(this.measureUnitID);
        dest.writeString(this.containerID);
        dest.writeString(this.containerName);
        dest.writeString(this.cartrips);
        dest.writeString(this.projectName);
        dest.writeString(this.projectID);
    }

    public LoadingCarBean() {
    }

    protected LoadingCarBean(Parcel in) {
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
        this.IsProduce = in.readByte() != 0;
        this.dong = in.readString();
        this.dongID = in.readString();
        this.sourceTableID = in.readString();
        this.sourceType = in.readString();
        this.materialsID = in.readString();
        this.materialsNames = in.readString();
        this.materialsNumber = in.readString();
        this.status = in.readString();
        this.LoadingContainerDate = in.readString();
        this.Specification = in.readString();
        this.measureUnit = in.readString();
        this.measureUnitID = in.readString();
        this.containerID = in.readString();
        this.containerName = in.readString();
        this.cartrips = in.readString();
        this.projectName = in.readString();
        this.projectID = in.readString();
    }

    public static final Parcelable.Creator<LoadingCarBean> CREATOR = new Parcelable.Creator<LoadingCarBean>() {
        @Override
        public LoadingCarBean createFromParcel(Parcel source) {
            return new LoadingCarBean(source);
        }

        @Override
        public LoadingCarBean[] newArray(int size) {
            return new LoadingCarBean[size];
        }
    };
}
