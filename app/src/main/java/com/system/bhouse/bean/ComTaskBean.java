package com.system.bhouse.bean;

import com.google.gson.annotations.SerializedName;
import com.system.bhouse.base.App;
import com.system.bhouse.utils.TenUtils.DataFormatUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2018-03-07.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bean
 */

public class ComTaskBean {

    /**
     * ID : 9dc7b4b3882048f5bccea193ccdf6fc3
     * 订单编号 : DZXQ-7-201801-0001
     * 项目名称 : 麓谷一期项目
     * 栋 : 1
     * 层 : 2
     * 需求日期 : 2018/1/18 16:54:38
     * 描述 : 测试单据
     * 状态 : 审核
     * 录入人 : 管理员
     * 录入时间 : 2018/1/18 16:55:03
     * 分录ID : 105e5d5f10db4a0884ff6161752c5320
     * 物料ID : acb6fd62b2f0405292fe8c0de0737f2f
     * 物料编码 : 1002.1084.0100.003
     * 物料名称 : PC构件
     * 规格型号 : 测试
     * 计量单位ID : c8e082b5f5f34d5f934f071e6b464238
     * 计量单位 : 块
     * 数量 : 30
     *
     * [{
     "物料ID": "acb6fd62b2f0405292fe8c0de0737f2f",
     "物料编码": "1002.1084.0100.003",
     "物料名称": "PC构件",
     "规格型号": "测试",
     "计量单位ID": "c8e082b5f5f34d5f934f071e6b464238",
     "计量单位": "块",
     "数量": 10
     }]
     */

    public String ID;
    @SerializedName("订单编号")
    public String hNumbe;
    @SerializedName("项目名称")
    public String projectName;
    @SerializedName("栋")
    public String dong;
    @SerializedName("层")
    public String ceng;
    @SerializedName("需求日期")
    public String requireData;
    @SerializedName("描述")
    public String description;
    @SerializedName("状态")
    public String status;
    @SerializedName("录入人")
    public String enterPeople;
    @SerializedName("录入时间")
    public String entryTime;

    public String getCheckPeople() {
        return checkPeople;
    }

    public void setCheckPeople(String checkPeople) {
        this.checkPeople = checkPeople;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    @SerializedName("审核人")
    public String checkPeople;
    @SerializedName("审核时间")
    public String checkTime;
    @SerializedName("分录ID")
    public String childTableID;
    @SerializedName("物料ID")
    public String goodsID;
    @SerializedName("物料编码")
    public String goodsCoding;
    @SerializedName("物料名称")
    public String goodsName;
    @SerializedName("规格型号")
    public String Specification;
    @SerializedName("计量单位ID")
    public String measureID;
    @SerializedName("计量单位")
    public String measure;
    @SerializedName("数量")
    public int amount;

    private HashMap<String,String> idNames =new HashMap<>();
    private HashMap<String,String> idValue=new HashMap<>();

    public HashMap<String, String> getIdNames() {
        setIdNames();
        return idNames;
    }

    public HashMap<String, String> getIdValue() {
        setIdValue();
        return idValue;
    }

    public void setIdNames() {
        this.idNames.put("ID",ID);
        this.idNames.put("hNumbe","订单编号");
        this.idNames.put("projectName","项目名称");
        this.idNames.put("dong","栋");
        this.idNames.put("ceng","层");
        this.idNames.put("requireData","需求日期");
        this.idNames.put("description","描述");
        this.idNames.put("status","状态");
        this.idNames.put("enterPeople","录入人");
        this.idNames.put("entryTime","录入时间");
        this.idNames.put("checkPeople","审核人");
        this.idNames.put("checkTime","审核时间");
        this.idNames.put("childTableID","分录ID");
        this.idNames.put("goodsID","物料ID");
        this.idNames.put("goodsCoding","物料编码");
        this.idNames.put("goodsName","物料名称");
        this.idNames.put("Specification","规格型号");
        this.idNames.put("measureID","计量单位ID");
        this.idNames.put("measure","计量单位");
        this.idNames.put("amount","数量");
    }

    public void setIdValue() {
        this.idValue.put("ID",ID);
        this.idValue.put("hNumbe",hNumbe==null? App.HNumber:hNumbe);
        this.idValue.put("projectName",projectName==null?"":projectName);
        this.idValue.put("dong",dong==null?"":dong);
        this.idValue.put("ceng",ceng==null?"":ceng);
        this.idValue.put("requireData",requireData==null? DataFormatUtils.getCurrentTime():requireData);
        this.idValue.put("description",description==null?"":description);
        this.idValue.put("status",status==null?"提交":status);
        this.idValue.put("enterPeople",enterPeople==null? App.menname:enterPeople);
        this.idValue.put("entryTime",entryTime==null?DataFormatUtils.getCurrentSimpleTime():entryTime);
        this.idValue.put("checkPeople",checkPeople==null? App.USER_INFO:checkPeople);
        this.idValue.put("checkTime",checkTime==null?DataFormatUtils.getCurrentSimpleTime():checkTime);
        this.idValue.put("childTableID",childTableID);
        this.idValue.put("goodsID",goodsID);
        this.idValue.put("goodsCoding",goodsCoding);
        this.idValue.put("goodsName",goodsName);
        this.idValue.put("Specification",Specification);
        this.idValue.put("measureID",measureID);
        this.idValue.put("measure",measure);
        this.idValue.put("amount",amount+"");
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getHNumbe() {
        return hNumbe;
    }

    public void setHNumbe(String hNumbe) {
        this.hNumbe = hNumbe;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDong() {
        return dong;
    }

    public void setDong(String dong) {
        this.dong = dong;
    }

    public String getCeng() {
        return ceng;
    }

    public void setCeng(String ceng) {
        this.ceng = ceng;
    }

    public String getRequireData() {
        return requireData;
    }

    public void setRequireData(String requireData) {
        this.requireData = requireData;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEnterPeople() {
        return enterPeople;
    }

    public void setEnterPeople(String enterPeople) {
        this.enterPeople = enterPeople;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getChildTableID() {
        return childTableID;
    }

    public void setChildTableID(String childTableID) {
        this.childTableID = childTableID;
    }

    public String getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
    }

    public String getGoodsCoding() {
        return goodsCoding;
    }

    public void setGoodsCoding(String goodsCoding) {
        this.goodsCoding = goodsCoding;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSpecification() {
        return Specification;
    }

    public void setSpecification(String Specification) {
        this.Specification = Specification;
    }

    public String getMeasureID() {
        return measureID;
    }

    public void setMeasureID(String measureID) {
        this.measureID = measureID;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
