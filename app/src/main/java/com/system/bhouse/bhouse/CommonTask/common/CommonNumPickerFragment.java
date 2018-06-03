package com.system.bhouse.bhouse.CommonTask.common;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
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

    private String[] labelNumString ;

    private String textLable;

    public CommonNumPickerFragment(String[] labelNumString, String textLable) {
        this.labelNumString = labelNumString;
        this.textLable = textLable;
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