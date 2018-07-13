package com.system.bhouse.utils.TenUtils;

/**
 * Created by Administrator on 2018-07-10.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.utils.TenUtils
 */

public class ButtonUtils {

    private final static int diff = 1000;

    private static int BUTTONID = -1;

    private static long Dtime = -1;

    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(-1, diff);
    }

    public static boolean isFastDoubleClick(int buttonId) {
        return isFastDoubleClick(buttonId, diff);
    }

    public static boolean isFastDoubleClick(int buttonId, int diff) {
        long currenttime = System.currentTimeMillis();
        long gapTime = currenttime - Dtime;
        if (BUTTONID == buttonId && Dtime > 0 && gapTime < diff) {
            return true;
        }
        Dtime = currenttime;
        BUTTONID = buttonId;
        return false;
    }

}
