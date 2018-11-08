package com.system.bhouse.bhouse.phone.activity.bean;

import com.system.bhouse.bhouse.CommonTask.ProduceManagement.adapter.entity.MultipleItem;

/**
 * Created by Administrator on 2018-10-16.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.phone.activity.bean
 */

public class ScanSectionMultipleiItem extends MultipleItem<ScanBean> {

    public ScanSectionMultipleiItem(ScanBean scanBean, int itemType, int spanSize, String content) {
        super(scanBean, itemType, spanSize, content);
    }

    public ScanSectionMultipleiItem(ScanBean scanBean, int itemType, int spanSize) {
        super(scanBean, itemType, spanSize);
    }

    public ScanSectionMultipleiItem(boolean isHeader, String headtitle) {
        super(isHeader, headtitle);
    }
}
