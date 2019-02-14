package com.system.bhouse.bhouse.CommonTask.TechnologyExecution;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.base.CheckStatusBeanImpl;
import com.system.bhouse.base.StatusBean;
import com.system.bhouse.base.SubmitStatusBeanImpl;
import com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Base.BaseBackFragment;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.BaseFragment.RecycleViewAdapterManager;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.BaseFragment.SwipeItemLayout;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ComponentIntoWareHouse.ComponentIntoWareHouseContentMessageActivity_;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ComponentTakeOff.ComponentTakeOffContentMessageActivity_;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.MaintenanceIntoWarehouse.MaintenanceWarehouseContentMessageActivity_;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.MaintenanceOutWarehouse.MaintenanceOutWarehouseContentMessageActivity_;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ModuleAssignMent.ModuleAssignMentContentMessageActivity_;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ModuleFeeding.ModuleFeedingContentMessageActivity_;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.PeopleAssignment.PeopleAssignmentContentMessageActivity_;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.StationCarAssignMent.StationCarAssignMentContentMessageActivity_;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.entity.RelatedDetailBean;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.entity.TechnologyBean;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseViewHolder;
import com.system.bhouse.bhouse.CommonTask.Widget.TimeLineItemTopBottomDecoration;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.config.Const;
import com.system.bhouse.utils.ValueUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018-07-17.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution
 */

