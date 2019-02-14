package com.system.bhouse.bhouse.CommonTask.ProduceManagement.ProductionOrder.DetailProductionOrder;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.base.StatusBean;
import com.system.bhouse.base.SubmitStatusBeanImpl;
import com.system.bhouse.bhouse.CommonTask.ProduceManagement.entity.productionOrderBean;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.TreeRecyclerAdapter;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.base.ViewHolder;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.item.GroupItem;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.item.SortChildItem;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.item.TreeItem;
import com.system.bhouse.bhouse.CommonTask.common.CommonDateTimePickerFragment;
import com.system.bhouse.bhouse.CommonTask.utils.ComTaskContentItemSectionItemTouchHelper;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;
import com.system.bhouse.bhouse.setup.utils.onMutiDataSetListener;
import com.system.bhouse.utils.TenUtils.L;
import com.system.bhouse.utils.TenUtils.T;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2018-06-22.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.ProduceManagement.ProductionOrder
 */

public class ProductionOrderViewActivity extends WWBackActivity implements ProductionOrderViewItemSection.OnItemClickListener, GroupItem.onChildItemClickListener, onMutiDataSetListener {

    public static final String TAG = "comtaskcontentmessageactivity";

    @BindView(R.id.listView)
    RecyclerView listView;
    @BindView(R.id.topListView)
    RecyclerView topListView;
    @BindView(R.id.tv_title_live_layout)
    TextView tv_title_live_layout;

    String HId;

    String receiptHnumber;

    StatusBean mStatus;

    private ProductionOrderViewActivity.MyTaskContentAdapter mRecyclerViewAdapter;
    private ArrayList<productionOrderBean> comTaskBeans = new ArrayList<>();
    private TreeRecyclerAdapter treeRecyclerAdapter;
    private boolean isDeleteAble = true;
    public static final int RESULT_SORTITEM_SELECTPROJECT = 1001;
    public static final int REQUST_QRCODE = 1008;

