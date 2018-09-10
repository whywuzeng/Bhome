package com.system.bhouse.bhouse;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.socks.library.KLog;
import com.system.bhouse.Custom.ShowDeviceMessageCustomDialog;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.base.database.AccountManager;
import com.system.bhouse.base.database.DatabaseManager;
import com.system.bhouse.base.database.UserProfile;
import com.system.bhouse.base.storage.BHPrefrences;
import com.system.bhouse.bean.UserInfo;
import com.system.bhouse.bhouse.phone.view.WaveView;
import com.system.bhouse.bhouse.setup.WWCommon.WWBaseActivity;
import com.system.bhouse.bhouse.setup.notification.MyNotificationActivity_;
import com.system.bhouse.utils.AppManager;
import com.system.bhouse.utils.DeviceMessageUtils;
import com.system.bhouse.utils.ProgressUtils;
import com.system.bhouse.utils.TenUtils.L;
import com.system.bhouse.utils.TenUtils.T;
import com.system.bhouse.utils.sharedpreferencesuser;
import com.tencent.android.tpush.XGCustomPushNotificationBuilder;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/3/9.
 * 登录的activity
 */
public class LoginActivity extends WWBaseActivity {
    @Bind(R.id.usernumber)
    EditText usernumber;
    @Bind(R.id.password_btn)
    EditText passwordBtn;
    @Bind(R.id.btn_login)
    Button btnLogin;


    @Bind(R.id.login_cb_remember)
    CheckBox loginCbRemember;
    @Bind(R.id.btn_domian)
    TextView btnDomian;
    private String password, usernumber1;
    private Toast mToast;

    private WaveView wave;
    private ImageView psw_visiable;
    private boolean isChecked=true;
    @Bind(R.id.login_cb_device)
    TextView login_cb_device;

    //手机系统硬件配置信息
    String deviceCpuID ;
    String deviceId ;
    String ip ;
    String DiskID;
    String memInfoIype;
    private UserInfo userInfo;
    private String[] deviceToken;

    private Message m;
    
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yun_login);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        Log.e(TAG, "onCreate: ");
        initview();
        //云加的方法
        initYUNSection();

//        getUpdateMsg();

    }

    private void initYUNSection() {
        wave=(WaveView)findViewById(R.id.wave);
        psw_visiable=(ImageView)findViewById(R.id.psw_visiable);
        passwordBtn=(EditText)findViewById(R.id.password_btn);

        ObjectAnimator waveShiftAnim = ObjectAnimator.ofFloat(
                wave, "waveShiftRatio1", 0f, 1f);
        waveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
        waveShiftAnim.setDuration(2300);
        waveShiftAnim.setInterpolator(new LinearInterpolator());
        waveShiftAnim.start();

    }

    //初始化布局 账号 密码
    private void initview() {
        btnLogin=(Button)this.findViewById(R.id.btn_login);
        usernumber.setText(sharedpreferencesuser.getUsername(LoginActivity.this));
        String userpassword = sharedpreferencesuser.getUserpassword(LoginActivity.this);
        if (!TextUtils.isEmpty(userpassword)) {
            passwordBtn.setText(sharedpreferencesuser.getUserpassword(LoginActivity.this));
            loginCbRemember.setChecked(true);
        }
        ((EditText) usernumber).setSelection(usernumber.getText().length());
        ((EditText) passwordBtn).setSelection(passwordBtn.getText().length());

         deviceCpuID = DeviceMessageUtils.getDeviceCpuID(this);
         deviceId = DeviceMessageUtils.getDeviceId(this);
         ip = DeviceMessageUtils.getIp(this);
         DiskID="userdata";
         memInfoIype = DeviceMessageUtils.getMemInfoIype(this,"MemTotal");
         deviceToken = new String[]{""};
        XGPushManager.registerPush(this, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                //token在设备卸载重装的时候有可能会变
                KLog.d("TPush", "注册成功，设备token为：" + data);
                deviceToken[0] = data.toString();
                App.deviceToken=deviceToken[0];

                m.obj = "token:" + data;
                m.sendToTarget();
            }
            @Override
            public void onFail(Object data, int errCode, String msg) {
                KLog.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });
        XGPushConfig.getToken(this);

        Handler handler = new HandlerExtension(LoginActivity.this);
        m = handler.obtainMessage();

