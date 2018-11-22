package com.system.bhouse.api;


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

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * test http://ip.taobao.com/service/getIpInfo.php?ip=21.22.11.33
 * <p>
 * Created by Administrator on 2016/3/14.
 * http://192.168.37.151:8070/ydlogin.aspx?username=admin&password=123456
 */
public interface ApiService {
//    @GET("service/getIpInfo.php")
//    Observable<GetIpInfoResponse> getIpInfo(@Query("ip") String ip);

    @GET("service/getIpInfo.php")
    Observable<GetIpInfoResponse> getIpInfo(@Query("ip") String ip);

    @GET("ydlogin.aspx")
    Observable<UserInfo[]> getIplogin(@Query("username") String username, @Query("password") String password);

    //2.获取省份接口：
    @GET("Getareatable.aspx")
    Observable<Shengfen[]> getShengfen(@Query("areaname") String areaname);

    //3.获取客户信息：
    @GET("Getcustomer.aspx")
    Observable<Kehu[]> getKehu(@Query("cname") String cname);

    //4.获取产品（别墅信息）：
    @GET("Getcustomerproject.aspx")
    Observable<Chanpin[]> getChanpin(@Query("pname") String pname);

    //5.获取运距信息：
    @GET("GetDistance.aspx")
    Observable<YunJu[]> getYunJu(@Query("Disnum") String Disnum);

    //6.获取合同状态信息
    @GET("Getcontractstu.aspx")
    Observable<Coname[]> getConame(@Query("coname") String coname);

    //7.获取施工状态信息
    @GET("Getconstructionstu.aspx")
    Observable<Cnname[]> getCnname(@Query("cnname") String cnname);

    //7.获取销售合同信息
    //http://192.168.37.151:8070/Getcuscontract.aspx?ccNumber=
    @GET("Getcuscontract.aspx")
    Observable<WholeConame[]> getWholeConame(@Query("ccNumber") String ccNumber);

    /**
     * @POST("/users/new")
        Call<User> createUser(@Body User user); post 提交
     */

    /**
     * http://192.168.37.151:8070/Getcuscontractinto.aspx?ccNumber=11&ccType=11&cid=1&cper=11&cphoe=22&did=1&otherfy=1&Installationarea=11&specificarea=21&aid=1&yjstartdate=2016-01-01&yjenddate=2016-02-01&yjdatenum=3&beizhu=22&addPer=admin
     */
    //8 9.插入合同信息
    @GET("Getcuscontractinto.aspx")
    Observable<SubmitRet[]> putWholeConame(@Query("ccNumber") String ccNumber, @Query("ccType") String ccType, @Query("cid") String cid, @Query("cper") String cper, @Query("cphoe") String cphoe, @Query("did") String did, @Query("Installationarea") String Installationarea, @Query("specificarea") String specificarea, @Query("aid") String aid, @Query("yjstartdate") String yjstartdate, @Query("yjenddate") String yjenddate, @Query("yjdatenum") String yjdatenum, @Query("beizhu") String beizhu, @Query("addPer") String addPer);

    /**
     * 8.根据合同ID获取合同信息
     * http://192.168.12.151:8070/Getcuscontractbyid.aspx?ccid=1
     */
    @GET("Getcuscontractbyid.aspx")
    Observable<WholeConame[]> getWholeConameById(@Query("ccid") int ccid);

    /**
     * 10.更新合同信息
     * http://192.168.12.151:8070/Getcuscontractupdate.aspx?ccNumber=11&ccType=11&cid=1&cper=11&cphoe=22&did=1&Installationarea=11&specificarea=21&aid=1&yjstartdate=2016-01-01&yjenddate=2016-02-01&yjdatenum=3&beizhu=22&addPer=admin&ccid=1
     */
    @GET("Getcuscontractupdate.aspx")
    Observable<SubmitRet[]> putWholeConameById(@Query("ccNumber") String ccNumber, @Query("ccType") String ccType, @Query("cid") String cid, @Query("cper") String cper, @Query("cphoe") String cphoe, @Query("did") String did, @Query("Installationarea") String Installationarea, @Query("specificarea") String specificarea, @Query("aid") String aid, @Query("yjstartdate") String yjstartdate, @Query("yjenddate") String yjenddate, @Query("yjdatenum") String yjdatenum, @Query("beizhu") String beizhu, @Query("addPer") String addPer, @Query("ccid") int ccid);

   /* 11.根据合同ID获取合同分录（订单）信息
    http://192.168.12.151:8070/GetcuscontractRbyid.aspx?ccid=1*/

    @GET("GetcuscontractRbyid.aspx")
    Observable<Dingdan[]> getDingdanById(@Query("ccid") int ccid);

/*    12.插入合同分录（订单）信息
    http://192.168.12.151:8070/GetcuscontractRinto.aspx?ccid=1&crNumber=11&proType=1&pid=11&pnum=22&did=1&Rowfy=11&transportfy=21&otherfy=1&htAmount=1&addPer=admin*/

