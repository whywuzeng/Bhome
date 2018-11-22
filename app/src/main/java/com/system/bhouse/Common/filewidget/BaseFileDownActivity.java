package com.system.bhouse.Common.filewidget;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.system.bhouse.Common.Global;
import com.system.bhouse.Common.filewidget.databean.AttachmentFileObject;
import com.system.bhouse.Common.filewidget.save.FileSaveHelp;
import com.system.bhouse.bhouse.setup.WWCommon.SmartRefreshBaseActivity;
import com.system.bhouse.bhouse.setup.utils.FileUtil;
import com.system.bhouse.utils.FileUtils;
import com.system.bhouse.utils.blankutils.ToastUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

    //链接的名字
    public static final String FILENAMEURL ="2018win7OA%E5%85%BC%E5%AE%B9%E6%A8%A1%E5%BC%8F%E8%B0%83%E6%95%B4.flv";

    public static final String FILENAMEDOWN ="2018win7OA兼容模式调整.flv";

    protected String urlDownload = Global.DOWNHOST_API+"%s";


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

    protected long getDownloadID(AttachmentFileObject object)
    {
        return downlist.getLong(object.file_id,0L);
    }

    protected void actionDownloadSingle(AttachmentFileObject tag){

        if (tag ==null)
        {
            ToastUtils.showShort("没有选中文件");
            return;
        }
        final File dir = FileUtils.getDestinationInExternalPublicDir(FileSaveHelp.getSettingDownPath(this),tag.getName());
        if (dir.exists()&&dir.isFile())
        {
            tag.isDownload =true;
            return;
        }
        final String absolutePath = dir.getAbsolutePath();
        if (!share.contains(FileUtil.DOWNLOAD_SETTING_HINT))
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
            edit.putBoolean(FileUtil.DOWNLOAD_SETTING_HINT,true);
            edit.commit();
        }else {
            download(tag);
        }
    }

    private String getEncodeUrl(AttachmentFileObject tag) {
        String  tmpurlDownload="";
        try {
            tmpurlDownload=  URLEncoder.encode(tag.getName(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        tmpurlDownload =String.format(urlDownload,tmpurlDownload);
        return tmpurlDownload;
    }

    protected void susscesRemoveFile(AttachmentFileObject object){
        downListedit.putLong(object.file_id,0);
    }

    private void download(AttachmentFileObject fileObject) {
        ArrayList<AttachmentFileObject> list =new ArrayList<>();
        list.add(fileObject);
        downLoad(list);
    }

    private void downLoad(ArrayList<AttachmentFileObject> list) {
        final AttachmentFileObject fileObject = list.get(0);
        //要检查权限
        final String encodeUrl = getEncodeUrl(fileObject);
        //ping下载路径  这里只能模拟

        //要添加cookie 查看cookie机制


        //manage.request 下载设置
//        String encodeUrl ="https://github.com/whywuzeng/Rxjava3/raw/master/javasync/2018win7OA%E5%85%BC%E5%AE%B9%E6%A8%A1%E5%BC%8F%E8%B0%83%E6%95%B4.flv";
        final String[] split = encodeUrl.split("/");
        final String fileName = split[split.length - 1];

        DownloadManager.Request request =new DownloadManager.Request(Uri.parse(encodeUrl));
        request.addRequestHeader("Cookie","");
        request.setDestinationInExternalPublicDir(FileSaveHelp.getSettingDownPath(this),fileObject.getName());
        // 下载时，不显示通知栏
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        request.setTitle(fileObject.getName());
        request.setVisibleInDownloadsUi(false);
        //保存下载downloadID
        final long downloadID = downloadManager.enqueue(request);
        downListedit.putLong(fileObject.file_id,downloadID);
        //启动handler
        downListedit.commit();

    }

    class DownloadChangeObsever extends ContentObserver{

        /**
         * Creates a content observer.
         *
         *   The handler to run {@link #onChange} on, or null if none.
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