    private ProductionOrderViewItemSection workflowSection;
    private Dialog bottomDialog;
    private String STATE_COMTASK = "state_comtask";
    private HashMap<String, String> headerProperties = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comtask_content_layout);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent!=null)
        {
            HId=intent.getStringExtra("result");
            intent.getStringExtra("position");
            Bundle extras = intent.getExtras();
            mStatus=(StatusBean)extras.getSerializable("data");
        }

        initComTaskActivity();
        annotationDispayHomeAsUp();
        topListView.setVisibility(View.GONE);
    }

    public void initComTaskActivity() {
        if (mStatus.isNewStatus()) {
            setActionBarMidlleTitle("新增生产备料清单");
        }
        else {
            setActionBarMidlleTitle("生产备料清单");
        }
        tv_title_live_layout.setText("生产备料分录");

        mRecyclerViewAdapter = new ProductionOrderViewActivity.MyTaskContentAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        listView.setLayoutManager(linearLayoutManager);

        workflowSection = new ProductionOrderViewItemSection(comTaskBeans, mStatus);
        String[] stringArray = getResources().getStringArray(R.array.productionPrepare_itemsection_order);
        workflowSection.setTVIDContent(stringArray);
        new ItemTouchHelper(new ComTaskContentItemSectionItemTouchHelper(mRecyclerViewAdapter)).attachToRecyclerView(listView);
        workflowSection.setOnItemClickListener(this);

        mRecyclerViewAdapter.addSection(workflowSection);

        listView.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewAdapter.notifyDataSetChanged();

        testData();
//        TopListViewInit(this.comTaskBeans);
    }

    /**
     * 初始布局  为列表布局
     * param comTaskBeans
     */
    private void TopListViewInit() {
        productionOrderBean comTaskBean1 = null;

        comTaskBean1 = this.comTaskBeans.get(0);

        if (!TextUtils.isEmpty(receiptHnumber)) {
            comTaskBean1.hNumbe = receiptHnumber;
            if (getComtaskSize()) {
                for (productionOrderBean bean : comTaskBeans) {
                    bean.hNumbe = receiptHnumber;
                }
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        String[] LETTERS = new String[]{"单据信息", "录入人信息", "审核人信息"};
        String[] key = {"4", "2", "2"};

        ArrayList<com.system.bhouse.bhouse.CommonTask.ProduceManagement.ProductionOrder.ProductionOrderContentMessageActivity.KeyType> keyTypes = new ArrayList<>();
        com.system.bhouse.bhouse.CommonTask.ProduceManagement.ProductionOrder.ProductionOrderContentMessageActivity.KeyType keyType = null;
        for (int i = 0; i < LETTERS.length; i++) {
            keyType = new com.system.bhouse.bhouse.CommonTask.ProduceManagement.ProductionOrder.ProductionOrderContentMessageActivity.KeyType();
            keyType.key = Integer.valueOf(key[i]);
            keyType.type = LETTERS[i];
            keyTypes.add(keyType);
        }

        final List<TreeItem> groupItems = new ArrayList<>();

        for (int i = 0; i < LETTERS.length; i++) {
            GroupItem sortGroupItem = new GroupItem();
            sortGroupItem.TitleKey = LETTERS[i];
            sortGroupItem.setmOnChildItemClickListener(this);
            sortGroupItem.setData(makeChlidData(comTaskBean1, keyTypes.get(i), mStatus));
            groupItems.add(sortGroupItem);
        }

        treeRecyclerAdapter = new TreeRecyclerAdapter();
        topListView.setLayoutManager(linearLayoutManager);
        topListView.setAdapter(treeRecyclerAdapter);
        treeRecyclerAdapter.getItemManager().replaceAllItem(groupItems);
    }


    /**
     * 初始化  childItem 子布局所需的数据
     *
     * @param comTaskBean1
     * @param data
     * @param mStatus
     * @return
     */
    protected ArrayList<SortChildItem.ViewModel> makeChlidData(productionOrderBean comTaskBean1, com.system.bhouse.bhouse.CommonTask.ProduceManagement.ProductionOrder.ProductionOrderContentMessageActivity.KeyType data, StatusBean mStatus) {
        ArrayList<SortChildItem.ViewModel> viewModels = new ArrayList<>();
        SortChildItem.ViewModel viewModel = null;
        if (data.key == 4) {

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "单据编号";
            viewModel.value = TextUtils.isEmpty(receiptHnumber) ? App.receiptHnumber : receiptHnumber;
            viewModel.key = "receiptHnumber";
            viewModel.isClick = false;
            viewModels.add(viewModel);
//            comTaskBean1.hNumbe = App.receiptHnumber;
            headerProperties.put(viewModel.key, viewModel.value);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "状态";
            viewModel.value = this.comTaskBeans.get(0).getStatus();
            viewModel.key = "status";
            viewModel.isClick = false;
            viewModels.add(viewModel);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "管理单元";
            viewModel.value = App.Mancompany == null ? "" : App.Mancompany;
            viewModel.key = "Mancompany";
            viewModel.isClick = false;
            viewModels.add(viewModel);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "计划开始日";
            viewModel.value = this.comTaskBeans.get(0).getPlanStartDate();
            viewModel.key = "planStartDate";
            if (mStatus.isNewStatus() || mStatus.isModifyStatus()) {
                viewModel.isClick = true;
            }
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key, viewModel.value);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "计划结束日";
            viewModel.value = this.comTaskBeans.get(0).getPlanEndDate();
            viewModel.key = "planEndDate";
            if (mStatus.isNewStatus() || mStatus.isModifyStatus()) {
                viewModel.isClick = true;
            }
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key, viewModel.value);
        }
        else if (data.key == 2 && data.type.equals("录入人信息")) {

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "录入人";
            viewModel.value = this.comTaskBeans.get(0).getEntryPeople();
            viewModel.key = "enterPeople";
            viewModel.isClick = false;
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key, viewModel.value);

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
        outState.putParcelableArrayList(STATE_COMTASK, this.comTaskBeans);
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

        if (data.name.equals("计划开始日")) {
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
//            ShowDialogWithRefreshDescription(text, adapterPosition);
        }
    }
