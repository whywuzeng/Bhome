package com.system.bhouse.Common.filewidget;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.system.bhouse.Common.filewidget.databean.AttachmentFileObject;
import com.system.bhouse.bhouse.setup.WWCommon.SmartRefreshBaseActivity;
import com.system.bhouse.bhouse.setup.utils.FileUtil;
import com.system.bhouse.utils.FileUtils;
import com.system.bhouse.utils.blankutils.ToastUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018-11-14.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.Common.filewidget
 */
public abstract class BaseFileDownActivity extends SmartRefreshBaseActivity {

    private SharedPreferences share;
    private DownloadManager downloadManager;
    private SharedPreferences downlist;
    private SharedPreferences.Editor downListedit;
    private static Myhandler myhandler;
    private DownloadChangeObsever downloadChangeObsever;
    private DownloadManagerPro downloadManagerPro;

    public static final String FILENAME ="2018win7OA%E5%85%BC%E5%AE%B9%E6%A8%A1%E5%BC%8F%E8%B0%83%E6%95%B4.flv";

    /**
     * 先放这里以后换个地方
     * @param downloadManagerStatus
     * @return
     */
    public static boolean isDownloading(int downloadManagerStatus) {
        return downloadManagerStatus == DownloadManager.STATUS_RUNNING
                || downloadManagerStatus == DownloadManager.STATUS_PAUSED
                || downloadManagerStatus == DownloadManager.STATUS_PENDING;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        share = getSharedPreferences(FileUtil.DOWNLOAD_SETTING, MODE_PRIVATE);
        downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        downloadManagerPro = new DownloadManagerPro(downloadManager);
        downlist=getSharedPreferences(FileUtil.DOWNLOAD_LIST,MODE_PRIVATE);
        downListedit = downlist.edit();
        myhandler =new Myhandler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (downloadChangeObsever==null) {
            downloadChangeObsever = new DownloadChangeObsever();
        }
        getContentResolver().registerContentObserver(DownloadManagerPro.CONTENT_URI,true,downloadChangeObsever);
    }

    protected void updateFileDownloadStatus(AttachmentFileObject mFileObject) {
        if (mFileObject.downloadId != 0L) {
            mFileObject.bytesAndStatus = downloadManagerPro.getBytesAndStatus(mFileObject.downloadId);
            Log.v("updateFileDownloadStat", mFileObject.getName() + ":" + mFileObject.bytesAndStatus[0] + " " + mFileObject.bytesAndStatus[1] + " " + mFileObject.bytesAndStatus[2]);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(downloadChangeObsever);
    }

    protected long getDownloadID()
    {
        return downlist.getLong(FILENAME,0L);
    }

    protected void actionDownloadSingle(AttachmentFileObject tag){
        if (tag ==null)
        {
            ToastUtils.showShort("没有选中文件");
            return;
        }
        final File dir = FileUtils.getDestinationInExternalPublicDir(  Environment.DIRECTORY_DOWNLOADS + File.separator + FileUtil.DOWNLOAD_FOLDER);
        final String absolutePath = dir.getAbsolutePath();
        if (!share.contains(FileUtil.DOWNLOAD_SETTING))
        {
            String msgFormat = "你的文件将下载到这个%s下载位置";
            AlertDialog.Builder builder =new AlertDialog.Builder(this);
                    builder.setTitle("提示")
                            .setMessage(String.format(msgFormat,absolutePath))
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    download(tag);
                                }
                            })
                            .show();

            final SharedPreferences.Editor edit = share.edit();
            edit.putBoolean(FileUtil.DOWNLOAD_SETTING,true);
            edit.commit();
        }else {
            download(tag);
        }
    }

    private void download(AttachmentFileObject fileObject) {
        ArrayList<AttachmentFileObject> list =new ArrayList<>();
        list.add(fileObject);
        downLoad(list);
    }

    private void downLoad(ArrayList<AttachmentFileObject> list) {
        final AttachmentFileObject fileObject = list.get(0);
        //要检查权限

        //ping下载路径  这里只能模拟
        String urldown ="https://dn-coding-net-production-file.codehub.cn/64bbff38-0a72-49d1-a973-6ebaab659a30.PNG?attname=IMG_1714.PNG&e=1542167298&token=goE9CtaiT5YaIP6ZQ1nAafd_C1Z_H2gVP8AwuC-5:XovB8A1pVOrN18xBe_BIq0Qsybw=";

        //要添加cookie 查看cookie机制


        //manage.request 下载设置
        String url ="https://github.com/whywuzeng/Rxjava3/raw/master/javasync/2018win7OA%E5%85%BC%E5%AE%B9%E6%A8%A1%E5%BC%8F%E8%B0%83%E6%95%B4.flv";
        final String[] split = url.split("/");
        final String fileName = split[split.length - 1];

        DownloadManager.Request request =new DownloadManager.Request(Uri.parse(url));
        request.addRequestHeader("Cookie","");
        request.setDestinationInExternalPublicDir(FileUtils.getDestinationInExternalPublicDir(FileUtils.DefaultDirsFileName).getAbsolutePath(),fileName);
        // 下载时，不显示通知栏
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        request.setTitle(fileObject.getName());
        request.setVisibleInDownloadsUi(false);
        //保存下载downloadID
        final long downloadID = downloadManager.enqueue(request);
        downListedit.putLong(fileName,downloadID);
        //启动handler
        downListedit.commit();

    }

    class DownloadChangeObsever extends ContentObserver{

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public DownloadChangeObsever() {
            super(myhandler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            checkFileDownloadStatus();
        }
    }

    private static class Myhandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    protected abstract void checkFileDownloadStatus();
}
