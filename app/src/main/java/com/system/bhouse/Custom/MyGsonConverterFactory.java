package com.system.bhouse.Custom;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Administrator on 2016-3-15.
 */
public final class MyGsonConverterFactory extends Converter.Factory {

    static final GsonBuilder gsonBuilder = new GsonBuilder();
    static final Gson gson1 = gsonBuilder.create();

    public static MyGsonConverterFactory create() {

        return create(gson1);
    }

    public static MyGsonConverterFactory create(Gson gson) {
        return new MyGsonConverterFactory(gson);
    }

    private final Gson gson;

    private MyGsonConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        return new GsonResponseBodyConverter<>(gson, type);
    }

    public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
        return new GsonRequestBodyConverter<>(gson, type);
    }
}