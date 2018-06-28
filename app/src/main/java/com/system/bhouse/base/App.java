package com.system.bhouse.base;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.socks.library.KLog;
import com.system.bhouse.bhouse.BuildConfig;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.phone.common.ConfigConsts;
import com.system.bhouse.bhouse.phone.config.PropertiesConfig;
import com.system.bhouse.bhouse.phone.modle.CheckedScope;
import com.system.bhouse.bhouse.setup.notification.receiver.NotificationService;
import com.system.bhouse.bhouse.setup.utils.FileUtil;
import com.system.bhouse.bhouse.task.bean.UserObject;
import com.system.bhouse.db.DBHelper;
import com.tencent.android.tpush.XGPushConfig;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;


/**
 * Created by Administrator on 2016-4-5.
 */
public class App extends MultiDexApplication {
    private static Context mApp;

    private static SQLiteDatabase db;

    private static DBHelper dbHelper;

    public static String INTENT_METHOD_FIRST_SINGUP="INTENT_METHOD_FIRST_SINGUP";


    public static boolean hasProjectManagePermission = true;

    public static boolean hasProjectCostPermission = true;

    public static boolean isProjectWorkOpen = false;

    public static boolean isProjectWorkAuditOpen = false;

    public static boolean isProjectCostStatisticsOpen = false;

    public static boolean isProjectCheckAuditOpen = false;

    public static boolean isProjectFillCheckOpen = false;

    public static boolean isProjectFillCheckAuditOpen = false;

    public static boolean isNonsupportMap;

    private ArrayList<CheckedScope> checkedScope;

    private boolean isAttendApproval;

    private boolean isAttendAudit;

    private boolean isAttendCheck;

    private boolean isLeave;

    public static int sEmojiNormal;
    public static int sEmojiMonkey;

    public static float sScale;
    public static int sWidthDp;
    public static int sWidthPix;
    public static int sHeightPix;

    public static Gson gson;

    public Vibrator mVibrator;

    //自己的信息
    public static UserObject sUserObject;

    public static final SimpleDateFormat sFormatThisYearSlashSECOND = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
    public static final SimpleDateFormat sFormatThisMonth = new SimpleDateFormat("MM-dd");

    private static final SimpleDateFormat sFormatOtherYear = new SimpleDateFormat("yy/MM/dd HH:mm");
    private static final SimpleDateFormat sFormatMessageOtherYear = new SimpleDateFormat("yy/MM/dd");
    private static final SimpleDateFormat sFormatThisYear = new SimpleDateFormat("MM/dd HH:mm");
    private static final SimpleDateFormat sFormatThisYearSlash = new SimpleDateFormat("MM-dd HH:mm");
    private static final SimpleDateFormat sFormatMessageThisYear = new SimpleDateFormat("MM/dd");
    private static final SimpleDateFormat sFormatToday = new SimpleDateFormat("今天 HH:mm");
    private static final SimpleDateFormat sFormatMessageToday = new SimpleDateFormat("今天");


    public static Context getContextApp() {
        return mApp;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //初始化LeakCanary
//        LeakCanary.install(this);
        mApp = this;
        KLog.init(BuildConfig.DEBUG);
        AutoLayoutConifg.getInstance().useDeviceSize();
        dbHelper = DBHelper.getInstance(this);
        db = dbHelper.getWritableDatabase();
        XGPushConfig.enableDebug(this,true);
        XGPushConfig.getToken(this);

//        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
//        SDKInitializer.initialize(getApplicationContext());
//        T.isShow=false;

        sEmojiNormal = getResources().getDimensionPixelSize(R.dimen.emoji_normal);
        sEmojiMonkey = getResources().getDimensionPixelSize(R.dimen.emoji_monkey);

        sScale = getResources().getDisplayMetrics().density;
        sWidthPix = getResources().getDisplayMetrics().widthPixels;
        sHeightPix = getResources().getDisplayMetrics().heightPixels;
        sWidthDp = (int) (sWidthPix / sScale);

        sUserObject = AccountInfo.loadAccount(this);

        // 初始化文件目录
        SdcardConfig.getInstance().initSdcard();

        // 捕捉异常
        AppUncaughtExceptionHandler crashHandler = AppUncaughtExceptionHandler.getInstance();
        crashHandler.init(getApplicationContext());

        try {
            Global.sVoiceDir = FileUtil.getDestinationInExternalFilesDir(this, Environment.DIRECTORY_MUSIC, FileUtil.DOWNLOAD_FOLDER).getAbsolutePath();
            Log.w("VoiceDir", Global.sVoiceDir);
        } catch (Exception e) {
            Global.errorLog(e);
        }

    }

    //有多少没有阅读的消息
    public static int ColumCount;

    public static int getColumnsCount(){
        NotificationService instance = NotificationService.getInstance(App.getContextApp());
        ColumCount=instance.findColumCount("1");
        return ColumCount;
    }


    public static Gson getAppGson() {

        if (gson == null) {
            gson = new GsonBuilder()
                    //序列化null
                    .serializeNulls()
                    // 设置日期时间格式，另有2个重载方法
                    // 在序列化和反序化时均生效
                    .setDateFormat("yyyy-MM-dd")
                    // 禁此序列化内部类
                    .disableInnerClassSerialization()
                    //生成不可执行的Json（多了 )]}' 这4个字符）
                    .generateNonExecutableJson()
                    //禁止转义html标签
                    .disableHtmlEscaping()
                    //格式化输出
                    .setPrettyPrinting()
                    .create();
        }
        return gson;
    }

