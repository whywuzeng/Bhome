package com.system.bhouse.bhouse.CommonTask.common;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.system.bhouse.bhouse.setup.utils.LabelNumPickerDialog;

/**
 * Created by Administrator on 2018-03-10.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.common
 */

public class CommonNumPickerFragment extends DialogFragment implements LabelNumPickerDialog.OnDateSetListener {

    private LabelNumPickerDialog.OnDateSetListener mCallBack;

    private final static String LABELNUMSTRING_ARG="labelnumstring_arg";

    private final static String TEXTLABLE_ARG ="textlable_arg";

    private String[] labelNumString ;

    private String textLable;

    public static CommonNumPickerFragment newInstance(String[] labelNumString, String textLable) {
        Bundle args = new Bundle();
        args.putStringArray(LABELNUMSTRING_ARG,labelNumString);
        args.putString(TEXTLABLE_ARG,textLable);
        CommonNumPickerFragment fragment = new CommonNumPickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            final Bundle arguments = getArguments();
            this.labelNumString = arguments.getStringArray(LABELNUMSTRING_ARG);
            this.textLable = arguments.getString(TEXTLABLE_ARG);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LabelNumPickerDialog labelNumPickerDialog = new LabelNumPickerDialog(getActivity(), 0, this,this.labelNumString,this.textLable);
        labelNumPickerDialog.setLabelNumString(labelNumString);
        labelNumPickerDialog.setTvLable(textLable);
        return labelNumPickerDialog;
    }


    public void setCallBack(LabelNumPickerDialog.OnDateSetListener mCallBack) {
        this.mCallBack = mCallBack;
    }

    @Override
    public void onDateSet(String provinceStr) {
        if (mCallBack != null)
            mCallBack.onDateSet(provinceStr);
    }
}