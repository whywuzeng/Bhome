package com.system.bhouse.utils.TenUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018-03-10.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.utils.TenUtils
 */

public class DataFormatUtils {

    private static final SimpleDateFormat sFormatThisYearSlash = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final SimpleDateFormat simpleFormatThisYearSlash = new SimpleDateFormat("yyyy-MM-dd");


    public static String getCurrentTime(){
        Date curDate = new Date(System.currentTimeMillis());
        String format = sFormatThisYearSlash.format(curDate);
        return format;
    }

    public static String getCurrentSimpleTime(){
        Date curDate = new Date(System.currentTimeMillis());
        String format = simpleFormatThisYearSlash.format(curDate);
        return format;
    }

    public static String dayFromTime(String time) {
        Date parse = null;
        try {
            parse = sFormatThisYearSlash.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sFormatThisYearSlash.format(parse);
    }
}

