package com.system.bhouse.utils;

import android.view.View;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2018-03-09.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.utils
 */

public class DoubleClick {


    private static final String TAG = "DoubleClick";

   static class  SomeOnSsubscribe implements Observable.OnSubscribe<Object> {

        private View mView;

        public SomeOnSsubscribe(View mView) {
            this.mView = mView;
        }

        @Override
        public void call(Subscriber<? super Object> subscriber) {
            mView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    subscriber.onNext(view);
                }
            });

        }
    }

    public static Observable avoidDoubleClick(View mButton){
        Observable mClickStream = Observable.create(new SomeOnSsubscribe(mButton)).debounce(1000, TimeUnit.MILLISECONDS);
        return mClickStream;

    }
}

//    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
