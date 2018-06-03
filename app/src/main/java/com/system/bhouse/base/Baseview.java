package com.system.bhouse.base;

import com.system.bhouse.Common.LoadOrder;

/**
 * Created by Administrator on 2016-4-15.
 * ClassName: com.system.bhouse.base
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public interface Baseview {
    void basetoast(String msg);
    void showProgress();
    void hideProgress();
    void ErrorRefresh(LoadOrder type);
}
