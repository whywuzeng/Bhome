package com.system.bhouse.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018-08-27.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.utils
 */

public class MeasureBarHeightUtils {

    /**
     * 以Toolbar 以例  扩充toolbar高度
     * @param view
     */
    public static void setHeight(View view,Context mContext) {
        // 获取actionbar的高度
        TypedArray actionbarSizeTypedArray = mContext.obtainStyledAttributes(new int[]{
                android.R.attr.actionBarSize
        });
        float height = actionbarSizeTypedArray.getDimension(0, 0);
        // ToolBar的top值
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        double statusBarHeight = getStatusBarHeight(mContext);
        lp.height = (int) (statusBarHeight + height);
        view.setPadding(0,(int) statusBarHeight,0, 0);
        view.setLayoutParams(lp);
        actionbarSizeTypedArray.recycle();
    }

    private static double getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
