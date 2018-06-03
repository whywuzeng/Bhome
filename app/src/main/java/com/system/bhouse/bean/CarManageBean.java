package com.system.bhouse.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018-04-10.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bean
 */

public class CarManageBean implements Parcelable {

    /**
     * ID : b976a632d2554aa19eaf8f648218da8e
     * 车次 : CHE-7-201803-0004
     * 货柜数 : 1
     * 收货后回收货柜 : true
     */

    private String ID;
    @SerializedName("车次")
    private String carNo;
    @SerializedName("货柜数")
    private int containernum=1;
    @SerializedName("收货后回收货柜")
    private boolean receivingcontainers;
    /**
     * 车次 : CHE-7-201803-0004
     * 货柜数 : 1
     * 收货后回收货柜 : true
     * 备注 : 444
     * 录入人 : 管理员
     * 录入时间 : 2018/3/25 14:09:44
     */

    @SerializedName("备注")
    private String beizhu;
    @SerializedName("录入人")
    private String entrypeople;
    @SerializedName("录入时间")
    private String entrytime;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public int getContainernum() {
        return containernum;
    }

    public void setContainernum(int containernum) {
        this.containernum = containernum;
    }

    public boolean isReceivingcontainers() {
        return receivingcontainers;
    }

    public void setReceivingcontainers(boolean receivingcontainers) {
        this.receivingcontainers = receivingcontainers;
    }

    public CarManageBean() {
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.beizhu);
        dest.writeString(this.entrypeople);
        dest.writeString(this.entrytime);
    }

    protected CarManageBean(Parcel in) {
        this.beizhu = in.readString();
        this.entrypeople = in.readString();
        this.entrytime = in.readString();
    }

    public static final Creator<CarManageBean> CREATOR = new Creator<CarManageBean>() {
        @Override
        public CarManageBean createFromParcel(Parcel source) {
            return new CarManageBean(source);
        }

        @Override
        public CarManageBean[] newArray(int size) {
            return new CarManageBean[size];
        }
    };
}
