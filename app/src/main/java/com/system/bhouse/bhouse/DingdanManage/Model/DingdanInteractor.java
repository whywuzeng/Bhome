package com.system.bhouse.bhouse.DingdanManage.Model;

import com.system.bhouse.base.RequestCallBack;

import rx.Subscription;

/**
 * Created by Administrator on 2016-4-15.
 * ClassName: com.system.bhouse.bhouse.DingdanManage.Model
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public interface DingdanInteractor<T> {
    Subscription getDingdanData(RequestCallBack<T> T,String username,int mid,int statusid,boolean checktrue);
}
