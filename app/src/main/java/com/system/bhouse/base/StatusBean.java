package com.system.bhouse.base;

import java.io.Serializable;

/**
 * Created by Administrator on 2018-03-25.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.base
 */

public class StatusBean implements Serializable{

    public void setBean(StatusBeanImpl bean) {
        this.bean = bean;
    }

    public StatusBeanImpl getBean() {
        return bean;
    }

    private StatusBeanImpl bean;


    public boolean isNewStatus() {
        return bean.isNewStatus();
    }

    public void setNewStatus(boolean newStatus) {
        bean.setNewStatus(newStatus);
    }

    public boolean isLookStatus() {
        return bean.isLookStatus();
    }

    public void setLookStatus(boolean lookStatus) {
        bean.setLookStatus(lookStatus);
    }

    public boolean isModifyStatus() {
//        return ModifyStatus&&LookStatus;
        return bean.isModifyStatus();
    }

    public void setModifyStatus(boolean modifyStatus) {
//        if (LookStatus) {
//            ModifyStatus = modifyStatus;
//        }
        bean.setModifyStatus(modifyStatus);
    }
}
