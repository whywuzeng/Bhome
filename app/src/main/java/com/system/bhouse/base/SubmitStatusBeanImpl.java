package com.system.bhouse.base;

/**
 * Created by Administrator on 2018-06-07.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.base
 */
//提交状态行为
public class SubmitStatusBeanImpl extends StatusBeanImpl{
    @Override
    public boolean isNewStatus() {
        return NewStatus;
    }

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

    @Override
    public boolean isModifyStatus() {
        return ModifyStatus&&LookStatus;
    }

    @Override
    public void setModifyStatus(boolean modifyStatus) {
        if (LookStatus) {
            ModifyStatus = modifyStatus;
        }
    }
}
