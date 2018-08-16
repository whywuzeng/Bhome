package com.system.bhouse.api.manager.ExceptionConverter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018-08-15.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.api.manager.ExceptionConverter
 */

public class ErrResponse {
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @SerializedName("body")
    public String msg;
}
