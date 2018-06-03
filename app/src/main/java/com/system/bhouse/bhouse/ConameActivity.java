package com.system.bhouse.bhouse;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.socks.library.KLog;
import com.system.bhouse.api.ApiServiceUtils;
import com.system.bhouse.base.App;
import com.system.bhouse.bean.Kehu;
import com.system.bhouse.bean.Shengfen;
import com.system.bhouse.bean.SubmitRet;
import com.system.bhouse.bean.WholeConame;
import com.system.bhouse.ui.ItemPopupWindow;
import com.system.bhouse.utils.AppManager;
import com.system.bhouse.utils.MeasureUtil;
import com.system.bhouse.utils.ProgressUtils;
import com.system.bhouse.utils.inteface.GetPopitemProject;
import com.system.bhouse.utils.inteface.getKVforpopup;
import com.zhy.autolayout.AutoLayoutActivity;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016-3-28.
 */
public class ConameActivity<T> extends AutoLayoutActivity implements getKVforpopup, GetPopitemProject<T> {
    /**
     * edittext  注入
     */
    @Bind(R.id.coname_hetongbianhao_edt)
    EditText coname_hetongbianhao_edt;
//    @Bind(R.id.coname_hetongxingzhi_edt)
//    EditText coname_hetongxingzhi_edt;
    @Bind(R.id.coname_kehumingchen_edt)
    EditText coname_kehumingchen_edt;
    @Bind(R.id.coname_lianxiren_edt)
    EditText coname_lianxiren_edt;
    @Bind(R.id.coname_lianxidianhua_edt)
    EditText coname_lianxidianhua_edt;
    @Bind(R.id.coname_yunju_edt)
    EditText coname_yunju_edt;
    @Bind(R.id.coname_jutididian_edt)
    EditText coname_jutididian_edt;
    @Bind(R.id.coname_anzhuangdidian_edt)
    EditText coname_anzhuangdidian_edt;
    @Bind(R.id.coname_shengfeh_edt)
    EditText coname_shengfeh_edt;
    @Bind(R.id.coname_hetongqitayaoqiu_et)
    EditText coname_hetongqitayaoqiu_et;
    @Bind(R.id.coname_lururen_edt)
    EditText coname_lururen_edt;
    @Bind(R.id.coname_yujikaigongriqi_edt)
    EditText conameYujikaigongriqiEdt;
    @Bind(R.id.coname_yujijiesuriqi_edt)
    EditText conameYujijiesuriqiEdt;
    @Bind(R.id.coname_yujigongqi_edt)
    EditText conameYujigongqiEdt;

    private LinearLayout yanzheng_linear;
    private ImageView iv_hetongbianhao, iv_kehubianma, iv_yunju, iv_shengfeh;
    private List<T> shengfens1;
    private View parent;
    private LinearLayout fanhui_lin;
    private Toast mToast;
    private Button btn_submit_common;
    private Map<String, Integer> map;
    private TextView topfanhuititle_textview_zhende;
    private Spinner spinner_coname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conameactivity);
        ButterKnife.bind(this);
        initview();
        initdata();
        initevent();
