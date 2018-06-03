package com.system.bhouse.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2016-5-24.
 * ClassName: com.system.bhouse.utils
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class ProgressUtils {
    private static ProgressDialog progressDialog;

    private static ProgressUtils mProgressUtils;

    private  static LoadingViewUtils viewUtils;



    public ProgressUtils(){};

    public static   ProgressUtils getInstance(){
        if (mProgressUtils==null) {

          synchronized (ProgressUtils.class) {
              if (mProgressUtils == null) {
                  mProgressUtils = new ProgressUtils();
              }
          }
        }
        return mProgressUtils;
    }

    public static void ShowProgress(Context context) {
//        progressDialog = ProgressDialog.show(context, "正在连接", "请等待......", true, false);
        if (viewUtils==null) {
            viewUtils = new LoadingViewUtils();
        }

            if(viewUtils!=null &&  viewUtils.getDialog()!=null
                    && viewUtils.getDialog().isShowing()) {
                //dialog is showing so do something

            } else {
                //dialog is not showing
                viewUtils.show(((AppCompatActivity) context).getSupportFragmentManager(), "");
            }

    }

    public static void DisMissProgress() {

        if(viewUtils!=null &&  viewUtils.getDialog()!=null
                && viewUtils.getDialog().isShowing()) {
            viewUtils.dismiss();
        }
    }
}
