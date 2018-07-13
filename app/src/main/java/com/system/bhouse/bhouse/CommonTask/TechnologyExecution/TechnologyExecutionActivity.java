package com.system.bhouse.bhouse.CommonTask.TechnologyExecution;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.system.bhouse.Custom.OnSpinerItemClick;
import com.system.bhouse.Custom.SpinnerDialog;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.entity.TechnologyBean;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.WWTimeLineActivity;
import com.system.bhouse.bhouse.CommonTask.Widget.TimeLineItemTopBottomDecoration;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.utils.ClickUtils;
import com.system.bhouse.utils.ValueUtils;
import com.zijunlin.Zxing.Demo.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018-07-09.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution
 */

public class TechnologyExecutionActivity extends WWTimeLineActivity implements BaseQuickAdapter.OnItemClickListener {

    private static final int RESULT_LOCAL = 2;

    public static final String RESULTQRCOMPONENT = "resultQrComponent";

    public static final String ORDER_ID = "order_id";

    public static final int RESULT_COMPONENT = 2 << 2;

    //二维码构件码
    public String resultQrcomponent = null;

    //订单ID
    public String Order_Id = null;

    @Bind(R.id.my_recycle_view)
    RecyclerView my_recycle_view;

    private View notDataView;
    private View errorView;

    //数据的集合
    private ArrayList<TechnologyBean> TechnologyBeans;

    //数据显示控件
    @Bind(R.id.tv_component_content)
     TextView tv_component_content;

    @Bind(R.id.tv_orderid_content)
     TextView tv_orderid_content;
    private SpinnerDialog spinnerDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.technology_layout_activity);
        ButterKnife.bind(this);
        setActionBarMidlleTitle("工艺执行");
        annotationDispayHomeAsUp();

        //获取传递值
        Intent intent = getIntent();
        if (intent != null) {
            resultQrcomponent = intent.getStringExtra(RESULTQRCOMPONENT);
            Order_Id = intent.getStringExtra(ORDER_ID);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TechnologyExecutionActivity.this);
        my_recycle_view.setLayoutManager(linearLayoutManager);

        stringArray = getResources().getStringArray(R.array.technology_execution);
        data.addAll(Arrays.asList(stringArray));
        my_recycle_view.addItemDecoration(new TimeLineItemTopBottomDecoration(), 0);

        notDataView = this.getLayoutInflater().inflate(R.layout.taskcomon_empty_view, (ViewGroup) my_recycle_view.getParent(), false);
        errorView = this.getLayoutInflater().inflate(R.layout.taskcommon_error_view, (ViewGroup) my_recycle_view.getParent(), false);

        adapter = new BaseQuickAdapter<String, MyBaseViewHolder>(R.layout.timeline_item) {
            @Override
            protected void convert(MyBaseViewHolder helper, String item) {
                helper.setText(R.id.tv_title, item);
                helper.setText(R.id.tv_sub_title, App.Mancompany);
            }
        };
        my_recycle_view.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        AskForBackgroud();
        showSpinnerDialog();
    }

    @OnClick(R.id.component_qrcode)
    public void componentClick() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, RESULT_COMPONENT);
    }

    @OnClick(R.id.orderid_qrcode)
    public void orderIdClick() {
       if (!ClickUtils.isFastDoubleClick()) {
           //弹出选择对话框
           spinnerDialog.showSpinerDialog();
       }
    }

    private void showSpinnerDialog() {
        ArrayList<String> items=new ArrayList<>();
        items.add("SCDD-7-20187-0008");
        items.add("SCDD-7-20186-0007");
        items.add("SCDD-7-20186-0006");
        items.add("SCDD-7-20186-0005");
        items.add("SCDD-7-20186-0002");
        items.add("SCDD-7-20186-0001");

        spinnerDialog = new SpinnerDialog(this, items, getResources().getString(R.string.lookup_order_id));
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                tv_orderid_content.setText(item);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_COMPONENT:
                QrCodeComponent(resultCode, data);
                break;
        }
    }

    private void QrCodeComponent(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getBundleExtra("bundle");
            String resultQr = bundle.getString("result");
            int extraPosition = bundle.getInt("position");

            ApiWebService.Get_Pro_Working_Main_poid_byprid_QR_Code_Json(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    //[{"订单ID":"08776473ea6a4c0ea7a3291d4aa0d359","订单编号":"SCDD-7-201807-0008"}]
                    String orderID = null;
                    String orderNumber = null;
                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        orderID = jsonObject.optString("订单ID", "");
                        orderNumber = jsonObject.optString("订单编号", "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        if (!TextUtils.isEmpty(orderID) && !TextUtils.isEmpty(orderNumber)) {
                            resultQrcomponent = resultQr;
                            Order_Id = orderID;

                            //显示textview数值
                            tv_component_content.setText(resultQr);
                        }else{
                            tv_component_content.setText("");
                        }
                    }
                }

                @Override
                public void ErrorBack(String error) {

                }
            }, resultQr);
        }
    }



    protected InnerHandle Technologyhandler = new InnerHandle(this) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (ValueUtils.IsFirstValueExist(TechnologyBeans)) {

                for (int i = 0; i < TechnologyBeans.size(); i++) {
                    TechnologyBean technologyBean = TechnologyBeans.get(i);
                    if (technologyBean.getWorkOrderStatus().equals("执行中")) {
                        initRecycleViewBg(adapter, technologyBean.getWorkOrderSequence() - 1);
                    }
                }
            }
        }
    };

    private void AskForBackgroud() {

        ApiWebService.Get_Pro_Working_Main_poid_Json(App.getContextApp(), new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                TechnologyBeans = App.getAppGson().fromJson(result, new TypeToken<List<TechnologyBean>>() {
                }.getType());

                if (ValueUtils.IsFirstValueExist(TechnologyBeans)) {
                    adapter.setNewData(data);

                    Technologyhandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Technologyhandler.sendEmptyMessage(1);
                        }
                    }, 500);

                }
                else {
                    adapter.setEmptyView(notDataView);
                }

            }

            @Override
            public void ErrorBack(String error) {

            }
        }, resultQrcomponent, Order_Id);
    }

    //点击响应
    @Override
    protected void IntentToFragment(int position) {
        ShowShort(position + "");
        switch (position) {
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            case 6:

                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        initRecycleViewBg(adapter, view, position);
        IntentToFragment(position);
    }

    private void initRecycleViewBg(BaseQuickAdapter adapter, int position) {
        View currentView = adapter.getViewByPosition(my_recycle_view, position, R.id.rl_content_layout);
        SelectColorBg(currentView);
        currentView.setTag(Color.rgb(51, 138, 185));
        int itemCount = adapter.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            if (i > position) {
                View byPosition = adapter.getViewByPosition(my_recycle_view, i, R.id.rl_content_layout);
                UnSelectColorBg(byPosition);
            }
            else if (i < position) {
                View byPosition = adapter.getViewByPosition(my_recycle_view, i, R.id.rl_content_layout);
                DisableBg(byPosition);
            }
            if (i <= position) {
                View byPosition = adapter.getViewByPosition(my_recycle_view, i, R.id.rlTimeline);
                SelectLineDotBg(byPosition);
            }
            else {
                View byPosition = adapter.getViewByPosition(my_recycle_view, i, R.id.rlTimeline);
                UnSelectLineDotBg(byPosition);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
