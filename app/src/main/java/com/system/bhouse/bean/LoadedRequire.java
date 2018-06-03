package com.system.bhouse.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018-02-27.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bean
 */

public class LoadedRequire implements Parcelable {

    /**
     * ID : 9dc7b4b3882048f5bccea193ccdf6fc3
     * 订单编号 : DZXQ-7-201801-0001
     * 项目名称 : 麓谷一期项目
     * 栋 : 1
     * 层 : 2
     * 需求日期 : 2018/1/18 16:54:38
     * 描述 : 测试单据
     * 状态 : 审核
     * 录入人 : 管理员
     * 录入时间 : 2018/1/18 16:55:03
     */

    private String ID;
    @SerializedName("订单编号")
    private String hNumbe;
    @SerializedName("项目名称")
    private String projectName;
    @SerializedName("栋")
    private String dong;
    @SerializedName("层")
    private String ceng;
    @SerializedName("需求日期")
    private String requireData;
    @SerializedName("描述")
    private String description;
    @SerializedName("状态")
    private String status;
    @SerializedName("录入人")
    private String enterPeople;
    @SerializedName("录入时间")
    private String entryTime;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getHNumbe() {
        return hNumbe;
    }

    public void setHNumbe(String hNumbe) {
        this.hNumbe = hNumbe;
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

    public String getRequireData() {
        return requireData;
    }

    public void setRequireData(String requireData) {
        this.requireData = requireData;
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

    public String getEnterPeople() {
        return enterPeople;
    }

    public void setEnterPeople(String enterPeople) {
        this.enterPeople = enterPeople;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.hNumbe);
        dest.writeString(this.projectName);
        dest.writeString(this.dong);
        dest.writeString(this.ceng);
        dest.writeString(this.requireData);
        dest.writeString(this.description);
        dest.writeString(this.status);
        dest.writeString(this.enterPeople);
        dest.writeString(this.entryTime);
    }

    public LoadedRequire() {
    }

    protected LoadedRequire(Parcel in) {
        this.ID = in.readString();
        this.hNumbe = in.readString();
        this.projectName = in.readString();
        this.dong = in.readString();
        this.ceng = in.readString();
        this.requireData = in.readString();
        this.description = in.readString();
        this.status = in.readString();
        this.enterPeople = in.readString();
        this.entryTime = in.readString();
    }

    public static final Parcelable.Creator<LoadedRequire> CREATOR = new Parcelable.Creator<LoadedRequire>() {
        @Override
        public LoadedRequire createFromParcel(Parcel source) {
            return new LoadedRequire(source);
        }

        @Override
        public LoadedRequire[] newArray(int size) {
            return new LoadedRequire[size];
        }
    };
}
