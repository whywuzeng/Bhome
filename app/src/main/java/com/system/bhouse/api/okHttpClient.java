package com.system.bhouse.api;

import com.socks.library.KLog;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by Administrator on 2016-6-1.
 * ClassName: com.system.bhouse.api
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class okHttpClient {

    public static OkHttpClient.Builder addProgressClient(OkHttpClient.Builder builder, final OnProgressResponseListener listener) {

//        OnProgressResponseListener listener=new OnProgressResponseListener() {
//            @Override
//            public void onResponseProgress(long bytesRead, long contentLength, boolean done) {
//                if (mProgressHandler==null){
//                    return;
//                }
//                progressBean.setBytesRead(bytesRead);
//                progressBean.setContentLength(contentLength);
//                progressBean.setDone(done);
//                mProgressHandler.sendMessage(progressBean);
//            }
//        };

        builder.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                KLog.d("start intercept");
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder().body(
                        new ProgressResponseBody(originalResponse.body(),listener))
                        .build();
            }
        });

        return builder;

    }
}
