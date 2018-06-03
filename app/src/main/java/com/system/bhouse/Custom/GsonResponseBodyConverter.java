package com.system.bhouse.Custom;

import com.google.gson.Gson;
import com.system.bhouse.bean.UserInfo;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Administrator on 2016-3-15.
 */
final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, UserInfo[]> {
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override public UserInfo[] convert(ResponseBody value) throws IOException {
        Reader reader = value.charStream();
        try {
            return gson.fromJson(reader, UserInfo[].class);
        } finally {
            reader.close();
        }
    }
}