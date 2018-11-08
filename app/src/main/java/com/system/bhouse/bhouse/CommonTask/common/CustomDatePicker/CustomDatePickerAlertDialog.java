package com.system.bhouse.bhouse.CommonTask.common.CustomDatePicker;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.utils.blankutils.LogUtils;

import java.util.Calendar;

/**
 * Created by Administrator on 2018-09-30.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.common.CustomDatePicker
 */

public class CustomDatePickerAlertDialog {

    private final Context context;
    private final AlertDialog                      dialog;             //dialog对象
    private       View                             dialogView;         //dialogView
    private       int                              year;
    private       int                              month;
    private       int                              day;

    private       int                              mHour;
    private       int                              mMinute;

    private DatePicker datePicker;
    private LinearLayout ll_root_dialog;
    private TextView tvSubTitle;
    private TextView tvTitle;
    private TextView tvSkipBT;
    private TextView tvCancleBT;
    private TextView tvConfirmBT;
    private TimePicker mTimePicker;

    /**
     * @param context     上下文
     * @param year        年份，具体年份   （此处三个日期 同 Calendar 中取出的值）
     * @param monthOfYear 月份，取值 0-11
     * @param dayOfMonth  天，取值1-31
     */
    public CustomDatePickerAlertDialog(Context context, int year, int monthOfYear, int dayOfMonth,int hour, int minute) {
        this.context = context;
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.datedialog);
        dialog = builder.create();

