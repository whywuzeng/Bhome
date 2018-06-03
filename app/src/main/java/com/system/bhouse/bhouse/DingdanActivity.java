package com.system.bhouse.bhouse;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.socks.library.KLog;
import com.system.bhouse.adpter.BaseAdapterHelper;
import com.system.bhouse.adpter.QuickAdapter;
import com.system.bhouse.api.ApiServiceUtils;
import com.system.bhouse.base.BaseActivity;
import com.system.bhouse.bean.Dingdan;
import com.system.bhouse.bean.WholeConame;
import com.system.bhouse.bean.formlist;
import com.system.bhouse.utils.AppManager;
import com.system.bhouse.utils.MeasureUtil;
import com.system.bhouse.utils.ProgressUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016-3-21.
 */
public class DingdanActivity<T> extends BaseActivity {
    @Bind(R.id.topfanhuititle_textview_zhende)
    TextView textTitle;
    @Bind(R.id.fanhui_lin)
    LinearLayout fanhuiLin;
    @Bind(R.id.dingdanxianshi_tv)
    TextView dingdanxianshiTv;
    private QuickAdapter quickAdapter;
    private QuickAdapter quickAdapter_dingdan;
    private List<formlist> formlist;
    private List<Dingdan> dingdanslist;
    private ArrayList<WholeConame> wholeConameList;
    private RelativeLayout topfanhuititle_rela_xiugai;
    private WholeConame wholeConame;
    private ListView Dingdannum_refresh_listview;
    private int Ccid;
    private WholeConame coname;
    private ListView coname_list_content;
    private ListView dingdan_list_content;
    private List<Dingdan> dingdans = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.dingdanactivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initdata();
        initview();
        initEvent();
//        quickAdapter = new QuickAdapter<formlist>(DingdanActivity.this, R.layout.orderinputitem, formlist) {
//            @Override
//            protected void convert(BaseAdapterHelper helper, formlist item) {
//                helper.setText(R.id.et_orideritem, item.getName());
//                helper.setText(R.id.orderitem_tv, item.getInput());
//            }
//        };
        /**
         * 订单的适配器
         */
        quickAdapter_dingdan = new QuickAdapter<Dingdan>(DingdanActivity.this, R.layout.dingdan_item_custom, dingdanslist) {
            @Override
            protected void convert(BaseAdapterHelper helper, final Dingdan item) {
                helper.setText(R.id.dingdan_bieshugenzhong_tv, item.getCrNumber());
                helper.setText(R.id.dingdan_canpinleixing_tv, item.getProType());
                helper.setText(R.id.dingdan_bieshubianma_tv, item.getPnumber());
                helper.setText(R.id.dingdan_chanpinbieshumingchen_tv, item.getPname());
                helper.setText(R.id.dingdan_chanpinguige_tv, item.getPmodel());
                helper.setText(R.id.dingdan_dinggoushuliang_tv, item.getPnum() + "");
                helper.setText(R.id.dingdan_yunju_tv, item.getDisnum());
                helper.setText(R.id.dingdan_yunfei_tv, item.getDisfy() + "");
                helper.setText(R.id.dingdan_cailianfei_tv, item.getRowfy() + "");
                helper.setText(R.id.dingdan_yunshufei_tv, item.getTransportfy() + "");
                helper.setText(R.id.dingdan_qitafeiyong_tv, item.getOtherfy() + "");
                helper.setText(R.id.dingdan_heijijine_tv, item.getHtAmount() + "");
                helper.setText(R.id.dingdan_shengchangongchang_tv, item.getManCompany());
                helper.setText(R.id.dingdan_danjuzhuangtai_tv, item.getStatus());
                helper.setText(R.id.dingdan_lururen_tv, item.getAddPer());
                helper.setText(R.id.dingdan_lurushijian_tv, item.getAddTime());
                helper.setText(R.id.dingdan_shenghairen_tv, item.getShPer());
                helper.setText(R.id.dingdan_shenhaishijian_tv, item.getShTime());
                dingdans.add(item);
                helper.getView(R.id.dingdanxiugai_tv_custom).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DingdanActivity.this, OrderInputActivity.class);
                        intent.putExtra("Dingdan", item);
                        startActivity(intent);
                    }
                });
            }
        };

