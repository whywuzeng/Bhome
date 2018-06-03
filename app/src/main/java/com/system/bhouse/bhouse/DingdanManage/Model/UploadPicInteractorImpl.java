package com.system.bhouse.bhouse.DingdanManage.Model;

import com.google.gson.Gson;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.RequestCallBack;
import com.system.bhouse.bean.PicUpLoad;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Administrator on 2016-5-26.
 * ClassName: com.system.bhouse.bhouse.DingdanManage.Model
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class UploadPicInteractorImpl implements UploadPicInteractor<PicUpLoad[]> {

    @Override
    public Subscription getUploadPic(final RequestCallBack<PicUpLoad[]> callBack, String ccid, String sid, String key, String name) {

//        return ApiServiceUtils.getInstence().getUploadPic(ccid,sid,key,name).subscribe(new Observer<PicUpLoad[]>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                callBack.RequestError(e.getLocalizedMessage());
//            }
//
//            @Override
//            public void onNext(PicUpLoad[] s) {
//                callBack.requestSuccess(s);
//            }
//        });

        //这里是图片获取 用webservice
        return ApiWebService.getUploadPic(new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                callBack.requestError(e.getLocalizedMessage());
            }

            @Override
            public void onNext(Object o) {
                Gson gson=new Gson();
                PicUpLoad[] s = gson.fromJson(o.toString(), PicUpLoad[].class);
                callBack.requestSuccess(s);
            }
        }, ccid, sid, key, name);
    }
}