//
//    private void ShowDialogWithRefreshDescription(String text, int adapterPosition) {
//        ShowDeviceMessageCustomDialog.Builder builder = new ShowDeviceMessageCustomDialog.Builder(this);
//        builder.setMessage("您的描述是:");
//        builder.setTitle("提示");
//        builder.setEdittextcontent(text);
//
//        View inflate = LayoutInflater.from(this).inflate(R.layout.beizhu_edittext, null, false);
//        builder.setContentView(inflate);
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                EditText viewEditText = (EditText) inflate.findViewById(R.id.edt_domian);
//                if (viewEditText.getText() != null) {
//                    String text = viewEditText.getText().toString();
//                    if (TextUtils.isEmpty(text)) {
//                        T.showfunShort(com.system.bhouse.bhouse.CommonTask.ProduceManagement.ProductionOrder.PickingOutLibaryContentMessageActivity.this, "备注不能为空");
//                    }
//                    else {
//                        getDateRefresh(text, adapterPosition, "描述");
//                    }
//                }
//            }
//        });
//
//        builder.setNegativeButton("取消",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//        builder.create().show();
//    }

//    //扫描回调 带出信息
//    @OnActivityResult(REQUST_QRCODE)
//    void resultGetRequstQrcode(int result, Intent data) {
//        if (result == RESULT_OK) {
//            Bundle bundle = data.getBundleExtra("bundle");
//            String resultQr = bundle.getString("result");
//            int extraPosition = bundle.getInt("position");
//            if (extraPosition == -1) {
//                ApiWebService.Get_Sale_Order_Car_check_Inprid_QR_Code_Json(this, new ApiWebService.SuccessCall() {
//                    @Override
//                    public void SuccessBack(String result) {
//
//                        ArrayList<productionOrderBean> loadingcarbean = App.getAppGson().fromJson(result, new TypeToken<List<productionOrderBean>>() {
//                        }.getType());
//
//                        if (loadingcarbean.isEmpty()) {
//                            T.showShort(com.system.bhouse.bhouse.CommonTask.ProduceManagement.ProductionOrder.PickingOutLibaryContentMessageActivity.this, getResources().getString(R.string.Qrcode_result));
//                        }
//
//                        for (productionOrderBean bean : loadingcarbean) {
//                            bean.hNumbe = headerProperties.get("receiptHnumber");
//                            bean.requireDate = headerProperties.get("requireDate");
//                            bean.description = headerProperties.get("description");
//                            bean.entryPeople = headerProperties.get("enterPeople");
//                        }
//
//                        //清空 二维码为空的
//                        for (productionOrderBean receBean : comTaskBeans) {
//                            if (TextUtils.isEmpty(receBean.getQrcode())) {
//                                comTaskBeans.remove(receBean);
//                            }
//                        }
//                        if (!(loadingcarbean.size() == 0)) {
//                            comTaskBeans.addAll(loadingcarbean);
//                        }
//                        ArrayList<productionOrderBean> clone = (ArrayList<productionOrderBean>) comTaskBeans.clone();
//                        comTaskBeans.clear();
//                        comTaskBeans.addAll(removeDupliById(clone));
//
//                        mRecyclerViewAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void ErrorBack(String error) {
//
//                    }
//                }, resultQr);
//            }
//            else {
//
//                ApiWebService.B_Get_Ware_house(this, new ApiWebService.SuccessCall() {
//                    @Override
//                    public void SuccessBack(String result) {
//
//                        ArrayList<productionOrderBean> loadingcarbean = App.getAppGson().fromJson(result, new TypeToken<List<productionOrderBean>>() {
//                        }.getType());
//
//                        if (!ValueUtils.IsFirstValueExist(loadingcarbean))
//                            return;
////                    comTaskBeans.get(extraPosition).setWareHouseID(loadingcarbean.get(0).wareHouseID);
////                    comTaskBeans.get(extraPosition).setWareHouseName(loadingcarbean.get(0).wareHouseName);
//
//                        ArrayList<productionOrderBean> clone = (ArrayList<productionOrderBean>) comTaskBeans.clone();
//                        comTaskBeans.clear();
//                        comTaskBeans.addAll(clone);
//                        mRecyclerViewAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void ErrorBack(String error) {
//
//                    }
//                }, resultQr);
//            }
//        }
//    }

    /**
     * pickActivity 结束回调
     * param result
     * param data
     */
