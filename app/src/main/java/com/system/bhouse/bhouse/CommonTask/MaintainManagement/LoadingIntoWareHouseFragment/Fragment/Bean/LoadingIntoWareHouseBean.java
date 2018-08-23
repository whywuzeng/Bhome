package com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Fragment.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.entity.TechnologyBaseBean;

/**
 * Created by Administrator on 2018-07-25.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ModuleAssignMent.Bean
 */
public class LoadingIntoWareHouseBean extends TechnologyBaseBean implements Parcelable {

    /**
     * 业务日期 : 2018/5/19 10:21:23
     * 入库ID : c7762521eb4e466a9ca517aca0bb881b
     * 入库仓库 : 1号库位
     * 出库ID : c7762521eb4e466a9ca517aca0bb881b
     * 出库仓库 : 1号库位
     * 分录ID : 157f497189114f4aafb99a780c3ce99c
     * 单据编号 : ZGRK-7-201806-0001
     * 备注 :
     * 审核人 : 管理员
     * 审核时间 : 2018-06-12 11:22:46
     * 层 : 2
     * 层ID : 998db5feddb8445e91421425f67d19a1
     * 录入人 : 管理员
     * 录入时间 : 2018/6/12 11:22:43
     * 数量 : 1.0
     * 栋 : 1
     * 栋ID : ffa460bb439740a395ca805b5b6f4ee8
     * 源单ID : 2f5abd40e6874c118d22a26b9fcd73c5
     * 物料ID : acb6fd62b2f0405292fe8c0de0737f2f
     * 物料二维码 : DZXQ-7-201805-0002.1002.1084.0100.003.2
     * 物料名称 : PC构件
     * 物料编码 : 1002.1084.0100.003
     * 状态 : 审核
     * 规格型号 : 测试
     * 计量单位 : 块
     * 计量单位ID : c8e082b5f5f34d5f934f071e6b464238
     * 订单ID : 71a11b59bfce444986f5b91891f9d0cb
     * 货柜ID : 9350b0e20c0742d2b0e1913124a87a11
     * 货柜名称 : 1号货柜
     * 项目 : 麓谷一期项目
     * 项目ID : a3296cf71c1d46b0963f9f45bfd58382
     */

    @SerializedName("入库ID")
    private String storageID;
    @SerializedName("入库仓库")
    private String storageWarehouse;
    @SerializedName("出库ID")
    private String outLibID;
    @SerializedName("出库仓库")
    private String outLibWarehouse;
    @SerializedName("层")
    private String ceng;
    @SerializedName("层ID")
    private String cengID;
    @SerializedName("栋")
    private String dong;
    @SerializedName("栋ID")
    private String dongID;
    @SerializedName("货柜ID")
    private String containerID;
    @SerializedName(value = "货柜名称",alternate = {"货柜"})
    private String containerName;
    @SerializedName("项目")
    private String project;
    @SerializedName("项目ID")
    private String projectID;

    public String getStorageID() {
        return storageID;
    }

    public void setStorageID(String storageID) {
        this.storageID = storageID;
    }

    public String getStorageWarehouse() {
        return storageWarehouse;
    }

    public void setStorageWarehouse(String storageWarehouse) {
        this.storageWarehouse = storageWarehouse;
    }

    public String getOutLibID() {
        return outLibID;
    }

    public void setOutLibID(String outLibID) {
        this.outLibID = outLibID;
    }

    public String getOutLibWarehouse() {
        return outLibWarehouse;
    }

    public void setOutLibWarehouse(String outLibWarehouse) {
        this.outLibWarehouse = outLibWarehouse;
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

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
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
        dest.writeString(this.storageID);
        dest.writeString(this.storageWarehouse);
        dest.writeString(this.outLibID);
        dest.writeString(this.outLibWarehouse);
        dest.writeString(this.ceng);
        dest.writeString(this.cengID);
        dest.writeString(this.dong);
        dest.writeString(this.dongID);
        dest.writeString(this.containerID);
        dest.writeString(this.containerName);
        dest.writeString(this.project);
        dest.writeString(this.projectID);
        dest.writeString(this.ID);
        dest.writeString(this.Qrcode);
        dest.writeString(this.subDirectoryID);
        dest.writeString(this.hNumbe);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeString(this.orderId);
        dest.writeString(this.plannedStartDate);
        dest.writeString(this.plannedEndDate);
        dest.writeString(this.quotaTime);
        dest.writeDouble(this.number);
        dest.writeString(this.sourceID);
        dest.writeString(this.specification);
        dest.writeString(this.measureUnit);
        dest.writeString(this.measureUnitID);
        dest.writeString(this.materialID);
        dest.writeString(this.materialName);
        dest.writeString(this.materialCoding);
        dest.writeInt(this.ListenerContext);
        dest.writeByte(this.disableDelete ? (byte) 1 : (byte) 0);
        dest.writeString(this.requireDate);
        dest.writeString(this.description);
        dest.writeString(this.checkPeople);
        dest.writeString(this.checkTime);
        dest.writeString(this.entryPeople);
        dest.writeString(this.entryTime);
        dest.writeString(this.status);
    }

    public LoadingIntoWareHouseBean() {
    }

    protected LoadingIntoWareHouseBean(Parcel in) {
        this.storageID = in.readString();
        this.storageWarehouse = in.readString();
        this.outLibID = in.readString();
        this.outLibWarehouse = in.readString();
        this.ceng = in.readString();
        this.cengID = in.readString();
        this.dong = in.readString();
        this.dongID = in.readString();
        this.containerID = in.readString();
        this.containerName = in.readString();
        this.project = in.readString();
        this.projectID = in.readString();
        this.ID = in.readString();
        this.Qrcode = in.readString();
        this.subDirectoryID = in.readString();
        this.hNumbe = in.readString();
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.orderId = in.readString();
        this.plannedStartDate = in.readString();
        this.plannedEndDate = in.readString();
        this.quotaTime = in.readString();
        this.number = in.readDouble();
        this.sourceID = in.readString();
        this.specification = in.readString();
        this.measureUnit = in.readString();
        this.measureUnitID = in.readString();
        this.materialID = in.readString();
        this.materialName = in.readString();
        this.materialCoding = in.readString();
        this.ListenerContext = in.readInt();
        this.disableDelete = in.readByte() != 0;
        this.requireDate = in.readString();
        this.description = in.readString();
        this.checkPeople = in.readString();
        this.checkTime = in.readString();
        this.entryPeople = in.readString();
        this.entryTime = in.readString();
        this.status = in.readString();
    }

    public static final Creator<LoadingIntoWareHouseBean> CREATOR = new Creator<LoadingIntoWareHouseBean>() {
        @Override
        public LoadingIntoWareHouseBean createFromParcel(Parcel source) {
            return new LoadingIntoWareHouseBean(source);
        }

        @Override
        public LoadingIntoWareHouseBean[] newArray(int size) {
            return new LoadingIntoWareHouseBean[size];
        }
    };
}
