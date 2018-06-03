package com.system.bhouse.bhouse.DingdanManage.Presenter;

import com.system.bhouse.base.BasePresenterImpl;
import com.system.bhouse.bhouse.DingdanManage.Model.UploadPicInteractor;
import com.system.bhouse.bhouse.DingdanManage.Model.UploadPicInteractorImpl;
import com.system.bhouse.bhouse.DingdanManage.UiInterface.UploadPicview;

/**
 * Created by Administrator on 2016-5-26.
 * ClassName: com.system.bhouse.bhouse.DingdanManage.Presenter
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class UploadPicPresenterImpl extends BasePresenterImpl<UploadPicview,String> implements UploadPicPresenter {

    private UploadPicInteractor interactor;
    private String ccid;
    private String sid;
    private String key;
    private String name;

    public UploadPicPresenterImpl(UploadPicview view,String ccid,String sid,String key,String name) {
        super(view);
        interactor=new UploadPicInteractorImpl();
        interactor.getUploadPic(this,ccid,sid,key,name);
        this.ccid=ccid;
        this.sid=sid;
        this.key=key;
        this.name=name;
    }

    @Override
    public void RefreshData() {

        interactor.getUploadPic(this,ccid,sid, key,name);
    }
}
