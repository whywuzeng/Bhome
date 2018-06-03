package com.system.bhouse.bhouse.setup.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.system.bhouse.bhouse.R;

/**
 * Created by Administrator on 2018-02-27.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.setup.utils
 */

public  class LabelNumPickerDialog extends AlertDialog implements DialogInterface.OnClickListener
{

    private NumberPicker mLabelnumPicker;

    public String[] getLabelNumString() {
        return labelNumString;
    }

    public void setLabelNumString(String[] labelNumString) {
        this.labelNumString = labelNumString;
    }

    private String labelNumString[]={""};

    public String getTvLable() {
        return tvLable;
    }

    public void setTvLable(String tvLable) {
        this.tvLable = tvLable;
    }

    private String tvLable="选择";

    public interface OnDateSetListener {

        /**
         * @param provinceStr The province string that was set.
         */
        void onDateSet(String provinceStr);
    }

    private final OnDateSetListener mCallBack;


    public LabelNumPickerDialog(@NonNull Context context, @StyleRes int themeResId, OnDateSetListener callBack, String[] labelNumString, String textLable) {
        super(context, themeResId);
        this.mCallBack=callBack;
        Context themeContext = getContext();
        setButton(BUTTON_POSITIVE, themeContext.getString(R.string.btn_dialog_done), this);
        setButton(BUTTON_NEGATIVE, themeContext.getString(R.string.btn_dialog_cancel), this);
        setIcon(0);
        LayoutInflater inflater =
                (LayoutInflater) themeContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_user_labelnum_dialog, null);
        TextView viewById =(TextView) view.findViewById(R.id.numberPicker_lable);
        viewById.setText(tvLable);
        setView(view);
        this.labelNumString=labelNumString;

        mLabelnumPicker = (NumberPicker) view.findViewById(R.id.labelnum);
        mLabelnumPicker.setOnValueChangedListener(valueChangeListener);

        setLabelNum(0,"");
        setTitle(textLable);
    }


    private int lIndex;

    private NumberPicker.OnValueChangeListener valueChangeListener=(picker, oldVal, newVal) -> {
        if (oldVal != newVal) {
            lIndex = newVal;
            setLabelNum(lIndex, "");
        }
    };

    private void setLabelNum(int lIndex, String lebelnum) {

        mLabelnumPicker.setDisplayedValues(null);
        mLabelnumPicker.setMinValue(0);
        mLabelnumPicker.setMaxValue(this.labelNumString.length - 1);
        mLabelnumPicker.setDisplayedValues(this.labelNumString);
        mLabelnumPicker.setValue(lIndex);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which)
        {
            case BUTTON_POSITIVE:

                mLabelnumPicker.clearFocus();
                if (mCallBack != null) {
                    mCallBack.onDateSet(getLabelNumStr());
                }
                break;
            case BUTTON_NEGATIVE:

                break;
        }
    }

    private String getLabelNumStr() {
        String lName = "";
        lName = mLabelnumPicker.getDisplayedValues()[lIndex];
        return lName;
    }
}
