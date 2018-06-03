package com.system.bhouse.bhouse.setup.utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by Administrator on 2018-02-27.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.setup.utils
 */

public class LabelNumPickerFragment extends DialogFragment implements LabelNumPickerDialog.OnDateSetListener {

    private LabelNumPickerDialog.OnDateSetListener mCallBack;

    private String[] labelNumString={"50","200","500","1000","全部"};
    private String textLable="记录数";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LabelNumPickerDialog labelNumPickerDialog = new LabelNumPickerDialog(getActivity(), 0, this, this.labelNumString, this.textLable);
        labelNumPickerDialog.setLabelNumString(labelNumString);
        labelNumPickerDialog.setTvLable("记录数");
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
