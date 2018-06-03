package com.system.bhouse.bhouse.setup.notification.bean;

import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.entity.SectionEntity;

/**
 * Created by Administrator on 2018-05-30.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.setup.notification.adapter
 */

public class NotificationSectionBean extends SectionEntity<String>{
    private boolean isMore;
    public NotificationSectionBean(boolean isHeader, String header, boolean isMroe) {
        super(isHeader, header);
        this.isMore = isMroe;
    }

    public NotificationSectionBean(String t) {
        super(t);
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean mroe) {
        isMore = mroe;
    }
}
