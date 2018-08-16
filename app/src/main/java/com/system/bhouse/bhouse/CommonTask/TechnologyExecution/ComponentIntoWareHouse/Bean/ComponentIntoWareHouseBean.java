package com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ComponentIntoWareHouse.Bean;

import com.google.gson.annotations.SerializedName;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.entity.TechnologyBaseBean;

/**
 * Created by Administrator on 2018-07-25.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ModuleAssignMent.Bean
 */

public class ComponentIntoWareHouseBean extends TechnologyBaseBean {


    /**
     * 业务日期 : 2018/8/9 9:49:04
     * 仓库ID : 8268302d925447ca85d14c5efb9bbc2f
     * 仓库名称 : 2号库位
     * 分录ID : f9006657b7c149b19b8189f5ede10345
     * 单据编号 : SCDDRK-7-201808-0001
     * 审核人 :
     * 审核时间 :
     * 录入人 : 管理员
     * 录入时间 : 2018/8/9 9:49:32
     * 数量 : 1.0
     * 来源单据ID : 808f2a114a354e4b956d0208b3cceacf
     * 来源类型 : 生产入库-入库质检
     * 来源类型ID : f5fa3f2a21a746f9aeb64a7a57d21138
     * 来源类型表 : Production_order_In_prid_QR_Code_check_r
     * 物料ID : acb6fd62b2f0405292fe8c0de0737f2f
     * 物料二维码 : DZXQ-7-201806-0009.1002.1084.0100.003.1
     * 物料名称 : PC构件
     * 物料编码 : 1002.1084.0100.003
     * 状态 : 保存
     * 规格型号 : 测试
     * 计量单位 : 块
     * 计量单位ID : c8e082b5f5f34d5f934f071e6b464238
     * 订单ID : 808f2a114a354e4b956d0208b3cceacf
     */
    @SerializedName("来源类型")
    public String soureTableType;
    @SerializedName("来源类型ID")
    public String soureTableTypeID;
    @SerializedName("来源类型表")
    public String soureTable;

    public String getWareHouse() {
        return wareHouse;
    }

    public void setWareHouse(String wareHouse) {
        this.wareHouse = wareHouse;
    }

    public String getWareHouseID() {
        return wareHouseID;
    }

    public void setWareHouseID(String wareHouseID) {
        this.wareHouseID = wareHouseID;
    }


    @SerializedName(value = "仓库",alternate = {"仓库名称"})
    public String wareHouse;
    @SerializedName("仓库ID")
    public String wareHouseID;
    @SerializedName(value = "分录描述")
    public String entrybeizhu="";
    @SerializedName("质检类型")
    public String checkType="合格";


}
