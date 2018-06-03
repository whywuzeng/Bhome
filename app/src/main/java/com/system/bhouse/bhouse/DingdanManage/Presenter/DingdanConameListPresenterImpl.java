package com.system.bhouse.bhouse.DingdanManage.Presenter;

import com.system.bhouse.Common.DataLoadType;
import com.system.bhouse.Common.LoadOrder;
import com.system.bhouse.base.BasePresenterImpl;
import com.system.bhouse.base.RequestCallBack;
import com.system.bhouse.bean.DingdanDetail;
import com.system.bhouse.bean.PicUpLoad;
import com.system.bhouse.bhouse.DingdanManage.Model.DingdanConameListInteractor;
import com.system.bhouse.bhouse.DingdanManage.Model.DingdanConameListInteractorImpl;
import com.system.bhouse.bhouse.DingdanManage.Model.UploadPicInteractor;
import com.system.bhouse.bhouse.DingdanManage.Model.UploadPicInteractorImpl;
import com.system.bhouse.bhouse.DingdanManage.UiInterface.DingdanConameListView;

import java.util.List;

/**
 * Created by Administrator on 2016-4-22.
 * ClassName: com.system.bhouse.bhouse.DingdanManage.Presenter
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class DingdanConameListPresenterImpl extends BasePresenterImpl<DingdanConameListView, List<DingdanDetail>> implements DingdanConameListPresenter {

    private DingdanConameListInteractor mDingdanConameListInteractor;
    private UploadPicInteractor pic_interactor;

    private String ccid;
    private String sid;
    private String key;
    private String  name;
    private LoadOrder list_type;
    private String EncodeSid;

    public DingdanConameListPresenterImpl(DingdanConameListView view, String id,String ccid, String sid, String key, String name) {
        super(view);
        mDingdanConameListInteractor = new DingdanConameListInteractorImpl();
        pic_interactor=new UploadPicInteractorImpl();

        this.ccid=id;
        mSubscription = mDingdanConameListInteractor.getDingdanConameData(this, id);

        this.EncodeSid=ccid;
        this.sid=sid;
        this.key=key;
        this.name=name;
    }

    @Override
    public void RefreshData(String id,LoadOrder type) {
        if(type==LoadOrder.Second) {
            list_type=LoadOrder.Second;
        }
        mSubscription = mDingdanConameListInteractor.getDingdanConameData(this, id);
    }

    @Override
    public void RefreshDataPicUpload(String ccid, String sid, String key, String name, final LoadOrder type) {
        if(!mSubscription.isUnsubscribed()) {
            getmSubscription= pic_interactor.getUploadPic(new RequestCallBack<PicUpLoad[]>() {
                @Override
                public void beforeRequest() {

                }

                @Override
                public void requestError(String msg) {
//                if(type==LoadOrder.First) {
//                    mView.ErrorRefresh(LoadOrder.First);
//                }
//                mView.showProgress();
                }

                @Override
                public void requestComplete() {

                }

                @Override
                public void requestSuccess(PicUpLoad[] data) {
                    mView.hideProgress();
                    mView.getUploadpic(data);
                }
            }, ccid, sid, key, name);

            mCompositeSubscription.add(getmSubscription);
        }

        this.sid=sid;
        this.key=key;
        this.name=name;
    }

    @Override
    public void requestError(String msg) {
        super.requestError(msg);
        mView.hideProgress();
        mView.basetoast(msg);
//        if(list_type!=LoadOrder.Second) {
//            mView.initConameData(null, DataLoadType.TYPE_REFRESH_FAIL);
//        }
    }

    @Override
    public void requestSuccess(List<DingdanDetail> data) {
        try {
            mView.initConameData(data, DataLoadType.TYPE_REFRESH_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
//       initDingdan();

    }

//    //紧接着回调
//    private void initDingdan() {
//        mSubscription = mDingdanConameListInteractor.getDingdanDingdanData(new RequestCallBack<Dingdan>() {
//            @Override
//            public void beforeRequest() {
//
//            }
//
//            @Override
//            public void RequestError(String msg) {
//                mView.initDingdanData(null, DataLoadType.TYPE_REFRESH_FAIL);
//            }
//
//            @Override
//            public void requestComplete() {
//
//            }
//
//            @Override
//            public void requestSuccess(Dingdan data) {
//                mView.initDingdanData(data, DataLoadType.TYPE_REFRESH_SUCCESS);
//            }
//        }, ccid);
//    }

    @Override
    public void onResume() {
        super.onResume();

//        pic_interactor.getUploadPic(new RequestCallBack<PicUpLoad[]>() {
//            @Override
//            public void beforeRequest() {
//
//            }
//
//            @Override
//            public void RequestError(String msg) {
////                if(mView!=null) {
////                    mView.ErrorRefresh(LoadOrder.Second);
////                    mView.showProgress();
////                }
//            }
//
//            @Override
//            public void requestComplete() {
//
//            }
//
//            @Override
//            public void requestSuccess(PicUpLoad[] data) {
//                if(mView!=null) {
//                    mView.hideProgress();
//                    mView.getUploadpic(data);
//                }
//            }
//        }, EncodeSid, sid, key, name);

    }

    @Override
    public void beforeRequest() {
        super.beforeRequest();
        mView.showProgress();
    }

    @Override
    public void requestComplete() {
        if(!mSubscription.isUnsubscribed()) {
            mView.hideProgress();
        }

        if (!mSubscription.isUnsubscribed()) {

            getmSubscription = pic_interactor.getUploadPic(new RequestCallBack<PicUpLoad[]>() {
                @Override
                public void beforeRequest() {

                }

                @Override
                public void requestError(String msg) {
                    mView.ErrorRefresh(LoadOrder.Second);
                    mView.showProgress();
                }

                @Override
                public void requestComplete() {

                }

                @Override
                public void requestSuccess(PicUpLoad[] data) {
                    mView.hideProgress();
                    mView.getUploadpic(data);
                }
            }, EncodeSid, sid, key, name);

            mCompositeSubscription.add(getmSubscription);
        }
    }
}