//        MyAdapterdingdanslist adapterdingdanslist=new MyAdapterdingdanslist(dingdanslist);
//        dingdan_list_content.setAdapter(adapterdingdanslist);

        dingdan_list_content.setItemsCanFocus(true);
        quickAdapter_dingdan.notifyDataSetChanged();
        textTitle.setText("订单显示");
        ininEvent();
    }

    class MyAdapterdingdanslist extends BaseAdapter {

        private List<Dingdan> dingdanslist;

        public MyAdapterdingdanslist(List<Dingdan> dingdanslist) {
            this.dingdanslist = dingdanslist;
        }

        @Override
        public int getCount() {
            return dingdanslist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(DingdanActivity.this, R.layout.dingdan_item_custom, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.dingdan_bieshugenzhong_tv.setText(dingdanslist.get(position).getCrNumber());
            holder.dingdan_canpinleixing_tv.setText(dingdanslist.get(position).getProType());
            holder.dingdan_bieshubianma_tv.setText(dingdanslist.get(position).getPnumber());
            holder.dingdan_chanpinbieshumingchen_tv.setText(dingdanslist.get(position).getPname());
            holder.dingdan_chanpinguige_tv.setText(dingdanslist.get(position).getPmodel());
            holder.dingdan_dinggoushuliang_tv.setText(dingdanslist.get(position).getPnum() + "");
            holder.dingdan_yunju_tv.setText(dingdanslist.get(position).getDisnum());
            holder.dingdan_yunfei_tv.setText(dingdanslist.get(position).getDisfy() + "");
            holder.dingdan_cailianfei_tv.setText(dingdanslist.get(position).getRowfy() + "");
            holder.dingdan_yunshufei_tv.setText(dingdanslist.get(position).getTransportfy() + "");
            holder.dingdan_qitafeiyong_tv.setText(dingdanslist.get(position).getOtherfy() + "");
            holder.dingdan_heijijine_tv.setText(dingdanslist.get(position).getHtAmount() + "");
            holder.dingdan_shengchangongchang_tv.setText(dingdanslist.get(position).getManCompany());
            holder.dingdan_danjuzhuangtai_tv.setText(dingdanslist.get(position).getStatus());
            holder.dingdan_lururen_tv.setText(dingdanslist.get(position).getAddPer());
            holder.dingdan_lurushijian_tv.setText(dingdanslist.get(position).getAddTime());
            holder.dingdan_shenghairen_tv.setText(dingdanslist.get(position).getShPer());
            holder.dingdan_shenhaishijian_tv.setText(dingdanslist.get(position).getShTime());

            holder.dingdanxiugai_tv_custom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    com.system.bhouse.utils.TenUtils.T.showShort(DingdanActivity.this, "可有....");
                }
            });
            return convertView;
        }


        class ViewHolder {
            @Bind(R.id.dingdanxianshi_tv_custom)
            TextView dingdanxianshi_tv_custom;
            @Bind(R.id.dingdanxiugai_tv_custom)
            TextView dingdanxiugai_tv_custom;
            @Bind(R.id.dingdan_bieshugenzhong_tv)
            TextView dingdan_bieshugenzhong_tv;
            @Bind(R.id.dingdan_canpinleixing_tv)
            TextView dingdan_canpinleixing_tv;
            @Bind(R.id.dingdan_bieshubianma_tv)
            TextView dingdan_bieshubianma_tv;
            @Bind(R.id.dingdan_chanpinbieshumingchen_tv)
            TextView dingdan_chanpinbieshumingchen_tv;
            @Bind(R.id.dingdan_chanpinguige_tv)
            TextView dingdan_chanpinguige_tv;
            @Bind(R.id.dingdan_dinggoushuliang_tv)
            TextView dingdan_dinggoushuliang_tv;
            @Bind(R.id.dingdan_yunju_tv)
            TextView dingdan_yunju_tv;
            @Bind(R.id.dingdan_yunfei_tv)
            TextView dingdan_yunfei_tv;
            @Bind(R.id.dingdan_cailianfei_tv)
            TextView dingdan_cailianfei_tv;
            @Bind(R.id.dingdan_yunshufei_tv)
            TextView dingdan_yunshufei_tv;
            @Bind(R.id.dingdan_qitafeiyong_tv)
            TextView dingdan_qitafeiyong_tv;
            @Bind(R.id.dingdan_heijijine_tv)
            TextView dingdan_heijijine_tv;
            @Bind(R.id.dingdan_shengchangongchang_tv)
            TextView dingdan_shengchangongchang_tv;
            @Bind(R.id.dingdan_danjuzhuangtai_tv)
            TextView dingdan_danjuzhuangtai_tv;
            @Bind(R.id.dingdan_lururen_tv)
            TextView dingdan_lururen_tv;
            @Bind(R.id.dingdan_lurushijian_tv)
            TextView dingdan_lurushijian_tv;
            @Bind(R.id.dingdan_shenghairen_tv)
            TextView dingdan_shenghairen_tv;
            @Bind(R.id.dingdan_shenhaishijian_tv)
            TextView dingdan_shenhaishijian_tv;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


    public void onClick1(View v) {

    }


    private void initEvent() {
//        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
//                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
//                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
//                // Update the LastUpdatedLabel
//
//                if (Ccid != 0) {
//                    Observable<T> kehuIp = (Observable<T>) ApiServiceUtils.getInstence().getWholeConameById(Ccid);
//                    getNetworkObserableWholeConamebyId(kehuIp);
////                    Observable<T> kehuIp1 = (Observable<T>) ApiServiceUtils.getInstence().getDingdanById(Ccid);
////                    getNetworkObserableDingdanbyId(kehuIp1);
//                }
//
//            }
//        });

        Observable<T> kehuIp = (Observable<T>) ApiServiceUtils.getInstence().getWholeConameById(Ccid);
        getNetworkObserableWholeConamebyId(kehuIp);

        /**
         * 下面订单的listview
         */

        /**
         * Add Sound Event Listener
         */

    }

    private void ininEvent() {
        topfanhuititle_rela_xiugai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (null != wholeConame) {
                    Intent intent = new Intent(DingdanActivity.this, ConameActivity.class);
                    intent.putExtra("wholeConame", wholeConame);
                    startActivity(intent);
                }
            }
        });
    }

    private void initview() {
        topfanhuititle_rela_xiugai = (RelativeLayout) findViewById(R.id.topfanhuititle_rela_xiugai);
        coname_list_content = (ListView) findViewById(R.id.coname_list_content);
        dingdan_list_content = (ListView) findViewById(R.id.dingdan_list_content);
        Point screenSize = MeasureUtil.getScreenSize(this);
        int y = screenSize.y;
    }

    private void getNetworkObserableDingdanbyId(Observable<T> shengfenIp) {
//        progressDialog = ProgressDialog.show(DingdanActivity.this, "loading....", "Please wait...", true, false);
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
//                Toast.makeText(DingdanActivity.this, e.toString(), 0).show();

                com.system.bhouse.utils.TenUtils.T.showShort
                        (DingdanActivity.this,e.toString());
                e.printStackTrace();
                e.toString();
            }

            @Override
            public void onNext(T shengfens2) {
                if (!(shengfens2 == null) && !TextUtils.isEmpty(shengfens2.toString()) && !(((T[]) shengfens2).length == 0)) {
                    KLog.e(((Dingdan[]) shengfens2)[0].toString());
                    SetDataToDingdanview(shengfens2);
                }
                else {

                }

                //showWindow(getparent(), shengfens1);
                ProgressUtils.DisMissProgress();
            }

        });
    }

    private void getNetworkObserableWholeConamebyId(Observable<T> shengfenIp) {
//        Observable<T> shengfenIp = (Observable<T>)ApiServiceUtils.getInstence().getShengfenIp("");
        ProgressUtils.ShowProgress(this);
        shengfenIp.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<T>() {
            @Override
            public void onCompleted() {

                Observable<T> kehuIp1 = (Observable<T>) ApiServiceUtils.getInstence().getDingdanById(Ccid);
                getNetworkObserableDingdanbyId(kehuIp1);
            }

            @Override
            public void onError(Throwable e) {

                ProgressUtils.DisMissProgress();
                /**
                 * 测试
                 */
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
                com.system.bhouse.utils.TenUtils.T.showShort(DingdanActivity.this, e.toString());
                e.printStackTrace();
                e.toString();
            }

            @Override
            public void onNext(T shengfens2) {
                if (!(shengfens2 == null) && !TextUtils.isEmpty(shengfens2.toString()) && !(((T[]) shengfens2).length == 0)) {
                    KLog.e(((WholeConame[]) shengfens2)[0].toString());

                    ProgressUtils.DisMissProgress();
                    SetDataToListview(shengfens2);
                }
                else {

                    ProgressUtils.DisMissProgress();
                }


                //showWindow(getparent(), shengfens1);

            }

        });
    }

    private void SetDataToDingdanview(T shengfens2) {
        Dingdan[] dingdans = (Dingdan[]) shengfens2;
        dingdanslist = new ArrayList<>();
        for (Dingdan dingdan : dingdans) {
            dingdanslist.add(dingdan);
        }

        quickAdapter_dingdan = new QuickAdapter<Dingdan>(DingdanActivity.this, R.layout.dingdan_item_custom, dingdanslist) {
            @Override
            protected void convert(BaseAdapterHelper helper, final Dingdan item) {
                helper.setText(R.id.dingdan_bieshugenzhong_tv, item.getCrNumber());
                helper.setText(R.id.dingdan_canpinleixing_tv, item.getProType());
                helper.setText(R.id.dingdan_bieshubianma_tv, item.getPnumber());
                helper.setText(R.id.dingdan_chanpinbieshumingchen_tv, item.getPname());
                helper.setText(R.id.dingdan_chanpinguige_tv, item.getPmodel());
                helper.setText(R.id.dingdan_dinggoushuliang_tv, item.getPnum() + "");
                helper.setText(R.id.dingdan_yunju_tv, item.getDisnum());
                helper.setText(R.id.dingdan_yunfei_tv, item.getDisfy() + "");
                helper.setText(R.id.dingdan_cailianfei_tv, item.getRowfy() + "");
                helper.setText(R.id.dingdan_yunshufei_tv, item.getTransportfy() + "");
                helper.setText(R.id.dingdan_qitafeiyong_tv, item.getOtherfy() + "");
                helper.setText(R.id.dingdan_heijijine_tv, item.getHtAmount() + "");
                helper.setText(R.id.dingdan_shengchangongchang_tv, item.getManCompany());
                helper.setText(R.id.dingdan_danjuzhuangtai_tv, item.getStatus());
                helper.setText(R.id.dingdan_lururen_tv, item.getAddPer());
                helper.setText(R.id.dingdan_lurushijian_tv, item.getAddTime());
                helper.setText(R.id.dingdan_shenghairen_tv, item.getShPer());
                helper.setText(R.id.dingdan_shenhaishijian_tv, item.getShTime());

                helper.getView(R.id.dingdanxiugai_tv_custom).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DingdanActivity.this, OrderInputActivity.class);
                        intent.putExtra("Dingdan", item);
                        startActivity(intent);
                    }
                });
            }
        };

        dingdan_list_content.setAdapter(quickAdapter_dingdan);
        quickAdapter_dingdan.notifyDataSetChanged();

    }

    private void SetDataToListview(T shengfens2) {
        this.coname = new WholeConame();
        WholeConame[] wholeConames = (WholeConame[]) shengfens2;
        this.coname = wholeConames[0];
        if (this.coname != null) {
            wholeConame = this.coname;
        }

        Resources resources = getResources();
        String[] string = resources.getStringArray(R.array.Coname_context);

        String[] string1 = null;
        string1 = new String[]{wholeConame.getCcid() + "", wholeConame.getCcNumber(), wholeConame.getCcType(), wholeConame.getCname(), wholeConame.getCper(), wholeConame.getCphoe(), wholeConame.getDisnum(), wholeConame.getRowfy() + "", wholeConame.getTransportfy() + "", wholeConame.getOtherfy() + "", wholeConame.getHjfy() + "", wholeConame.getInstallationarea(), wholeConame.getSpecificarea(), wholeConame.getArea(), wholeConame.getYjstartdate(), wholeConame.getYjenddate(), wholeConame.getYjdatenum() + "", wholeConame.getBeizhu(), wholeConame.getStatus(), wholeConame.getManCompany(), wholeConame.getAddPer(), wholeConame.getAddTime(), wholeConame.getShPer(), wholeConame.getShTime()};
        formlist.clear();
        for (int i = 0; i < string.length; i++) {
            formlist formlist1 = new formlist();
            formlist1.setName(string[i]);
            formlist1.setInput(string1[i]);
            formlist.add(formlist1);
        }

        adapter_coname_list_content adapter_coname_list_content=new adapter_coname_list_content(formlist);
        coname_list_content.setAdapter(adapter_coname_list_content);
    }

    class adapter_coname_list_content extends BaseAdapter {
        private List<formlist> formlist;

        public adapter_coname_list_content(List<formlist> formlist) {
            this.formlist = formlist;
        }

        @Override
        public int getCount() {
            return formlist.size();
        }

        @Override
        public Object getItem(int position) {
            return formlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder=null;
            if(convertView==null){
                convertView = View.inflate(DingdanActivity.this, R.layout.orderinputitem, null);
                holder=new ViewHolder(convertView);
                convertView.setTag(holder);
            }else{
                holder=(ViewHolder)convertView.getTag();
            }
            holder.et_orideritem.setText(formlist.get(position).getName());
            holder.orderitem_tv.setText(formlist.get(position).getInput());
            return convertView;
        }


         class ViewHolder {
            @Bind(R.id.et_orideritem)
            TextView et_orideritem;
            @Bind(R.id.orderitem_tv)
            TextView orderitem_tv;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    private void initdata() {
        formlist = new ArrayList<>();

        Intent intent = getIntent();
        wholeConameList = intent.getParcelableArrayListExtra("wholeConameList");
        int position = intent.getIntExtra("position", 0);
        wholeConame = wholeConameList.get(position - 1);//越界了
        Ccid = wholeConame.getCcid();

        if (this.coname != null) {
            wholeConame = this.coname;
        }

        Resources resources = getResources();
        String[] string = resources.getStringArray(R.array.Coname_context);

        String[] string1 = resources.getStringArray(R.array.textinput);
        string1 = new String[]{wholeConame.getCcid() + "", wholeConame.getCcNumber(), wholeConame.getCcType(), wholeConame.getCname(), wholeConame.getCper(), wholeConame.getCphoe(), wholeConame.getDisnum(), wholeConame.getRowfy() + "", wholeConame.getTransportfy() + "", wholeConame.getOtherfy() + "", wholeConame.getHjfy() + "", wholeConame.getInstallationarea(), wholeConame.getSpecificarea(), wholeConame.getArea(), wholeConame.getYjstartdate(), wholeConame.getYjenddate(), wholeConame.getYjdatenum() + "", wholeConame.getBeizhu(), wholeConame.getStatus(), wholeConame.getManCompany(), wholeConame.getAddPer(), wholeConame.getAddTime(), wholeConame.getShPer(), wholeConame.getShTime()};

        for (int i = 0; i < string.length; i++) {
            formlist formlist1 = new formlist();
            formlist1.setName(string[i]);
            formlist1.setInput(string1[i]);
            formlist.add(formlist1);
        }
    }

    @OnClick(R.id.fanhui_lin)
    public void onClick() {

        AppManager.getAppManager().finishActivity(this);
    }

}
