package com.system.bhouse.bhouse.CommonTask.MaintainManagement.MaintainOutWareHouse;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.system.bhouse.Custom.ShowDeviceMessageCustomDialog;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.base.CheckStatusBeanImpl;
import com.system.bhouse.base.StatusBean;
import com.system.bhouse.base.SubmitStatusBeanImpl;
import com.system.bhouse.bean.BProBOM;
import com.system.bhouse.bhouse.CommonTask.BaseTaskFragment.BaseContentMessageActivity;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.MaintenanceOutWarehouse.Bean.MaintenanceOutWarehouseBean;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.MaintenanceOutWarehouse.Bean.MaintenanceOutWarehouseBeanSection;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.MaintenanceOutWarehouse.MaintenanceOutWarehouseSectionAdapter;
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
import com.system.bhouse.utils.TenUtils.L;
import com.system.bhouse.utils.TenUtils.T;
import com.system.bhouse.utils.ValueUtils;
import com.zijunlin.Zxing.Demo.CaptureActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2018-03-05.
 * <p>
 *     养护出库界面
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask
 */
@EActivity(R.layout.activity_comtask_content_layout)
@OptionsMenu(R.menu.menu_comtask)
public class MaintainOutWareHouseContentMessageActivity extends BaseContentMessageActivity implements  GroupItem.onChildItemClickListener, onMutiDataSetListener ,BaseQuickAdapter.OnItemChildClickListener,BaseContentMessageActivity.SetOnAddItemClickListener {

    public static final String TAG = "comtaskcontentmessageactivity";
    private static final String module_name = "养护出库";


    @ViewById
    RecyclerView listView;
    @ViewById
    RecyclerView topListView;
    @ViewById
    TextView tv_title_live_layout;

    @Extra
    String HId;

    @Extra
    String receiptHnumber;

    @Extra
    StatusBean mStatus;

    @Extra
    String componentQr;

    @Extra
    String orderId;

    @Extra
    String workOrderID;

    private ArrayList<MaintenanceOutWarehouseBean> comTaskBeans = new ArrayList<>();
    private ArrayList<MaintenanceOutWarehouseBeanSection> assignmentBeanSectionArrayList=new ArrayList<>() ;
    private TreeRecyclerAdapter treeRecyclerAdapter;
    private boolean isDeleteAble = true;
    public static final int RESULT_SORTITEM_SELECTPROJECT = 1001;
    public static final int REQUST_QRCODE = 1008;

    private String STATE_COMTASK = "state_comtask";
    private HashMap<String,String> headerProperties=new HashMap<>();
    private MaintenanceOutWarehouseSectionAdapter maintenanceOutWarehouseSectionAdapter;
    private ArrayList<SortChildItem.ViewModel> TopViewHolders;

    @AfterViews
    public void initComTaskActivity() {
        tv_title_live_layout.setText(module_name + "分录");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(linearLayoutManager);
        recycleViewAddEmptySection(listView);
        String[] stringArray = getResources().getStringArray(R.array.maintenance_itemsection_order);

        maintenanceOutWarehouseSectionAdapter = new MaintenanceOutWarehouseSectionAdapter(R.layout.activity_comtask_content_layout_item, R.layout.layout_home_recommend_empty_noheight, R.layout.comtask_content_item_footer,assignmentBeanSectionArrayList,stringArray,mStatus);

        listView.setNestedScrollingEnabled(false);

        listView.setAdapter(maintenanceOutWarehouseSectionAdapter);
        listView.addItemDecoration(new TimeLineItemTopBottomDecoration());
        maintenanceOutWarehouseSectionAdapter.setOnItemChildClickListener(this);

        testData();

        setScrollViewFirst();
        setmSetOnAddItemClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 检查是否正在重新创建一个以前销毁的实例
        if (savedInstanceState != null) {
            // 从已保存状态恢复成员的值
//            this.comTaskBeans = savedInstanceState.getParcelableArrayList(STATE_COMTASK);
        }
        else {
            // 可能初始化一个新实例的默认值的成员
        }
    }