//    @OnActivityResult(RESULT_SORTITEM_SELECTPROJECT)
//    void resultProjcetName(int result, Intent data) {
//        if (result == RESULT_OK) {
//            String extrasName = data.getStringExtra("projectname");
//            int extraPosition = data.getIntExtra("position", 0);
//            String extra = data.getStringExtra("HId");
//            String extraCoding = data.getStringExtra("coding");
//            String extraBOMID = data.getStringExtra("BOMID");
//        }
//    }

    //得到分录的数据
//    private void getEntriesData(String extra) {
//        ApiWebService.Get_Sale_Order_Hois_Req_Json(this, new ApiWebService.SuccessCall() {
//            @Override
//            public void SuccessBack(String result) {
//
//                ArrayList<productionOrderBean> loadingcarbean = App.getAppGson().fromJson(result, new TypeToken<List<productionOrderBean>>() {
//                }.getType());
//
//                for (productionOrderBean bean : loadingcarbean) {
//                    bean.hNumbe = comTaskBeans.get(0).getHNumbe();
//                    bean.cartrips = comTaskBeans.get(0).getCartrips();
//                    bean.containerName = comTaskBeans.get(0).getContainerName();
//                    bean.containerID = comTaskBeans.get(0).getContainerID();
//                    bean.requireDate = comTaskBeans.get(0).getRequireDate();
//                    bean.description = comTaskBeans.get(0).getDescription();
//                    bean.entryPeople = comTaskBeans.get(0).getEntryPeople();
//                }
//                comTaskBeans.clear();
//                comTaskBeans.addAll(loadingcarbean);
//                mRecyclerViewAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void ErrorBack(String error) {
//
//            }
//        }, extra);
//    }

    public static ArrayList<productionOrderBean> removeDupliById(List<productionOrderBean> persons) {
        Set<productionOrderBean> personSet = new TreeSet<>((o1, o2) -> {
            if (o1.getQrcode().equals(o2.getQrcode())) {
                return 0;
            }
            else {
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
        if (viewModel.name.equals(typestring) && typestring.equals("计划开始日")) {
            if (viewModel.value == null || !viewModel.value.equals(date))
                viewModel.value = date;
            for (productionOrderBean receBean : comTaskBeans) {
                receBean.setPlanStartDate(viewModel.value);
            }
        }
        else if (viewModel.name.equals(typestring) && typestring.equals("计划结束日")) {
            if (viewModel.value == null || !viewModel.value.equals(date))
                viewModel.value = date;
            for (productionOrderBean receBean : comTaskBeans) {
                receBean.setPlanEndDate(viewModel.value);
            }
        }
        else if (viewModel.name.equals("描述")) {
            if (viewModel.value == null || !viewModel.value.equals(date))
                viewModel.value = date;

            for (int i = 0; i < comTaskBeans.size(); i++) {
                comTaskBeans.get(i).description = viewModel.value;
            }
        }
        headerProperties.put(viewModel.key, viewModel.value);
        treeItem.setData(viewModel);
        treeRecyclerAdapter.notifyDataSetChanged();
    }


    public static class KeyType {
        public int key;
        public String type;
    }


    /**
     * 请求主数据
     */
    private void testData() {

        ApiWebService.Get_Production_orderView_list_Json(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                ArrayList<productionOrderBean> tomTaskBeans = App.getAppGson().fromJson(result, new TypeToken<List<productionOrderBean>>() {
                }.getType());
                //为空就创建一个新的空对象
                if (tomTaskBeans.isEmpty()) {
                    productionOrderBean bean = new productionOrderBean();
                    bean.setDisableDelete(true);
                    comTaskBeans.add(bean);
                }
                else {
                    comTaskBeans.addAll(tomTaskBeans);
                }
                mRecyclerViewAdapter.notifyDataSetChanged();
                TopListViewInit();
            }

            @Override
            public void ErrorBack(String error) {

            }
        }, HId);
        //上个result id 值
    }


    private int ikey = 0;

    @Override
    public void onItemClick(View view, View textView, int position) {

    }

    @Override
    public void onImgItemDelete(int position, int positionAdapter) {

        if (isDeleteAble) {//此时为增加动画效果，刷新部分数据源，防止删除错乱
            isDeleteAble = false;//初始值为true,当点击删除按钮以后，休息0.5秒钟再让他为
            //true,起到让数据源刷新完成的作用
            // 删除数据
            mRecyclerViewAdapter.onItemRemove(positionAdapter);
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
            mRecyclerViewAdapter.notifyItemRemoved(positionAdapter);
            mRecyclerViewAdapter.notifyDataSetChanged();
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
    @Override
    public void onImgItemAdd(View view, int position, RecyclerView.ViewHolder holder) {
        if (comTaskBeans == null)
            return;
        productionOrderBean dataBean = new productionOrderBean();
        comTaskBeans.add(dataBean);

        mRecyclerViewAdapter.notifyItemInserted(comTaskBeans.size());
        if (!isVisBottom(listView)) {
            listView.smoothScrollToPosition(comTaskBeans.size() + 1);
        }
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    private class MyTaskContentAdapter extends ComTaskContentItemSectionItemTouchHelper.ItemTouchAdapterImpl {

        @Override
        public void onItemMove(int fromPosition, int toPosition) {

        }

        @Override
        public void onItemRemove(int position) {

        }
    }

    /**
     * show1 展示 dialog
     */
    private void show1() {
        bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.confirmation_dialog_content_normal, null);
        bottomDialog.setContentView(contentView);
        LinearLayout llModify = (LinearLayout) contentView.findViewById(R.id.ll_modify);
        LinearLayout llSubmit = (LinearLayout) contentView.findViewById(R.id.ll_submit);
        LinearLayout llCheck = (LinearLayout) contentView.findViewById(R.id.ll_check);
        LinearLayout llFanCheck = (LinearLayout) contentView.findViewById(R.id.ll_fanCheck);
        LinearLayout llQrcode = (LinearLayout) contentView.findViewById(R.id.ll_qrcode);

        TextView tvModify = (TextView) contentView.findViewById(R.id.tv_modify);
        TextView tvSubmit = (TextView) contentView.findViewById(R.id.tv_submit);
        TextView tvCheck = (TextView) contentView.findViewById(R.id.tv_check);
        TextView tvFanCheck = (TextView) contentView.findViewById(R.id.tv_fanCheck);
        TextView tvDelete = (TextView) contentView.findViewById(R.id.tv_delete);
        TextView tvQrcode = (TextView) contentView.findViewById(R.id.tv_qrcode);
        tvQrcode.setText("选取构件");

        /**
         * 这里{按键会变化View.GONE}
         */

        llCheck.setVisibility(mStatus.getBean().visCheckBtn ? View.VISIBLE : View.GONE);
        llModify.setVisibility(mStatus.getBean().visModifyBtn ? View.VISIBLE : View.GONE);
        llFanCheck.setVisibility(mStatus.getBean().visCheckFBtn ? View.VISIBLE : View.GONE);
        tvDelete.setVisibility(mStatus.getBean().visDeleteBtn ? View.VISIBLE : View.GONE);
        llQrcode.setVisibility(mStatus.getBean().visQRBtn ? View.VISIBLE : View.GONE);
        llSubmit.setVisibility(mStatus.getBean().visSubmitBtn ? View.VISIBLE : View.GONE);

        Observable.create(subscriber -> {
            tvQrcode.setOnClickListener(v -> {
                subscriber.onNext(v);
            });
        }).debounce(350, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(V -> {
            L.e("double click");
            bottomDialog.dismiss();
            tvQrcodeAction();
        });

        Observable.create(subscriber -> {
            tvDelete.setOnClickListener(v -> {
                subscriber.onNext(v);
            });
        }).debounce(350, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(V -> {
            L.e("double click");
            bottomDialog.dismiss();
//            tvDeleteAction();
        });

        Observable.create(subscriber -> {
            tvFanCheck.setOnClickListener(v -> {
                subscriber.onNext(v);
            });
        }).debounce(350, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(V -> {
            L.e("double click");
            bottomDialog.dismiss();
//            tvFanCheckAction();
        });

        tvModify.setOnClickListener(v -> {
            mStatus.setBean(new SubmitStatusBeanImpl().setVisSubmitBtn(true).setVisQRBtn(true));
            mStatus.setLookStatus(true);
            mStatus.setModifyStatus(true);
            if (mStatus.isModifyStatus()) {
                setActionBarMidlleTitle("修改构件退货");
                TopListViewInit();

                workflowSection = new ProductionOrderViewItemSection(comTaskBeans, mStatus);
                String[] stringArray = getResources().getStringArray(R.array.ComponentReturn_itemsection_order);
                workflowSection.setTVIDContent(stringArray);
                mRecyclerViewAdapter.removeAllSections();
                mRecyclerViewAdapter.addSection(workflowSection);

                mRecyclerViewAdapter.notifyDataSetChanged();
                bottomDialog.dismiss();
            }
        });

        Observable.create(subscriber -> {
            tvSubmit.setOnClickListener(v -> {
                subscriber.onNext(v);
            });
        }).debounce(350, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(V -> {
            L.e("double click");
            bottomDialog.dismiss();
            if (mStatus.isNewStatus()) {
//                tvSubmitActionforList();
            }
            else if (mStatus.isModifyStatus()) {
//                tvSubmitActionforList();
            }
        });

        Observable.create(subscriber -> {
            tvCheck.setOnClickListener(v -> {
                subscriber.onNext(v);
            });
        }).debounce(350, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(V -> {
            L.e("double click");
            bottomDialog.dismiss();
//            tvCheckAction();
        });

        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

    //吊装需求拉取 点击事件//构件选取
    private void tvQrcodeAction() {

//        Intent intent = new Intent(com.system.bhouse.bhouse.CommonTask.ProduceManagement.ProductionOrder.PickingOutLibaryContentMessageActivity.this, CaptureActivity.class);
//        intent.putExtra("position", -1);
//        startActivityForResult(intent, REQUST_QRCODE);
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


    protected void sureDataRefresh(String type) {
        EventBus.getDefault().post("刷新" + type + "数据");
    }


    public static class DataBean {
        public String wuLiao;
        public String countUnit;
        public String count;
    }

    private boolean isVisBottom(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE) {
            return true;
        }
        else {
            return false;
        }
    }

//    @OptionsItem
//    protected final void action_operat_status() {
//        Observable<Object> objectObservable = Observable.create(subscriber -> {
//            show1();
//        });
//        Observable observableMobileKey = ApiWebService.Get_KeyTimestr(App.MobileKey);
//        observableMobileKey.concatWith(objectObservable).subscribe(new Subscriber() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Object o) {
//                App.KeyTimestring = o.toString();
//            }
//        });
//    }

}
