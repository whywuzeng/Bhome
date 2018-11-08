package com.system.bhouse.bhouse.CommonTask.common;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.system.bhouse.bhouse.CommonTask.common.CustomDatePicker.CustomDatePickerAlertDialog;
import com.system.bhouse.bhouse.setup.utils.LabelNumPickerDialog;
import com.system.bhouse.bhouse.setup.utils.onMutiDataSetListener;

/**
 * Created by Administrator on 2018-03-13.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.common
 */

public class CommonDateTimePickerFragment extends DialogFragment implements DateTimePickerDialog2.OnDateSetListener {

    private LabelNumPickerDialog.OnDateSetListener mCallBack;
    private onMutiDataSetListener listener;

    private String[] labelNumString;

    private String textLable;
    public final static String PARAM_DATA = "param_data";
    public final static String PARAM_TYPE = "param_type";
    public final static String PARAM_POSITION = "param_position";

    private String dateString = "";
    private String dateType = "";
    private int datePosition;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null) {
            dateString = getArguments().getString(PARAM_DATA);
            dateType = getArguments().getString(PARAM_TYPE);
            datePosition = getArguments().getInt(PARAM_POSITION);
        }
        CustomDatePickerAlertDialog labelNumPickerDialog = DateTimePickerDialog2.getInstance().getCustomDialog(getActivity(), 0, this, dateString);

        return labelNumPickerDialog.getDialogObj();
    }


    public void setCallBack(LabelNumPickerDialog.OnDateSetListener mCallBack) {
        this.mCallBack = mCallBack;
    }

    public void setListener(onMutiDataSetListener listener)
    {
        this.listener=listener;
    }

    @Override
    public void onDateSet(String provinceStr) {
        if (mCallBack != null)
            mCallBack.onDateSet(provinceStr);
        if (listener!=null)
        listener.onMutiDateSet(provinceStr,  dateType,  datePosition);
    }
}