    private void initStatusData(){

        //得到初始数据，处理单据状态
        if (mStatus.isNewStatus()) {
            setActionBarMidlleTitle("新增" + module_name);
//            BottomAction();
        }
        else {
            setActionBarMidlleTitle(module_name);
        }

        if (!TextUtils.isEmpty(receiptHnumber)) {
            if (getComtaskSize()) {
                for (MaintenanceOutWarehouseBean bean : comTaskBeans) {
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        ArrayList<MaintainOutWareHouseContentMessageActivity.KeyType> keyTypes = new ArrayList<>();
        MaintainOutWareHouseContentMessageActivity.KeyType keyType = null;
        for (int i = 0; i < Const.LETTERS.length; i++) {
            keyType = new MaintainOutWareHouseContentMessageActivity.KeyType();
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
        if (comTaskBeans.get(0).getStatus().trim().equals(Const.SAVE_STATUS)) {
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
            checkStatusBean.setVisCheckFBtn(true).setVisDeleteBtn(true);
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
    protected ArrayList<SortChildItem.ViewModel> makeChlidData(MaintainOutWareHouseContentMessageActivity.KeyType data, StatusBean mStatus) {
        ArrayList<SortChildItem.ViewModel> viewModels = new ArrayList<>();
        SortChildItem.ViewModel viewModel=null;
        if (data.key == 4) {

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "单据编号";
            viewModel.value = this.comTaskBeans.get(0).gethNumbe();
            viewModel.key = "receiptHnumber";
            viewModel.isClick = false;
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key,viewModel.value);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "台车";
            viewModel.value = this.comTaskBeans.get(0).getStationCarName();
            viewModel.key = "stationCarName";
            if (mStatus.isNewStatus() || mStatus.isModifyStatus()) {
                viewModel.isClick = true;
            }
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key,viewModel.value);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "养护窑库";
            viewModel.value = this.comTaskBeans.get(0).getWareHouse();
            viewModel.key = "wareHouse";
            if (mStatus.isNewStatus() || mStatus.isModifyStatus()) {
                viewModel.isClick = true;
            }
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key,viewModel.value);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "产线";
            viewModel.value = this.comTaskBeans.get(0).getProductionLineName();
            viewModel.key = "productionLineName";
            viewModel.isQrcodeBtn=true;
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


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        outState.putParcelableArrayList(STATE_COMTASK, this.comTaskBeans);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    /**
     * 最小子布局的点击 回调
     * param data
     * param holder
     */
    @Override
    public void onChildItemClick(SortChildItem.ViewModel data, ViewHolder holder) {
        String type = "";
        if (data.name.equals("台车")) {
            ApiWebService.Get_Production_order_yhy_Out_Tid_Json(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {

                    ArrayList<BProBOM> bProBOMS= App.getAppGson().fromJson(result, new TypeToken<List<BProBOM>>(){}.getType());
                    CommonPickerActivity_.intent(MaintainOutWareHouseContentMessageActivity.this).title("台车").bProBOMs(bProBOMS).LayoutID(R.layout.commonpicker_item).Position(holder.getAdapterPosition()).startForResult(RESULT_SORTITEM_SELECTPROJECT);
                }

                @Override
                public void ErrorBack(String error) {

                }
            },"9999","");
        }else if (data.name.equals("产线"))
        {
            //扫二维码
            Intent intent = new Intent(this, CaptureActivity.class);
            intent.putExtra("position",holder.getAdapterPosition());
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
            commonDateTimePickerFragment.show(getSupportFragmentManager(), "datePicker");
            getSupportFragmentManager().executePendingTransactions();
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
            commonDateTimePickerFragment.show(getSupportFragmentManager(), "datePicker");
            getSupportFragmentManager().executePendingTransactions();
        }
        else if (data.name.equals("描述")) {
            int adapterPosition = holder.getAdapterPosition();
            String text = data.value;
            ShowDialogWithRefreshDescription(text, adapterPosition);
        }
    }

    private void ShowDialogWithRefreshDescription(String text, int adapterPosition) {
        ShowDeviceMessageCustomDialog.Builder builder = new ShowDeviceMessageCustomDialog.Builder(this);
        builder.setMessage("您的描述是:");
        builder.setTitle("提示");
        builder.setEdittextcontent(text);

        View inflate = LayoutInflater.from(this).inflate(R.layout.beizhu_edittext, null, false);
        builder.setContentView(inflate);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                EditText viewEditText = (EditText) inflate.findViewById(R.id.edt_domian);
                if (viewEditText.getText() != null) {
                    String text = viewEditText.getText().toString();
                    if (TextUtils.isEmpty(text)) {
                        T.showfunShort(MaintainOutWareHouseContentMessageActivity.this, "备注不能为空");
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

    //扫描回调 带出信息
    @OnActivityResult(REQUST_QRCODE)
    void resultGetRequstQrcode(int result, Intent data) {
        if (result == RESULT_OK) {
            Bundle bundle = data.getBundleExtra("bundle");
            String resultQr = bundle.getString("result");
            int extraPosition = bundle.getInt("position");
            if (extraPosition==-1) {
                ApiWebService.Get_Production_order_Mould_bypoid_Json(this, new ApiWebService.SuccessCall() {
                    @Override
                    public void SuccessBack(String result) {

                        ArrayList<MaintenanceOutWarehouseBean> loadingcarbean = App.getAppGson().fromJson(result, new TypeToken<List<MaintenanceOutWarehouseBean>>() {
                        }.getType());

                        if (loadingcarbean.isEmpty())
                        {
                            T.showShort(MaintainOutWareHouseContentMessageActivity.this,getResources().getString(R.string.Qrcode_result));
                        }

                        for (MaintenanceOutWarehouseBean bean : loadingcarbean) {
                            bean.hNumbe= headerProperties.get("receiptHnumber");
                            bean.requireDate= headerProperties.get("requireDate");
                            bean.description=headerProperties.get("description");
                            bean.entryPeople= headerProperties.get("enterPeople");
                        }

                        //清空 二维码为空的
                        for (MaintenanceOutWarehouseBean receBean : comTaskBeans) {
                            if (TextUtils.isEmpty(receBean.Qrcode)) {
                                comTaskBeans.remove(receBean);
                            }
                        }
                        if (!(loadingcarbean.size() == 0)) {
                            comTaskBeans.addAll(loadingcarbean);
                        }
                        ArrayList<MaintenanceOutWarehouseBean> clone =(ArrayList<MaintenanceOutWarehouseBean>)comTaskBeans.clone();
                        comTaskBeans.clear();
                        comTaskBeans.addAll(removeDupliById(clone));
                        ClearAssignMentSectionArrayList();
                        maintenanceOutWarehouseSectionAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void ErrorBack(String error) {

                    }
                },HId,resultQr);
            }else if (extraPosition==4)
            {
                ApiWebService.B_Get_Ware_house(this, new ApiWebService.SuccessCall() {
                    @Override
                    public void SuccessBack(String result) {
                        ArrayList<MaintenanceOutWarehouseBean> loadingcarbean = App.getAppGson().fromJson(result, new TypeToken<List<MaintenanceOutWarehouseBean>>() {
                        }.getType());
                        //[{"仓库ID":"d0c526b054e84f6598710eee704d8234","仓库编码":"WH_B-7-0005","仓库名称":"PC一线库位"}]
                        //d0c526b054e84f6598710eee704d8234 现提交的ID

                        if (!ValueUtils.IsFirstValueExist(loadingcarbean))
                            return;
                        for (MaintenanceOutWarehouseBean bean:comTaskBeans) {
                            bean.setProductionLineID(loadingcarbean.get(0).wareHouseID);
                            bean.setProductionLineName(loadingcarbean.get(0).wareHouse);
//                            bean.setWareHouseID(loadingcarbean.get(0).wareHouseID);
//                            bean.setWareHouse(loadingcarbean.get(0).wareHouse);
                        }

                        getDateRefresh(loadingcarbean.get(0).wareHouse,extraPosition,"产线");
                    }

                    @Override
                    public void ErrorBack(String error) {

                    }
                },resultQr);
            }
        }
    }

    /**
     * pickActivity 结束回调  台车
     * param result
     * param data
     */
    @OnActivityResult(RESULT_SORTITEM_SELECTPROJECT)
    void resultProjcetName(int result, Intent data) {
        if (result == RESULT_OK) {
            String extrasName = data.getStringExtra("projectname");
            int extraPosition = data.getIntExtra("position", 0);
            String extra = data.getStringExtra("HId");
            String extraCoding = data.getStringExtra("coding");
            String extraBOMID = data.getStringExtra("BOMID");

            getDateRefresh(extrasName,extraPosition,"台车");

            if (!TextUtils.isEmpty(extrasName))
            {
                if (ValueUtils.IsFirstValueExist(comTaskBeans))
                {
                    for (MaintenanceOutWarehouseBean bean : comTaskBeans)
                    {
                        bean.stationCarName=extrasName;
                        bean.stationCarID=extra;
                        bean.stationCarCoding=extraCoding;
                    }
                }
                headerProperties.put("stationCarCoding",extraCoding);
                ApiWebService.Get_Production_order_yhy_Out_byTid_Json(this, new ApiWebService.SuccessCall() {
                    @Override
                    public void SuccessBack(String result) {

                        ArrayList<MaintenanceOutWarehouseBean> loadingcarbean = App.getAppGson().fromJson(result, new TypeToken<List<MaintenanceOutWarehouseBean>>() {
                        }.getType());

                        if (loadingcarbean.isEmpty())
                        {
                            showButtomToast(getResources().getString(R.string.Qrcode_result));
                        }

                        for (MaintenanceOutWarehouseBean bean : loadingcarbean) {
                            bean.hNumbe= headerProperties.get("receiptHnumber");
                            bean.entryPeople= headerProperties.get("enterPeople");
                            bean.stationCarCoding= headerProperties.get("stationCarCoding");
                        }

                        comTaskBeans.clear();
                        if (!(loadingcarbean.size() == 0)) {
                            comTaskBeans.addAll(loadingcarbean);
                        }

                        CleartreeRecyclerAdapter();

                        ClearAssignMentSectionArrayList();
                        maintenanceOutWarehouseSectionAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void ErrorBack(String error) {

                    }
                },comTaskBeans.get(0).stationCarID);
            }
        }
    }

    public static ArrayList<MaintenanceOutWarehouseBean> removeDupliById(List<MaintenanceOutWarehouseBean> persons) {
        Set<MaintenanceOutWarehouseBean> personSet = new TreeSet<>((o1, o2) -> {
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
            for (MaintenanceOutWarehouseBean receBean : comTaskBeans) {
                receBean.requireDate=viewModel.value;
            }
        }
        else if (viewModel.name.equals("描述")) {
            if (viewModel.value==null||!viewModel.value.equals(date))
                viewModel.value = date;

            for (int i = 0; i < comTaskBeans.size(); i++) {
                comTaskBeans.get(i).description = viewModel.value;
            }
        }else if (viewModel.name.equals("台车"))
        {
            if (viewModel.value==null||!viewModel.value.equals(date))
                viewModel.value = date;

            for (int i = 0; i < comTaskBeans.size(); i++) {
                comTaskBeans.get(i).stationCarName = viewModel.value;
            }
        }else if (viewModel.name.equals("产线"))
        {
            if (viewModel.value==null||!viewModel.value.equals(date))
                viewModel.value = date;

            for (int i = 0; i < comTaskBeans.size(); i++) {
                comTaskBeans.get(i).productionLineName = viewModel.value;
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
        }
    }

    /**
     * 得到职员信息
     * @param position
     */
    private void getStaffData(int position) {
        ApiWebService.Get_Production_order_Per_Member_Json(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                ArrayList<BProBOM> bProBOMs = App.getAppGson().fromJson(result, new TypeToken<List<BProBOM>>() {
                }.getType());

                CommonPickerActivity_.intent(MaintainOutWareHouseContentMessageActivity.this).title("职员信息").bProBOMs(bProBOMs).Position(position).LayoutID(R.layout.commonpicker_item).startForResult(RESULT_SORTITEM_SELECTPROJECT);

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
     * addQr 按钮的 处理事件 模具调拨
     */
    private void TvAddQrAction() {

        ApiWebService.Get_Production_order_yhy_Out_Mould_Db(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                showButtomToast(result);
            }

            @Override
            public void ErrorBack(String error) {

            }
        },comTaskBeans.get(0).stationCarID,comTaskBeans.get(0).wareHouseID);

//        ApiWebService.Get_Production_order_Tray_Is_Moid_qr_Tray_Db(this, new ApiWebService.SuccessCall() {
//            @Override
//            public void SuccessBack(String result) {
//
//                ArrayList<StationCarDetachModuleBean> loadingcarbean = App.getAppGson().fromJson(result, new TypeToken<List<StationCarDetachModuleBean>>() {}.getType());
//
//                if (!ValueUtils.IsFirstValueExist(loadingcarbean))
//                    return;
//
//                comTaskBeans.get(0).setProductionLineID(loadingcarbean.get(0).productionLineID);
//                comTaskBeans.get(0).setProductionLineName(loadingcarbean.get(0).productionLineName);
//
//                ClearAssignMentSectionArrayList();
//                maintenanceOutWarehouseSectionAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void ErrorBack(String error) {
//
//            }
//        }, orderId, componentQr, comTaskBeans.get(0).ID);
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
        ApiWebService.Get_Production_order_yhy_OutView_Json(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                ArrayList<MaintenanceOutWarehouseBean> tomTaskBeans = App.getAppGson().fromJson(result, new TypeToken<List<MaintenanceOutWarehouseBean>>() {
                }.getType());
                //为空就创建一个新的空对象
                if (tomTaskBeans.isEmpty()) {
                    //为头recycleView 设置空数据
                    comTaskBeans.add(new MaintenanceOutWarehouseBean());
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
                maintenanceOutWarehouseSectionAdapter.notifyDataSetChanged();
            }

            @Override
            public void ErrorBack(String error) {
                //设置error字段
                setErrorViewContext(error);
                //设置Empty  ErrorView
                maintenanceOutWarehouseSectionAdapter.setEmptyView(errorView);
            }
        }, HId);
    }

    private void CleartreeRecyclerAdapter(){
        List<TreeItem> datas = treeRecyclerAdapter.getDatas();
        KeyType keyType = new KeyType();

        ArrayList<SortChildItem.ViewModel> viewModels = new ArrayList<>();
        keyType.key = 4;
        viewModels.addAll(makeChlidData(keyType, mStatus));
        keyType.key = 2;
        keyType.type = Const.LETTERS[1];
        viewModels.addAll(makeChlidData(keyType, mStatus));
        keyType.key = 2;
        keyType.type = Const.LETTERS[2];
        viewModels.addAll(makeChlidData(keyType, mStatus));

        for (int i=0,j=0;i<datas.size();i++,j++)
        {
            if (datas.get(i) instanceof SortChildItem)
            {

                ((SortChildItem) datas.get(i)).setData(viewModels.get(j));

            }else if (datas.get(i) instanceof GroupItem)
            {
                j--;
            }
        }

//        treeRecyclerAdapter.getItemManager().addItems(datas);
        treeRecyclerAdapter.getItemManager().notifyDataChanged();
    }

    /**
     * 清理数据
     * 重新包裹 assignmentBeanSectionArrayList
     */
    private void ClearAssignMentSectionArrayList() {
        assignmentBeanSectionArrayList.clear();
        for (MaintenanceOutWarehouseBean bean : comTaskBeans) {
            assignmentBeanSectionArrayList.add(new MaintenanceOutWarehouseBeanSection(bean));
        }
        assignmentBeanSectionArrayList.add(new MaintenanceOutWarehouseBeanSection(false, "", false, true, ""));
    }


    private int ikey = 0;

    public void onImgItemDelete(int position) {

        if (isDeleteAble) {//此时为增加动画效果，刷新部分数据源，防止删除错乱
            isDeleteAble = false;//初始值为true,当点击删除按钮以后，休息0.5秒钟再让他为
            //true,起到让数据源刷新完成的作用
            // 删除数据
            ikey++;
            L.e("点击了一次" + ikey);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(600);//休息
                        isDeleteAble = true;//可点击按钮
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            // adapter 刷新
            if (position == comTaskBeans.size())
                return;
            if (!TextUtils.isEmpty(comTaskBeans.get(0).status) && comTaskBeans.get(0).status.equals("审核")) {
                T.showShort(this, "该单据正在审核状态，不能删除");
                return;
            }
            comTaskBeans.remove(position);
            ClearAssignMentSectionArrayList();
            maintenanceOutWarehouseSectionAdapter.notifyItemRemoved(position-1);
            maintenanceOutWarehouseSectionAdapter.notifyDataSetChanged();
//            //网上说这个方式刷新 position正确
//            mRecyclerViewAdapter.notifyItemRangeChanged(0,comTaskBeans.size());//刷新被删除数据，以及其后面的数据
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                }
            }, 200);

        }
    }


    //点击增加的按钮
    public void onImgItemAdd(View view, int position) {
        if (comTaskBeans == null)
            return;
        /**
         * bottomView 数据特别多 直接复制上一个
         */
        MaintenanceOutWarehouseBean bean;
        bean=comTaskBeans.get(0);

//        comTaskBeans.add(bean1);

        if (!isVisBottom(listView)) {
            listView.smoothScrollToPosition(comTaskBeans.size() + 1);
        }

        ClearAssignMentSectionArrayList();
        maintenanceOutWarehouseSectionAdapter.notifyItemInserted(comTaskBeans.size());
        maintenanceOutWarehouseSectionAdapter.notifyDataSetChanged();
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
        mStatus.setBean(new SubmitStatusBeanImpl().setVisSubmitBtn(true).setVisQRBtn(true));
        mStatus.setLookStatus(true);
        mStatus.setModifyStatus(true);
        if (mStatus.isModifyStatus()) {
            setActionBarMidlleTitle("修改" + module_name);
//            TopListViewInit();
            CleartreeRecyclerAdapter();

            ClearAssignMentSectionArrayList();
            maintenanceOutWarehouseSectionAdapter.notifyDataSetChanged();
            bottomDialog.dismiss();
        }
    }

    //台车调拨
    @Override
    protected void tvQrcodeAction(TextView tvQrcode) {

        if (!getComtaskSize()) {
            T.showShort(this, Const.Entry_is_empty);
            return;
        }

        ApiWebService.Get_Production_order_yhy_Out_Trolley_Db(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                showButtomToast(result);
            }

            @Override
            public void ErrorBack(String error) {

            }
        }, comTaskBeans.get(0).stationCarID, comTaskBeans.get(0).stationCarCoding, comTaskBeans.get(0).stationCarName, comTaskBeans.get(0).productionLineID);

//        ApiWebService.Get_Production_order_Tray_Is_Moid_qr_Add(this, new ApiWebService.SuccessCall() {
//            @Override
//            public void SuccessBack(String result) {
//
////                [{"台车ID":"e5ddba4fada141539f87516c51b3e012","台车名称":"9号台车"}]
//
//                showButtomToast(result);
//                if (result.contains("成功"))
//                {
//                    onBackPressed();
//                }
//            }
//
//            @Override
//            public void ErrorBack(String error) {
//
//            }
//        }, comTaskBeans.get(0).ID, comTaskBeans.get(0).getProductionLineID(),orderId, componentQr, comTaskBeans.get(0).getFeedingTime());

//        Intent intent = new Intent(ModuleAssignMentContentMessageActivity.this, CaptureActivity.class);
//        intent.putExtra("position", -1);
//        startActivityForResult(intent, REQUST_QRCODE);
    }

    /**
     * 访问下面recycleView数据 根据生产订单和二维码获取托盘配料单信息
     */
    public void BottomAction(){


        ApiWebService.Get_Production_order_Tray_Is_Moid_qr_byTray_Json(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                /**
                 "单据ID": "7efaf36e001d414b94326c0a04d47e29",
                 "单据编号": "TPFP-7-201808-0005",
                 "托盘ID": "0aea156d7a494628a2f8759f1b047bf5",
                 "托盘名称": "托盘3",
                 "托盘编号": "TP-7-201807-0003"
                 */

                ArrayList<MaintenanceOutWarehouseBean> loadingcarbean = App.getAppGson().fromJson(result, new TypeToken<List<MaintenanceOutWarehouseBean>>() {}.getType());

                if (loadingcarbean.isEmpty()) {
                    T.showShort(MaintainOutWareHouseContentMessageActivity.this, getResources().getString(R.string.Qrcode_result));
                }

                //清空 二维码为空的
                for (MaintenanceOutWarehouseBean receBean : comTaskBeans) {
                    if (TextUtils.isEmpty(receBean.ID)) {
                        comTaskBeans.remove(receBean);
                    }
                }
                if (!(loadingcarbean.size() == 0)) {
                    comTaskBeans.addAll(loadingcarbean);
                }

                getProductionOrderTray();
            }

            @Override
            public void ErrorBack(String error) {

            }
        }, orderId, componentQr,"9999","");

        //"7efaf36e001d414b94326c0a04d47e29"
    }

    private void getProductionOrderTray() {
        ApiWebService.Get_Production_order_Tray_Is_Moid_qr_byRow_Json(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {

                ArrayList<MaintenanceOutWarehouseBean> loadingcarbean = App.getAppGson().fromJson(result, new TypeToken<List<MaintenanceOutWarehouseBean>>() {
                }.getType());

                if (loadingcarbean.isEmpty()) {
                    T.showShort(MaintainOutWareHouseContentMessageActivity.this, getResources().getString(R.string.Qrcode_result));
                }

                //清空 二维码为空的
                for (MaintenanceOutWarehouseBean receBean : comTaskBeans) {
                    if (TextUtils.isEmpty(receBean.ID)) {
                        comTaskBeans.remove(receBean);
                    }
                }
                comTaskBeans.clear();
                if (!(loadingcarbean.size() == 0)) {
                    comTaskBeans.addAll(loadingcarbean);
                }

                CleartreeRecyclerAdapter();

                ClearAssignMentSectionArrayList();
                maintenanceOutWarehouseSectionAdapter.notifyDataSetChanged();

            }

            @Override
            public void ErrorBack(String error) {

            }
        }, orderId, componentQr, comTaskBeans.get(0).getID());
    }

    @Override
    protected void onPause() {
        super.onPause();
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
            T.showShort(this, Const.Entry_is_empty);
            return;
        }
//        for (int i=0;i<comTaskBeans.size();i++) {
//            if (TextUtils.isEmpty(this.comTaskBeans.get(i).getModuleID())) {
//                T.showShort(this, "第"+(i+1)+"行的模具为空不能提交");
//                return;
//            }
//        }
        int size = this.comTaskBeans.size();
        String[][] billtable = null;
        billtable = new String[size][22];
        for (int i = 0; i < size; i++) {
            MaintenanceOutWarehouseBean confirmationReceBean = comTaskBeans.get(i);

            billtable[i][0] = confirmationReceBean.hNumbe;
            billtable[i][1] = confirmationReceBean.maintainIntoID;
            billtable[i][2] = confirmationReceBean.stationCarID;
            billtable[i][3] = confirmationReceBean.wareHouseID;
            billtable[i][4] = App.menname;
            billtable[i][5] = confirmationReceBean.orderId;
            billtable[i][6] = confirmationReceBean.moduleID;
            billtable[i][7] = confirmationReceBean.maintenanceStartTime;
            billtable[i][8] = confirmationReceBean.maintenanceEndTime;
            billtable[i][9] = confirmationReceBean.stationAssigmentSublistID;
            billtable[i][10] = confirmationReceBean.materialID;
            billtable[i][11] = confirmationReceBean.Qrcode;
            billtable[i][12] = confirmationReceBean.materialCoding;
            billtable[i][13] = confirmationReceBean.materialName;
            billtable[i][14] = confirmationReceBean.specification;
            billtable[i][15] = confirmationReceBean.measureUnitID;
            billtable[i][16] = confirmationReceBean.measureUnit;
            billtable[i][17] = confirmationReceBean.number+"";
            billtable[i][18] = confirmationReceBean.sourceID;
            billtable[i][19] = confirmationReceBean.productionLineID;
            billtable[i][20] = confirmationReceBean.stationCarCoding;
            billtable[i][21] = confirmationReceBean.moduleCoding;
        }
        if (mStatus.isNewStatus()) {

            ApiWebService.Get_Production_order_yhy_Out_Add(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    showButtomToast(result);
                    if (!result.contains("失败")) {
//                        onBackPressed();
//                        sureDataRefresh("tvSubmitAction");
                    }
                }

                @Override
                public void ErrorBack(String error) {

                }
            }, billtable);
        }
        else if (mStatus.isModifyStatus()) {
        }
    }

    @Override
    protected void tvCheckAction(TextView tvCheck) {

        ApiWebService.Get_Production_order_Trolley_sh(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(MaintainOutWareHouseContentMessageActivity.this, result);
//                onBackPressed();
//                sureDataRefresh("tvCheckAction");
                testData();
            }

            @Override
            public void ErrorBack(String error) {

            }
        }, comTaskBeans.get(0).ID);
    }

    @Override
    protected void tvFanCheckAction(TextView tvFanCheck) {
        ApiWebService.Get_Production_order_Trolley_shf(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(MaintainOutWareHouseContentMessageActivity.this, result);
//                onBackPressed();
//                sureDataRefresh("tvFanCheckAction");
                testData();
            }

            @Override
            public void ErrorBack(String error) {

            }
        }, comTaskBeans.get(0).ID);
    }

    @Override
    protected void tvDeleteAction(TextView tvDelete) {
        ApiWebService.Get_Production_order_yhy_Out_Del(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(MaintainOutWareHouseContentMessageActivity.this, result);
                onBackPressed();
//                sureDataRefresh("tvDeleteAction");
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

    @Override
    protected void ShowPolicy(StatusBean mStatusBean, LinearLayout llModify, LinearLayout llSubmit, LinearLayout llCheck, LinearLayout llFanCheck, LinearLayout llQrcode, TextView tvDelete) {

        llCheck.setVisibility(View.GONE);
        llModify.setVisibility(View.GONE);
        llFanCheck.setVisibility(View.GONE);
        tvDelete.setVisibility(mStatusBean.getBean().visDeleteBtn?View.VISIBLE:View.GONE);
        llQrcode.setVisibility(mStatusBean.getBean().visQRBtn?View.VISIBLE:View.GONE);
        llSubmit.setVisibility(mStatusBean.getBean().visSubmitBtn?View.VISIBLE:View.GONE);
    }

    @OptionsItem
    protected final void action_operat_status() {
        Observable<Object> objectObservable = Observable.create(subscriber -> {
            if (!ClickUtils.isFastDoubleClick()) {
                show1(mStatus);
            }
            setTvQrcodeContext("台车调拨",View.VISIBLE);
            setTvQrAddContext("模具调拨",true);
        });
        Observable observableMobileKey = ApiWebService.Get_KeyTimestr(App.MobileKey);
        observableMobileKey.concatWith(objectObservable).subscribe(new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                App.KeyTimestring = o.toString();
            }
        });
    }

}


