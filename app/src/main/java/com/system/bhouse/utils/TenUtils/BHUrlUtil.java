package com.system.bhouse.utils.TenUtils;

/**
 * Created by Administrator on 2018-11-21.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.utils.TenUtils
 */
public class BHUrlUtil {

    public static void SplitChineseASSIC(String path) {

        StringBuilder chString = new StringBuilder();
        StringBuilder enString = new StringBuilder();

        final char[] chars = path.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            final char aChar = chars[i];
            if ((aChar >= 0x4e00) && (aChar <= 0x9fbb)) {
                chString.append(String.valueOf(aChar));

            }
            else {

            }
        }
    }

}
