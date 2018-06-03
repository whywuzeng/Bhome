package com.system.bhouse.utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.task.view.LoadingView;

/**
 * Created by Administrator on 2017-12-26.
 */

public class LoadingViewUtils extends DialogFragment {

    Dialog mDialog;
    LoadingView loadingView;

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
        loadingView = new LoadingView(getActivity());
        if (mDialog == null) {
            mDialog = new Dialog(getActivity(), R.style.cart_dialog);
            mDialog.setContentView(loadingView);
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.getWindow().setGravity(Gravity.CENTER);
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