//        submitdata();

        topfanhuititle_textview_zhende.setText(R.string.hetongluru);
    }

    private void submitdata() {
        String s = coname_hetongbianhao_edt.getText().toString();
        String s1 = spinner_coname.getSelectedItem().toString();
        String s3 = coname_kehumingchen_edt.getText().toString();
        String s4 = coname_lianxiren_edt.getText().toString();
        String s5 = coname_lianxidianhua_edt.getText().toString();
        String s6 = coname_yunju_edt.getText().toString();
        String s8 = coname_anzhuangdidian_edt.getText().toString();
        String s9 = coname_jutididian_edt.getText().toString();
        String s10 = coname_shengfeh_edt.getText().toString();
        String s11 = coname_hetongqitayaoqiu_et.getText().toString();
        String s14 = coname_lururen_edt.getText().toString();
        String Yujikaigongriqi = conameYujikaigongriqiEdt.getText().toString();
        String Yujijiesuriqi = conameYujijiesuriqiEdt.getText().toString();
        String Yujigongqi = this.conameYujigongqiEdt.getText().toString();

//        HttpUtility.UrlEncode
//        URLEncoder.encode

        if (null != getIntent().getParcelableExtra("wholeConame")) {
            WholeConame wholeConame = getIntent().getParcelableExtra("wholeConame");
            if (checkLoginInfo(s, s1, s3, s4, s5, s6, s8, s9, s10, s11, s14, Yujikaigongriqi, Yujijiesuriqi, Yujigongqi)) {
                if (!TextUtils.isEmpty(map.get(s3) + "") && !TextUtils.isEmpty(map.get(s6) + "") && !TextUtils.isEmpty(map.get(s10) + "")) {

                    //更新(插入一条合同)
                    Observable<T> kehuIp = (Observable<T>) ApiServiceUtils.getInstence().putWholeConameById(s, URLEncoder.encode(s1), map.get(s3) + "", URLEncoder.encode(s4), s5, map.get(s6) + "", URLEncoder.encode(s8), URLEncoder.encode(s9), map.get(s10) + "", Yujikaigongriqi, Yujijiesuriqi, Yujigongqi, URLEncoder.encode(s11), URLEncoder.encode(App.USER_INFO),wholeConame.getCcid());
                    getNetworkObserablethree(kehuIp);

                }

            }
        }
            else if (checkLoginInfo(s, s1, s3, s4, s5, s6, s8, s9, s10, s11, App.USER_INFO, Yujikaigongriqi, Yujijiesuriqi, Yujigongqi)) {
                if (!TextUtils.isEmpty(map.get(s3) + "") && !TextUtils.isEmpty(map.get(s6) + "") && !TextUtils.isEmpty(map.get(s10) + "")) {
                    //提交(插入一条合同)
                    Observable<T> kehuIp = (Observable<T>) ApiServiceUtils.getInstence().putWholeConame(s, URLEncoder.encode(s1), map.get(s3) + "", URLEncoder.encode(s4), s5, map.get(s6) + "", URLEncoder.encode(s8), URLEncoder.encode(s9), map.get(s10) + "", Yujikaigongriqi, Yujijiesuriqi, Yujigongqi, URLEncoder.encode(s11), URLEncoder.encode(App.USER_INFO));
                    getNetworkObserablethree(kehuIp);
                }

            }
        }


    private void showToast(int resId) {

        if (mToast == null) {
            mToast = Toast.makeText(getBaseContext(), resId, Toast.LENGTH_SHORT);
        }
        mToast.setText(resId);
        mToast.show();
    }

    private boolean checkLoginInfo(String s, String s1, String s3, String s4, String s5, String s6, String s8, String s9, String s10, String s11, String s14, String Yujikaigongriqi, String Yujijiesuriqi, String Yujigongqi) {

        if (TextUtils.isEmpty(s)) {
            showToast(R.string.hetongbianhao_is_null);
            return false;
        }
        else if (TextUtils.isEmpty(s1)) {
            showToast(R.string.hetongxingzhi_is_null);
            return false;
        }
        else if (TextUtils.isEmpty(s3)) {
            showToast(R.string.kehumingchen_is_null);
            return false;
        }
        else if (TextUtils.isEmpty(s4)) {
            showToast(R.string.lianxiren_is_null);
            return false;
        }
        else if (TextUtils.isEmpty(s5)) {
            showToast(R.string.lianxidianhua_is_null);
            return false;
        }
        else if (TextUtils.isEmpty(s6)) {
            showToast(R.string.yunju_is_null);
            return false;
        }
        else if (TextUtils.isEmpty(s8)) {
            showToast(R.string.anzhuangdidian_is_null);
            return false;
        }
        else if (TextUtils.isEmpty(s9)) {
            showToast(R.string.jutididian_is_null);
            return false;
        }
        else if (TextUtils.isEmpty(s10)) {
            showToast(R.string.shengfen_is_null);
            return false;
        }
        else if (TextUtils.isEmpty(s11)) {
            showToast(R.string.hetongqitayaoqiu_is_null);
            return false;
        }
        else if (TextUtils.isEmpty(s14)) {
            showToast(R.string.lururen_is_null);
            return false;
        }
        else if (TextUtils.isEmpty(Yujikaigongriqi)) {
            showToast(R.string.Yujikaigongriqi_is_null);
            return false;
        }
        else if (TextUtils.isEmpty(Yujijiesuriqi)) {
            showToast(R.string.Yujijiesuriqi_is_null);
            return false;
        }
        else if (TextUtils.isEmpty(Yujigongqi) && Integer.valueOf(Yujigongqi) < 0) {
            showToast(R.string.Yujigongqinum);
            return false;
        }
        else if (Double.valueOf(Yujigongqi) < 0) {
            showToast(R.string.Yujigongqinum);
            return false;
        }
        return true;
    }

    private void initevent() {
        //时间
//        coname_lurushijian_edt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initDialogPicker(coname_lurushijian_edt).show();
//            }
//        });
//jian pan

        conameYujikaigongriqiEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialogPicker(conameYujikaigongriqiEdt).show();
            }
        });

        conameYujijiesuriqiEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialogPicker(conameYujijiesuriqiEdt).show();
            }
        });

        yanzheng_linear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (getCurrentFocus() != null
                            && getCurrentFocus().getWindowToken() != null) {
                        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        manager.hideSoftInputFromWindow(getCurrentFocus()
                                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                return false;
            }
        });

        fanhui_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ConameActivity.this).setTitle("警告").setMessage("订单没有提交,是否退出").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppManager.getAppManager().finishActivity(ConameActivity.this);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });

        //imageview listener
