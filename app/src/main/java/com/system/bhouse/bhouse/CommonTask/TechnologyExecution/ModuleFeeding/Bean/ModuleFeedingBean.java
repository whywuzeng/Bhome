package com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ModuleFeeding.Bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.entity.TechnologyBaseBean;
import com.system.bhouse.utils.TenUtils.DataFormatUtils;

/**
 * Created by Administrator on 2018-07-25.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ModuleAssignMent.Bean
 */

public class ModuleFeedingBean extends TechnologyBaseBean {

    @SerializedName("产线ID")
    public String productionLineID;
    @SerializedName("产线名称")
    public String productionLineName;
    @SerializedName("台车ID")
    public String stationCarID;
    @SerializedName("台车名称")
    public String stationCarName;
    @SerializedName("模具ID")
    public String moduleID;
    @SerializedName("模具分配ID")
    public String moduleAssignID;
    @SerializedName("模具名称")
    public String moduleName="";
    /**
     * 产线ID : d0c526b054e84f6598710eee704d8234
     * 产线名称 : PC一线库位
     * 分录ID : 3ff608e3c0bd4e3998a1ac9a6f77b6e3
     * 单据编号 : TPFP-7-201808-0005
     * 台车ID : e5ddba4fada141539f87516c51b3e012
     * 台车名称 : 9号台车
     * 开始日期 : 2018/8/6 15:13:37
     * 托盘ID : 0aea156d7a494628a2f8759f1b047bf5
     * 托盘名称 : 托盘3
     * 数量 : 6.0
     * 来源单据ID : 66461e17ded34815a43a7b90037df713
     * 构件二维码 : DZXQ-7-201806-0009.1002.1084.0100.003.1
     * 模具ID : 0d041a0b8d604eda8eef762bb4a9e8d5
     * 模具名称 : PC构件004
     * 物料ID : 810617e700654211827f46b51648ec98
     * 物料名称 : 直条螺纹钢
     * 物料编码 : 3000301049
     * 确认人 : 管理员
     * 确认时间 : 2018-08-06 15:17:45
     * 结束日期 : 2018/8/7 15:13:37
     * 规格型号 : HRB400E φ20
     * 计量单位 : 根
     * 计量单位ID : acd006b1fe8d43b4bb24c27071a88503
     * 订单ID : 808f2a114a354e4b956d0208b3cceacf
     */

    @SerializedName("托盘ID")
    private String trayID;
    @SerializedName("托盘名称")
    private String trayName;
    @SerializedName("确认人")
    private String feeder;
    @SerializedName("确认时间")
    private String feedingTime="";

    public String getProductionLineID() {
        return productionLineID;
    }

    public void setProductionLineID(String productionLineID) {
        this.productionLineID = productionLineID;
    }

    public String getProductionLineName() {
        return productionLineName;
    }

    public void setProductionLineName(String productionLineName) {
        this.productionLineName = productionLineName;
    }

    public String getStationCarID() {
        return stationCarID;
    }

    public void setStationCarID(String stationCarID) {
        this.stationCarID = stationCarID;
    }

    public String getStationCarName() {
        return stationCarName;
    }

    public void setStationCarName(String stationCarName) {
        this.stationCarName = stationCarName;
    }

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }

    public String getModuleAssignID() {
        return moduleAssignID;
    }

    public void setModuleAssignID(String moduleAssignID) {
        this.moduleAssignID = moduleAssignID;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getTrayID() {
        return trayID;
    }

    public void setTrayID(String trayID) {
        this.trayID = trayID;
    }

    public String getTrayName() {
        return trayName;
    }

    public void setTrayName(String trayName) {
        this.trayName = trayName;
    }


    public String getFeeder() {
        return feeder;
    }

    public void setFeeder(String feeder) {
        this.feeder = feeder;
    }

    public String getFeedingTime() {
        if (TextUtils.isEmpty(feedingTime)) {
            return DataFormatUtils.getCurrentTime();
        }
        return feedingTime;
    }

    public void setFeedingTime(String feedingTime) {
        this.feedingTime = feedingTime;
    }
}
