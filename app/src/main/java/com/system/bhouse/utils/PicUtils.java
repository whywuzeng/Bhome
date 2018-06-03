package com.system.bhouse.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.system.bhouse.utils.TenUtils.SDCardUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by Administrator on 2016-5-25.
 * ClassName: com.system.bhouse.utils
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class PicUtils {

    public static void setPicsmall(Drawable drawable, List<String> list_newpath,String filename){
        Bitmap bitmap = ViewUtil.drawableToBitmap(drawable);
        String sdCardPath="";
        if(SDCardUtils.isSDCardEnable()){
            sdCardPath = SDCardUtils.getSDCardPath();
        }
        sdCardPath= sdCardPath+"/Bhouse/";

        list_newpath.add(sdCardPath + filename+".png");
        File dirFile = new File(sdCardPath);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        try {
            File myCaptureFile = new File(sdCardPath + filename+".png");
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void setPicsmallFile(File file, List<String> list_newpath,String filename){
        Bitmap bitmap=BitmapFactory.decodeFile(file.getAbsolutePath());
        String sdCardPath="";
        if(SDCardUtils.isSDCardEnable()){
            sdCardPath = SDCardUtils.getSDCardPath();
        }
        sdCardPath= sdCardPath+"/Bhouse/";

        list_newpath.add(sdCardPath + filename+".png");
        File dirFile = new File(sdCardPath);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        try {
            File myCaptureFile = new File(sdCardPath + filename+".png");
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 65, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static BitmapFactory.Options getBitmapOption(int inSampleSize){
       System.gc();
       BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
       return options;
    }

    public static String getPinsType(String type){
        if (type == null||type.isEmpty()) {
            return ".jpeg";
        }

        if (type.contains("jpeg")){
            return ".jpeg";
        }else if (type.contains("png")){
            return ".png";
        }else if (type.contains("gif")){
            return ".gif";
        }else if (type.contains("mp4")){
            return ".mp4";
        }else if (type.contains("apk")){
            return ".apk";
        }

        return ".jpeg";
    }
}
