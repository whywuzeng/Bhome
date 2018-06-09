package com.system.bhouse.base;

import java.io.Serializable;

/**
 * Created by Administrator on 2018-06-07.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.base
 */

public abstract class StatusBeanImpl implements Serializable{

    public boolean visModifyBtn=false;
    public boolean visQRBtn=false;
    public boolean visSubmitBtn=false;
    public boolean visCheckBtn=false;
    public boolean visCheckFBtn=false;
    public boolean visDeleteBtn=false;

    public StatusBeanImpl setVisModifyBtn(boolean visModifyBtn) {
        this.visModifyBtn = visModifyBtn;
        return this;
    }

    public StatusBeanImpl setVisQRBtn(boolean visQRBtn) {
        this.visQRBtn = visQRBtn;
        return this;
    }

    public StatusBeanImpl setVisSubmitBtn(boolean visSubmitBtn) {
        this.visSubmitBtn = visSubmitBtn;
        return this;
    }

    public StatusBeanImpl setVisCheckBtn(boolean visCheckBtn) {
        this.visCheckBtn = visCheckBtn;
        return this;
    }

    public StatusBeanImpl setVisCheckFBtn(boolean visCheckFBtn) {
        this.visCheckFBtn = visCheckFBtn;
        return this;
    }

    public StatusBeanImpl setVisDeleteBtn(boolean visDeleteBtn) {
        this.visDeleteBtn = visDeleteBtn;
        return this;
    }

    protected boolean NewStatus=false;
    protected boolean LookStatus=false;
    protected boolean ModifyStatus=false;

    public abstract boolean isNewStatus();
    public abstract void setNewStatus(boolean newStatus);
    public abstract boolean isLookStatus();
    public abstract void setLookStatus(boolean lookStatus);
    public abstract boolean isModifyStatus();
    public abstract void setModifyStatus(boolean modifyStatus);
}
