package com.system.bhouse.api;

/**
 * Created by Administrator on 2016-5-31.
 * ClassName: com.system.bhouse.comInterface
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public interface OnProgressResponseListener {
    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
