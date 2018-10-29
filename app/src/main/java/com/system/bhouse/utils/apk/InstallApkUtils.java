package com.system.bhouse.utils.apk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.system.bhouse.utils.IntentUtils;

import java.io.File;

/**
 * Created by Administrator on 2018-10-09.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.utils.apk
 */

public class InstallApkUtils {

    /**
     * 打开apk启动安装，并在安装完成后自动打开
     *
     * activity.getFilesDir() --- /data/user/0/com.system.bhouse.bhouse/files
     * activity.getCacheDir() --- /data/user/0/com.system.bhouse.bhouse/cache/HttpCache
     *  Environment.getExternalStorageDirectory()   ----/storage/emulated/0
     *
     */
    public static void installApk(String savePath, Context mContext) {
        File apkfile = new File(savePath);
        if (!apkfile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//安装完成后自动打开
        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= 24) {
            intent=IntentUtils.getApk7FileIntent(apkfile,mContext);
        } else {
//            intent.setDataAndType(Uri.parse("file://" + apkfile.toString()),
//                    "application/vnd.android.package-archive");// File.toString()会返回路径信息
            intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
        }
        mContext.startActivity(intent);
    }

}
