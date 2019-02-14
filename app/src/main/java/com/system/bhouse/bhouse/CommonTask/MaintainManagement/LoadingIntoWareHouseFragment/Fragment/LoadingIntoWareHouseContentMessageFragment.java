package com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.system.bhouse.Custom.ShowDeviceMessageCustomDialog;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.base.CheckStatusBeanImpl;
import com.system.bhouse.base.StatusBean;
import com.system.bhouse.base.SubmitStatusBeanImpl;
import com.system.bhouse.bean.BProBOM;
import com.system.bhouse.bean.wareHouseBean;
import com.system.bhouse.bhouse.CommonTask.BaseTaskFragment.BaseContentMessageActivity;
import com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Base.BaseContentMessageFragment;
import com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Fragment.Bean.LoadingIntoPickBean;
import com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Fragment.Bean.LoadingIntoWareHouseBean;
import com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Fragment.Bean.LoadingIntoWareHouseBeanSection;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.Widget.TimeLineItemTopBottomDecoration;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.TreeRecyclerAdapter;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.base.ViewHolder;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.item.GroupItem;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.item.SortChildItem;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.item.TreeItem;
import com.system.bhouse.bhouse.CommonTask.common.CommonDateTimePickerFragment;
import com.system.bhouse.bhouse.CommonTask.common.CommonPickerActivity_;
import com.system.bhouse.bhouse.CommonTask.utils.ComTaskContentItemSectionItemTouchHelper;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.utils.onMutiDataSetListener;
import com.system.bhouse.config.Const;
import com.system.bhouse.utils.ClickUtils;
import com.system.bhouse.utils.TenUtils.T;
import com.system.bhouse.utils.ValueUtils;
import com.system.bhouse.utils.custom.CustomToast;
import com.zijunlin.Zxing.Demo.CaptureActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import butterknife.BindView;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2018-03-05.
 * <p>
 *     装柜入库界面
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask
 */
public class LoadingIntoWareHouseContentMessageFragment extends BaseContentMessageFragment implements  GroupItem.onChildItemClickListener, onMutiDataSetListener ,BaseQuickAdapter.OnItemChildClickListener,BaseContentMessageActivity.SetOnAddItemClickListener {

    public static final String TAG = "comtaskcontentmessageactivity";
    private static final String module_name = "装柜入库";

    public static final String ARG_HID="arg_hid";
    public static final String ARG_RECEIPTHNUMBER="arg_receipthnumber";
    public static final String ARG_MSTATUS="arg_mstatus";
    public static final String ARG_COMPONENTQR="arg_componentqr";
    public static final String ARG_ORDERID="arg_orderid";
    public static final String ARG_WORKORDERID="arg_workorderid";
    //表头
    public static final String ON_CHILD_ITEM_CLICK = "onChildItemClick";

    //分录
    public static final String ON_ITEM_CHILD_CLICK = "onItemChildClick";

    @BindView(R.id.listView)
    RecyclerView listView;
    @BindView(R.id.topListView)
    RecyclerView topListView;
    @BindView(R.id.tv_title_live_layout)
    TextView tv_title_live_layout;
    @BindView(R.id.toolbar_com)
    Toolbar toolbar;


//    @Extra
    String HId;

//    @Extra
    String receiptHnumber;

//    @Extra
    StatusBean mStatus;

//    @Extra
    String componentQr;

//    @Extra
    String orderId;

//    @Extra
    String workOrderID;

    private ArrayList<LoadingIntoWareHouseBean> comTaskBeans = new ArrayList<>();
    private ArrayList<LoadingIntoWareHouseBeanSection> assignmentBeanSectionArrayList=new ArrayList<>() ;
    private TreeRecyclerAdapter treeRecyclerAdapter;
    private boolean isDeleteAble = true;
    public static final int RESULT_SORTITEM_SELECTPROJECT = 1001;
    public static final int REQUST_QRCODE = 1008;

    private String STATE_COMTASK = "state_comtask";
    private HashMap<String,String> headerProperties=new HashMap<>();
    private com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Fragment.LoadingIntoWareHouseSectionAdapter LoadingIntoWareHouseSectionAdapter;
    private ArrayList<SortChildItem.ViewModel> TopViewHolders;

