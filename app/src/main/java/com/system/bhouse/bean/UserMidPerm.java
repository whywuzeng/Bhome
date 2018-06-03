package com.system.bhouse.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017-8-28.
 */

public class UserMidPerm implements Parcelable {

    /**
     * Mid : 1
     * ManNumber : 01
     * ManCompany : 远大住工
     * Fatherid : 0
     * Levelid : 1
     * EASnumber : 0
     * EAScgnumber :
     * EASkcnumber :
     * IsSub : false
     * status : true
     * property : 0
     * gsmid : 1
     * iscaigou : false
     * iskucun : false
     * addPer : admin
     * addtime : 2015/8/15 14:53:21
     * EASName :
     * EAScsName :
     * fmsNumber : 0
     * ispcmaker : false
     * flAccNumber :
     * MenType : 其他
     * Pc_Type : 其他
     * Is_Hz : false
     * sid : 0
     * IstoHost : false
     *
     * [{"mid":1,"Mancompany":"远大住工"},{"mid":7,"Mancompany":"麓谷工厂"},{"mid":8,"Mancompany":"PC产线"},{"mid":11,"Mancompany":"测试帐套"},{"mid":12,"Mancompany":"PC一线"},{"mid":13,"Mancompany":"PC二线"},{"mid":14,"Mancompany":"PC三线"},{"mid":15,"Mancompany":"PC四线"},{"mid":16,"Mancompany":"PC五线"},{"mid":17,"Mancompany":"钢筋加工中心"},{"mid":18,"Mancompany":"钢筋加工中心-2期"},{"mid":19,"Mancompany":"物流部"}]
     */

    @SerializedName(value = "Mid", alternate = {"mid"})
    public int Mid;
    public String ManNumber;
    @SerializedName(value = "Mancompany", alternate = {"ManCompany"})
    private String ManCompany;
    private int Fatherid;
    private int Levelid;
    private String EASnumber;
    private String EAScgnumber;
    private String EASkcnumber;
    private boolean IsSub;
    private boolean status;
    private int property;
    private int gsmid;
    private boolean iscaigou;
    private boolean iskucun;
    private String addPer;
    private String addtime;
    private String EASName;
    private String EAScsName;
    private int fmsNumber;
    private boolean ispcmaker;
    private String flAccNumber;
    private String MenType;
    private String Pc_Type;
    private boolean Is_Hz;
    private int sid;
    private boolean IstoHost;

    public int getMid() {
        return Mid;
    }

    public void setMid(int Mid) {
        this.Mid = Mid;
    }

    public String getManNumber() {
        return ManNumber;
    }

    public void setManNumber(String ManNumber) {
        this.ManNumber = ManNumber;
    }

    public String getManCompany() {
        return ManCompany;
    }

    public void setManCompany(String ManCompany) {
        this.ManCompany = ManCompany;
    }

    public int getFatherid() {
        return Fatherid;
    }

    public void setFatherid(int Fatherid) {
        this.Fatherid = Fatherid;
    }

    public int getLevelid() {
        return Levelid;
    }

    public void setLevelid(int Levelid) {
        this.Levelid = Levelid;
    }

    public String getEASnumber() {
        return EASnumber;
    }

    public void setEASnumber(String EASnumber) {
        this.EASnumber = EASnumber;
    }

    public String getEAScgnumber() {
        return EAScgnumber;
    }

    public void setEAScgnumber(String EAScgnumber) {
        this.EAScgnumber = EAScgnumber;
    }

    public String getEASkcnumber() {
        return EASkcnumber;
    }

    public void setEASkcnumber(String EASkcnumber) {
        this.EASkcnumber = EASkcnumber;
    }

    public boolean isIsSub() {
        return IsSub;
    }

