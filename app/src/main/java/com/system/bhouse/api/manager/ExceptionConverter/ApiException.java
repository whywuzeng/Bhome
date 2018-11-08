package com.system.bhouse.api.manager.ExceptionConverter;

/**
 * Created by Administrator on 2018-08-16.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.api.manager.ExceptionConverter
 */

public class ApiException extends Exception {

    private final int code;
    private String displayMessage;

    public static final int UNKNOWN = 1000;
    public static final int PARSE_ERROR = 1001;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
    public String getDisplayMessage() {
        return displayMessage;
    }
    public void setDisplayMessage(String msg) {
        this.displayMessage = msg + "(code:" + code + ")";
    }
}