        initDate(year, monthOfYear, dayOfMonth,hour,minute);
        initDialogView();
        initView();
    }

    private void initView() {
         ll_root_dialog = dialogView.findViewById(R.id.ll_root_antDialog);
         tvSubTitle = dialogView.findViewById(R.id.tv_subTitle);
        tvTitle =(TextView)dialogView.findViewById(R.id.tv_title);
        tvSkipBT =(TextView)dialogView.findViewById(R.id.tv_skipBT);
         tvCancleBT =(TextView)dialogView.findViewById(R.id.tv_cancleBT);
         tvConfirmBT =(TextView)dialogView.findViewById(R.id.tv_confirmBT);
        yearMonthDay();
    }

    /**
     * 初始化日期，如果外部在初始化传递的都是0 ，则使用该日期去初始化DatePicker
     *
     * @param year        年份，具体年份   （此处三个日期 同 Calendar 中取出的值）
     * @param monthOfYear 月份，取值 0-11
     * @param dayOfMonth  天，取值1-31
     */
    private void initDate(int year, int monthOfYear, int dayOfMonth,int hour, int minute) {
        if (year == 0 || dayOfMonth == 0) {
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);   //取值 0-11
            day = calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            this.year = year;
            month = monthOfYear;
            day = dayOfMonth;
        }
        LogUtils.e("初始化时的年月日是：", year + "/" + (month + 1) + "/" + day);

        if (hour == -1 || minute == -1) {
            Calendar calendar = Calendar.getInstance();
            mHour = calendar.get(Calendar.HOUR_OF_DAY);
            mMinute = calendar.get(Calendar.MINUTE);
        } else {
            mHour = hour;
            mMinute = minute;
        }
        LogUtils.e("初始化时的时间是：", mHour + "/" + mMinute);

    }

    private void initDialogView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        dialogView = inflater.inflate(R.layout.custom_datepicker_dialog_ant, null, false);
        dialog.setView(dialogView);     //设置view
        setLayoutByPx(0, 0);          //设置宽高
        if (Build.VERSION.SDK_INT >= 21) {  //21之前设置背景的时候依旧会有白色边框
            setBackGroundDrawableResource(0);
        }
        setDimAmount(0.15f);
        datePicker = dialogView.findViewById(R.id.datePicker_customDatePickerDialog);
        mTimePicker =(TimePicker)dialogView.findViewById(R.id.timePicker_customTimePickerDialog);
        initDatePicker();
    }

    private void initDatePicker() {

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int selectedyear, int monthOfYear, int dayOfMonth) {
                year = selectedyear;
                month = monthOfYear;
                day = dayOfMonth;
                yearMonthDay();
            }
        });

        mTimePicker.setIs24HourView(true);
        mTimePicker.setCurrentHour(mHour);     //0-23
        mTimePicker.setCurrentMinute(mMinute); //0-59
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                LogUtils.e("设置的时间是：", hourOfDay + "/" + minute);
                yearMonthDay();
            }
        });
    }

    /**
     * 展示dialog
     */
    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * 关闭dialog
     */
    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 返回dialog的view对象
     */
    public View getDialogView() {
        return dialogView;
    }

    /**
     * 返回dialog对象
     */
    public Dialog getDialogObj() {
        return dialog;
    }

    /**
     * 设置dialog的宽高信息,单位px
     * 注意：不推荐用该方法，由于标注是按照IOS标准标的像素，如果直接传递像素，在安卓设备上会产生较严重的偏差
     */
    public void setLayoutByPx(final int width, final int height) {
        final Window window = dialog.getWindow();
        if (null != window) {
            Display display = window.getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            final int windowWidth = metrics.widthPixels;

            if (height != 0) {      //如果不为0，则指定LL的高度填充父窗体，也就是填满指定的高度值,避免出现内容小于指定高度时，内容底部显示白色块
                LinearLayout ll_root_dialog = dialogView.findViewById(R.id.ll_root_antDialog);
                ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) ll_root_dialog.getLayoutParams();
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                ll_root_dialog.setLayoutParams(layoutParams);
                ll_root_dialog.setGravity(Gravity.CENTER);
            }

            dialogView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
                                           int oldRight, int oldBottom) {
                    int finalWidth = width <= 0 ? (int) (windowWidth * 0.76) : width;
                    int finalHeight = height <= 0 ? FrameLayout.LayoutParams.WRAP_CONTENT : height;
                    //                    LogUtils.e("宽高", finalWidth + "/" + finalHeight);
                    window.setLayout(finalWidth, finalHeight);
                    window.setGravity(Gravity.CENTER);

                }
            });
        }
    }

    /**
     * 设置dialog的宽高信息，单位dp
     * 推荐使用这种，先将标注图上的px 按照2：1 转成dp,然后调用该方法
     */
    public void setLayoutByDp(final int width, final int height) {
        final Window window = dialog.getWindow();
        if (null != window) {
            Display display = window.getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            final int windowWidth = metrics.widthPixels;

            if (height != 0) {      //如果不为0，则指定LL的高度填充父窗体，也就是填满指定的高度值,避免出现内容小于指定高度时，内容底部显示白色块
                ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) ll_root_dialog.getLayoutParams();
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                ll_root_dialog.setLayoutParams(layoutParams);
                ll_root_dialog.setGravity(Gravity.CENTER);
            }

            dialogView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
                                           int oldRight, int oldBottom) {
                    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                    int widthPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, metrics);
                    int heightPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, metrics);
                    int finalWidth = widthPx <= 0 ? (int) (windowWidth * 0.76) : widthPx;
                    int finalHeight = heightPx <= 0 ? FrameLayout.LayoutParams.WRAP_CONTENT : heightPx;
                    //                    LogUtils.e("宽高", widthPx + "/" + heightPx);
                    window.setLayout(finalWidth, finalHeight);
                }
            });
        }
    }

    /**
     * 设置dialog的宽高信息，无单位
     * 也推荐使用这种，按照比率设置宽高
     *
     * @param widthRate  内容区域占屏幕宽度的多少，取值（0，1]
     * @param heightRate 内容区域占屏幕高度的多少,取值 （0，1]
     */
    public void setLayoutByRate(final float widthRate, final float heightRate) {
        final Window window = dialog.getWindow();
        if (null != window) {
            Display display = window.getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            final int windowWidth = metrics.widthPixels;
            final int windowHeight = metrics.heightPixels;

            if (heightRate != 0) {      //如果不为0，则指定LL的高度填充父窗体，也就是填满指定的高度值,避免出现内容小于指定高度时，内容底部显示白色块
                ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) ll_root_dialog.getLayoutParams();
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                ll_root_dialog.setLayoutParams(layoutParams);
                ll_root_dialog.setGravity(Gravity.CENTER);
            }

            dialogView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
                                           int oldRight, int oldBottom) {
                    int finalWidth = widthRate <= 0 ? (int) (windowWidth * 0.76) : (int) (windowWidth * widthRate);
                    int finalHeight = heightRate <= 0 ? FrameLayout.LayoutParams.WRAP_CONTENT : (int) (windowHeight *
                            heightRate);
                    //                    LogUtils.e("宽高", finalWidth + "/" + finalHeight);
                    window.setLayout(finalWidth, finalHeight);
                }
            });
        }
    }

    /**
     * 设置dialog的背景--传入资源id
     */
    public void setBackGroundDrawableResource(int drawableResId) {
        Window window = dialog.getWindow();
        if (null != window) {
            if (0 == drawableResId) {
                drawableResId = R.drawable.shape_bk_rect_cornor_white;
            }
            window.setBackgroundDrawableResource(drawableResId);
        }
    }

    /**
     * 设置背景图--传入drawable对象
     */
    public void setBackGroundDrawable(Drawable drawable) {
        Window window = dialog.getWindow();
        if (null != window) {
            if (null == drawable) {
                drawable = context.getResources().getDrawable(R.drawable.shape_bk_rect_cornor_white);
            }
            window.setBackgroundDrawable(drawable);
        }
    }

    /**
     * 设置背景图--根据传入的color值生成对应填充色的圆角背景图
     *
     * @param colorInt      色值
     * @param conorRadiusPx 圆角半径,单位PX
     */
    public void setBackGroundDrawable(
            @ColorInt
                    int colorInt, int conorRadiusPx) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(colorInt);
        drawable.setCornerRadius(conorRadiusPx);
        drawable.setShape(GradientDrawable.RECTANGLE);

        Window window = dialog.getWindow();
        if (null != window) {
            window.setBackgroundDrawable(drawable);
        }

    }

    /**
     * 设置确定按钮的问题及其点击事件,
     * 传入的事件监听为null时，会关闭dialog
     */
    public void setPositiveButton(String des, final AntDatePickerDialogClickListener clickListener) {
        tvConfirmBT.setVisibility(View.VISIBLE);
        tvConfirmBT.setText(des);
        tvConfirmBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != clickListener) {
                    clickListener.onClick(v, year, month + 1, day,mHour,mMinute);
                }
                dismissDialog();
            }
        });
    }

    public void setPositiveButton(int strResId, final AntDatePickerDialogClickListener clickListener) {
        tvConfirmBT.setVisibility(View.VISIBLE);
        tvConfirmBT.setText(context.getResources().getString(strResId));
        tvConfirmBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != clickListener) {
                    clickListener.onClick(v, year, month + 1, day,mHour,mMinute);
                }
                dismissDialog();
            }
        });
    }

    /**
     * 取消按钮的点击事件
     */
    public void setNegativeButton(String des, final AntDatePickerDialogClickListener clickListener) {
        tvCancleBT.setVisibility(View.VISIBLE);
        tvCancleBT.setText(des);
        tvCancleBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != clickListener) {
                    clickListener.onClick(v, year, month + 1, day,mHour,mMinute);
                }
                dismissDialog();
            }
        });
    }

    public void setNegativeButton(int strResId, final AntDatePickerDialogClickListener clickListener) {
        tvCancleBT.setVisibility(View.VISIBLE);
        tvCancleBT.setText(context.getResources().getString(strResId));
        tvCancleBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != clickListener) {
                    clickListener.onClick(v, year, month + 1, day,mHour,mMinute);
                }
                dismissDialog();
            }
        });
    }

    /**
     * 跳过按钮的点击事件
     */
    public void setSkipButton(String des, final AntDatePickerDialogClickListener clickListener) {
        tvSkipBT.setVisibility(View.VISIBLE);
        tvSkipBT.setText(des);
        tvSkipBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != clickListener) {
                    clickListener.onClick(v, year, month + 1, day,mHour,mMinute);
                }
                dismissDialog();
            }
        });
    }

    public void setSkipButton(int strResId, final AntDatePickerDialogClickListener clickListener) {
        tvSkipBT.setVisibility(View.VISIBLE);
        tvSkipBT.setText(context.getResources().getString(strResId));
        tvSkipBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != clickListener) {
                    clickListener.onClick(v, year, month + 1, day,mHour,mMinute);
                }
                dismissDialog();
            }
        });
    }

    /**
     * 设置副标题 为年月日格式
     */
    public void yearMonthDay(){
        String format ="%d年%d月%d日%d时%d分";
        String timeFormat = String.format(format, year, month+1, day, mHour, mMinute);
        setSubTitle(timeFormat);
    }


    /**
     * 设置确认按钮的文字颜色
     */
    public void setPositiveButtonTextColor(
            @ColorInt
                    int color) {
        tvConfirmBT.setTextColor(color);
    }

    public void setPositiveButtonTextColor(ColorStateList colorStateList) {
        tvConfirmBT.setTextColor(colorStateList);
    }

    /**
     * 设置取消按钮的字体颜色
     */
    public void setNegativeButtonTextColor(
            @ColorInt
                    int color) {
        tvCancleBT.setTextColor(color);
    }

    public void setNegativeButtonTextColor(ColorStateList colorStateList) {
        tvCancleBT.setTextColor(colorStateList);
    }

    /**
     * 设置跳过按钮的字体颜色
     */
    public void setSkipButtonTextColor(
            @ColorInt
                    int color) {
        tvSkipBT.setTextColor(color);
    }

    public void setSkipButtonTextColor(ColorStateList colorStateList) {
        tvSkipBT.setTextColor(colorStateList);
    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
    }

    public void setTitle(int strResId) {
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(context.getResources().getString(strResId));
    }

    /**
     * 设置副标题
     */
    public void setSubTitle(String title) {
        tvSubTitle.setVisibility(View.VISIBLE);
        tvSubTitle.setText(title);
    }

    public void setSubTitle(int strResId) {
        tvSubTitle.setVisibility(View.VISIBLE);
        tvSubTitle.setText(context.getResources().getString(strResId));
    }

    /**
     * 设置副标题的点击事件
     */
    public void setSubTitleClickListener(final AntDatePickerDialogClickListener clickListener) {
        tvSubTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != clickListener) {
                    dismissDialog();
                    clickListener.onClick(tvSubTitle, year, month, day,mHour,mMinute);
                }
            }
        });
    }


    /**
     * 更改主标题文字的大小
     */
    public void setTitleTextSize(int sizeSP) {
        if (sizeSP <= 0) {
            sizeSP = 14;
        }
        TextView tvTitle = dialogView.findViewById(R.id.tv_title);
        tvTitle.setTextSize(sizeSP);
    }

    /**
     * 修改Dialog阴影区域的灰度百分比
     * <p>
     * 取值 0-1.
     */
    public void setDimAmount(float rate) {
        Window window = dialog.getWindow();
        if (null != window) {
            if (rate < 0) {
                rate = 0;
            } else if (rate > 1) {
                rate = 1;
            }
            window.setDimAmount(rate);
        }
    }

    /**
     * 点击非内容区域是否可以关闭
     */
    public void setCancelable(boolean bool) {
        dialog.setCancelable(bool);
    }

    /**
     * 对外暴露点击事件的自定义接口
     */
    public interface AntDatePickerDialogClickListener {
        void onClick(View view, int selectedYear, int selectedMonth, int selectedDay,int selectedHour ,int selectedMinute);
    }
}