    public static LoadingIntoWareHouseContentMessageFragment newInstance(String HId,String receiptHnumber,StatusBean mStatus,String componentQr,String orderId, String workOrderID) {
        
        Bundle args = new Bundle();
        args.putString(ARG_HID,HId);
        args.putString(ARG_RECEIPTHNUMBER,receiptHnumber);
        args.putSerializable(ARG_MSTATUS,mStatus);
        args.putString(ARG_COMPONENTQR,componentQr);
        args.putString(ARG_ORDERID,orderId);
        args.putString(ARG_WORKORDERID,workOrderID);

        LoadingIntoWareHouseContentMessageFragment fragment = new LoadingIntoWareHouseContentMessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //加载布局 初始化数据
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.activity_comtask_content_layout);
        Bundle bundle = getArguments();
        if (bundle!=null)
        {
            HId=bundle.getString(ARG_HID);
            receiptHnumber= bundle.getString(ARG_RECEIPTHNUMBER);
            mStatus= (StatusBean) bundle.getSerializable(ARG_MSTATUS);
            componentQr=bundle.getString(ARG_COMPONENTQR);
            orderId=bundle.getString(ARG_ORDERID);
            workOrderID=bundle.getString(ARG_WORKORDERID);
        }
    }

    //lazyView 懒加载
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        tv_title_live_layout.setText(module_name + "分录");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity,LinearLayoutManager.VERTICAL,false);
        listView.setLayoutManager(linearLayoutManager);
        recycleViewAddEmptySection(listView);
        String[] stringArray = getResources().getStringArray(R.array.loadingwarehouse_itemsection_order);

        LoadingIntoWareHouseSectionAdapter = new LoadingIntoWareHouseSectionAdapter(R.layout.activity_comtask_content_layout_item, R.layout.layout_home_recommend_empty_noheight, R.layout.comtask_content_item_footer,assignmentBeanSectionArrayList,stringArray,mStatus);
        listView.setNestedScrollingEnabled(false);

        listView.setAdapter(LoadingIntoWareHouseSectionAdapter);
        listView.addItemDecoration(new TimeLineItemTopBottomDecoration());
        LoadingIntoWareHouseSectionAdapter.setOnItemChildClickListener(this);
        testData();
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        initToolbarNav(toolbar);

        setScrollViewFirst();
        setmSetOnAddItemClickListener(this);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setRemoveDuration(600);
        defaultItemAnimator.setAddDuration(600);
        listView.setItemAnimator(defaultItemAnimator);
    }

    protected void initToolbarListener(){

    }

    /**
     * toolbar 回退
     * @param toolbar
     */
    @Override
    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoadingIntoWareHouseContentActivity)_mActivity).onBackPressedSupport();
                sureDataRefresh("tvFanCheckAction");
            }
        });
    }

    @Override
    public boolean onBackPressedSupport() {
        sureDataRefresh("tvFanCheckAction");
        return super.onBackPressedSupport();
    }

    //对用户可见时回调
    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    //对用户不可见时回调
    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
    }

    private void initStatusData(){

        //得到初始数据，处理单据状态
        if (mStatus.isNewStatus()) {
            setActionBarMidlleTitle("新增" + module_name);
        }
        else {
            setActionBarMidlleTitle(module_name);
        }

        if (!TextUtils.isEmpty(receiptHnumber)) {
            if (getComtaskSize()) {
                for (LoadingIntoWareHouseBean bean : comTaskBeans) {
                    bean.hNumbe = receiptHnumber;
                }
            }
        }else if (ValueUtils.IsFirstValueExist(this.comTaskBeans)){
            receiptHnumber=this.comTaskBeans.get(0).hNumbe;
        }
    }

    /**
     * 初始布局  为列表布局
     * param comTaskBeans
     */
    private void TopListViewInit() {

        initStatusData();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity);

        ArrayList<LoadingIntoWareHouseContentMessageFragment.KeyType> keyTypes = new ArrayList<>();
        LoadingIntoWareHouseContentMessageFragment.KeyType keyType = null;
        for (int i = 0; i < Const.LETTERS.length; i++) {
            keyType = new LoadingIntoWareHouseContentMessageFragment.KeyType();
            keyType.key = Integer.valueOf(Const.key[i]);
            keyType.type = Const.LETTERS[i];
            keyTypes.add(keyType);
        }

        final List<TreeItem> groupItems = new ArrayList<>();

        for (int i = 0; i < Const.LETTERS.length; i++) {
            GroupItem sortGroupItem = new GroupItem();
            sortGroupItem.TitleKey = Const.LETTERS[i];
            sortGroupItem.setmOnChildItemClickListener(this);
            TopViewHolders = makeChlidData(keyTypes.get(i), mStatus);
            sortGroupItem.setData(TopViewHolders);
            groupItems.add(sortGroupItem);
        }

        treeRecyclerAdapter = new TreeRecyclerAdapter();
        topListView.setLayoutManager(linearLayoutManager);
        topListView.setAdapter(treeRecyclerAdapter);
        treeRecyclerAdapter.getItemManager().replaceAllItem(groupItems);
    }

    /*
    请求有数据返回  才去判断是否状态相同
     */
    private void ifStateForOrderId() {
        if (!ValueUtils.IsFirstValueExist(comTaskBeans))
        {
            return;
        }

        //提交  请求有数据 就是保存状态
        if (comTaskBeans.get(0).getStatus().trim().equals(Const.SUBMIT_STATUS)) {
            /**
             * 请求有数据,就是
             */
            //保存状态
            SubmitStatusBeanImpl submitStatusBean = new SubmitStatusBeanImpl();
            submitStatusBean.setVisCheckBtn(true).setVisDeleteBtn(true).setVisModifyBtn(true);
            mStatus.setBean(submitStatusBean);
            mStatus.setLookStatus(true);
            return;
        }
        else if (comTaskBeans.get(0).getStatus().trim().equals(Const.CHECK_STATUS)) {
            //审核状态  养护出库审核状态也可以删除
            CheckStatusBeanImpl checkStatusBean = new CheckStatusBeanImpl();
            checkStatusBean.setVisCheckFBtn(true);
            mStatus.setBean(checkStatusBean);
            mStatus.setLookStatus(true);
            return;
        }
    }


    /**
     * 初始化  childItem 子布局所需的数据
     *
     * @return
     */
    protected ArrayList<SortChildItem.ViewModel> makeChlidData(LoadingIntoWareHouseContentMessageFragment.KeyType data, StatusBean mStatus) {
        ArrayList<SortChildItem.ViewModel> viewModels = new ArrayList<>();
        SortChildItem.ViewModel viewModel=null;
        if (data.key == 4) {

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "单据编号";
            viewModel.value = this.comTaskBeans.get(0).gethNumbe();
            viewModel.key = "hNumbe";
            viewModel.isClick = false;
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key,viewModel.value);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "货柜";
            viewModel.value = this.comTaskBeans.get(0).getContainerName();
            viewModel.key = "containerName";
            viewModel.isClick = false;
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key,viewModel.value);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "业务日期";
            viewModel.value = this.comTaskBeans.get(0).getRequireDate();
            viewModel.key = "requireDate";
            if (mStatus.isNewStatus() || mStatus.isModifyStatus()) {
                viewModel.isClick = true;
            }
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key,viewModel.value);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "仓库";
            viewModel.value = this.comTaskBeans.get(0).getStorageWarehouse();
            viewModel.key = "storageWarehouse";
            viewModel.isQrcodeBtn=true;
            if (mStatus.isNewStatus() || mStatus.isModifyStatus()) {
                viewModel.isClick = true;
            }
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key,viewModel.value);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "描述";
            viewModel.value = this.comTaskBeans.get(0).getDescription();
            viewModel.key = "description";
            if (mStatus.isNewStatus() || mStatus.isModifyStatus()) {
                viewModel.isClick = true;
            }
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key,viewModel.value);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "管理单元";
            viewModel.value = App.Mancompany == null ? "" : App.Mancompany;
            viewModel.key = "Mancompany";
            viewModel.isClick = false;
            viewModels.add(viewModel);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "状态";
            viewModel.value = this.comTaskBeans.get(0).getStatus();
            viewModel.key = "status";
            viewModel.isClick = false;
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key,viewModel.value);
        }
        else if (data.key == 2 && data.type.equals("录入人信息")) {

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "录入人";
            viewModel.value = this.comTaskBeans.get(0).getEntryPeople();
            viewModel.key = "enterPeople";
            viewModel.isClick = false;
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key,viewModel.value);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "录入时间";
            viewModel.value = this.comTaskBeans.get(0).getEntryTime();
            viewModel.key = "entryTime";
            viewModel.isClick = false;
            viewModels.add(viewModel);

        }
        else if (data.key == 2 && data.type.equals("审核人信息")) {

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "审核人";
            viewModel.value = this.comTaskBeans.get(0).getCheckPeople();
            viewModel.key = "checkPeople";
            viewModel.isClick = false;
            viewModels.add(viewModel);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "审核时间";
            viewModel.value = this.comTaskBeans.get(0).getCheckTime();
            viewModel.key = "checkTime";
            viewModel.isClick = false;
            viewModels.add(viewModel);
        }
        return viewModels;
    }


    /**
     * 最小子布局的点击 回调
     * param data
     * param holder
     */
    @Override
    public void onChildItemClick(SortChildItem.ViewModel data, ViewHolder holder) {

        if (ClickUtils.isFastDoubleClick())
        {
            return;
        }

        String type = "";
        if (data.name.equals("仓库")) {
            if (TextUtils.isEmpty(headerProperties.get("containerName")))
            {
                showButtomToast("请先订单拉取");
                return;
            }

            //扫二维码 台车
            Intent intent = new Intent(_mActivity, CaptureActivity.class);
            intent.putExtra("position",holder.getAdapterPosition());
            intent.putExtra("tag", ON_CHILD_ITEM_CLICK);
            startActivityForResult(intent,REQUST_QRCODE);
        }
        else if (data.name.equals("业务日期")) {
            CommonDateTimePickerFragment commonDateTimePickerFragment = new CommonDateTimePickerFragment();
            Bundle bundle = new Bundle();
            bundle.putString(CommonDateTimePickerFragment.PARAM_DATA, data.value);
            bundle.putString(CommonDateTimePickerFragment.PARAM_TYPE, data.name);
            bundle.putInt(CommonDateTimePickerFragment.PARAM_POSITION, holder.getAdapterPosition());
            commonDateTimePickerFragment.setListener(this);
            commonDateTimePickerFragment.setArguments(bundle);
            commonDateTimePickerFragment.setCancelable(true);
            commonDateTimePickerFragment.show(_mActivity.getSupportFragmentManager(), "datePicker");
            _mActivity.getSupportFragmentManager().executePendingTransactions();
        }
        else if (data.name.equals("计划结束日")) {
            CommonDateTimePickerFragment commonDateTimePickerFragment = new CommonDateTimePickerFragment();
            Bundle bundle = new Bundle();
            bundle.putString(CommonDateTimePickerFragment.PARAM_DATA, data.value);
            bundle.putString(CommonDateTimePickerFragment.PARAM_TYPE, data.name);
            bundle.putInt(CommonDateTimePickerFragment.PARAM_POSITION, holder.getAdapterPosition());
            commonDateTimePickerFragment.setListener(this);
            commonDateTimePickerFragment.setArguments(bundle);
            commonDateTimePickerFragment.setCancelable(true);

            commonDateTimePickerFragment.show(_mActivity.getSupportFragmentManager(), "datePicker");
            _mActivity.getSupportFragmentManager().executePendingTransactions();
        }
        else if (data.name.equals("描述")) {
            int adapterPosition = holder.getAdapterPosition();
            String text = data.value;
            ShowDialogWithRefreshDescription(text, adapterPosition);
        }
    }

    private void ShowDialogWithRefreshDescription(String text, int adapterPosition) {
        ShowDeviceMessageCustomDialog.Builder builder = new ShowDeviceMessageCustomDialog.Builder(_mActivity);
        builder.setMessage("您的描述是:");
        builder.setTitle("提示");
        builder.setEdittextcontent(text);

        View inflate = LayoutInflater.from(_mActivity).inflate(R.layout.beizhu_edittext, null, false);
        builder.setContentView(inflate);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                EditText viewEditText = (EditText) inflate.findViewById(R.id.edt_domian);
                if (viewEditText.getText() != null) {
                    String text = viewEditText.getText().toString();
                    if (TextUtils.isEmpty(text)) {
                        T.showfunShort(_mActivity, "备注不能为空");
                    }
                    else {
                        getDateRefresh(text, adapterPosition, "描述");
                    }
                }
            }
        });

        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_comtask,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * 重写 显示QrAdd 显示策略
     * @param text
     * @param invisiable
     */
    @Override
    protected void setTvQrAddContext(String text, boolean invisiable) {
        tvQrcodeAdd.setText(text);
        addInvisiable=invisiable;
        llQrcodeAdd.setVisibility(invisiable?View.VISIBLE:View.GONE);
        if (addInvisiable)
            llQrcodeAdd.setVisibility(mStatus.getBean().visCheckBtn?View.VISIBLE:View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (ClickUtils.isFastDoubleClick()) {
            return super.onOptionsItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.action_operat_status:
                if (ClickUtils.isFastDoubleClick())
                    return false;
                show1(mStatus);
                setTvQrcodeContext("装车订单拉取", View.VISIBLE);
                setTvQrAddContext("货柜调拨", true);

//                Observable<Object> objectObservable = Observable.create(subscriber -> {
//                });
//                Observable observableMobileKey = ApiWebService.Get_KeyTimestr(App.MobileKey);
//                observableMobileKey.concatWith(objectObservable).subscribe(new Subscriber() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Object o) {
//                        App.KeyTimestring = o.toString();
//                    }
//                });

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUST_QRCODE && resultCode == RESULT_OK && data != null) {
            Bundle bundle = data.getBundleExtra("bundle");
            String resultQr = bundle.getString("result");
            int extraPosition = bundle.getInt("position",-2);
            String tag = bundle.getString("tag");
            //
            if (tag.equals(ON_ITEM_CHILD_CLICK)) {
                //分录的出库仓库
                ApiWebService.B_Get_Ware_house(_mActivity, new ApiWebService.SuccessCall() {
                    @Override
                    public void SuccessBack(String result) {

                        ArrayList<wareHouseBean> carNo = App.getAppGson().fromJson(result, new TypeToken<List<wareHouseBean>>() {
                        }.getType());

                        if (!ValueUtils.IsFirstValueExist(carNo)) {
//                            showButtomToast(getResources().getString(R.string.Qrcode_result));

                            comTaskBeans.get(extraPosition).setOutLibID("");
                            comTaskBeans.get(extraPosition).setOutLibWarehouse("");

                            headerProperties.put("outLibID",comTaskBeans.get(0).getOutLibID());

                            ClearAssignMentSectionArrayList();
                            LoadingIntoWareHouseSectionAdapter.notifyItemChanged(extraPosition);

                            CustomToast.showWarning();
                            return;
                        }

                        comTaskBeans.get(extraPosition).setOutLibID(carNo.get(0).getWareHouseID());
                        comTaskBeans.get(extraPosition).setOutLibWarehouse(carNo.get(0).getWareHouseName());

                        headerProperties.put("outLibID",comTaskBeans.get(0).getOutLibID());

                        ClearAssignMentSectionArrayList();
                        LoadingIntoWareHouseSectionAdapter.notifyItemChanged(extraPosition);
                    }

                    @Override
                    public void ErrorBack(String error) {

                    }
                },resultQr);

            }else if (extraPosition==4&&tag.equals(ON_CHILD_ITEM_CLICK))
            {
                //入库仓库
                ApiWebService.B_Get_Ware_house(_mActivity, new ApiWebService.SuccessCall() {
                    @Override
                    public void SuccessBack(String result) {

                        ArrayList<wareHouseBean> carNo = App.getAppGson().fromJson(result, new TypeToken<List<wareHouseBean>>() {
                        }.getType());

                        if (!ValueUtils.IsFirstValueExist(carNo)) {
//                            showButtomToast(getResources().getString(R.string.Qrcode_result));
                            CustomToast.showWarning();
                            getDateRefresh("",extraPosition,"仓库");
                            return;
                        }
                        for (LoadingIntoWareHouseBean bean : comTaskBeans) {
                            bean.setStorageID(carNo.get(0).getWareHouseID());
//                            bean.setStationCarCoding(loadingcarbean.get(0).stationCarCoding);
                            bean.setStorageWarehouse(carNo.get(0).getWareHouseName());
                        }
//                        headerProperties.put("stationCarCoding",loadingcarbean.get(0).stationCarCoding);
                        headerProperties.put("storageID",carNo.get(0).getWareHouseID());
                        headerProperties.put("storageWarehouse",carNo.get(0).getWareHouseName());
                        getDateRefresh(carNo.get(0).getWareHouseName(),extraPosition,"仓库");
                    }

                    @Override
                    public void ErrorBack(String error) {

                    }
                },resultQr);
            }
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == RESULT_SORTITEM_SELECTPROJECT && resultCode == RESULT_OK && data != null)
        {
            String extrasName = data.getString(LoadingIntoWareHousePickerFragment.BUNDLE_RESULT_ARGS_PROJECTNAME);
            int extraPosition = data.getInt(LoadingIntoWareHousePickerFragment.BUNDLE_RESULT_AGES_POSITION, 0);
            String extra = data.getString(LoadingIntoWareHousePickerFragment.BUNDLE_RESULT_AGES_HID); //订单ID
            String extraCoding = data.getString(LoadingIntoWareHousePickerFragment.BUNDLE_RESULT_AGES_CODING);
            String extraBOMID = data.getString(LoadingIntoWareHousePickerFragment.BUNDLE_RESULT_AGES_BOMID);//货柜Id
            String containerName = data.getString(LoadingIntoWareHousePickerFragment.BUNDLE_RESULT_AGES_CONTAINER);//货柜name
//            this.comTaskBeans.get(0).setContainerID(extraBOMID);
//            this.comTaskBeans.get(0).setContainerName(containerName);
            headerProperties.put("containerID",extraBOMID);
            headerProperties.put("containerName",containerName);

            ApiWebService.Get_Production_order_Container_bysoid_Json(_mActivity, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    ArrayList<LoadingIntoWareHouseBean> loadingcarbean = App.getAppGson().fromJson(result, new TypeToken<List<LoadingIntoWareHouseBean>>() {
                    }.getType());

                    if (!ValueUtils.IsFirstValueExist(loadingcarbean)) {
//                        showButtomToast(R.string.Qrcode_result);
                        CustomToast.showWarning();
                        return;
                    }

                    for (LoadingIntoWareHouseBean bean : loadingcarbean) {
                        bean.setContainerID(extraBOMID);
                        bean.setContainerName(containerName);
                        bean.sethNumbe(receiptHnumber);
                    }


                    if (!(loadingcarbean.size() == 0)) {
                        comTaskBeans.clear();
                        comTaskBeans.addAll(loadingcarbean);
                    }

                    getDateRefresh(comTaskBeans.get(0).getContainerName(),2,"货柜");
                    getDateRefresh(comTaskBeans.get(0).getStorageWarehouse(),4,"仓库");
                    ClearAssignMentSectionArrayList();
                    LoadingIntoWareHouseSectionAdapter.notifyDataSetChanged();
                }

                @Override
                public void ErrorBack(String error) {

                }
            },extra);
        }
    }

    public static ArrayList<LoadingIntoWareHouseBean> removeDupliById(List<LoadingIntoWareHouseBean> persons) {
        Set<LoadingIntoWareHouseBean> personSet = new TreeSet<>((o1, o2) -> {
            if (o1.Qrcode.equals(o2.Qrcode))
            {
                return 0;
            }else {
                return 1;
            }
        });
        personSet.addAll(persons);
        return new ArrayList<>(personSet);
    }

    /**
     * 设置 最小子布局为空
     * param position
     */
    private void setChlidItemEmpty(int position) {
        List<TreeItem> datas = treeRecyclerAdapter.getDatas();

        SortChildItem treeItem = (SortChildItem) datas.get(position);
        SortChildItem.ViewModel viewModel = treeItem.getData();
        viewModel.value = "";
        treeItem.setData(viewModel);
    }

    /**
     * 设置 position 的值
     * param position
     */
    private void setChlidItemValue(int position, String name, String value) {
        List<TreeItem> datas = treeRecyclerAdapter.getDatas();

        SortChildItem treeItem = (SortChildItem) datas.get(position);
        SortChildItem.ViewModel viewModel = treeItem.getData();
        if (viewModel.name.equals(name)) {
            viewModel.value = value;
            treeItem.setData(viewModel);
            treeRecyclerAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 日期 click的回调
     * param date
     */

    @Override
    public void onMutiDateSet(String timeStr, String title, int position) {
        getDateRefresh(timeStr, position, title);
    }

    /**
     * 更新data 位置
     *
     * @param date
     * @param position
     * @param typestring
     */
    private void getDateRefresh(String date, int position, String typestring) {
        List<TreeItem> datas = treeRecyclerAdapter.getDatas();

        SortChildItem treeItem = (SortChildItem) datas.get(position); //需求日期
        SortChildItem.ViewModel viewModel = treeItem.getData();
        if (viewModel.name.equals(typestring) && typestring.equals("业务日期")) {
            if (viewModel.value == null || !viewModel.value.equals(date))
                viewModel.value = date;
            for (LoadingIntoWareHouseBean receBean : comTaskBeans) {
                receBean.requireDate=viewModel.value;
            }
        }
        else if (viewModel.name.equals("描述")) {
            if (viewModel.value==null||!viewModel.value.equals(date))
                viewModel.value = date;

            for (int i = 0; i < comTaskBeans.size(); i++) {
                comTaskBeans.get(i).description = viewModel.value;
            }
        }else if (viewModel.name.equals("货柜"))
        {
            if (viewModel.value==null||!viewModel.value.equals(date))
                viewModel.value = date;

            for (int i = 0; i < comTaskBeans.size(); i++) {
                comTaskBeans.get(i).setContainerName(viewModel.value);
            }
        }else if (viewModel.name.equals("仓库"))
        {
            if (viewModel.value==null||!viewModel.value.equals(date))
                viewModel.value = date;

            for (int i = 0; i < comTaskBeans.size(); i++) {
                comTaskBeans.get(i).setStorageWarehouse(viewModel.value);
            }
        }

        headerProperties.put(viewModel.key,viewModel.value);
        treeItem.setData(viewModel);
        treeRecyclerAdapter.notifyDataSetChanged();
    }

    /*
        下部分的 部件点击事件监听，Add按钮  删除按钮
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (ClickUtils.isFastDoubleClick())
            return;
        switch (view.getId()) {
            //每个item删除标准
            case R.id.img_delete_item:
                onImgItemDelete(position);
                break;
            //增加item 按钮
            case R.id.btn_addItem:
                onImgItemAdd(view,position);
                break;
            /**
             * item 加个 常量ID
             */
            case R.id.mianLayout:
//                getStaffData(position);
                break;

            case R.id.ll_top5:
                Intent intent = new Intent(_mActivity, CaptureActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("tag", ON_ITEM_CHILD_CLICK);
                startActivityForResult(intent,REQUST_QRCODE);
                break;
        }
    }

    /**
     * 得到职员信息
     * @param position
     */
    private void getStaffData(int position) {
        ApiWebService.Get_Production_order_Per_Member_Json(_mActivity, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                ArrayList<BProBOM> bProBOMs = App.getAppGson().fromJson(result, new TypeToken<List<BProBOM>>() {
                }.getType());

                CommonPickerActivity_.intent(LoadingIntoWareHouseContentMessageFragment.this).title("职员信息").bProBOMs(bProBOMs).Position(position).LayoutID(R.layout.commonpicker_item).startForResult(RESULT_SORTITEM_SELECTPROJECT);

            }

            @Override
            public void ErrorBack(String error) {

            }
        }, 9999, "");
    }

    /**
     * show1 加个按钮操作
     * @param view
     */
    @Override
    public void onAddItemClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_qrcode_add:
                TvAddQrAction();
                break;

        }
    }

    /**
     * addQr 按钮的处理事件 货柜调拨
     */
    private void TvAddQrAction() {
        ApiWebService.Get_Production_order_Container_Container_Db(_mActivity, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                showButtomToast(result);
            }

            @Override
            public void ErrorBack(String error) {

            }
        },comTaskBeans.get(0).getID());
    }

    public static class KeyType {
        public int key;
        public String type;
    }

    /**
     * 请求主数据
     */
    private void testData() {
        comTaskBeans.clear();
        ApiWebService.Get_Production_order_ContainerView_Json(_mActivity, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                ArrayList<LoadingIntoWareHouseBean> tomTaskBeans = App.getAppGson().fromJson(result, new TypeToken<List<LoadingIntoWareHouseBean>>() {
                }.getType());
                //为空就创建一个新的空对象
                if (tomTaskBeans.isEmpty()) {
                    //为头recycleView 设置空数据
                    comTaskBeans.add(new LoadingIntoWareHouseBean());
                }
                else {
                    comTaskBeans.addAll(tomTaskBeans);
                    /**
                     * 进行判断 mStatus 状态  判断状态是否一致  不一致进行订单状态变更
                     */
                    ifStateForOrderId();
                }
                ClearAssignMentSectionArrayList();
                TopListViewInit();
                LoadingIntoWareHouseSectionAdapter.notifyDataSetChanged();
            }

            @Override
            public void ErrorBack(String error) {
                //设置error字段
                setErrorViewContext(error);
                //设置Empty  ErrorView
                LoadingIntoWareHouseSectionAdapter.setEmptyView(errorView);
            }
        }, HId);
    }

    private void CleartreeRecyclerAdapter(){
        List<TreeItem> datas = treeRecyclerAdapter.getDatas();
        KeyType keyType = new KeyType();

        List<TreeItem> treeItems =new ArrayList<>();
        /**
         * 要修改datas  重新塞入sortChildItem 对象  .因为在这个initChild里才进行了事件监听
         */
        keyType.key = 4;
        treeItems.addAll(GroupItemRefresh(datas, Const.LETTERS[0], makeChlidData(keyType, mStatus)));
        keyType.key = 2;
        keyType.type = Const.LETTERS[1];
        treeItems.addAll(GroupItemRefresh(datas, Const.LETTERS[1], makeChlidData(keyType, mStatus)));
        keyType.key = 2;
        keyType.type = Const.LETTERS[2];
        treeItems.addAll(GroupItemRefresh(datas, Const.LETTERS[2], makeChlidData(keyType, mStatus)));

        for (int i=0,j=0;i<datas.size();i++,j++)
        {
            if (datas.get(i) instanceof SortChildItem)
            {
                datas.set(i,treeItems.get(j)); //重新塞入 treeItems
            }else if (datas.get(i) instanceof GroupItem)
            {
                j--;
            }
        }

        treeRecyclerAdapter.getItemManager().notifyDataChanged();
    }

    /**
     *  得到对应的chilids  SortChildItem ，并重新更新了ArrayList<SortChildItem.ViewModel> data 数据
     * @param datas
     * @param letter
     * @param data
     * @return
     */
    private List<TreeItem> GroupItemRefresh(List<TreeItem> datas, String letter, ArrayList<SortChildItem.ViewModel> data) {
        for (TreeItem item : datas) {
            if (item instanceof GroupItem) {
                GroupItem item1 = (GroupItem) item;
                if (item1.TitleKey.equals(letter)) {
                    ((GroupItem) item).setData(data);
                    return item1.getAllChilds();
                }
            }
        }
        return null;
    }

    /**
     * 清理数据
     * 重新包裹 assignmentBeanSectionArrayList
     */
    private void ClearAssignMentSectionArrayList() {
        assignmentBeanSectionArrayList.clear();
        for (LoadingIntoWareHouseBean bean : comTaskBeans) {
            assignmentBeanSectionArrayList.add(new LoadingIntoWareHouseBeanSection(bean));
        }
        assignmentBeanSectionArrayList.add(new LoadingIntoWareHouseBeanSection(false, "", false, true, ""));
    }



    public void onImgItemDelete(int position) {
        comTaskBeans.remove(position);
        ClearAssignMentSectionArrayList();
        //不requestLayout();
        if (position == comTaskBeans.size()) {
            listView.setHasFixedSize(true);
        }
        LoadingIntoWareHouseSectionAdapter.notifyItemRemoved(position);

        if (position != comTaskBeans.size()) {
            LoadingIntoWareHouseSectionAdapter.notifyItemRangeChanged(position, comTaskBeans.size() - position);
        }
         //等动画运行完毕 再requestLayout();
        listView.postDelayed(new Runnable() {
            @Override
            public void run() {
                listView.setHasFixedSize(false);
                listView.requestLayout();
            }
        },listView.getItemAnimator().getRemoveDuration());

//     //网上说这个方式刷新 position正确
//     mRecyclerViewAdapter.notifyItemRangeChanged(0,comTaskBeans.size());//刷新被删除数据，以及其后面的数据
    }


    //点击增加的按钮
    public void onImgItemAdd(View view, int position) {
        if (comTaskBeans == null)
            return;
        /**
         * bottomView 数据特别多 直接复制上一个
         */
        LoadingIntoWareHouseBean bean;
        bean=comTaskBeans.get(0);

        if (!isVisBottom(listView)) {
            listView.smoothScrollToPosition(comTaskBeans.size() + 1);
        }

        ClearAssignMentSectionArrayList();
        LoadingIntoWareHouseSectionAdapter.notifyItemInserted(comTaskBeans.size());
        LoadingIntoWareHouseSectionAdapter.notifyDataSetChanged();
    }

    private class MyTaskContentAdapter extends ComTaskContentItemSectionItemTouchHelper.ItemTouchAdapterImpl {

        @Override
        public void onItemMove(int fromPosition, int toPosition) {

        }

        @Override
        public void onItemRemove(int position) {

        }
    }

    //单据---修改状态
    @Override
    protected void tvModifyAction(TextView tvModify) {
        mStatus.setBean(new SubmitStatusBeanImpl().setVisSubmitBtn(true));
        mStatus.setLookStatus(true);
        mStatus.setModifyStatus(true);
        if (mStatus.isModifyStatus()) {
            setActionBarMidlleTitle("修改" + module_name);
//            TopListViewInit();
            CleartreeRecyclerAdapter();

            ClearAssignMentSectionArrayList();
            LoadingIntoWareHouseSectionAdapter.notifyDataSetChanged();
            bottomDialog.dismiss();
        }
    }

    //装车订单拉取
    @Override
    protected void tvQrcodeAction(TextView tvQrcode) {
        ApiWebService.Get_Production_order_Container_soid_Json(_mActivity, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {

                ArrayList<LoadingIntoPickBean> bProBOMS= App.getAppGson().fromJson(result, new TypeToken<List<LoadingIntoPickBean>>(){}.getType());
                startForResult(LoadingIntoWareHousePickerFragment.newInstance("装车订单","-1",bProBOMS,"",-1,R.layout.loadinginto_picker_item,true), RESULT_SORTITEM_SELECTPROJECT);

//                CommonPickerActivity_.intent(LoadingIntoWareHouseContentMessageFragment.this).title("装车订单").bProBOMs(bProBOMS).LayoutID(R.layout.loadinginto_picker_item).Position(-1).isNewsAdapter(true).startForResult(RESULT_SORTITEM_SELECTPROJECT);
            }

            @Override
            public void ErrorBack(String error) {

            }
        },9999,"");
    }


    private boolean getComtaskSize() {
        if (this.comTaskBeans == null || this.comTaskBeans.size() <= 0) {
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    protected void tvSubmitActionforList(TextView tvSubmit) {
        if (!getComtaskSize()) {
            T.showShort(_mActivity, Const.Entry_is_empty);
            return;
        }
        if (TextUtils.isEmpty(this.comTaskBeans.get(0).getContainerID())) {
            T.showShort(_mActivity, "货柜为空不能提交");
            return;
        }

        for (int i=0;i<comTaskBeans.size();i++) {
            if (TextUtils.isEmpty(this.comTaskBeans.get(i).getOutLibID())) {
                T.showShort(_mActivity, "第"+(i+1)+"行的出库仓库为空不能提交");
                return;
            }
        }
        int size = this.comTaskBeans.size();
        String[][] billtable = null;
        billtable = new String[size][19];
        for (int i = 0; i < size; i++) {
            LoadingIntoWareHouseBean confirmationReceBean = comTaskBeans.get(i);

            billtable[i][0] = confirmationReceBean.hNumbe;
            billtable[i][1] = confirmationReceBean.requireDate;
            billtable[i][2] = confirmationReceBean.getContainerID();
            billtable[i][3] = confirmationReceBean.getStorageID();
            billtable[i][4] = confirmationReceBean.getDescription();
            billtable[i][5] = App.menname;
            billtable[i][6] = confirmationReceBean.Qrcode;
            billtable[i][7] = confirmationReceBean.materialID;
            billtable[i][8] = confirmationReceBean.materialCoding;
            billtable[i][9] = confirmationReceBean.materialName;
            billtable[i][10] = confirmationReceBean.specification;
            billtable[i][11] = confirmationReceBean.measureUnitID;
            billtable[i][12] = confirmationReceBean.measureUnit;
            billtable[i][13] = confirmationReceBean.number+"";
            billtable[i][14] = confirmationReceBean.getOutLibID();
            billtable[i][15] = confirmationReceBean.sourceID;
            billtable[i][16] = confirmationReceBean.getProjectID();
            billtable[i][17] = confirmationReceBean.getDongID();
            billtable[i][18] = confirmationReceBean.getCengID();
        }
        if (mStatus.isNewStatus()) {

            ApiWebService.Get_Production_order_Container_Add(_mActivity, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    showButtomToast(result);

                    if (!result.contains("失败")) {
                        _mActivity.onBackPressed();
                    }
                }

                @Override
                public void ErrorBack(String error) {

                }
            }, billtable);
        }
        else if (mStatus.isModifyStatus()) {
            ApiWebService.Get_Production_order_Container_Eedit(_mActivity, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    showButtomToast(result);

                    if (!result.contains("失败")) {
                        _mActivity.onBackPressed();
                    }
                }

                @Override
                public void ErrorBack(String error) {

                }
            }, billtable,comTaskBeans.get(0).getID());
        }
    }

