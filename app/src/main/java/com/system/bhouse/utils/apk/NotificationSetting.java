package com.system.bhouse.utils.apk;

import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import com.system.bhouse.utils.blankutils.ToastUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Administrator on 2018-10-29.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.utils.apk
 */

public class NotificationSetting {


    
    private static final String TAG = "NotificationSetting";
    private static Context mContext;

    //检测通知开关 针对8.0之前设备，通过AppOpsManager的checkOpNoThrow方法获取。
    private static boolean isEnableV19(Context context, String pkg, int uid) {
        try {
            String CHECK_OP_NO_THROW = "checkOpNoThrow";
            String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
            Class appOpsClass = null;
            AppOpsManager mAppOps = null;
            mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE,
                    Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) ==
                    AppOpsManager.MODE_ALLOWED);
        } catch (Exception e) {
            return true;
        }
    }

    //2.针对8.0及以上设备，发现上述方式不生效
    private static boolean isEnableV26(Context context, String pkg, int uid) {
        try {
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            Method sServiceField = notificationManager.getClass().getDeclaredMethod("getService");
            sServiceField.setAccessible(true);
            Object sService = sServiceField.invoke(notificationManager);

            Method method = sService.getClass().getDeclaredMethod("areNotificationsEnabledForPackage"
                    , String.class, Integer.TYPE);
            method.setAccessible(true);
            return (boolean) method.invoke(sService, pkg, uid);
        } catch (Exception e) {
            return true;
        }
    }

    public static void checkNotificationSetting(Context context){
        mContext =context;
        boolean isEnable;
        if (Build.VERSION.SDK_INT >=26)
        {
            isEnable= isEnableV26(context,mContext.getPackageName(),getUID());
        }else {
            isEnable= isEnableV19(context,mContext.getPackageName(),getUID());
        }
        if (!isEnable)
        {
            toSetting();
        }
    }

    //后去uid
    private static int getUID(){
        int uid=-1;
        final PackageManager pm = mContext.getPackageManager();
        List<PackageInfo> packinfos = pm
                .getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES
                        | PackageManager.GET_PERMISSIONS);
        for (PackageInfo info : packinfos) {

             uid = info.applicationInfo.uid;

            String packageName = pm.getNameForUid(uid);
            if (packageName.equalsIgnoreCase(mContext.getPackageName()))
            {
                return uid;
            }

            String name = pm.getApplicationLabel(info.applicationInfo).toString();

        }
       return uid;
    }

    //打开通知开关

    private static void toSetting() {
        ToastUtils.showShort("请设置通知管理为:允许通知");
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", mContext.getPackageName());
        }
        mContext.startActivity(localIntent);
    }

}
