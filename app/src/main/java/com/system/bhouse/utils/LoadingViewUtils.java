package com.system.bhouse.utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.ui.loading.DYLoadingView;
import com.system.bhouse.utils.dialog.BaseDialogFragment;

/**
 * Created by Administrator on 2017-12-26.
 */

public class LoadingViewUtils extends BaseDialogFragment {

    Dialog mDialog;
//    LoadingView loadingView;
    DYLoadingView loadingView;

    public LoadingViewUtils() {
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        Window window = getDialog().getWindow();
        window.getDecorView().setPadding(0,0,0,0);
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.0f;
        windowParams.width=WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(windowParams);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        loadingView = new LoadingView(getActivity());
        loadingView =new DYLoadingView(getActivity());
        if (mDialog == null) {
            mDialog = new Dialog(getActivity(), R.style.cart_dialog);
            mDialog.setContentView(loadingView);
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.getWindow().setGravity(Gravity.CENTER);
            //把这个动画效果 关了 以后就怪怪的
            mDialog.getWindow().getAttributes().windowAnimations= R.style.fade_dialog;

            View view = mDialog.getWindow().getDecorView();
        }
        return mDialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mDialog = null;
        System.gc();
    }
}
