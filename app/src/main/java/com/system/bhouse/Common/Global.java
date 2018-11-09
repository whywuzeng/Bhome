package com.system.bhouse.Common;

import android.content.res.AssetManager;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;

import org.xml.sax.XMLReader;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017-10-30.
 */

public class Global {

    private static SimpleDateFormat DayFormatTime = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd EEE");

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

}
