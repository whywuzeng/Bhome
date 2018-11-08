package com.system.bhouse.utils;

import android.app.Application;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.system.bhouse.bhouse.R;

import java.io.File;

/**
 * Created by Administrator on 2016-5-31.
 * ClassName: com.system.bhouse.utils
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class NotificationUtils {
    //默认的图标
    private static final int resId= R.mipmap.mais;

    //默认使用应用图标的通知栏
    public static Notification showNotification(
            Application application, String title, String text) {
        return showNotification(application, resId, title, text);
    }

    public static Notification showNotification(
            Application application, int resIdIcon, String title, String text) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(application)
                .setSmallIcon(resIdIcon)//图标
                .setContentTitle(title)//标题
                .setContentText(text)//正文描述
                .setTicker(text);//滚动文字
        return mBuilder.build();
    }

    //默认方法
    public static Notification showProcessNotification(
            Application application, String title, int process) {
        return showProcessNotification(application, resId, title, process);
    }


    public static Notification showProcessNotification(
            Application application, int resIdIcon, String title, int process) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(application)
                .setSmallIcon(resIdIcon)//图标
                .setContentTitle(title)//标题
                .setProgress(100, process, false)
                ;
        return mBuilder.build();
    }


    public static Notification showIntentNotification(
            Application application, File file, String type, String title, String text) {
        return showIntentNotification(application,resId,file,type,title,text);
    }

    public static Notification showIntentNotification(
            Application application, int resId, File file, String type, String title, String text) {
        Intent intent=null;
        if(type!=null&&type.contains("mp4")) {
             intent = IntentUtils.getVideoFileIntent(file);//使用工具类 包装打开文件的intent
        }else if(type!=null&&(type.contains("jpeg")||type.contains("png"))){
            intent = IntentUtils.startImageFile(file,type);
        }else if(type!=null&&type.contains("apk")){
            if (Build.VERSION.SDK_INT<24) {
                intent = IntentUtils.getApkFileIntent(file);
            }else {
                intent = IntentUtils.getApk7FileIntent(file,application);
            }
        }

        //点击安装apk 这要改成  file:///storage/emulated/0/DCIM/bhouseDownoad/1540795217514.apk

        PendingIntent pendingIntent = PendingIntent.getActivity(application, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(application)
                .setSmallIcon(resId)//图标
                .setContentTitle(title)
                .setContentText(text)
                .setTicker(text)
                .setContentIntent(pendingIntent)
//                .setAutoCancel(true)//点击之后 自动消失
                ;
        return mBuilder.build();
    }


    private void showNotification(int id, String contentText, String contentTitle, String ticker) {
//        Uri uri = Uri.parse("http://www.pixiv.net/member_illust.php?mode=big&illust_id=1636887");
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        Logger.d(pendingIntent.toString());
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
//                .setSmallIcon(R.drawable.ic_launcher)
//                .setContentText(contentText)
//                .setContentTitle(contentTitle)
//                .setTicker(ticker)
//                .setAutoCancel(true);
//
//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        int incr;
//                        // Do the "lengthy" operation 20 times
//                        for (incr = 0; incr <= 100; incr += 10) {
//                            // Sets the progress indicator to a max value, the
//                            // current completion percentage, and "determinate"
//                            // state
//                            mBuilder.setProgress(100, incr, false);
//                            // Displays the progress bar for the first time.
//                            mNontificationManager.notify(id, mBuilder.build());
//                            // Sleeps the thread, simulating an operation
//                            // that takes time
//                            try {
//                                // Sleep for 5 seconds
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                Logger.d("sleep failure");
//                            }
//                        }
//                        // When the loop is finished, updates the notification
//                        mBuilder.setContentText("Download complete")
//                                // Removes the progress bar
//                                .setProgress(0, 0, false)
//                                .setContentIntent(pendingIntent);
//                        Logger.d(Thread.currentThread().toString());
//                        mNontificationManager.notify(id, mBuilder.build());
//                    }
//                }
//// Starts the thread by calling the run() method in its Runnable
//        ).start();
////        mNontificationManager.notify(id, mBuilder.build());
    }
}