    public static App getInstance() {
        if (mApp == null) {
            mApp = new App();
        }
        return (App) mApp;
    }


    /**
     * 获取自身App安装包信息
     *
     * @return
     */
    public PackageInfo getLocalPackageInfo() {
        return getPackageInfo(getPackageName());
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo(String packageName) {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info;
    }

    public void setConfigDefaultCheckView(boolean z) {
        new PropertiesConfig().saveProperty(getApplicationContext(), ConfigConsts.IS_MAPCHECK, String.valueOf(z));
    }

    public ArrayList<CheckedScope> getCheckedScope() {
        return this.checkedScope;
    }

    public void setCheckedScope(ArrayList<CheckedScope> arrayList) {
        this.checkedScope = arrayList;
    }


    public int getStatusHeight() {
        try {
            Class cls = Class.forName("com.android.internal.R$dimen");
            return getResources().getDimensionPixelSize(Integer.parseInt(cls.getField("status_bar_height").get(cls.newInstance()).toString()));
        } catch (Exception e) {
            return 0;
        }
    }


    public boolean isAttendApproval() {
        return this.isAttendApproval;
    }

    public boolean isAttendAudit() {
        return this.isAttendAudit;
    }

    public boolean isAttendCheck() {
        return this.isAttendCheck;
    }

    public boolean isLeave() {
        return this.isLeave;
    }

    private boolean isOverTime;

    public boolean isOverTime() {
        return this.isOverTime;
    }

    private boolean isBusiness;

    public boolean isBusiness() {
        return this.isBusiness;
    }

//    private LocationClient position = null;
//
//    public LocationClient getLocationClientInstance() {
//        if (this.position == null) {
//            this.position = new LocationClient(this);
//        }
//        return this.position;
//    }

    public boolean isUserMockLocation() {
        boolean z = false;
        try {
            if (Build.VERSION.SDK_INT < 23) {
                z = Settings.Secure.getString(getContentResolver(), "mock_location").equals("1");
            }
        } catch (Exception e) {
        }
        return z;
    }

    public static void popSoftkeyboard(Context ctx, View view, boolean wantPop) {
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (wantPop) {
            view.requestFocus();
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
        else {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void SetPicassopic(String picurl, ImageView view) {
        Glide.with(mApp).load(picurl).asBitmap().placeholder(R.drawable.ic_fail).diskCacheStrategy(DiskCacheStrategy.RESULT).error(R.drawable.ic_fail).into(view);
    }

    public static String getTimeDetail(long timeInMillis) {
        return dayToNow(timeInMillis, true);
    }

    public static String dayToNow(long time) {
        return dayToNow(time, true);
    }

    public static String dayToNow(long time, boolean displayHour) {
        long nowMill = System.currentTimeMillis();

        long minute = (nowMill - time) / 60000;
        if (minute < 60) {
            if (minute <= 0) {
                return Math.max((nowMill - time) / 1000, 1) + "秒前"; // 由于手机时间的原因，有时候会为负，这时候显示1秒前
            }
            else {
                return minute + "分钟前";
            }
        }

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time);
        int year = calendar.get(GregorianCalendar.YEAR);
        int month = calendar.get(GregorianCalendar.MONTH);
        int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);

        calendar.setTimeInMillis(nowMill);
        Long timeObject = new Long(time);
        if (calendar.get(GregorianCalendar.YEAR) != year) { // 不是今年
            SimpleDateFormat sFormatOtherYear = displayHour ? App.sFormatOtherYear : App.sFormatMessageOtherYear;
            return sFormatOtherYear.format(timeObject);
        }
        else if (calendar.get(GregorianCalendar.MONTH) != month
                || calendar.get(GregorianCalendar.DAY_OF_MONTH) != day) { // 今年
            SimpleDateFormat sFormatThisYear = displayHour ? App.sFormatThisYear : App.sFormatMessageThisYear;
            return sFormatThisYear.format(timeObject);
        }
        else { // 今天
            SimpleDateFormat sFormatToday = displayHour ? App.sFormatToday : App.sFormatMessageToday;
            return sFormatToday.format(timeObject);
        }
    }


    public static SQLiteDatabase getDB() {
        return db;
    }

    public static int dpToPx(int dpValue) {
        final float scale = mApp.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static String USER_INFO="";
    public static Integer MID;
    public static String MobileKey="";
    public static int Filenum;
    public static double Filesize;
    public static String domian="";
    public static String Mancompany="";
    public static int Fatherid;
    public static String usertype="";
    //吊装编码
    public static String HNumber = "";
    //获取收货确认编号
    public static String receiptHnumber = "";

    //获取退货需求编号
    public static String returnRequire = "";

    public static String KeyTimestring = "";
    public static Integer GSMID;

    //deviceToken
    public static String deviceToken="";

    //其他接口必须的
    public static int Property;

    public static boolean IsSub=false;
    //名字
    public static String menname="";
    //职位
    public static String mpname="";

}
