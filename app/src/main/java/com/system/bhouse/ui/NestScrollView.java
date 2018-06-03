package com.system.bhouse.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2017-03-15.
 * ClassName: com.system.bhouse.ui
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class NestScrollView extends ScrollView{
    public NestScrollView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        mTouchSlop= ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public NestScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mTouchSlop=ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public NestScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        mTouchSlop=ViewConfiguration.get(context).getScaledTouchSlop();
    }

    private float xDistance, yDistance;
    private float xLast, yLast;
    private int mTouchSlop;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();
                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;
//                if (xDistance > yDistance) {
//                    return false;
//                }
                if (yDistance>mTouchSlop)
                {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }
}
