package com.system.bhouse.bhouse.setup.WWCommon;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.system.bhouse.bhouse.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by chaochen on 14-10-7.
 * 封装了下拉刷新
 */
public abstract class RefreshBaseFragment extends WWBaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String UPDATE_ALL = "999999999";
    public static final int UPDATE_ALL_INT = 999999999;

    protected Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==REFRESH_DATA)
            {
                setRefreshing(false);
            }
        }
    };

    protected int REFRESH_DATA=0x4564654;
    protected SwipeRefreshLayout swipeRefreshLayout;

    protected final boolean isRefreshing() {
        if (swipeRefreshLayout != null) {
            return swipeRefreshLayout.isRefreshing();
        }

        return false;
    }

    protected final void setRefreshing(boolean refreshing) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(refreshing);
        }
    }

    protected final void initRefreshLayout() {
        View rootView = getView();
        if (rootView == null) {
            return;
        }

        View swipe = rootView.findViewById(R.id.swipeRefreshLayout);
        if (swipe == null) {
            return;
        }
        
        swipeRefreshLayout = (SwipeRefreshLayout) swipe;
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.green);
    }

    protected final void disableRefreshing() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setEnabled(false);
        }
    }

    protected final void enableSwipeRefresh(boolean enable) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setEnabled(enable);
        }
    }

    protected final void exampleLoadingFinishe()
    {
        final Observable.OnSubscribe<Object> objectOnSubscribe = new Observable.OnSubscribe<Object>(){
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                subscriber.onCompleted();
            }
        };

        Observable.create(objectOnSubscribe).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .delay(2, TimeUnit.SECONDS).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                Message message=Message.obtain();
                handler.sendEmptyMessage(REFRESH_DATA);
            }

            @Override
            public void onError(Throwable e) {
               handler.sendEmptyMessage(REFRESH_DATA);
            }

            @Override
            public void onNext(Object o) {

            }
        });

    }
}
