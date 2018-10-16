package com.system.bhouse.ui.loading;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by Administrator on 2018-09-12.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.ui.loading
 */

public class DoubleBallLoading extends View{

    //半径
    private float ballradius=9;

    //比例
    private float scale=1.8f;

    //开始分数比例
    private float startFraction=0.2f;

    //停止缩放的比例
    private float endFraction=0.8f;

    //两球之间的
    private float gap=4;
    //初始x
    private float initLeftX=0;
    //初始Y
    private float initLeftY=0;
    private float initRightX=0;
    private int initRightY=0;
    private long tiPause=80;
    private long duration=3500;
    //动画运行百分比
    private float animatedFraction;
    private boolean isAnimCancel;
    private boolean isRecy;
    private ValueAnimator valueAnimator;
    private float lfrBallRadius;
    private float rflBallRadius;

    Paint leftPaint, rightPaint,mixPaint;
    Path mixPath;
    private Path leftPath;
    private Path rightPath;


    public DoubleBallLoading(Context context) {
        super(context);
        init();
    }

    public DoubleBallLoading(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DoubleBallLoading(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //找到画笔
        leftPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rightPaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        mixPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mixPath=new Path();

         leftPath = new Path();
         rightPath = new Path();
        leftPaint.setColor(Color.RED);
        rightPaint.setColor(Color.BLUE);

        mixPaint.setColor(Color.parseColor("#000000"));

       start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //得到初始x 和 y
        float distance = ballradius  + gap + ballradius ;
        Paint lftpaint ,rgtpaint;
        if (isRecy)
        {
            lftpaint=leftPaint;
            rgtpaint=rightPaint;
            lfrBallRadius =ballradius;
            rflBallRadius=ballradius*scale;

        }else {

            lftpaint=rightPaint;
            rgtpaint=leftPaint;
            lfrBallRadius =ballradius*scale;
            rflBallRadius=ballradius;
        }
        initLeftX =(getMeasuredWidth() / 2 - distance / 2);
        initRightX=(getMeasuredWidth() / 2 + distance / 2);

        if (animatedFraction<startFraction) {
            lfrBallRadius = (ballradius * scale - ballradius) * animatedFraction + ballradius;
            rflBallRadius = ballradius * scale - (ballradius * scale - ballradius) * animatedFraction;
        }else if (animatedFraction>endFraction)
        {
//            float scaleFraction = (animatedFraction - 1) / (endFraction - 1);
            float scaleFraction = (animatedFraction - 1)+(endFraction - 1);
            lfrBallRadius=ballradius*scale- (ballradius * scale - ballradius) * scaleFraction;
            rflBallRadius=(ballradius * scale - ballradius) * scaleFraction + ballradius;
        }else {

            lfrBallRadius=ballradius * scale;
            rflBallRadius=ballradius;
        }

        initLeftY = getMeasuredHeight() / 2;

        initRightY =getMeasuredHeight() / 2;
        //得到变化x
        initLeftX = initLeftX + distance * animatedFraction;
        initRightX = initRightX - distance * animatedFraction;
        //得到变化的radius


        leftPath.reset();
        leftPath.addCircle(initLeftX, initLeftY, lfrBallRadius, Path.Direction.CW);

        rightPath.reset();
        rightPath.addCircle(initRightX, initRightY, rflBallRadius,Path.Direction.CW);
        mixPath.op(rightPath, leftPath, Path.Op.INTERSECT);
            canvas.drawPath(rightPath,rgtpaint);
            canvas.drawPath(leftPath,lftpaint);
            canvas.drawPath(mixPath,mixPaint);
    }

    //初始化动画
    private void initAnim(){
        //运行动画来 ini 重绘.
         valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(duration);
        if (tiPause>0)
        {
            //设置启动停止时间
            valueAnimator.setStartDelay(tiPause);
            //设置差值器
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        }

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatedFraction = animation.getAnimatedFraction();
                invalidate();
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                //取消
                isAnimCancel=true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //开始  要重绘
                if (!isAnimCancel)
                {
                    valueAnimator.start();
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                isRecy=!isRecy;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                //标志正在动画中
                isRecy=!isRecy;
            }
        });
    }

    private void start(){
        if (valueAnimator==null)
        {
            initAnim();
        }
        if (valueAnimator.isRunning())
        {
            valueAnimator.cancel();
        }
        post(new Runnable() {
            @Override
            public void run() {
                isAnimCancel = false;
                isRecy = false;
                valueAnimator.start();
            }
        });

    }


}
