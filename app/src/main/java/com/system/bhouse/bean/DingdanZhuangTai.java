package com.system.bhouse.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016-5-17.
 * ClassName: com.system.bhouse.bean
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class DingdanZhuangTai implements Parcelable {

    /**
     * ID : 87
     * ccNumber : TY-0001
     * ccType : 标准
     * cname : 湖北建筑公司
     * Installationarea : 中国湖南长沙
     * ManCompany : 远大住工
     * mid : 1
     * stuname : 确认发货
     * addPer : 管理员
     * billdate : 2016/5/13 15:34:31
     */

    @SerializedName("ID")
    public int ID;
    @SerializedName("ccNumber")
    public String ccNumber;
    @SerializedName("ccType")
    public String ccType;
    @SerializedName("cname")
    public String cname;
    @SerializedName("Installationarea")
    public String Installationarea;
    @SerializedName("ManCompany")
    public String ManCompany;
    @SerializedName("mid")
    public int mid;
    @SerializedName("stuname")
    public String stuname;
    @SerializedName("addPer")
    public String addPer;
    @SerializedName("billdate")
    public String billdate;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ID);
        dest.writeString(this.ccNumber);
        dest.writeString(this.ccType);
        dest.writeString(this.cname);
        dest.writeString(this.Installationarea);
        dest.writeString(this.ManCompany);
        dest.writeInt(this.mid);
        dest.writeString(this.stuname);
        dest.writeString(this.addPer);
        dest.writeString(this.billdate);
    }

    public DingdanZhuangTai() {
    }

    protected DingdanZhuangTai(Parcel in) {
        this.ID = in.readInt();
        this.ccNumber = in.readString();
        this.ccType = in.readString();
        this.cname = in.readString();
        this.Installationarea = in.readString();
        this.ManCompany = in.readString();
        this.mid = in.readInt();
        this.stuname = in.readString();
        this.addPer = in.readString();
        this.billdate = in.readString();
    }

    public static final Parcelable.Creator<DingdanZhuangTai> CREATOR = new Parcelable.Creator<DingdanZhuangTai>() {
        public DingdanZhuangTai createFromParcel(Parcel source) {
            return new DingdanZhuangTai(source);
        }

        public DingdanZhuangTai[] newArray(int size) {
            return new DingdanZhuangTai[size];
        }
    };
}
