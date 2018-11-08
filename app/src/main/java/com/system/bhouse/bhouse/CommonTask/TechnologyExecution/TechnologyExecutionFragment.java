package com.system.bhouse.bhouse.CommonTask.TechnologyExecution;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.system.bhouse.Custom.OnSpinerItemClick;
import com.system.bhouse.Custom.SpinnerDialog;
import com.system.bhouse.Custom.TagGroup.TagGroup;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Base.BaseBackFragment;
import com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Fragment.LoadingIntoWareHouseContentActivity;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.BaseFragment.RecycleViewAdapterManager;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.BaseFragment.SwipeItemLayout;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.entity.Orderbean;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.entity.TechnologyBean;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.selectploy.DisablePloy;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.selectploy.DisableRecallPloy;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.selectploy.NoAssociationPloy;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.selectploy.SelectPloy;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.selectploy.TechnologSelectColorBg;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.selectploy.UnSelectLineDotBg;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.selectploy.UnSelectPloy;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseViewHolder;
import com.system.bhouse.bhouse.CommonTask.Widget.TimeLineItemTopBottomDecoration;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.config.Const;
import com.system.bhouse.utils.ClickUtils;
import com.system.bhouse.utils.blankutils.TimeUtils;
import com.system.bhouse.utils.ValueUtils;
import com.zijunlin.Zxing.Demo.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018-07-16.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution
 */

public class TechnologyExecutionFragment extends BaseBackFragment implements BaseQuickAdapter.OnItemClickListener,BaseQuickAdapter.OnItemChildClickListener{

    private static final int RESULT_LOCAL = 2;

    public static final String RESULTQRCOMPONENT = "resultQrComponent";

    public static final String ORDER_ID = "order_id";

    public static final int RESULT_COMPONENT = 2 << 2;

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

    @Bind(R.id.orderid_qrcode)
    Button orderidBtn;


    //data工序数组
    protected String[] stringArray;

    //data 工序数据
    protected List<String> data=new ArrayList<>();

    protected BaseQuickAdapter<TechnologyBean, MyBaseViewHolder> adapter;
    private RecycleViewAdapterManager recycleViewAdapterManager;


    //二维码构件码
    public String resultQrcomponent = null;

    //订单ID
    public String Order_Id = null;

    public static final String ARG_RESULTQRCOMPONENT = "arg_resultQrComponent";

