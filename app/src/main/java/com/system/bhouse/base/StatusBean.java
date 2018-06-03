package com.system.bhouse.base;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018-03-25.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.base
 */

public class StatusBean implements Parcelable {

    private boolean NewStatus=false;
    private boolean LookStatus=false;
    private boolean ModifyStatus=false;

    public boolean isNewStatus() {
        return NewStatus;
    }

    public void setNewStatus(boolean newStatus) {
        NewStatus = newStatus;
    }

    public boolean isLookStatus() {
        return LookStatus;
    }

    public void setLookStatus(boolean lookStatus) {
        LookStatus = lookStatus;
    }

    public boolean isModifyStatus() {

        return ModifyStatus&&LookStatus;
    }

    public void setModifyStatus(boolean modifyStatus) {
        if (LookStatus) {
            ModifyStatus = modifyStatus;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.NewStatus ? (byte) 1 : (byte) 0);
        dest.writeByte(this.LookStatus ? (byte) 1 : (byte) 0);
        dest.writeByte(this.ModifyStatus ? (byte) 1 : (byte) 0);
    }

    public StatusBean() {
    }

    protected StatusBean(Parcel in) {
        this.NewStatus = in.readByte() != 0;
        this.LookStatus = in.readByte() != 0;
        this.ModifyStatus = in.readByte() != 0;
    }

    public static final Parcelable.Creator<StatusBean> CREATOR = new Parcelable.Creator<StatusBean>() {
        @Override
        public StatusBean createFromParcel(Parcel source) {
            return new StatusBean(source);
        }

        @Override
        public StatusBean[] newArray(int size) {
            return new StatusBean[size];
        }
    };
}
