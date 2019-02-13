package com.system.bhouse.Common;

import android.content.res.AssetManager;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.style.URLSpan;
import android.util.Log;

import org.xml.sax.XMLReader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Administrator on 2017-10-30.
 * 这才是 全局Global
 */

public class Global {

    private static final String ERRTAG = "errorLog";
    private static SimpleDateFormat DayFormatTime = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd EEE");

    public static final String DOWNHOST_API="https://github.com/whywuzeng/Rxjava3/raw/master/javasync/";

    //文件大小
    public static DecimalFormat df = new DecimalFormat("#.00");


    public static String dayFromTime(long time) {
        return DayFormatTime.format(time);
    }

    public static Html.TagHandler tagHandler =new Html.TagHandler(){
        @Override
        public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
            if (tag.toLowerCase().equals("")&!opening)
            {
                output.append("\n\n");
            }
        }
    };

    public static Spannable changeHyperlinkColorMaopao(String content, Html.ImageGetter imageGetter, Html.TagHandler tagHandler, AssetManager assetManager) {
        Spannable s = (Spannable) Html.fromHtml(content, imageGetter, tagHandler);
        return s;
    }

    public static class MessageParse {
        public String text = "";
        public ArrayList<String> uris = new ArrayList<>();
        public boolean isVoice;
        public String voiceUrl;
        public int voiceDuration;
        public int played;
        public int id;

        public String toString() {
            String s = "text " + text + "\n";
            for (int i = 0; i < uris.size(); ++i) {
                s += uris.get(i) + "\n";
            }
            return s;
        }
    }

//    public static Spannable changeHyperlinkColorMaopao(String content, Html.ImageGetter imageGetter, Html.TagHandler tagHandler, AssetManager assetManager) {
//        Spannable s = changeHyperlinkColor(content, imageGetter, tagHandler, 0xFF3BBD79);
//        return spannToGif(s, assetManager);
//    }
//
//    public static Spannable changeHyperlinkColor(String content, Html.ImageGetter imageGetter, Html.TagHandler tagHandler, int color) {
//        Spannable s = (Spannable) Html.fromHtml(content, imageGetter, tagHandler);
//        return getCustomSpannable(color, s);
//    }


    /**
     * 显示文件大小,保留两位
     */
    public static String HumanReadableFilesize(double size) {
        String[] units = new String[]{"B", "KB", "MB", "GB", "TB", "PB"};
        double mod = 1024.0;
        int i = 0;
        while (size >= mod) {
            size /= mod;
            i++;
        }
        //return Math.round(size) + units[i];
        return df.format(size) + " " + units[i];
    }

    static public String readTextFile(File file,String charset)
    {
        final FileInputStream is;
        try {
            is = new FileInputStream(file);
            return readTextFile(is,charset);
        } catch (FileNotFoundException e) {
            errorLog(e);
        }
        return "";
    }

    private static String readTextFile(FileInputStream is, String charset)  {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        byte[] buf=new byte[1024];
        int len;
        try {
            while ( (len= is.read(buf)) != -1)
            {
                stream.write(buf,0,len);
            }
            stream.close();
            is.close();
        } catch (IOException e) {
            Global.errorLog(e);
        }

        try {
            return new String(stream.toByteArray(),charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "nsupportedEncodingException";
        }
    }

    static public void errorLog(Exception e)
    {
        if (e ==null)
        {
            return;
        }
        e.printStackTrace();
        Log.e(ERRTAG, "errorLog: "+e);
    }

    public static String getUUID32(){
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        return uuid;
    }

    public static Spannable changeHyperlinkColor(String content){
        return Global.changeHyperlinkColor(content,null,null);
    }

    private static Spannable changeHyperlinkColor(String content, Html.ImageGetter imageGetter, Html.TagHandler tagHandler) {

        return Global.changeHyperlinkColor(content,imageGetter,tagHandler,0xFF3BBD79);
    }

    private static Spannable changeHyperlinkColor(String content, Html.ImageGetter imageGetter, Html.TagHandler tagHandler, int color) {
        final Spannable s = (Spannable) Html.fromHtml(content, imageGetter, tagHandler);
        return getCustomSpannable(s,color);
    }

    private static Spannable getCustomSpannable(Spannable s, int color) {
        URLSpan[] urlSpan = s.getSpans(0, s.length(), URLSpan.class);
//        for (URLSpan span : urlSpan) {
//            int start = s.getSpanStart(span);
//            int end = s.getSpanEnd(span);
//            s.removeSpan(span);
//            span = new URLSpanNoUnderline(span.getURL(), color);
//            s.setSpan(span, start, end, 0);
//        }
//
//        QuoteSpan quoteSpans[] = s.getSpans(0, s.length(), QuoteSpan.class);
//        for (QuoteSpan span : quoteSpans) {
//            int start = s.getSpanStart(span);
//            int end = s.getSpanEnd(span);
//            s.removeSpan(span);
//            GrayQuoteSpan grayQuoteSpan = new GrayQuoteSpan();
//            s.setSpan(grayQuoteSpan, start, end, 0);
//        }

        return null;
    }

}
