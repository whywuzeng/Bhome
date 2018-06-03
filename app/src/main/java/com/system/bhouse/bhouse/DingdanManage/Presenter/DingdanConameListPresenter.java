package com.system.bhouse.bhouse.DingdanManage.Presenter;

import com.system.bhouse.Common.LoadOrder;
import com.system.bhouse.base.BasePresenter;

/**
 * Created by Administrator on 2016-4-22.
 * ClassName: com.system.bhouse.bhouse.DingdanManage.Presenter
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public interface DingdanConameListPresenter extends BasePresenter {
    void RefreshData(String id,LoadOrder type);

    void RefreshDataPicUpload(String ccid,String sid,String key,String name,LoadOrder type);
}