    @GET("GetcuscontractRinto.aspx")
    Observable<SubmitRet[]> putDingdan(@Query("ccid") String ccid, @Query("crNumber") String crNumber, @Query("proType") String proType, @Query("pid") String pid, @Query("pnum") String pnum, @Query("did") String did, @Query("Rowfy") String Rowfy, @Query("transportfy") String transportfy, @Query("otherfy") String otherfy, @Query("htAmount") String htAmount, @Query("addPer") String addPer);

  /*  13.修改合同分录（订单）信息
    http://192.168.12.151:8070/GetcuscontractRupdate.aspx?ccid=1&crNumber=11&proType=1&pid=1&pnum=22&did=1&Rowfy=11&transportfy=21&otherfy=1&htAmount=1&addPer=admin&crid=1*/


    @GET("GetcuscontractRupdate.aspx")
    Observable<SubmitRet[]> putDingdanById(@Query("ccid") String ccid, @Query("crNumber") String crNumber, @Query("proType") String proType, @Query("pid") String pid, @Query("pnum") String pnum, @Query("did") String did, @Query("Rowfy") String Rowfy, @Query("transportfy") String transportfy, @Query("otherfy") String otherfy, @Query("htAmount") String htAmount, @Query("addPer") String addPer, @Query("crid") String crid);


      /*test测试  http://192.168.12.151:8070/Getcuscontractbyid.aspx?ccid=46*/

    @GET("Getcuscontractbyid.aspx")
    Observable<WholeConame[]> testgetWholeConameById(@Query("ccid") int ccid);

    /*  http://192.168.12.151:8070/GetcuscontractbyStatus.aspx?username=admin&mid=1&statusid=7&checktrue=true*/
    @GET("GetcuscontractbyStatus.aspx")
    Observable<DingdanZhuangTai[]> getDingdanfahuo(@Query("username") String username, @Query("mid") int mid, @Query("statusid") int statusid, @Query("checktrue") boolean checktrue);

    /* "http://localhost:8080/sdfasdfsdf/FileImageUploadServlet"*/
   /* http://192.168.12.151:8070/Getupfile.aspx*/
   /* http://localhost:8034/Default.aspx?filename=*/
    @Multipart
    @POST("Default.aspx")
    Observable<String> getUpload(@Part("file\";filename=\"image.png\"") RequestBody photos, @Query("filename") String description);

    /*http://192.168.12.151:8070/Getcuscontractbyorderid.aspx?ccid=87*/
    @GET("Getcuscontractbyorderid.aspx")
    Observable<DingdanDetail[]> getDingdanDetail(@Query("ccid") int ccid);

   /* http:// 192.168.12.151:8070/Getcuscontractupdatestatus.aspx?ccid=87&ccNumber=33&sid=2&username=a1&mid=1*/

    /*  16.更新订单状态
      http:// 192.168.12.151:8070/Getcuscontractupdatestatus.aspx?ccid=87&ccNumber=33&sid=2&username=a1&mid=1*/
    @GET("Getcuscontractupdatestatus.aspx")
    Observable<SubmitRet[]> getStatusforweb(@Query("ccid") String ccid, @Query("ccNumber") String ccNumber, @Query("sid") String sid, @Query("username") String username,
                                            @Query("mid") String mid,@Query("key") String key);

   /* 17.查看图片的接口
    http://192.168.11.29:8070/GetEnclosurebystatus.aspx?ccid=ODc=&sid=NQ==&key=cnV3ZXJpMzU4MDYzamZkaTJ5NDM4&username=admin*/
    @GET("GetEnclosurebystatus.aspx")
    Observable<PicUpLoad[]> getUploadPic(@Query("ccid") String ccid,@Query("sid") String sid,@Query("key") String key,@Query("username") String username);

/*    19.查询用户管理单元范围
    http://192.168.11.29:8070/Getusermidperm.aspx?username=admin&key=cnV3ZXJpMzU4MDYzamZkaTJ5NDM4*/

    @GET("Getusermidperm.aspx")
    Observable<UserManagement[]> getUserManagement(@Query("username") String username,@Query("key") String key);

//    http://flv2.bn.netease.com/videolib3/1605/31/LHezq5440/SD/LHezq5440-mobile.mp4

    @GET("videolib3/1605/31/LHezq5440/SD/{name}")
    Observable<ResponseBody> httpMp4(@Path("name") String name);

    /*http://gis.bhome.com.cn:8070/update/app-broad.apk*/
//    Ver:1.0.0.1&File:http://maisweb.bhome.com.cn/upfile/app-mais.apk
    @GET("upfile/{name}")
    Observable<ResponseBody> httpApk(@Path("name") String name);

    /*http://gis.bhome.com.cn:8070/update/update.txt*/
//    http://maisweb.bhome.com.cn/upfile/update.txt
    @GET("upfile/update.txt")
    Observable<ResponseBody> getUpdateMsg();

    @GET("")
    Observable<ResponseBody> getSingleApk();

//https://github.com/whywuzeng/Rxjava3/blob/master/javasync/json/maopao.json
//    @GET("json/maopao.json")
//    Observable<Object>
}
