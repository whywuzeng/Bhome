package com.system.bhouse.utils;

import android.os.Environment;
import android.support.annotation.NonNull;

import com.socks.library.KLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2016-6-1.
 * ClassName: com.system.bhouse.utils
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class FileUtils {

    /**
     * 存储文件的路径
     */
    public static final String DefaultDirsFileName = "bhouseDownoad";

    /**
     * 是否第一次来设置下载 。告诉他下载路径
     */

    public final static String DOWNLOAD_SETTING = "download_setting";

    /**
     * 生成文件夹路径
     * @param dirType
     * @return
     */
    public static File getDestinationInExternalPublicDir(String dirType)
    {
        //获取的手机存储路径是/storage/emulated/0
        final File file = Environment.getExternalStoragePublicDirectory(dirType);
        if (file.exists())
        {
            if (!file.isDirectory())
            {
                throw new IllegalStateException(file.getAbsolutePath()+"already exist and not is a directory");
            }
        }else {
            if (!file.mkdir())
            {
                throw new IllegalStateException("unable create Directory"+file.getAbsolutePath());
            }
        }
        return file;
    }

    @NonNull
    public static File getDirsFile() {
        return getDirsFile(DefaultDirsFileName);
    }

    @NonNull
    public static File getDirsFile(String dirsFileName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), dirsFileName);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    //保存字符串到文件中
    public static String saveAsFileWriter(String content, File file, String name) {

        File futureStudioIconFile = new File(file.getPath(), name);
        FileWriter fwriter = null;
        try {
            fwriter = new FileWriter(futureStudioIconFile);
            fwriter.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return futureStudioIconFile.getPath();
    }

    public static String getXmlString(File xmlfile) {
        String xmlString;
        byte[] strBuffer = null;
        int flen = 0;
        try {
            InputStream in = new FileInputStream(xmlfile);
            flen = (int) xmlfile.length();
            strBuffer = new byte[flen];
            in.read(strBuffer, 0, flen);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        xmlString = new String(strBuffer);      //构建String时，可用byte[]类型，

        return xmlString;
    }


    public static File writeResponseBodyToDisk(File file, ResponseBody body, String name) {

        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(file.getPath(), name);
//            Logger.d("isExternalStorageWritable=" + Utils.isExternalStorageWritable());
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;
                    if (fileSizeDownloaded == 0) {
                        KLog.d("file download: " + fileSizeDownloaded + " of " + fileSize);
                    }

                    if (fileSizeDownloaded == fileSize) {
                        KLog.d("file download: " + fileSizeDownloaded + " of " + fileSize);
                    }
                }
                outputStream.flush();
                //所有流程操作完成 返回true
                return futureStudioIconFile;
            } catch (IOException e) {
                //捕捉到写入异常 返回false
                return null;
            } finally {
                //finally修饰的代码块 一定执行 关闭流
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            //捕捉到 文件的异常 返回false
            return null;
        }

    }
}
