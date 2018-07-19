package com.system.bhouse.bhouse.CommonTask.TechnologyExecution.Custom;

import android.content.Context;
import android.support.animation.SpringAnimation;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.socks.library.KLog;

/**
 * Created by Administrator on 2018-07-19.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution.Custom
 */

public class SpringScrollView extends NestedScrollView{

    private static final String TAG = "SpringScrollView";

    private float startDragY;

    private SpringAnimation springAnim;
    private float downRawx;
    private float downRawY;
    private int mTouchSlop;

    public SpringScrollView(Context context) {
        super(context);
        init(context);
    }

    public SpringScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SpringScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        springAnim = new SpringAnimation(this, SpringAnimation.TRANSLATION_Y, 0);
        //刚度 默认1200 值越大回弹的速度越快
        springAnim.getSpring().setStiffness(200.0f);
        //阻尼 默认0.5 值越小，回弹之后来回的次数越多
        springAnim.getSpring().setDampingRatio(1.50f);

    }

    private boolean isTop;
    private boolean isBottom;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                isTop =false;
                isBottom =false;
                downRawx = ev.getRawX();
                downRawY = ev.getRawY();

                break;
            case MotionEvent.ACTION_MOVE:
                CheckDirection(ev);
                if (isTop&&getScrollY()<=0)
                {
                    //顶部下拉
                    if (startDragY ==0)
                    {
                        startDragY=ev.getRawY();
                    }

                    if (ev.getRawY() - startDragY>0)
                    {
                        setTranslationY((ev.getRawY() - startDragY) / 3);
                        return true;
                    }else{
                        //为上拉了
                        springAnim.cancel();
                        setTranslationY(0);
                    }
                }else if (isBottom&&(getScrollY() + getHeight()) >= getChildAt(0).getMeasuredHeight()) {
                    //底部上拉
                    if (startDragY == 0) {
                        startDragY = ev.getRawY();
                    }
                    if (ev.getRawY() - startDragY < 0) {
                        setTranslationY((ev.getRawY() - startDragY) / 3);
                        return true;
                    } else {
                        springAnim.cancel();
                        setTranslationY(0);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (getTranslationY() != 0) {
                    springAnim.start();
                }
                startDragY = 0;
                break;

        }
        return super.onTouchEvent(ev);
    }

    private void CheckDirection(MotionEvent ev) {

        float dx = ev.getX() - downRawx;
        float dy = ev.getY() - downRawY;

        isBottom = Math.abs(dy) > mTouchSlop && dy < 0;
        isTop = Math.abs(dy) > mTouchSlop && dy > 0;

        KLog.e("dx:="+dx);
        KLog.e("dy:="+dy);

    }
}
