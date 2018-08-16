package com.system.bhouse.bhouse.CommonTask.TechnologyExecution.StationCarAssignMent.Bean;

import com.google.gson.annotations.SerializedName;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.entity.TechnologyBaseBean;

/**
 * Created by Administrator on 2018-07-25.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ModuleAssignMent.Bean
 */

public class StationCarAssignMentBean extends TechnologyBaseBean {

    /**
     * 二维码 : DZXQ-7-201806-0009.1002.1084.0100.003.1
     * 产线ID : d0c526b054e84f6598710eee704d8234
     * 产线名称 : PC一线库位
     * 分录ID : 803a3fa1be3c42bc8c0cb11a68ecc9d2
     * 单据编号 : TCFP-7-201808-0005
     * 台车ID : e5ddba4fada141539f87516c51b3e012
     * 台车名称 : 9号台车
     * 审核人 :
     * 审核时间 :
     * 开始日期 : 2018/8/2 15:16:04
     * 录入人 : 管理员
     * 录入时间 : 2018/8/2 15:27:13
     * 数量 : 1.0
     * 模具ID : 0d041a0b8d604eda8eef762bb4a9e8d5
     * 模具分配ID : 9fddc23ba62f437dbf6a3ff2c84cac67
     * 模具名称 : PC构件004
     * 源单ID : e598d829b14f406ebd3799bd41a191e8
     * 物料ID : acb6fd62b2f0405292fe8c0de0737f2f
     * 物料名称 : PC构件
     * 物料编码 : 1002.1084.0100.003
     * 状态 : 保存
     * 结束日期 : 2018/8/2 15:36:04
     * 规格型号 : 测试
     * 计量单位 : 块
     * 计量单位ID : c8e082b5f5f34d5f934f071e6b464238
     * 订单ID : 808f2a114a354e4b956d0208b3cceacf
     */

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
    public String moduleName;

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
}
