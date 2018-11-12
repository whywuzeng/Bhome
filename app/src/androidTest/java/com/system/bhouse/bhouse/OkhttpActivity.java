package com.system.bhouse.bhouse;

import android.test.ActivityTestCase;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018-11-12.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse
 */
public class OkhttpActivity extends ActivityTestCase {


    private final OkHttpClient httpClient =new OkHttpClient();
    @Override
    protected void runTest() throws Throwable {
        super.runTest();

        onMultiHeadValue();
    }

    private void onMultiHeadValue() throws IOException {
        final Request request = new Request.Builder().url("https://api.github.com/repos/square/okhttp/issues")
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build();
        final Response execute = httpClient.newCall(request).execute();
        if (!execute.isSuccessful())
        {
            throw new IOException();
        }
        System.out.println("Server :"+execute.header("Server"));
        System.out.println("Date:"+execute.header("Date"));
        System.out.println("Vary:"+execute.headers("Vary"));
    }

}
