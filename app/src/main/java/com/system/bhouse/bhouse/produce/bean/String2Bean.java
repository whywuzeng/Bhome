package com.system.bhouse.bhouse.produce.bean;

/**
 * Created by Administrator on 2017-9-11.
 */

public class String2Bean {

//    0 备料成功,1 虚拟公司不能执行, 2 当前单据未审核, 3 备料失败

    public static String[] StartBeiliaos={"备料成功","虚拟公司不能执行","当前单据未审核","备料失败"};

//     0 完成备料,1 虚拟公司不能执行, 2 当前单据未开始备料, 3 完成备料失败

    public static String[] EndBeiliaos={"完成备料","虚拟公司不能执行","当前单据未开始备料","完成备料失败"};

//    0 取消备料,1 虚拟公司不能执行, 2 当前单据未开始备料, 3 已进行领料,4 取消备料失败

    public static String[] Cancelbeiliaos={"取消备料","虚拟公司不能执行","当前单据未开始备料","已进行领料","取消备料失败"};

    public static String IsNUll="改单据的数据为空";
}
