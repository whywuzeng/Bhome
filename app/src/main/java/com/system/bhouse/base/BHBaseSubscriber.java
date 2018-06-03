package com.system.bhouse.base;

import android.support.annotation.CallSuper;
import android.util.Log;

import com.system.bhouse.adpter.RequestError;
import com.system.bhouse.utils.NetworkUtils;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/8/24 0024.
 * 把回调各个方法统一处理，并且这里对返回错误做了统一处理
 */
public class BHBaseSubscriber<T> extends Subscriber<T> {

    private RequestError requestError;

    public BHBaseSubscriber(RequestError requestError) {
        this.requestError=requestError;
    }

    @CallSuper
    @Override
    public void onStart() {
        super.onStart();
    }

    @CallSuper
    @Override
    public void onCompleted() {

    }

    @CallSuper
    @Override
    public void onError(Throwable e) {
        String errorMsg = null;
        if (e instanceof HttpException) {
            switch (((HttpException) e).code()) {
                case 403:
                    errorMsg = "没有权限访问此链接！";
                    break;
                case 504:
                    if (!NetworkUtils.isNetworkAvailable(App.getContextApp())) {
                        errorMsg = "没有联网哦！";
                    }
                    else {
                        errorMsg = "网络连接超时！";
                    }
                    break;
                default:
                    errorMsg = ((HttpException) e).message();
                    break;
            }
        }
        else if (!NetworkUtils.isNetworkAvailable(App.getContextApp())) {
            errorMsg = "没有联网哦！";
        }
        else if (e instanceof UnknownHostException) {
            errorMsg = "不知名主机！";
        }
        else if (e instanceof SocketTimeoutException) {
            errorMsg = "网络连接超时！";
        }
        else if (e instanceof Exception) {
            errorMsg = "未知异常！";
            Log.e("errorMsg","错误",e);
        }
        requestError.forRequestError(errorMsg);
    }

    @CallSuper
    @Override
    public void onNext(T t) {
    }

}
