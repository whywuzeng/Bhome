package com.system.bhouse.bhouse.CommonTask.MaintainManagement.StationCarDetachModule.Bean;

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
public class StationCarDetachModuleBean extends TechnologyBaseBean implements Parcelable {

    @SerializedName("台车ID")
    public String stationCarID;
    @SerializedName(value = "台车名称",alternate ={"台车"} )
    public String stationCarName;
    @SerializedName("模具ID")
    public String moduleID;
    @SerializedName("模具分配ID")
    public String moduleAssignID;
    @SerializedName(value = "模具名称",alternate = {"模具"})
    public String moduleName="";

    public String getMaintenanceStartTime() {
        return maintenanceStartTime;
    }

    public void setMaintenanceStartTime(String maintenanceStartTime) {
        this.maintenanceStartTime = maintenanceStartTime;
    }

    public String getMaintenanceEndTime() {
        return maintenanceEndTime;
    }

    public void setMaintenanceEndTime(String maintenanceEndTime) {
        this.maintenanceEndTime = maintenanceEndTime;
    }

    public String getStationAssigmentSublistID() {
        return stationAssigmentSublistID;
    }

    public void setStationAssigmentSublistID(String stationAssigmentSublistID) {
        this.stationAssigmentSublistID = stationAssigmentSublistID;
    }

    public String getStationCarCoding() {
        return stationCarCoding;
    }

    public void setStationCarCoding(String stationCarCoding) {
        this.stationCarCoding = stationCarCoding;
    }

    public String getModuleCoding() {
        return moduleCoding;
    }

    public void setModuleCoding(String moduleCoding) {
        this.moduleCoding = moduleCoding;
    }


    /**
     * 仓库 : 养护窑002库位
     * 仓库ID : d88aade93adb435bad0911e712cc59cc
     * 养护开始时间 : 2018/8/7 15:04:49
     * 养护结束时间 : 2018/8/7 15:54:49
     * 分录ID : cbd4f3e22abc4ab48eaf230ae1dadbd9
     * 单据编号 : YHRK-7-201808-0003
     * 台车 : 9号台车
     * 台车ID : e5ddba4fada141539f87516c51b3e012
     * 台车分配分录ID : f4904e038213401c96890260bd9026ba
     * 台车编码 : TC-7-201807-0009
     * 审核人 : 管理员
     * 审核时间 : 2018-8-7 15:05:27
     * 录入人 : 管理员
     * 录入时间 : 2018/8/7 15:05:27
     * 数量 : 1.0
     * 构件二维码 : DZXQ-7-201806-0009.1002.1084.0100.003.1
     * 模具 : PC构件004
     * 模具ID : 0d041a0b8d604eda8eef762bb4a9e8d5
     * 模具编码 : 1002.1084.0100.003.0004
     * 物料ID : acb6fd62b2f0405292fe8c0de0737f2f
     * 物料名称 : PC构件
     * 物料编码 : 1002.1084.0100.003
     * 状态 : 审核
     * 规格型号 : 测试
     * 计量单位 : 块
     * 计量单位ID : c8e082b5f5f34d5f934f071e6b464238
     * 订单ID : 808f2a114a354e4b956d0208b3cceacf
     * 调拨前仓库 : PC一线库位
     * 调拨前仓库ID : d0c526b054e84f6598710eee704d8234
     */

    @SerializedName("养护开始时间")
    public String maintenanceStartTime;
    @SerializedName("养护结束时间")
    public String maintenanceEndTime;
    @SerializedName("台车分配分录ID")
    public String stationAssigmentSublistID;
    @SerializedName("台车编码")
    public String stationCarCoding;
    @SerializedName("模具编码")
    public String moduleCoding;

    /**
     * 产线ID : d0c526b054e84f6598710eee704d8234
     * 产线名称 : PC一线库位
     * 分录ID : 3ff608e3c0bd4e3998a1ac9a6f77b6e3
     * 单据编号 : TPFP-7-201808-0005
     * 台车ID : e5ddba4fada141539f87516c51b3e012
     * 台车名称 : 9号台车
     * 开始日期 : 2018/8/6 15:13:37
     * 托盘ID : 0aea156d7a494628a2f8759f1b047bf5
     * 托盘名称 : 托盘3
     * 数量 : 6.0
     * 来源单据ID : 66461e17ded34815a43a7b90037df713
     * 构件二维码 : DZXQ-7-201806-0009.1002.1084.0100.003.1
     * 模具ID : 0d041a0b8d604eda8eef762bb4a9e8d5
     * 模具名称 : PC构件004
     * 物料ID : 810617e700654211827f46b51648ec98
     * 物料名称 : 直条螺纹钢
     * 物料编码 : 3000301049
     * 确认人 : 管理员
     * 确认时间 : 2018-08-06 15:17:45
     * 结束日期 : 2018/8/7 15:13:37
     * 规格型号 : HRB400E φ20
     * 计量单位 : 根
     * 计量单位ID : acd006b1fe8d43b4bb24c27071a88503
     * 订单ID : 808f2a114a354e4b956d0208b3cceacf
     */


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

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }

    public String getModuleAssignID() {
        return moduleAssignID;
    }

    public void setModuleAssignID(String moduleAssignID) {
        this.moduleAssignID = moduleAssignID;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.stationCarID);
        dest.writeString(this.stationCarName);
        dest.writeString(this.moduleID);
        dest.writeString(this.moduleAssignID);
        dest.writeString(this.moduleName);
        dest.writeString(this.maintenanceStartTime);
        dest.writeString(this.maintenanceEndTime);
        dest.writeString(this.stationAssigmentSublistID);
        dest.writeString(this.stationCarCoding);
        dest.writeString(this.moduleCoding);
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

    public StationCarDetachModuleBean() {
    }

    protected StationCarDetachModuleBean(Parcel in) {
        this.stationCarID = in.readString();
        this.stationCarName = in.readString();
        this.moduleID = in.readString();
        this.moduleAssignID = in.readString();
        this.moduleName = in.readString();
        this.maintenanceStartTime = in.readString();
        this.maintenanceEndTime = in.readString();
        this.stationAssigmentSublistID = in.readString();
        this.stationCarCoding = in.readString();
        this.moduleCoding = in.readString();
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

    public static final Creator<StationCarDetachModuleBean> CREATOR = new Creator<StationCarDetachModuleBean>() {
        @Override
        public StationCarDetachModuleBean createFromParcel(Parcel source) {
            return new StationCarDetachModuleBean(source);
        }

        @Override
        public StationCarDetachModuleBean[] newArray(int size) {
            return new StationCarDetachModuleBean[size];
        }
    };
}