    public static final String ARG_ORDER_ID = "arg_order_id";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.Order_Id = arguments.getString(ARG_ORDER_ID);
            this.resultQrcomponent = arguments.getString(ARG_RESULTQRCOMPONENT);
        }
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.technology_layout_fragment);
    }

    /**
     * newInstance
     * @return
     * @param order_Id
     * @param resultQrcomponent
     */
    public static TechnologyExecutionFragment newInstance(String order_Id, String resultQrcomponent) {
        
        Bundle args = new Bundle();
        args.putString(ARG_ORDER_ID,order_Id);
        args.putString(ARG_RESULTQRCOMPONENT,resultQrcomponent);
        TechnologyExecutionFragment fragment = new TechnologyExecutionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * toolbar 回退  Activity root界面Fragment
     * @param toolbar
     */
    @Override
    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoadingIntoWareHouseContentActivity)_mActivity).onBackPressedSupport();
            }
        });
    }

    //对用户可见时回调
    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (!TextUtils.isEmpty(resultQrcomponent)&&!TextUtils.isEmpty(Order_Id)) {
            AskForBackgroud();
        }
    }

    //对用户不可见时回调
    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
    }

    @Override
    public void onLazyInitView(Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        my_recycle_view.setLayoutManager(linearLayoutManager);

        stringArray = getResources().getStringArray(R.array.technology_execution);
        data.addAll(Arrays.asList(stringArray));
        my_recycle_view.addItemDecoration(new TimeLineItemTopBottomDecoration(), 0);

        notDataView = inflater.inflate(R.layout.taskcomon_empty_view, (ViewGroup) my_recycle_view.getParent(), false);
        errorView = inflater.inflate(R.layout.taskcommon_error_view, (ViewGroup) my_recycle_view.getParent(), false);
        adapter = new BaseQuickAdapter<TechnologyBean, MyBaseViewHolder>(R.layout.item_left_and_right_menu) {
            @Override
            protected void convert(MyBaseViewHolder helper, TechnologyBean item) {
                helper.setText(R.id.tv_title, item.workOrderName);
                helper.setText(R.id.tv_sub_title, App.Mancompany);
                SwipeItemLayout layout = (SwipeItemLayout) helper.getView(R.id.swipe_layout);
                TextView Rightview = (TextView) helper.getView(R.id.right_menu);
                //关联明细
                helper.setGone(R.id.rightDetail_menu,item.isRelateForm);
                TagGroup tagGroup = (TagGroup) helper.getView(R.id.tag_group);
                TagGroup tagGroup1 = (TagGroup) helper.getView(R.id.tag_group1);
                TagGroup groupRed= (TagGroup) helper.getView(R.id.tag_group_beauty_inverse);
                if (TextUtils.isEmpty(item.getStartTime())||TextUtils.isEmpty(item.getEndTime()))
                {
                    tagGroup.setVisibility(View.GONE);
                }else {
                    tagGroup.setVisibility(View.VISIBLE);
                    String simpleDataString = TimeUtils.getSimpleDataString(item.getStartTime());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(simpleDataString);

                    String simpleDataEndString = TimeUtils.getSimpleDataString(item.getEndTime());
                    SimpleDateFormat simpleDateEndFormat = new SimpleDateFormat(simpleDataEndString);

                    tagGroup.setTags(String.format("耗时:%s", TimeUtils.getFitTimeSpanTwoFormat(item.getStartTime(), item.getEndTime(), simpleDateFormat,simpleDateEndFormat,3)));
                }
                getContextMessage(item.isHang,tagGroup1,groupRed);


                if (item.getWorkOrderStatus().equals("执行中")) {
                    SelectColorBg(helper.itemView);
                    SelectLineDotBg(helper.itemView);
                }else if(item.getWorkOrderStatus().equals("未开始"))
                {
                    UnSelectColorBg(helper.itemView);
                    UnSelectLineDotBg(helper.itemView);
                }else if (item.getWorkOrderStatus().equals("已完成"))
                {
                    DisableBg(helper.itemView);
                    SelectLineDotBg(helper.itemView);
                }

                if (!item.isRelateForm){
                    //关联明细策略
                    NoAssociationBg(helper.itemView);
                }

                helper.addOnClickListener(R.id.rightremove_menu);
                helper.addOnClickListener(R.id.rightDetail_menu);
                helper.addOnClickListener(R.id.right_menu);

                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (layout.isOpen())
                        {
                            layout.close();
                        }else {
                            layout.open();
                            if (recycleViewAdapterManager !=null)
                            {

                                recycleViewAdapterManager.setItemMultiViewClose(helper.getAdapterPosition());
                            }
                        }
                    }
                });

//                if (Rightview != null) {
//                    Rightview.setOnClickListener(v -> {
////                        mItemTouchListener.onRightMenuClick("right " + helper.getAdapterPosition());
//                        layout.close();
//                    });
//                }
            }
        };