public class Test1Fragment extends BaseBackFragment implements ItemTouchListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.my_recycle_view)
    RecyclerView my_recycle_view;

    //data 工序数据
    protected List<String> data=new ArrayList<>();

    //data工序数组
    protected String[] stringArray;

    protected BaseQuickAdapter<RelatedDetailBean, MyBaseViewHolder> adapter;
    private ArrayList<RelatedDetailBean> bean;
    private StatusBean mStatusBean;

    private String componentQr;

    private String orderId;
    private TechnologyBean technologyBeans = null;
    private boolean isUpdate;
    private RecycleViewAdapterManager recycleViewAdapterManager;

    public static final String ARG_componentQr="arg_componentQr";
    public static final String ARG_OrderId="arg_OrderId";
    public static final String ARG_title="arg_title";

    public static Test1Fragment newInstance(String componentQr, String OrderId, TechnologyBean title) {
        
        Bundle args = new Bundle();
        args.putString(ARG_componentQr,componentQr);
        args.putString(ARG_OrderId,OrderId);
        args.putParcelable(ARG_title,title);
        Test1Fragment fragment = new Test1Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.technology_layout_fragment2);
        Bundle bundle = getArguments();
        if (bundle!=null)
        {
            this.componentQr=bundle.getString(ARG_componentQr);
            this.orderId= bundle.getString(ARG_OrderId);
            this.technologyBeans= bundle.getParcelable(ARG_title);
        }
    }

    //初始化布局
    @Override
    public void onLazyInitView(Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        my_recycle_view.setLayoutManager(linearLayoutManager);

        mStatusBean=new StatusBean();

        my_recycle_view.addItemDecoration(new TimeLineItemTopBottomDecoration());

        recycleViewAddEmptySection(my_recycle_view);

        adapter = new BaseQuickAdapter<RelatedDetailBean, MyBaseViewHolder>(R.layout.multiple_right_menu) {
            @Override
            protected void convert(MyBaseViewHolder helper, RelatedDetailBean item) {
                helper.setText(R.id.tv_title, item.getDocumentName());
                helper.setText(R.id.tv_sub_title, App.Mancompany);
                if (item.getDocumentStatus().equals("已完成")) {
                    helper.setBackgroundRes(R.id.relative_bg, R.drawable.bg_timeline_btn_disable);

                }else{
                    helper.setBackgroundRes(R.id.relative_bg, R.drawable.bg_timeline_btn_normal);
                }
                helper.setGone(R.id.right_menu,item.getDocumentStatus().equals("已完成"));

                helper.setGone(R.id.left_menu,item.getDocumentStatus().equals(""));

                helper.addOnClickListener(R.id.right_menu);
                helper.addOnClickListener(R.id.left_menu);

                SwipeItemLayout layout = (SwipeItemLayout) helper.getView(R.id.swipe_layout);

                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (layout.isOpen())
                        {
                            layout.close();
                        }else {
                            layout.open();
                            if (recycleViewAdapterManager!=null)
                            recycleViewAdapterManager.setItemMultiViewClose(helper.getAdapterPosition());
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

        adapter.bindToRecyclerView(my_recycle_view);
        recycleViewAdapterManager = new RecycleViewAdapterManager(adapter,linearLayoutManager);
        adapter.setOnItemChildClickListener(this);

    }

    /**
     * 传输的数据
     * @param componentQr
     * @param orderId
     * @param technologyBeans
     */
    @Override
    public void sendRelatedDetail(String componentQr, String orderId, TechnologyBean technologyBeans) {
        this.componentQr=componentQr;
        this.orderId=orderId;
        this.technologyBeans =technologyBeans;
        isUpdate =true;
        if (isUpdate)
        ParseNetwork(technologyBeans);
    }

    /**
     * 获取状态数据
     *    {
     "单据名称": "人工派工单",
     "单据状态": "",
     "单据表单": "Production_order_Per",
     "源单ID": ""
     },
     * @param technologyBean
     */
    public void ParseNetwork(TechnologyBean technologyBean){
        ApiWebService.Get_Pro_Working_Item_r_poid_Json(getActivity(), new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                bean= App.getAppGson().fromJson(result, new TypeToken<List<RelatedDetailBean>>() {
                }.getType());
                if (ValueUtils.IsFirstValueExist(bean))
                {
                    adapter.setNewData(bean);
                }else {
                    adapter.setNewData(bean);
                    adapter.setEmptyView(notDataView);
                }
                isUpdate=false;
            }

            @Override
            public void ErrorBack(String error) {
                setErrorViewContext(error);
                adapter.setEmptyView(errorView);
                isUpdate=false;
            }
        },technologyBean.getWorkOrdersubDirectoryID());
    }

    //对用户可见时回调
    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (!isUpdate&&technologyBeans!=null)
            ParseNetwork(technologyBeans);
    }

    /**
     * 删除 查看 修改 三个按钮的调用--offline
     * @param adapter
     * @param view     The view whihin the ItemView that was clicked
     * @param position The position of the view int the adapter
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        gotoActivityMultiIntent(view, position);
    }

    private void gotoActivityMultiIntent(View view, int position) {

        if (this.bean.get(position).getDocumentStatus().equals(Const.Completed_STATUS))
        {
            //审核状态
            CheckStatusBeanImpl checkStatusBean = new CheckStatusBeanImpl();
            checkStatusBean.setVisCheckFBtn(true);
            mStatusBean.setBean(checkStatusBean);
            mStatusBean.setLookStatus(true);
        }else {
            mStatusBean.setBean(new SubmitStatusBeanImpl().setVisSubmitBtn(true));
            mStatusBean.setNewStatus(true);
        }

        switch (view.getId())
        {
            //查看
            case R.id.right_menu:

                switch (this.bean.get(position).getDatabaseTableName())
                {
                     //人力分配
                    case "Production_order_Per":
                        goPeopleAssignment(this.bean.get(position).getSoutceID(),"");

                        break;
                    //模具分配
                    case "Production_order_Mould":
                        goModuleAssignment(this.bean.get(position).getSoutceID(),"");

                        break;
                        //台车分配
                    case "Production_order_Trolley":

                        goStationCarAssignMent(this.bean.get(position).getSoutceID(),"");

                        break;

                        //模具投料单
                    case "Production_order_Tray":
                        goModuleFeeding(this.bean.get(position).getSoutceID(),"");
                        break;
                        //养护入库
                    case "Production_order_yhy_In":
                        goMaintenanceWare(this.bean.get(position).getSoutceID(),"");
                        break;
                    case "Production_order_yhy_Out":
                       goMaintenanceOutWareHouse(this.bean.get(position).getSoutceID(),"");
                        break;
                        //构件脱模
                    case "Production_order_yhy_Out_Mould":
                        mStatusBean.setBean(new SubmitStatusBeanImpl().setVisDeleteBtn(true));
                        mStatusBean.setLookStatus(true);
                        goComponentTakeOff(this.bean.get(position).getSoutceID(),"");
                        break;
                        //构件入库
                    case "Production_order_In":
                        goComponentIntoWareHouse(this.bean.get(position).getSoutceID(),"");
                        break;
                }
                break;

            //新建
            case R.id.left_menu:

                switch (bean.get(position).getDatabaseTableName())
                {
                    //人力分配
                    case "Production_order_Per":

                        ApiWebService.Get_Production_order_Per_po_Number(getActivity(), new ApiWebService.SuccessCall() {
                            @Override
                            public void SuccessBack(String result) {
                                goPeopleAssignment("",result);
                            }
                            @Override
                            public void ErrorBack(String error) {

                            }
                        });

                        break;
                    //模具分配
                    case "Production_order_Mould":
                        mStatusBean.getBean().setVisQRBtn(true);
                        ApiWebService.Get_Production_order_Mould_po_Number(getActivity(), new ApiWebService.SuccessCall() {
                            @Override
                            public void SuccessBack(String result) {

                                goModuleAssignment("",result);
                            }
                            @Override
                            public void ErrorBack(String error) {

                            }
                        });
                        break;

                        //台车分配
                    case "Production_order_Trolley":
                        mStatusBean.getBean().setVisQRBtn(true);
                        ApiWebService.Get_Production_order_Trolleypo_Number(getActivity(), new ApiWebService.SuccessCall() {
                            @Override
                            public void SuccessBack(String result) {
                                goStationCarAssignMent("",result);
                            }
                            @Override
                            public void ErrorBack(String error) {

                            }
                        });
                        break;

                    //模具投料单
                    case "Production_order_Tray":
                        goModuleFeeding("","");
                        break;

                    //养护入库
                    case "Production_order_yhy_In":

                        goMaintenanceWare(this.bean.get(position).getSoutceID(),"");
                        break;
                    //养护出库
                    case "Production_order_yhy_Out":
                        goMaintenanceOutWareHouse(this.bean.get(position).getSoutceID(),"");
                        break;
                        //构件脱模
                    case "Production_order_yhy_Out_Mould":

                        //构件脱模的特殊界面 只有两个状态
                        SubmitStatusBeanImpl submitStatusBean = new SubmitStatusBeanImpl();
                        submitStatusBean.setVisQRBtn(true);
                        mStatusBean.setBean(submitStatusBean);
                        mStatusBean.setNewStatus(true);

                        ApiWebService.Get_Production_order_yhy_Out_Mouldpo_Number(getActivity(), new ApiWebService.SuccessCall() {
                            @Override
                            public void SuccessBack(String result) {
                                goComponentTakeOff("",result);
                            }

                            @Override
                            public void ErrorBack(String error) {

                            }
                        });
                        break;
                    //构件入库单
                    case "Production_order_In":

                        ApiWebService.Get_Production_order_In_prid_QR_Code_po_Number(getActivity(), new ApiWebService.SuccessCall() {
                            @Override
                            public void SuccessBack(String result) {
                                goComponentIntoWareHouse("",result);
                            }

                            @Override
                            public void ErrorBack(String error) {

                            }
                        });
                        break;
                }
                break;
        }
    }

    /**
     * 转向 构件入库界面
     */
    private void goComponentIntoWareHouse(String HId, String result){
        ComponentIntoWareHouseContentMessageActivity_.intent(this).HId(HId).receiptHnumber(result).componentQr(componentQr).orderId(orderId).mStatus(mStatusBean).start();
    }

    /**
     * 转向构件脱模界面
     */
    private void goComponentTakeOff(String HId, String result) {

        ComponentTakeOffContentMessageActivity_.intent(this).HId(HId).receiptHnumber(result).componentQr(componentQr).orderId(orderId).mStatus(mStatusBean).start();
    }


    /**
     * 转向养护出库界面
     */
    private void goMaintenanceOutWareHouse(String HId, String result) {
        MaintenanceOutWarehouseContentMessageActivity_.intent(this).HId(HId).receiptHnumber(result).componentQr(componentQr).orderId(orderId).mStatus(mStatusBean).start();
    }

    /**
     * 转向养护入库界面
     */
    private void goMaintenanceWare(String HId, String result) {
        MaintenanceWarehouseContentMessageActivity_.intent(this).HId(HId).receiptHnumber(result).componentQr(componentQr).orderId(orderId).mStatus(mStatusBean).start();
    }

    /**
     * 模具投料
     * @param HId
     * @param result
     */
    private void goModuleFeeding(String HId, String result) {
        if (TextUtils.isEmpty(HId)&&TextUtils.isEmpty(result))
        {
            //模具投料的特殊界面 只有两个状态
            SubmitStatusBeanImpl submitStatusBean = new SubmitStatusBeanImpl();
            submitStatusBean.setVisQRBtn(true);
            mStatusBean.setBean(submitStatusBean);
            mStatusBean.setNewStatus(true);
        }else {
            mStatusBean.setBean(new SubmitStatusBeanImpl().setVisDeleteBtn(true));
            mStatusBean.setLookStatus(true);
        }

        ModuleFeedingContentMessageActivity_.intent(this).HId(HId).receiptHnumber(result).componentQr(componentQr).orderId(orderId).mStatus(mStatusBean).start();
    }

    /**
     * 转向台车界面
     */
    private void goStationCarAssignMent(String HId, String result) {
        StationCarAssignMentContentMessageActivity_.intent(this).HId(HId).receiptHnumber(result).componentQr(componentQr).orderId(orderId).mStatus(mStatusBean).start();
    }


    /**
     * 转向模具分配界面
     * @param HId
     * @param result
     */
    private void goModuleAssignment(String HId, String result) {
        ModuleAssignMentContentMessageActivity_.intent(this).HId(HId).receiptHnumber(result).componentQr(componentQr).orderId(orderId).mStatus(mStatusBean).start();
    }

    /**
     * 转向人工分配界面
     * @param HId
     * @param result
     */
    private void goPeopleAssignment(String HId,String result) {
        PeopleAssignmentContentMessageActivity_.intent(getActivity()).HId(HId).receiptHnumber(result).componentQr(componentQr).workOrderID(technologyBeans.getWorkOrdersubDirectoryID()).orderId(orderId).mStatus(mStatusBean).start();
    }

    protected static class MyBaseViewHolder extends BaseViewHolder {

        public MyBaseViewHolder(View view) {
            super(view);
        }
    }
}
