package com.system.bhouse.bhouse.CommonTask.common;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.utils.TenUtils.DataFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2018-02-27.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.setup.utils
 */

public  class DateTimePickerDialog extends AlertDialog implements DialogInterface.OnClickListener
{
    private final DatePicker mDatePicker;
    private final TimePicker mTimePicker;
    private final Integer year;
    private final int month;
    private final Integer day;
    private final Integer hour;
    private final Integer minute;
    private final Integer second;
    private String tvLable="选择";
    private String dateStringBack;
    private String timeStringBack;

    public interface OnDateSetListener {

        /**
         * @param provinceStr The province string that was set.
         */
        void onDateSet(String provinceStr);
    }

    private final OnDateSetListener mCallBack;


    public DateTimePickerDialog(@NonNull Context context, @StyleRes int themeResId, OnDateSetListener callBack, String dateString) {
        super(context, themeResId);
        this.mCallBack=callBack;
        Context themeContext = getContext();
        setButton(BUTTON_POSITIVE, themeContext.getString(R.string.btn_dialog_done), this);
        setButton(BUTTON_NEGATIVE, themeContext.getString(R.string.btn_dialog_cancel), this);
        setIcon(0);
        LayoutInflater inflater =
                (LayoutInflater) themeContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_date_time_dialog, null);
        mDatePicker =(DatePicker) view.findViewById(R.id.DatePicker);
        mTimePicker =(TimePicker) view.findViewById(R.id.TimePicker);
        mTimePicker.setIs24HourView(true);
        if (dateString==null||dateString.isEmpty()) {
            dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(Calendar.getInstance().getTimeInMillis());
        }
        String dateformat = getDate(dateString);

        String[] date = dateformat.split("-");

         year = Integer.valueOf(date[0]);
         month = Integer.valueOf(date[1])-1;
         day = Integer.valueOf(date[2]);
        String[] splitDateString = dateString.split(" ");
        String dateTime = getTime(splitDateString[1]);
        String[] time = dateTime.split(":");
        hour = Integer.valueOf(time[0]);
         minute = Integer.valueOf(time[1]);
         second = Integer.valueOf(time[2]);

        setView(view);

        setTitle(this.tvLable);
        mDatePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker arg0, int year, int month,
                                      int day) {

                dateStringBack=year+"-"+(month+1)+"-"+day+" ";
            }
        });

        // 为TimePicker指定监听吕
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // TODO Auto-generated method stub
                timeStringBack=hourOfDay+":"+minute;
            }

        });

    }

    @Nullable
    private String getDate(String dateString) {
        String dateformat = null;
//        2018/3/25 14:46:51
        if (dateString.contains("/"))
        {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date parse = simpleDateFormat.parse(dateString);
                dateformat = new SimpleDateFormat("yyyy-MM-dd").format(parse);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date parse = simpleDateFormat.parse(dateString);
                dateformat = new SimpleDateFormat("yyyy-MM-dd").format(parse);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dateformat;
    }

    @Nullable
    private String getTime(String dateString) {
        String dateformat = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            Date parse = simpleDateFormat.parse(dateString);
            dateformat = new SimpleDateFormat("HH:mm:ss").format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateformat;
    }



    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which)
        {
            case BUTTON_POSITIVE:

                mDatePicker.clearFocus();
                mTimePicker.clearFocus();
                if (mCallBack != null) {
                    if (dateStringBack==null)
                    {
                        dateStringBack=year+"-"+(month+1)+"-"+day+" ";
                    }
                    if (timeStringBack==null)
                    {
                        timeStringBack= hour +":"+minute+":"+second;
                    }else{
                        timeStringBack+=":"+second;
                    }
//                    final Calendar c = Calendar.getInstance();
//                    c.set(year, monthOfYear, dayOfMonth);
                    String standardDateFromTime = DataFormatUtils.dayFromTime(dateStringBack + timeStringBack);
                    mCallBack.onDateSet(standardDateFromTime);
                }
                break;
            case BUTTON_NEGATIVE:

                break;
        }
    }
}
