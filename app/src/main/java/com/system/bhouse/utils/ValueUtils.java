package com.system.bhouse.utils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018-05-04.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.utils
 */

public class ValueUtils {

    public static <B> boolean IsFirstValueExist(ArrayList<B> beans) {
        if (beans==null||beans.isEmpty()) {
            return false;
        }
        else {
            return true;
        }
    }
}
