package com.system.bhouse.bhouse.Service;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import com.socks.library.KLog;
import com.system.bhouse.api.ApiServiceUtils;
import com.system.bhouse.base.DownloadInfo;
import com.system.bhouse.api.OnProgressResponseListener;
import com.system.bhouse.utils.FileUtils;
import com.system.bhouse.utils.NotificationUtils;
import com.system.bhouse.utils.PicUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016-5-31.
 * ClassName: com.system.bhouse.bhouse.Service
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class DownloadService extends IntentService {
    private static final String TAG = "DownloadService";
    private static final String KEYURL = "KeyUrl";
    private static final String KEYTYPE = "KeyType";

    private static final int MSG_START = 0;
    private static final int MSG_LOADING = 1;
    private static final int MSG_COMPLETE = 2;

    private static final OnProgressResponseListener mListener = new OnProgressResponseListener() {
        @Override
        public void onResponseProgress(long bytesRead, long contentLength, boolean done) {
            KLog.d(Thread.currentThread().toString() + " " + bytesRead + "/" + contentLength + " done=" + done);
            mDownloadInfo.mProcess = (int) (bytesRead * 100 / contentLength);
        }
    };

    private long mDelayedTime = 1000;

    private Map<String, DownloadInfo> mMap = new HashMap<>();

    private static DownloadInfo mDownloadInfo;

    private NotifyHandler mNotifyHandler;

    private HandlerThread mHandlerThread;

    //// TODO: 2016/5/18 0018 目前存在内存泄露状态 因为OnProgressResponseListener在Service中实例化 然后弃用 但是okhttp的生命周期比较长
    //哪为什么不在 Handler中实例化 然后和线程一起弃用？
    private final class NotifyHandler extends Handler {
        //下载操作不频繁 可以当做类变量 使用时候再创建
        private NotificationManager mNotificationManager;

        public NotifyHandler(Looper looper) {
            super(looper);
            mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }

        @Override
        public void handleMessage(Message msg) {
            DownloadInfo downloadInfo = (DownloadInfo) msg.obj;
            int notifyId = downloadInfo.mWorkId;
            Notification notification;
            KLog.d("what=" + msg.what + " obj=" + downloadInfo.toString());
            switch (msg.what) {
                case MSG_START:
                    notification = NotificationUtils.showNotification(
                            getApplication(),
                            downloadInfo.fileName,
                            downloadInfo.mState
                    );
                    break;
                case MSG_LOADING:
                    notification = NotificationUtils.showProcessNotification
                            (getApplication(), downloadInfo.fileName, downloadInfo.mProcess);
                    break;
                case MSG_COMPLETE:
                    notification = NotificationUtils.showIntentNotification(
                            getApplication(),
                            downloadInfo.mFile,
                            downloadInfo.mMediaType,
                            downloadInfo.fileName,
                            downloadInfo.mState
                    );

                    break;
                default:
                    notification = NotificationUtils.showNotification(
                            getApplication(),
                            "错误",
                            "无法处理接受的消息"
                    );
                    break;
            }
            mNotificationManager.notify(notifyId, notification);
        }
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            KLog.d(Thread.currentThread().toString() + " mProcess=" + mDownloadInfo.mProcess);
            Message message = mNotifyHandler.obtainMessage();
            message.what = MSG_LOADING;
            message.obj = mDownloadInfo;
            mNotifyHandler.sendMessage(message);
            mNotifyHandler.postDelayed(this, mDelayedTime);
        }
    };

    public DownloadService() {
        super(TAG);
        KLog.d(TAG);
    }

    public static void launch(Activity activity, String url, String type) {
        Intent intent = new Intent(activity, DownloadService.class);
        intent.putExtra(KEYURL, url);
        intent.putExtra(KEYTYPE, type);
        activity.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        KLog.d(TAG);

        //线程不阻塞的回调 能够得到每次的发起操作回调 而不会阻塞
        String url = intent.getStringExtra(KEYURL);
        String type = intent.getStringExtra(KEYTYPE);
        String filename = String.valueOf(System.currentTimeMillis()) + PicUtils.getPinsType(type);
        int mWorkId = url.hashCode();
        //根据参数 构造对象
        DownloadInfo mDownloadInfo = new DownloadInfo(filename, type, mWorkId, url, DownloadInfo.StateStart);
        KLog.d(mWorkId + " " + Thread.currentThread().toString());
        //根据url的hashcode 放入
        mMap.put(url, mDownloadInfo);
        sendNotifyMessage(mDownloadInfo);
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendNotifyMessage(DownloadInfo mDownloadInfo) {
        Message message = mNotifyHandler.obtainMessage();
        message.what = MSG_START;
        message.obj = mDownloadInfo;
        mNotifyHandler.sendMessage(message);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //处理队列中的消息 顺序调用 处理完一个再处理下一个
        //这里是线程阻塞方法 刚好可以好判断当前任务
        //从map中取出构造的好的 对象 开始任务
        String url = intent.getStringExtra(KEYURL);
        KLog.d(url.hashCode() + " " + Thread.currentThread().toString());
        mDownloadInfo = mMap.get(url);
        actionDownload(url, mDownloadInfo, mListener);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.d(TAG);

        //构造HandlerThread 负责发送通知栏消息的线程
        mHandlerThread = new HandlerThread("notifyThread");
        mHandlerThread.start();
        mNotifyHandler = new NotifyHandler(mHandlerThread.getLooper());
    }


    /**
     * 阻塞当前线程的下载方法
     *
     * @param mImageUrl
     */
    private void actionDownload(String mImageUrl, final DownloadInfo DownloadInfo, OnProgressResponseListener listener) {
        String[] split = mImageUrl.split("/");

        ApiServiceUtils.getInstence().httpApk(split[4], listener)
                //IntentService 有内部变量HandlerThread 运行在子线程中 所以不用切换线程
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
                .map(new Func1<ResponseBody, File>() {
                    @Override
                    public File call(ResponseBody responseBody) {
                        KLog.d(responseBody.contentLength() + " " + responseBody.contentType().toString());
//                        mDownloadInfo.mMediaType = responseBody.contentType().toString();
                        File file = FileUtils.getDirsFile();//构造目录文件
                        KLog.d(file.getPath());

                        return FileUtils.writeResponseBodyToDisk(file, responseBody, mDownloadInfo.fileName);
                    }
                })
//                .filter(file -> file != null)
                .filter(new Func1<File, Boolean>() {
                    @Override
                    public Boolean call(File file) {

                        return file!=null;
                    }
                })
                .subscribe(new Subscriber<File>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        //开始下载 线程开始轮询
                        mNotifyHandler.postDelayed(mRunnable, mDelayedTime);
                    }

                    @Override
                    public void onCompleted() {
                        KLog.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.d(e.toString());
                    }

                    @Override
                    public void onNext(File file) {
                        KLog.d(file.getAbsolutePath());
                        KLog.d(Thread.currentThread().toString());
                        mNotifyHandler.removeCallbacks(mRunnable);
                        sendFileNotifyMessage(file, DownloadInfo);
                    }
                });


    }

    private void sendFileNotifyMessage(File file, DownloadInfo mDownloadInfo) {
        mDownloadInfo.mFile = file;
        mDownloadInfo.mState = "下载完成";
        Message message = mNotifyHandler.obtainMessage();
        message.what = MSG_COMPLETE;
        message.obj = mDownloadInfo;
        mNotifyHandler.sendMessage(message);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        KLog.d(TAG);
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KLog.d(TAG + " " + Thread.currentThread().toString());
//        mListener = null;
//        mDownloadInfo = null;
        mHandlerThread.quit();//结束轮询
//        HuaBanApplication.getRefwatcher(this).watch(this);

    }

}

