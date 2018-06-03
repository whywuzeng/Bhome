package com.system.bhouse.bhouse.CommonTask.model;

import java.util.List;

/**
 * Created by anfs on 20/12/2016.
 * 项目筛选条件
 */
public class FilterModel {
    public FilterModel() {
        status = 0;     //状态
        label = null;   //记录数
        keyword = null; //订单编号
    }

    public FilterModel(int status, String keyword) {
        this.status = status;
        this.label = null;
        this.keyword = keyword;
    }

    public FilterModel(String label, String keyword) {
        this.label = label;
        this.status = 0;
        this.keyword = keyword;
    }

    public FilterModel(int status, String label, String keyword) {
        this.status = status;
        this.label = label;
        this.keyword = keyword;
    }

    public FilterModel(String keyword) {
        this.label = null;
        this.status = 0;
        this.keyword = keyword;
    }

    public FilterModel(int status) {
        this.label = null;
        this.status = status;
        this.keyword = null;
    }


    public int status;//任务状态，进行中的为1，已完成的为2
    public String label;//任务标签
    public String keyword;//根据关键字筛选任务
    public boolean isShow=true;

    public FilterModel(List<TaskLabelModel> labelModels) {
        this.labelModels = labelModels;
    }

    public boolean isFilter() {
        return status != 0 || label != null && keyword != null;
    }

    public long statusTaskDoing;
    public long statusTaskDone;
    public List<TaskLabelModel> labelModels;
}
