package com.system.bhouse.bhouse.DingdanManage.Model;

import com.google.gson.Gson;
import com.system.bhouse.api.ApiServiceUtils;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.RequestCallBack;
import com.system.bhouse.bean.Dingdan;
import com.system.bhouse.bean.DingdanDetail;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016-4-22.
 * ClassName: com.system.bhouse.bhouse.DingdanManage.Model
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class DingdanConameListInteractorImpl implements DingdanConameListInteractor {
    //总的只有一个
    @Override
    public Subscription getDingdanConameData(final RequestCallBack<List<DingdanDetail>> callBack, String id) {
//        return ApiServiceUtils.getInstence().getDingdandetail(Integer.valueOf(id)).subscribeOn(Schedulers.io()).
//                observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<DingdanDetail[]>() {
//            @Override
//            public void onCompleted() {
//                callBack.requestComplete();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//            callBack.RequestError(e.getLocalizedMessage() +"\n" +e);
//            }
//
//            @Override
//            public void onNext(DingdanDetail[] wholeConames) {
//                List<DingdanDetail> dingdanDetails=new ArrayList<DingdanDetail>();
//                for(DingdanDetail detail:wholeConames){
//                    dingdanDetails.add(detail);
//                }
//                callBack.requestSuccess(dingdanDetails);
//            }
//        });

        //用webservice访问
        return ApiWebService.getDingdandetails(new Subscriber() {
            @Override
            public void onCompleted() {
                callBack.requestComplete();
            }

            @Override
            public void onError(Throwable e) {
                callBack.requestError(e.getLocalizedMessage() +"\n" +e);
            }

            @Override
            public void onNext(Object o) {
                Gson gson=new Gson();
                DingdanDetail[] wholeConames = gson.fromJson(o.toString(), DingdanDetail[].class);
                List<DingdanDetail> dingdanDetails=new ArrayList<DingdanDetail>();
                for(DingdanDetail detail:wholeConames){
                    dingdanDetails.add(detail);
                }
                callBack.requestSuccess(dingdanDetails);
            }
        }, Integer.valueOf(id));
    }

    @Override
    public Subscription getDingdanDingdanData(final RequestCallBack<Dingdan> callBack, String id) {

        return ApiServiceUtils.getInstence().getDingdanById(Integer.valueOf(id)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Dingdan[]>() {
            @Override
            public void onCompleted() {
                callBack.requestComplete();
            }

            @Override
            public void onError(Throwable e) {
                callBack.requestError(e.getLocalizedMessage()+"\n"+e);
            }

            @Override
            public void onNext(Dingdan[] dingdans) {
                callBack.requestSuccess(dingdans[0]);
            }
        });
    }
}
