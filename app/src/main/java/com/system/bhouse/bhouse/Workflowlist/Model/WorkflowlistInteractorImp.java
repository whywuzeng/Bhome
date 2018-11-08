package com.system.bhouse.bhouse.Workflowlist.Model;

import com.system.bhouse.base.RequestCallBack;

import rx.Subscription;

/**
 * Created by Administrator on 2017-03-16.
 * ClassName: com.system.bhouse.bhouse.Workflowlist.Model
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public  class WorkflowlistInteractorImp implements WorkflowlistInteractor {
    @Override
    public Subscription getWorkflowlist(final RequestCallBack<Object> callBack, String status, int rownum, String username, String FormNumber) {
        
//        return ApiWebService.getGetWorkflowlistMessagedt(new Subscriber() {
//            @Override
//            public void onCompleted() {
//                callBack.requestComplete();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//            callBack.requestError(e.getLocalizedMessage());
//            }
//
//            @Override
//            public void onNext(Object o) {
//                Log.i("TAG", "onNext: "+o.toString());
//            }
//        },status,rownum,username,FormNumber);
        return null;
    }
}
