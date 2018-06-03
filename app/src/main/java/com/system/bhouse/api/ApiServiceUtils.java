package com.system.bhouse.api;

import android.util.Log;

import com.socks.library.KLog;
import com.system.bhouse.base.App;
import com.system.bhouse.bean.Chanpin;
import com.system.bhouse.bean.Cnname;
import com.system.bhouse.bean.Coname;
import com.system.bhouse.bean.Dingdan;
import com.system.bhouse.bean.DingdanDetail;
import com.system.bhouse.bean.DingdanZhuangTai;
import com.system.bhouse.bean.Kehu;
import com.system.bhouse.bean.PicUpLoad;
import com.system.bhouse.bean.Shengfen;
import com.system.bhouse.bean.SubmitRet;
import com.system.bhouse.bean.UserInfo;
import com.system.bhouse.bean.UserManagement;
import com.system.bhouse.bean.WholeConame;
import com.system.bhouse.bean.YunJu;
import com.system.bhouse.utils.sharedpreferencesuser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016-3-15.
 */
public class ApiServiceUtils {

    public static String ENDPONIT = "http://maisweb.bhome.com.cn/";

//    public static final String ENDPONIT = "http://192.168.11.29:8070/";

//    private static final String ENDPONIT = "http://192.168.12.151:8070/";

    private static final String IMAGE="http://192.168.12.151:8034/";

    private static ApiServiceUtils apiServiceUtils;

