package com.system.bhouse.Common;

import android.graphics.drawable.Drawable;

import static com.system.bhouse.base.App.sEmojiMonkey;
import static com.system.bhouse.base.App.sEmojiNormal;

/**
 * Created by chaochen on 14-11-12.
 */
public class DrawableTool {
    public static void zoomDrwable(Drawable drawable, boolean isMonkey) {
        int width = isMonkey ? sEmojiMonkey : sEmojiNormal;
        drawable.setBounds(0, 0, width, width);
    }
}
