package com.system.bhouse.utils.apk;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.system.bhouse.bhouse.R;

/**
 * Created by Administrator on 2018-10-09.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.utils.apk
 */

public class LoadingApkProgress {

    private final AlertDialog alertDialog;
    private final Context mContext;
    private ProgressBar progressBar;
    private TextView title;

    public LoadingApkProgress(Context mContext){
        alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        this.mContext =mContext;
    }

    public void beginDialog(){
        alertDialog.show();
        final Window window = alertDialog.getWindow();
        if (window!=null)
        {
            window.setContentView(R.layout.dialog_loadprogress_layout);
            window.setGravity(Gravity.CENTER);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width=WindowManager.LayoutParams.MATCH_PARENT;
            params.flags=WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            params.dimAmount=0.7f;
            window.setAttributes(params);


            progressBar = window.findViewById(R.id.progress);
            title = window.findViewById(R.id.tv_progress_text);
        }
    }

    public void setProgressBar(int progress){
        progressBar.setProgress(progress);
        final String stringFormat = mContext.getResources().getString(R.string.progress_text);
        title.setText(String.format(stringFormat,progress,"%"));
    }

    public void cancel(){
        if (alertDialog.isShowing()) {
            alertDialog.cancel();
        }
    }
}
