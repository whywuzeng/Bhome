package net.qiujuer.italker.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import net.qiujuer.genius.ui.Ui;

/**
 * Created by Administrator on 2019-02-18.
 * <p>
 * by author wz
 * <p>
 * net.qiujuer.italker.utils
 */
public class BitmapWaterMarkUtil {
    private static final String TAG = "BitmapWaterMarkUtil";

    private static Bitmap BitmapWithText(String username, AppCompatActivity context){

        Bitmap bitmap = Bitmap.createBitmap((int) Ui.dipToPx(context.getResources(),240), (int) Ui.dipToPx(context.getResources(),240), Bitmap.Config.ARGB_8888);
        Log.e(TAG, "BitmapWithText: "+"水印 bitmap 宽为："+(int) Ui.dipToPx(context.getResources(),240)+"bitmap 高为："+ (int) Ui.dipToPx(context.getResources(),240));
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setAlpha(80);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(Ui.dipToPx(context.getResources(),45));
        Path path = new Path();
        path.moveTo(Ui.dipToPx(context.getResources(),30), (int) Ui.dipToPx(context.getResources(),240));
        path.lineTo(Ui.dipToPx(context.getResources(),180), 0);
        canvas.drawTextOnPath(username, path, 0, 30, paint);
        return bitmap;
    }

    public static BitmapDrawable BitmapDrawableWithBitmap(String username, AppCompatActivity context){
        BitmapDrawable bitmapDrawable = new BitmapDrawable(BitmapWithText(username, context));
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        bitmapDrawable.setDither(true);
        return bitmapDrawable;
    }
}
