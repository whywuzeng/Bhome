package com.system.bhouse.base.rxlife;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;
import com.system.bhouse.utils.LogUtil;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

/**
 * Created by Administrator on 2018-09-07.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.base.rxlife
 */

public class RxCompatActivity extends WWBackActivity {
    private static final String TAG = "ComTaskActivity";
    private final static BehaviorSubject<ActivityEventTag> BEHAVIOR_SUBJECT= BehaviorSubject.create();

    public <T> Observable.Transformer<T,T> bindUnitEvent(ActivityEventTag eventTag){
        Observable<ActivityEventTag> eventTagObservable = BEHAVIOR_SUBJECT.takeFirst(new Func1<ActivityEventTag, Boolean>() {
            @Override
            public Boolean call(ActivityEventTag activityEventTag) {
                LogUtil.e(TAG,"activityEventTag is:"+activityEventTag);
                return activityEventTag.equals(eventTag);
            }
        });

        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> soureOb) {

                return soureOb.takeUntil(eventTagObservable);
            }
        };
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BEHAVIOR_SUBJECT.onNext(ActivityEventTag.CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BEHAVIOR_SUBJECT.onNext(ActivityEventTag.RESUME);
    }

    @Override
    protected void onStart() {
        super.onStart();
        BEHAVIOR_SUBJECT.onNext(ActivityEventTag.START);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BEHAVIOR_SUBJECT.onNext(ActivityEventTag.PAUSE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        BEHAVIOR_SUBJECT.onNext(ActivityEventTag.STOP);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BEHAVIOR_SUBJECT.onNext(ActivityEventTag.DESTORY);
    }
}
