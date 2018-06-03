package com.system.bhouse.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.system.bhouse.base.App;
import com.system.bhouse.utils.TenUtils.DataFormatUtils;

/**
 * Created by Administrator on 2018-05-08.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bean.EventBean
 */

public class BaseBean {

    public boolean isDisableDelete() {
        return disableDelete;
    }

    public void setDisableDelete(boolean disableDelete) {
        this.disableDelete = disableDelete;
    }

    public boolean disableDelete = false;


    @SerializedName(value = "业务日期", alternate = {"需求日期"})
    public String requireDate = "";

    public String getRequireDate() {
        if (TextUtils.isEmpty(requireDate)) {
            return DataFormatUtils.getCurrentTime();
        }
        return requireDate;
    }

    @SerializedName(value = "表头备注",alternate = {"备注"})
    public String description = "";

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @SerializedName("审核人")
    public String checkPeople = "";

    public String getCheckPeople() {
        return checkPeople;
    }

    public void setCheckPeople(String checkPeople) {
        this.checkPeople = checkPeople;
    }


    @SerializedName("审核时间")
    public String checkTime = "";

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }


    @SerializedName("录入人")
    public String entryPeople = "";

    public String getEntryPeople() {
        if (TextUtils.isEmpty(entryPeople)) {
            return App.menname;
        }
        return entryPeople;
    }

    public void setEntryPeople(String entryPeople) {
        this.entryPeople = entryPeople;
    }

    @SerializedName("录入时间")
    public String entryTime = "";

    public String getEntryTime() {
        if (TextUtils.isEmpty(entryTime)) {
            return DataFormatUtils.getCurrentTime();
        }
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }


    @SerializedName("状态")
    public String status = "";

    public String getStatus() {
        if (TextUtils.isEmpty(status)) {
            return "提交";
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
