package com.system.bhouse.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
    private static boolean isResume;


    public ProgressUtils(){}

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
        String tag = LoadingViewUtils.class.getName();

//        List<Fragment> fragments = ((AppCompatActivity) context).getSupportFragmentManager().getFragments();
//        if (fragments.size()>0)
//        {
//            FragmentManager fragmentManager = fragments.get(0).getFragmentManager();
//
////            FragmentManager fragmentManager = fragments.get(0).getChildFragmentManager();
//            FragmentManager childFragmentManager = fragmentManager.getFragments().get(0).getChildFragmentManager();
////            (((FragmentManagerImpl) fragmentManager).mAdded).get(0).getChildFragmentManager();
//            viewUtils = getnewIntanceFragment(context, tag, fragmentManager);
//            viewUtils.show(fragmentManager,tag,fragments.get(0).isResume());
//        }else {
//        isResume = ((WWBaseActivity) context).isResumed;
        viewUtils = getnewIntanceFragment(context, tag, ((AppCompatActivity) context).getSupportFragmentManager());

        try {
            viewUtils.show(((AppCompatActivity) context).getSupportFragmentManager(),tag,true);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        }

//        if (viewUtils != null && viewUtils.getDialog() != null
//                && viewUtils.getDialog().isShowing()) {
//            //dialog is showing so do something
//        }
//        else {
//            //dialog is not showing
//            viewUtils.show(((AppCompatActivity) context).getSupportFragmentManager(), "");
//        }
    }

    private static LoadingViewUtils getnewIntanceFragment(Context context, String tag,FragmentManager fm) {
        Fragment fragment = fm.findFragmentByTag(tag);
        if (fragment==null)
        {
            fragment= Fragment.instantiate(context,tag);
            LoadingViewUtils loadFragment = (LoadingViewUtils) fragment;
            loadFragment.setCancelable(false);
            return loadFragment;
        }

        return  (LoadingViewUtils) fragment;
    }

    public static void DisMissProgress() {

//        if(viewUtils!=null &&  viewUtils.getDialog()!=null
//                && viewUtils.getDialog().isShowing()) {
//            viewUtils.dismissAllowingStateLoss();
//        }
//            viewUtils.dismiss(isResume);
        viewUtils.dismissAllowingStateLoss();
    }
}
