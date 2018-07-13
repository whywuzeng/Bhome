package com.system.bhouse.bhouse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.system.bhouse.Custom.htextview.HTextView;
import com.system.bhouse.Custom.htextview.HTextViewType;
import com.system.bhouse.base.App;
import com.system.bhouse.base.BaseActivity;
import com.system.bhouse.bhouse.setup.notification.MyNotificationActivity_;
import com.system.bhouse.utils.AppManager;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Created by Administrator on 2016/3/7.
 */
public class WelcomActivity extends BaseActivity {

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    private HTextView hTextView5;

    private  Subscription subscribe;

    private MsgReceiver updateListViewReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hTextView5 = (HTextView) findViewById(R.id.linetextview111);

        hTextView5.setTextColor(Color.WHITE);
        hTextView5.setTypeface(null);
        hTextView5.animateText("wuzeng");
        hTextView5.setAnimateType(HTextViewType.LINE);
        updateListViewReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.system.bhouse.bhouse.activity.UPDATE_LISTVIEW");
        registerReceiver(updateListViewReceiver, intentFilter);


        Observable<Object> zhi = Observable.create(subscriber -> {
            hTextView5.animateText("智");
            subscriber.onCompleted();
        }).delay(700, TimeUnit.MILLISECONDS);
        Observable<Object> neng = Observable.create(subscriber -> {
            hTextView5.animateText("能");
            subscriber.onCompleted();
        }).delay(700, TimeUnit.MILLISECONDS).subscribeOn(AndroidSchedulers.mainThread());
        Observable<Object> zhi1 = Observable.create(subscriber -> {
            hTextView5.animateText("制");
            subscriber.onCompleted();
        }).delay(700, TimeUnit.MILLISECONDS).subscribeOn(AndroidSchedulers.mainThread());
        Observable<Object> zao = Observable.create(subscriber -> {
            hTextView5.animateText("造");
            subscriber.onCompleted();
        }).delay(700, TimeUnit.MILLISECONDS).subscribeOn(AndroidSchedulers.mainThread());

         subscribe = zhi.concatWith(neng).concatWith(zhi1).concatWith(zao).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(WelcomActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                AppManager.getAppManager().finishActivity(WelcomActivity.this);
                            }
                        }, 100);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }
                });


//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent=new Intent(WelcomActivity.this,LoginActivity.class);
//                startActivity(intent);
//                AppManager.getAppManager().finishActivity(WelcomActivity.this);
//            }
//        },3000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        XGPushClickedResult click = XGPushManager.onActivityStarted(this);
        if (click != null) {
            String cusContent = click.getCustomContent();
            if (!subscribe.isUnsubscribed()) {
                subscribe.unsubscribe();
            }
            if (TextUtils.isEmpty(App.USER_INFO)) {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            else {
                MyNotificationActivity_.intent(this).start();
            }
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        XGPushManager.onActivityStoped(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(updateListViewReceiver);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_line_text_view;
    }

    public class MsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

        }
    }
}


//class ClickListener implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//            if (v instanceof HTextView) {
//                if (index + 1 >= sentences.length) {
//                    index = 0;
//                }
//                ((HTextView) v).animateText(sentences[index++]);
//            }
//        }
//    }
