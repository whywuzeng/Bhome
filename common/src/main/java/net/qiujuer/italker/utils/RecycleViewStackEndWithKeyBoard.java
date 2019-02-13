package net.qiujuer.italker.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2019-01-24.
 * <p>
 * by author wz
 * <p>
 * net.qiujuer.italker.utils
 */
public class RecycleViewStackEndWithKeyBoard {
    public static void assistActivity (Activity activity, LinearLayoutManager manager) {
        new RecycleViewStackEndWithKeyBoard(activity,manager);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;

    private RecycleViewStackEndWithKeyBoard(Activity activity, final LinearLayoutManager manager) {
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                getMethodSetStackEnd(manager);
            }
        });
    }

    private void getMethodSetStackEnd(LinearLayoutManager manager) {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard/4)) {
                if (!manager.getStackFromEnd())
                    manager.setStackFromEnd(true);
            }
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);// 全屏模式下： return r.bottom
    }
}