//        initCustomNotification(getApplicationContext());
    }

    private void initCustomNotification(Context context) {
        XGCustomPushNotificationBuilder build = new XGCustomPushNotificationBuilder();

        int id = context.getResources().getIdentifier(
                "refreshing_sound", "raw", context.getPackageName());
        String uri = "android.resource://"
                + context.getPackageName() + "/" + id;
        build.setSound(Uri.parse(uri));
        // 设置自定义通知layout,通知背景等可以在layout里设置
        build.setLayoutId(R.layout.notification);
        // 设置自定义通知内容id
        build.setLayoutTextId(R.id.content);
        // 设置自定义通知标题id
        build.setLayoutTitleId(R.id.title);
        // 设置自定义通知图片id
        build.setLayoutIconId(R.id.icon);
        // 设置自定义通知图片资源
        build.setLayoutIconDrawableId(R.mipmap.mais);
        // 设置状态栏的通知小图标
        //build.setbigContentView()
        build.setIcon(R.mipmap.mais);
        // 设置时间id
        build.setLayoutTimeId(R.id.time);
        Intent intent = new Intent(this, MyNotificationActivity_.class);
        PendingIntent activity = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        build.setContentIntent(activity);
        // 若不设定以上自定义layout，又想简单指定通知栏图片资源
        //build.setNotificationLargeIcon(R.drawable.ic_action_search);
        // 客户端保存build_id
        XGPushManager.setPushNotificationBuilder(this, 1, build);
//        XGPushManager.setDefaultNotificationBuilder(this, build);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        XGPushConfig.getToken(this);
    }

    //点击事件
    @OnClick({R.id.btn_login,R.id.psw_visiable,R.id.login_cb_device})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                doLogin();

                break;

            case  R.id.psw_visiable:

                if(isChecked){
                    //选择状态 显示明文--设置为可见的密码
                    passwordBtn.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    psw_visiable.setImageResource(R.drawable.login_btn_eye_kejie);
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    passwordBtn.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    psw_visiable.setImageResource(R.drawable.login_btn_eye_bukejian);
                }
                isChecked=!isChecked;
                break;
            case R.id.login_cb_device:
                ShowDeviceMessageCustomDialog.Builder builder = new ShowDeviceMessageCustomDialog.Builder(this);
                builder.setMessage(getPhoneInfo(this));
                builder.setTitle("设备认证");
                builder.setPositiveButton("认证", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                    //点击触发认证事件
                                deviceCertification();
                            }
                });

                builder.setNegativeButton("取消",
                        new android.content.DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.create().show();

                break;
        }
    }
    private void  deviceCertification()
    {
        if (TextUtils.isEmpty(usernumber.getText()))
        {
            T.showShort(this,"用户名不能为空,无法认证");
            return;
        }
        if (TextUtils.isEmpty(deviceToken[0]))
        {
            T.showShort(this,"DeviceToken正在获取,请稍后认证");
            return;
        }
        ApiWebService.Getlogin_reg_Json(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                L.e(result);
                T.showLong(LoginActivity.this,result);
            }

            @Override
            public void ErrorBack(String error) {
                L.e(error);
            }
        }, usernumber.getText().toString(),deviceCpuID,deviceToken[0],ip,DiskID,memInfoIype);
    }

    private boolean isLogin;

    //点击事件处理
    private void doLogin() {
        isLogin=false;
        password = passwordBtn.getText().toString();
        usernumber1 = usernumber.getText().toString();
        if (!checkLoginInfo(usernumber1, password)) {
            return;
        }
        //保存数据 记住密码
        else if (loginCbRemember.isChecked()) {
            sharedpreferencesuser.putUserpassword(this, password);
        }
        else {
            sharedpreferencesuser.putUserpassword(this, "");
        }

        Observable loginMsg = ApiWebService.getLoginMsg(usernumber1, password);

        loginMsg.subscribe(new Subscriber<String>() {

            @Override
            public void onStart() {
                ProgressUtils.getInstance().ShowProgress(LoginActivity.this);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ProgressUtils.getInstance().DisMissProgress();
            }

            @Override
            public void onNext(String result) {
                Gson gson = new Gson();
                UserInfo[] getIpInfoResponse = gson.fromJson(result, UserInfo[].class);
                if (!(getIpInfoResponse == null)  && (getIpInfoResponse.length > 0)) {
                    isLogin = true;

                    for (int j = 0; j < getIpInfoResponse.length; j++) { //就只有一个
                        //静态保存
                        userInfo = getIpInfoResponse[j];
                        App.USER_INFO = userInfo.getUsername();
                        App.MID = userInfo.getMid();
                        App.GSMID = userInfo.getGsmid();
                        App.MobileKey = userInfo.MobileKey;
                        App.Filenum = userInfo.Filenum;
                        App.Filesize = userInfo.getFilesizeX();
                        App.Mancompany = userInfo.getManCompany();
                        App.Fatherid=userInfo.getFatherid();

                        App.usertype = userInfo.getUsertype();
                        App.Property = userInfo.getProperty();
                        App.IsSub = userInfo.isIsSub();
                        App.menname = userInfo.getMenname();
                        App.mpname = userInfo.mpname;
                        App.XinggeId=userInfo.XingeAppID;
                        BHPrefrences.setAppProfileId(userInfo.XingeAppID);
                    }

                    final UserProfile profile = new UserProfile(Long.valueOf(getIpInfoResponse[0].XingeAppID), userInfo.getUsername(), userInfo.getMid(),userInfo.getGsmid(), userInfo.MobileKey, userInfo.Filenum, userInfo.getFilesizeX(), userInfo.getManCompany(), userInfo.getProperty(),userInfo.getFatherid(), userInfo.isIsSub(), userInfo.getMenname(), userInfo.mpname, userInfo.getUsertype());
                    DatabaseManager.getInstance().getDao().insertOrReplace(profile);

                    Observable regokJson = ApiWebService.Getlogin_regok_Json(deviceCpuID, deviceToken[0], ip, DiskID, memInfoIype);
                    regokJson.subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                            ProgressUtils.getInstance().DisMissProgress();
                        }

                        @Override
                        public void onError(Throwable e) {
                            ProgressUtils.getInstance().DisMissProgress();
                        }

                        @Override
                        public void onNext(String result) {
                            try {
                                JSONArray jsonArray = new JSONArray(result.toString());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    //循环遍历，依次取出JSONObject对象
                                    //用getInt和getString方法取出对应键值  [{"loing_regokdr":"否"}]
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    if (jsonObject.has("stu_no")) {
                                        int stu_no = jsonObject.getInt("stu_no");
                                    }
                                    if (jsonObject.has("stu_sex")) {
                                        String stu_sex = jsonObject.getString("stu_sex");
                                    }
                                    String stu_name = "";
                                    if (jsonObject.has("loing_regokdr")) {
                                        stu_name = jsonObject.getString("loing_regokdr");
                                    }
                                    if (stu_name.equals("否")) {
                                        T.showShort(LoginActivity.this, "当前设备不兼容");
                                        //handle跳转至
                                        Message obtain = Message.obtain();
                                        obtain.what = Integer.valueOf(1);
                                        handler.sendMessage(obtain);
                                        AccountManager.setSignState(true);
                                    }
                                    else if (stu_name.equals("是")&&isLogin){
                                        //handle跳转至
                                        Message obtain = Message.obtain();
                                        obtain.what = Integer.valueOf(1);
                                        handler.sendMessage(obtain);

                                        //已经注册并登录成功了
                                        AccountManager.setSignState(true);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                else {
                    ProgressUtils.getInstance().DisMissProgress();
                    Toast.makeText(LoginActivity.this, R.string.passworderror, Toast.LENGTH_SHORT).show();
                }

            }
        });


//        loginMsg.concatWith(regokJson).subscribe(ApiWebService.getMutiCallback(this, new ApiWebService.ObjectSuccessCall() {
//            @Override
//            public void SuccessBack(Object result) {
//
//                try {
//                    JSONArray jsonArray = new JSONArray(result.toString());
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        //循环遍历，依次取出JSONObject对象
//                        //用getInt和getString方法取出对应键值  [{"loing_regokdr":"否"}]
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        if (jsonObject.has("stu_no")) {
//                            int stu_no = jsonObject.getInt("stu_no");
//                        }
//                        if (jsonObject.has("stu_sex")) {
//                            String stu_sex = jsonObject.getString("stu_sex");
//                        }
//                        String stu_name = "";
//                        if (jsonObject.has("loing_regokdr")) {
//                            stu_name = jsonObject.getString("loing_regokdr");
//                        }
//                        if (stu_name.equals("否")) {
//                            T.showShort(LoginActivity.this, "当前设备不兼容");
//                            //handle跳转至
////                                        Message obtain = Message.obtain();
////                                        obtain.what = Integer.valueOf(1);
////                                        handler.sendMessage(obtain);
//                        }
//                        else if (stu_name.equals("是")&&isLogin){
//                            //handle跳转至
//                            Message obtain = Message.obtain();
//                            obtain.what = Integer.valueOf(1);
//                            handler.sendMessage(obtain);
//
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    JSONArray jsonArray = new JSONArray(result.toString());
//                    for (int i = 0; i < jsonArray.length(); i++) {
//
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        if (jsonObject.has("username")) {
//
//                            Gson gson = new Gson();
//                            UserInfo[] getIpInfoResponse = gson.fromJson(result.toString(), UserInfo[].class);
//                            if (!(getIpInfoResponse == null) && !TextUtils.isEmpty(getIpInfoResponse.toString()) && !(getIpInfoResponse.length == 0)) {
//                                isLogin = true;
//
//                                for (int j = 0; j < getIpInfoResponse.length; j++) { //就只有一个
//                                    //静态保存
//                                    userInfo = getIpInfoResponse[j];
//                                    App.USER_INFO = userInfo.getUsername();
//                                    App.MID = userInfo.getMid();
//                                    App.GSMID = userInfo.getGsmid();
//                                    App.MobileKey = userInfo.MobileKey;
//                                    App.Filenum = userInfo.Filenum;
//                                    App.Filesize = userInfo.getFilesizeX();
//                                    App.Mancompany = userInfo.getManCompany();
//
//                                    App.usertype = userInfo.getUsertype();
//                                    App.Property = userInfo.getProperty();
//                                    App.IsSub = userInfo.isIsSub();
//                                    App.menname = userInfo.getMenname();
//                                    App.mpname = userInfo.mpname;
//                                }
//                            }
//                            else {
//
//                                Toast.makeText(LoginActivity.this, R.string.passworderror, Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                    if (jsonArray.length()<=0)
//                    {
//                        Toast.makeText(LoginActivity.this, R.string.passworderror, Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void ErrorBack(Object error) {
//
//            }
//        }));

//        ApiWebService.getLoginMsg(this, new ApiWebService.SuccessCall() {
//            @Override
//            public void SuccessBack(String result) {
//                Gson gson=new Gson();
//                UserInfo[] getIpInfoResponse = gson.fromJson(result, UserInfo[].class);
//                if (!(getIpInfoResponse == null) && !TextUtils.isEmpty(getIpInfoResponse.toString()) && !(getIpInfoResponse.length == 0)) {
//
//                    for (int i = 0; i < getIpInfoResponse.length; i++) { //就只有一个
//                        //静态保存
//                        userInfo = getIpInfoResponse[i];
//                        App.USER_INFO = userInfo.getUsername();
//                        App.MID = userInfo.getMid();
//                        App.GSMID= userInfo.getGsmid();
//                        App.MobileKey = userInfo.MobileKey;
//                        App.Filenum = userInfo.Filenum;
//                        App.Filesize = userInfo.getFilesizeX();
//                        App.Mancompany = userInfo.getManCompany();
//
//                        App.usertype=userInfo.getUsertype();
//                        App.Property=userInfo.getProperty();
//                        App.IsSub=userInfo.isIsSub();
//                        App.menname=userInfo.getMenname();
//                        App.mpname=userInfo.mpname;
//                    }
//
//                    ApiWebService.Getlogin_regok_Json(LoginActivity.this, new ApiWebService.SuccessCall() {
//                        @Override
//                        public void SuccessBack(String result) {
//                            L.e(result);
//                            try {
//                                JSONArray jsonArray = new JSONArray(result);
//                                for (int i = 0; i< jsonArray.length(); i++) {
//                                    //循环遍历，依次取出JSONObject对象
//                                    //用getInt和getString方法取出对应键值  [{"loing_regokdr":"否"}]
//                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                    if(jsonObject.has("stu_no")) {
//                                        int stu_no = jsonObject.getInt("stu_no");
//                                    }
//                                    if (jsonObject.has("stu_sex")) {
//                                        String stu_sex = jsonObject.getString("stu_sex");
//                                    }
//                                    String stu_name="";
//                                    if (jsonObject.has("loing_regokdr")) {
//                                         stu_name = jsonObject.getString("loing_regokdr");
//                                    }
//                                    if (stu_name.equals("否"))
//                                    {
//                                        T.showShort(LoginActivity.this,"当前设备不兼容");
//                                        //handle跳转至
////                                        Message obtain = Message.obtain();
////                                        obtain.what = Integer.valueOf(1);
////                                        handler.sendMessage(obtain);
//                                    }else{
//                                        //handle跳转至
//                                        Message obtain = Message.obtain();
//                                        obtain.what = Integer.valueOf(1);
//                                        handler.sendMessage(obtain);
//
//                                    }
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//
//                        @Override
//                        public void ErrorBack(String error) {
//                            L.e(error);
//                        }
//                    },deviceCpuID,deviceToken[0],ip,DiskID,memInfoIype);
//
//                }
//                else {
//                    Toast.makeText(LoginActivity.this, R.string.passworderror, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void ErrorBack(String error) {
//                    T.showShort(LoginActivity.this,error);
//            }
//        },usernumber1, password);

//        //访问网络  登陆接口数据接收
//        ProgressUtils.ShowProgress(this);
//        ApiServiceUtils.getInstence().getloginIp(usernumber1, password).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<UserInfo[]>() {
//            @Override
//            public void onCompleted() {
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                ProgressUtils.DisMissProgress();
//                /**
//                 * 测试
//                 */
////                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                startActivity(intent);
////                Toast.makeText(LoginActivity.this, e.toString(), 0).show();
//                T.showShort
//                        (LoginActivity.this, e.toString());
//                e.printStackTrace();
//                e.toString();
//            }
//
//            @Override
//            public void onNext(final UserInfo[] getIpInfoResponse) {
//                if (!(getIpInfoResponse == null) && !TextUtils.isEmpty(getIpInfoResponse.toString()) && !(getIpInfoResponse.length == 0)) {
//                    Log.i("TGA", getIpInfoResponse[0].toString() + "--------------------");
//                    UserInfo userInfo = null;
//                    for (int i = 0; i < getIpInfoResponse.length; i++) {
//                        //静态保存
//                        userInfo = new UserInfo();
//                        userInfo = getIpInfoResponse[i];
//                        App.USER_INFO = userInfo.getUsername();
//                        App.MID = userInfo.getMid();
//                        App.MobileKey = userInfo.MobileKey;
//                        App.Filenum = userInfo.Filenum;
//                        App.Filesize = userInfo.Filesize;
//                        App.Mancompany = userInfo.getManCompany();
//                    }
//                    //handle跳转
//                    Message obtain = Message.obtain();
//                    obtain.what = Integer.valueOf(1);
//                    handler.sendMessage(obtain);
//                }
//                else {
//                    ProgressUtils.DisMissProgress();
//                    Toast.makeText(LoginActivity.this, R.string.passworderror, Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });

    }


    //域名点击处理
    @OnClick(R.id.btn_domian)
    public void onClick() {
        ShowDeviceMessageCustomDialog.Builder builder = new ShowDeviceMessageCustomDialog.Builder(this);
        builder.setMessage("在这里输入要修改的域名");
        builder.setTitle("提示");
        builder.setEdittextcontent(sharedpreferencesuser.getUserdomain(this));
        View inflate = LayoutInflater.from(this).inflate(R.layout.domain_edittext, null, false);
        builder.setContentView(inflate);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                EditText viewEditText = (EditText)inflate.findViewById(R.id.edt_domian);
                if (viewEditText.getText() != null) {

                    String text = viewEditText.getText().toString();
                    if (TextUtils.isEmpty(text)) {
                        T.showfunShort(LoginActivity.this,"域名不能为空");
                    }
                    else {
                        sharedpreferencesuser.putUserdomain(App.getContextApp(), text);
                        T.showfunShort(LoginActivity.this, "你选择了: " + text);
                    }
                }
            }
        });

        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();
    }


    class MyThread implements Runnable {
        @Override
        public void run() {
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", "1");
            msg.setData(data);
            handler.sendMessage(msg);
        }

    }

    private void dialog1(int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage("是否确认退出?"); //设置内容
        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //关闭dialog
                Toast.makeText(LoginActivity.this, "确认" + which, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(LoginActivity.this, "取消" + which, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNeutralButton("忽略", new DialogInterface.OnClickListener() {//设置忽略按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(LoginActivity.this, "忽略" + which, Toast.LENGTH_SHORT).show();
            }
        });
        //参数都设置完成了，创建并显示出来
        AlertDialog alertDialog = builder.create();
        if (i == 1) {
            builder.create().show();
        }
        else {
            alertDialog.dismiss();
        }
    }

    public String getPhoneInfo(Context context) {

        deviceToken[0]= XGPushConfig.getToken(this);

        StringBuilder sb = new StringBuilder();

        String deviceCpuID = DeviceMessageUtils.getDeviceCpuID(context);
        String deviceId = DeviceMessageUtils.getDeviceId(context);
        String ip = DeviceMessageUtils.getIp(context);
        String DiskID="userdata";
        String memInfoIype = DeviceMessageUtils.getMemInfoIype(context,"MemTotal");
        sb.append("\ndeviceCpuID = " + deviceCpuID);
        sb.append("\ndeviceId = " + deviceId);
        sb.append("\nip = " + ip);
        sb.append("\nDiskID = " + DiskID);
        sb.append("\nmemInfoIype = " + memInfoIype);
        sb.append("\ndeviceToken ="+ deviceToken[0]);

        return sb.toString();
    }

    public final static String USERLOGNDATA="userlogndata";
    //这里处理跳转结果
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("mylog", "请求结果为-->" + val);
            // TODO
            // UI界面的更新等相关操作
            if (msg.what == 1) {
                //
                sharedpreferencesuser.putUsername(LoginActivity.this, usernumber1);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra(USERLOGNDATA,userInfo);
                startActivity(intent);
                AppManager.getAppManager().finishActivity(LoginActivity.this);
            }
            else {
                Toast.makeText(LoginActivity.this, "失败", Toast.LENGTH_SHORT).show();
            }
        }
    };


    private boolean checkLoginInfo(String userName, String password) {

        if (TextUtils.isEmpty(userName)) {
            showToast(R.string.user_is_null);
            return false;
        }
        else if (TextUtils.isEmpty(password)) {
            showToast(R.string.pwd_is_null);
            return false;
        }
        return true;
    }

    private void showToast(int resId) {

        if (mToast == null) {
            mToast = Toast.makeText(getBaseContext(), resId, Toast.LENGTH_SHORT);
        }
        mToast.setText(resId);
        mToast.show();
    }

    private static class HandlerExtension extends Handler {
        WeakReference<LoginActivity> mActivity;

        HandlerExtension(LoginActivity activity) {
            mActivity = new WeakReference<LoginActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoginActivity theActivity = mActivity.get();
            if (theActivity == null) {
                theActivity = new LoginActivity();
            }
            if (msg != null) {

            }
        }
    }
}
