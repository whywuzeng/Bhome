package com.system.bhouse.bhouse.CommonTask.utils;

import android.view.View;

import com.system.bhouse.bhouse.R;

/**
 * Created by chenchao on 2016/12/14.
 * 与 view_blank_common 配套使用
 */

public class BlankViewHelp extends BlankViewDisplay {

    public static void setBlankLoading(View v, boolean show) {
        showLoading(v, show);
        if (v!=null) {
            if (show) {
                v.setVisibility(View.VISIBLE);
            }
            else {
                v.setVisibility(View.GONE);
            }
        }
    }

    public static void setBlank(int itemSize, Object fragment, boolean request, View v, View.OnClickListener onClick) {
        showLoading(v, false);
        BlankViewDisplay.setBlank(itemSize, fragment, request, v, onClick);
    }

    public static void setBlank(int itemSize, Object fragment, boolean request, View v, View.OnClickListener onClick, String tipString) {
        showLoading(v, false);
        BlankViewDisplay.setBlank(itemSize, fragment, request, v, onClick, tipString);
    }

    private static void showLoading(View v, boolean show) {

        int loadingVisable = show ? View.VISIBLE : View.GONE;
        int otherVisable = show ? View.GONE : View.VISIBLE;
        if (v==null)
            return;
        v.findViewById(R.id.icon).setVisibility(otherVisable);
        v.findViewById(R.id.message).setVisibility(otherVisable);
        v.findViewById(R.id.btnRetry).setVisibility(otherVisable);
        v.findViewById(R.id.loadingLayout).setVisibility(loadingVisable);
    }

}
