package com.system.bhouse.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016-4-15.
 * ClassName: com.system.bhouse.utils
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class ViewUtil {

        /**
         * Drawable 着色的后向兼容方案
         * @param drawable
         * @param colors
         * @return
         */
        public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
            final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTintList(wrappedDrawable, colors);
            return wrappedDrawable;
        }

        public static void rotateScreen(Activity activity) {
            if (activity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }

        public static void setFullScreen(Activity activity, boolean full) {
            if (full) {
                setFullScreen(activity);
            } else {
                quitFullScreen(activity);
            }
        }

        public static void setFullScreen(Activity activity) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        public static void quitFullScreen(Activity activity) {
            final WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity.getWindow().setAttributes(attrs);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        /**
         * 动态修改tab的模式
         *
         * @param tabLayout
         */
        public static void dynamicSetTablayoutMode(TabLayout tabLayout) {
            int tabTotalWidth = 0;
            int childCount = tabLayout.getChildCount();
            for (int i = 0; i < tabLayout.getChildCount(); i++) {
                final View view = tabLayout.getChildAt(i);
                view.measure(0, 0);
                tabTotalWidth += view.getMeasuredWidth();
            }
            int x = MeasureUtil.getScreenSize(tabLayout.getContext()).x;
            if (tabTotalWidth <= MeasureUtil.getScreenSize(tabLayout.getContext()).x) {
                tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                tabLayout.setTabMode(TabLayout.MODE_FIXED);
            } else {
                tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            }
        }

        /**
         * 解决InputMethodManager内存泄露现象
         *
         * @param destContext
         */
        public static void fixInputMethodManagerLeak(Context destContext) {
            if (destContext == null) {
                return;
            }

            InputMethodManager imm = (InputMethodManager) destContext
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm == null) {
                return;
            }

            String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
            Field f = null;
            Object obj_get = null;
            for (String param : arr) {
                try {
                    f = imm.getClass().getDeclaredField(param);
                    if (!f.isAccessible()) {
                        f.setAccessible(true);
                    } // author: sodino mail:sodino@qq.com
                    obj_get = f.get(imm);
                    if (obj_get != null && obj_get instanceof View) {
                        View v_get = (View) obj_get;
                        if (v_get
                                .getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                            f.set(imm, null); // 置空，破坏掉path to gc节点
                        } else {
                            // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        /*if (QLog.isColorLevel()) {
                            QLog.d(ReflecterHelper.class.getSimpleName(), QLog.CLR, "fixInputMethodManagerLeak break, context is not suitable, get_context=" + v_get.getContext()+" dest_context=" + destContext);
                        }*/
                            break;
                        }
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }


    public static Bitmap drawableToBitmap(Drawable drawable) {

         Bitmap bitmap = Bitmap.createBitmap(
               drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
               : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);

        //canvas.setBitmap(bitmap);

       drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

         drawable.draw(canvas);

         return bitmap;

    }

    public static Bitmap drawTextToBitmap(Context mContext, String mText, int left, int top1, int width, int height) {
        try {
            Resources resources = mContext.getResources();
            float scale = resources.getDisplayMetrics().density;
//            Bitmap bitmap = BitmapFactory.decodeResource(resources, resourceId);

//            android.graphics.Bitmap.Config bitmapConfig =   bitmap.getConfig();
//            // set default bitmap config if none
//            if(bitmapConfig == null) {
//                bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
//            }
            // resource bitmaps are imutable,
            // so we need to convert it to mutable one
//            bitmap = bitmap.copy(bitmapConfig, true);

//            Canvas canvas = new Canvas(bitmap);
            //自我创造bitmap
            Bitmap bitmap = Bitmap.createBitmap(
                    width,
                    height,
                     Bitmap.Config.ARGB_8888
                            );

            double scaleBai = 4.0 / 14.0;
            //字体的大小
            int Textsize = (int) (0.285 * width);

            Canvas canvas = new Canvas(bitmap);
            // new antialised Paint
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            // text color - #3D3D3D
            paint.setColor(Color.rgb(255,255, 255));
            // text size in pixels
            paint.setTextSize((int) (Textsize * scale));
            // text shadow
            paint.setShadowLayer(1f, 0f, 1f, Color.DKGRAY);
            paint.setTextAlign(Paint.Align.CENTER);

            // draw text to the Canvas center
            Rect bounds = new Rect();
            paint.getTextBounds(mText, 0, mText.length(), bounds);


            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
            float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom

            int baseLineY = (int) ((height/2) - top/2 - bottom/2);//基线中间点的y轴计算公式

            canvas.drawText(mText,width/2,baseLineY,paint);

//            int x = (bitmap.getWidth()- bounds.width())/4;
//            int y = (bitmap.getHeight()- bounds.height())/2;
//
//            canvas.drawText(mText, x * scale, y * scale, paint);

            return bitmap;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }

    }


}
