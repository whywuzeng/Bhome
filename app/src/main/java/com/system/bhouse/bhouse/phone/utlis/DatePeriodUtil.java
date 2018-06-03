package com.system.bhouse.bhouse.phone.utlis;

import com.system.bhouse.bhouse.phone.common.FileConst;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePeriodUtil {
    public static final int DELTA_TIME_CURRENT_AND_SAME_WEEK = 7;
    public static final int DELTA_TIME_DATE_AND_TIME = 4;
    public static final int DELTA_TIME_FINISH_WITHOUT_MONTH = 6;
    public static final int DELTA_TIME_FINISH_WITHOUT_YEAR = 5;
    public static final int DELTA_TIME_WITHOUT_DATE = 1;
    public static final int DELTA_TIME_WTTHOUT_TIME = 2;
    public static final SimpleDateFormat df_date = new SimpleDateFormat("M月d日");
    public static final SimpleDateFormat df_date_year = new SimpleDateFormat("yyyy年M月d日");
    public static final SimpleDateFormat df_only_date = new SimpleDateFormat("d日");
    public static final SimpleDateFormat df_time = new SimpleDateFormat("H:mm");

    public static final String getCheckTime(long j) {
        return isYesterdayOrToday(new Date(j)) == -1 ? DateFormatUtil.SHORT_TIME.format(Long.valueOf(j)) : isCurrentYear(j) ? DateFormatUtil.WORKLOG_TIME.format(Long.valueOf(j)) : DateFormatUtil.LEAVE_FULL_FORMATE_WITH_SPACE.format(Long.valueOf(j));
    }

    public static String getChinaWeekDayFromNum(int i) {
        switch (i) {
            case 1:
                return "日";
            case 2:
                return "一";
            case 3:
                return "二";
            case 4:
                return "三";
            case 5:
                return "四";
            case 6:
                return "五";
            case 7:
                return "六";
            default:
                return "";
        }
    }

    public static String getDateString(Date date, int i) {
        return date.getYear() == i ? df_date.format(date) : df_date_year.format(date);
    }
//
//    public static String getFriendlyDate(long j, boolean z) {
//        Date date = new Date(System.currentTimeMillis());
//        Date date2 = new Date(j);
//        int isYesterdayOrToday = isYesterdayOrToday(date2);
//        return isYesterdayOrToday == -1 ? z ? getFriendlyTime(j, date) : "今天" : isYesterdayOrToday == 0 ? "昨天" : isYesterdayOrToday == 1 ? "明天" : isSameWeek(date2, date) ? getWeekDay(date2) : isCurrentYear(j) ? DateFormatUtil.SHORT_DATE.format(Long.valueOf(j)) : DateFormatUtil.SHORT_DATE_WITH_YEAR.format(Long.valueOf(j));
//    }
//
//    public static String getFriendlyTime(long j, Date date) {
//        int time = (int) ((date.getTime() - j) / Util.MILLSECONDS_OF_HOUR);
//        return time == 0 ? ((int) ((date.getTime() - j) / Util.MILLSECONDS_OF_MINUTE)) <= 1 ? "刚刚" : Math.max((date.getTime() - j) / Util.MILLSECONDS_OF_MINUTE, 1) + "分钟前" : time + "小时前";
//    }

    public static String getPeriodString(int i, Date date, Date date2) {
        StringBuffer stringBuffer = new StringBuffer();
        switch (Helper.getType4TimeDelta(date, date2)) {
            case 1:
                if (isYesterdayOrToday(date) == 0) {
                    stringBuffer.append("昨天");
                } else if (isYesterdayOrToday(date) == -1) {
                    stringBuffer.append("");
                } else {
                    stringBuffer.append(getDateString(date, i));
                }
                stringBuffer.append(df_time.format(date));
                stringBuffer.append("到");
                stringBuffer.append(df_time.format(date2));
                break;
            case 2:
                stringBuffer.append(getDateString(date, i));
                stringBuffer.append("到");
                stringBuffer.append(df_date.format(date2));
                break;
            case 4:
                stringBuffer.append(df_date_year.format(date));
                stringBuffer.append(df_time.format(date));
                stringBuffer.append("到");
                stringBuffer.append(df_date_year.format(date2));
                stringBuffer.append(df_time.format(date2));
                break;
            case 5:
                stringBuffer.append(getDateString(date, i));
                stringBuffer.append(df_time.format(date));
                stringBuffer.append("到");
                stringBuffer.append(df_date.format(date2));
                stringBuffer.append(df_time.format(date2));
                break;
            case 6:
                stringBuffer.append(getDateString(date, i));
                stringBuffer.append(df_time.format(date));
                stringBuffer.append("到");
                stringBuffer.append(df_only_date.format(date2));
                stringBuffer.append(df_time.format(date2));
                break;
            case 7:
                stringBuffer.append(getWeekDay(date));
                stringBuffer.append(df_time.format(date));
                stringBuffer.append("到");
                stringBuffer.append(getWeekDay(date2));
                stringBuffer.append(df_time.format(date2));
                break;
        }
        return stringBuffer.toString();
    }

    public static String getPeriodString(Date date, Date date2) {
        return getPeriodString(new Date(System.currentTimeMillis()).getYear(), date, date2);
    }

    public static int getType4TimeDelta(Date date, Date date2) {
        return (isSameWeek(date, date2) && isSameWeek(date2, new Date(System.currentTimeMillis())) && date2.getDate() - date.getDate() >= 1) ? 7 : date.getYear() == date2.getYear() ? date.getMonth() != date2.getMonth() ? 5 : date.getDate() != date2.getDate() ? isSameZeroHour(date, date2) ? 2 : 6 : !isSameZeroHour(date, date2) ? 1 : 4 : 4;
    }

    public static String getWeekDay(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return "周" + getChinaWeekDayFromNum(instance.get(Calendar.DAY_OF_WEEK));
    }

    public static boolean isCurrentYear(long j) {
        return new Date(j).getYear() == new Date(System.currentTimeMillis()).getYear();
    }

    public static boolean isSameWeek(Date date, Date date2) {
        Calendar gregorianCalendar = new GregorianCalendar(date.getYear() + 1900, date.getMonth(), date.getDate());
        Calendar gregorianCalendar2 = new GregorianCalendar(date2.getYear() + 1900, date2.getMonth(), date2.getDate());
        long timeInMillis = new GregorianCalendar(2000, 0, 3).getTimeInMillis();
        return (gregorianCalendar.getTimeInMillis() - timeInMillis) / FileConst.WEEK_SECOND == (gregorianCalendar2.getTimeInMillis() - timeInMillis) / FileConst.WEEK_SECOND;
    }

    private static boolean isSameZeroHour(Date date, Date date2) {
        return date.getHours() == 0 && date2.getHours() == 0 && date.getMinutes() == 0 && date2.getMinutes() == 0;
    }

    public static int isYesterdayOrToday(Date date) {
        Date date2 = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date2);
        date2 = null;
        try {
            date2 = simpleDateFormat.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (date2.getTime() - date.getTime() <= 0 || date2.getTime() - date.getTime() > 86400000) ? (date.getTime() - date2.getTime() < 0 || date.getTime() - date2.getTime() >= 86400000) ? (date.getTime() - date2.getTime() < 86400000 || date.getTime() - date2.getTime() >= 172800000) ? 2 : 1 : -1 : 0;
    }
}
