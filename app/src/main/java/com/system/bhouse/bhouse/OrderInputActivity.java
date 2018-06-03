package com.system.bhouse.bhouse;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.socks.library.KLog;
import com.system.bhouse.adpter.BaseAdapterHelper;
import com.system.bhouse.adpter.QuickAdapter;
import com.system.bhouse.api.ApiServiceUtils;
import com.system.bhouse.base.App;
import com.system.bhouse.bean.Dingdan;
import com.system.bhouse.bean.Shengfen;
import com.system.bhouse.bean.SubmitRet;
import com.system.bhouse.bean.formlist;
import com.system.bhouse.ui.ItemPopupWindow;
import com.system.bhouse.utils.AppManager;
import com.system.bhouse.utils.ProgressUtils;
import com.system.bhouse.utils.inteface.GetPopitemProject;
import com.system.bhouse.utils.inteface.getKVforpopup;
import com.zhy.autolayout.AutoLayoutActivity;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016-3-15.
 * 销售订单输入
 */
public class OrderInputActivity<T> extends AutoLayoutActivity implements getKVforpopup, GetPopitemProject<T> {
    @Bind(R.id.topfanhuititle_textview_zhende)
    TextView topfanhuititleTextview;
    @Bind(R.id.fanhui_lin)
    LinearLayout fanhuiLin;
    @Bind(R.id.spinner)
    Spinner spinner;
    @Bind(R.id.iv_xuhao)
    ImageView ivXuhao;
    @Bind(R.id.iv_xiangmu)
    ImageView ivXiangmu;
    @Bind(R.id.dingdan_cailianfei_edt)
    EditText dingdan_cailianfei_edt;
    @Bind(R.id.dingdan_yunshufei_edt)
    EditText dingdan_yunshufei_edt;
    @Bind(R.id.dingdan_qitafeiyong_edt)
    EditText dingdan_qitafeiyong_edt;
    @Bind(R.id.dingdan_heijijine_edt)
    EditText dingdan_heijijine_edt;
    @Bind(R.id.btn_submit_common)
    Button btn_submit_common;
    @Bind(R.id.dingdan_xuhao_edt)
    EditText dingdan_xuhao_edt;
    @Bind(R.id.dingdan_bieshugenzhong_edt)
    EditText dingdan_bieshugenzhong_edt;
    @Bind(R.id.dingdan_chanpinbieshuxingxi_edt)
    EditText dingdan_chanpinbieshuxingxi_edt;
    @Bind(R.id.dingdan_dinggoushuliang_edt)
    EditText dingdan_dinggoushuliang_edt;
    @Bind(R.id.dingdan_yunju_edt)
    EditText dingdan_yunju_edt;
    @Bind(R.id.dingdan_lururen_edt)
    EditText dingdan_lururen_edt;

    //    private ImageView iv_quyu;
    //    private ImageView iv_shengchangongchang;
    private DatePicker datePicker;
    //    private EditText dingdan_xiadanshijian_edt;
    //    private EditText dingdan_yujikaigong_edt;
//    private EditText dingdan_yujiwangong_edt;
    private LinearLayout yanzheng_linear;
    private Toast mToast;
    /**
     * 一个item的父布局 linearyout
     */
    private View parent;

    //    private ImageView iv_kehu;
//    private ImageView iv_chanpin;
//    private ImageView iv_hetongzhuangtai;
//    private ImageView iv_gongchenzhuangtai;
    private ImageView iv_yuju;
    private Map<String, Integer> map;

    @Override
    public void setlistKV(String s, View position, Map<String, Integer> map) {
        formlist.get(this.getPosition1()).setInput(s);
        if (position.getId() == getparent().getId()) {
            ViewGroup parent1 = (ViewGroup) getparent().getParent();
            for (int i = 0; i < parent1.getChildCount(); i++) {
                if (parent1.getChildAt(i) instanceof EditText) {
                    ((EditText) parent1.getChildAt(i)).setText(s);
                }
            }
        }
        this.map.putAll(map);

//        quickAdapter = new QuickAdapter<formlist>(OrderInputActivity.this, R.layout.orderinputitem, formlist) {
//            @Override
//            protected void convert(BaseAdapterHelper helper, formlist item) {
//                helper.setText(R.id.et_orideritem, item.getName());
//                helper.setText(R.id.orderitem_tv, item.getInput());
//            }
//        };
//
//        orderinputLv.setAdapter(quickAdapter);
    }

