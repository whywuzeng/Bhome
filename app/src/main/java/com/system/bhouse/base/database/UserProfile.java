package com.system.bhouse.base.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author: wuchao
 * @date: 2017/11/26 22:18
 * @desciption:
 */
@Entity(nameInDb = "user_profile")
public class UserProfile {

    @Id
    private long userId = 0;
    private String name;
    private int mid;
    private int Gsmid;
    private String mobilekey;
    private int filenum;
    private double filesize;
    private String mancompany;
    private int property;
    private  int fatherid;
    private boolean issub;
    private boolean Is_Pro_User;
    private String Pro_Userstring;
    //名字
    private  String menname;
    //职位
    private  String mpname;
    private  String usertype;
    @Generated(hash = 1083037799)
    public UserProfile(long userId, String name, int mid, int Gsmid,
            String mobilekey, int filenum, double filesize, String mancompany,
            int property, int fatherid, boolean issub, boolean Is_Pro_User,
            String Pro_Userstring, String menname, String mpname, String usertype) {
        this.userId = userId;
        this.name = name;
        this.mid = mid;
        this.Gsmid = Gsmid;
        this.mobilekey = mobilekey;
        this.filenum = filenum;
        this.filesize = filesize;
        this.mancompany = mancompany;
        this.property = property;
        this.fatherid = fatherid;
        this.issub = issub;
        this.Is_Pro_User = Is_Pro_User;
        this.Pro_Userstring = Pro_Userstring;
        this.menname = menname;
        this.mpname = mpname;
        this.usertype = usertype;
    }
    @Generated(hash = 968487393)
    public UserProfile() {
    }
    public long getUserId() {
        return this.userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getMid() {
        return this.mid;
    }
    public void setMid(int mid) {
        this.mid = mid;
    }
    public String getMobilekey() {
        return this.mobilekey;
    }
    public void setMobilekey(String mobilekey) {
        this.mobilekey = mobilekey;
    }
    public int getFilenum() {
        return this.filenum;
    }
    public void setFilenum(int filenum) {
        this.filenum = filenum;
    }
    public double getFilesize() {
        return this.filesize;
    }
    public void setFilesize(double filesize) {
        this.filesize = filesize;
    }
    public String getMancompany() {
        return this.mancompany;
    }
    public void setMancompany(String mancompany) {
        this.mancompany = mancompany;
    }
    public int getProperty() {
        return this.property;
    }
    public void setProperty(int property) {
        this.property = property;
    }
    public boolean getIssub() {
        return this.issub;
    }
    public void setIssub(boolean issub) {
        this.issub = issub;
    }
    public String getMenname() {
        return this.menname;
    }
    public void setMenname(String menname) {
        this.menname = menname;
    }
    public String getMpname() {
        return this.mpname;
    }
    public void setMpname(String mpname) {
        this.mpname = mpname;
    }
    public String getUsertype() {
        return this.usertype;
    }
    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
    public int getGsmid() {
        return this.Gsmid;
    }
    public void setGsmid(int Gsmid) {
        this.Gsmid = Gsmid;
    }
    public int getFatherid() {
        return this.fatherid;
    }
    public void setFatherid(int fatherid) {
        this.fatherid = fatherid;
    }
    public boolean getIs_Pro_User() {
        return this.Is_Pro_User;
    }
    public void setIs_Pro_User(boolean Is_Pro_User) {
        this.Is_Pro_User = Is_Pro_User;
    }
    public String getPro_Userstring() {
        return this.Pro_Userstring;
    }
    public void setPro_Userstring(String Pro_Userstring) {
        this.Pro_Userstring = Pro_Userstring;
    }
}
