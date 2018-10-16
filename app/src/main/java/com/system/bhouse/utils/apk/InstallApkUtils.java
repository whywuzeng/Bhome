package com.system.bhouse.utils.apk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

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
            //AndroidManifest provider authorities
            Uri apkUri = FileProvider.getUriForFile(mContext, "com.bhome.fileprovider", apkfile);
            //Granting Temporary Permissions to a URI
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//对目标应用临时授权该Uri所代表的文件
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
//            intent.setDataAndType(Uri.parse("file://" + apkfile.toString()),
//                    "application/vnd.android.package-archive");// File.toString()会返回路径信息
            intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
        }
        mContext.startActivity(intent);
    }

}