    private List<formlist> formlist;

    public int getPosition1() {
        return position1;
    }

    public void setPosition1(int position1) {
        this.position1 = position1;
    }

    public int position1;

    private QuickAdapter quickAdapter;
    private List<String> list;
    private List<String> inputlist = new ArrayList<>();
    private ListView listView;
    private ArrayList<String> listspin;
    //    private MyspinnerAdapter myspinnerAdapter;
    Spinner spiview;
    private List<T> shengfens1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderinputactivity);
        ButterKnife.bind(this);

        list = new ArrayList<>();
        listspin = new ArrayList<>();
        initview();
        initdata();
        initEvent();
//        AppCompatSpinner
//        myspinnerAdapter = new MyspinnerAdapter(OrderInputActivity.this, listspin);
        quickAdapter = new QuickAdapter<formlist>(OrderInputActivity.this, R.layout.orderinputitem, formlist) {
            @Override
            protected void convert(BaseAdapterHelper helper, formlist item) {
                helper.setText(R.id.et_orideritem, item.getName());
                helper.setText(R.id.orderitem_tv, item.getInput());
//                 spiview = (Spinner) helper.getView(R.id.spinner_input);

//                helper.setOnClickListener(R.id.orderitem_tv, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        showWindow(v);
//
//                    }
//                });
//                helper.setText(R.id.tv_describe, item.getDesc());
//                helper.setText(R.id.tv_time, item.getTime());
//                helper.setText(R.id.tv_phone, item.getPhone());
            }
        };
