package com.system.bhouse.utils;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;

/**
 * Created by Administrator on 2016-5-20.
 * ClassName: com.system.bhouse.utils
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class BHEncodeUtils {
    public static String BHEncode(String parms) {
        String s = Base64.encodeToString(parms.getBytes(), Base64.NO_WRAP);
        String encode = URLEncoder.encode(s);
        return encode;
    }

    /**
     * 对服务器返回的内容进行解压并返回解压后的内容
     * @param is
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public static String unGzip(InputStream is) throws IOException,
            UnsupportedEncodingException {
        GZIPInputStream in = new GZIPInputStream(is);
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        int len = -1;
        byte[] buffer = new byte[1024];
        while ((len = in.read(buffer)) != -1) {
            arrayOutputStream.write(buffer, 0, len);
        }
        in.close();
        arrayOutputStream.close();
        is.close();
        return new String(arrayOutputStream.toByteArray(), "utf-8");
    }
}
