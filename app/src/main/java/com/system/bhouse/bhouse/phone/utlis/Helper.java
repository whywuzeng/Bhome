package com.system.bhouse.bhouse.phone.utlis;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Vibrator;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.system.bhouse.base.App;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.phone.common.FileConst;
import com.system.bhouse.bhouse.phone.common.JsonConsts;
import com.system.bhouse.bhouse.phone.common.ListData;
import com.system.bhouse.bhouse.phone.common.RemindConst;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

import uk.co.senab.photoview.IPhotoView;

public class Helper {
    private static final String ANDROID = "android";
    public static final int DELTA_TIME_CURRENT_AND_SAME_WEEK = 7;
    public static final int DELTA_TIME_DATE_AND_TIME = 4;
    public static final int DELTA_TIME_FINISH_WITHOUT_MONTH = 6;
    public static final int DELTA_TIME_FINISH_WITHOUT_YEAR = 5;
    public static final int DELTA_TIME_WITHOUT_DATE = 1;
    public static final int DELTA_TIME_WTTHOUT_TIME = 2;
    public static final int THRESHOLD_TIME = 1440;
    private static final String UN_KNOWN = "unknown";
    public static final SimpleDateFormat df_date = new SimpleDateFormat("M月d日");
    public static final SimpleDateFormat df_date_year = new SimpleDateFormat("yyyy年M月d日");
    public static final SimpleDateFormat df_only_date = new SimpleDateFormat("d日");
    public static final SimpleDateFormat df_time = new SimpleDateFormat("H:mm");
    public static final LayoutParams fillparent = new LayoutParams(-1, -1);

    public static int FormatTime2Time(String str) {
        int i = 0;
        int indexOf = str.indexOf(":");
        if (indexOf > 0) {
            int intValue = Integer.valueOf(str.substring(0, indexOf)).intValue();
            if (indexOf + 1 < str.length()) {
                i = Integer.valueOf(str.substring(indexOf + 1)).intValue();
            }
            indexOf = ((intValue * 60) + 1440) + i;
            CAMLog.v("format time 2 time", "hour->" + intValue + ",min" + i + ",time->" + indexOf);
            return indexOf;
        } else if (indexOf >= 0) {
            return 0;
        } else {
            i = Integer.valueOf(str).intValue();
            CAMLog.v("format time 2 time", "time->" + i);
            return i;
        }
    }

    public static boolean check(JSONObject jSONObject) {
        boolean z = false;
        try {
            z = jSONObject.get("retcode").equals(Integer.valueOf(0));
        } catch (JSONException e) {
        }
        return z;
    }

    public static boolean checkNotify(JSONObject jSONObject) {
        return jSONObject != null && jSONObject.optInt("retcode", 0) == 0 && jSONObject.has(JsonConsts.NOTIFYTIME);
    }

    public static void cleanList(BaseAdapter baseAdapter, ListData<?> listData) {
        if (listData != null) {
            listData.clear();
        }
        if (baseAdapter != null) {
            baseAdapter.notifyDataSetChanged();
        }
    }

    public static void cleanList(BaseAdapter baseAdapter, ArrayList arrayList) {
        if (arrayList != null) {
            arrayList.clear();
        }
        if (baseAdapter != null) {
            baseAdapter.notifyDataSetChanged();
        }
    }
//
//    public static void converttToUrl(TextView textView, String str) {
//        CharSequence valueOf = SpannableString.valueOf(str);
//        Matcher matcher = Pattern.compile(UrlMatcher.URL).matcher(valueOf);
//        while (matcher.find()) {
//            String group = matcher.group(0);
//            if (!StringUtil.isEmpty(group)) {
//                valueOf.setSpan(new CustomUrlSpan(group), matcher.start(), matcher.end(), 33);
//            }
//        }
//        textView.setText(valueOf);
//        textView.setMovementMethod(new LinkMovementClickMethod());
//    }

//    public static void decideZoomBtnStatus(BaiduMap baiduMap, ImageButton imageButton, ImageButton imageButton2, boolean z) {
//        if (baiduMap.getMapStatus().zoom == 19.0f) {
//            imageButton.setBackgroundResource(R.drawable.mapcheck_zoom_in_d);
//        } else if (baiduMap.getMapStatus().zoom == IPhotoView.DEFAULT_MAX_SCALE) {
//            imageButton2.setBackgroundResource(R.drawable.mapcheck_zoom_out_d);
//        } else {
//            imageButton.setBackgroundResource(R.drawable.mapcheck_zoomin_x);
//            imageButton2.setBackgroundResource(R.drawable.mapcheck_zoomout_x);
//        }
//    }

