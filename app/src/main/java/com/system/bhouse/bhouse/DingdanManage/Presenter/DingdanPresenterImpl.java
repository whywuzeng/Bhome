package com.system.bhouse.bhouse.DingdanManage.Presenter;

import com.socks.library.KLog;
import com.system.bhouse.Common.LoadOrder;
import com.system.bhouse.base.BasePresenterImpl;
import com.system.bhouse.bean.DingdanZhuangTai;
import com.system.bhouse.bhouse.DingdanManage.Model.DingdanInteractor;
import com.system.bhouse.bhouse.DingdanManage.Model.DingdanInteractorImpl;
import com.system.bhouse.bhouse.DingdanManage.UiInterface.DingdanViewInterface;

import java.util.List;

/**
 * Created by Administrator on 2016-4-15.
 * ClassName: com.system.bhouse.bhouse.DingdanManage.Presenter
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class DingdanPresenterImpl extends BasePresenterImpl<DingdanViewInterface, List<DingdanZhuangTai>> implements DingdanPresenter {

    private LoadOrder list_load;
    private String id;
    private DingdanInteractor<List<DingdanZhuangTai>> mBeanDingdanInteractor;
    String username; int mid;int statusid; boolean checktrue;

    public DingdanPresenterImpl(DingdanViewInterface mDingdanViewInterface, String username, int mid, int statusid, boolean checktrue) {
        super(mDingdanViewInterface);
        mBeanDingdanInteractor = new DingdanInteractorImpl();
        if(checktrue==false) {
            mCompositeSubscription.add(mBeanDingdanInteractor.getDingdanData(this, username, mid, statusid, checktrue));
        }
        this.username = username;
        this.mid = mid;
        this.statusid = statusid;
        this.checktrue = checktrue;
    }

    @Override
    public void requestComplete() {
        super.requestComplete();

    }

    @Override
    public void beforeRequest() {
        super.beforeRequest();

    }

    @Override
    public void requestError(String msg) {
        super.requestError(msg);
        if(list_load!=LoadOrder.Second) {
            mView.ErrorRefresh(LoadOrder.Second);
        }
    }


    @Override
    public void requestSuccess(List<DingdanZhuangTai> data) {
        KLog.e("请求成功");
        mView.updateNewsList(data);
    }

    @Override
    public void refreshData(LoadOrder type) {

        int[] data = {65, 47};
//        int max=data.length;
//        int min=0;
//        Random random = new Random();
//        int s = random.nextInt(max)%(max-min+1) + min;

        int random1 = 1 + (int) (Math.random() * 2);

        mCompositeSubscription.add( mBeanDingdanInteractor.getDingdanData(this, username, mid,
         statusid, checktrue));
        if (type==LoadOrder.Second){
            list_load =LoadOrder.Second;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        if(checktrue==false) {
//            mSubscription = mBeanDingdanInteractor.getDingdanData(this, username, mid,
//                    statusid, checktrue);
//        }
    }
}
