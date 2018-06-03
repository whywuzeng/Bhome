package com.system.bhouse.bhouse.phone.utlis;

import android.annotation.SuppressLint;

import com.system.bhouse.bhouse.phone.common.BusinessConst;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint({"SimpleDateFormat"})
public class TimeUtil {
    public static String getChatTime(long j) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        switch (Integer.parseInt(simpleDateFormat.format(new Date(System.currentTimeMillis()))) - Integer.parseInt(simpleDateFormat.format(new Date(j)))) {
            case 0:
                return "今天 " + getHourAndMin(j);
            case 1:
                return "昨天 " + getHourAndMin(j);
            case 2:
                return "前天 " + getHourAndMin(j);
            default:
                return getTime(j);
        }
    }

    public static String getFriendlyChatTime(long j, boolean z) {
        Date date = new Date(System.currentTimeMillis());
        Date date2 = new Date(j);
        int isYesterdayOrToday = DatePeriodUtil.isYesterdayOrToday(date2);
        if (isYesterdayOrToday == -1) {
            return getHourAndMin(j);
        }
        if (isYesterdayOrToday == 0) {
            return "昨天 " + (z ? getHourAndMin(j) : "");
        } else if (isYesterdayOrToday == 1) {
            return "明天 " + (z ? getHourAndMin(j) : "");
        } else if (DatePeriodUtil.isSameWeek(date2, date)) {
            return DatePeriodUtil.getWeekDay(date2) + (z ? BusinessConst.PAUSE_MARK + getHourAndMin(j) : "");
        } else if (!z) {
            return DateFormatUtil.LEAVE_DATE_FORMATE.format(Long.valueOf(j));
        } else {
            return (isCurrentYear(j) ? DateFormatUtil.SHORT_DATE.format(Long.valueOf(j)) : DateFormatUtil.LEAVE_DATE_FORMATE.format(Long.valueOf(j))) + BusinessConst.PAUSE_MARK + getHourAndMin(j);
        }
    }

    public static String getFriendlyDate(long j) {
        Date date = new Date(System.currentTimeMillis());
        Date date2 = new Date(j);
        int isYesterdayOrToday = DatePeriodUtil.isYesterdayOrToday(date2);
        return isYesterdayOrToday == -1 ? "今天" : isYesterdayOrToday == 0 ? "昨天 " : isYesterdayOrToday == 1 ? "明天 " : DatePeriodUtil.isSameWeek(date2, date) ? DatePeriodUtil.getWeekDay(date2) : isCurrentYear(j) ? DateFormatUtil.SHORT_DATE.format(Long.valueOf(j)) : DateFormatUtil.LEAVE_DATE_FORMATE.format(Long.valueOf(j));
    }

    public static String getHourAndMin(long j) {
        return new SimpleDateFormat("H:mm").format(new Date(j));
    }

    public static String getRecentFileFriendlyTime(long j) {
        return (isCurrentYear(j) ? DateFormatUtil.SHORT_DATE_DEVIDE.format(Long.valueOf(j)) : DateFormatUtil.LEAVE_DATE_FORMATE.format(Long.valueOf(j))) + BusinessConst.PAUSE_MARK + getHourAndMin(j);
    }

    public static String getShortFriendlyDate(long j) {
        Date date = new Date(System.currentTimeMillis());
        Date date2 = new Date(j);
        int isYesterdayOrToday = DatePeriodUtil.isYesterdayOrToday(date2);
        return isYesterdayOrToday == -1 ? "今天" : isYesterdayOrToday == 0 ? "昨天" : isYesterdayOrToday == 1 ? "明天" : DatePeriodUtil.isSameWeek(date2, date) ? DatePeriodUtil.getWeekDay(date2) : isCurrentYear(j) ? DateFormatUtil.SHORT_DATE.format(Long.valueOf(j)) : DateFormatUtil.SHORT_DATE_WITH_YEAR.format(Long.valueOf(j));
    }

    public static String getTime(long j) {
        return new SimpleDateFormat("yy-MM-dd HH:mm").format(new Date(j));
    }

    public static boolean isCloseEnough(long j, long j2) {
        long j3 = j - j2;
        if (j3 < 0) {
            j3 = -j3;
        }
        return j3 < 120000;
    }

    public static boolean isCurrentYear(long j) {
        return new Date(j).getYear() == new Date(System.currentTimeMillis()).getYear();
    }
}
