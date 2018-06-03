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

public class BProBOM implements Parcelable {

    /**
     * ID : a3296cf71c1d46b0963f9f45bfd58382
     * 编码 : 001
     * 名称 : 麓谷一期项目
     * 录入人 : 管理员
     * 录入时间 : 2017/11/25 11:55:01
     * BOMID :
     */


    private String ID;
    @SerializedName(value = "编码",alternate = {"车次","货柜编码","需求编号"})
    private String coding;
    @SerializedName(value = "名称",alternate = {"装柜数","货柜名称","项目"})
    private String projectname;
    @SerializedName("录入人")
    private String entrypeople;
    @SerializedName("录入时间")
    private String entrytime;
    private String BOMID;

    public String getCeng() {
        return ceng;
    }

    public void setCeng(String ceng) {
        this.ceng = ceng;
    }

    public String getDong() {
        return dong;
    }

    public void setDong(String dong) {
        this.dong = dong;
    }

    @SerializedName(value = "层")
    private String ceng;
    @SerializedName(value = "栋")
    private String dong;

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

    public BProBOM() {
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
        dest.writeString(this.ceng);
        dest.writeString(this.dong);
    }

    protected BProBOM(Parcel in) {
        this.ID = in.readString();
        this.coding = in.readString();
        this.projectname = in.readString();
        this.entrypeople = in.readString();
        this.entrytime = in.readString();
        this.BOMID = in.readString();
        this.ceng = in.readString();
        this.dong = in.readString();
    }

    public static final Creator<BProBOM> CREATOR = new Creator<BProBOM>() {
        @Override
        public BProBOM createFromParcel(Parcel source) {
            return new BProBOM(source);
        }

        @Override
        public BProBOM[] newArray(int size) {
            return new BProBOM[size];
        }
    };
}