    public static String floatFormat(float f) {
        return new BigDecimal((double) f).setScale(1, 4).toString();
    }

    public static String formatStaffCounts(int i) {
        return i > 9 ? " (" + i + ")人" : " ( " + i + " )人";
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

    public static int getErrCode(JSONObject jSONObject) {
        try {
            return jSONObject.getInt("retcode");
        } catch (JSONException e) {
            e.printStackTrace();
            return 1005;
        }
    }

//    public static String getErrCode(BDLocation bDLocation) {
//        return bDLocation == null ? "" : "\n错误码" + String.valueOf(bDLocation.getLocType());
//    }

    public static String getErrReason(JSONObject jSONObject) {
        try {
            return jSONObject.getString("explanation");
        } catch (JSONException e) {
            return "无法连接到服务器(错误代码1006)";
        }
    }

    public static String getErrReason(JSONObject jSONObject, int i) {
        try {
            return jSONObject.getString("explanation");
        } catch (JSONException e) {
            return "无法连接到服务器(错误代码" + String.valueOf(i) + ")";
        }
    }

    public static String getFormatTime(int i) {
        if (i < 1440) {
            return String.valueOf(i);
        }
        int i2 = i - 1440;
        int i3 = i2 / 60;
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR_OF_DAY, i3);
        instance.set(Calendar.MINUTE, i2 % 60);
        return DateFormatUtil.LEAVE_TIME_FORMATE.format(Long.valueOf(instance.getTimeInMillis()));
    }

    public static String getPeriodDayString(int i, Date date, Date date2) {
        StringBuffer stringBuffer = new StringBuffer();
        switch (getType4DateDelta(date, date2)) {
            case 1:
                stringBuffer.append(TimeUtil.getFriendlyDate(date.getTime()));
                break;
            case 2:
                stringBuffer.append(getDateString(date, i));
                stringBuffer.append("到");
                stringBuffer.append(df_date.format(date2));
                break;
            case 4:
                stringBuffer.append(df_date_year.format(date));
                stringBuffer.append("到");
                stringBuffer.append(df_date_year.format(date2));
                break;
            case 5:
                stringBuffer.append(getDateString(date, i));
                stringBuffer.append("到");
                stringBuffer.append(df_date.format(date2));
                break;
            case 6:
                stringBuffer.append(getDateString(date, i));
                stringBuffer.append("到");
                stringBuffer.append(df_only_date.format(date2));
                break;
            case 7:
                stringBuffer.append(getWeekDay(date));
                stringBuffer.append("到");
                stringBuffer.append(getWeekDay(date2));
                break;
        }
        return stringBuffer.toString();
    }

    public static String getPeriodDayString(Date date, Date date2) {
        return getPeriodDayString(new Date(System.currentTimeMillis()).getYear(), date, date2);
    }

