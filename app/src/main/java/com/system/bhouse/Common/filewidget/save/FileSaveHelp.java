package com.system.bhouse.Common.filewidget.save;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.system.bhouse.bhouse.setup.utils.FileUtil;

import java.io.File;

/**
 * Created by Administrator on 2018-11-16.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.Common.filewidget.save
 */
public class FileSaveHelp {

    public static final String defaultPath = Environment.DIRECTORY_DOWNLOADS + File.separator + FileUtil.DOWNLOAD_FOLDER;
    //第一次下载提醒下载位置
    private final SharedPreferences share;

    public FileSaveHelp(Context context) {
        share = context.getSharedPreferences(FileUtil.DOWNLOAD_SETTING, Context.MODE_PRIVATE);
    }

    public String getDefaultPath() {
        String downloadPath;
        if (share.contains(FileUtil.DOWNLOAD_PATH)) {
            return downloadPath = share.getString(FileUtil.DOWNLOAD_PATH, defaultPath);
        }
        else {
            return defaultPath;
        }
    }

    public static String getSettingDownPath(Context context){
        if (context ==null)
        {
            return defaultPath;
        }
       return new FileSaveHelp(context).getDefaultPath();
    }

}