//        my_recycle_view.setAdapter(adapter);
        adapter.bindToRecyclerView(my_recycle_view);

        recycleViewAdapterManager = new RecycleViewAdapterManager(adapter,linearLayoutManager);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
        orderidBtn.setClickable(false);
        //初始化 肯定到是空的
        adapter.setEmptyView(notDataView);
    }

    @OnClick(R.id.ll_component_qrcode)
    public void componentClick() {
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, RESULT_COMPONENT);
    }

    @OnClick(R.id.ll_orderid_qrcode)
    public void orderIdClick() {
        if (!ClickUtils.isFastDoubleClick()) {
            //弹出选择对话框
            if (spinnerDialog==null||TextUtils.isEmpty(tv_component_content.getText()))
            {
                return;
            }
            spinnerDialog.showSpinerDialog();
        }
    }

    //初始化 spinnerDialog
    private void showSpinnerDialog(ArrayList<Orderbean> items) {
        orderidBtn.setClickable(true);
        //这个数据  是由 扫码二维码得到的数据.
            spinnerDialog = new SpinnerDialog<Orderbean>(getActivity(), items, getActivity().getResources().getString(R.string.lookup_order_id));
            spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick<Orderbean>() {

                @Override
                public void onClick(Orderbean item, int position) {
                    tv_orderid_content.setText(item.oriderNumber);
                    Order_Id = item.oriderid;
                    //重新请求工序
                    AskForBackgroud();
                }
            });
    }

    /**
     * 启动工序 和 停止工序  模板明细 点击事件
     * @param adapter
     * @param view     The view whihin the ItemView that was clicked
     * @param position The position of the view int the adapter
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (ClickUtils.isFastDoubleClick())
        {
            return;
        }
        switch (view.getId())
        {
            case R.id.rightremove_menu:
                Cancel(position);
                break;
            case R.id.rightDetail_menu:
                Detail(position);
                break;
            case R.id.right_menu:
                StartUp(position);
                break;
        }
    }

    protected static class MyBaseViewHolder extends BaseViewHolder {

        public MyBaseViewHolder(View view) {
            super(view);
        }
    }

    /**
     * 背景切换的Handle
     */
    protected InnerHandle Technologyhandler = new InnerHandle((TechnologyExecutionActivity) getActivity()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (ValueUtils.IsFirstValueExist(TechnologyBeans)) {

                for (int i = 0; i < TechnologyBeans.size(); i++) {
                    TechnologyBean technologyBean = TechnologyBeans.get(i);
                    /**
                     * 选中上一个 执行工序
                     * 延时执行  无关联策略
                     */
                    if (!technologyBean.isRelateForm) {
                        //看不见的就获取不到
                        View firstcurrentView = adapter.getViewByPosition(my_recycle_view, technologyBean.getWorkOrderSequence() - 1, R.id.rl_content_layout);
                        NoAssociationBg(firstcurrentView);
                    }
                }
            }
        }
    };


    //弱引用 Handle的写法
    public class InnerHandle extends Handler {
        private final WeakReference<TechnologyExecutionActivity> wwTimeLineActivityWeakReference;

        public InnerHandle(TechnologyExecutionActivity mActivity){
            this.wwTimeLineActivityWeakReference=new WeakReference<TechnologyExecutionActivity>(mActivity);
        }
    }


    /**
     * 请求工序接口
     */
    private void AskForBackgroud() {

        ApiWebService.Get_Pro_Working_Main_poid_Json(getActivity(), new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                TechnologyBeans = App.getAppGson().fromJson(result, new TypeToken<List<TechnologyBean>>() {
                }.getType());

                if (ValueUtils.IsFirstValueExist(TechnologyBeans)) {
                    adapter.setNewData(TechnologyBeans);

//                    Technologyhandler.sendEmptyMessageDelayed(1,300);
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
    protected void IntentToFragment(int position, View view) {

    }

    private String getContextMessage(boolean isHang, TagGroup tagGroup1,TagGroup tagRed) {
        if (isHang){
            tagGroup1.setVisibility(View.GONE);
            tagRed.setTags("挂起");
            tagRed.setVisibility(View.VISIBLE);
            return "挂起";
//            TagView childAt = tagGroup1.getChildAt(0);
//            style="@style/TagGroup.Beauty_Red.Inverse"

        }
        else {
            tagGroup1.setTags("正常");
            tagGroup1.setVisibility(View.VISIBLE);
            tagRed.setVisibility(View.GONE);
            return "正常";
        }
    }

    /**
     *  Item 点击事件 响应
     * @param adapter  the adpater
     * @param view     The itemView within the RecyclerView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     */

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        performItemLayoutBg(adapter, view, position);
        IntentToFragment(position,view);
    }

    private void performItemLayoutBg(BaseQuickAdapter adapter, View view, int position) {
//        TechnologSelectColorBg technologSelectColorBg = new TechnologSelectColorBg();
//        technologSelectColorBg.performItemLayoutopen(view);
//        int itemCount = adapter.getItemCount();
//        for (int i = 0; i < itemCount; i++) {
//            if (i!=position)
//            {
//                View byPosition = adapter.getViewByPosition(my_recycle_view, i, R.id.rl_content_layout);
//                technologSelectColorBg.performItemLayoutClose(view);
//            }
//        }
    }


    protected void NoAssociationBg(View view)
    {
        NoAssociationPloy noAssociationPloy= new TechnologSelectColorBg();
        noAssociationPloy.NoAssociationBg(view);
    }

    protected void DisableRecall(View view){
        DisableRecallPloy mDisableRecall = new TechnologSelectColorBg();
        mDisableRecall.DisableRecall(view);
    }

    protected void DisableBg(View view)
    {
        DisablePloy ploy = new TechnologSelectColorBg();
        ploy.Disablebg(view);
    }

    protected void UnSelectLineDotBg(View view) {
        UnSelectPloy ploy = new UnSelectLineDotBg();
        ploy.UnSelectbg(view);
    }

    protected void SelectLineDotBg(View view) {
        SelectPloy ploy = new UnSelectLineDotBg();
        ploy.selectBg(view);
    }

    protected void SelectColorBg(View view) {
        SelectPloy ploy = new TechnologSelectColorBg();
        ploy.selectBg(view);
    }

    protected void UnSelectColorBg(View view) {
        UnSelectPloy ploy = new TechnologSelectColorBg();
        ploy.UnSelectbg(view);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //回调时要置空数据
        resultQrcomponent=null;
        Order_Id=null;
        QrCodeComponent(resultCode, data);
//        switch (requestCode) {
//            case RESULT_COMPONENT:
//                QrCodeComponent(resultCode, data);
//                break;
//        }
        //原数据置空
        tv_orderid_content.setText("");
    }

    private void QrCodeComponent(int resultCode, Intent data) {
        //订单编号集合
        ArrayList<Orderbean> items=new ArrayList<>();
        if (resultCode == 0||resultCode == Activity.RESULT_OK) {
            //返回data ==null
            if (data==null)
            {
                return;
            }
            Bundle bundle = data.getBundleExtra("bundle");
            String resultQr = bundle.getString("result");
//              String resultQr="DZXQ-7-201806-0009.1002.1084.0100.003.1";
//            int extraPosition = bundle.getInt("position");

            ApiWebService.Get_Pro_Working_Main_poid_byprid_QR_Code_Json(_mActivity, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    //[{"订单ID":"08776473ea6a4c0ea7a3291d4aa0d359","订单编号":"SCDD-7-201807-0008"}]
                    if (TextUtils.isEmpty(result)||result.contains(Const.RESULTEMPTY))
                    {
                        if (TechnologyBeans!=null) {
                            TechnologyBeans.clear();
                        }
                        adapter.setEmptyView(notDataView);
                        tv_component_content.setText("");
                        return;
                    }
                    String orderID = null;
                    String orderNumber = null;
                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        Orderbean orderbean;
                        for (int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            orderID = jsonObject.optString("订单ID", "");
                            orderNumber = jsonObject.optString("订单编号", "");
                            orderbean=new Orderbean();
                            orderbean.oriderid=orderID;
                            orderbean.oriderNumber=orderNumber;
                            items.add(orderbean);
                        }
                        showSpinnerDialog(items);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        if (!TextUtils.isEmpty(orderID) && !TextUtils.isEmpty(orderNumber)) {
                            resultQrcomponent = resultQr;
                            //先给resultQrcomponent赋值
                            // Order_Id = orderID;

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
            },resultQr); //resultQr
        }
    }

    private void StartUp(int position) {

        ApiWebService.SettingsKeyNetWork(new ApiWebService.ObjectSuccessCall() {
            @Override
            public void SuccessBack(Object result) {

                ApiWebService.Get_Pro_Working_Main_poid_zx(getActivity(), new ApiWebService.SuccessCall() {
                    @Override
                    public void SuccessBack(String result) {
                        showButtomToast(result);
                    }

                    @Override
                    public void ErrorBack(String error) {
                        showButtomToast(error);
                    }
                }, TechnologyBeans.get(position).workRouteID, TechnologyBeans.get(position).workOrdersubDirectoryID);
            }

            @Override
            public void ErrorBack(Object error) {

            }
        });
    }

    private void Cancel(int position)
    {
        ApiWebService.SettingsKeyNetWork(new ApiWebService.ObjectSuccessCall() {
            @Override
            public void SuccessBack(Object result) {

                ApiWebService.Get_Pro_Working_Main_poid_zxf(getActivity(), new ApiWebService.SuccessCall() {
                    @Override
                    public void SuccessBack(String result) {
                        showButtomToast(result);
                    }

                    @Override
                    public void ErrorBack(String error) {

                    }
                },TechnologyBeans.get(position).workRouteID, TechnologyBeans.get(position).workOrdersubDirectoryID);
            }

            @Override
            public void ErrorBack(Object error) {

            }
        });
    }

    /**
     * 关联明细
     * @param position
     */
    private void Detail(int position){
        //点击传递fragment 数据
//        if (mTechnologyActivityListenter!=null)
//        {
//            mTechnologyActivityListenter.OnFragmentItemClick(resultQrcomponent,Order_Id,TechnologyBeans.get(position));
//        }
        start(Test1Fragment.newInstance(resultQrcomponent,Order_Id,TechnologyBeans.get(position)));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TechnologyActivityListenter)
        {
            mTechnologyActivityListenter=(TechnologyActivityListenter) context;
        }
    }

    private TechnologyActivityListenter mTechnologyActivityListenter;

    interface TechnologyActivityListenter {
         void OnFragmentItemClick(String componentQr, String OrderId, TechnologyBean title);
    }
}
