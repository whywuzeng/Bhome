package com.system.bhouse.bhouse.CommonTask.TechnologyExecution.PeopleAssignment.Bean;

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

public class PeopleAssignmentBean extends TechnologyBaseBean implements Parcelable {

    /**
     * 二维码 : DZXQ-7-201806-0009.1002.1084.0100.003.1
     * 分录ID : 3472ed5319c94083afc73f8d1d894369
     * 单据编号 : RGPQ-7-201806-0020
     * 备注 :
     * 定额人数 : 1.0
     * 实际人数 : 1.0
     * 实际结束日期 : 2018-06-28 09:29:40
     * 审核人 : 管理员
     * 审核时间 : 2018-06-28 09:09:56
     * 工序名称 : 布模
     * 工序资源ID : 383f87ed9ac14917bf8aa0a6adee6c71
     * 录入人 : 管理员
     * 录入时间 : 2018/6/28 9:09:54
     * 状态 : 审核
     * 职员ID : 4
     * 职员名称 : 邹序平
     * 职员编码 : 10249
     * 计划开始日期 : 2018-06-28 09:09:40
     * 计划开始日期1 : 2018-06-28 09:29:40
     * 订单ID : 808f2a114a354e4b956d0208b3cceacf
     */

    @SerializedName("定额人数")
    public double quotaNumber;
    @SerializedName("实际人数")
    public double actualNumber;
    @SerializedName("工序名称")
    public String processName;
    @SerializedName("工序资源ID")
    public String processResourceId;
    @SerializedName("职员ID")
    public int StaffID;
    @SerializedName("职员名称")
    public String StaffName;
    @SerializedName("职员编码")
    public String StaffCoding;

    public String getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(String actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    @SerializedName(value = "实际结束日期",alternate = {"实际结束时间"})
    public String actualEndTime;

    public double getQuotaNumber() {
        return quotaNumber;
    }

    public void setQuotaNumber(double quotaNumber) {
        this.quotaNumber = quotaNumber;
    }

    public double getActualNumber() {
        return actualNumber;
    }

    public void setActualNumber(double actualNumber) {
        this.actualNumber = actualNumber;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessResourceId() {
        return processResourceId;
    }

    public void setProcessResourceId(String processResourceId) {
        this.processResourceId = processResourceId;
    }

    public int getStaffID() {
        return StaffID;
    }

    public void setStaffID(int StaffID) {
        this.StaffID = StaffID;
    }

    public String getStaffName() {
        return StaffName;
    }

    public void setStaffName(String StaffName) {
        this.StaffName = StaffName;
    }

    public String getStaffCoding() {
        return StaffCoding;
    }

    public void setStaffCoding(String StaffCoding) {
        this.StaffCoding = StaffCoding;
    }

    public PeopleAssignmentBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.quotaNumber);
        dest.writeDouble(this.actualNumber);
        dest.writeString(this.processName);
        dest.writeString(this.processResourceId);
        dest.writeInt(this.StaffID);
        dest.writeString(this.StaffName);
        dest.writeString(this.StaffCoding);
        dest.writeString(this.actualEndTime);
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

    protected PeopleAssignmentBean(Parcel in) {
        this.quotaNumber = in.readDouble();
        this.actualNumber = in.readDouble();
        this.processName = in.readString();
        this.processResourceId = in.readString();
        this.StaffID = in.readInt();
        this.StaffName = in.readString();
        this.StaffCoding = in.readString();
        this.actualEndTime = in.readString();
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

    public static final Creator<PeopleAssignmentBean> CREATOR = new Creator<PeopleAssignmentBean>() {
        @Override
        public PeopleAssignmentBean createFromParcel(Parcel source) {
            return new PeopleAssignmentBean(source);
        }

        @Override
        public PeopleAssignmentBean[] newArray(int size) {
            return new PeopleAssignmentBean[size];
        }
    };
}