//        quickAdapter.notifyDataSetChanged();

        topfanhuititleTextview.setText("订单录入");
    }

    private void setparent(View parent) {
        this.parent = parent;
    }

    private View getparent() {
        return parent;
    }

    /**
     * private ImageView iv_kehu;
     * private ImageView iv_chanpin;
     * private ImageView   iv_hetongzhuangtai;
     * private ImageView iv_gongchenzhuangtai;
     */
    private void initEvent() {

        ivXuhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemPopupWindow = null;
                setparent(v);
                Observable<T> kehuIp = (Observable<T>) ApiServiceUtils.getInstence().getWholeConame("");
                getNetworkObserabletwo(kehuIp);
            }
        });

        ivXiangmu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemPopupWindow = null;
                setparent(v);
                Observable<T> kehuIp = (Observable<T>) ApiServiceUtils.getInstence().getChanpin("");
                getNetworkObserabletwo(kehuIp);
            }
        });

        iv_yuju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemPopupWindow = null;
                setparent(v);
                Observable<T> kehuIp = (Observable<T>) ApiServiceUtils.getInstence().getYunju("");
                getNetworkObserabletwo(kehuIp);
            }
        });



        set_eSearch_TextChanged();


        btn_submit_common.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitdata();
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


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    /**
     * 访问网络
     */

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
//                Toast.makeText(OrderInputActivity.this, e.toString(), 0).show();
                com.system.bhouse.utils.TenUtils.T.showShort
                        (OrderInputActivity.this,e.toString());
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

    private void getNetworkObserable() {
        ProgressUtils.ShowProgress(this);
        ApiServiceUtils.getInstence().getShengfenIp("").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Shengfen[]>() {
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
//                Toast.makeText(OrderInputActivity.this, e.toString(), 0).show();
                com.system.bhouse.utils.TenUtils.T.showShort
                        (OrderInputActivity.this,e.toString());
                e.printStackTrace();
                e.toString();
            }

            @Override
            public void onNext(Shengfen[] shengfens) {
                if (!(shengfens == null) && !TextUtils.isEmpty(shengfens.toString()) && !(shengfens.length == 0)) {
                    Log.i("TGA", shengfens[0].toString() + "--------------------");
                    Message obtain = Message.obtain();
                    obtain.what = Integer.valueOf(1);
//                    handler.sendMessage(obtain);
                    shengfens1 = new ArrayList<>();
                    for (int i = 0; i < shengfens.length; i++) {
                        Log.i("78798798798798798", shengfens[i].toString());
                        shengfens1.add((T) shengfens[i]);
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

    /**
     * 创建对话框
     *
     * @param editText
     * @return
     */

    private DatePickerDialog initDialogPicker(final EditText editText) {
        Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(OrderInputActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker dp, int year, int mounth, int day) {
                        editText.setText(year + "年" + (mounth + 1) + "月" + day + "日");
                    }
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
        return datePickerDialog;
    }

    @Override
    public void onBackPressed() {
//        if (itemPopupWindow.popupWindow != null && itemPopupWindow.popupWindow.isShowing()) {
//            itemPopupWindow.popupWindow.dismiss();
//            itemPopupWindow.popupWindow = null;
//            WindowManager.LayoutParams params= ( this).getWindow().getAttributes();
//            params.alpha=1f;
//            ( this).getWindow().setAttributes(params);
//        }else {
        super.onBackPressed();
//        }
    }


    private void initview() {
        map = new HashMap<>();
//        iv_quyu = (ImageView) findViewById(R.id.iv_quyu);
//        iv_shengchangongchang = (ImageView) findViewById(R.id.iv_shengchangongchang);
//        dingdan_xiadanshijian_edt = (EditText) findViewById(R.id.dingdan_xiadanshijian_edt);
//        dingdan_xiadanshijian_edt.setInputType(InputType.TYPE_NULL);
//        dingdan_yujikaigong_edt = (EditText) findViewById(R.id.dingdan_yujikaigong_edt);
//        dingdan_yujikaigong_edt.setInputType(InputType.TYPE_NULL);
//        dingdan_yujiwangong_edt = (EditText) findViewById(R.id.dingdan_yujiwangong_edt);
//        dingdan_yujiwangong_edt.setInputType(InputType.TYPE_NULL);
        yanzheng_linear = (LinearLayout) findViewById(R.id.yanzheng_linear);
//        iv_kehu = (ImageView) findViewById(R.id.iv_kehu);
//        iv_chanpin = (ImageView) findViewById(R.id.iv_chanpin);
//        iv_hetongzhuangtai = (ImageView) findViewById(R.id.iv_hetongzhuangtai);
//        iv_gongchenzhuangtai = (ImageView) findViewById(R.id.iv_gongchenzhuangtai);
        iv_yuju = (ImageView) findViewById(R.id.iv_yujun);

        dingdan_lururen_edt.setText(App.USER_INFO);
    }



    private static ItemPopupWindow itemPopupWindow;

    @SuppressWarnings("deprecation")
    public void showWindow(View v, List<T> ts) {
        List<Shengfen> shengfenList1 = (List<Shengfen>) ts;
        if (itemPopupWindow == null) {
            itemPopupWindow = new ItemPopupWindow();

        }
        else {
            itemPopupWindow.disswindow();
        }
        itemPopupWindow.initquickadapter(OrderInputActivity.this, v, shengfenList1);
        itemPopupWindow.showWindow(OrderInputActivity.this, v);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 点击空白处，隐藏软键盘
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null
                    && getCurrentFocus().getWindowToken() != null) {
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    private void initdata() {
        //27个字段
        formlist = new ArrayList<>();
//        R.string.chengben
        Resources resources = getResources();
        String[] string = resources.getStringArray(R.array.textname);
        for (int i = 0; i < string.length; i++) {
            formlist formlist1 = new formlist();
            formlist1.setName(string[i]);
            formlist.add(formlist1);
        }
        for (int i = 0; i < 20; i++) {
            listspin.add(i + "-pppppp-");
        }

        /**
         * spinner 用法
         */
        String[] mItems = getResources().getStringArray(R.array.xiangmuleibie);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //绑定 Adapter到控件
        spinner.setAdapter(adapter);

        Dingdan dingdna = getIntent().getParcelableExtra("Dingdan");
        if(dingdna!=null) {
            CheckDingdan(dingdna);

        }


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

    @OnClick(R.id.fanhui_lin)
    public void onClick() {
        new AlertDialog.Builder(this).setTitle("警告").setMessage("订单没有提交,是否退出").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppManager.getAppManager().finishActivity(OrderInputActivity.this);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();

    }

    @Override
    public void getpopitemproject(T temp, View temp1) {

    }

    private void CheckDingdan(Dingdan dingdan) {
        if(!TextUtils.isEmpty(dingdan.getCcNumber())){
            dingdan_xuhao_edt.setText(dingdan.getCcNumber());
        }
        if (!TextUtils.isEmpty(dingdan.getCrNumber())) {
            dingdan_bieshugenzhong_edt.setText(dingdan.getCrNumber());
        }
        if (!TextUtils.isEmpty(dingdan.getPname())) {
            dingdan_chanpinbieshuxingxi_edt.setText(dingdan.getPname());
        }
        if (!TextUtils.isEmpty(dingdan.getPnum()+"")) {
            dingdan_dinggoushuliang_edt.setText(dingdan.getPnum()+"");
        }
        if (!TextUtils.isEmpty(dingdan.getDisnum())) {
            dingdan_yunju_edt.setText(dingdan.getDisnum());
        }
        if (!TextUtils.isEmpty(dingdan.getRowfy()+"")) {
            dingdan_cailianfei_edt.setText(dingdan.getRowfy()+"");
        }
        if (!TextUtils.isEmpty(dingdan.getTransportfy()+"")) {
            dingdan_yunshufei_edt.setText(dingdan.getTransportfy()+"");
        }
        if (!TextUtils.isEmpty(dingdan.getOtherfy()+"")) {
            dingdan_qitafeiyong_edt.setText(dingdan.getOtherfy()+"");
        }
        if (!TextUtils.isEmpty(dingdan.getHtAmount()+"")) {
            dingdan_heijijine_edt.setText(dingdan.getHtAmount() + "");
        }
        if(!TextUtils.isEmpty(dingdan.getProType())){
            SpinnerAdapter adapter = spinner.getAdapter();
            if(adapter!=null) {
                int count = adapter.getCount();
               for(int i=0;i<count;i++){
                  if( adapter.getItem(i).equals(dingdan.getProType())){
                      spinner.setSelection(i);
                  }
               }
            }
        }

    }

    /**
     * 提交数据
     *
     * @Bind(R.id.dingdan_xuhao_edt) EditText dingdan_xuhao_edt;
     * @Bind(R.id.dingdan_bieshugenzhong_edt) EditText dingdan_bieshugenzhong_edt;
     * @Bind(R.id.dingdan_chanpinbieshuxingxi_edt) EditText dingdan_chanpinbieshuxingxi_edt;
     * spinner
     * @Bind(R.id.dingdan_dinggoushuliang_edt) EditText dingdan_dinggoushuliang_edt;
     * @Bind(R.id.dingdan_yunju_edt) EditText dingdan_yunju_edt;
     * @Bind(R.id.dingdan_cailianfei_edt) EditText dingdan_cailianfei_edt;
     * @Bind(R.id.dingdan_yunshufei_edt) EditText dingdan_yunshufei_edt;
     * @Bind(R.id.dingdan_qitafeiyong_edt) EditText dingdan_qitafeiyong_edt;
     * @Bind(R.id.dingdan_heijijine_edt) EditText dingdan_heijijine_edt;
     * @Bind(R.id.dingdan_lururen_edt) EditText dingdan_lururen_edt;
     */
    private void submitdata() {

        String dingdan_xuhao = dingdan_xuhao_edt.getText().toString();
        String dingdan_bieshugenzhong = dingdan_bieshugenzhong_edt.getText().toString();
//        spinner.
        String dingdan_chanpinleixing = spinner.getSelectedItem().toString();
        String dingdan_chanpinbieshuxingxi = dingdan_chanpinbieshuxingxi_edt.getText().toString();
        String dingdan_dinggoushuliang = dingdan_dinggoushuliang_edt.getText().toString();
        String dingdan_yunju = dingdan_yunju_edt.getText().toString();
        String dingdan_cailianfei = dingdan_cailianfei_edt.getText().toString();
        String dingdan_yunshufei = dingdan_yunshufei_edt.getText().toString();
        String dingdan_qitafeiyong = dingdan_qitafeiyong_edt.getText().toString();
        String dingdan_heijijine = dingdan_heijijine_edt.getText().toString();
        String dingdan_lururen = dingdan_lururen_edt.getText().toString();

        if (null != getIntent().getParcelableExtra("Dingdan")) {
            Dingdan dingdan = getIntent().getParcelableExtra("Dingdan");
            map.clear();
            map.put(dingdan.getCcNumber(),dingdan.getCcid());
            map.put(dingdan.getDisnum(), dingdan.getDid());
            map.put(dingdan.getPname(), dingdan.getPid());
            if (checkLoginInfo(dingdan_xuhao, dingdan_bieshugenzhong, dingdan_chanpinbieshuxingxi, dingdan_chanpinleixing, dingdan_dinggoushuliang, dingdan_yunju, dingdan_cailianfei, dingdan_yunshufei, dingdan_qitafeiyong, dingdan_heijijine, dingdan_lururen)) {
                if (!TextUtils.isEmpty(map.get(dingdan_chanpinbieshuxingxi) + "") && !TextUtils.isEmpty(map.get(dingdan_xuhao) + "") && !TextUtils.isEmpty(map.get(dingdan_yunju) + "")) {

                int crid = dingdan.getCrid();
                Observable<T> kehuIp = (Observable<T>) ApiServiceUtils.getInstence().putDingdaById(map.get(dingdan_xuhao) + "", dingdan_bieshugenzhong, URLEncoder.encode(dingdan_chanpinleixing), map.get(dingdan_chanpinbieshuxingxi) + "", dingdan_dinggoushuliang, map.get(dingdan_yunju) + "", dingdan_cailianfei, dingdan_yunshufei, dingdan_qitafeiyong, dingdan_heijijine, URLEncoder.encode(dingdan_lururen),crid+"");
                getNetworkObserablethree(kehuIp);
            }
        }
        }else if (checkLoginInfo(dingdan_xuhao, dingdan_bieshugenzhong, dingdan_chanpinbieshuxingxi, dingdan_chanpinleixing, dingdan_dinggoushuliang, dingdan_yunju, dingdan_cailianfei, dingdan_yunshufei, dingdan_qitafeiyong, dingdan_heijijine, dingdan_lururen)) {
            if (!TextUtils.isEmpty(map.get(dingdan_chanpinbieshuxingxi) + "") && !TextUtils.isEmpty(map.get(dingdan_xuhao) + "") && !TextUtils.isEmpty(map.get(dingdan_yunju) + "")) {

                Observable<T> kehuIp = (Observable<T>) ApiServiceUtils.getInstence().putDingdan(map.get(dingdan_xuhao)+"", dingdan_bieshugenzhong,URLEncoder.encode(dingdan_chanpinleixing), map.get(dingdan_chanpinbieshuxingxi) + "", dingdan_dinggoushuliang, map.get(dingdan_yunju)+"", dingdan_cailianfei, dingdan_yunshufei,dingdan_qitafeiyong, dingdan_heijijine, URLEncoder.encode(dingdan_lururen));
                getNetworkObserablethree(kehuIp);
            }
        }

    }

    private void getNetworkObserablethree(Observable<T> shengfenIp) {
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
//                Toast.makeText(OrderInputActivity.this, e.toString(), 0).show();
                com.system.bhouse.utils.TenUtils.T.showShort
                        (OrderInputActivity.this,e.toString());
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

                    if (((SubmitRet)shengfens1.get(0)).getDnum() == 1) {
                        Toast.makeText(OrderInputActivity.this, "成功==" + shengfens1.get(0), 0).show();
                        KLog.e(shengfens1.get(0));

                        new AlertDialog.Builder(OrderInputActivity.this).setTitle("成功").setMessage("订单提交成功,是否退出").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                OrderInputActivity.this.setVisible(true);
                            }
                        }).create().show();
                    }else if(((SubmitRet)shengfens1.get(0)).getDnum() == 0){
                        Toast.makeText(OrderInputActivity.this, "失败==" + shengfens1.get(0), 0).show();
                    }
                    ProgressUtils.DisMissProgress();
//                    Toast.makeText(OrderInputActivity.this, "getIpInfoResponse==" + shengfens, 0).show();

                }
                else {
                    ProgressUtils.DisMissProgress();
                }
//                showWindow(getparent(), shengfens1);


            }

        });
    }

    private void showToast(int resId) {

        if (mToast == null) {
            mToast = Toast.makeText(getBaseContext(), resId, Toast.LENGTH_SHORT);
        }
        mToast.setText(resId);
        mToast.show();
    }



    private boolean checkLoginInfo(String dingdan_xuhao, String dingdan_bieshugenzhong, String dingdan_chanpinbieshuxingxi, String dingdan_chanpinleixing, String dingdan_dinggoushuliang, String dingdan_yunju, String dingdan_cailianfei, String dingdan_yunshufei, String dingdan_qitafeiyong, String dingdan_heijijine, String dingdan_lururen) {

        if (TextUtils.isEmpty(dingdan_xuhao)) {
            showToast(R.string.hetongbianhao_is_null);
            return false;
        }
        else if (TextUtils.isEmpty(dingdan_bieshugenzhong)) {
            showToast(R.string.bieshugenzhong_is_null);
            return false;
        }
        else if (TextUtils.isEmpty(dingdan_chanpinbieshuxingxi)) {
            showToast(R.string.chanpinbieshuxingxi_is_null);
            return false;
        }
        else if (TextUtils.isEmpty(dingdan_chanpinleixing)) {
            showToast(R.string.chanpinleixing_is_null);
            return false;
        }
        else if (TextUtils.isEmpty(dingdan_dinggoushuliang)) {
            showToast(R.string.dinggoushuliang_is_null);
            return false;
        }
        else if (TextUtils.isEmpty(dingdan_yunju)) {
            showToast(R.string.yunju_is_null);
            return false;
        }
        else if (TextUtils.isEmpty(dingdan_cailianfei)) {
            showToast(R.string.cailianfei_is_null);
            return false;
        }
        else if (TextUtils.isEmpty(dingdan_yunshufei)) {
            showToast(R.string.yunshufei_is_null);
            return false;
        }
        else if (TextUtils.isEmpty(dingdan_qitafeiyong)) {
            showToast(R.string.qitafeiyong_is_null);
            return false;
        }
        else if (TextUtils.isEmpty(dingdan_heijijine)) {
            showToast(R.string.heijijine_is_null);
            return false;
        }
        else if (TextUtils.isEmpty(dingdan_lururen)) {
            showToast(R.string.lururen_is_null);
            return false;
        }
        return true;
    }


    /**
     * 监听3个总数
     *
     * @Bind(R.id.dingdan_cailianfei_edt) EditText dingdan_cailianfei_edt;
     * @Bind(R.id.dingdan_yunshufei_edt) EditText dingdan_yunshufei_edt;
     * @Bind(R.id.dingdan_qitafeiyong_edt) EditText dingdan_qitafeiyong_edt;
     * @Bind(R.id.dingdan_heijijine_edt) EditText dingdan_heijijine_edt;
     */

    Handler myhandler = new Handler();

    Runnable eChanged = new Runnable() {

        @Override
        public void run() {
            Double aDouble = 0.0;
            Double bDouble = 0.0;
            Double cDouble = 0.0;
            String dingdan_cailianfei = dingdan_cailianfei_edt.getText().toString();
            String dingdan_yunshufei = dingdan_yunshufei_edt.getText().toString();
            String dingdan_qitafeiyong = dingdan_qitafeiyong_edt.getText().toString();

            try {
                if (!TextUtils.isEmpty(dingdan_cailianfei)) {
                    aDouble = Double.valueOf(dingdan_cailianfei);
                }
                if (!TextUtils.isEmpty(dingdan_yunshufei)) {
                    bDouble = Double.valueOf(dingdan_yunshufei);
                }
                if (!TextUtils.isEmpty(dingdan_qitafeiyong)) {
                    cDouble = Double.valueOf(dingdan_qitafeiyong);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(OrderInputActivity.this, "输入的数字金额有错误请更正", 0).show();
            }
            Double num = aDouble + bDouble + cDouble;
            dingdan_heijijine_edt.setText(num + "");
        }
    };

    private void set_eSearch_TextChanged() {
        dingdan_cailianfei_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                //这个应该是在改变的时候会做的动作吧，具体还没用到过。
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
                //这是文本框改变之前会执行的动作
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                /**这是文本框改变之后 会执行的动作
                 * 因为我们要做的就是，在文本框改变的同时，我们的listview的数据也进行相应的变动，并且如一的显示在界面上。
                 * 所以这里我们就需要加上数据的修改的动作了。
                 */
                myhandler.post(eChanged);
            }
        });

        dingdan_yunshufei_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                //这个应该是在改变的时候会做的动作吧，具体还没用到过。
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
                //这是文本框改变之前会执行的动作
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                /**这是文本框改变之后 会执行的动作
                 * 因为我们要做的就是，在文本框改变的同时，我们的listview的数据也进行相应的变动，并且如一的显示在界面上。
                 * 所以这里我们就需要加上数据的修改的动作了。
                 */
                myhandler.post(eChanged);
            }
        });

        dingdan_qitafeiyong_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                //这个应该是在改变的时候会做的动作吧，具体还没用到过。
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
                //这是文本框改变之前会执行的动作
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                /**这是文本框改变之后 会执行的动作
                 * 因为我们要做的就是，在文本框改变的同时，我们的listview的数据也进行相应的变动，并且如一的显示在界面上。
                 * 所以这里我们就需要加上数据的修改的动作了。
                 */
                myhandler.post(eChanged);
            }
        });

    }

