package com.system.bhouse.base;

import com.socks.library.KLog;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016-4-15.
 * ClassName: com.system.bhouse.base
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class BasePresenterImpl<T extends Baseview, V> implements BasePresenter, RequestCallBack<V>  {

    protected Subscription mSubscription;

    protected Subscription getmSubscription;

    //管理对象
    protected CompositeSubscription mCompositeSubscription
            = new CompositeSubscription();
    protected T mView;

    public BasePresenterImpl(T view){
        this.mView=view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        if(mSubscription!=null&&getmSubscription!=null&&!mSubscription.isUnsubscribed()&&!getmSubscription.isUnsubscribed()){

            mCompositeSubscription.add(mSubscription);
            mCompositeSubscription.add(getmSubscription);
        }

        if(null!=mCompositeSubscription){
            mCompositeSubscription.unsubscribe();
        }

        if (null != mSubscription && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mView = null;

        if(null!=getmSubscription&&!getmSubscription.isUnsubscribed()){
            getmSubscription.unsubscribe();
        }
    }

    @Override
    public void beforeRequest() {
        mView.showProgress();
    }

    @Override
    public void requestError(String msg) {
        KLog.e(msg);
        mView.basetoast(msg);
        mView.hideProgress();
    }

    @Override
    public void requestComplete() {
        mView.hideProgress();
    }

    @Override
    public void requestSuccess(V data) {

    }
}
