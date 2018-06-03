package com.system.bhouse.bhouse.DingdanManage.UiInterface;

import com.system.bhouse.Common.DataLoadType;
import com.system.bhouse.base.Baseview;
import com.system.bhouse.bean.Dingdan;
import com.system.bhouse.bean.DingdanDetail;
import com.system.bhouse.bean.PicUpLoad;

import java.util.List;

/**
 * Created by Administrator on 2016-4-22.
 * ClassName: com.system.bhouse.bhouse.DingdanManage.UiInterface
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public interface DingdanConameListView extends Baseview {
    void initConameData(List<DingdanDetail> data,DataLoadType type);
    void initDingdanData(Dingdan data,DataLoadType type);

    void getUploadpic(PicUpLoad[] s);

}
