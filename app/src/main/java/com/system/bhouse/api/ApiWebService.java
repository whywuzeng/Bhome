package com.system.bhouse.api;

import android.content.Context;

import com.socks.library.KLog;
import com.system.bhouse.adpter.RequestError;
import com.system.bhouse.base.App;
import com.system.bhouse.base.BHBaseSubscriber;
import com.system.bhouse.bean.ComTaskBeanChild;
import com.system.bhouse.utils.ProgressUtils;
import com.system.bhouse.utils.TenUtils.L;
import com.system.bhouse.utils.TenUtils.T;
import com.system.bhouse.utils.sharedpreferencesuser;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import org.json.JSONArray;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016-8-22.
 * ClassName: com.system.bhouse.api
 * Author:Administrator
 * Fuction:webservice 构建webservice 网络请求
 * UpdateUser:
 * UpdateDate:
 */
public class ApiWebService {

    private static String BASE_URL="http://maisweb.bhome.com.cn:7785/";

    public static  String WSDL_URL=BASE_URL+"Service1.asmx?WSDL";

    public static final String NAMESPACE="http://tempuri.org/";

    public static final int SUCCESS=0;
    public static final int ERROR=1;
    public static final int Complete=2;
    private static final java.lang.String tag="ApiWebService";

    public static void setUserMaindomain()
    {
        try {
            String userdomain = sharedpreferencesuser.getUserdomain(App.getContextApp());
            if(null!=userdomain){
                BASE_URL="http://"+userdomain+"/";

                WSDL_URL=BASE_URL+"Service1.asmx?WSDL";
            }else {
                KLog.e("数据失效,域名是http://gis.bhome.com.cn:8070/");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public static Subscription putUploadPic(Context mContext,SuccessCall call,String bytes,String FileName,String bid,String sid,String username,String Keystring)
    {
        String methodName="upfilebyte";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("b1", bytes);
        request.addProperty("FileName",FileName);
        request.addProperty("bid",bid);
        request.addProperty("sid",sid);
        request.addProperty("username",username);
        request.addProperty("Keystring",Keystring);
        return getNetworkService(mContext,call,request);
    }

      /* 17.查看图片的接口
    http://192.168.11.29:8070/GetEnclosurebystatus.aspx?ccid=ODc=&sid=NQ==&key=cnV3ZXJpMzU4MDYzamZkaTJ5NDM4&username=admin*/
    public static Subscription getUploadPic(Subscriber mSubscriber,String ccid, String sid, String key, String name)
    {
        String methodName="GetEnclosurebystatus"; //(用户密码验证)
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        //integer类型
        request.addProperty("ccid",ccid);
        request.addProperty("sid",sid);
        request.addProperty("username",name);
        request.addProperty("Keystring",key);
        return getNetworkService(mSubscriber,request,null);
    }

/*    15.根据订单ID获取明细信息
    http://192.168.12.151:8070/Getcuscontractbyorderid.aspx?ccid=87*/
    public static Subscription getDingdandetails(Subscriber mSubscriber,int ccid)
    {
        String methodName="Getcuscontractbyorderid"; //(用户密码验证)
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        //integer类型
        request.addProperty("ccid",ccid);
        return getNetworkService(mSubscriber,request,null);
    }


/*    14.根据订单业务状态获取订单信息（用户，管理单元ID，当前单据状态ID，是否选中） (大型接口)
    http://192.168.12.151:8070/GetcuscontractbyStatus.aspx?username=admin&mid=1&statusid=7&checktrue=true*/
    public static Subscription getDingdanStatusmore(Subscriber mSubscriber,String username,int mid,int statusid,boolean checktrue)
    {
        String methodName="GetcuscontractbyStatus"; //(用户密码验证)
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("username",username);
        //integer类型
        request.addProperty("mid",mid);
        request.addProperty("statusid",statusid);
        request.addProperty("checktrue",checktrue);
        return getNetworkService(mSubscriber,request,null);
    }


    //管理单元
//    19.查询用户管理单元范围
//    http://192.168.11.29:8070/Getusermidperm.aspx?username=admin&key=cnV3ZXJpMzU4MDYzamZkaTJ5NDM4
    public static Subscription getUserManagement(final Context mContext,final SuccessCall call,String admin,String encode_key)
    {
        String methodName="Getusermidperm_Json"; //(用户密码验证)
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("username",admin);
        request.addProperty("Keystring",encode_key);
        return getNetworkService(mContext,call,request);
    }

    //登录的接口
    public static Observable getLoginMsg(String admin,String password){
        setUserMaindomain();
        String methodName="Getlogin_Json"; //(用户密码验证)
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("name",admin);
        request.addProperty("pass",password);
       return getMutiNetworkService(request);
    }

    //更新订单状态

    /**
     *
     * @return
     */
    public static Subscription getDingdanStatus(final Context mContext, final SuccessCall call,String ccid,String ccNumber,String sid,String username,String mid,String Keystring){
        String methodName="Getcuscontractupdatestatus";
        SoapObject request=new SoapObject(NAMESPACE,methodName);
        request.addProperty("ccid",ccid);
        request.addProperty("ccNumber",ccNumber);
        request.addProperty("sid",sid);
        request.addProperty("username",username);
        request.addProperty("mid",mid);
        request.addProperty("Keystring",Keystring);
        return getNetworkService(mContext,call,request);
    }

//    1．流程审批获取审批记录

    /**
     * 传入参数：status 状态（未审批，已审批）
     rownum 查询记录数
     username 当前登录用户名
     FormNumber 模糊查询条件（单据编号）
     * @return
     */

//    public static Subscription getGetWorkflowlistMessagedt(Subscriber mSubscriber,String status, int rownum, String username, String FormNumber)
//    {
//            String methodName="GetWorkflowlistMessagedt";
//        SoapObject request = new SoapObject(NAMESPACE, methodName);
//        request.addProperty("status",status);
//        request.addProperty("rownum",rownum);
//        request.addProperty("username",username);
//        request.addProperty("FormNumber",FormNumber);
//        return getNetworkService(mSubscriber,request);
//    }

    /**
     * 查询当前登录公司用户当前所在单元的有权限的所有子级ID
     * @param  //返回接口
     * @param  //用户名
     * @param    //管理单元编号
     * @return
     */
    public static Subscription getGetmidfulllistdsbyusermid_Json(final Context mContext, final SuccessCall call,String username, int mid)
    {
        String methodName="Getmidfulllistdsbyusermid_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("username",username);
        request.addProperty("mid",mid);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 5.根据管理单元ID查询单个管理单元信息
     * [{"mid":1,"Fatherid":0,"Levelid":1,"EASnumber":"0","EAScgnumber":"","EASkcnumber":"","IsSub":false,"ManNumber":"01","property":0,"ManCompany":"远大住工","gsmid":1}]
     * @return
     */
    public static Subscription getGetmidlistds_Json(final Context mContext, final SuccessCall call,int mid)
    {
        String methodName="Getmidlistds_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("mid",mid);
        return getNetworkService(mContext,call,request);
    }

    /**
     * //6.查询所有启用的公司列表
     * @param mContext
     * @param call
     * @return
     */
    public static Subscription getGetmidfulllistds_Json(final Context mContext, final SuccessCall call)
    {
        String methodName="Getmidfulllistds_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 4.查询用户有权限的所有管理单元范围
     * [{"mid":1,"Mancompany":"远大住工"},{"mid":7,"Mancompany":"麓谷工厂"},{"mid":8,"Mancompany":"PC产线"},{"mid":11,"Mancompany":"测试帐套"},{"mid":12,"Mancompany":"PC一线"},{"mid":13,"Mancompany":"PC二线"},{"mid":14,"Mancompany":"PC三线"},{"mid":15,"Mancompany":"PC四线"},{"mid":16,"Mancompany":"PC五线"},{"mid":17,"Mancompany":"钢筋加工中心"},{"mid":18,"Mancompany":"钢筋加工中心-2期"},{"mid":19,"Mancompany":"物流部"}]
     *
     */
    public static Observable Getusermidperm_Json(String username)
    {
        String methodName="Getusermidperm_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("username",username);
        return getMutiNetworkService(request);
    }


    /**
     * 6.查询所有启用的公司列表 去除dialog
     *
     * @return
     */
    public static Observable Getmidfulllistds_Json(String username)
    {
        setUserMaindomain();
        String methodName="Getmidfulllistds_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("username",username);
        return getMutiNetworkService(request);
    }


    public static Subscription Getupdatepassdt_Json(final Context mContext, final SuccessCall call,String username,String passold,String passnew,String Passnew1)
    {
        String methodName="Getmidfulllistds_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("username",username);
        request.addProperty("passold",passold);
        request.addProperty("passnew",passnew);
        request.addProperty("Passnew1",Passnew1);
        return getNetworkService(mContext,call,request);
    }


//   16e4cf8dd69c47d294628dba8d05b519
    //10.根据ID获取生产订单表头信息

    public static Subscription Get_Production_order_Bill_Json(final Context mContext, final SuccessCall call,String poid,int gsmid,int property,boolean IsSubtitle)
    {
        String methodName="Get_Production_order_Bill_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        return getNetworkService(mContext,call,request);
    }

    //11.根据ID获取生产订单详细信息

    public static Subscription Get_Production_order_list_Json(final Context mContext, final SuccessCall call,String poid,int gsmid,int property,boolean IsSubtitle)
    {
        String methodName="Get_Production_order_list_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        return getNetworkService(mContext,call,request);
    }

    //12.根据ID对生产订单开始备料

    public static Subscription Get_Production_orderStock_start(final Context mContext, final SuccessCall call,String poid, int gsmid, int property, boolean IsSubtitle)
    {
        String methodName="Get_Production_orderStock_start";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        return getNetworkService(mContext,call,request);
    }

    //16e4cf8dd69c47d294628dba8d05b519

    //13.根据ID对生产订单标记备料完成

    public static Subscription Get_Production_orderStock_end(final Context mContext, final SuccessCall call,String poid, int gsmid, int property, boolean IsSubtitle)
    {
        String methodName="Get_Production_orderStock_end";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        return getNetworkService(mContext,call,request);
    }


    //14.根据ID对生产订单取消备料
    public static Subscription Get_Production_orderStock_del(final Context mContext, final SuccessCall call,String poid, int gsmid, int property, boolean IsSubtitle)
    {
        String methodName="Get_Production_orderStock_del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        return getNetworkService(mContext,call,request);
    }

    //设备认证
    public static Subscription Getlogin_reg_Json(final Context mContext, final SuccessCall call,String username, String CpuID, String MacAddress, String IpAddress, String DiskID, String TotalPhysicalMemory)
    {
        setUserMaindomain();
        String methodName="Getlogin_reg_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("username",username);
        request.addProperty("CpuID",CpuID);
        request.addProperty("MacAddress",MacAddress);
        request.addProperty("IpAddress",IpAddress);
        request.addProperty("DiskID",DiskID);
        request.addProperty("TotalPhysicalMemory",TotalPhysicalMemory);
        return getNetworkService(mContext,call,request);
    }

    //设备认证校验
    public static Observable Getlogin_regok_Json(String CpuID, String MacAddress, String IpAddress, String DiskID, String TotalPhysicalMemory)
    {
        setUserMaindomain();
        String methodName="Getlogin_regok_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("username",App.USER_INFO);
        request.addProperty("CpuID",CpuID);
        request.addProperty("MacAddress",MacAddress);
        request.addProperty("IpAddress",IpAddress);
        request.addProperty("DiskID",DiskID);
        request.addProperty("TotalPhysicalMemory",TotalPhysicalMemory);
        return getMutiNetworkService(request);
    }

    /**
     *param rowcount   Rowcount    记录数
     Statusstr    状态
     h_Number     吊装编号
     gsmid        归属公司ID
     property     是否公司(0 公司，1 部门)
     IsSubtitle   是否实体(true 是，false 否)
     * return
     */
    public static Subscription Get_Hois_Req_Json(final Context mContext, final SuccessCall call,int rowcount, String statusstr, String h_Number, int gsmid, int property, boolean IsSubtitle,String username){
        String methodName="Get_Hois_Req_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("statusstr",statusstr);
        request.addProperty("h_Number",h_Number);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("username",username);
        request.addProperty("Is_Pro_User",App.Is_Pro_User);
        request.addProperty("Pro_Userstring",App.Pro_Userstring);
        return getNetworkService(mContext,call,request);
    }

    /**
     *
     * param hid        需求ID
     * param gsmid      归属公司ID
     * param property   是否公司(0 公司，1 部门)
     * param IsSubtitle 是否实体(true 是，false 否)
     * return
     */
    public static Subscription Get_Hois_ReqView_Json(final Context mContext, final SuccessCall call,String hid, int gsmid, int property, boolean IsSubtitle)
    {
        String methodName="Get_Hois_ReqView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("hid",hid);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    public static Observable Get_KeyTimestr(String Keystring)
    {
        String methodName="Get_KeyTimestr";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("Keystring",Keystring);
        return getMutiNetworkService(request);
    }

    /**
     * param gsmid
     * param property
     * param IsSubtitle  接口名称：获取吊装需求编号
     * return
     */
    public static Subscription Get_Hois_Req_h_Number(final Context mContext, final SuccessCall call,int gsmid, int property, boolean IsSubtitle)
    {
        String methodName="Get_Hois_Req_h_Number";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

//吊装需求-项目查询(栋查询，层查询)
    public static Subscription B_Pro_BOM_Select(final Context mContext, final SuccessCall call,int rowcount,String window_Type, String PNumber,String fpbid,int gsmid, int property, boolean IsSubtitle)
    {
        String methodName="B_Pro_BOM_Select";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("window_Type",window_Type);
        request.addProperty("PNumber",PNumber);
        request.addProperty("fpbid",fpbid);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("Is_Pro_User",App.Is_Pro_User);
        request.addProperty("Pro_Userstring",App.Pro_Userstring);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    //吊装需求-获取层BOM)
    public static Subscription Get_Hois_Req_cengBom_Json(final Context mContext, final SuccessCall call,String bomid, int gsmid, int property, boolean IsSubtitle)
    {
        String methodName="Get_Hois_Req_cengBom_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("bomid",bomid);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    //新增吊装需求信息
    public static Subscription Get_Hois_Req_Add(final Context mContext, final SuccessCall call,String[][] billtable,int gsmid,int property,boolean IsSubtitle, String Keystring, String KeyTimestring, String username)
    {
        String methodName="Get_Hois_Req_Add";
        SoapObject request = new SoapObject(NAMESPACE, methodName);

        List<ComTaskBeanChild> list = new ArrayList<>();//先定义一个集合对象

        for(int i=0; i<billtable.length; i++){//遍历二维数组，对集合进行填充
            ComTaskBeanChild comTaskBeanChild = new ComTaskBeanChild();//初始化一个ArrayList集合
            comTaskBeanChild.hNumbe =billtable[i][0];//数组的列放到集合中
            comTaskBeanChild.Pro_id =billtable[i][1];//数组的列放到集合中
            comTaskBeanChild.Pro_dong_id =billtable[i][2];//数组的列放到集合中
            comTaskBeanChild.Pro_ceng_id =billtable[i][3];//数组的列放到集合中
            comTaskBeanChild.requireData =billtable[i][4];//数组的列放到集合中
            comTaskBeanChild.description =billtable[i][5];//数组的列放到集合中
            comTaskBeanChild.enterPeople =billtable[i][6];//数组的列放到集合中
            comTaskBeanChild.goodsID =billtable[i][7];//数组的列放到集合中
            comTaskBeanChild.goodsCoding =billtable[i][8];//数组的列放到集合中
            comTaskBeanChild.goodsName =billtable[i][9];//数组的列放到集合中
            comTaskBeanChild.Specification =billtable[i][10];
            comTaskBeanChild.measureID =billtable[i][11];
            comTaskBeanChild.measure =billtable[i][12];
            comTaskBeanChild.amount =billtable[i][13];
            list.add(comTaskBeanChild);//二维数组放到集合中
        }
        ComTaskBeanChild[] obj = new ComTaskBeanChild[list.size()];
        list.toArray(obj);

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtable);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.addProperty("billtablestr",s1);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("Keystring",Keystring);
        request.addProperty("KeysTimetring",KeyTimestring);
        request.addProperty("username",username);
        return getNetworkService(mContext,call,request);
    }

    //审核吊装需求
    public static Subscription Get_Hois_Req_sh(final Context mContext, final SuccessCall call,String hid,String addPer, int gsmid, int property, boolean IsSubtitle, String Keystring,String KeyTimestring,String username)
    {
        String methodName="Get_Hois_Req_sh";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("hid",hid);
        request.addProperty("addPer",App.menname);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("Keystring",Keystring);
        request.addProperty("KeysTimetring",KeyTimestring);
        request.addProperty("username",username);
        return getNetworkService(mContext,call,request);
    }

    //反审核吊装需求
    public static Subscription Get_Hois_Req_shf(final Context mContext, final SuccessCall call,String hid,String addPer, int gsmid, int property, boolean IsSubtitle, String Keystring,String KeyTimestring,String username)
    {
        String methodName="Get_Hois_Req_shf";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("hid",hid);
        request.addProperty("addPer",App.menname);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("Keystring",Keystring);
        request.addProperty("KeysTimetring",KeyTimestring);
        request.addProperty("username",username);
        return getNetworkService(mContext,call,request);
    }

    //删除吊装需求
    public static Subscription Get_Hois_Req_Del(final Context mContext, final SuccessCall call,String hid,String addPer, int gsmid, int property, boolean IsSubtitle, String Keystring,String KeyTimestring,String username)
    {
        String methodName="Get_Hois_Req_Del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("hid",hid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("Keystring",Keystring);
        request.addProperty("KeysTimetring",KeyTimestring);
        request.addProperty("username",username);
        return getNetworkService(mContext,call,request);
    }

        //生成二维码
    public static Subscription Get_Hois_Req_QR_Code_Create(final Context mContext, final SuccessCall call,String hid,String addPer, int gsmid, int property, boolean IsSubtitle, String Keystring,String KeyTimestring,String username)
    {
        String methodName="Get_Hois_Req_QR_Code_Create";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("hid",hid);
        request.addProperty("addPer",App.menname);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("Keystring",Keystring);
        request.addProperty("KeysTimetring",KeyTimestring);
        request.addProperty("username",username);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 工地收货确认列表
     * param mContext
     * param call
     * param request
     * return
     * 接口名称：工地收货确认列表
     接口地址：http://192.168.11.96:7785/Service1.asmx
     */
    public static Subscription Get_Sale_Order_Car_qr_Json(final Context mContext, final SuccessCall call,int rowcount, String statusstr, String h_Number, int gsmid, int property, boolean IsSubtitle, String username)
    {
        String methodName="Get_Sale_Order_Car_qr_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("statusstr",statusstr);
        request.addProperty("h_Number",h_Number);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("username",username);
        request.addProperty("Is_Pro_User",App.Is_Pro_User);
        request.addProperty("Pro_Userstring",App.Pro_Userstring);
        return getNetworkService(mContext,call,request);
    }

    /**
     *接口名称：工地收货确认详细信息
     * 接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     * aram mContext
     * param call
     * param request
     * return
     */

    public static Subscription Get_Sale_Order_Car_qrView_Json(final Context mContext, final SuccessCall call,String scqid, int gsmid, int property, boolean IsSubtitle)
    {
        String methodName="Get_Sale_Order_Car_qrView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("scqid",scqid);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取收货确认编号
     接口地址：http://192.168.11.96:7785/Service1.asmx
     * param mContext
     * param call
     * param request
     * return
     */
    public static Subscription Get_Sale_Order_Car_qr_so_Number(final Context mContext, final SuccessCall call,int gsmid, int property, boolean IsSubtitle)
    {
        String methodName="Get_Sale_Order_Car_qr_so_Number";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：根据车次获取工地待收货信息  //yao扫描二维码
     * param mContext
     * param call
     * param CHEid
     * param gsmid
     * param property
     * param IsSubtitle
     * return
     */
    public static Subscription Get_Sale_Order_Car_qrCHE_Json(final Context mContext, final SuccessCall call,String CHEid, int gsmid, int property, boolean IsSubtitle)
    {
        String methodName="Get_Sale_Order_Car_qrCHE_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("CHEid",CHEid);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("Is_Pro_User",App.Is_Pro_User);
        request.addProperty("Pro_Userstring",App.Pro_Userstring);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：根据工地待收货信息查找二维码匹配需求数量
     * aram mContext
     * param call
     * param prid_QR_Code
     * param gsmid
     * param property
     * param IsSubtitle
     * @return
     */
    public static Subscription Get_Sale_Order_Car_qr_hrq_Type_Json(final Context mContext, final SuccessCall call,String prid_QR_Code, int gsmid, int property, boolean IsSubtitle)
    {
        String methodName="Get_Sale_Order_Car_qr_hrq_Type_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：新增工地收货确认信息
     * param billtablestr
     * param gsmid
     * param property
     * param IsSubtitle
     * param Keystring
     * param KeysTimetring
     * param username
     */

    public static Subscription Get_Sale_Order_Car_qr_Add(final Context mContext, final SuccessCall call,String[][] billtablestr, int gsmid, int property, boolean IsSubtitle, String Keystring, String KeysTimetring, String username)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String methodName="Get_Sale_Order_Car_qr_Add";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("Keystring",Keystring);
        request.addProperty("KeysTimetring",KeysTimetring);
        request.addProperty("username",username);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：修改工地收货确认信息
     * @param mContext
     * @param call
     * @param billtablestr
     * @param scqid
     * @param gsmid
     * @param property
     * @param IsSubtitle
     * @param Keystring
     * @param KeysTimetring
     * @param username
     * @return
     */
    public static Subscription Get_Sale_Order_Car_qr_Eedit(final Context mContext, final SuccessCall call,String[][] billtablestr,String scqid, int gsmid, int property, boolean IsSubtitle, String Keystring, String KeysTimetring, String username)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String methodName="Get_Sale_Order_Car_qr_Eedit";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("scqid",scqid);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("Keystring",Keystring);
        request.addProperty("KeysTimetring",KeysTimetring);
        request.addProperty("username",username);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：删除收货确认编号
     * @param mContext
     * @param call
     * @param
     * @return
     */
    public static Subscription Get_Sale_Order_Car_qr_Del(final Context mContext, final SuccessCall call,String scqid,String addPer, int gsmid, int property, boolean IsSubtitle, String Keystring,String KeyTimestring,String username)
    {
        String methodName="Get_Sale_Order_Car_qr_Del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("scqid",scqid);
        request.addProperty("addPer",App.menname);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("Keystring",Keystring);
        request.addProperty("KeysTimetring",KeyTimestring);
        request.addProperty("username",username);
        return getNetworkService(mContext,call,request);
    }


    /**
     * 接口名称：审核收货确认
     */
    public static Subscription Get_Sale_Order_Car_qr_sh(final Context mContext, final SuccessCall call,String  scqid,String addPer, int gsmid, int property, boolean IsSubtitle, String Keystring,String KeyTimestring,String username)
    {
        String methodName="Get_Sale_Order_Car_qr_sh";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("scqid",scqid);
        request.addProperty("addPer",App.menname);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("Keystring",Keystring);
        request.addProperty("KeysTimetring",KeyTimestring);
        request.addProperty("username",username);
        return getNetworkService(mContext,call,request);
    }

    /**
     * //接口名称：反审核收货确认
     * param mContext
     * param call
     * param hid
     * @param addPer
     * @param gsmid
     * @param property
     * @param IsSubtitle
     * @param Keystring
     * @param KeyTimestring
     * @param username
     * @return
     */
    public static Subscription Get_Sale_Order_Car_qr_shf(final Context mContext, final SuccessCall call,String scqid,String addPer, int gsmid, int property, boolean IsSubtitle, String Keystring,String KeyTimestring,String username)
    {
        String methodName="Get_Sale_Order_Car_qr_shf";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("scqid",scqid);
        request.addProperty("addPer",App.menname);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("Keystring",Keystring);
        request.addProperty("KeysTimetring",KeyTimestring);
        request.addProperty("username",username);
        return getNetworkService(mContext,call,request);
    }

    /**
     *接口名称：工地退货申请列表
     * @param rowcount
     * @param statusstr
     * @param h_Number
     * @param gsmid
     * @param property
     * @param IsSubtitle
     * @param username
     */
    public static Subscription Get_Sale_Order_Car_qr_rn_Json(final Context mContext, final SuccessCall call,int rowcount, String statusstr, String h_Number, int gsmid, int property, boolean IsSubtitle, String username)
    {
        String methodName="Get_Sale_Order_Car_qr_rn_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("statusstr",statusstr);
        request.addProperty("h_Number",h_Number);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("username",username);
        request.addProperty("Is_Pro_User",App.Is_Pro_User);
        request.addProperty("Pro_Userstring",App.Pro_Userstring);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：工地退货申请详细信息
     */
    public static Subscription Get_Sale_Order_Car_qr_rnView_Json(final Context mContext, final SuccessCall call,String scrid, int gsmid, int property, boolean IsSubtitle)
    {
        String methodName="Get_Sale_Order_Car_qr_rnView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("scrid",scrid);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取退货申请编号
     * Get_Sale_Order_Car_qr_rn_so_Number(int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Sale_Order_Car_qr_rn_so_Number(final Context mContext, final SuccessCall call,int gsmid, int property, boolean IsSubtitle)
    {
        String methodName="Get_Sale_Order_Car_qr_rn_so_Number";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        return getNetworkServiceNoProgress(mContext,call,request);
    }
    /**
     *
     * 接口名称：根据构件二维码获取可退货信息
     * Get_Sale_Order_Car_qr_rn_prid_QR_Code_Json(string prid_QR_Code, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Sale_Order_Car_qr_rn_prid_QR_Code_Json(final Context mContext, final SuccessCall call,String prid_QR_Code, int gsmid, int property, boolean IsSubtitle)
    {
        String methodName="Get_Sale_Order_Car_qr_rn_prid_QR_Code_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("Is_Pro_User",App.Is_Pro_User);
        request.addProperty("Pro_Userstring",App.Pro_Userstring);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：新增工地退货申请信息
     *Get_Sale_Order_Car_qr_rn_Add(string billtablestr, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Car_qr_rn_Add(final Context mContext, final SuccessCall call,String[][] billtablestr, int gsmid, int property, boolean IsSubtitle, String Keystring, String KeysTimetring, String username)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String methodName="Get_Sale_Order_Car_qr_rn_Add";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("Keystring",Keystring);
        request.addProperty("KeysTimetring",KeysTimetring);
        request.addProperty("username",username);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：修改工地退货申请信息
     * Get_Sale_Order_Car_qr_rn_Eedit(string billtablestr, string scrid, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Car_qr_rn_Eedit(final Context mContext, final SuccessCall call,String[][] billtablestr,String scrid, int gsmid, int property, boolean IsSubtitle, String Keystring, String KeysTimetring, String username)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String methodName="Get_Sale_Order_Car_qr_rn_Eedit";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("scrid",scrid);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("Keystring",Keystring);
        request.addProperty("KeysTimetring",KeysTimetring);
        request.addProperty("username",username);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：删除退货申请编号
     * Get_Sale_Order_Car_qr_rn_Del(string scrid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Car_qr_rn_Del(final Context mContext, final SuccessCall call,String scrid,String addPer, int gsmid, int property, boolean IsSubtitle, String Keystring,String KeyTimestring,String username)
    {
        String methodName="Get_Sale_Order_Car_qr_rn_Del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("scrid",scrid);
        request.addProperty("addPer",App.menname);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("Keystring",Keystring);
        request.addProperty("KeysTimetring",KeyTimestring);
        request.addProperty("username",username);
        return getNetworkService(mContext,call,request);
    }

    /**
     *接口名称：审核退货申请
     * Get_Sale_Order_Car_qr_rn_sh(string scrid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Car_qr_rn_sh(final Context mContext, final SuccessCall call,String  scrid,String addPer, int gsmid, int property, boolean IsSubtitle, String Keystring,String KeyTimestring,String username)
    {
        String methodName="Get_Sale_Order_Car_qr_rn_sh";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("scrid",scrid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("Keystring",Keystring);
        request.addProperty("KeysTimetring",KeyTimestring);
        request.addProperty("username",username);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：反审核退货申请
     * Get_Sale_Order_Car_qr_rn_shf(string scrid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Car_qr_rn_shf(final Context mContext, final SuccessCall call,String scrid,String addPer, int gsmid, int property, boolean IsSubtitle, String Keystring,String KeyTimestring,String username)
    {
        String methodName="Get_Sale_Order_Car_qr_rn_shf";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("scrid",scrid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("Keystring",Keystring);
        request.addProperty("KeysTimetring",KeyTimestring);
        request.addProperty("username",username);
        return getNetworkService(mContext,call,request);
    }


    /**
     * 接口名称：工地补货列表
     *    Get_Sale_Order_Car_qr_In_Json(int rowcount, string statusstr, string h_Number, int gsmid, int property, bool IsSubtitle, string username)
     */
    public static Subscription Get_Sale_Order_Car_qr_In_Json(final Context mContext, final SuccessCall call,int rowcount, String statusstr, String h_Number, int gsmid, int property, boolean IsSubtitle, String username)
    {
        String methodName="Get_Sale_Order_Car_qr_In_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("statusstr",statusstr);
        request.addProperty("h_Number",h_Number);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("username",username);
        request.addProperty("Is_Pro_User",App.Is_Pro_User);
        request.addProperty("Pro_Userstring",App.Pro_Userstring);
        return getNetworkService(mContext,call,request);
    }


    /**
     * 接口名称：工地补货详细信息
     * Get_Sale_Order_Car_qr_InView_Json(string sciid, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Sale_Order_Car_qr_InView_Json(final Context mContext, final SuccessCall call,String sciid, int gsmid, int property, boolean IsSubtitle)
    {
        String methodName="Get_Sale_Order_Car_qr_InView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("sciid",sciid);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取补货编号
     * Get_Sale_Order_Car_qr_In_so_Number(int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Sale_Order_Car_qr_In_so_Number(final Context mContext, final SuccessCall call,int gsmid, int property, boolean IsSubtitle)
    {
        String methodName="Get_Sale_Order_Car_qr_In_so_Number";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：根据构件二维码获取可补货信息
     *Get_Sale_Order_Car_qr_In_prid_QR_Code_Json(string prid_QR_Code, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Sale_Order_Car_qr_In_prid_QR_Code_Json(final Context mContext, final SuccessCall call,String prid_QR_Code, int gsmid, int property, boolean IsSubtitle)
    {
        String methodName="Get_Sale_Order_Car_qr_In_prid_QR_Code_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("Is_Pro_User",App.Is_Pro_User);
        request.addProperty("Pro_Userstring",App.Pro_Userstring);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：新增工地补货信息
     * Get_Sale_Order_Car_qr_In_Add(string billtablestr, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Car_qr_In_Add(final Context mContext, final SuccessCall call,String[][] billtablestr, int gsmid, int property, boolean IsSubtitle, String Keystring, String KeysTimetring, String username)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String methodName="Get_Sale_Order_Car_qr_In_Add";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("Keystring",Keystring);
        request.addProperty("KeysTimetring",KeysTimetring);
        request.addProperty("username",username);
        return getNetworkService(mContext,call,request);
    }

    /**
     *修改工地补货信息
     *Get_Sale_Order_Car_qr_In_Eedit(string billtablestr, string sciid, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Car_qr_In_Eedit(final Context mContext, final SuccessCall call,String[][] billtablestr,String sciid, int gsmid, int property, boolean IsSubtitle, String Keystring, String KeysTimetring, String username)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String methodName="Get_Sale_Order_Car_qr_In_Eedit";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("sciid",sciid);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("Keystring",Keystring);
        request.addProperty("KeysTimetring",KeysTimetring);
        request.addProperty("username",username);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：删除补货编号
     * Get_Sale_Order_Car_qr_In_Del(string sciid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Car_qr_In_Del(final Context mContext, final SuccessCall call,String sciid,String addPer, int gsmid, int property, boolean IsSubtitle, String Keystring,String KeyTimestring,String username)
    {
        String methodName="Get_Sale_Order_Car_qr_In_Del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("sciid",sciid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("Keystring",Keystring);
        request.addProperty("KeysTimetring",KeyTimestring);
        request.addProperty("username",username);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：审核补货
     * Get_Sale_Order_Car_qr_In_sh(string sciid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Car_qr_In_sh(final Context mContext, final SuccessCall call,String  sciid,String addPer, int gsmid, int property, boolean IsSubtitle, String Keystring,String KeyTimestring,String username)
    {
        String methodName="Get_Sale_Order_Car_qr_In_sh";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("sciid",sciid);
        request.addProperty("addPer",App.menname);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("Keystring",Keystring);
        request.addProperty("KeysTimetring",KeyTimestring);
        request.addProperty("username",username);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：反审核补货
     * Get_Sale_Order_Car_qr_In_shf(string sciid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Car_qr_In_shf(final Context mContext, final SuccessCall call,String sciid,String addPer, int gsmid, int property, boolean IsSubtitle, String Keystring,String KeyTimestring,String username)
    {
        String methodName="Get_Sale_Order_Car_qr_In_shf";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("sciid",sciid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("Keystring",Keystring);
        request.addProperty("KeysTimetring",KeyTimestring);
        request.addProperty("username",username);
        return getNetworkService(mContext,call,request);
    }

    /**
     * Observable list列表
     * @param rowcount
     * @param Car_No
     * @param gsmid
     * @param property
     * @param IsSubtitle
     * @param username
     * @return
     */
    public static Observable Get_Car_list_Json(int rowcount, String Car_No, int gsmid, int property, boolean IsSubtitle, String username)
    {
        String methodName="Get_Car_list_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("Car_No",Car_No);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("username",username);
        return getMutiNetworkService(request);
    }

    /**
     * Observable  listview 列表详细信息
     * @param Car_No_id
     * @param gsmid
     * @param property
     * @param IsSubtitle
     * @return
     */
    public static Observable Get_Car_listView_Json(String Car_No_id, int gsmid, int property, boolean IsSubtitle)
    {
        String methodName="Get_Car_listView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("Car_No_id",Car_No_id);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        return getMutiNetworkService(request);
    }


    /**
     * 接口名称：车次列表
     * Get_Car_list_Json(int rowcount, string Car_No, int gsmid, int property, bool IsSubtitle, string username)
     */
    public static Subscription Get_Car_list_Json(final Context mContext, final SuccessCall call,int rowcount, String Car_No, int gsmid, int property, boolean IsSubtitle, String username)
    {
        String methodName="Get_Car_list_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("Car_No",Car_No);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        request.addProperty("username",username);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：车次详细信息
     * Get_Car_listView_Json(string Car_No_id, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Car_listView_Json(final Context mContext, final SuccessCall call,String Car_No_id, int gsmid, int property, boolean IsSubtitle)
    {
        String methodName="Get_Car_listView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("Car_No_id",Car_No_id);
        request.addProperty("gsmid",gsmid);
        request.addProperty("property",property);
        request.addProperty("IsSubtitle",IsSubtitle);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：获取车次编号
     * Get_Car_No(int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Car_No(final Context mContext, final SuccessCall call)
    {
        String methodName="Get_Car_No";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：新增车次信息
     * Get_Car_list_Add(string billtablestr, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Car_list_Add(final Context mContext, final SuccessCall call,String[][] billtablestr)
    {
        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String methodName="Get_Car_list_Add";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：修改车次信息
     * Get_Car_list_Eedit(string billtablestr, string Car_No_id, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Car_list_Eedit(final Context mContext, final SuccessCall call,String[][] billtablestr,String Car_No_id)
    {
        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String methodName="Get_Car_list_Eedit";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("Car_No_id",Car_No_id);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：删除车次
     * Get_Car_list_Del(string Car_No_id, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Car_list_Del(final Context mContext, final SuccessCall call,String Car_No_id,String addPer)
    {
        String methodName="Get_Car_list_Del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("Car_No_id",Car_No_id);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：装车订单列表
     * Get_Sale_Order_Json(int rowcount, string statusstr, string so_Number, int gsmid, int property, bool IsSubtitle, string username)
     */
    public static Subscription Get_Sale_Order_Json(final Context mContext, final SuccessCall call,int rowcount, String statusstr, String so_Number)
    {
        String methodName="Get_Sale_Order_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("statusstr",statusstr);
        request.addProperty("so_Number",so_Number);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：装车订单详细信息
     * Get_Sale_OrderView_Json(string soid, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Sale_OrderView_Json(final Context mContext, final SuccessCall call,String soid)
    {
        String methodName="Get_Sale_OrderView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("soid",soid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取装车订单编号
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_so_Number(int gsmid, int property, bool IsSubtitle)
     */

    public static Subscription Get_Sale_Order_so_Number(final Context mContext, final SuccessCall call)
    {
        String methodName="Get_Sale_Order_so_Number";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：获取装车可选车次信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Car_listJson(int rowcount, string Car_No, int gsmid, int property, bool IsSubtitle, string username)
     */
    public static Subscription Get_Sale_Order_Car_listJson(final Context mContext, final SuccessCall call,int rowcount, String Car_No)
    {
        String methodName="Get_Sale_Order_Car_listJson";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("Car_No",Car_No);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("username",App.USER_INFO);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：获取装车可选货柜信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Container_listJson(int rowcount, string C_Number, string Container_datestr, int gsmid, int property, bool IsSubtitle, string username)
     */
    public static Subscription Get_Sale_Order_Container_listJson(final Context mContext, final SuccessCall call,int rowcount, String C_Number,String Container_datestr)
    {
        String methodName="Get_Sale_Order_Container_listJson";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("C_Number",C_Number);
        request.addProperty("Container_datestr",Container_datestr);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("username",App.USER_INFO);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：获取装车可选需求信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Hois_Req_listJson(int rowcount, string so_Number, int gsmid, int property, bool IsSubtitle, string username)
     */

    public static Subscription Get_Sale_Order_Hois_Req_listJson(final Context mContext, final SuccessCall call,int rowcount, String so_Number)
    {
        String methodName="Get_Sale_Order_Hois_Req_listJson";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("so_Number",so_Number);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("username",App.USER_INFO);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     *
     接口名称：获取装车可选需求明细信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Hois_Req_Json(string hid, int gsmid, int property, bool IsSubtitle)
     */

    public static Subscription Get_Sale_Order_Hois_Req_Json(final Context mContext, final SuccessCall call, String hid)
    {
        String methodName="Get_Sale_Order_Hois_Req_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("hid",hid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：新增装车订单信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Add(string billtablestr, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */

    public static Subscription Get_Sale_Order_Add(final Context mContext, final SuccessCall call,String[][] billtablestr)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String methodName="Get_Sale_Order_Add";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：修改装车订单信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Eedit(string billtablestr, string soid, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     *
     */
    public static Subscription Get_Sale_Order_Eedit(final Context mContext, final SuccessCall call,String[][] billtablestr,String soid)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String methodName="Get_Sale_Order_Eedit";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("soid",soid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：删除装车订单
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Del(string soid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Del(final Context mContext, final SuccessCall call,String soid,String addPer)
    {
        String methodName="Get_Sale_Order_Del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("soid",soid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：审核装车订单
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_sh(string soid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_sh(final Context mContext, final SuccessCall call,String soid,String addPer)
    {
        String methodName="Get_Sale_Order_sh";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("soid",soid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：反审核装车订单
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_shf(string soid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_shf(final Context mContext, final SuccessCall call,String soid,String addPer)
    {
        String methodName="Get_Sale_Order_shf";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("soid",soid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：运输发货列表
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Car_Json(int rowcount, string statusstr, string so_Number, int gsmid, int property, bool IsSubtitle, string username)
     */
    public static Subscription Get_Sale_Order_Car_Json(final Context mContext, final SuccessCall call,int rowcount, String statusstr, String so_Number)
    {
        String methodName="Get_Sale_Order_Car_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("statusstr",statusstr);
        request.addProperty("so_Number",so_Number);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：运输发货详细信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_CarView_Json(string scid, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Sale_Order_CarView_Json(final Context mContext, final SuccessCall call,String scid)
    {
        String methodName="Get_Sale_Order_CarView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("scid",scid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取运输发货编号
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Car_so_Number(int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Sale_Order_Car_so_Number(final Context mContext, final SuccessCall call)
    {
        String methodName="Get_Sale_Order_Car_so_Number";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：根据车次获取可发货信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_CarCHE_Json(string CHEid, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Sale_Order_CarCHE_Json(final Context mContext, final SuccessCall call, String CHEid)
    {
        String methodName="Get_Sale_Order_CarCHE_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("CHEid",CHEid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：新增运输发货信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Car_Add(string billtablestr, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Car_Add(final Context mContext, final SuccessCall call,String[][] billtablestr)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String methodName="Get_Sale_Order_Car_Add";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：修改运输发货信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：Get_Sale_Order_Car_Eedit(string billtablestr, string scid, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Car_Eedit(final Context mContext, final SuccessCall call,String[][] billtablestr,String scid)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String methodName="Get_Sale_Order_Car_Eedit";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("scid",scid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /*
    接口名称：删除运输发货
接口地址：http://192.168.11.96:7785/Service1.asmx
接口函数：
Get_Sale_Order_Car_Del(string scid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Car_Del(final Context mContext, final SuccessCall call,String scid,String addPer)
    {
        String methodName="Get_Sale_Order_Car_Del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("scid",scid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /*
    接口名称：审核运输发货
接口地址：http://192.168.11.96:7785/Service1.asmx
接口函数：
Get_Sale_Order_Car_sh(string scid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Car_sh(final Context mContext, final SuccessCall call,String scid,String addPer)
    {
        String methodName="Get_Sale_Order_Car_sh";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("scid",scid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }
    /*
    接口名称：反审核运输发货
接口地址：http://192.168.11.96:7785/Service1.asmx
接口函数：
Get_Sale_Order_Car_shf(string scid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Car_shf(final Context mContext, final SuccessCall call,String scid,String addPer)
    {
        String methodName="Get_Sale_Order_Car_shf";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("scid",scid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     *接口名称：构件退货列表
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Car_check_In_Json(int rowcount, string statusstr, string so_Number, int gsmid, int property, bool IsSubtitle, string username)
     */
    public static Subscription Get_Sale_Order_Car_check_In_Json(final Context mContext, final SuccessCall call,int rowcount, String statusstr, String so_Number)
    {
        String methodName="Get_Sale_Order_Car_check_In_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("statusstr",statusstr);
        request.addProperty("so_Number",so_Number);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     *接口名称：构件退货详细信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Car_check_InView_Json(string sciid, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Sale_Order_Car_check_InView_Json(final Context mContext, final SuccessCall call,String sciid)
    {
        String methodName="Get_Sale_Order_Car_check_InView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("sciid",sciid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取构件退货编号
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Car_check_In_so_Number(int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Sale_Order_Car_check_In_so_Number(final Context mContext, final SuccessCall call)
    {
        String methodName="Get_Sale_Order_Car_check_In_so_Number";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：根据构件二维码获取可退货信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Car_check_Inprid_QR_Code_Json(string prid_QR_Code, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Sale_Order_Car_check_Inprid_QR_Code_Json(final Context mContext, final SuccessCall call, String prid_QR_Code)
    {
        String methodName="Get_Sale_Order_Car_check_Inprid_QR_Code_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：根据仓库二维码获取仓库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     B_Get_Ware_house(string Ware_houseid, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription B_Get_Ware_house(final Context mContext, final SuccessCall call, String Ware_houseid)
    {
        String methodName="B_Get_Ware_house";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("Ware_houseid",Ware_houseid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：新增构件退货信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Car_check_In_Add(string billtablestr, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Car_check_In_Add(final Context mContext, final SuccessCall call,String[][] billtablestr)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String methodName="Get_Sale_Order_Car_check_In_Add";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：修改构件退货信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：Get_Sale_Order_Car_check_In_Eedit(string billtablestr, string sciid, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Car_check_In_Eedit(final Context mContext, final SuccessCall call,String[][] billtablestr,String sciid)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String methodName="Get_Sale_Order_Car_check_In_Eedit";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("sciid",sciid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：删除构件退货
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Car_check_In_Del(string sciid, string addPer, int gsmid, int property, bool IsSubtitle, string
     */
    public static Subscription Get_Sale_Order_Car_check_In_Del(final Context mContext, final SuccessCall call,String sciid,String addPer)
    {
        String methodName="Get_Sale_Order_Car_check_In_Del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("sciid",sciid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：审核构件退货
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Car_check_In_sh(string sciid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Car_check_In_sh(final Context mContext, final SuccessCall call,String sciid,String addPer)
    {
        String methodName="Get_Sale_Order_Car_check_In_sh";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("sciid",sciid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：反审核构件退货
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Car_check_In_shf(string sciid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Car_check_In_shf(final Context mContext, final SuccessCall call,String sciid,String addPer)
    {
        String methodName="Get_Sale_Order_Car_check_In_shf";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("sciid",sciid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     *
     接口名称：货柜回收列表
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Car_Container_Json(int rowcount, string statusstr, string so_Number, int gsmid, int property, bool IsSubtitle, string username)
     */
    public static Subscription Get_Sale_Order_Car_Container_Json(final Context mContext, final SuccessCall call,int rowcount, String statusstr, String so_Number)
    {
        String methodName="Get_Sale_Order_Car_Container_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("statusstr",statusstr);
        request.addProperty("so_Number",so_Number);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：货柜回收详细信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Car_ContainerView_Json(string sccid, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Sale_Order_Car_ContainerView_Json(final Context mContext, final SuccessCall call,String sccid)
    {
        String methodName="Get_Sale_Order_Car_ContainerView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("sccid",sccid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     *接口名称：获取货柜回收编号
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Car_Container_so_Number(int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Sale_Order_Car_Container_so_Number(final Context mContext, final SuccessCall call)
    {
        String methodName="Get_Sale_Order_Car_Container_so_Number";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }


    /**
     * 接口名称：根据车次二维码获取车次信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Car_ContainerCHE_Json(string CHEid, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Sale_Order_Car_ContainerCHE_Json(final Context mContext, final SuccessCall call, String CHEid)
    {
        String methodName="Get_Sale_Order_Car_ContainerCHE_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("CHEid",CHEid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：根据货柜二维码获取可回收信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Car_Containerprid_QR_Code_Json(string prid_QR_Code, string Car_No, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Sale_Order_Car_Containerprid_QR_Code_Json(final Context mContext, final SuccessCall call, String prid_QR_Code,String Car_No)
    {
        String methodName="Get_Sale_Order_Car_Containerprid_QR_Code_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("Car_No",Car_No);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：新增货柜回收信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Car_Container_Add(string billtablestr, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Car_Container_Add(final Context mContext, final SuccessCall call,String[][] billtablestr)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String methodName="Get_Sale_Order_Car_Container_Add";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：修改货柜回收信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：Get_Sale_Order_Car_Container_Eedit(string billtablestr, string sccid, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Car_Container_Eedit(final Context mContext, final SuccessCall call,String[][] billtablestr,String sccid)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String methodName="Get_Sale_Order_Car_Container_Eedit";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("sccid",sccid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：删除货柜回收
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Car_Container_Del(string sccid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Car_Container_Del(final Context mContext, final SuccessCall call,String sccid)
    {
        String methodName="Get_Sale_Order_Car_Container_Del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("sccid",sccid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：审核货柜回收
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Car_Container_sh(string sccid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Car_Container_sh(final Context mContext, final SuccessCall call,String sccid)
    {
        String methodName="Get_Sale_Order_Car_Container_sh";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("sccid",sccid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：反审核货柜回收
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Sale_Order_Car_Container_shf(string sccid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Sale_Order_Car_Container_shf(final Context mContext, final SuccessCall call,String sccid)
    {
        String methodName="Get_Sale_Order_Car_Container_shf";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("sccid",sccid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：生产订单列表
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Json(int rowcount, string statusstr, string po_Number, int gsmid, int property, bool IsSubtitle, string username)

     */
    public static Subscription Get_Production_order_Json(final Context mContext, final SuccessCall call,int rowcount, String statusstr, String po_Number)
    {
        String methodName="Get_Production_order_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("statusstr",statusstr);
        request.addProperty("po_Number",po_Number);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("username",App.USER_INFO);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：生产订单详细信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_orderView_Json(string poid, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_orderView_Json(final Context mContext, final SuccessCall call,String poid)
    {
        String methodName="Get_Production_orderView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：审核生产订单
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_sh(string poid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)

     */
    public static Subscription Get_Production_order_sh(final Context mContext, final SuccessCall call,String poid)
    {
        String methodName="Get_Production_order_sh";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：反审核生产订单
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_shf(string poid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_shf(final Context mContext, final SuccessCall call,String poid)
    {
        String methodName="Get_Production_order_shf";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：删除生产订单
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Del(string poid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)

     */
    public static Subscription Get_Production_order_Del(final Context mContext, final SuccessCall call,String poid)
    {
        String methodName="Get_Production_order_Del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：根据生产订单ID获取备料详细信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_orderView_list_Json(string poid, int gsmid, int property, bool IsSubtitle, string username)
     */
    public static Subscription Get_Production_orderView_list_Json(final Context mContext, final SuccessCall call,String poid)
    {
        String methodName="Get_Production_orderView_list_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：生产订单-开始备料
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Stock_start(string poid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Stock_start(final Context mContext, final SuccessCall call,String poid)
    {
        String methodName="Get_Production_order_Stock_start";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：生产订单-备料完成
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Stock_end(string poid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Stock_end(final Context mContext, final SuccessCall call,String poid)
    {
        String methodName="Get_Production_order_Stock_end";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：生产订单-取消备料
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Stock_del(string poid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Stock_del(final Context mContext, final SuccessCall call,String poid)
    {
        String methodName="Get_Production_order_Stock_del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：领料出库列表
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Rmaterlist_Json(int rowcount, string statusstr, string po_Number, int gsmid, int property, bool IsSubtitle, string username)
     */
    public static Subscription Get_Production_order_Rmaterlist_Json(final Context mContext, final SuccessCall call,int rowcount, String statusstr, String po_Number)
    {
        String methodName="Get_Production_order_Rmaterlist_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("statusstr",statusstr);
        request.addProperty("po_Number",po_Number);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：领料出库详细信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_RmaterlistView_Json(string pmid, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_RmaterlistView_Json(final Context mContext, final SuccessCall call,String pmid)
    {
        String methodName="Get_Production_order_RmaterlistView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("pmid",pmid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取领料出库编号
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Rmaterlist_po_Number(int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_Rmaterlist_po_Number(final Context mContext, final SuccessCall call )
    {
        String methodName="Get_Production_order_Rmaterlist_po_Number";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：扫描生产订单二维码获取可领料信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Rmaterlist_bypoid_Json(string poid, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_Rmaterlist_bypoid_Json(final Context mContext, final SuccessCall call,String poid)
    {
        String methodName="Get_Production_order_Rmaterlist_bypoid_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：新增领料出库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Rmaterlist_Add(string billtablestr, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Rmaterlist_Add(final Context mContext, final SuccessCall call,String[][] billtablestr)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String methodName="Get_Production_order_Rmaterlist_Add";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：修改领料出库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Rmaterlist_Eedit(string billtablestr, string pmid, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Rmaterlist_Eedit(final Context mContext, final SuccessCall call,String[][] billtablestr,String pmid)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String methodName="Get_Production_order_Rmaterlist_Eedit";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("pmid",pmid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：删除领料出库数据
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Rmaterlist_Del(string pmid,string poid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Rmaterlist_Del(final Context mContext, final SuccessCall call,String pmid,String poid)
    {
        String methodName="Get_Production_order_Rmaterlist_Del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("pmid",pmid);
        request.addProperty("poid",poid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：审核领料出库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Rmaterlist_sh(string pmid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Rmaterlist_sh(final Context mContext, final SuccessCall call,String pmid)
    {
        String methodName="Get_Production_order_Rmaterlist_sh";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("pmid",pmid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：反审核领料出库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Rmaterlist_shf(string pmid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Rmaterlist_shf(final Context mContext, final SuccessCall call,String pmid)
    {
        String methodName="Get_Production_order_Rmaterlist_shf";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("pmid",pmid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：完工入库列表
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_In_Json(int rowcount, string statusstr, string po_Number, int gsmid, int property, bool IsSubtitle, string username)
     */
    public static Subscription Get_Production_order_In_Json(final Context mContext, final SuccessCall call,int rowcount, String statusstr, String po_Number)
    {
        String methodName="Get_Production_order_In_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("statusstr",statusstr);
        request.addProperty("po_Number",po_Number);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：完工入库详细信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_InView_Json(string pmid, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_InView_Json(final Context mContext, final SuccessCall call,String pmid)
    {
        String methodName="Get_Production_order_InView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("pmid",pmid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取完工入库编号
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_In_po_Number(int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_In_po_Number(final Context mContext, final SuccessCall call)
    {
        String methodName="Get_Production_order_In_po_Number";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：扫描生产订单二维码获取可入库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_In_bypoid_Json(string poid, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_In_bypoid_Json(final Context mContext, final SuccessCall call, String poid)
    {
        String methodName="Get_Production_order_In_bypoid_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：新增完工入库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_In_Add(string billtablestr, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_In_Add(final Context mContext, final SuccessCall call,String[][] billtablestr)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String methodName="Get_Production_order_In_Add";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：修改完工入库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_In_Eedit(string billtablestr, string pmid, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)

     */
    public static Subscription Get_Production_order_In_Eedit(final Context mContext, final SuccessCall call,String[][] billtablestr,String sccid)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String methodName="Get_Production_order_In_Eedit";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("pmid",sccid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：删除完工入库数据
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_In_Del(string pmid, string poid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_In_Del(final Context mContext, final SuccessCall call,String pmid,String poid)
    {
        String methodName="Get_Production_order_In_Del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("pmid",pmid);
        request.addProperty("poid",poid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：审核完工入库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_In_sh(string pmid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_In_sh(final Context mContext, final SuccessCall call,String pmid)
    {
        String methodName="Get_Production_order_In_sh";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("pmid",pmid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：反审核完工入库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_In_shf(string pmid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_In_shf(final Context mContext, final SuccessCall call,String pmid)
    {
        String methodName="Get_Production_order_In_shf";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("pmid",pmid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：托盘配料列表
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Tray_Json(int rowcount, string statusstr, string po_Number, bool Is_Moid_qr, int gsmid, int property, bool IsSubtitle, string username)
     */
    public static Subscription Get_Production_order_Tray_Json(final Context mContext, final SuccessCall call,int rowcount, String statusstr, String po_Number,boolean Is_Moid_qr)
    {
        String methodName="Get_Production_order_Tray_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("statusstr",statusstr);
        request.addProperty("po_Number",po_Number);
        request.addProperty("Is_Moid_qr",Is_Moid_qr);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：托盘配料详细信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_TrayView_Json(string p_Tid, int gsmid, int property, bool IsSubtitle)
     */

    public static Subscription Get_Production_order_TrayView_Json(final Context mContext, final SuccessCall call,String p_Tid)
    {
        String methodName="Get_Production_order_TrayView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Tid",p_Tid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取托盘配料编号
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Tray_po_Number(int gsmid, int property, bool IsSubtitle)

     */
    public static Subscription Get_Production_order_Tray_po_Number(final Context mContext, final SuccessCall call )
    {
        String methodName="Get_Production_order_Tray_po_Number";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取可托盘配料的台车模具信息（可多选）
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Tray_byTid_Json(int rowcount, string prid_QR_Code, int gsmid, int property, bool IsSubtitle, string username)
     */
    public static Subscription Get_Production_order_Tray_byTid_Json(final Context mContext, final SuccessCall call,String prid_QR_Code,int rowcount)
    {
        String methodName="Get_Production_order_Tray_byTid_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：根据订单ID集合与构件二维码集获取可配料的台车模具明细(集合实例：a','b','c','s)
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Tray_byTidView_Json(string poidstr, string prid_QR_Codestr, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_Tray_byTidView_Json(final Context mContext, final SuccessCall call,String poidstr, String prid_QR_Codestr)
    {
        String methodName="Get_Production_order_Tray_byTidView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poidstr",poidstr);
        request.addProperty("prid_QR_Codestr",prid_QR_Codestr);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：新增托盘配料信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Tray_Add(string billtablestr, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Tray_Add(final Context mContext, final SuccessCall call,String[][] billtablestr)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String methodName="Get_Production_order_Tray_Add";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：修改托盘配料信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Tray_Eedit(string billtablestr, string p_Tid, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Tray_Eedit(final Context mContext, final SuccessCall call,String[][] billtablestr,String p_Tid)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String methodName="Get_Production_order_Tray_Eedit";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("p_Tid",p_Tid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：删除托盘配料数据
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Tray_Del(string p_Tid, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Tray_Del(final Context mContext, final SuccessCall call,String p_Tid)
    {
        String methodName="Get_Production_order_Tray_Del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Tid",p_Tid);
//        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }


    /**
     * 接口名称：审核托盘配料信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Tray_sh(string p_Tid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Tray_sh(final Context mContext, final SuccessCall call,String p_Tid)
    {
        String methodName="Get_Production_order_Tray_sh";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Tid",p_Tid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：反审核托盘配料信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Tray_shf(string p_Tid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Tray_shf(final Context mContext, final SuccessCall call,String p_Tid)
    {
        String methodName="Get_Production_order_Tray_shf";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Tid",p_Tid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：根据托盘二维码获取货柜信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     B_Get_Tray_Man(string Tid, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription B_Get_Tray_Man(final Context mContext, final SuccessCall call,String Tid )
    {
        String methodName="B_Get_Tray_Man";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("Tid",Tid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：根据构件二维码获取订单信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Pro_Working_Main_poid_byprid_QR_Code_Json(string prid_QR_Code, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Pro_Working_Main_poid_byprid_QR_Code_Json(final Context mContext, final SuccessCall call,String prid_QR_Code)
    {
        String methodName="Get_Pro_Working_Main_poid_byprid_QR_Code_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     *
     接口名称：根据构件二维码和订单ID获取工艺路线执行信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Pro_Working_Main_poid_Json(string prid_QR_Code,string poid, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Pro_Working_Main_poid_Json(final Context mContext, final SuccessCall call , String prid_QR_Code,String poid)
    {
        String methodName="Get_Pro_Working_Main_poid_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("poid",poid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：执行工序（无关联单据）
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Pro_Working_Main_poid_zx(string pwid_p, string pwid_i_p, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Pro_Working_Main_poid_zx(final Context mContext, final SuccessCall call,String pwid_p,String pwid_i_p)
    {
        String methodName="Get_Pro_Working_Main_poid_zx";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("pwid_p",pwid_p);
        request.addProperty("pwid_i_p",pwid_i_p);
        request.addProperty("addPer",App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：撤回工序（无关联单据）
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Pro_Working_Main_poid_zxf(string pwid_p, string pwid_i_p, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Pro_Working_Main_poid_zxf(final Context mContext, final SuccessCall call,String pwid_p,String pwid_i_p)
    {
        String methodName="Get_Pro_Working_Main_poid_zxf";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("pwid_p",pwid_p);
        request.addProperty("pwid_i_p",pwid_i_p);
        request.addProperty("addPer",App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     接口名称：获取工序对应的资源单据明细
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Pro_Working_Item_r_poid_Json(string pwid_i_p, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Pro_Working_Item_r_poid_Json(final Context mContext, final SuccessCall call,String pwid_i_p)
    {
        String methodName="Get_Pro_Working_Item_r_poid_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("pwid_i_p",pwid_i_p);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     接口名称：获取模具分配单详细信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_MouldView_Json(string p_moid_r, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_MouldView_Json(final Context mContext, final SuccessCall call,String p_moid_r)
    {
        String methodName="Get_Production_order_MouldView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_moid_r",p_moid_r);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }
    /**
    接口名称：获取模具分配单详细信息(订单和二维码)
    接口地址：http://192.168.11.96:7785/Service1.asmx
    接口函数：
    Get_Production_order_MouldView_Json_poidprid_QR_Code(string poid, string prid_QR_Code, int gsmid, int property, bool IsSubtitle)
    */
    public static Subscription Get_Production_order_MouldView_Json_poidprid_QR_Code(final Context mContext, final SuccessCall call,String poid, String prid_QR_Code)
    {
        String methodName="Get_Production_order_MouldView_Json_poidprid_QR_Code";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取模具分配单编号
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Mould_po_Number(int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_Mould_po_Number(final Context mContext, final SuccessCall call )
    {
        String methodName="Get_Production_order_Mould_po_Number";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }
    /**
     * 接口名称：根据生产订单和二维码获取需要分配模具的构件信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Mould_bypoid_Json(string poid, string prid_QR_Code, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_Mould_bypoid_Json(final Context mContext, final SuccessCall call,String poid,String prid_QR_Code)
    {
        String methodName="Get_Production_order_Mould_bypoid_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：根据订单ID与开始和结束日期获取可分配的模具信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Mould_Mouldfp_Json(string poid, string prid_QR_Code, string prid, DateTime startdate, DateTime enddate, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_Mould_Mouldfp_Json(final Context mContext, final SuccessCall call,String poid, String prid_QR_Code,String prid,String startdate, String enddate)
    {
        String methodName="Get_Production_order_Mould_Mouldfp_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("prid",prid);
        request.addProperty("startdate",startdate);
        request.addProperty("enddate",enddate);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：新增模具分配信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Mould_Add(string billtablestr, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Mould_Add(final Context mContext, final SuccessCall call,String[][] billtablestr)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String methodName="Get_Production_order_Mould_Add";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：修改模具分配信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Mould_Eedit(string billtablestr, string p_moid, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Mould_Eedit(final Context mContext, final SuccessCall call,String[][] billtablestr,String p_moid)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String methodName="Get_Production_order_Mould_Eedit";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("p_moid",p_moid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：删除模具分配信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Mould_Del(string p_moid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Mould_Del(final Context mContext, final SuccessCall call,String p_moid)
    {
        String methodName="Get_Production_order_Mould_Del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_moid",p_moid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：审核模具分配信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Mould_sh(string p_moid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Mould_sh(final Context mContext, final SuccessCall call,String p_moid)
    {
        String methodName="Get_Production_order_Mould_sh";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_moid",p_moid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：反审核模具分配信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Mould_shf(string p_moid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Mould_shf(final Context mContext, final SuccessCall call,String p_moid)
    {
        String methodName="Get_Production_order_Mould_shf";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_moid",p_moid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 台车分配单
     接口名称：获取台车分配单详细信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_TrolleyView_Json(string p_Tid_r, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_TrolleyView_Json(final Context mContext, final SuccessCall call,String p_Tid_r)
    {
        String methodName="Get_Production_order_TrolleyView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Tid_r",p_Tid_r);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取台车分配单详细信息(订单和二维码)
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_TrolleyView_Json_poidprid_QR_Code(string poid, string prid_QR_Code, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_TrolleyView_Json_poidprid_QR_Code(final Context mContext, final SuccessCall call,String poid,String prid_QR_Code)
    {
        String methodName="Get_Production_order_TrolleyView_Json_poidprid_QR_Code";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取台车分配单编号
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Trolleypo_Number(int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_Trolleypo_Number(final Context mContext, final SuccessCall call )
    {
        String methodName="Get_Production_order_Trolleypo_Number";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：根据生产订单和二维码获取需要分配台车的构件模具信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Trolley_bypoid_Json(string poid, string prid_QR_Code, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_Trolley_bypoid_Json(final Context mContext, final SuccessCall call,String poid,String prid_QR_Code)
    {
        String methodName="Get_Production_order_Trolley_bypoid_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：根据订单ID与开始和结束日期获取可分配的台车信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Trolley_Trolleyfp_Json(string poid, string prid_QR_Code, string Moid, DateTime startdate, DateTime enddate, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_Trolley_Trolleyfp_Json(final Context mContext, final SuccessCall call,String poid, String prid_QR_Code,String prid,String startdate, String enddate)
    {
        String methodName="Get_Production_order_Trolley_Trolleyfp_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("prid",prid);
        request.addProperty("startdate",startdate);
        request.addProperty("enddate",enddate);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：新增台车分配信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Trolley_Add(string billtablestr, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Trolley_Add(final Context mContext, final SuccessCall call,String[][] billtablestr)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String methodName="Get_Production_order_Trolley_Add";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：根据台车ID获取台车对应的产线信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Trolley_widfp_Json(string Tid, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_Trolley_widfp_Json(final Context mContext, final SuccessCall call,String Tid)
    {
        String methodName="Get_Production_order_Trolley_widfp_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("Tid",Tid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }


    /**
     * 接口名称：修改台车分配信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Trolley_Eedit(string billtablestr, string p_Tid, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Trolley_Eedit(final Context mContext, final SuccessCall call,String[][] billtablestr,String p_Tid)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String methodName="Get_Production_order_Trolley_Eedit";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("p_Tid",p_Tid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：删除台车分配信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Trolley_Del(string p_Tid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Trolley_Del(final Context mContext, final SuccessCall call,String p_Tid)
    {
        String methodName="Get_Production_order_Trolley_Del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Tid",p_Tid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }
    /**
     * 接口名称：台车分配-模具调拨
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Trolley_Mould_Db(string p_Tid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Trolley_Mould_Db(final Context mContext, final SuccessCall call,String p_Tid)
    {
        String methodName="Get_Production_order_Trolley_Mould_Db";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Tid",p_Tid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：审核台车分配信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Trolley_sh(string p_Tid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Trolley_sh(final Context mContext, final SuccessCall call,String p_Tid)
    {
        String methodName="Get_Production_order_Trolley_sh";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Tid",p_Tid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：反审核台车分配信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Trolley_shf(string p_Tid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Trolley_shf(final Context mContext, final SuccessCall call,String p_Tid)
    {
        String methodName="Get_Production_order_Trolley_shf";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Tid",p_Tid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 人工分配单
     接口名称：获取人工分配单详细信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_PerView_Json(string Per_id, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_PerView_Json(final Context mContext, final SuccessCall call,String Per_id)
    {
        String methodName="Get_Production_order_PerView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("Per_id",Per_id);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     接口名称：获取人工分配单详细信息(订单和二维码)
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_PerView_Json_poidprid_QR_Code(string poid, string prid_QR_Code, string pwid_i_p, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_PerView_Json_poidprid_QR_Code(final Context mContext, final SuccessCall call,String poid,String prid_QR_Code,String pwid_i_p)
    {
        String methodName="Get_Production_order_PerView_Json_poidprid_QR_Code";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("pwid_i_p",pwid_i_p);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取人工分配单编号
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Per_po_Number(int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_Per_po_Number(final Context mContext, final SuccessCall call )
    {
        String methodName="Get_Production_order_Per_po_Number";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     接口名称：根据生产订单和二维码，工序资源ID获取需要分配的人工分配信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Per_bypoid_Json(string poid, string prid_QR_Code, string pwid_i_r_p, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_Per_bypoid_Json(final Context mContext, final SuccessCall call,String poid,String prid_QR_Code,String pwid_i_r_p)
    {
        String methodName="Get_Production_order_Per_bypoid_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("pwid_i_p",pwid_i_r_p);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     *
     接口名称：查询职员信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Per_Member_Json(int rowcount,string mennumber,int gsmid, int property, bool IsSubtitle)传入参数：
     */
    public static Subscription Get_Production_order_Per_Member_Json(final Context mContext, final SuccessCall call,int rowcount,String mennumber )
    {
        String methodName="Get_Production_order_Per_Member_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("mennumber",mennumber);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：新增人工分配信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Per_Add(string billtablestr, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Per_Add(final Context mContext, final SuccessCall call,String[][] billtablestr)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String methodName="Get_Production_order_Per_Add";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：修改人工分配信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Per_Eedit(string billtablestr, string Per_id, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Per_Eedit(final Context mContext, final SuccessCall call,String[][] billtablestr,String Per_id)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String methodName="Get_Production_order_Per_Eedit";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("Per_id",Per_id);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }


    /**
     * 接口名称：删除人工分配信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Per_Del(string Per_id, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Per_Del(final Context mContext, final SuccessCall call,String Per_id)
    {
        String methodName="Get_Production_order_Per_Del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("Per_id",Per_id);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：审核人工分配信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Per_sh(string Per_id, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)s
     */
    public static Subscription Get_Production_order_Per_sh(final Context mContext, final SuccessCall call,String Per_id)
    {
        String methodName="Get_Production_order_Per_sh";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("Per_id",Per_id);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：反审核人工分配信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Per_shf(string Per_id, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Per_shf(final Context mContext, final SuccessCall call,String Per_id)
    {
        String methodName="Get_Production_order_Per_shf";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("Per_id",Per_id);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 模具投料单
     接口名称：获取模具投料单详细信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Tray_Is_Moid_qrView_Json(string p_Tid, string poid, string prid_QR_Code, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_Tray_Is_Moid_qrView_Json(final Context mContext, final SuccessCall call,String p_Tid)
    {
        String methodName="Get_Production_order_Tray_Is_Moid_qrView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Tid",p_Tid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取模具投料单详细信息(订单和二维码)
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Tray_Is_Moid_qrView_Json_poidprid_QR_Code(string poid, string prid_QR_Code, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_Tray_Is_Moid_qrView_Json_poidprid_QR_Code(final Context mContext, final SuccessCall call,String poid,String prid_QR_Code)
    {
        String methodName="Get_Production_order_Tray_Is_Moid_qrView_Json_poidprid_QR_Code";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     接口名称：根据生产订单和二维码获取托盘配料单信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Tray_Is_Moid_qr_byTray_Json(int rowcount, string PNumber, string poid, string prid_QR_Code, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_Tray_Is_Moid_qr_byTray_Json(final Context mContext, final SuccessCall call,String poid,String prid_QR_Code,String rowcount,String PNumber)
    {
        String methodName="Get_Production_order_Tray_Is_Moid_qr_byTray_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("PNumber",PNumber);
        request.addProperty("poid",poid);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：根据生产订单和二维码，托盘配料单ID获取需要模具投料的信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Tray_Is_Moid_qr_byRow_Json(string p_Tid, string poid, string prid_QR_Code, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_Tray_Is_Moid_qr_byRow_Json(final Context mContext, final SuccessCall call,String poid,String prid_QR_Code,String p_Tid)
    {
        String methodName="Get_Production_order_Tray_Is_Moid_qr_byRow_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("p_Tid",p_Tid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：模具投料-托盘调拨
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Tray_Is_Moid_qr_Tray_Db(string p_Tid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Tray_Is_Moid_qr_Tray_Db(final Context mContext, final SuccessCall call,String p_Tid,String wid_In)
    {
        String methodName="Get_Production_order_Tray_Is_Moid_qr_Tray_Db";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("addPer",App.menname);
        request.addProperty("wid_In",wid_In);
        request.addProperty("p_Tid",p_Tid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：模具投料
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Tray_Is_Moid_qr_Add(string p_Tid,string wid_In, string poid, string prid_QR_Code, DateTime billdate, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Tray_Is_Moid_qr_Add(final Context mContext, final SuccessCall call,String p_Tid,String wid_In,String poid,String prid_QR_Code,String billdate)
    {
        String methodName="Get_Production_order_Tray_Is_Moid_qr_Add";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Tid",p_Tid);
        request.addProperty("wid_In",wid_In);
        request.addProperty("poid",poid);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("billdate",billdate);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：删除模具投料
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Tray_Is_Moid_qr_Del(string p_Tid, string poid, string prid_QR_Code, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Tray_Is_Moid_qr_Del(final Context mContext, final SuccessCall call,String p_Tid,String poid,String prid_QR_Code)
    {
        String methodName="Get_Production_order_Tray_Is_Moid_qr_Del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Tid",p_Tid);
        request.addProperty("poid",poid);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 养护入库明细
     接口名称：获取养护入库详细信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_In_byidr_Json(string p_Yid_r, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_yhy_In_byidr_Json(final Context mContext, final SuccessCall call,String p_Yid_r)
    {
        String methodName="Get_Production_order_yhy_In_byidr_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Yid_r",p_Yid_r);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取养护入库信息(订单和二维码)
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_In_byidr_Json_poidprid_QR_Code(string poid, string prid_QR_Code, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_yhy_In_byidr_Json_poidprid_QR_Code(final Context mContext, final SuccessCall call,String poid,String prid_QR_Code)
    {
        String methodName="Get_Production_order_yhy_In_byidr_Json_poidprid_QR_Code";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }



    /**
     * 养护出库明细
     接口名称：获取养护出库详细信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_Out_byidr_Json(string p_Yid_o_r, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_yhy_Out_byidr_Json(final Context mContext, final SuccessCall call,String p_Yid_r)
    {
        String methodName="Get_Production_order_yhy_Out_byidr_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Yid_r",p_Yid_r);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取养护出库信息(订单和二维码)
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_Out_byidr_Json_poidprid_QR_Code(string poid, string prid_QR_Code, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_yhy_Out_byidr_Json_poidprid_QR_Code(final Context mContext, final SuccessCall call,String poid,String prid_QR_Code)
    {
        String methodName="Get_Production_order_yhy_Out_byidr_Json_poidprid_QR_Code";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 构件脱模
     接口名称：获取构件脱模详细信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_Out_MouldView_Json(string p_Yid_o_m_r, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_yhy_Out_MouldView_Json(final Context mContext, final SuccessCall call,String p_Yid_o_m_r)
    {
        String methodName="Get_Production_order_yhy_Out_MouldView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Yid_o_m_r",p_Yid_o_m_r);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取构件脱模详细信息(订单和二维码)
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_Out_MouldView_Json_poidprid_QR_Code(string poid, string prid_QR_Code, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_yhy_Out_MouldView_Json_poidprid_QR_Code(final Context mContext, final SuccessCall call,String poid,String prid_QR_Code)
    {
        String methodName="Get_Production_order_yhy_Out_MouldView_Json_poidprid_QR_Code";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：根据生产订单和二维码获取需要构件脱模的信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_Out_Mould_bypoid_Json(string poid, string prid_QR_Code, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_yhy_Out_Mould_bypoid_Json(final Context mContext, final SuccessCall call,String poid,String prid_QR_Code)
    {
        String methodName="Get_Production_order_yhy_Out_Mould_bypoid_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     接口名称：获取构件脱模单编号
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_Out_Mouldpo_Number(int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_yhy_Out_Mouldpo_Number(final Context mContext, final SuccessCall call )
    {
        String methodName="Get_Production_order_yhy_Out_Mouldpo_Number";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：新增构件脱模信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_Out_Mould_Add(string billtablestr, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_yhy_Out_Mould_Add(final Context mContext, final SuccessCall call,String[][] billtablestr)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String methodName="Get_Production_order_yhy_Out_Mould_Add";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：删除构件脱模
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_Out_Mould_Del(string p_Yid_o_m, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_yhy_Out_Mould_Del(final Context mContext, final SuccessCall call,String p_Yid_o_m)
    {
        String methodName="Get_Production_order_yhy_Out_Mould_Del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Yid_o_m",p_Yid_o_m);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 构件入库单
     接口名称：获取构件入库详细信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_In_prid_QR_CodeView_Json(string pmid_r, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_In_prid_QR_CodeView_Json(final Context mContext, final SuccessCall call,String pmid_r)
    {
        String methodName="Get_Production_order_In_prid_QR_CodeView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("pmid_r",pmid_r);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取构件入库详细信息(订单和二维码)
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_In_prid_QR_CodeView_Json_poidprid_QR_Code(string poid, string prid_QR_Code, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_In_prid_QR_CodeView_Json_poidprid_QR_Code(final Context mContext, final SuccessCall call,String poid,String prid_QR_Code )
    {
        String methodName="Get_Production_order_In_prid_QR_CodeView_Json_poidprid_QR_Code";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取构件入库编号
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_In_prid_QR_Code_po_Number(int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_In_prid_QR_Code_po_Number(final Context mContext, final SuccessCall call )
    {
        String methodName="Get_Production_order_In_prid_QR_Code_po_Number";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：扫描生产订单与构件二维码获取可入库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_In_prid_QR_Code_bypoid_Json(string poid, string prid_QR_Code, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_In_prid_QR_Code_bypoid_Json(final Context mContext, final SuccessCall call,String poid,String prid_QR_Code)
    {
        String methodName="Get_Production_order_In_prid_QR_Code_bypoid_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("poid",poid);
        request.addProperty("prid_QR_Code",prid_QR_Code);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：新增构件入库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_In_prid_QR_Code_Add(string billtablestr, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_In_prid_QR_Code_Add(final Context mContext, final SuccessCall call,String[][] billtablestr)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String methodName="Get_Production_order_In_prid_QR_Code_Add";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：修改构件入库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_In_prid_QR_Code_Eedit(string billtablestr, string pmid, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_In_prid_QR_Code_Eedit(final Context mContext, final SuccessCall call,String[][] billtablestr,String pmid)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String methodName="Get_Production_order_In_prid_QR_Code_Eedit";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("pmid",pmid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：删除构件入库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_In_prid_QR_Code_Del(string pmid, string poid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_In_prid_QR_Code_Del(final Context mContext, final SuccessCall call,String pmid,String poid)
    {
        String methodName="Get_Production_order_In_prid_QR_Code_Del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("pmid",pmid);
        request.addProperty("poid",poid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：审核构件入库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_In_prid_QR_Code_sh(string pmid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_In_prid_QR_Code_sh(final Context mContext, final SuccessCall call,String pmid)
    {
        String methodName="Get_Production_order_In_prid_QR_Code_sh";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("pmid",pmid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：反审核构件入库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_In_prid_QR_Code_shf(string pmid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_In_prid_QR_Code_shf(final Context mContext, final SuccessCall call,String pmid)
    {
        String methodName="Get_Production_order_In_prid_QR_Code_shf";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("pmid",pmid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 养护入库单
     接口名称：获取养护入库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_In_Json(int rowcount, string po_Number, int gsmid, int property, bool IsSubtitle, string username)
     */
    public static Subscription Get_Production_order_yhy_In_Json(final Context mContext, final SuccessCall call,int rowcount , String po_Number )
    {
        String methodName="Get_Production_order_yhy_In_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("po_Number",po_Number);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取养护入库详细信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhyView_Json(string p_Yid, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_yhyView_Json(final Context mContext, final SuccessCall call,String p_Yid)
    {
        String methodName="Get_Production_order_yhyView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Yid",p_Yid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取养护入库单编号
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_Inpo_Number(int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_yhy_Inpo_Number(final Context mContext, final SuccessCall call )
    {
        String methodName="Get_Production_order_yhy_Inpo_Number";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：扫描台车二维码获取可养护入库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_byTid_Json(string Tid, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_yhy_byTid_Json(final Context mContext, final SuccessCall call ,String Tid)
    {
        String methodName="Get_Production_order_yhy_byTid_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("Tid",Tid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：养护入库仓库选择信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_In_wid_Json(int rowcount, string po_Number, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_yhy_In_wid_Json(final Context mContext, final SuccessCall call ,int rowcount,String po_Number)
    {
        String methodName="Get_Production_order_yhy_In_wid_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("po_Number",po_Number);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：养护入库-台车调拨
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_In_Trolley_Db(string Tid, string T_Number, string T_Name,string wid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_yhy_In_Trolley_Db(final Context mContext, final SuccessCall call,String Tid,String T_Number,String T_Name,String wid)
    {
        String methodName="Get_Production_order_yhy_In_Trolley_Db";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("Tid",Tid);
        request.addProperty("T_Number",T_Number);
        request.addProperty("T_Name",T_Name);
        request.addProperty("wid",wid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：养护入库-模具调拨
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_In_Mould_Db(string Tid,string wid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_yhy_In_Mould_Db(final Context mContext, final SuccessCall call,String Tid,String wid)
    {
        String methodName="Get_Production_order_yhy_In_Mould_Db";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("Tid",Tid);
        request.addProperty("wid",wid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：新增养护入库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_In_Add(string billtablestr, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */

    public static Subscription Get_Production_order_yhy_In_Add(final Context mContext, final SuccessCall call,String[][] billtablestr)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String methodName="Get_Production_order_yhy_In_Add";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：删除养护入库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_In_Del(string p_Yid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_yhy_In_Del(final Context mContext, final SuccessCall call,String p_Yid)
    {
        String methodName="Get_Production_order_yhy_In_Del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Yid",p_Yid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 养护出库单
     接口名称：获取养护出库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_Out_Json(int rowcount, string po_Number, int gsmid, int property, bool IsSubtitle, string username)
     */
    public static Subscription Get_Production_order_yhy_Out_Json(final Context mContext, final SuccessCall call,int rowcount, String po_Number)
    {
        String methodName="Get_Production_order_yhy_Out_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("po_Number",po_Number);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取养护出库详细信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_OutView_Json(string p_Yid_o, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_yhy_OutView_Json(final Context mContext, final SuccessCall call,String p_Yid_o)
    {
        String methodName="Get_Production_order_yhy_OutView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Yid_o",p_Yid_o);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取养护出库单编号
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_Outpo_Number(int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_yhy_Outpo_Number(final Context mContext, final SuccessCall call)
    {
        String methodName="Get_Production_order_yhy_Outpo_Number";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：养护出库台车选择信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_Out_Tid_Json(int rowcount, string po_Number, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_yhy_Out_Tid_Json(final Context mContext, final SuccessCall call, String rowcount,String po_Number)
    {
        String methodName="Get_Production_order_yhy_Out_Tid_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("po_Number",po_Number);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：根据台车ID获取可养护出库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_Out_byTid_Json(string Tid, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_yhy_Out_byTid_Json(final Context mContext, final SuccessCall call, String Tid)
    {
        String methodName="Get_Production_order_yhy_Out_byTid_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("Tid",Tid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：养护出库-台车调拨
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_Out_Trolley_Db(string Tid, string T_Number, string T_Name,string wid_In, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_yhy_Out_Trolley_Db(final Context mContext, final SuccessCall call,String Tid, String T_Number, String T_Name,String wid_In )
    {
        String methodName="Get_Production_order_yhy_Out_Trolley_Db";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("Tid",Tid);
        request.addProperty("T_Number",T_Number);
        request.addProperty("T_Name",T_Name);
        request.addProperty("wid_In",wid_In);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：养护出库-模具调拨
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_Out_Mould_Db(string Tid,string wid_In, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_yhy_Out_Mould_Db(final Context mContext, final SuccessCall call,String Tid,String wid_In)
    {
        String methodName="Get_Production_order_yhy_Out_Mould_Db";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("Tid",Tid);
        request.addProperty("wid_In",wid_In);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：新增养护出库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_Out_Add(string billtablestr, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_yhy_Out_Add(final Context mContext, final SuccessCall call,String[][] billtablestr)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String methodName="Get_Production_order_yhy_Out_Add";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：删除养护出库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_yhy_Out_Del(string p_Yid_o, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_yhy_Out_Del(final Context mContext, final SuccessCall call,String p_Yid_o)
    {
        String methodName="Get_Production_order_yhy_Out_Del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Yid_o",p_Yid_o);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 台车模具分离单
     接口名称：获取台车模具分离信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Trolley_Mould_Json(int rowcount, string statusstr, string po_Number, int gsmid, int property, bool IsSubtitle, string username)
     */
    public static Subscription Get_Production_order_Trolley_Mould_Json(final Context mContext, final SuccessCall call,int rowcount, String statusstr, String po_Number)
    {
        String methodName="Get_Production_order_Trolley_Mould_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("statusstr",statusstr);
        request.addProperty("po_Number",po_Number);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取台车模具分离详细信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Trolley_MouldView_Json(string p_Tid_m, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_Trolley_MouldView_Json(final Context mContext, final SuccessCall call,String p_Tid_m)
    {
        String methodName="Get_Production_order_Trolley_MouldView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Tid_m",p_Tid_m);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：获取台车模具分离编号
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Trolley_Mould_po_Number(int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_Trolley_Mould_po_Number(final Context mContext, final SuccessCall call)
    {
        String methodName="Get_Production_order_Trolley_Mould_po_Number";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：根据台车二维码获取台车信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     B_Get_Trolley_Man(string Tid, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription B_Get_Trolley_Man(final Context mContext, final SuccessCall call,String Tid)
    {
        String methodName="B_Get_Trolley_Man";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("Tid",Tid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：根据模具二维码获取模具信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     B_Get_Mould_Man(string Moid, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription B_Get_Mould_Man(final Context mContext, final SuccessCall call,String Moid)
    {
        String methodName="B_Get_Mould_Man";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("Moid",Moid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     接口名称：根据台车ID和模具ID获取可分离的信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Trolley_Mould_bypMoid_Json(string Tid,string Moid, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_Trolley_Mould_bypMoid_Json(final Context mContext, final SuccessCall call, String Tid,String Moid)
    {
        String methodName="Get_Production_order_Trolley_Mould_bypMoid_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("Tid",Tid);
        request.addProperty("Moid",Moid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：新增台车模具分离信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Trolley_Mould_Add(string billtablestr, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Trolley_Mould_Add(final Context mContext, final SuccessCall call,String[][] billtablestr)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String methodName="Get_Production_order_Trolley_Mould_Add";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：删除台车模具分离
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Trolley_Mould_Del(string p_Tid_m, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Trolley_Mould_Del(final Context mContext, final SuccessCall call,String p_Tid_m)
    {
        String methodName="Get_Production_order_Trolley_Mould_Del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Tid_m",p_Tid_m);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 装柜入库单
     接口名称：获取装柜入库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Container_Json(int rowcount, string statusstr, string po_Number, int gsmid, int property, bool IsSubtitle, string username)
     */
    public static Subscription Get_Production_order_Container_Json(final Context mContext, final SuccessCall call,int rowcount, String statusstr, String po_Number)
    {
        String methodName="Get_Production_order_Container_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("statusstr",statusstr);
        request.addProperty("po_Number",po_Number);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：获取装柜入库详细信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_ContainerView_Json(string p_Cid, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_ContainerView_Json(final Context mContext, final SuccessCall call,String p_Cid)
    {
        String methodName="Get_Production_order_ContainerView_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Cid",p_Cid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：获取装柜入库编号
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Container_po_Number(int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_Container_po_Number(final Context mContext, final SuccessCall call)
    {
        String methodName="Get_Production_order_Container_po_Number";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：可装柜入库得销售订单选择信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Container_soid_Json(int rowcount, string po_Number, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_Container_soid_Json(final Context mContext, final SuccessCall call, int rowcount,String po_Number)
    {
        String methodName="Get_Production_order_Container_soid_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("rowcount",rowcount);
        request.addProperty("po_Number",po_Number);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     接口名称：根据销售订单ID获取可装柜信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Container_bysoid_Json(string soid, int gsmid, int property, bool IsSubtitle)
     */
    public static Subscription Get_Production_order_Container_bysoid_Json(final Context mContext, final SuccessCall call, String soid)
    {
        String methodName="Get_Production_order_Container_bysoid_Json";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("soid",soid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        return getNetworkServiceNoProgress(mContext,call,request);
    }

    /**
     * 接口名称：新增装柜入库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Container_Add(string billtablestr, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Container_Add(final Context mContext, final SuccessCall call,String[][] billtablestr)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String methodName="Get_Production_order_Container_Add";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：修改装柜入库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Container_Eedit(string billtablestr, string p_Cid, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Container_Eedit(final Context mContext, final SuccessCall call,String[][] billtablestr,String p_Cid)
    {

        String s1=null;
        try {
            JSONArray jsonArray = new JSONArray(billtablestr);
            s1 = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String methodName="Get_Production_order_Container_Eedit";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("billtablestr",s1);
        request.addProperty("p_Cid",p_Cid);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：删除装柜入库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Container_Del(string p_Cid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Container_Del(final Context mContext, final SuccessCall call,String p_Cid )
    {
        String methodName="Get_Production_order_Container_Del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Cid",p_Cid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：装柜入库-货柜调拨
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Container_Container_Db(string p_Cid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Container_Container_Db(final Context mContext, final SuccessCall call,String p_Cid )
    {
        String methodName="Get_Production_order_Container_Container_Db";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Cid",p_Cid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：审核装柜入库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Container_sh(string p_Cid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Container_sh(final Context mContext, final SuccessCall call,String p_Cid)
    {
        String methodName="Get_Production_order_Container_sh";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Cid",p_Cid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }

    /**
     * 接口名称：反审核装柜入库信息
     接口地址：http://192.168.11.96:7785/Service1.asmx
     接口函数：
     Get_Production_order_Container_shf(string p_Cid, string addPer, int gsmid, int property, bool IsSubtitle, string Keystring, string KeysTimetring, string username)
     */
    public static Subscription Get_Production_order_Container_shf(final Context mContext, final SuccessCall call,String p_Cid)
    {
        String methodName="Get_Production_order_Container_shf";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("p_Cid",p_Cid);
        request.addProperty("addPer", App.menname);
        request.addProperty("gsmid",App.GSMID);
        request.addProperty("property",App.Property);
        request.addProperty("IsSubtitle",App.IsSub);
        request.addProperty("Keystring",App.MobileKey);
        request.addProperty("KeysTimetring",App.KeyTimestring);
        request.addProperty("username",App.USER_INFO);
        return getNetworkService(mContext,call,request);
    }



    /**
     * //小型的接口
     * @param mContext
     * @param call
     * @param request
     * @return
     */
    public static Subscription getNetworkService(final Context mContext, final SuccessCall call,final SoapObject request){

        Subscription networkService = getNetworkService(new BHBaseSubscriber<String>(new RequestError() {
            @Override
            public void forRequestError(String msg) {
                T.showShort(mContext, msg);
                call.ErrorBack(msg);
                ProgressUtils.getInstance().DisMissProgress();
            }
        }) {
            @Override
            public void onStart() {
                super.onStart();
                ProgressUtils.getInstance().ShowProgress(mContext);
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                ProgressUtils.getInstance().DisMissProgress();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(String o) {
                super.onNext(o);
                call.SuccessBack(o);
            }
        }, request, (RxAppCompatActivity) mContext);
//        RxSubscriptionManager.getInstanse().addRxSubscription(networkService);
        return networkService;
    }

     //联合请求
    //根据要先请求 KeyString 得到KeyTimeString

    public static void SettingsKeyNetWork(ObjectSuccessCall call) {
        Observable observableMobileKey = ApiWebService.Get_KeyTimestr(App.MobileKey);
        observableMobileKey.subscribe(new BHBaseSubscriber<Object>(new RequestError() {
            @Override
            public void forRequestError(String msg) {
                call.ErrorBack(msg);
            }
        }) {
            @Override
            public void onStart() {
                super.onStart();

            }

            @Override
            public void onCompleted() {
                super.onCompleted();

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);

            }

            @Override
            public void onNext(Object o) {
                super.onNext(o);
                App.KeyTimestring= o.toString();
                call.SuccessBack(o);
            }
        });
    }

//大型的接口 可以接这个
    private static  Subscription getNetworkService(Subscriber mSubscriber, final SoapObject request,RxAppCompatActivity context){
        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(final Subscriber<? super Object> subscriber) {

                if (!subscriber.isUnsubscribed()) {
                        subscriber.onStart();
                    Object response = connectService(request);
                    if (response instanceof  SoapPrimitive||response instanceof  SoapObject)
                    {
                        String s = response.toString();
                        subscriber.onNext(s);
                        subscriber.onCompleted();
                    }else if (response instanceof Throwable)
                    {
                        subscriber.onError((Throwable)response);
                    }else{
                        subscriber.onCompleted();
                    }

                }
            }}).compose(context.<Object>bindToLifecycle())
                .subscribeOn(Schedulers.io()).

            observeOn(AndroidSchedulers.mainThread()).subscribe(mSubscriber);
        }



    //小型的接口(去dialog) 为多接口处理数据，回调处理  返回的是Object类型
    public static Subscriber getMutiCallback(final Context mContext, final ObjectSuccessCall call)
    {
        return new BHBaseSubscriber<Object>(new RequestError() {
            @Override
            public void forRequestError(String msg) {

            }
        }) {
            @Override
            public void onStart() {
                super.onStart();
                ProgressUtils.getInstance().ShowProgress(mContext);
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                ProgressUtils.getInstance().DisMissProgress();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                L.v(tag, "", (Throwable) e);
                T.showShort(mContext, e.getLocalizedMessage());
//                call.ErrorBack(e);
                ProgressUtils.getInstance().DisMissProgress();
            }

            @Override
            public void onNext(Object o) {
                super.onNext(o);
                call.SuccessBack(o);
            }
        };
    }

    public static Subscription getNetworkServiceNoProgress(final Context mContext, final SuccessCall call,final SoapObject request)
    {
        Subscription networkService = getNetworkService(new BHBaseSubscriber<String>(new RequestError() {
            @Override
            public void forRequestError(String msg) {
                T.showShort(mContext, msg);
                call.ErrorBack(msg);
            }
        }) {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(String o) {
                super.onNext(o);
                call.SuccessBack(o);
            }
        }, request, (RxAppCompatActivity) mContext);
//        RxSubscriptionManager.getInstanse().addRxSubscription(networkService);
        return networkService;
    }


    private static Object connectService(final SoapObject request) {
        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER12);

            envelope.bodyOut = request;//由于是发送请求，所以是设置bodyOut
            envelope.dotNet = true;//由于是.net开发的webservice，所以这里要设置为true

            HttpTransportSE httpTransportSE = new HttpTransportSE(WSDL_URL);
            httpTransportSE.call(null, envelope);//调用
            if (envelope.getResponse() instanceof SoapPrimitive) {
                SoapPrimitive response1 = (SoapPrimitive) envelope.getResponse();
                KLog.e(response1.toString());
                return response1;
//            subscriber.onNext(s);
            }
            else if (envelope.getResponse() instanceof SoapObject) {
                SoapObject response1 = (SoapObject) envelope.getResponse();
                KLog.e(response1.toString());
                return response1;
//            subscriber.onNext(s);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            Throwable cause = (Throwable) e;
            return cause;
//        subscriber.onError(cause);
        }
        return null;
    }

    /**
     * 多个接口的联合使用
     */
    private static Observable getMutiNetworkService(final SoapObject request) {
        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(final Subscriber<? super Object> subscriber) {

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onStart();
                    Object response = connectService(request);
                    if (response instanceof SoapPrimitive || response instanceof SoapObject) {
                        String s = response.toString();
                        subscriber.onNext(s);
                        subscriber.onCompleted();
                    }
                    else if (response instanceof Throwable) {
                        subscriber.onError((Throwable) response);
                    }else {
                        subscriber.onCompleted();
                    }

                }
            }
        }).
                subscribeOn(Schedulers.io()).

                observeOn(AndroidSchedulers.mainThread());
    }



    public interface SuccessCall{
            void SuccessBack(String result);
            void ErrorBack(String error);
    }

    public interface ObjectSuccessCall{
        void SuccessBack(Object result);
        void ErrorBack(Object error);
    }

}
