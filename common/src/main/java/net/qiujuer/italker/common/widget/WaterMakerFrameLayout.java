package net.qiujuer.italker.common.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import net.qiujuer.genius.ui.Ui;

/**
 * Created by Administrator on 2019-02-18.
 * <p>
 * by author wz
 * <p>
 * net.qiujuer.italker.common.widget
 */
public class WaterMakerFrameLayout extends FrameLayout{

    private Bitmap bitmap;
    private BitmapDrawable bitmapDrawable;

    public WaterMakerFrameLayout(@NonNull Context context) {
        super(context);
        init(context);
    }

    public WaterMakerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WaterMakerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;

    private void init(Context context) {

         bitmap = Bitmap.createBitmap(240, 240, Bitmap.Config.ARGB_8888);

         bitmapDrawable = new BitmapDrawable(bitmap);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setAlpha(80);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(Ui.dipToPx(getResources(),30f));
        Path path = new Path();
        path.moveTo(30, 150);
        path.lineTo(300, 0);
        canvas.drawTextOnPath(userName, path, 0, 30, paint);

        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        bitmapDrawable.setDither(true);

        canvas.restore();
    }
}
