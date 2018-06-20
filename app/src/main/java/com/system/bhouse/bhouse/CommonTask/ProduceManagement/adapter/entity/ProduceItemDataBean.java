package com.system.bhouse.bhouse.CommonTask.ProduceManagement.adapter.entity;

/**
 * Created by Administrator on 2018-06-20.
 * <p>
 * by author wz
 * 给生产界面  item 填充数据的基类
 * <p>
 * com.system.bhouse.bhouse.CommonTask.ProduceManagement.adapter
 */

public class ProduceItemDataBean {
    public ProduceItemDataBean(int resId,String subtext) {
        this.resId = resId;
        this.subtext=subtext;
    }
    public int resId;
    public String subtext;

}
