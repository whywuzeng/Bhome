package com.system.bhouse.bhouse.okhttp;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018-11-12.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse
 */
public class okhttp {

    private final OkHttpClient httpClient =new OkHttpClient();
    private OkHttpClient okHttpClient;

    @Test
    public void okHttpTest() throws IOException {
//        onMultiHeadValue();
//          onPostMarkDown();
        final File file = new File("");
        final File canonicalFile = file.getCanonicalFile();
                CacheResponse(canonicalFile);
        CacheResponseMethod();
    }

    /**
     * Vary:Accept-Encoding
     Vary:Accept    一个Vary字段返回多个值
     * @throws IOException
     */
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

    public static final MediaType MEDIA_TYPE_MARKDOWN =MediaType.parse("text/x-markdown;charset=utf-8");
    private void onPostMarkDown() throws IOException {

        String postBody =""
                + "Releases\n"
                + "--------\n"
                + "\n"
                + " * _1.0_ May 6, 2013\n"
                + " * _1.1_ June 15, 2013\n"
                + " * _1.2_ August 11, 2013\n";

        //指明文档格式
        final Request build = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
                .build();
        final Response response = httpClient.newCall(build).execute();
        if (!response.isSuccessful())
        {
            throw new IOException();
        }

        System.out.println(response.body().toString());
    }

    private void CacheResponseMethod() throws IOException {
        //504 Unsatisfiable Request
        final Request build = new Request.Builder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .url("http://publicobject.com/helloworld.txt")
                .build();
        final Response response = okHttpClient.newCall(build).execute();
        if (!response.isSuccessful()) {
            throw new IOException();
        }

        final String responseBoby1 = response.body().string();
        final Response response1 = response.cacheResponse();
        final Response response2 = response.networkResponse();

        final Response response5 = okHttpClient.newCall(build).execute();
        if (!response.isSuccessful()) {
            throw new IOException();
        }

        final String responseBoby2 = response5.body().toString();
        final Response response3 = response5.cacheResponse();
        final Response response4 = response5.networkResponse();
    }

    public void CacheResponse(File cacheDirectory) {
        int cacheSize=10*1024*1024;
        final Cache cache = new Cache(cacheDirectory, cacheSize);

        okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();

    }
}
