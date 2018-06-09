package com.system.bhouse.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.system.bhouse.base.App;
import com.system.bhouse.base.StatusBean;
import com.system.bhouse.utils.TenUtils.DataFormatUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2018-03-22.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bean
 */

public class ConfirmationReceBean implements Parcelable {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfirmationReceBean that = (ConfirmationReceBean) o;

        if (amount != that.amount) return false;
        if (requireAmount != that.requireAmount) return false;
        if (!ID.equals(that.ID)) return false;
        if (!receiptHnumber.equals(that.receiptHnumber)) return false;
        if (!cartrips.equals(that.cartrips)) return false;
        if (!licensePlate.equals(that.licensePlate)) return false;
        if (!subDirectoryID.equals(that.subDirectoryID)) return false;
        if (!Qrcode.equals(that.Qrcode)) return false;
        if (!containerID.equals(that.containerID)) return false;
        if (!container.equals(that.container)) return false;
        if (!materialsID.equals(that.materialsID)) return false;
        if (!materialsNumber.equals(that.materialsNumber)) return false;
        if (!materialsNames.equals(that.materialsNames)) return false;
        if (!specification.equals(that.specification)) return false;
        if (!measureID.equals(that.measureID)) return false;
        if (!measure.equals(that.measure)) return false;
        if (!sourceTableID.equals(that.sourceTableID)) return false;
        if (!entryPeople.equals(that.entryPeople)) return false;
        if (!entryTime.equals(that.entryTime)) return false;
        if (!checkPeople.equals(that.checkPeople)) return false;
        if (!checkTime.equals(that.checkTime)) return false;
        if (!hNumbe.equals(that.hNumbe)) return false;
        if (!projectID.equals(that.projectID)) return false;
        if (!dongID.equals(that.dongID)) return false;
        if (!cengID.equals(that.cengID)) return false;
        if (!requireDate.equals(that.requireDate)) return false;
        if (!description.equals(that.description)) return false;
        if (!status.equals(that.status)) return false;
        if (!projectName.equals(that.projectName)) return false;
        if (!dong.equals(that.dong)) return false;
        if (!ceng.equals(that.ceng)) return false;
        if (!requireID.equals(that.requireID)) return false;
        if (!requireType.equals(that.requireType)) return false;
        if (!qualityType.equals(that.qualityType)) return false;
        if (!mStatusBean.equals(that.mStatusBean)) return false;
        if (!idNames.equals(that.idNames)) return false;
        return idValue.equals(that.idValue);
    }

    @Override
    public int hashCode() {
        int result = ID.hashCode();
        result = 31 * result + receiptHnumber.hashCode();
        result = 31 * result + cartrips.hashCode();
        result = 31 * result + licensePlate.hashCode();
        result = 31 * result + subDirectoryID.hashCode();
        result = 31 * result + Qrcode.hashCode();
        result = 31 * result + containerID.hashCode();
        result = 31 * result + container.hashCode();
        result = 31 * result + materialsID.hashCode();
        result = 31 * result + materialsNumber.hashCode();
        result = 31 * result + materialsNames.hashCode();
        result = 31 * result + specification.hashCode();
        result = 31 * result + measureID.hashCode();
        result = 31 * result + measure.hashCode();
        result = 31 * result + amount;
        result = 31 * result + sourceTableID.hashCode();
        result = 31 * result + entryPeople.hashCode();
        result = 31 * result + entryTime.hashCode();
        result = 31 * result + checkPeople.hashCode();
        result = 31 * result + checkTime.hashCode();
        result = 31 * result + hNumbe.hashCode();
        result = 31 * result + projectID.hashCode();
        result = 31 * result + dongID.hashCode();
        result = 31 * result + cengID.hashCode();
        result = 31 * result + requireDate.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + projectName.hashCode();
        result = 31 * result + dong.hashCode();
        result = 31 * result + ceng.hashCode();
        result = 31 * result + requireAmount;
        result = 31 * result + requireID.hashCode();
        result = 31 * result + requireType.hashCode();
        result = 31 * result + qualityType.hashCode();
        result = 31 * result + mStatusBean.hashCode();
        result = 31 * result + idNames.hashCode();
        result = 31 * result + idValue.hashCode();
        return result;
    }

    /**
     * ID : 766bf4c931eb42838606eb51c2a481bf
     * 收货单编号 : SHQR-7-201803-0001
     * 车次 : CHE-7-201801-0001
     * 车牌号 : 测试车牌
     * 业务日期 : 2018/3/25 12:55:25
     * 备注 :
     * 状态 : 提交
     * 录入人 : 管理员
     * 录入时间 : 2018/3/20 12:55:35
     *
     *    * 车次 : CHE-7-201801-0001
     * 车牌号 : 测试车牌
     * 分录ID : 3c3d3cf8d2774d00911b25febf2479c2
     * ID : 766bf4c931eb42838606eb51c2a481bf
     * 二维码 : DZXQ-7-201803-0001.1002.1084.0100.003.2
     * 货柜ID : 9350b0e20c0742d2b0e1913124a87a11
     * 货柜 : 1号货柜
     * 物料ID : acb6fd62b2f0405292fe8c0de0737f2f
     * 物料编码 : 1002.1084.0100.003
     * 物料名称 : PC构件
     * 规格型号 : 测试
     * 计量单位ID : c8e082b5f5f34d5f934f071e6b464238
     * 计量单位 : 块
     * 数量 : 1
     * 源单ID : d0d2a9a04c044f2fb03e554f68df50b2
     * 录入人 : 管理员
     * 录入时间 : 2018/3/20 12:55:35
     * 审核人 :
     * 审核时间 :
     * 单据编号 : SHQR-7-201803-0001
     * 项目ID : a3296cf71c1d46b0963f9f45bfd58382
     * 栋ID : ffa460bb439740a395ca805b5b6f4ee8
     * 层ID : 998db5feddb8445e91421425f67d19a1
     * 业务日期 : 2018/3/25 12:55:25
     * 备注 :
     * 状态 : 提交
     * 项目 : 麓谷一期项目
     * 栋 : 1
     * 层 : 2
     * 需求量 : 1
     * 需求ID : 1ec73f73573343e38b8bb0790d90541c
     * 需求类型 : 吊装需求
     */

    public String ID;
    @SerializedName(value = "收货单编号",alternate = {"退货单编号","补货单编号"})
    public String receiptHnumber;
    @SerializedName("车次")
    public String cartrips;
    @SerializedName("车牌号")
    public String licensePlate;
    @SerializedName("分录ID")
    public String subDirectoryID;
    @SerializedName("二维码")
    public String Qrcode;


    @SerializedName("货柜ID")
    public String containerID;
    @SerializedName("货柜")
    public String container;
    @SerializedName("物料ID")
    public String materialsID;
    @SerializedName("物料编码")
    public String materialsNumber;
    @SerializedName("物料名称")
    public String materialsNames;
    @SerializedName("规格型号")
    public String specification;
    @SerializedName("计量单位ID")
    public String measureID;
    @SerializedName("计量单位")
    public String measure;
    @SerializedName(value = "数量",alternate = {"收货数量","退货数量","补货数量"})
    public int amount;
    @SerializedName("源单ID")
    public String sourceTableID;
    @SerializedName("录入人")
    public String entryPeople;
    @SerializedName("录入时间")
    public String entryTime;
    @SerializedName("审核人")
    public String checkPeople;
    @SerializedName("审核时间")
    public String checkTime;
    @SerializedName("单据编号")
    public String hNumbe;
    @SerializedName("项目ID")
    public String projectID;
    @SerializedName("栋ID")
    public String dongID;
    @SerializedName("层ID")
    public String cengID;
    @SerializedName("业务日期")
    public String requireDate;
    @SerializedName("备注")
    public String description;
    @SerializedName("状态")
    public String status;
    @SerializedName("项目")
    public String projectName;
    @SerializedName("栋")
    public String dong;
    @SerializedName("层")
    public String ceng;
    @SerializedName(value = "需求量",alternate = {"需求数量","可退货量","可补货量","可补货数量","可退货数量"})
    public int requireAmount;

    @SerializedName("需求ID")
    public String requireID;
    @SerializedName("需求类型")
    public String requireType;

    public String getQualityType() {
        return qualityType;
    }

    public void setQualityType(String qualityType) {
        this.qualityType = qualityType;
    }

    @SerializedName("质检类型")
    public String qualityType;

