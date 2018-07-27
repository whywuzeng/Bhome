package com.system.bhouse.Custom.View.SceneSurface;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.Surface;

import com.system.bhouse.bhouse.R;

/**
 * Created by Administrator on 2018/7/23 0023.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.Custom.View.SceneSurface
 */

public class RenderHandleThread extends HandlerThread implements Handler.Callback {

    public static final int MSG_START = 100 << 4;
    public static final int MSG_CLEAR = 200 << 4;

    public Handler getmReceiver() {
        return mReceiver;
    }

    private Handler mReceiver;

    private boolean mRunning=false;

    private Context context;
    private Surface surfaceHolder;
    private Scene scene;
    private Rect mSurfaceRect;
    private Paint mPaint;


    public RenderHandleThread(String name) {
        super(name);
    }

    public RenderHandleThread(String name, int priority) {
        super(name, priority);
    }

    public RenderHandleThread(Surface surface, Context context) {
        super("DrawingThread");

        this.context = context;
        this.surfaceHolder = surface;
        scene = new Scene(context);
        mSurfaceRect=new Rect();
        //add scene/actor
        scene.setBg(BitmapFactory.decodeResource(context.getResources(), R.drawable.bg0_fine_day));
//        scene.add(new BirdUp(context));
        scene.add(new CloudLeft(context));
        scene.add(new CloudRight(context));

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onLooperPrepared() {
        mReceiver = new Handler(getLooper(), this);
        // 开始渲染
        mRunning = true;
        mReceiver.sendEmptyMessage(MSG_START);
    }

    @Override
    public boolean quit() {
        // 退出前清除所有的消息
        mRunning = false;
//        surfaceHolder=null;
        mReceiver.removeCallbacksAndMessages(null);
        return super.quit();
    }


    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what)
        {
            case MSG_START:
                // 如果取消，则不做任何事情
                if (!mRunning) return true;
                if (scene.getWidth() != 0 && scene.getHeight() != 0) {
                    draw();
                }
                break;
            case MSG_CLEAR:
                //最后绘制一次背景
                if (!mRunning) return true;
                if (scene.getWidth() != 0 && scene.getHeight() != 0) {
                    drawBg();
                }
                scene.clear();
                break;
        }

        // 发送下一帧
        if (mRunning) {
            mReceiver.sendEmptyMessage(MSG_START);
        }
        return true;
    }

    private void drawBg() {
        try {
            if (surfaceHolder!=null) {
                synchronized (surfaceHolder) {
                    if (surfaceHolder!=null)
                    {
                        Canvas canvas = surfaceHolder.lockCanvas(mSurfaceRect);
                        if (canvas != null&&surfaceHolder!=null&&surfaceHolder.isValid()&&!mRunning) {
                            canvas.drawColor(Color.rgb(35, 183, 215));
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void draw() {
        try {
        if (surfaceHolder!=null) {
            synchronized (surfaceHolder) {
                if (surfaceHolder!=null)
                {
                    Canvas canvas = surfaceHolder.lockCanvas(mSurfaceRect);
                    if (canvas != null&&surfaceHolder!=null&&surfaceHolder.isValid()&&mRunning) {
                        scene.draw(canvas);
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    public void updateSize(int width, int height) {
        scene.setWidth(width);
        scene.setHeight(height);
        mSurfaceRect.set(0, 0, width, height);
    }

    public void setWidth(int width) {
        scene.setWidth(width);
    }

    public void setHeight(int height) {
        scene.setHeight(height);
    }

    public void clear(){
        mReceiver.sendEmptyMessage(MSG_CLEAR);
    }
}
