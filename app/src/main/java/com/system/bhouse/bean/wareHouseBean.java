package com.system.bhouse.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018-07-27.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bean
 */

public class wareHouseBean {

    /**
     * 仓库ID : 8268302d925447ca85d14c5efb9bbc2f
     * 仓库名称 : 2号库位
     * 仓库编码 : WH_B-7-0002
     */

    @SerializedName("仓库ID")
    public String wareHouseID;
    @SerializedName("仓库名称")
    public String wareHouseName;
    @SerializedName("仓库编码")
    public String wareHouseCoding;

    public String getWareHouseID() {
        return wareHouseID;
    }

    public void setWareHouseID(String wareHouseID) {
        this.wareHouseID = wareHouseID;
    }

    public String getWareHouseName() {
        return wareHouseName;
    }

    public void setWareHouseName(String wareHouseName) {
        this.wareHouseName = wareHouseName;
    }

    public String getWareHouseCoding() {
        return wareHouseCoding;
    }

    public void setWareHouseCoding(String wareHouseCoding) {
        this.wareHouseCoding = wareHouseCoding;
    }
}