//    [{"需求ID":"1ec73f73573343e38b8bb0790d90541c","需求数量":1,"需求类型":"吊装需求"}]

    public StatusBean getmStatusBean() {
        return mStatusBean;
    }

    public void setmStatusBean(StatusBean mStatusBean) {
        this.mStatusBean = mStatusBean;
    }

    private StatusBean mStatusBean;


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getReceiptHnumber() {
        return receiptHnumber;
    }

    public void setReceiptHnumber(String receiptHnumber) {
        this.receiptHnumber = receiptHnumber;
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

    public String getSubDirectoryID() {
        return subDirectoryID;
    }

    public void setSubDirectoryID(String subDirectoryID) {
        this.subDirectoryID = subDirectoryID;
    }

    public String getQrcode() {
        return Qrcode;
    }

    public void setQrcode(String qrcode) {
        Qrcode = qrcode;
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

    public String getMaterialsID() {
        return materialsID;
    }

    public void setMaterialsID(String materialsID) {
        this.materialsID = materialsID;
    }

    public String getMaterialsNumber() {
        return materialsNumber;
    }

    public void setMaterialsNumber(String materialsNumber) {
        this.materialsNumber = materialsNumber;
    }

    public String getMaterialsNames() {
        return materialsNames;
    }

    public void setMaterialsNames(String materialsNames) {
        this.materialsNames = materialsNames;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getMeasureID() {
        return measureID;
    }

    public void setMeasureID(String measureID) {
        this.measureID = measureID;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
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

    public String getEntryPeople() {
        return entryPeople;
    }

    public void setEntryPeople(String entryPeople) {
        this.entryPeople = entryPeople;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
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

    public String gethNumbe() {
        return hNumbe;
    }

    public void sethNumbe(String hNumbe) {
        this.hNumbe = hNumbe;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getDongID() {
        return dongID;
    }

    public void setDongID(String dongID) {
        this.dongID = dongID;
    }

    public String getCengID() {
        return cengID;
    }

    public void setCengID(String cengID) {
        this.cengID = cengID;
    }

    public String getRequireDate() {
        return requireDate;
    }

    public void setRequireDate(String requireDate) {
        this.requireDate = requireDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDong() {
        return dong;
    }

    public void setDong(String dong) {
        this.dong = dong;
    }

    public String getCeng() {
        return ceng;
    }

    public void setCeng(String ceng) {
        this.ceng = ceng;
    }

    public int getRequireAmount() {
        return requireAmount;
    }

    public void setRequireAmount(int requireAmount) {
        this.requireAmount = requireAmount;
    }

    public String getRequireID() {
        return requireID;
    }

    public void setRequireID(String requireID) {
        this.requireID = requireID;
    }

    public String getRequireType() {
        return requireType;
    }

    public void setRequireType(String requireType) {
        this.requireType = requireType;
    }

    public void setIdNames(HashMap<String, String> idNames) {
        this.idNames = idNames;
    }

    public void setIdValue(HashMap<String, String> idValue) {
        this.idValue = idValue;
    }

    private HashMap<String,String> idNames =new HashMap<>();
    private HashMap<String,String> idValue=new HashMap<>();


    public HashMap<String, String> getIdNames() {
        setIdNames();
        return idNames;
    }

    public HashMap<String, String> getIdValue() {
        setIdValue();
        return idValue;
    }

    public void setIdNames() {
        this.idNames.put("ID", ID);
        this.idNames.put("receiptHnumber", "收货单编号");
        this.idNames.put("cartrips", "车次");

        this.idNames.put("licensePlate", "车牌号");
        this.idNames.put("subDirectoryID", "分录ID");
        this.idNames.put("Qrcode", "二维码");
        this.idNames.put("containerID", "货柜ID");
        this.idNames.put("container", "货柜");

        this.idNames.put("sourceTableID", "源单ID");
        this.idNames.put("hNumbe", "单据编号");
        this.idNames.put("projectID", "项目ID");
        this.idNames.put("dongID", "栋ID");
        this.idNames.put("cengID", "层ID");
        this.idNames.put("projectName", "项目");
        this.idNames.put("requireAmount", "需求量");
        this.idNames.put("requireID", "需求ID");
        this.idNames.put("requireType", "需求类型");

        this.idNames.put("dong", "栋");
        this.idNames.put("ceng", "层");
        this.idNames.put("requireData", "业务日期");
        this.idNames.put("description", "描述");
        this.idNames.put("status", "状态");
        this.idNames.put("enterPeople", "录入人");
        this.idNames.put("entryTime", "录入时间");
        this.idNames.put("checkPeople", "审核人");
        this.idNames.put("checkTime", "审核时间");
        this.idNames.put("goodsID", "物料ID");
        this.idNames.put("goodsCoding", "物料编码");
        this.idNames.put("goodsName", "物料名称");
        this.idNames.put("Specification", "规格型号");
        this.idNames.put("measureID", "计量单位ID");
        this.idNames.put("measure", "计量单位");
        this.idNames.put("amount", "数量");

    }



    public void setIdValue() {
        this.idValue.put("ID",ID);
        this.idValue.put("receiptHnumber",receiptHnumber==null? App.receiptHnumber:receiptHnumber);

        this.idValue.put("cartrips",cartrips==null?"":cartrips);
        this.idValue.put("licensePlate",licensePlate==null? "":licensePlate);

        this.idValue.put("subDirectoryID",subDirectoryID==null? "":subDirectoryID);
        this.idValue.put("Qrcode",Qrcode==null? "":Qrcode);
        this.idValue.put("containerID",containerID==null? "":containerID);
        this.idValue.put("container",container==null? "":container);
        this.idValue.put("hNumbe",hNumbe==null? App.HNumber:hNumbe);
        this.idValue.put("projectName",projectName==null?"":projectName);

        this.idValue.put("sourceTableID",sourceTableID==null?"":sourceTableID);
        this.idValue.put("projectID",projectID==null?"":projectID);
        this.idValue.put("dongID",dongID==null?"":dongID);
        this.idValue.put("cengID",cengID==null?"":cengID);

        this.idValue.put("requireID",requireID==null?"":requireID);
        this.idValue.put("requireType",requireType==null?"":requireType);
        this.idValue.put("cengID",cengID==null?"":cengID);

        this.idValue.put("requireAmount",requireAmount+"");
        this.idValue.put("dong",dong==null?"":dong);
        this.idValue.put("ceng",ceng==null?"":ceng);
        this.idValue.put("requireData",requireDate==null? DataFormatUtils.getCurrentTime():requireDate);
        this.idValue.put("description",description==null?"":description);
        this.idValue.put("status",status==null?"提交":status);
        this.idValue.put("enterPeople",entryPeople==null? App.menname:entryPeople);
        this.idValue.put("entryTime",entryTime==null?DataFormatUtils.getCurrentSimpleTime():entryTime);
        this.idValue.put("checkPeople",checkPeople==null? "":checkPeople);
        this.idValue.put("checkTime",checkTime==null?"":checkTime);
        this.idValue.put("childTableID",subDirectoryID);
        this.idValue.put("goodsID",materialsID);
        this.idValue.put("goodsCoding",materialsNumber);
        this.idValue.put("goodsName",materialsNames);
        this.idValue.put("Specification",specification);
        this.idValue.put("measureID",measureID);
        this.idValue.put("measure",measure);
        this.idValue.put("amount",amount+"");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.receiptHnumber);
        dest.writeString(this.cartrips);
        dest.writeString(this.licensePlate);
        dest.writeString(this.subDirectoryID);
        dest.writeString(this.Qrcode);
        dest.writeString(this.containerID);
        dest.writeString(this.container);
        dest.writeString(this.materialsID);
        dest.writeString(this.materialsNumber);
        dest.writeString(this.materialsNames);
        dest.writeString(this.specification);
        dest.writeString(this.measureID);
        dest.writeString(this.measure);
        dest.writeInt(this.amount);
        dest.writeString(this.sourceTableID);
        dest.writeString(this.entryPeople);
        dest.writeString(this.entryTime);
        dest.writeString(this.checkPeople);
        dest.writeString(this.checkTime);
        dest.writeString(this.hNumbe);
        dest.writeString(this.projectID);
        dest.writeString(this.dongID);
        dest.writeString(this.cengID);
        dest.writeString(this.requireDate);
        dest.writeString(this.description);
        dest.writeString(this.status);
        dest.writeString(this.projectName);
        dest.writeString(this.dong);
        dest.writeString(this.ceng);
        dest.writeInt(this.requireAmount);
        dest.writeString(this.requireID);
        dest.writeString(this.requireType);
    }

    public ConfirmationReceBean() {
    }

    protected ConfirmationReceBean(Parcel in) {
        this.ID = in.readString();
        this.receiptHnumber = in.readString();
        this.cartrips = in.readString();
        this.licensePlate = in.readString();
        this.subDirectoryID = in.readString();
        this.Qrcode = in.readString();
        this.containerID = in.readString();
        this.container = in.readString();
        this.materialsID = in.readString();
        this.materialsNumber = in.readString();
        this.materialsNames = in.readString();
        this.specification = in.readString();
        this.measureID = in.readString();
        this.measure = in.readString();
        this.amount = in.readInt();
        this.sourceTableID = in.readString();
        this.entryPeople = in.readString();
        this.entryTime = in.readString();
        this.checkPeople = in.readString();
        this.checkTime = in.readString();
        this.hNumbe = in.readString();
        this.projectID = in.readString();
        this.dongID = in.readString();
        this.cengID = in.readString();
        this.requireDate = in.readString();
        this.description = in.readString();
        this.status = in.readString();
        this.projectName = in.readString();
        this.dong = in.readString();
        this.ceng = in.readString();
        this.requireAmount = in.readInt();
        this.requireID = in.readString();
        this.requireType = in.readString();
    }

    public static final Parcelable.Creator<ConfirmationReceBean> CREATOR = new Parcelable.Creator<ConfirmationReceBean>() {
        @Override
        public ConfirmationReceBean createFromParcel(Parcel source) {
            return new ConfirmationReceBean(source);
        }

        @Override
        public ConfirmationReceBean[] newArray(int size) {
            return new ConfirmationReceBean[size];
        }
    };
}


