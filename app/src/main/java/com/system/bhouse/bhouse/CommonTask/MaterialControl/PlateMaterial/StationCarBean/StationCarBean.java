package com.system.bhouse.bhouse.CommonTask.MaterialControl.PlateMaterial.StationCarBean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.system.bhouse.bean.BaseBean;

/**
 * Created by Administrator on 2018-07-03.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.MaterialControl.PlateMaterial.StationCarBean
 */

public class StationCarBean extends BaseBean implements Parcelable {

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

   private boolean isSelect=false;

    @SerializedName("台车名称")
    private String stationCarName;
    @SerializedName("构件二维码")
    private String componentQrcode;
    @SerializedName("模具名称")
    private String moduleName;
    @SerializedName("生产订单号")
    private String producationOrderNumber;
    @SerializedName("订单ID")
    private String oriderID;

    public String getStationCarName() {
        return stationCarName;
    }

    public void setStationCarName(String stationCarName) {
        this.stationCarName = stationCarName;
    }

    public String getComponentQrcode() {
        return componentQrcode;
    }

    public void setComponentQrcode(String componentQrcode) {
        this.componentQrcode = componentQrcode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getProducationOrderNumber() {
        return producationOrderNumber;
    }

    public void setProducationOrderNumber(String producationOrderNumber) {
        this.producationOrderNumber = producationOrderNumber;
    }

    public String getOriderID() {
        return oriderID;
    }

    public void setOriderID(String oriderID) {
        this.oriderID = oriderID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.stationCarName);
        dest.writeString(this.componentQrcode);
        dest.writeString(this.moduleName);
        dest.writeString(this.producationOrderNumber);
        dest.writeString(this.oriderID);
        dest.writeByte(this.disableDelete ? (byte) 1 : (byte) 0);
        dest.writeString(this.requireDate);
        dest.writeString(this.description);
        dest.writeString(this.checkPeople);
        dest.writeString(this.checkTime);
        dest.writeString(this.entryPeople);
        dest.writeString(this.entryTime);
        dest.writeString(this.status);
    }

    public StationCarBean() {
    }

    protected StationCarBean(Parcel in) {
        this.stationCarName = in.readString();
        this.componentQrcode = in.readString();
        this.moduleName = in.readString();
        this.producationOrderNumber = in.readString();
        this.oriderID = in.readString();
        this.disableDelete = in.readByte() != 0;
        this.requireDate = in.readString();
        this.description = in.readString();
        this.checkPeople = in.readString();
        this.checkTime = in.readString();
        this.entryPeople = in.readString();
        this.entryTime = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<StationCarBean> CREATOR = new Parcelable.Creator<StationCarBean>() {
        @Override
        public StationCarBean createFromParcel(Parcel source) {
            return new StationCarBean(source);
        }

        @Override
        public StationCarBean[] newArray(int size) {
            return new StationCarBean[size];
        }
    };
}