    public void setIsSub(boolean IsSub) {
        this.IsSub = IsSub;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public boolean isIscaigou() {
        return iscaigou;
    }

    public void setIscaigou(boolean iscaigou) {
        this.iscaigou = iscaigou;
    }

    public boolean isIskucun() {
        return iskucun;
    }

    public void setIskucun(boolean iskucun) {
        this.iskucun = iskucun;
    }

    public String getAddPer() {
        return addPer;
    }

    public void setAddPer(String addPer) {
        this.addPer = addPer;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getEASName() {
        return EASName;
    }

    public void setEASName(String EASName) {
        this.EASName = EASName;
    }

    public String getEAScsName() {
        return EAScsName;
    }

    public void setEAScsName(String EAScsName) {
        this.EAScsName = EAScsName;
    }

    public int getFmsNumber() {
        return fmsNumber;
    }

    public void setFmsNumber(int fmsNumber) {
        this.fmsNumber = fmsNumber;
    }

    public boolean isIspcmaker() {
        return ispcmaker;
    }

    public void setIspcmaker(boolean ispcmaker) {
        this.ispcmaker = ispcmaker;
    }

    public String getFlAccNumber() {
        return flAccNumber;
    }

    public void setFlAccNumber(String flAccNumber) {
        this.flAccNumber = flAccNumber;
    }

    public String getMenType() {
        return MenType;
    }

    public void setMenType(String MenType) {
        this.MenType = MenType;
    }

    public String getPc_Type() {
        return Pc_Type;
    }

    public void setPc_Type(String Pc_Type) {
        this.Pc_Type = Pc_Type;
    }

    public boolean isIs_Hz() {
        return Is_Hz;
    }

    public void setIs_Hz(boolean Is_Hz) {
        this.Is_Hz = Is_Hz;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public boolean isIstoHost() {
        return IstoHost;
    }

    public void setIstoHost(boolean IstoHost) {
        this.IstoHost = IstoHost;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Mid);
        dest.writeString(this.ManNumber);
        dest.writeString(this.ManCompany);
        dest.writeInt(this.Fatherid);
        dest.writeInt(this.Levelid);
        dest.writeString(this.EASnumber);
        dest.writeString(this.EAScgnumber);
        dest.writeString(this.EASkcnumber);
        dest.writeByte(this.IsSub ? (byte) 1 : (byte) 0);
        dest.writeByte(this.status ? (byte) 1 : (byte) 0);
        dest.writeInt(this.property);
        dest.writeInt(this.gsmid);
        dest.writeByte(this.iscaigou ? (byte) 1 : (byte) 0);
        dest.writeByte(this.iskucun ? (byte) 1 : (byte) 0);
        dest.writeString(this.addPer);
        dest.writeString(this.addtime);
        dest.writeString(this.EASName);
        dest.writeString(this.EAScsName);
        dest.writeInt(this.fmsNumber);
        dest.writeByte(this.ispcmaker ? (byte) 1 : (byte) 0);
        dest.writeString(this.flAccNumber);
        dest.writeString(this.MenType);
        dest.writeString(this.Pc_Type);
        dest.writeByte(this.Is_Hz ? (byte) 1 : (byte) 0);
        dest.writeInt(this.sid);
        dest.writeByte(this.IstoHost ? (byte) 1 : (byte) 0);
    }

    public UserMidPerm() {
    }

    protected UserMidPerm(Parcel in) {
        this.Mid = in.readInt();
        this.ManNumber = in.readString();
        this.ManCompany = in.readString();
        this.Fatherid = in.readInt();
        this.Levelid = in.readInt();
        this.EASnumber = in.readString();
        this.EAScgnumber = in.readString();
        this.EASkcnumber = in.readString();
        this.IsSub = in.readByte() != 0;
        this.status = in.readByte() != 0;
        this.property = in.readInt();
        this.gsmid = in.readInt();
        this.iscaigou = in.readByte() != 0;
        this.iskucun = in.readByte() != 0;
        this.addPer = in.readString();
        this.addtime = in.readString();
        this.EASName = in.readString();
        this.EAScsName = in.readString();
        this.fmsNumber = in.readInt();
        this.ispcmaker = in.readByte() != 0;
        this.flAccNumber = in.readString();
        this.MenType = in.readString();
        this.Pc_Type = in.readString();
        this.Is_Hz = in.readByte() != 0;
        this.sid = in.readInt();
        this.IstoHost = in.readByte() != 0;
    }

    public static final Parcelable.Creator<UserMidPerm> CREATOR = new Parcelable.Creator<UserMidPerm>() {
        @Override
        public UserMidPerm createFromParcel(Parcel source) {
            return new UserMidPerm(source);
        }

        @Override
        public UserMidPerm[] newArray(int size) {
            return new UserMidPerm[size];
        }
    };
}
