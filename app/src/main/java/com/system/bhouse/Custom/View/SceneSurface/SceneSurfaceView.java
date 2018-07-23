package com.system.bhouse.Custom.View.SceneSurface;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by wz on 2018/7/22.
 */

public class SceneSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private RenderThread renderThread;
    private SurfaceHolder surfaceHolder;

    int width;
    int height;

    public SceneSurfaceView(Context context) {
        super(context);
        init();
    }

    public SceneSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SceneSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

//        setZOrderOnTop(true);
//        setZOrderMediaOverlay(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);

        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("weather", "onMeasure width=" + width + ",height=" + height);
        if (renderThread != null) {
            renderThread.setWidth(width);
            renderThread.setHeight(height);
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e("weather", "surfaceCreated");
        if (renderThread == null) {
//            renderThread = new RenderThread(surfaceHolder, getContext());
            renderThread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        renderThread.getRenderHandler().sendEmptyMessage(1);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d("weather", "onFinishInflate");
    }
}
