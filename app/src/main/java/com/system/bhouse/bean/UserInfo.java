package com.system.bhouse.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016-3-15.
 */
public class UserInfo implements Parcelable {

    /**
     * MobileKey : ruweri358063jfdi2y438
     */

    @SerializedName("MobileKey")
    public String MobileKey;
    /**
     * Filenum : 3
     * Filesize : 2.097152E7
     */

    @SerializedName("Filenum")
    public int Filenum;
    /**
     * Filesize : 20971520
     * property : 0
     * gsmid : 1
     */
    @SerializedName("mpname")
    public String mpname;

    @SerializedName("Filesize")
    private int FilesizeX;
    private int property;
    private int gsmid;
    public String XingeAppKey;
    public String XingeAppID;

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", usertype='" + usertype + '\'' +
                ", ManCompany='" + ManCompany + '\'' +
                ", ManNumber='" + ManNumber + '\'' +
                ", Fatherid=" + Fatherid +
                ", Levelid=" + Levelid +
                ", EASnumber='" + EASnumber + '\'' +
                ", EAScgnumber='" + EAScgnumber + '\'' +
                ", EASkcnumber='" + EASkcnumber + '\'' +
                ", IsSub=" + IsSub +
                ", mid=" + mid +
                ", menname='" + menname + '\'' +
                ", mmid=" + mmid +
                '}';
    }

    /**
     * username : admin
     * usertype : 管理员
     * ManCompany : 远大住工
     * ManNumber : 01
     * Fatherid : 0
     * Levelid : 1
     * EASnumber : 0
     * EAScgnumber :
     * EASkcnumber :
     * IsSub : false
     * mid : 1
     * menname : 管理员
     * mmid : 1
     */

    private String username;
    private String usertype;
    private String ManCompany;
    private String ManNumber;
    private int Fatherid;
    private int Levelid;
    private String EASnumber;
    private String EAScgnumber;
    private String EASkcnumber;
    private boolean IsSub;
    private int mid;
    private String menname;
    private int mmid;


    public void setUsername(String username) {
        this.username = username;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public void setManCompany(String ManCompany) {
        this.ManCompany = ManCompany;
    }

    public void setManNumber(String ManNumber) {
        this.ManNumber = ManNumber;
    }

    public void setFatherid(int Fatherid) {
        this.Fatherid = Fatherid;
    }

    public void setLevelid(int Levelid) {
        this.Levelid = Levelid;
    }

    public void setEASnumber(String EASnumber) {
        this.EASnumber = EASnumber;
    }

    public void setEAScgnumber(String EAScgnumber) {
        this.EAScgnumber = EAScgnumber;
    }

    public void setEASkcnumber(String EASkcnumber) {
        this.EASkcnumber = EASkcnumber;
    }

    public void setIsSub(boolean IsSub) {
        this.IsSub = IsSub;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public void setMenname(String menname) {
        this.menname = menname;
    }

    public void setMmid(int mmid) {
        this.mmid = mmid;
    }

    public String getUsername() {
        return username;
    }

    public String getUsertype() {
        return usertype;
    }

    public String getManCompany() {
        return ManCompany;
    }

    public String getManNumber() {
        return ManNumber;
    }

    public int getFatherid() {
        return Fatherid;
    }

    public int getLevelid() {
        return Levelid;
    }

    public String getEASnumber() {
        return EASnumber;
    }

    public String getEAScgnumber() {
        return EAScgnumber;
    }

    public String getEASkcnumber() {
        return EASkcnumber;
    }

    public boolean isIsSub() {
        return IsSub;
    }

    public int getMid() {
        return mid;
    }

    public String getMenname() {
        return menname;
    }

    public int getMmid() {
        return mmid;
    }

    public int getFilesizeX() {
        return FilesizeX;
    }

    public void setFilesizeX(int FilesizeX) {
        this.FilesizeX = FilesizeX;
    }

    public int getProperty() {
        return property;
    }

    public void setProperty(int property) {
        this.property = property;
    }

    public int getGsmid() {
        return gsmid;
    }

    public void setGsmid(int gsmid) {
        this.gsmid = gsmid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.MobileKey);
        dest.writeInt(this.Filenum);
        dest.writeInt(this.FilesizeX);
        dest.writeInt(this.property);
        dest.writeInt(this.gsmid);
        dest.writeString(this.username);
        dest.writeString(this.usertype);
        dest.writeString(this.ManCompany);
        dest.writeString(this.ManNumber);
        dest.writeInt(this.Fatherid);
        dest.writeInt(this.Levelid);
        dest.writeString(this.EASnumber);
        dest.writeString(this.EAScgnumber);
        dest.writeString(this.EASkcnumber);
        dest.writeByte(this.IsSub ? (byte) 1 : (byte) 0);
        dest.writeInt(this.mid);
        dest.writeString(this.menname);
        dest.writeInt(this.mmid);
    }

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        this.MobileKey = in.readString();
        this.Filenum = in.readInt();
        this.FilesizeX = in.readInt();
        this.property = in.readInt();
        this.gsmid = in.readInt();
        this.username = in.readString();
        this.usertype = in.readString();
        this.ManCompany = in.readString();
        this.ManNumber = in.readString();
        this.Fatherid = in.readInt();
        this.Levelid = in.readInt();
        this.EASnumber = in.readString();
        this.EAScgnumber = in.readString();
        this.EASkcnumber = in.readString();
        this.IsSub = in.readByte() != 0;
        this.mid = in.readInt();
        this.menname = in.readString();
        this.mmid = in.readInt();
    }

    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
