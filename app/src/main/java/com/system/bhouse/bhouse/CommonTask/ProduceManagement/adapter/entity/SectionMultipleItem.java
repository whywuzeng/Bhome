package com.system.bhouse.bhouse.CommonTask.ProduceManagement.adapter.entity;

/**
 * Created by Administrator on 2018-06-20.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.ProduceManagement.adapter
 */

public class SectionMultipleItem extends MultipleItem<ProduceItemDataBean> {

    public SectionMultipleItem(ProduceItemDataBean produceItemDataBean, int itemType, int spanSize, String content) {
        super(produceItemDataBean, itemType, spanSize, content);
    }

    public SectionMultipleItem(ProduceItemDataBean produceItemDataBean, int itemType, int spanSize) {
        super(produceItemDataBean, itemType, spanSize);
    }

    public SectionMultipleItem(boolean isHeader, String headtitle) {
        super(isHeader, headtitle);
    }
}
