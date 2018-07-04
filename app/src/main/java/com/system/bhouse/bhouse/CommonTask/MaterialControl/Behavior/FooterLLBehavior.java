package com.system.bhouse.bhouse.CommonTask.MaterialControl.Behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;

/**
 * Created by Administrator on 2018-06-29.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.MaterialControl.Behavior
 */

public class FooterLLBehavior extends CoordinatorLayout.Behavior{
 
    private static final String TAG = "FooterLLBehavior";
    
    private int directionChange;

    public FooterLLBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    //表示对这个滑动 关心？ 父布局的
    @Override
    public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout, final
    View child, final View directTargetChild, final View target, final int
                                               nestedScrollAxes) {
        // 确保是竖直判断的滚动  //nestedScrollAxes表示滑动方向
        return  (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL)!=0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        // 在这个方法里面只处理向上滑动
        if (dy>0 && directionChange<0||dy<0 && directionChange>0)
        {
            //重新置零  马上反方向
            child.animate().cancel();
            directionChange=0;
        }
        directionChange+=dy;
        if (directionChange > child.getHeight() && child.getVisibility() == View.VISIBLE) {
            //隐藏
            hide(child);
        }
        else if (directionChange < 0 && child.getVisibility() != View.VISIBLE) {
            //显示
            show(child);
        }
    }
    

    //处理 关心的滑动,在(父布局)进行滑动 前调用
    @Override
    public void onNestedScroll(final CoordinatorLayout coordinatorLayout, final
    View child, final View target, final int dxConsumed, final int dyConsumed,
                               final int dxUnconsumed, final int dyUnconsumed) {

//        if (dyConsumed>0 && directionChange<0||dyConsumed<0 && directionChange>0)
//        {
//            //重新置零  马上反方向
//            child.animate().cancel();
//            directionChange=0;
//        }
//        directionChange+=dyConsumed;
//        if (directionChange > child.getHeight() && child.getVisibility() == View.VISIBLE) {
//            //隐藏
//            hide(child);
//        }
//        else if (directionChange < 0 && child.getVisibility() != View.VISIBLE) {
//            //显示
//            show(child);
//        }
    }


    private void hide(final View view)
    {
        ViewPropertyAnimator viewPropertyAnimator = view.animate().translationY(view.getHeight()).setInterpolator(new FastOutSlowInInterpolator()).setDuration(200);
        viewPropertyAnimator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void  show(final View view)
    {
        ViewPropertyAnimator viewPropertyAnimator = view.animate().translationY(0).setInterpolator(new FastOutSlowInInterpolator()).setDuration(200);
        viewPropertyAnimator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }
        });
    }
}
