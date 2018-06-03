package com.system.bhouse.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018-03-12.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bean
 */

public class BProBOMDong implements Parcelable {

    /**
     * ID : ffa460bb439740a395ca805b5b6f4ee8
     * 编码 : 1
     * 名称 : 栋
     * 录入人 : 管理员
     * 录入时间 : 2017/11/25 11:55:01
     * BOMID :
     */


    private String ID;
    @SerializedName("编码")
    private String coding;
    @SerializedName("名称")
    private String projectname;
    @SerializedName("录入人")
    private String entrypeople;
    @SerializedName("录入时间")
    private String entrytime;
    private String BOMID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getEntrypeople() {
        return entrypeople;
    }

    public void setEntrypeople(String entrypeople) {
        this.entrypeople = entrypeople;
    }

    public String getEntrytime() {
        return entrytime;
    }

    public void setEntrytime(String entrytime) {
        this.entrytime = entrytime;
    }

    public String getBOMID() {
        return BOMID;
    }

    public void setBOMID(String BOMID) {
        this.BOMID = BOMID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.coding);
        dest.writeString(this.projectname);
        dest.writeString(this.entrypeople);
        dest.writeString(this.entrytime);
        dest.writeString(this.BOMID);
    }

    public BProBOMDong() {
    }

    protected BProBOMDong(Parcel in) {
        this.ID = in.readString();
        this.coding = in.readString();
        this.projectname = in.readString();
        this.entrypeople = in.readString();
        this.entrytime = in.readString();
        this.BOMID = in.readString();
    }

    public static final Parcelable.Creator<BProBOMDong> CREATOR = new Parcelable.Creator<BProBOMDong>() {
        @Override
        public BProBOMDong createFromParcel(Parcel source) {
            return new BProBOMDong(source);
        }

        @Override
        public BProBOMDong[] newArray(int size) {
            return new BProBOMDong[size];
        }
    };
}
