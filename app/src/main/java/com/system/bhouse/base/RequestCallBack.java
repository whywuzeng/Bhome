package com.system.bhouse.base;

/**
 * Created by Administrator on 2016-4-15.
 * ClassName: com.system.bhouse.base
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public interface RequestCallBack<T> {

    void beforeRequest();

    void  requestError(String msg);

    void  requestComplete();

    void  requestSuccess(T data);
}
