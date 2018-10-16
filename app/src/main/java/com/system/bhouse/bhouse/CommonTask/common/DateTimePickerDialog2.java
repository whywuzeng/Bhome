package com.system.bhouse.bhouse.CommonTask.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;

import com.system.bhouse.bhouse.CommonTask.common.CustomDatePicker.CustomDatePickerAlertDialog;
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

public  class DateTimePickerDialog2  implements CustomDatePickerAlertDialog.AntDatePickerDialogClickListener
{
    private  Integer year;
    private  int month;
    private  Integer day;
    private  Integer hour;
    private  Integer minute;
    private  Integer second;
    private String tvLable="选择";
    private String dateStringBack;
    private String timeStringBack;

    private final static class HOLDER{
        private final static DateTimePickerDialog2 mIntance=new DateTimePickerDialog2();
    }

    public static DateTimePickerDialog2 getInstance(){
        return HOLDER.mIntance;
    }

    @Override
    public void onClick(View view, int selectedYear, int selectedMonth, int selectedDay, int selectedHour, int selectedMinute) {

        if (mCallBack != null) {

            StringBuilder append = new StringBuilder()
                    .append(selectedYear)
                    .append("-").append(selectedMonth)
                    .append("-").append(selectedDay)
                    .append(" ").append(selectedHour)
                    .append(":").append(selectedMinute)
                    .append(":").append(second);

            String standardDateFromTime = DataFormatUtils.dayFromTime(append.toString());
            mCallBack.onDateSet(standardDateFromTime);
        }
    }

    public interface OnDateSetListener {

        /**
         * @param provinceStr The province string that was set.
         */
        void onDateSet(String provinceStr);
    }

    private  OnDateSetListener mCallBack;


    private DateTimePickerDialog2() {
    }

    public CustomDatePickerAlertDialog getCustomDialog(@NonNull Context mContext, @StyleRes int themeResId, OnDateSetListener callBack, String dateString){
        this.mCallBack=callBack;

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

//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);

        ///////////////////////////////////////////新dialog////////////////////////////////////////////////////////
        CustomDatePickerAlertDialog dialog = new CustomDatePickerAlertDialog(mContext, year, month, day,hour,minute);
//        dialog.setPositiveButton("确定", new CustomDatePickerAlertDialog.AntDatePickerDialogClickListener() {
//            @Override
//            public void onClick(View view, int year, int month, int day,int hour,int minute) {
//                LogUtils.e("你设置的日期是：", year + "/" + month + "/" + day);
//                ToastUtils.showShort("你设置的日期是%s：", year + "/" + month + "/" + day);
////                showTimePickerDialog(year, month, day);
//            }
//        });
        dialog.setPositiveButton("确定", this);
        dialog.setNegativeButton("取消", null);
        dialog.setTitle("请设置日期时间");
        dialog.setCancelable(false);
        return  dialog;
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

}
