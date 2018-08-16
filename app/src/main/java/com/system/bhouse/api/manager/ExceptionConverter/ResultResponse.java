package com.system.bhouse.api.manager.ExceptionConverter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018-08-15.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.api.manager.ExceptionConverter
 */

public class ResultResponse {

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @SerializedName("code")
    public int result;
}
