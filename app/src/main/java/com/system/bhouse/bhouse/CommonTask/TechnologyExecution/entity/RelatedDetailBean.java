package com.system.bhouse.bhouse.CommonTask.TechnologyExecution.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018-07-18.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution.entity
 */

public class RelatedDetailBean {

    /**
     * 单据名称 : 人工派工单
     * 单据状态 : 已完成
     * 源单ID : cf1af6a7d60e4eab93c75442b097ac98
     */

    @SerializedName("单据名称")
    private String documentName;
    @SerializedName("单据状态")
    private String documentStatus;
    @SerializedName("源单ID")
    private String soutceID;

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public String getSoutceID() {
        return soutceID;
    }

    public void setSoutceID(String soutceID) {
        this.soutceID = soutceID;
    }
}
