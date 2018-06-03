package com.system.bhouse.bhouse.DingdanManage.UiInterface;

import com.system.bhouse.base.Baseview;
import com.system.bhouse.bean.DingdanZhuangTai;

import java.util.List;

/**
 * Created by Administrator on 2016-4-15.
 * ClassName: com.system.bhouse.bhouse.DingdanManage.UiInterface
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public interface DingdanViewInterface extends Baseview {
    void updateNewsList(List<DingdanZhuangTai> beans);

    void CompleteforV();
}