    public static ApiServiceUtils getInstence() {
        try {
            String userdomain = sharedpreferencesuser.getUserdomain(App.getContextApp());
            if(null!=userdomain){
                ENDPONIT="http://"+userdomain+"/";
            }else {
                KLog.e("数据失效,域名是http://gis.bhome.com.cn:8070/");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  new ApiServiceUtils();
    }

    public Observable<UserInfo[]> getloginIp(String username, String password) {
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPONIT).addConverterFactory(GsonConverterFactory)

        Retrofit retrofit = null;
        Observable<UserInfo[]> admin=null;
        try {
            retrofit = new Retrofit.Builder().baseUrl(ENDPONIT).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

            ApiService apiService = retrofit.create(ApiService.class);
            Map<String, String> map = new HashMap<>();
            map.put("username", "admin");
            map.put("password", "123456");
           admin = apiService.getIplogin(username, password);
            return admin;
        } catch (final Exception e) {
            e.printStackTrace();
//            Toast.makeText(App.getContextApp(), "此域名出错", Toast.LENGTH_SHORT).show();
            return admin.create(new Observable.OnSubscribe<UserInfo[]>() {
                @Override
                public void call(Subscriber<? super UserInfo[]> subscriber) {
                    subscriber.onError(e);
                }
            });
        }
    }

    public Observable<Shengfen[]> getShengfenIp(String areaname) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPONIT).addConverterFactory(GsonConverterFactory.create()
        ).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<Shengfen[]> shengfen = apiService.getShengfen(areaname);
        return shengfen;
    }

    public Observable<Kehu[]> getKehuIp(String areaname) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPONIT).addConverterFactory(GsonConverterFactory.create()
        ).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<Kehu[]> shengfen = apiService.getKehu(areaname);
        return shengfen;
    }

    public Observable<Chanpin[]> getChanpin(String areaname) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPONIT).addConverterFactory(GsonConverterFactory.create()
        ).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<Chanpin[]> shengfen = apiService.getChanpin(areaname);
        return shengfen;
    }

    public Observable<YunJu[]> getYunju(String areaname) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPONIT).addConverterFactory(GsonConverterFactory.create()
        ).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<YunJu[]> shengfen = apiService.getYunJu(areaname);
        return shengfen;
    }

    public Observable<Coname[]> getConame(String areaname) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPONIT).addConverterFactory(GsonConverterFactory.create()
        ).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<Coname[]> shengfen = apiService.getConame(areaname);
        return shengfen;
    }

    public Observable<Cnname[]> getCnname(String areaname) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPONIT).addConverterFactory(GsonConverterFactory.create()
        ).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<Cnname[]> shengfen = apiService.getCnname(areaname);
        return shengfen;
    }

    public Observable<WholeConame[]> getWholeConame(String areaname) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPONIT).addConverterFactory(GsonConverterFactory.create()
        ).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<WholeConame[]> shengfen = apiService.getWholeConame(areaname);
        return shengfen;
    }

    public Observable<SubmitRet[]> putWholeConame(String ccNumber, String ccType, String cid, String cper, String cphoe, String did, String Installationarea, String specificarea, String aid, String yjstartdate, String yjenddate, String yjdatenum, String beizhu, String addPer) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPONIT).addConverterFactory(GsonConverterFactory.create()
        ).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<SubmitRet[]> shengfen = apiService.putWholeConame(ccNumber, ccType, cid, cper, cphoe, did, Installationarea, specificarea, aid, yjstartdate, yjenddate, yjdatenum, beizhu, addPer);

        Log.i("shengfen-->>>>>>>>-----", retrofit.baseUrl().toString());
        return shengfen;
    }

    public Observable<WholeConame[]> getWholeConameById(int ccid) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPONIT).addConverterFactory(GsonConverterFactory.create()
        ).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<WholeConame[]> shengfen = apiService.getWholeConameById(ccid);
        return shengfen;
    }

    public Observable<SubmitRet[]> putWholeConameById(String ccNumber, String ccType, String cid, String cper, String cphoe, String did, String Installationarea, String specificarea, String aid, String yjstartdate, String yjenddate, String yjdatenum, String beizhu, String addPer, int ccid) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPONIT).addConverterFactory(GsonConverterFactory.create()
        ).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<SubmitRet[]> shengfen = apiService.putWholeConameById(ccNumber, ccType, cid, cper, cphoe, did, Installationarea, specificarea, aid, yjstartdate, yjenddate, yjdatenum, beizhu, addPer, ccid);

        Log.i("shengfen-->>>>>>>>-----", retrofit.baseUrl().toString());
        return shengfen;
    }

    public Observable<Dingdan[]> getDingdanById(int ccid) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPONIT).addConverterFactory(GsonConverterFactory.create()
        ).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<Dingdan[]> shengfen = apiService.getDingdanById(ccid);
        return shengfen;
    }

    public Observable<SubmitRet[]> putDingdan(String ccid, String crNumber, String proType, String pid, String pnum, String did, String Rowfy, String transportfy, String otherfy, String htAmount, String addPer) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPONIT).addConverterFactory(GsonConverterFactory.create()
        ).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<SubmitRet[]> shengfen = apiService.putDingdan(ccid, crNumber, proType, pid, pnum, did, Rowfy, transportfy, otherfy, htAmount, addPer);

        Log.i("shengfen-->>>>>>>>-----", retrofit.baseUrl().toString());
        return shengfen;
    }

    public Observable<SubmitRet[]> putDingdaById(String ccid, String crNumber, String proType, String pid, String pnum, String did, String Rowfy, String transportfy, String otherfy, String htAmount, String addPer, String crid) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPONIT).addConverterFactory(GsonConverterFactory.create()
        ).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<SubmitRet[]> shengfen = apiService.putDingdanById(ccid, crNumber, proType, pid, pnum, did, Rowfy, transportfy, otherfy, htAmount, addPer, crid);

        Log.i("shengfen-->>>>>>>>-----", retrofit.baseUrl().toString());
        return shengfen;
    }

    public  Observable<DingdanZhuangTai[]> TestgetDingdanById(String username,int mid,int statusid,boolean checktrue) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPONIT).addConverterFactory(GsonConverterFactory.create()
        ).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<DingdanZhuangTai[]> shengfen = apiService.getDingdanfahuo(username, mid, statusid, checktrue).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
        return shengfen;
    }

    public Observable<String> getUpload(File file, String description) {
        RequestBody photorequestBody = RequestBody.create(MediaType.parse("image/png"), file);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(IMAGE).addConverterFactory(GsonConverterFactory.create()
        ).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<String> shengfen = apiService.getUpload(photorequestBody, description).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
        return shengfen;
    }


    public Observable<DingdanDetail[]> getDingdandetail(int ccid) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPONIT).addConverterFactory(GsonConverterFactory.create()
        ).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<DingdanDetail[]> shengfen = apiService.getDingdanDetail(ccid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
        return shengfen;
    }


    public Observable<SubmitRet[]> getDingdandetail(String ccid,String ccNumber,String sid,String username,String mid,String key) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPONIT).addConverterFactory(GsonConverterFactory.create()
        ).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<SubmitRet[]> shengfen = apiService.getStatusforweb(ccid, ccNumber, sid, username, mid, key).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
        return shengfen;
    }

    public Observable<PicUpLoad[]> getUploadPic(String ccid,String sid,String key,String username) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPONIT).addConverterFactory(GsonConverterFactory.create()
        ).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<PicUpLoad[]> shengfen = apiService.getUploadPic(ccid, sid, key, username).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
        return shengfen;
    }

    public Observable<UserManagement[]> getUserManagement(String username,String key) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPONIT).addConverterFactory(GsonConverterFactory.create()
        ).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<UserManagement[]> shengfen = apiService.getUserManagement(username, key).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
        return shengfen;
    }

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public Observable<ResponseBody> httpMp4(String name,OnProgressResponseListener listener){

//        okHttpClient.addProgressClient(httpClient,listener).build()

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://flv2.bn.netease.com/").client(okHttpClient.addProgressClient(httpClient,listener).build()).
        addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<ResponseBody> responseBodyObservable = apiService.httpMp4(name);
        return responseBodyObservable;
    }

    public Observable<ResponseBody> httpApk(String url,OnProgressResponseListener listener){

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPONIT).client(okHttpClient.addProgressClient(httpClient, listener).build()).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<ResponseBody> responseBodyObservable = apiService.httpApk(url);
        return responseBodyObservable;
    }


    public Observable<ResponseBody> getUpdateMsg(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPONIT).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<ResponseBody> responseBodyObservable = apiService.getUpdateMsg().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
        return responseBodyObservable;
    }

    public Observable<ResponseBody> getSingleApk(String url,OnProgressResponseListener listener){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).client(okHttpClient.addProgressClient(httpClient, listener).build()).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<ResponseBody> responseBodyObservable = apiService.getSingleApk().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
        return responseBodyObservable;
    }

//    private final OkHttpClient client=new OkHttpClient();
//
//    public void run121() throws Exception{
//        Request request=new Request.Builder().url("").build();
//        Response response=client.newCall(request).execute();
//        if(!response.isSuccessful()){
//            throw new IOException("Unex")
//        }
//    }

}
