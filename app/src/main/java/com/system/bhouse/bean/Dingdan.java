package com.system.bhouse.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016-4-6.
 */
public class Dingdan implements Parcelable {

    /**
     * crid : 1
     * ccid : 1
     * crNumber : 11
     * proType : 1
     * pnumber : 03002
     * pname : 美式别墅
     * pmodel : A3
     * pnum : 22.0
     * Disnum : 50KM
     * Disfy : 1000.0
     * Rowfy : 11.0
     * transportfy : 21.0
     * otherfy : 1.0
     * htAmount : 1.0
     * mid : 36
     * ManCompany : 麓谷工厂
     * status : 保存
     * addPer : admin
     * addTime : 2016/4/5 13:32:15
     * shPer :
     * shTime :
     */

    private int crid;
    private int ccid;
    private String crNumber;
    private String proType;
    private String pnumber;
    private String pname;
    private String pmodel;
    private double pnum;
    private String Disnum;
    private double Disfy;
    private double Rowfy;
    private double transportfy;
    private double otherfy;
    private double htAmount;
    private int mid;
    private String ManCompany;
    private String status;
    private String addPer;
    /**
     * pid : 2
     * did : 1
     * mid1 : 36
     */

    private int pid;
    private int did;
    private int mid1;
    /**
     * ccNumber : HT0000001
     */

    private String ccNumber;

    @Override
    public String toString() {
        return "Dingdan{" +
                "crid=" + crid +
                ", ccid=" + ccid +
                ", crNumber='" + crNumber + '\'' +
                ", proType='" + proType + '\'' +
                ", pnumber='" + pnumber + '\'' +
                ", pname='" + pname + '\'' +
                ", pmodel='" + pmodel + '\'' +
                ", pnum=" + pnum +
                ", Disnum='" + Disnum + '\'' +
                ", Disfy=" + Disfy +
                ", Rowfy=" + Rowfy +
                ", transportfy=" + transportfy +
                ", otherfy=" + otherfy +
                ", htAmount=" + htAmount +
                ", mid=" + mid +
                ", ManCompany='" + ManCompany + '\'' +
                ", status='" + status + '\'' +
                ", addPer='" + addPer + '\'' +
                ", addTime='" + addTime + '\'' +
                ", shPer='" + shPer + '\'' +
                ", shTime='" + shTime + '\'' +
                '}';
    }

    private String addTime;
    private String shPer;
    private String shTime;

    public void setCrid(int crid) {
        this.crid = crid;
    }

    public void setCcid(int ccid) {
        this.ccid = ccid;
    }

    public void setCrNumber(String crNumber) {
        this.crNumber = crNumber;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public void setPnumber(String pnumber) {
        this.pnumber = pnumber;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public void setPmodel(String pmodel) {
        this.pmodel = pmodel;
    }

    public void setPnum(double pnum) {
        this.pnum = pnum;
    }

    public void setDisnum(String Disnum) {
        this.Disnum = Disnum;
    }

    public void setDisfy(double Disfy) {
        this.Disfy = Disfy;
    }

    public void setRowfy(double Rowfy) {
        this.Rowfy = Rowfy;
    }

    public void setTransportfy(double transportfy) {
        this.transportfy = transportfy;
    }

    public void setOtherfy(double otherfy) {
        this.otherfy = otherfy;
    }

    public void setHtAmount(double htAmount) {
        this.htAmount = htAmount;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public void setManCompany(String ManCompany) {
        this.ManCompany = ManCompany;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAddPer(String addPer) {
        this.addPer = addPer;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public void setShPer(String shPer) {
        this.shPer = shPer;
    }

    public void setShTime(String shTime) {
        this.shTime = shTime;
    }

    public int getCrid() {
        return crid;
    }

    public int getCcid() {
        return ccid;
    }

    public String getCrNumber() {
        return crNumber;
    }

    public String getProType() {
        return proType;
    }

    public String getPnumber() {
        return pnumber;
    }

    public String getPname() {
        return pname;
    }

    public String getPmodel() {
        return pmodel;
    }

    public double getPnum() {
        return pnum;
    }

    public String getDisnum() {
        return Disnum;
    }

    public double getDisfy() {
        return Disfy;
    }

    public double getRowfy() {
        return Rowfy;
    }

    public double getTransportfy() {
        return transportfy;
    }

    public double getOtherfy() {
        return otherfy;
    }

    public double getHtAmount() {
        return htAmount;
    }

    public int getMid() {
        return mid;
    }

    public String getManCompany() {
        return ManCompany;
    }

    public String getStatus() {
        return status;
    }

    public String getAddPer() {
        return addPer;
    }

    public String getAddTime() {
        return addTime;
    }

    public String getShPer() {
        return shPer;
    }

    public String getShTime() {
        return shTime;
    }

    public Dingdan() {
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public void setMid1(int mid1) {
        this.mid1 = mid1;
    }

    public int getPid() {
        return pid;
    }

    public int getDid() {
        return did;
    }

    public int getMid1() {
        return mid1;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.crid);
        dest.writeInt(this.ccid);
        dest.writeString(this.crNumber);
        dest.writeString(this.proType);
        dest.writeString(this.pnumber);
        dest.writeString(this.pname);
        dest.writeString(this.pmodel);
        dest.writeDouble(this.pnum);
        dest.writeString(this.Disnum);
        dest.writeDouble(this.Disfy);
        dest.writeDouble(this.Rowfy);
        dest.writeDouble(this.transportfy);
        dest.writeDouble(this.otherfy);
        dest.writeDouble(this.htAmount);
        dest.writeInt(this.mid);
        dest.writeString(this.ManCompany);
        dest.writeString(this.status);
        dest.writeString(this.addPer);
        dest.writeInt(this.pid);
        dest.writeInt(this.did);
        dest.writeInt(this.mid1);
        dest.writeString(this.ccNumber);
        dest.writeString(this.addTime);
        dest.writeString(this.shPer);
        dest.writeString(this.shTime);
    }

    protected Dingdan(Parcel in) {
        this.crid = in.readInt();
        this.ccid = in.readInt();
        this.crNumber = in.readString();
        this.proType = in.readString();
        this.pnumber = in.readString();
        this.pname = in.readString();
        this.pmodel = in.readString();
        this.pnum = in.readDouble();
        this.Disnum = in.readString();
        this.Disfy = in.readDouble();
        this.Rowfy = in.readDouble();
        this.transportfy = in.readDouble();
        this.otherfy = in.readDouble();
        this.htAmount = in.readDouble();
        this.mid = in.readInt();
        this.ManCompany = in.readString();
        this.status = in.readString();
        this.addPer = in.readString();
        this.pid = in.readInt();
        this.did = in.readInt();
        this.mid1 = in.readInt();
        this.ccNumber = in.readString();
        this.addTime = in.readString();
        this.shPer = in.readString();
        this.shTime = in.readString();
    }

    public static final Creator<Dingdan> CREATOR = new Creator<Dingdan>() {
        public Dingdan createFromParcel(Parcel source) {
            return new Dingdan(source);
        }

        public Dingdan[] newArray(int size) {
            return new Dingdan[size];
        }
    };
}