//    审核
    @Override
    protected void tvCheckAction(TextView tvCheck) {
        ApiWebService.Get_Production_order_Container_sh(_mActivity, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                showButtomToast(result);
                //就是在原界面刷新
                testData();
//                sureDataRefresh("tvCheckAction");
            }

            @Override
            public void ErrorBack(String error) {

            }
        },comTaskBeans.get(0).getID());
    }

    //反审核
    @Override
    protected void tvFanCheckAction(TextView tvFanCheck) {
        ApiWebService.Get_Production_order_Container_shf(_mActivity, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                showButtomToast(result);
                //就是在原界面刷新
                testData();
//                sureDataRefresh("tvFanCheckAction");
            }

            @Override
            public void ErrorBack(String error) {

            }
        },comTaskBeans.get(0).getID());
    }

    @Override
    protected void tvDeleteAction(TextView tvDelete) {
        ApiWebService.Get_Production_order_Container_Del(_mActivity, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(_mActivity, result);
                _mActivity.onBackPressed();
            }

            @Override
            public void ErrorBack(String error) {

            }
        }, comTaskBeans.get(0).getID());
    }

    protected void sureDataRefresh(String type) {
        EventBus.getDefault().post("刷新" + type + "数据");
    }


    public static class DataBean {
        public String wuLiao;
        public String countUnit;
        public String count;
    }

}


