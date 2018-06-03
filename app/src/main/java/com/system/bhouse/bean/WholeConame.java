package com.system.bhouse.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016-3-28.
 */
public class WholeConame implements Parcelable {

    /**
     * ccid : 1
     * ccNumber : HT-20160101-001
     * ccType : 标准
     * cname : 湖北建筑公司
     * cper : 黄先生
     * cphoe : 15409876542
     * Disnum : 50KM
     * Rowfy : 900.0
     * transportfy : 2000.0
     * otherfy : 400.0
     * hjfy : 3300.0
     * Installationarea : 湖南永州
     * specificarea : 湖南永州80号
     * area : 湖南
     * beizhu : 测试
     * status : 保存
     * ManCompany : 麓谷工厂
     * addPer : admin
     * addTime : 2016/1/1 0:00:00
     * shPer :
     * shTime :
     */

    private int ccid;
    private String ccNumber;
    private String ccType;
    private String cname;
    private String cper;
    private String cphoe;
    private String Disnum;
    private double Rowfy;
    private double transportfy;

    @Override
    public String toString() {
        return "WholeConame{" +
                "ccid=" + ccid +
                ", ccNumber='" + ccNumber + '\'' +
                ", ccType='" + ccType + '\'' +
                ", cname='" + cname + '\'' +
                ", cper='" + cper + '\'' +
                ", cphoe='" + cphoe + '\'' +
                ", Disnum='" + Disnum + '\'' +
                ", Rowfy=" + Rowfy +
                ", transportfy=" + transportfy +
                ", otherfy=" + otherfy +
                ", hjfy=" + hjfy +
                ", Installationarea='" + Installationarea + '\'' +
                ", specificarea='" + specificarea + '\'' +
                ", area='" + area + '\'' +
                ", beizhu='" + beizhu + '\'' +
                ", status='" + status + '\'' +
                ", ManCompany='" + ManCompany + '\'' +
                ", addPer='" + addPer + '\'' +
                ", addTime='" + addTime + '\'' +
                ", shPer='" + shPer + '\'' +
                ", shTime='" + shTime + '\'' +
                ", yjstartdate='" + yjstartdate + '\'' +
                ", yjenddate='" + yjenddate + '\'' +
                ", yjdatenum=" + yjdatenum +
                ", cid=" + cid +
                ", did=" + did +
                ", aid=" + aid +
                ", mid=" + mid +
                '}';
    }

    private double otherfy;
    private double hjfy;
    private String Installationarea;
    private String specificarea;
    private String area;
    private String beizhu;
    private String status;
    private String ManCompany;
    private String addPer;
    private String addTime;
    private String shPer;
    private String shTime;
    /**
     * cid : 1
     * cnumber : 010005
     * did : 1
     * aid : 1
     * yjstartdate : 2016/1/1 0:00:00
     * yjenddate : 2016/2/1 0:00:00
     * yjdatenum : 3.0
     * mid : 36
     */

    private String yjstartdate;
    private String yjenddate;
    private double yjdatenum;
    /**
     * cid : 1
     * cnumber : 010005
     * did : 1
     * aid : 1
     * mid : 36
     */

    private int cid;
    private int did;
    private int aid;
    private int mid;

    public void setCcid(int ccid) {
        this.ccid = ccid;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public void setCcType(String ccType) {
        this.ccType = ccType;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setCper(String cper) {
        this.cper = cper;
    }

    public void setCphoe(String cphoe) {
        this.cphoe = cphoe;
    }

    public void setDisnum(String Disnum) {
        this.Disnum = Disnum;
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

    public void setHjfy(double hjfy) {
        this.hjfy = hjfy;
    }

    public void setInstallationarea(String Installationarea) {
        this.Installationarea = Installationarea;
    }

    public void setSpecificarea(String specificarea) {
        this.specificarea = specificarea;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setManCompany(String ManCompany) {
        this.ManCompany = ManCompany;
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

    public int getCcid() {
        return ccid;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public String getCcType() {
        return ccType;
    }

    public String getCname() {
        return cname;
    }

    public String getCper() {
        return cper;
    }

    public String getCphoe() {
        return cphoe;
    }

    public String getDisnum() {
        return Disnum;
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

    public double getHjfy() {
        return hjfy;
    }

    public String getInstallationarea() {
        return Installationarea;
    }

    public String getSpecificarea() {
        return specificarea;
    }

    public String getArea() {
        return area;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public String getStatus() {
        return status;
    }

    public String getManCompany() {
        return ManCompany;
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

    public WholeConame() {
    }

    public void setYjstartdate(String yjstartdate) {
        this.yjstartdate = yjstartdate;
    }

    public void setYjenddate(String yjenddate) {
        this.yjenddate = yjenddate;
    }

    public void setYjdatenum(double yjdatenum) {
        this.yjdatenum = yjdatenum;
    }

    public String getYjstartdate() {
        return yjstartdate;
    }

    public String getYjenddate() {
        return yjenddate;
    }

    public double getYjdatenum() {
        return yjdatenum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ccid);
        dest.writeString(this.ccNumber);
        dest.writeString(this.ccType);
        dest.writeString(this.cname);
        dest.writeString(this.cper);
        dest.writeString(this.cphoe);
        dest.writeString(this.Disnum);
        dest.writeDouble(this.Rowfy);
        dest.writeDouble(this.transportfy);
        dest.writeDouble(this.otherfy);
        dest.writeDouble(this.hjfy);
        dest.writeString(this.Installationarea);
        dest.writeString(this.specificarea);
        dest.writeString(this.area);
        dest.writeString(this.beizhu);
        dest.writeString(this.status);
        dest.writeString(this.ManCompany);
        dest.writeString(this.addPer);
        dest.writeString(this.addTime);
        dest.writeString(this.shPer);
        dest.writeString(this.shTime);
        dest.writeString(this.yjstartdate);
        dest.writeString(this.yjenddate);
        dest.writeDouble(this.yjdatenum);
    }

    protected WholeConame(Parcel in) {
        this.ccid = in.readInt();
        this.ccNumber = in.readString();
        this.ccType = in.readString();
        this.cname = in.readString();
        this.cper = in.readString();
        this.cphoe = in.readString();
        this.Disnum = in.readString();
        this.Rowfy = in.readDouble();
        this.transportfy = in.readDouble();
        this.otherfy = in.readDouble();
        this.hjfy = in.readDouble();
        this.Installationarea = in.readString();
        this.specificarea = in.readString();
        this.area = in.readString();
        this.beizhu = in.readString();
        this.status = in.readString();
        this.ManCompany = in.readString();
        this.addPer = in.readString();
        this.addTime = in.readString();
        this.shPer = in.readString();
        this.shTime = in.readString();
        this.yjstartdate = in.readString();
        this.yjenddate = in.readString();
        this.yjdatenum = in.readDouble();
    }

    public static final Creator<WholeConame> CREATOR = new Creator<WholeConame>() {
        public WholeConame createFromParcel(Parcel source) {
            return new WholeConame(source);
        }

        public WholeConame[] newArray(int size) {
            return new WholeConame[size];
        }
    };

    public void setCid(int cid) {
        this.cid = cid;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getCid() {
        return cid;
    }

    public int getDid() {
        return did;
    }

    public int getAid() {
        return aid;
    }

    public int getMid() {
        return mid;
    }
}
