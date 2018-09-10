package com.system.bhouse.bhouse.CommonTask;


import android.support.annotation.NonNull;
import android.util.Log;

import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.base.rxlife.RxCompatActivity;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.utils.LogUtil;
import com.system.bhouse.utils.TenUtils.L;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017-12-7.
 * 吊装需求
 */

@EActivity(R.layout.comtask_activity)
public class ComTaskActivity extends RxCompatActivity {
    //    @ViewById(R.id.main_fragment)
//     IndexViewPager viewPager;
    private static final String TAG = "ComTaskActivity";
    private HangRequiretFragment taskFragment;


    @AfterViews
    void InitOnCreate() {
        setActionBarMidlleTitle("吊装需求");
//        viewPager.setAdapter(new MyFragmentIndexViewPager(getSupportFragmentManager()));
        taskFragment = HangRequiretFragment_.builder().build();
        if (taskFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, taskFragment).commit();
        }

        ApiWebService.Get_Hois_Req_Json(this, new ApiWebService.SuccessCall() {

            @Override
            public void SuccessBack(String result) {
                L.e(result);

            }

            @Override
            public void ErrorBack(String error) {

            }
        },  50 ,  "提交" , "", App.GSMID, App.Property, App.IsSub, App.USER_INFO);

    }

    public void TestMethodRxLift(){
        Observable.interval(2, TimeUnit.SECONDS).takeUntil(Observable.timer(5,TimeUnit.SECONDS)).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                LogUtil.d(TAG,"mun is :"+aLong);
            }
        });
    }

    private void startIntervalTask1() {
        Observable.interval(2, TimeUnit.SECONDS).compose(bindUntilDelay(5)).subscribe(new Action1<Long>() {
            @Override
            public void call(Long num) {
                Log.d("MainActivity", "num:" + num);
            }
        });

    }

    @NonNull
    private Observable.Transformer<Long, Long> bindUntilDelay(final int delaySecond) {
        return new Observable.Transformer<Long, Long>() {
            @Override
            public Observable<Long> call(Observable<Long> longObservable) {
                return longObservable.takeUntil(Observable.timer(delaySecond,TimeUnit.SECONDS));
            }
        };
    }

//    public <T> Observable.Transformer<T, T> bindUntilEvent(final ActivityEvent bindEvent) {
//        //被监视的Observable
//        final Observable<ActivityEvent> observable = lifeSubject.takeFirst(new Func1<ActivityEvent, Boolean>() {
//            @Override
//            public Boolean call(ActivityEvent event) {
//                return event.equals(bindEvent);
//            }
//        });
//
//        return new Observable.Transformer<T, T>() {
//            @Override
//            public Observable<T> call(Observable<T> sourceOb) {
//                return sourceOb.takeUntil(observable);
//            }
//        };
//    }

}

//class MyFragmentIndexViewPager extends SaveFragmentPagerAdapter {
//
//    public MyFragmentIndexViewPager(FragmentManager fm) {
//        super(fm);
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        ComTaskFragment taskFragment = new ComTaskFragment_();
//        saveFragment(taskFragment);
//        return taskFragment;
//    }
//
//    @Override
//    public int getCount() {
//        return 1;
//    }
//}
