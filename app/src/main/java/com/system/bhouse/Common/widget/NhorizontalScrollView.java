package com.system.bhouse.Common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2018-05-10.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.Common.widget
 */

public class NhorizontalScrollView extends ScrollView {

    public NhorizontalScrollView(Context context) {
        this(context, null);
    }

    public NhorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NhorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public NhorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs);
    }

    int mLastXIntercept;
    int mLastYIntercept;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isintercept = false;
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isintercept = false;
                break;

            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;
                if (Math.abs(deltaX) < Math.abs(deltaY)) {
                    isintercept = true;
                }
                else {
                    return false;
                }
                break;

            case MotionEvent.ACTION_UP:
                isintercept = false;
                break;
        }
        mLastXIntercept = x;
        mLastYIntercept = y;
        return super.onInterceptTouchEvent(ev);
    }
}

