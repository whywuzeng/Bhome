package com.system.bhouse.base;

/**
 * Created by Administrator on 2018-06-07.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.base
 */
//审批状态的行为
public class CheckStatusBeanImpl extends StatusBeanImpl{

    //审批状态没有新建行为
    @Override
    public boolean isNewStatus() {
        return false;
    }

    //此行为无意义
    @Override
    public void setNewStatus(boolean newStatus) {
        this.NewStatus=newStatus;
    }

    @Override
    public boolean isLookStatus() {
        return LookStatus;
    }

    @Override
    public void setLookStatus(boolean lookStatus) {
        this.LookStatus=lookStatus;
    }

    //审批状态没有修改行为
    @Override
    public boolean isModifyStatus() {
        return false;
    }

    //此方法也无意义
    @Override
    public void setModifyStatus(boolean modifyStatus) {
        this.ModifyStatus=modifyStatus;
    }
}
