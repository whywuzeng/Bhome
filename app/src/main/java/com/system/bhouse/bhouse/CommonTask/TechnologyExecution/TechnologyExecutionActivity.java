package com.system.bhouse.bhouse.CommonTask.TechnologyExecution;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Base.MySupportActivity;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.utils.MeasureUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-07-09.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution
 */

public class TechnologyExecutionActivity extends MySupportActivity  {

    private static final String TAG = "TechnologyExecutionActivity";

    public static final String RESULTQRCOMPONENT = "resultQrComponent";

    public static final String ORDER_ID = "order_id";

    //二维码构件码
    public String resultQrcomponent = null;

    //订单ID
    public String Order_Id = null;


    private ItemTouchListener mItemTouchListener;
    private TechnologyExecutionFragment mTechnologyExecutionFragment;


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

        mTechnologyExecutionFragment = findFragment(TechnologyExecutionFragment.class);
        if (mTechnologyExecutionFragment == null) {
            loadRootFragment(R.id.content_contarner_layout, TechnologyExecutionFragment.newInstance(Order_Id,resultQrcomponent));
        }
    }

    /**
     * 这 调整tabview底线的宽度
     * @param tabLayout
     */
    public void reflex(final TabLayout tabLayout){
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    int dp10 = MeasureUtil.dip2px(tabLayout.getContext(), 40);

                    try {
                        Method method = mTabStrip.getClass().getDeclaredMethod("setSelectedIndicatorColor", int.class);
                        method.setAccessible(true);
                        method.invoke(mTabStrip, Color.parseColor("#FFC3C3C3"));
                    } catch (NoSuchMethodException | InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }


                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    //扫码回调
//    private void QrCodeComponent(int resultCode, Intent data) {
//        if (resultCode == Activity.RESULT_OK) {
//            Bundle bundle = data.getBundleExtra("bundle");
//            String resultQr = bundle.getString("result");
//            int extraPosition = bundle.getInt("position");
//
//            ApiWebService.Get_Pro_Working_Main_poid_byprid_QR_Code_Json(this, new ApiWebService.SuccessCall() {
//                @Override
//                public void SuccessBack(String result) {
//                    //[{"订单ID":"08776473ea6a4c0ea7a3291d4aa0d359","订单编号":"SCDD-7-201807-0008"}]
//                    String orderID = null;
//                    String orderNumber = null;
//                    try {
//                        JSONArray jsonArray = new JSONArray(result);
//                        Orderbean orderbean;
//                        for (int i=0;i<jsonArray.length();i++)
//                        {
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            orderID = jsonObject.optString("订单ID", "");
//                            orderNumber = jsonObject.optString("订单编号", "");
//                            orderbean=new Orderbean();
//                            orderbean.oriderid=orderID;
//                            orderbean.oriderNumber=orderNumber;
//                            items.add(orderbean);
//                        }
//                        showSpinnerDialog();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    } finally {
//                        if (!TextUtils.isEmpty(orderID) && !TextUtils.isEmpty(orderNumber)) {
//                            resultQrcomponent = resultQr;
//                            Order_Id = orderID;
//
//                            //显示textview数值
//                            tv_component_content.setText(resultQr);
//                        }else{
//                            tv_component_content.setText("");
//                        }
//                    }
//                }
//
//                @Override
//                public void ErrorBack(String error) {
//
//                }
//            }, resultQr);
//        }
//    }

//    protected static class MyBaseViewHolder extends BaseViewHolder {
//
//        public MyBaseViewHolder(View view) {
//            super(view);
//        }
//    }
//
//    protected InnerHandle Technologyhandler = new InnerHandle(this) {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//            if (ValueUtils.IsFirstValueExist(TechnologyBeans)) {
//
//                for (int i = 0; i < TechnologyBeans.size(); i++) {
//                    TechnologyBean technologyBean = TechnologyBeans.get(i);
//                    if (technologyBean.getWorkOrderStatus().equals("执行中")) {
//                        initRecycleViewBg(adapter, technologyBean.getWorkOrderSequence() - 1);
//                    }
//                }
//            }
//        }
//    };
//
//
//    //弱引用 Handle的写法
//    public class InnerHandle extends Handler {
//        private final WeakReference<TechnologyExecutionActivity> wwTimeLineActivityWeakReference;
//
//        public InnerHandle(TechnologyExecutionActivity mActivity){
//            this.wwTimeLineActivityWeakReference=new WeakReference<TechnologyExecutionActivity>(mActivity);
//        }
//    }
//
}
