package com.system.bhouse.bhouse.setup.notification.bean;

import net.qiujuer.italker.common.adapter.entity.SectionEntity;

/**
 * Created by Administrator on 2018-06-04.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.setup.notification.bean
 */

public class XGNotificationSectionEntity extends SectionEntity<XGNotification>{
    private boolean isMore;
    public XGNotificationSectionEntity(boolean isHeader, String header, boolean isMroe) {
        super(isHeader, header);
        this.isMore = isMroe;
    }

    public XGNotificationSectionEntity(XGNotification t) {
        super(t);
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean mroe) {
        isMore = mroe;
    }
}
