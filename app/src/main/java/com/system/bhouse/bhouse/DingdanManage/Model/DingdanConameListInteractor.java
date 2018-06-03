package com.system.bhouse.bhouse.DingdanManage.Model;

import com.system.bhouse.base.RequestCallBack;
import com.system.bhouse.bean.Dingdan;
import com.system.bhouse.bean.DingdanDetail;

import java.util.List;

import rx.Subscription;

/**
 * Created by Administrator on 2016-4-22.
 * ClassName: com.system.bhouse.bhouse.DingdanManage.Model
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public interface DingdanConameListInteractor {
    Subscription getDingdanConameData(RequestCallBack<List<DingdanDetail>> callBack,String id);

    Subscription getDingdanDingdanData(RequestCallBack<Dingdan> callBack,String id);
}
