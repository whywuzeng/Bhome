package com.system.bhouse.utils.custom;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.system.bhouse.base.App;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.utils.TenUtils.DensityUtils;
import com.system.bhouse.utils.blankutils.ScreenUtils;
import com.system.bhouse.utils.blankutils.ToastUtils;
import com.system.bhouse.utils.blankutils.Utils;

/**
 * Created by Administrator on 2018-09-25.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.utils.custom
 */

public class CustomToast {

    private CustomToast(int gravity, int xOffset, int yOffset, int bgColor, int msgColor, int msgTextSize, String text) {
        this.gravity = gravity;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.bgColor = bgColor;
        this.msgColor = msgColor;
        this.msgTextSize = msgTextSize;
        this.text = text;
    }
    //这是自定义布局的
    public void buildLong(){
        ToastUtils.setGravity(gravity,xOffset,yOffset);
        ToastUtils.setBgColor(bgColor);
        ToastUtils.setMsgColor(msgColor);
        ToastUtils.setMsgTextSize(msgTextSize);
        showCustomLong(R.layout.custom_toast_view,text);
    }
    //这是自定义布局的
    public void buildShort(){
        ToastUtils.setGravity(gravity,xOffset,yOffset);
        ToastUtils.setBgColor(bgColor);
        ToastUtils.setMsgColor(msgColor);
        ToastUtils.setMsgTextSize(msgTextSize);

        showCustomShort(R.layout.custom_toast_view,text);
    }

    private static View showCustomLong(@LayoutRes final int layoutId,String text) {
        final View view = getView(layoutId);
        //要保证布局有这个id
        TextView message= view.findViewById(R.id.toastMessage);
        message.setText(text);
        ToastUtils.show(view, Toast.LENGTH_LONG);
        return view;
    }

    private static View showCustomShort(@LayoutRes final int layoutId, String text) {
        final View view = getView(layoutId);
        //要保证布局有这个id
        TextView message= view.findViewById(R.id.toastMessage);
        message.setText(text);
        ToastUtils.show(view, Toast.LENGTH_SHORT);
        return view;
    }

    private static View getView(@LayoutRes final int layoutId) {
        LayoutInflater inflate =
                (LayoutInflater) Utils.getApp().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //noinspection ConstantConditions
        return inflate.inflate(layoutId, null);
    }

    /**
     * 警告toast warning
     */
    public static void showWarning(){
        int screenHeight = ScreenUtils.getScreenHeight();
        int topOffset = screenHeight / 4;
        ToastUtils.setGravity(Gravity.TOP,0,topOffset);
//        ToastUtils.setBgColor(App.getContextApp().getResources().getColor(R.color.primary_dark));
        ToastUtils.setMsgColor(App.getContextApp().getResources().getColor(R.color.green));
//        ToastUtils.setBgResource(R.drawable.square_toast_bg);
        ToastUtils.setMsgTextSize((int) DensityUtils.px2sp(App.getContextApp(),24));
//        ToastUtils.showLong("此二维码无数据");
        ToastUtils.showCustomLong(R.layout.custom_toast_view);
    }

    /**
     * 系统默认的toast
     */
    public static void showDefault(final String format,int duration,final Object... args) {
        int screenHeight = ScreenUtils.getScreenHeight();
        int topOffset = screenHeight / 8;
        ToastUtils.setGravity(Gravity.BOTTOM,0,topOffset);

        if (duration == Toast.LENGTH_LONG) {
            ToastUtils.showLong(format,args);
        }
        else if (duration == Toast.LENGTH_SHORT) {
            ToastUtils.showShort(format,args);
        }
    }

    public static void showDefault(CharSequence text, int duration) {
        ToastUtils.setGravity(Gravity.BOTTOM,0,0);
        if (duration == Toast.LENGTH_LONG) {
            ToastUtils.showLong(text);
        }
        else if (duration == Toast.LENGTH_SHORT) {
            ToastUtils.showShort(text);
        }
    }

    private final int gravity;
    private final int xOffset;
    private final int yOffset;
    private final int bgColor;
    private final int msgColor;
    private final int msgTextSize;
    private final String text;

    public static final class CustomToastBuild{

        private  int gravity;
        private  int xOffset;
        private  int yOffset;
        private  int bgColor;
        private  int msgColor;
        private  int msgTextSize;
        private  String text;

        public CustomToastBuild setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public CustomToastBuild setxOffset(int xOffset) {
            this.xOffset = xOffset;
            return this;
        }

        public CustomToastBuild setyOffset(int yOffset) {
            this.yOffset = yOffset;
            return this;
        }

        public CustomToastBuild setBgColor(int bgColor) {
            this.bgColor = bgColor;
            return this;
        }

        public CustomToastBuild setMsgColor(int msgColor) {
            this.msgColor = msgColor;
            return this;
        }

        public CustomToastBuild setMsgTextSize(int msgTextSize) {
            this.msgTextSize = msgTextSize;
            return this;
        }

        public CustomToastBuild setText(String text) {
            this.text = text;
            return this;
        }

        public CustomToast build(){
            return new CustomToast(this.gravity,this.xOffset,this.yOffset,this.bgColor,this.msgColor,this.msgTextSize,this.text);
        }

    }
}
