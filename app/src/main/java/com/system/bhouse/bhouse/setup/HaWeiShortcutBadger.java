package com.system.bhouse.bhouse.setup;

import android.content.ContentResolver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.system.bhouse.base.App;

/**
 * Created by Administrator on 2018-06-06.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.setup
 */

public class HaWeiShortcutBadger {


    //检测EMUI版本是否支持
    public static boolean checkIsSupportedByVersion(){
        try {
            PackageManager manager = App.getContextApp().getPackageManager();
            PackageInfo info = manager.getPackageInfo("com.huawei.android.launcher", 0);
            if(info.versionCode>=63029){
                return  true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //控制显示的个数
    public static void handleBadge(int num){
        if(!checkIsSupportedByVersion()){
            Log.i("badgedemo", "not supported badge!");
            return;
        }
        try{
            Bundle bunlde =new Bundle();
            bunlde.putString("package", "com.system.bhouse.bhouse");
            bunlde.putString("class", "com.system.bhouse.bhouse.WelcomActivity");
            bunlde.putInt("badgenumber",num);
            ContentResolver t=App.getContextApp().getContentResolver();
            Bundle result=t.call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, bunlde);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
