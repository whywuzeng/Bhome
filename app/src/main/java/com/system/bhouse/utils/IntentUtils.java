package com.system.bhouse.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by Administrator on 2016-5-31.
 * ClassName: com.system.bhouse.utils
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class IntentUtils {
   public static Intent startUriLink(String link) {
        Uri uri = Uri.parse(link);
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static boolean checkResolveIntent(Activity activity, Intent intent) {
        //只有当检查出能够接受intent的对象不为空 返回true
        return intent.resolveActivity(activity.getPackageManager()) != null;
    }

    //Android获取一个用于打开图片文件的intent
    public static Intent startImageFile(File file,String type) {
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, type);
        return intent;
    }

    //android获取一个用于打开视频文件的intent
    public static Intent getVideoFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    //android获取一个用于打开apk文件的intent
    public static Intent getApkFileIntent(File file)
    {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),  "application/vnd.android.package-archive");
        return intent;
    }

    public static Intent getApk7FileIntent(File file, Context context)
    {//content://com.system.bhouse.bhouse.fileprovider/download/DCIM/bhouseDownoad/1540801209129.apk
        //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
        Uri apkUri =
                FileProvider.getUriForFile(context, "com.system.bhouse.bhouse.fileprovider", file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //添加这一句表示对目标应用临时授权该Uri所代表的文件
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        return intent;
    }
}
