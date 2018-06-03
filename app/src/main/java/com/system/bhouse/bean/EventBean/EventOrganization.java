package com.system.bhouse.bean.EventBean;

/**
 * Created by Administrator on 2018-04-13.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bean.EventBean
 */

public class EventOrganization {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public EventOrganization(String message) {
        this.message = message;
    }
}