    public static String getPeriodString(int i, Date date, Date date2) {
        StringBuffer stringBuffer = new StringBuffer();
        switch (getType4TimeDelta(date, date2)) {
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

    public static int getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    public static int getScreenDensityInInt(Context context) {
        int screenDensity = getScreenDensity(context);
        return screenDensity <= RemindConst.TWO_HOURS ? RemindConst.TWO_HOURS : screenDensity <= 160 ? 160 : screenDensity <= 240 ? 240 : screenDensity <= 320 ? 320 : screenDensity <= 480 ? 480 : 480;
    }

    public static int getType4DateDelta(Date date, Date date2) {
        return (isSameWeek(date, date2) && isSameWeek(date2, new Date(System.currentTimeMillis())) && date2.getDate() - date.getDate() >= 1) ? 7 : date.getYear() != date2.getYear() ? 4 : date.getMonth() != date2.getMonth() ? 5 : date.getDate() != date2.getDate() ? 6 : 1;
    }

    public static int getType4TimeDelta(Date date, Date date2) {
        return (isSameWeek(date, date2) && isSameWeek(date2, new Date(System.currentTimeMillis())) && date2.getDate() - date.getDate() >= 1) ? 7 : date.getYear() != date2.getYear() ? 4 : date.getMonth() != date2.getMonth() ? 5 : date.getDate() != date2.getDate() ? date2.getDate() - date.getDate() == 1 ? !isSameZeroHour(date, date2) ? 6 : 2 : !isSameZeroHour(date, date2) ? 6 : 2 : isSameZeroHour(date, date2) ? 1 : 1;
    }

    public static String getWeekDay(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return "周" + getChinaWeekDayFromNum(instance.get(Calendar.DAY_OF_WEEK));
    }

    public static int getWheelFontSize(Context context) {
        int screenDensity = getScreenDensity(context);
        return screenDensity <= RemindConst.TWO_HOURS ? 12 : screenDensity <= 160 ? 16 : screenDensity <= 240 ? 30 : screenDensity <= 320 ? 40 : screenDensity <= 480 ? 55 : 55;
    }

    public static void goDeveloperSettings(Context context) {
        try {
            context.startActivity(new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS"));
        } catch (Throwable th) {
            Toast.makeText(context, "请进入手机系统设置->开发者选项\n关闭允许模拟位置选项", Toast.LENGTH_SHORT).show();
        }
    }

    public static void hideInputMethod(Context context, EditText editText) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        } catch (Throwable th) {
        }
    }

    public static void hideNoData(View view) {
        view.setVisibility(View.GONE);
    }

    public static boolean isAbsouluteTime(int i) {
        return i >= 1440;
    }

    public static boolean isAccuracyFailed(float f) {
        return isZero(f) || Math.abs(((double) f) - Double.MIN_VALUE) < 0.001d;
    }

    public static boolean isLatOrLngFailed(double d) {
        return isZero(d) || Math.abs(d - Double.MIN_VALUE) < 0.001d;
    }

    public static boolean isLocFailed(double d, double d2) {
        return isZero(d) || isZero(d2) || (Math.abs(d - Double.MIN_VALUE) < 0.001d && Math.abs(d2 - Double.MIN_VALUE) < 0.001d);
    }

    public static boolean isNeedSysCamera() {
        String str;
        try {
            str = Build.MODEL;
        } catch (Throwable th) {
            str = "";
        }
        return (str.toLowerCase().contains("mi") && str.contains("4")) || str.toLowerCase().contains("htc desire s") || ((str.toLowerCase().contains("mx4") && str.toLowerCase().contains("pro")) || str.toLowerCase().contains("coolpad 7275") || ((str.toLowerCase().contains("coolpad") && str.toLowerCase().contains("7295")) || ((str.toLowerCase().contains("sm") && str.toLowerCase().contains("g9350")) || str.toLowerCase().contains("nx549j"))));
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

    public static boolean isVirtual(Context context) {
        String str="";
        Sensor defaultSensor;
        CharSequence charSequence="";
        Exception exception;
        Exception exception2;
        String str2 = UN_KNOWN;
        String str3 = UN_KNOWN;
        try {
//            Class cls = Class.forName(MeizuConstants.CLS_NAME_SYSTEM_PROPERTIES);
//            str = (String) cls.getMethod("get", new Class[]{String.class, String.class}).invoke(cls.newInstance(), new Object[]{"gsm.version.baseband", UN_KNOWN});

            try {
                str3 = Build.SERIAL;
                String str4 = str3;
                defaultSensor = ((SensorManager) context.getSystemService(Context.SENSOR_SERVICE)).getDefaultSensor(9);

                charSequence = str4;

            } catch (Exception e) {
                exception = e;
                Object obj = str3;
                exception2 = exception;
                CAMLog.e("mface", "isVirtual ：" + exception2.toString());
                defaultSensor = null;
                CAMLog.e("mface", "SerialNumber ：" + charSequence + " 是否无序列号:" + UN_KNOWN.equals(charSequence));

                CAMLog.e("mface", "baseband ：" + str + " 是否无基带信息:" + UN_KNOWN.equals(str));
                if (UN_KNOWN.equals(str)) {
                }
                if (UN_KNOWN.equals(str)) {
                }
                if (!UN_KNOWN.equals(str)) {
                }
            }
        } catch (Exception e2) {
            exception = e2;
            str = str2;
            charSequence = str3;
            exception2 = exception;
            CAMLog.e("mface", "isVirtual ：" + exception2.toString());
            defaultSensor = null;
            CAMLog.e("mface", "SerialNumber ：" + charSequence + " 是否无序列号:" + UN_KNOWN.equals(charSequence));
            CAMLog.e("mface", "baseband ：" + str + " 是否无基带信息:" + UN_KNOWN.equals(str));
            if (UN_KNOWN.equals(str)) {
            }
            if (UN_KNOWN.equals(str)) {
            }
            if (UN_KNOWN.equals(str)) {
            }
        }
        CAMLog.e("mface", "SerialNumber ：" + charSequence + " 是否无序列号:" + UN_KNOWN.equals(charSequence));

//        CAMLog.e("mface", "baseband ：" + str + " 是否无基带信息:" + UN_KNOWN.equals(str));

        return (UN_KNOWN.equals(str) || !UN_KNOWN.equals(charSequence)) ? (UN_KNOWN.equals(str) || !ANDROID.equals(charSequence)) ? UN_KNOWN.equals(str) && Pattern.compile("[0-9]*").matcher(charSequence).matches() && defaultSensor == null : true : true;
    }

    private static int isYesterdayOrToday(Date date) {
        Date date2 = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date2);
        date2 = null;
        try {
            date2 = simpleDateFormat.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (date2.getTime() - date.getTime() <= 0 || date2.getTime() - date.getTime() > 86400000) ? (date2.getMonth() == date.getMonth() && date2.getDate() == date.getDate()) ? -1 : 1 : 0;
    }

    public static boolean isZero(double d) {
        return Math.abs(d - 0.0d) < 0.001d;
    }

    public static boolean isZero(float f) {
        return ((double) Math.abs(f - 0.0f)) < 0.001d;
    }

    public static void playCheckSound(Context context) {
        SoundPool soundPool = new SoundPool(10, 1, 5);
        soundPool.play(soundPool.load(context, R.raw.checksound, 1), IPhotoView.DEFAULT_MIN_SCALE, IPhotoView.DEFAULT_MIN_SCALE, 0, 0, IPhotoView.DEFAULT_MIN_SCALE);
    }

    public static void playCheckVibrator(Context context) {
        ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(200);
    }

    public static void setHeightAndWidth(View view, int i, int i2) {
        view.getLayoutParams().width = i2;
        view.getLayoutParams().height = i;
    }

    public static void setProgressFor6(ProgressBar progressBar) {
        if (progressBar != null) {
            try {
                if (VERSION.SDK_INT > 22) {
                    progressBar.setIndeterminateDrawable(App.getInstance().getResources().getDrawable(R.drawable.cam_circle_loading_6));
                }
            } catch (Throwable th) {
            }
        }
    }

    public static void showInputMethod(Context context, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(editText, 2);
    }

    public static void showNewVersionDialog(Context context, App cAMApp) {
    }

    public static void showNoData(View view) {
        view.bringToFront();
        view.setVisibility(View.VISIBLE);
    }

    public static void showShutOffFakeLoaction(Context context, AlertDialog alertDialog, String str) {
    }

    public static void showShutOffFakeLoaction(Context context, String str) {
        Builder builder = new Builder(context);
        builder.setCancelable(false);
        builder.setTitle("请关闭允许模拟位置选项");
        builder.setPositiveButton(str, null);
//        builder.setNegativeButton("设置", new 1(context));
        try {
            builder.show();
        } catch (Throwable th) {
        }
    }

    public static void waitingOff(RelativeLayout relativeLayout) {
        relativeLayout.setVisibility(View.GONE);
    }

    public static void waitingOn(RelativeLayout relativeLayout) {
        relativeLayout.setVisibility(View.VISIBLE);
        relativeLayout.bringToFront();
    }
}
