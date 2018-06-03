package com.system.bhouse.bhouse.DingdanManage.Model;

import com.google.gson.Gson;
import com.socks.library.KLog;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.RequestCallBack;
import com.system.bhouse.bean.DingdanZhuangTai;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Administrator on 2016-4-15.
 * ClassName: com.system.bhouse.bhouse.DingdanManage.Model
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class DingdanInteractorImpl implements DingdanInteractor<List<DingdanZhuangTai>> {
    @Override
    public  Subscription getDingdanData(final RequestCallBack<List<DingdanZhuangTai>> T, final String username,int mid,int statusid,boolean checktrue) {

//        return Observable.create(new Observable.OnSubscribe<bean>() {
//            @Override
//            public void call(Subscriber<? super bean> subscriber) {
//                bean bean=null;
//                for (int i = 0; i < 20; i++) {
//                     bean = new bean(id + ": title"+i, id + ": desc" + i, id + ": phone" + i, id + ": time" + i);
//                }
//                subscriber.onNext(bean);
//            }
//        })
//                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<bean>() {
//                    @Override
//                    public void onCompleted() {
//                        T.requestComplete();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        T.RequestError(e.getLocalizedMessage());
//                    }
//
//                    @Override
//                    public void onNext(bean s) {
//                        T.requestSuccess(s);
//                    }
//                });
           /* //这里后台接受  这里是真正可以运行的Http的部分 下面*/
//            return ApiServiceUtils.getInstence().TestgetDingdanById(username, mid, statusid, checktrue).subscribe(new Observer<DingdanZhuangTai[]>() {
//                @Override
//                public void onCompleted() {
//                    T.requestComplete();
//                }
//
//                @Override
//                public void onError(Throwable e) {
//                    T.RequestError(e.getLocalizedMessage() + "\n" + e + "提交的id： " + username);
//                }
//
//                @Override
//                public void onNext(DingdanZhuangTai[] wholeConames) {
//                    List<DingdanZhuangTai> wholeConameList = new ArrayList<DingdanZhuangTai>();
//                    for (DingdanZhuangTai temple : wholeConames) {
//                        wholeConameList.add(temple);
//                    }
//                    T.requestSuccess(wholeConameList);
//                    KLog.e(wholeConameList.toString());
//                }
//            });
            //订单状态更新获取数据
            return ApiWebService.getDingdanStatusmore(new Subscriber() {
                @Override
                public void onCompleted() {
                    T.requestComplete();
                }

                @Override
                public void onError(Throwable e) {
                    T.requestError(e.getLocalizedMessage() + "\n" + e + "提交的id： " + username);
                }

                @Override
                public void onNext(Object o) {
                    Gson gson=new Gson();
                    DingdanZhuangTai[] wholeConames = gson.fromJson(o.toString(), DingdanZhuangTai[].class);
                    List<DingdanZhuangTai> wholeConameList = new ArrayList<DingdanZhuangTai>();
                    for (DingdanZhuangTai temple : wholeConames) {
                        wholeConameList.add(temple);
                    }
                    T.requestSuccess(wholeConameList);
                    KLog.e(wholeConameList.toString());
                }
            },username,mid,statusid,checktrue);
        }
}
