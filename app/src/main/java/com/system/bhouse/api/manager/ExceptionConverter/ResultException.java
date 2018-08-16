package com.system.bhouse.api.manager.ExceptionConverter;

/**
 * Created by Administrator on 2018-08-15.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.api.manager.ExceptionConverter
 */

public class ResultException extends RuntimeException{
    private int errCode = 0;

    public ResultException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
    }

    public int getErrCode() {
        return errCode;
    }
}
