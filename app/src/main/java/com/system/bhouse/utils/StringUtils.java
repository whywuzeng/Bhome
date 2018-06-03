package com.system.bhouse.utils;

/**
 * Created by Administrator on 2018-01-15.
 */

public class StringUtils {
    public static String IndexLastJsonResult(String result)
    {
        int starti = result.indexOf("[");
        int endi = result.lastIndexOf("]");
        if (-1!=result.indexOf("[")&&-1!=result.lastIndexOf("]")){
            String replace = result.replace("[", "");
            String replace1 = replace.replace("]", "");
            String substring = result.substring(starti, endi);
        }
        return "";
    }
}
