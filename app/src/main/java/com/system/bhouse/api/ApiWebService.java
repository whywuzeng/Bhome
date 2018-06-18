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
        return getNetworkService(mSubscriber,request);
    }

/*    15.根据订单ID获取明细信息
    http://192.168.12.151:8070/Getcuscontractbyorderid.aspx?ccid=87*/
    public static Subscription getDingdandetails(Subscriber mSubscriber,int ccid)
    {
        String methodName="Getcuscontractbyorderid"; //(用户密码验证)
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        //integer类型
        request.addProperty("ccid",ccid);
        return getNetworkService(mSubscriber,request);
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
        return getNetworkService(mSubscriber,request);
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
    public static Subscription getLoginMsg(final Context mContext, final SuccessCall call,String admin,String password){
        setUserMaindomain();
        String methodName="Getlogin_Json"; //(用户密码验证)
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("name",admin);
        request.addProperty("pass",password);
       return getNetworkServiceNoProgress(mContext,call,request);
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

    public static Subscription getGetWorkflowlistMessagedt(Subscriber mSubscriber,String status, int rownum, String username, String FormNumber)
    {
            String methodName="GetWorkflowlistMessagedt";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("status",status);
        request.addProperty("rownum",rownum);
        request.addProperty("username",username);
        request.addProperty("FormNumber",FormNumber);
        return getNetworkService(mSubscriber,request);
    }

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
    public static Subscription Getlogin_regok_Json(final Context mContext, final SuccessCall call,String CpuID, String MacAddress, String IpAddress, String DiskID, String TotalPhysicalMemory)
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
        return getNetworkService(mContext,call,request);
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
        return getNetworkServiceNoProgress(mContext,call,request);
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
        return getNetworkServiceNoProgress(mContext,call,request);
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
        return getNetworkServiceNoProgress(mContext,call,request);
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
        return getNetworkServiceNoProgress(mContext,call,request);
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
        return getNetworkServiceNoProgress(mContext,call,request);
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
        return getNetworkServiceNoProgress(mContext,call,request);
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
        return getNetworkServiceNoProgress(mContext,call,request);
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
        return getNetworkServiceNoProgress(mContext,call,request);
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
        return getNetworkServiceNoProgress(mContext,call,request);
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
    public static Subscription Get_Sale_Order_Car_Container_Del(final Context mContext, final SuccessCall call,String sccid,String addPer)
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
    public static Subscription Get_Sale_Order_Car_Container_sh(final Context mContext, final SuccessCall call,String sccid,String addPer)
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
    public static Subscription Get_Sale_Order_Car_Container_shf(final Context mContext, final SuccessCall call,String sccid,String addPer)
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
        request.addProperty("so_Number",po_Number);
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
        request.addProperty("sccid",poid);
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
    public static Subscription Get_Production_order_sh(final Context mContext, final SuccessCall call,String poid,String addPer)
    {
        String methodName="Get_Production_order_sh";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("sccid",poid);
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
    public static Subscription Get_Production_order_shf(final Context mContext, final SuccessCall call,String poid,String addPer)
    {
        String methodName="Get_Production_order_shf";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("sccid",poid);
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
    public static Subscription Get_Production_order_Del(final Context mContext, final SuccessCall call,String poid,String addPer)
    {
        String methodName="Get_Production_order_Del";
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("sccid",poid);
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


    /**
     * //小型的接口
     * @param mContext
     * @param call
     * @param request
     * @return
     */
    public static Subscription getNetworkService(final Context mContext, final SuccessCall call,final SoapObject request){

        return getNetworkService(new BHBaseSubscriber<String>(new RequestError() {
            @Override
            public void forRequestError(String msg) {
                T.showShort(mContext, msg);
                call.ErrorBack(msg);
                ProgressUtils.getInstance().DisMissProgress();
            }
        }){
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
        },request);
    }


//大型的接口 可以接这个
    private static  Subscription getNetworkService(Subscriber mSubscriber, final SoapObject request){
        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(final Subscriber<? super Object> subscriber) {

                if (!subscriber.isUnsubscribed()) {
                        subscriber.onStart();
//                        String methodName="Getlogin"; //(用户密码验证)
//                        SoapObject request = new SoapObject(NAMESPACE, methodName);
//                        request.addProperty("name","admin");
//                        request.addProperty("pass","19900927");
                        //创建SoapSerializationEnvelope 对象，同时指定soap版本号(之前在wsdl中看到的)
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
            }}).
            subscribeOn(Schedulers.io()).

            observeOn(AndroidSchedulers.mainThread()).subscribe(mSubscriber);
        }



    //小型的接口(去dialog) 为多接口处理数据，回调处理  返回的是Object类型
    public static Subscriber getMutiCallback(final Context mContext, final ObjectSuccessCall call)
    {
        return new Subscriber() {
            @Override
            public void onStart() {
                super.onStart();
                ProgressUtils.getInstance().ShowProgress(mContext);
            }

            @Override
            public void onCompleted() {
                ProgressUtils.getInstance().DisMissProgress();
            }

            @Override
            public void onError(Throwable e) {
                L.v(tag,"",(Throwable) e);
                T.showShort(mContext,e.getLocalizedMessage());
//                call.ErrorBack(e);
                ProgressUtils.getInstance().DisMissProgress();
            }

            @Override
            public void onNext(Object o) {
                call.SuccessBack(o);
            }
        };
    }

    public static Subscription getNetworkServiceNoProgress(final Context mContext, final SuccessCall call,final SoapObject request)
    {
        return getNetworkService(new BHBaseSubscriber<String>(new RequestError() {
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
        },request);
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
//                        String methodName="Getlogin"; //(用户密码验证)
//                        SoapObject request = new SoapObject(NAMESPACE, methodName);
//                        request.addProperty("name","admin");
//                        request.addProperty("pass","19900927");
                    //创建SoapSerializationEnvelope 对象，同时指定soap版本号(之前在wsdl中看到的)
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