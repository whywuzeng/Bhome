package com.system.bhouse.bhouse.phone.view;

/**
 * Created by Administrator on 2017-12-22.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class WaveView
        extends View {

    public static final int dIs = Color.parseColor("#28FFFFFF");

    public static final int dIt = Color.parseColor("#3CFFFFFF");

    public static final int dIb = Color.parseColor("#13FFFFFF");



    public static final a dIu = a.SQUARE;

    private Paint mBorderPaint;
    private Matrix dIA;

    // paint to draw wave
    private Paint mViewPaint;

    private Paint dIC;
    private Paint dID;

    private float dIE;

    private  float dIE_3;
    private float mDefaultWaterLevel;
    private float dIG;
    private double dIH;
    private float mAmplitudeRatio = 0.05F;
    private float mWaveLengthRatio = 1.0F;
    private float mWaterLevelRatio = 0.5F;
    private float mWaveShiftRatio = 0.0F;
    private float dIM = 0.0F;
    private float dIN = 0.0F;
    private int dIO = dIs;
    private int dIP = dIt;
    private int dIB=dIb;
    private a dIQ = dIu;

    // if true, the shader will display the wave
    private boolean mShowWave=true;

    private BitmapShader mWaveShader;

    private BitmapShader dIx;
    private BitmapShader dIy;
    private Matrix dIz;
    private Matrix mShaderMatrix;
    private static final float DEFAULT_WAVE_LENGTH_RATIO = 1.0f;

    public WaveView(Context paramContext) {
        super(paramContext);
        init();
    }

    public WaveView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }

    public WaveView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init();
    }

    private void atI() {
        if (this.mWaveShader != null) {
            return;
        }
        this.dIH = (2.0f * Math.PI / DEFAULT_WAVE_LENGTH_RATIO / getWidth());
        this.dIE = (getHeight() * 0.07F);

        this.dIE_3 = (getHeight() * 0.10F);

        this.mDefaultWaterLevel = (getHeight() * 0.5F);
        this.dIG = getWidth();

        Bitmap localBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);

        Paint localPaint = new Paint();
        localPaint.setStrokeWidth(2.0F);
        localPaint.setAntiAlias(true);

        int j = getWidth() + 1;
        int k = getHeight();

        float[] arrayOfFloat = new float[j];
        localPaint.setColor(this.dIO);

        int i = 0;
        while (i < j) {
            double d1 = i;
            double d2 = this.dIH;
            double d3 = this.mDefaultWaterLevel;
            double d4 = this.dIE;
            float f = (float) (Math.sin(d1 * d2) * d4 + d3);
            localCanvas.drawLine(i, f, i, k + 1, localPaint);
            arrayOfFloat[i] = f;
            i += 1;
        }

        localPaint.setColor(this.dIP);
        final int wave2Shift = (int) (this.dIG / 4);
        for (int beginX = 0; beginX < j; beginX++) {
            localCanvas.drawLine(beginX,  arrayOfFloat[(beginX + wave2Shift) % j], beginX, k + 1, localPaint);
        }

        //第3个波的绘制
        float[] arrayOfFloat_1 = new float[j];

        localPaint.setColor(this.dIB);

        final int wave2Shift2 = (int) (this.dIG / 3);
//        for (int beginX = 0; beginX < j; beginX++) {
//            localCanvas.drawLine(beginX,  arrayOfFloat[(beginX + wave2Shift2) % j], beginX, k + 1, localPaint);
//        }
        int ie = 0;
        while (ie < j) {
            double d1 = ie;
            double d2 = this.dIH;
            double d3 = this.mDefaultWaterLevel;
            double d4 = this.dIE_3;
            float f = (float) (Math.sin(d1 * d2) * d4 + d3);
            arrayOfFloat_1[ie] = f;
            ie += 1;
        }
        for (int beginX = 0; beginX < j; beginX++) {
            localCanvas.drawLine(beginX,  arrayOfFloat_1[(beginX + wave2Shift2) % j], beginX, k + 1, localPaint);
        }

        this.mWaveShader = new BitmapShader(localBitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        this.mViewPaint.setShader(this.mWaveShader);
    }

    private void atJ() {
        if (this.dIx != null) {
            return;
        }
        Bitmap localBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint = new Paint();
        localPaint.setStrokeWidth(2.0F);
        localPaint.setAntiAlias(true);
        int j = getWidth() + 1;
        int k = getHeight();
        float[] arrayOfFloat = new float[j];
        localPaint.setColor(this.dIP);
        int i = 0;
        while (i < j) {
            double d1 = i;
            double d2 = this.dIH;
            double d3 = this.mDefaultWaterLevel;
            double d4 = this.dIE;
            float f = (float) (Math.sin(d1 * d2) * d4 + d3);
            localCanvas.drawLine(i, f, i, k + 1, localPaint);
            arrayOfFloat[i] = f;
            i += 1;
        }
        this.dIx = new BitmapShader(localBitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        this.dIC.setShader(this.dIx);
    }

    private void atK() {
        if (this.dIy != null) {
            return;
        }
        Bitmap localBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint = new Paint();
        localPaint.setStrokeWidth(2.0F);
        localPaint.setAntiAlias(true);
        int j = getWidth() + 1;
        int k = getHeight();
        float[] arrayOfFloat = new float[j];
        localPaint.setColor(this.dIP);
        int i = 0;
        while (i < j) {
            double d1 = i;
            double d2 = this.dIH;
            double d3 = this.mDefaultWaterLevel;
            double d4 = this.dIE;
            float f = (float) (Math.sin(d1 * d2) * d4 + d3);
            localCanvas.drawLine(i, f, i, k + 1, localPaint);
            arrayOfFloat[i] = f;
            i += 1;
        }
        this.dIy = new BitmapShader(localBitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        this.dID.setShader(this.dIy);
    }

    private void init() {
        this.mShaderMatrix = new Matrix();
        this.mViewPaint = new Paint();
        this.dIz = new Matrix();
        this.dIC = new Paint();
        this.mViewPaint.setAntiAlias(true);
        this.dIC.setAntiAlias(true);
        this.dIA = new Matrix();
        this.dID = new Paint();
        this.dID.setAntiAlias(true);
    }

    public float getAmplitudeRatio() {
        return this.mAmplitudeRatio;
    }

    public float getWaterLevelRatio() {
        return this.mWaterLevelRatio;
    }

    public float getWaveLengthRatio() {
        return this.mWaveLengthRatio;
    }

    public float getWaveShiftRatio1() {
        return this.mWaveShiftRatio;
    }

    public float getWaveShiftRatio2() {
        return this.dIM;
    }

    public float getWaveShiftRatio3() {
        return this.dIN;
    }

    @Override
    protected void onDraw(Canvas paramCanvas) {
        float f1;
        if ((this.mShowWave) && (this.mWaveShader != null)) {
            if (this.mViewPaint.getShader() == null) {
                this.mViewPaint.setShader(this.mWaveShader);
            }
            this.mShaderMatrix.setScale(this.mWaveLengthRatio / 1.0F, this.mAmplitudeRatio / 0.05F, 0.0F, this.mDefaultWaterLevel);
            this.mShaderMatrix.postTranslate(this.mWaveShiftRatio * getWidth(), (0.5F - this.mWaterLevelRatio) * getHeight());

            this.mWaveShader.setLocalMatrix(this.mShaderMatrix);

            float borderWidth = this.mBorderPaint == null ? 0f : this.mBorderPaint.getStrokeWidth();

            switch (this.dIQ)
            {
                case CIRCLE:
                    if (borderWidth > 0) {
                        paramCanvas.drawCircle(getWidth() / 2f, getHeight() / 2f,
                                (getWidth() - borderWidth) / 2f - 1f, this.mBorderPaint);
                    }
                    float radius = getWidth() / 2f - borderWidth;
                    paramCanvas.drawCircle(getWidth() / 2f, getHeight() / 2f, radius, this.mViewPaint);
                    break;
                case SQUARE:
                    if (borderWidth > 0) {
                        paramCanvas.drawRect(
                                borderWidth / 2f,
                                borderWidth / 2f,
                                getWidth() - borderWidth / 2f - 0.5f,
                                getHeight() - borderWidth / 2f - 0.5f,
                                this.mBorderPaint);
                    }
                    paramCanvas.drawRect(borderWidth, borderWidth, getWidth() - borderWidth,
                            getHeight() - borderWidth, this.mViewPaint);
                    break;

            }

        }else {
            this.mViewPaint.setShader(null);
        }

    }

    @Override
    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
        if ((paramBoolean) && (getWidth() > 0) && (getHeight() > 0)) {
            atI();
            atJ();
            atK();
        }
    }

    @Override
    protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    }

    public void setAmplitudeRatio(float paramFloat) {
        if (this.mAmplitudeRatio != paramFloat) {
            this.mAmplitudeRatio = paramFloat;
            invalidate();
        }
    }

    public void setShowWave(boolean paramBoolean) {
        this.mShowWave = paramBoolean;
    }

    public void setWaterLevelRatio(float paramFloat) {
        if (this.mWaterLevelRatio != paramFloat) {
            this.mWaterLevelRatio = paramFloat;
            invalidate();
        }
    }

    public void setWaveLengthRatio(float paramFloat) {
        this.mWaveLengthRatio = paramFloat;
    }

    public void setWaveShiftRatio1(float paramFloat) {
        if (this.mWaveShiftRatio != paramFloat) {
            this.mWaveShiftRatio = paramFloat;
            invalidate();
        }
    }

    public void setWaveShiftRatio2(float paramFloat) {
        if (this.dIM != paramFloat) {
            this.dIM = paramFloat;
            invalidate();
        }
    }

    public void setWaveShiftRatio3(float paramFloat) {
        if (this.dIN != paramFloat) {
            this.dIN = paramFloat;
            invalidate();
        }
    }


    public static enum a {
        CIRCLE, SQUARE;

        private a() {
        }
    }
}

