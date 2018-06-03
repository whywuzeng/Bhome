package com.system.bhouse.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
/**
 * Created by Administrator on 2016/3/10.
 */
public class sharedpreferencesuser {
    private static Context context;
    private static  SharedPreferences user;
    public static String TASKDATA="taskdata";
    private final static String USERUNIT="userunit";

    public static void putUsername(Context context,String username){
        user = context.getSharedPreferences("user", Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = user.edit();
        edit.putString("username",username);
        edit.commit();
    }

    public static String getUsername(Context context){
        user = context.getSharedPreferences("user", Activity.MODE_PRIVATE);
        String username = user.getString("username", "");
        return username;
    }

    public static void putUserpassword(Context context,String password){
        user=context.getSharedPreferences("user",Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=user.edit();
        editor.putString("password",password);
        editor.commit();
    }

    public static String getUserpassword(Context context){
        user=context.getSharedPreferences("user", Activity.MODE_PRIVATE);
        String password = user.getString("password", "");
        return password;
    }

    public static void putUserdomain(Context context,String domain) {
        user = context.getSharedPreferences("user", Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = user.edit();
        edit.putString("domain", domain);
        edit.commit();
    }

    //192.168.11.96:7785  http://maisweb.bhome.com.cn/Service1.asmx
    public static String getUserdomain(Context context){
        user=context.getSharedPreferences("user", Activity.MODE_PRIVATE);
        String domain = user.getString("domain", "maisweb.bhome.com.cn");
        return domain;
    }

    public static void putAvator_filefloder(Context context,String avator) {
        user = context.getSharedPreferences("user", Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = user.edit();
        edit.putString("avator", avator);
        edit.commit();
    }


    public static String getAvator_filefloder(Context context)
    {
        user= context.getSharedPreferences("user",Activity.MODE_PRIVATE);
        String avator = user.getString("avator", "");
        return avator;
    }

    public static void putVersionCode(Context context,String versioncode){
        user = context.getSharedPreferences("user", Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = user.edit();
        edit.putString("versioncode", versioncode);
        edit.commit();
    }

    public static String getVersionCode(Context context){
        user=context.getSharedPreferences("user", Activity.MODE_PRIVATE);
        String domain = user.getString("versioncode","0");
        return domain;
    }

    public static void putApkUrl(Context context,String versioncode){
        user = context.getSharedPreferences("user", Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = user.edit();
        edit.putString("apkurl", versioncode);
        edit.commit();
    }

    public static String getApkUrl(Context context){
        user=context.getSharedPreferences("user", Activity.MODE_PRIVATE);
        String domain = user.getString("apkurl",null);
        return domain;
    }

    public static void putVerFilePath(Context context,String versioncode){
        user = context.getSharedPreferences("user", Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = user.edit();
        edit.putString("VerFilePath", versioncode);
        edit.commit();
    }

    public static String getVerFilePath(Context context){
        user=context.getSharedPreferences("user", Activity.MODE_PRIVATE);
        String domain = user.getString("VerFilePath",null);
        return domain;
    }

    //初始化 context sharepreferences的存取空间
    private static void initSharedUserUnit()
    {
        user=context.getSharedPreferences(USERUNIT,Context.MODE_PRIVATE);
    }


    /**
     * 存取userinfo
     * @param userInfo
     */
    public static void putUserInfo(String userInfo){
        initSharedUserUnit();
        SharedPreferences.Editor edit = user.edit();
        edit.putString("userInfo",userInfo);
        edit.commit();
    }

    public static String getUserInfo()
    {
        initSharedUserUnit();
        return user.getString("userInfo","");
    }

    /**
     * 存取mid
     * @param userInfo
     */

    public static void putMid(Integer mid) {
        initSharedUserUnit();
        SharedPreferences.Editor edit = user.edit();
        edit.putInt("Mid", mid);
        edit.commit();
    }

    public static int getMid() {
        initSharedUserUnit();
        return user.getInt("Mid", 0);
    }

    /**
     * 存取MobileKey
     * @param userInfo
     */
    public static void putMobileKey(String mobileKey)
    {
        initSharedUserUnit();
        SharedPreferences.Editor edit = user.edit();
        edit.putString("mobileKey",mobileKey);
        edit.commit();
    }

    public static String getMobileKey()
    {
        initSharedUserUnit();
        return user.getString("mobileKey","");
    }

    /**
     * 存取GSMID
     * @param userInfo
     */
    public static void putGSMID(String GSMID)
    {
        initSharedUserUnit();
        SharedPreferences.Editor edit = user.edit();
        edit.putString("GSMID",GSMID);
        edit.commit();
    }

    public static String getGSMID()
    {
        initSharedUserUnit();
        return user.getString("GSMID","");
    }

    /**
     * 存取Property
     */
    public static void putProperty(String Property)
    {
        initSharedUserUnit();
        SharedPreferences.Editor edit = user.edit();
        edit.putString("Property",Property);
        edit.commit();
    }

    public static String getProperty()
    {
        initSharedUserUnit();
        return user.getString("Property","");
    }

    /**
     * 存取IsSub
     */
    public static void putIsSub(String IsSub)
    {
        initSharedUserUnit();
        SharedPreferences.Editor edit = user.edit();
        edit.putString("IsSub",IsSub);
        edit.commit();
    }

    public static String getIsSub()
    {
        initSharedUserUnit();
        return user.getString("IsSub","");
    }





//    public static void  putApplications(Context context,ArrayList<FunctionBean> functionBeens )
//    {
//        user= context.getSharedPreferences("user", Activity.MODE_PRIVATE);
//        // 创建字节输出流
//        String SceneListString="";
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        try {
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
//            objectOutputStream.writeObject(functionBeens);
//
//             SceneListString  = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
//            objectOutputStream.close();
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        SharedPreferences.Editor edit = user.edit();
//
//        edit.putString("ApplicationData",SceneListString);
//        edit.commit();
//    }

//    public static ArrayList<FunctionBean> getApplications(Context context)
//    {
//        user=context.getSharedPreferences("user", Activity.MODE_PRIVATE);
//        try {
//        String domain = user.getString("ApplicationData",null);
//        byte[] mobileBytes = Base64.decode(domain.getBytes(),
//                Base64.DEFAULT);
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
//                mobileBytes);
//        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
//
//            ArrayList<FunctionBean> SceneList = (ArrayList<FunctionBean>) objectInputStream
//                .readObject();
//        objectInputStream.close();
//
//            return SceneList;
//        } catch (StreamCorruptedException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }

}
