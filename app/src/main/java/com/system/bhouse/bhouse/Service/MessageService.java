package com.system.bhouse.bhouse.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.system.bhouse.bhouse.MainActivity;
import com.system.bhouse.bhouse.R;

/**
 * Created by Administrator on 2016-4-14.
 * ClassName: com.system.bhouse.bhouse.Service
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class MessageService extends Service{

    //获取消息线程
    private MessageThread messageThread = null;

    //点击查看
    private Intent messageIntent = null;
    private PendingIntent messagePendingIntent = null;

    //通知栏消息
    private int messageNotificationID = 1000;
    private Notification.Builder messageNotification = null;
    private NotificationManager messageNotificatioManager = null;


    private myBinder binder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        binder=new myBinder();
        return binder;
    }

    class myBinder extends Binder {

    }

    @Override
    public void onCreate() {
        //初始化
        //点击跳转的activity
        messageIntent = new Intent(this, MainActivity.class);
        messagePendingIntent = PendingIntent.getActivity(this,0,messageIntent,0);

        messageNotification = new Notification.Builder(this);
        messageNotification.setContentIntent(messagePendingIntent);
        messageNotification.setSmallIcon(R.mipmap.ic_launcher);
        messageNotification.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        messageNotification.setAutoCancel(true);
        messageNotification.setContentTitle("普通通知");
        messageNotification.setContentText("ContentText标题");
        messageNotification.setSubText("SubText标题");
        messageNotification.setTicker("新消息咯");
        messageNotificatioManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //开启线程
        messageThread = new MessageThread();
        messageThread.isRunning = true;
        messageThread.start();
        super.onCreate();
    }


    /**
     * 从服务器端获取消息
     *
     */
    class MessageThread extends Thread{
        //运行状态，下一步骤有大用
        public boolean isRunning = true;
        public void run() {
            while(isRunning){
                try {
                    //获取服务器消息
                    String serverMessage = getServerMessage();

                    if(serverMessage!=null&&!"".equals(serverMessage)){
                        //更新通知栏
                        messageNotificatioManager.notify(messageNotificationID, messageNotification.build());
                        //每次通知完，通知ID递增一下，避免消息覆盖掉
                        messageNotificationID++;
                    }
                    //休息10分钟
                    Thread.sleep(50000*60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

//            TimerTask task=new TimerTask() {
//                @Override
//                public void run() {
//
//                }
//            };
//            new Timer().schedule(task,1000,30*60*1000);
        }
    }
    @Override
    public void onDestroy() {
        //  System.exit(0);
        //或者，二选一，推荐使用System.exit(0)，这样进程退出的更干净
        messageThread.isRunning = false;
        super.onDestroy();
    }
    /**
     * 这里以此方法为服务器Demo，仅作示例
     * @return 返回服务器要推送的消息，否则如果为空的话，不推送
     */
    public String getServerMessage(){
        return "YES!";
    }

}
