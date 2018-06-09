package com.system.bhouse.bhouse.CommonTask.Widget.UpScorllLayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2018-06-07.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.Widget.UpScorllLayout
 */

public class ScrollLinearLayout extends LinearLayout {

    public ScrollLinearLayout(Context context) {
        this(context,null);
    }

    public ScrollLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    private void init() {

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return false;
    }
}