//    public class MyspinnerAdapter extends BaseAdapter {
//        LayoutInflater inflater;
//        Context context;
//        ArrayList<String> list;
//
//        public MyspinnerAdapter(Context context, ArrayList<String> list) {
//            super();
//            this.context = context;
//            this.list = list;
//            inflater = LayoutInflater.from(context);
//        }
//
//        @Override
//        public int getCount() {
//            // TODO Auto-generated method stub
//            return list.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            // TODO Auto-generated method stub
//            return list.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            // TODO Auto-generated method stub
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder viewHolder = null;
//            if (convertView == null) {
//                convertView = inflater.inflate(R.layout.myspinner_dropdown_items, null);
//                viewHolder = new ViewHolder();
//                viewHolder.textView = (TextView) convertView.findViewById(R.id.text1);
////                viewHolder.spinner = (Spinner) convertView.findViewById(R.id.spinner_input);
////                viewHolder.spinner.updateViewLayout();
//                convertView.setTag(viewHolder);
//            }
//            else {
//                viewHolder = (ViewHolder) convertView.getTag();
//            }
//            viewHolder.textView.setText(list.get(position));
//            return convertView;
//        }
//
//        public class ViewHolder {
//            TextView textView;
//            Spinner spinner;
//        }
//
//        public void refresh(List<String> l) {
//            this.list.clear();
//            list.addAll(l);
//            notifyDataSetChanged();
//        }
//
//        public void add(String str) {
//            list.add(str);
//            notifyDataSetChanged();
//        }
//
//        public void add(ArrayList<String> str) {
//            list.addAll(str);
//            notifyDataSetChanged();
//
//        }
//    }

}
