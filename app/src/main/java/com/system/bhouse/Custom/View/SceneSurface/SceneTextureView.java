package com.system.bhouse.Custom.View.SceneSurface;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;

/**
 * Created by Administrator on 2018/7/23 0023.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.Custom.View.SceneSurface
 */

public class SceneTextureView extends TextureView implements TextureView.SurfaceTextureListener {

    private RenderHandleThread renderThread;
    int width;
    int height;


    public SceneTextureView(Context context) {
        super(context);
        init();
    }

    public SceneTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SceneTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setSurfaceTextureListener(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Log.e("weather", "onSurfaceTextureAvailable");
        if (renderThread == null) {
            renderThread = new RenderHandleThread(new Surface(surface), getContext());

            renderThread.start();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        renderThread.updateSize(width,height);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
//        renderThread.getRenderHandler().sendEmptyMessage(1);
        renderThread.quit();
        renderThread = null;
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

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
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d("weather", "onFinishInflate");
    }
}