//        iv_hetongbianhao.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                itemPopupWindow = null;
//                setparent(v);
//                Observable<T> kehuIp = (Observable<T>) ApiServiceUtils.getInstence().getKehuIp("");
//                getNetworkObserabletwo(kehuIp);
//            }
//        });

        iv_kehubianma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemPopupWindow = null;
                setparent(v);
                Observable<T> kehuIp = (Observable<T>) ApiServiceUtils.getInstence().getKehuIp("");
                getNetworkObserabletwo(kehuIp);

            }
        });

        iv_yunju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemPopupWindow = null;
                setparent(v);
                Observable<T> kehuIp = (Observable<T>) ApiServiceUtils.getInstence().getYunju("");
                getNetworkObserabletwo(kehuIp);

            }
        });


        iv_shengfeh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemPopupWindow = null;
                setparent(v);
                Observable<T> kehuIp = (Observable<T>) ApiServiceUtils.getInstence().getShengfenIp("");
                getNetworkObserabletwo(kehuIp);

            }
        });

        btn_submit_common.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitdata();
            }
        });


        spinner_coname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
//                spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

    }

    private void initdata() {
        coname_lururen_edt.setText(App.USER_INFO);

        /**
         * spinner 用法
         */
        String[] mItems = getResources().getStringArray(R.array.hetongxingzhi);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //绑定 Adapter到控件
        spinner_coname.setAdapter(adapter);

        ChecktheBunder();

    }

    /**
     * @Bind(R.id.coname_yujikaigongriqi_edt) EditText conameYujikaigongriqiEdt;
     * @Bind(R.id.coname_yujijiesuriqi_edt) EditText conameYujijiesuriqiEdt;
     */
    private void set_conameYujigongqiEdt() {
        if (!TextUtils.isEmpty(conameYujikaigongriqiEdt.getText().toString()) && !TextUtils.isEmpty(conameYujijiesuriqiEdt.getText().toString())) {
            Date date1 = MeasureUtil.getDate(conameYujikaigongriqiEdt.getText().toString());
            Date date2 = MeasureUtil.getDate(conameYujijiesuriqiEdt.getText().toString());
            int intervalDays = MeasureUtil.getIntervalDays(date1, date2);
            if (intervalDays > 0) {
                conameYujigongqiEdt.setText(intervalDays + "");
            }
            else {
                conameYujigongqiEdt.setText(intervalDays + "");
                showToast(R.string.Yujijiesuriqi_is_error);
            }
        }
    }

    private void ChecktheBunder() {
        Intent intent = this.getIntent();
        if (null != intent.getParcelableExtra("wholeConame")) {
            WholeConame wholeConame = intent.getParcelableExtra("wholeConame");
            int ccid = wholeConame.getCcid();

            Observable<T> kehuIp = (Observable<T>) ApiServiceUtils.getInstence().getWholeConameById(ccid);
            getNetworkObserableWholeConamebyId(kehuIp);

            KLog.e(wholeConame.toString());
            CheckWholeConame(wholeConame);
        }
    }

    private void CheckWholeConame(WholeConame wholeConame) {
        if (!TextUtils.isEmpty(wholeConame.getCcNumber())) {
            coname_hetongbianhao_edt.setText(wholeConame.getCcNumber());
        }
        if (!TextUtils.isEmpty(wholeConame.getCcType())) {
//            spinner_coname.setText(wholeConame.getCcType());
            SpinnerAdapter adapter = spinner_coname.getAdapter();
            int count = adapter.getCount();
            for(int i=0;i<count;i++){
                if(adapter.getItem(i).equals(wholeConame.getCcType())){
                    spinner_coname.setSelection(i);
                }
            }
        }
        if (!TextUtils.isEmpty(wholeConame.getCname())) {
            coname_kehumingchen_edt.setText(wholeConame.getCname());
        }
        if (!TextUtils.isEmpty(wholeConame.getCper())) {
            coname_lianxiren_edt.setText(wholeConame.getCper());
        }
        if (!TextUtils.isEmpty(wholeConame.getCphoe())) {
            coname_lianxidianhua_edt.setText(wholeConame.getCphoe());
        }
        if (!TextUtils.isEmpty(wholeConame.getDisnum())) {
            coname_yunju_edt.setText(wholeConame.getDisnum());
        }
        if (!TextUtils.isEmpty(wholeConame.getInstallationarea())) {
            coname_anzhuangdidian_edt.setText(wholeConame.getInstallationarea());
        }
        if (!TextUtils.isEmpty(wholeConame.getSpecificarea())) {
            coname_jutididian_edt.setText(wholeConame.getSpecificarea());
        }
        if (!TextUtils.isEmpty(wholeConame.getArea())) {
            coname_shengfeh_edt.setText(wholeConame.getArea());

        }
        if (!TextUtils.isEmpty(wholeConame.getAddPer())) {
            coname_lururen_edt.setText(wholeConame.getAddPer());
        }
        if (!TextUtils.isEmpty(wholeConame.getYjstartdate())) {
            conameYujikaigongriqiEdt.setText(wholeConame.getYjstartdate());
        }
        if (!TextUtils.isEmpty(wholeConame.getYjenddate())) {
            conameYujijiesuriqiEdt.setText(wholeConame.getYjenddate());
        }
        if (!TextUtils.isEmpty(wholeConame.getYjdatenum() + "")) {
            conameYujigongqiEdt.setText(wholeConame.getYjdatenum() + "");
        }
        if (!TextUtils.isEmpty(wholeConame.getBeizhu())) {
            coname_hetongqitayaoqiu_et.setText(wholeConame.getBeizhu());
        }

    }

    private void initview() {
        yanzheng_linear = (LinearLayout) findViewById(R.id.yanzheng_linear);

        iv_hetongbianhao = (ImageView) findViewById(R.id.iv_hetongbianhao);
        iv_yunju = (ImageView) findViewById(R.id.iv_yunju);
        iv_shengfeh = (ImageView) findViewById(R.id.iv_shengfeh);
        iv_kehubianma = (ImageView) findViewById(R.id.iv_kehubianma);

        fanhui_lin = (LinearLayout) findViewById(R.id.fanhui_lin);
        btn_submit_common = (Button) findViewById(R.id.btn_submit_common);

        topfanhuititle_textview_zhende=(TextView)findViewById(R.id.topfanhuititle_textview_zhende);
        coname_kehumingchen_edt.setClickable(false);
        coname_kehumingchen_edt.setFocusable(false);
        coname_yunju_edt.setClickable(false);
        coname_yunju_edt.setFocusable(false);
        coname_shengfeh_edt.setClickable(false);
        coname_shengfeh_edt.setFocusable(false);

        map = new HashMap<>();
        spinner_coname=(Spinner)findViewById(R.id.spinner_coname);
    }

    /**
     * 创建对话框
     *
     * @param editText
     * @return
     */

    private DatePickerDialog initDialogPicker(final EditText editText) {
        Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(ConameActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker dp, int year, int mounth, int day) {
                        editText.setText(year + "-" + (mounth + 1) + "-" + day);
                        if (editText == conameYujijiesuriqiEdt) {
                            set_conameYujigongqiEdt();
                        }
                        else if (editText == conameYujikaigongriqiEdt) {
                            set_conameYujigongqiEdt();
                        }
                    }
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
        return datePickerDialog;
    }


    private void getNetworkObserableWholeConamebyId(Observable<T> shengfenIp) {
//        Observable<T> shengfenIp = (Observable<T>)ApiServiceUtils.getInstence().getShengfenIp("");
        ProgressUtils.ShowProgress(this);
        shengfenIp.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<T>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ProgressUtils.DisMissProgress();
                /**
                 * 测试
                 */
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);

                com.system.bhouse.utils.TenUtils.T.showShort
                        (ConameActivity.this,e.toString());
                e.printStackTrace();
                e.toString();
            }

            @Override
            public void onNext(T shengfens2) {
                if (!(shengfens2 == null) && !TextUtils.isEmpty(shengfens2.toString()) && !(((T[]) shengfens2).length == 0)) {
                    KLog.e(((WholeConame[]) shengfens2)[0].toString());
                    ProgressUtils.DisMissProgress();
                    WholeConame wholeConame = ((WholeConame[]) shengfens2)[0];
                    map.clear();
                    map.put(wholeConame.getCname(), wholeConame.getCid());
                    map.put(wholeConame.getDisnum(), wholeConame.getDid());
                    map.put(wholeConame.getArea(), wholeConame.getAid());
                }
                else {
                    ProgressUtils.DisMissProgress();
                }

                //showWindow(getparent(), shengfens1);

            }

        });
    }


    private void getNetworkObserablethree(Observable<T> shengfenIp) {
//        Observable<T> shengfenIp = (Observable<T>)ApiServiceUtils.getInstence().getShengfenIp("");
//        progressDialog = ProgressDialog.show(ConameActivity.this, "loading....", "Please wait...", true, false);
        ProgressUtils.ShowProgress(this);
        shengfenIp.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<T>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ProgressUtils.DisMissProgress();
                /**
                 * 测试
                 */
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//                Toast.makeText(ConameActivity.this, e.toString(), 0).show();
                com.system.bhouse.utils.TenUtils.T.showShort(ConameActivity.this,e.toString());
                e.printStackTrace();
                e.toString();
            }

            @Override
            public void onNext(T shengfens2) {
                T[] shengfens = (T[]) shengfens2;
                if (!(shengfens == null) && !TextUtils.isEmpty(shengfens.toString()) && !(shengfens.length == 0)) {
                    Log.i("TGA", shengfens[0].toString() + "--------------------");
//                    handler.sendMessage(obtain);
                    List<T> shengfens1 = new ArrayList<>();
                    for (int i = 0; i < shengfens.length; i++) {
                        Log.i("78798798798798798", shengfens[i].toString());
                        shengfens1.add(shengfens[i]);
                    }
                    KLog.e(shengfens1.get(0));
                    if (((SubmitRet)shengfens1.get(0)).getDnum() == 1) {
                        new AlertDialog.Builder(ConameActivity.this).setTitle("成功").setMessage("订单提交成功,是否退出").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ConameActivity.this.setVisible(true);
                            }
                        }).create().show();
                    }else if(((SubmitRet)shengfens1.get(0)).getDnum() == 0){
                        Toast.makeText(ConameActivity.this, "失败==" + shengfens1.get(0), 0).show();
                    }

                }
                else {

                }
                ProgressUtils.DisMissProgress();
