package com.system.bhouse.bhouse.DingdanManage.Model;

import com.system.bhouse.base.RequestCallBack;

import rx.Subscription;

/**
 * Created by Administrator on 2016-5-26.
 * ClassName: com.system.bhouse.bhouse.DingdanManage.Model
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public interface UploadPicInteractor<T> {
    Subscription getUploadPic(RequestCallBack<T> callBack,String ccid,String sid,String key,String name);

}