//                showWindow(getparent(), shengfens1);

            }

        });
    }

    private void getNetworkObserabletwo(Observable<T> shengfenIp) {
//        Observable<T> shengfenIp = (Observable<T>)ApiServiceUtils.getInstence().getShengfenIp("");
        ProgressUtils.ShowProgress(this);
        shengfenIp.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<T>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                ProgressUtils.DisMissProgress();
                /**
                 * 测试
                 */
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
                com.system.bhouse.utils.TenUtils.T.showShort
                        (ConameActivity.this,e.toString());
                e.printStackTrace();
                e.toString();
            }

            @Override
            public void onNext(T shengfens2) {
                T[] shengfens = (T[]) shengfens2;
                if (!(shengfens == null) && !TextUtils.isEmpty(shengfens.toString()) && !(shengfens.length == 0)) {
                    Log.i("TGA", shengfens[0].toString() + "--------------------");
//                    handler.sendMessage(obtain);
                    shengfens1 = new ArrayList<>();
                    for (int i = 0; i < shengfens.length; i++) {
                        Log.i("78798798798798798", shengfens[i].toString());
                        shengfens1.add(shengfens[i]);
                    }


                    ProgressUtils.DisMissProgress();
//                    Toast.makeText(OrderInputActivity.this, "getIpInfoResponse==" + shengfens, 0).show();
                }
                else {

                    ProgressUtils.DisMissProgress();
                }
                showWindow(getparent(), shengfens1);
            }

        });
    }

    private void setparent(View parent) {
        this.parent = parent;
    }

    private View getparent() {
        return parent;
    }

    private static ItemPopupWindow itemPopupWindow;

    @SuppressWarnings("deprecation")
    public void showWindow(View v, List<T> ts) {
        List<Shengfen> shengfenList1 = (List<Shengfen>) ts;
        if (itemPopupWindow == null) {
            itemPopupWindow = new ItemPopupWindow();
//            if(itemPopupWindow.popupWindow.isShowing()) {
//                itemPopupWindow.disswindow();
//            }
        }
        else {
            itemPopupWindow.disswindow();
        }
        itemPopupWindow.initquickadapter(ConameActivity.this, v, shengfenList1);
        itemPopupWindow.showWindow(ConameActivity.this, v);

    }

    @Override
    public void setlistKV(String s, View position, Map<String, Integer> map) {
        if (position.getId() == getparent().getId()) {
            ViewGroup parent1 = (ViewGroup) getparent().getParent();
            for (int i = 0; i < parent1.getChildCount(); i++) {
                if (parent1.getChildAt(i) instanceof EditText) {
                    ((EditText) parent1.getChildAt(i)).setText(s);
                }
            }
        }

        this.map.putAll(map);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            new AlertDialog.Builder(this).setTitle("警告").setMessage("订单没有提交,是否退出").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void getpopitemproject(T temp, View temp1) {

        if (temp1.getId() == getparent().getId()) {
            ViewGroup parent1 = (ViewGroup) getparent().getParent();
            for (int i = 0; i < parent1.getChildCount(); i++) {
                if (parent1.getChildAt(i) instanceof EditText) {
                    if (((EditText) parent1.getChildAt(i)).getId() == R.id.coname_kehumingchen_edt) {
                        coname_lianxiren_edt.setText(((Kehu) temp).getCper());
                        coname_lianxidianhua_edt.setText(((Kehu) temp).getCphoe());
                    }
                }
            }
        }
    }
}
