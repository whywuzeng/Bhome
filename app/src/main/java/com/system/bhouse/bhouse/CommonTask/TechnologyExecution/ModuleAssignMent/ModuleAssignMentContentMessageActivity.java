package com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ModuleAssignMent;

import android.app.Dialog;
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
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.system.bhouse.Custom.ShowDeviceMessageCustomDialog;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.base.StatusBean;
import com.system.bhouse.base.SubmitStatusBeanImpl;
import com.system.bhouse.bhouse.CommonTask.BaseTaskFragment.BaseContentMessageActivity;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ModuleAssignMent.Bean.ModuleAssignmentBean;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ModuleAssignMent.Bean.ModuleAssignmentBeanSection;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.Widget.TimeLineItemTopBottomDecoration;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.TreeRecyclerAdapter;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.base.ViewHolder;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.item.GroupItem;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.item.SortChildItem;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.item.TreeItem;
import com.system.bhouse.bhouse.CommonTask.common.CommonDateTimePickerFragment;
import com.system.bhouse.bhouse.CommonTask.utils.ComTaskContentItemSectionItemTouchHelper;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.utils.onMutiDataSetListener;
import com.system.bhouse.utils.ClickUtils;
import com.system.bhouse.utils.TenUtils.L;
import com.system.bhouse.utils.TenUtils.T;
import com.system.bhouse.utils.ValueUtils;

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
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask
 */
@EActivity(R.layout.activity_comtask_content_layout)
@OptionsMenu(R.menu.menu_comtask)
public class ModuleAssignMentContentMessageActivity extends BaseContentMessageActivity implements  GroupItem.onChildItemClickListener, onMutiDataSetListener ,BaseQuickAdapter.OnItemChildClickListener{

    public static final String TAG = "comtaskcontentmessageactivity";

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

    private final static String STATUS_SAVE="保存";
    private final static String STATUS_CHECK="审核";

    private ArrayList<ModuleAssignmentBean> comTaskBeans = new ArrayList<>();
    private ArrayList<ModuleAssignmentBeanSection> assignmentBeanSectionArrayList=new ArrayList<>() ;
    private TreeRecyclerAdapter treeRecyclerAdapter;
    private boolean isDeleteAble = true;
    public static final int RESULT_SORTITEM_SELECTPROJECT = 1001;
    public static final int REQUST_QRCODE = 1008;

    private Dialog bottomDialog;
    private String STATE_COMTASK = "state_comtask";
    private HashMap<String,String> headerProperties=new HashMap<>();
    private ModuleAssignmentSectionAdapter moduleAssignmentSectionAdapter;

    @AfterViews
    public void initComTaskActivity() {
        tv_title_live_layout.setText("模具分配分录");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(linearLayoutManager);
        recycleViewAddEmptySection(listView);
        String[] stringArray = getResources().getStringArray(R.array.module_itemsection_order);

        moduleAssignmentSectionAdapter = new ModuleAssignmentSectionAdapter(R.layout.activity_comtask_content_layout_item, R.layout.layout_home_recommend_empty_noheight, R.layout.comtask_content_item_footer,assignmentBeanSectionArrayList,stringArray,mStatus);

        listView.setNestedScrollingEnabled(false);

        listView.setAdapter(moduleAssignmentSectionAdapter);
        listView.addItemDecoration(new TimeLineItemTopBottomDecoration());
        moduleAssignmentSectionAdapter.setOnItemChildClickListener(this);
        testData();
//      TopListViewInit(this.comTaskBeans);
        BottomAction();
        setScrollViewFirst();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 检查是否正在重新创建一个以前销毁的实例
        if (savedInstanceState != null) {
            // 从已保存状态恢复成员的值
            this.comTaskBeans = savedInstanceState.getParcelableArrayList(STATE_COMTASK);
        }
        else {
            // 可能初始化一个新实例的默认值的成员
        }
    }

    /**
     * 初始布局  为列表布局
     * param comTaskBeans
     */
    private void TopListViewInit() {
        /**
         * 进行判断 mStatus 状态
         */
        //得到初始数据，处理单据状态
        if (mStatus.isNewStatus()) {
            setActionBarMidlleTitle("新增模具分配");
        }
        else {
            setActionBarMidlleTitle("模具分配");
        }

        if (!TextUtils.isEmpty(receiptHnumber)) {
            if (getComtaskSize()) {
                for (ModuleAssignmentBean bean : comTaskBeans) {
                    bean.hNumbe = receiptHnumber;
                }
            }
        }else if (ValueUtils.IsFirstValueExist(this.comTaskBeans)){
            receiptHnumber=this.comTaskBeans.get(0).hNumbe;
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        String[] LETTERS = new String[]{"单据信息", "录入人信息", "审核人信息"};
        String[] key = {"4", "2", "2"};

        ArrayList<ModuleAssignMentContentMessageActivity.KeyType> keyTypes = new ArrayList<>();
        ModuleAssignMentContentMessageActivity.KeyType keyType = null;
        for (int i = 0; i < LETTERS.length; i++) {
            keyType = new ModuleAssignMentContentMessageActivity.KeyType();
            keyType.key = Integer.valueOf(key[i]);
            keyType.type = LETTERS[i];
            keyTypes.add(keyType);
        }

        final List<TreeItem> groupItems = new ArrayList<>();

        for (int i = 0; i < LETTERS.length; i++) {
            GroupItem sortGroupItem = new GroupItem();
            sortGroupItem.TitleKey = LETTERS[i];
            sortGroupItem.setmOnChildItemClickListener(this);
            sortGroupItem.setData(makeChlidData(keyTypes.get(i), mStatus));
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
     * @return
     */
    protected ArrayList<SortChildItem.ViewModel> makeChlidData( ModuleAssignMentContentMessageActivity.KeyType data, StatusBean mStatus) {
        ArrayList<SortChildItem.ViewModel> viewModels = new ArrayList<>();
        SortChildItem.ViewModel viewModel=null;
        if (data.key == 4) {

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "单据编号";
            viewModel.value = TextUtils.isEmpty(receiptHnumber) ? App.receiptHnumber : receiptHnumber;
            viewModel.key = "receiptHnumber";
            viewModel.isClick = false;
            viewModels.add(viewModel);
//            comTaskBean1.hNumbe = App.receiptHnumber;
            headerProperties.put(viewModel.key,viewModel.value);

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
            viewModel.name = "业务日期";
            viewModel.value = this.comTaskBeans.get(0).getRequireDate();
            viewModel.key = "requireDate";
            if (mStatus.isNewStatus() || mStatus.isModifyStatus()) {
                viewModel.isClick = true;
            }
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

        if (data.name.equals("业务日期")) {
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
                        T.showfunShort(ModuleAssignMentContentMessageActivity.this, "备注不能为空");
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

                        ArrayList<ModuleAssignmentBean> loadingcarbean = App.getAppGson().fromJson(result, new TypeToken<List<ModuleAssignmentBean>>() {
                        }.getType());

                        if (loadingcarbean.isEmpty())
                        {
                            T.showShort(ModuleAssignMentContentMessageActivity.this,getResources().getString(R.string.Qrcode_result));
                        }

                        for (ModuleAssignmentBean bean : loadingcarbean) {
                            bean.hNumbe= headerProperties.get("receiptHnumber");
                            bean.requireDate= headerProperties.get("requireDate");
                            bean.description=headerProperties.get("description");
                            bean.entryPeople= headerProperties.get("enterPeople");
                        }

                        //清空 二维码为空的
                        for (ModuleAssignmentBean receBean : comTaskBeans) {
                            if (TextUtils.isEmpty(receBean.Qrcode)) {
                                comTaskBeans.remove(receBean);
                            }
                        }
                        if (!(loadingcarbean.size() == 0)) {
                            comTaskBeans.addAll(loadingcarbean);
                        }
                        ArrayList<ModuleAssignmentBean> clone =(ArrayList<ModuleAssignmentBean>)comTaskBeans.clone();
                        comTaskBeans.clear();
                        comTaskBeans.addAll(removeDupliById(clone));

                        moduleAssignmentSectionAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void ErrorBack(String error) {

                    }
                },HId,resultQr);
            }else {
                //分录下的Item 回调.
//                ApiWebService.B_Get_Ware_house(this, new ApiWebService.SuccessCall() {
//                    @Override
//                    public void SuccessBack(String result) {
//
//                        ArrayList<ModuleAssignmentBean> loadingcarbean = App.getAppGson().fromJson(result, new TypeToken<List<ModuleAssignmentBean>>() {
//                        }.getType());
//
//                        if (!ValueUtils.IsFirstValueExist(loadingcarbean))
//                            return;
//                        comTaskBeans.get(extraPosition).setWareHouseID(loadingcarbean.get(0).wareHouseID);
//                        comTaskBeans.get(extraPosition).setWareHouseName(loadingcarbean.get(0).wareHouseName);
//
//                        ArrayList<ModuleAssignmentBean> clone = (ArrayList<ModuleAssignmentBean>)comTaskBeans.clone();
//                        comTaskBeans.clear();
//                        comTaskBeans.addAll(clone);
//                        mRecyclerViewAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void ErrorBack(String error) {
//
//                    }
//                },resultQr);
            }
        }
    }

    /**
     * pickActivity 结束回调
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
        }
    }

    //得到分录的数据
//    private void getEntriesData(String extra) {
//        ApiWebService.Get_Sale_Order_Hois_Req_Json(this, new ApiWebService.SuccessCall() {
//            @Override
//            public void SuccessBack(String result) {
//
//                ArrayList<ModuleAssignmentBean> loadingcarbean = App.getAppGson().fromJson(result, new TypeToken<List<ModuleAssignmentBean>>() {
//                }.getType());
//
//                for (ModuleAssignmentBean bean : loadingcarbean) {
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

    public static ArrayList<ModuleAssignmentBean> removeDupliById(List<ModuleAssignmentBean> persons) {
        Set<ModuleAssignmentBean> personSet = new TreeSet<>((o1, o2) -> {
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
            for (ModuleAssignmentBean receBean : comTaskBeans) {
                receBean.requireDate=viewModel.value;
            }
        }
        else if (viewModel.name.equals("描述")) {
            if (viewModel.value==null||!viewModel.value.equals(date))
                viewModel.value = date;

            for (int i = 0; i < comTaskBeans.size(); i++) {
                comTaskBeans.get(i).description = viewModel.value;
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

                break;
        }
    }


    public static class KeyType {
        public int key;
        public String type;
    }


    /**
     * 请求主数据
     */
    private void testData() {

        ApiWebService.Get_Production_order_MouldView_Json(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                ArrayList<ModuleAssignmentBean> tomTaskBeans = App.getAppGson().fromJson(result, new TypeToken<List<ModuleAssignmentBean>>() {
                }.getType());
                //为空就创建一个新的空对象
                if (tomTaskBeans.isEmpty()) {

                    moduleAssignmentSectionAdapter.setEmptyView(notDataView);
                    //为头recycleView 设置空数据
                    comTaskBeans.add(new ModuleAssignmentBean());
                }
                else {
                    comTaskBeans.addAll(tomTaskBeans);
                    ClearAssignMentSectionArrayList();
                }
                moduleAssignmentSectionAdapter.notifyDataSetChanged();
                TopListViewInit();
            }

            @Override
            public void ErrorBack(String error) {
                //设置error字段
                setErrorViewContext(error);
                //设置Empty  ErrorView
                moduleAssignmentSectionAdapter.setEmptyView(errorView);
            }
        }, HId);
    }

    /**
     * 清理数据
     * 重新包裹 assignmentBeanSectionArrayList
     */
    private void ClearAssignMentSectionArrayList() {
        assignmentBeanSectionArrayList.clear();
        for(ModuleAssignmentBean bean : comTaskBeans)
        {
            assignmentBeanSectionArrayList.add(new ModuleAssignmentBeanSection(bean));
        }
    }


    private int ikey = 0;

    public void onItemClick(View view, View textView, int position) {
//        ModuleAssignmentBean searchHistroyBeans = workflowSection.getSearchHistroyBeans(position);
//        if (textView.getTag() == "1111") {
//            if (TextUtils.isEmpty(searchHistroyBeans.getModuleID()))
//                return;
//            //扫描 仓库码
//            Intent intent = new Intent(this, CaptureActivity.class);
//            intent.putExtra("position", position);
//            startActivityForResult(intent, REQUST_QRCODE);
//        }
    }

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
            moduleAssignmentSectionAdapter.notifyItemRemoved(position-1);
            moduleAssignmentSectionAdapter.notifyDataSetChanged();
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
    public void onImgItemAdd(View view, int position, RecyclerView.ViewHolder holder) {
        if (comTaskBeans == null)
            return;
//        ModuleAssignmentBean dataBean = new ModuleAssignmentBean();
//        comTaskBeans.add(dataBean);
//
//        mRecyclerViewAdapter.notifyItemInserted(comTaskBeans.size());
//        if (!isVisBottom(listView)) {
//            listView.smoothScrollToPosition(comTaskBeans.size() + 1);
//        }
//        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    private class MyTaskContentAdapter extends ComTaskContentItemSectionItemTouchHelper.ItemTouchAdapterImpl {

        @Override
        public void onItemMove(int fromPosition, int toPosition) {

        }

        @Override
        public void onItemRemove(int position) {

        }
    }

//    /**
//     * show1 展示 dialog
//     */
//    private void show1() {
//        bottomDialog = new Dialog(this, R.style.BottomDialog);
//        View contentView = LayoutInflater.from(this).inflate(R.layout.confirmation_dialog_content_normal, null);
//        bottomDialog.setContentView(contentView);
//        LinearLayout llModify = (LinearLayout) contentView.findViewById(R.id.ll_modify);
//        LinearLayout llSubmit = (LinearLayout) contentView.findViewById(R.id.ll_submit);
//        LinearLayout llCheck = (LinearLayout) contentView.findViewById(R.id.ll_check);
//        LinearLayout llFanCheck = (LinearLayout) contentView.findViewById(R.id.ll_fanCheck);
//        LinearLayout llQrcode = (LinearLayout) contentView.findViewById(R.id.ll_qrcode);
//
//        TextView tvModify = (TextView) contentView.findViewById(R.id.tv_modify);
//        TextView tvSubmit = (TextView) contentView.findViewById(R.id.tv_submit);
//        TextView tvCheck = (TextView) contentView.findViewById(R.id.tv_check);
//        TextView tvFanCheck = (TextView) contentView.findViewById(R.id.tv_fanCheck);
//        TextView tvDelete = (TextView) contentView.findViewById(R.id.tv_delete);
//        TextView tvQrcode = (TextView) contentView.findViewById(R.id.tv_qrcode);
//
//        tvQrcode.setText("可领料信息");
//
//        /**
//         * 这里{按键会变化View.GONE}
//         */
//
//        llCheck.setVisibility(mStatus.getBean().visCheckBtn?View.VISIBLE:View.GONE);
//        llModify.setVisibility(mStatus.getBean().visModifyBtn?View.VISIBLE:View.GONE);
//        llFanCheck.setVisibility(mStatus.getBean().visCheckFBtn?View.VISIBLE:View.GONE);
//        tvDelete.setVisibility(mStatus.getBean().visDeleteBtn?View.VISIBLE:View.GONE);
//        llQrcode.setVisibility(mStatus.getBean().visQRBtn?View.VISIBLE:View.GONE);
//        llSubmit.setVisibility(mStatus.getBean().visSubmitBtn?View.VISIBLE:View.GONE);
//
//        Observable.create(subscriber -> {
//            tvQrcode.setOnClickListener(v -> {
//                subscriber.onNext(v);
//            });
//        }).debounce(350, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(V -> {
//            L.e("double click");
//            bottomDialog.dismiss();
//            tvQrcodeAction(tvQrcode);
//        });
//
//        Observable.create(subscriber -> {
//            tvDelete.setOnClickListener(v -> {
//                subscriber.onNext(v);
//            });
//        }).debounce(350, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(V -> {
//            L.e("double click");
//            bottomDialog.dismiss();
//            tvDeleteAction(tvDelete);
//        });
//
//        Observable.create(subscriber -> {
//            tvFanCheck.setOnClickListener(v -> {
//                subscriber.onNext(v);
//            });
//        }).debounce(350, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(V -> {
//            L.e("double click");
//            bottomDialog.dismiss();
//            tvFanCheckAction(tvFanCheck);
//        });
//
//        Observable.create(subscriber -> {
//            tvModify.setOnClickListener(v -> {
//                subscriber.onNext(v);
//            });
//        }).debounce(350, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(V -> {
//            L.e("double click");
//            bottomDialog.dismiss();
//            tvModifyAction(tvModify);
//        });
//
//        Observable.create(subscriber -> {
//            tvSubmit.setOnClickListener(v -> {
//                subscriber.onNext(v);
//            });
//        }).debounce(350, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(V -> {
//            L.e("double click");
//            bottomDialog.dismiss();
//            if (mStatus.isNewStatus()) {
//                tvSubmitActionforList(tvSubmit);
//            }
//            else if (mStatus.isModifyStatus()) {
//                tvSubmitActionforList(tvSubmit);
//            }
//        });
//
//        Observable.create(subscriber -> {
//            tvCheck.setOnClickListener(v -> {
//                subscriber.onNext(v);
//            });
//        }).debounce(350, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(V -> {
//            L.e("double click");
//            bottomDialog.dismiss();
//            tvCheckAction(tvCheck);
//        });
//
//        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
//        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
//        contentView.setLayoutParams(layoutParams);
//        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
//        bottomDialog.setCanceledOnTouchOutside(true);
//        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
//        bottomDialog.show();
//    }

    //单据---修改状态
    @Override
    protected void tvModifyAction(TextView tvModify) {
        mStatus.setBean(new SubmitStatusBeanImpl().setVisSubmitBtn(true).setVisQRBtn(true));
        mStatus.setLookStatus(true);
        mStatus.setModifyStatus(true);
        if (mStatus.isModifyStatus()) {
            setActionBarMidlleTitle("修改完工入库");
            TopListViewInit();

            ClearAssignMentSectionArrayList();
            moduleAssignmentSectionAdapter.notifyDataSetChanged();
            bottomDialog.dismiss();
        }
    }

    //可领料信息  二维码扫描
    @Override
    protected void tvQrcodeAction(TextView tvQrcode) {

        ApiWebService.Get_Production_order_Mould_Mouldfp_Json(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                //[{"模具ID":"11a5b5ccb081467ab80af1da6b05082f","模具名称":"PC构件003"}]

                ArrayList<ModuleAssignmentBean> loadingcarbean = App.getAppGson().fromJson(result, new TypeToken<List<ModuleAssignmentBean>>() {
                }.getType());

                if (!ValueUtils.IsFirstValueExist(loadingcarbean))
                    return;
                comTaskBeans.get(0).setModuleID(loadingcarbean.get(0).moduleID);
                comTaskBeans.get(0).setModuleName(loadingcarbean.get(0).moduleName);

                ClearAssignMentSectionArrayList();
                moduleAssignmentSectionAdapter.notifyDataSetChanged();
            }

            @Override
            public void ErrorBack(String error) {

            }
        }, orderId, componentQr, comTaskBeans.get(0).materialID, comTaskBeans.get(0).startTime, comTaskBeans.get(0).endTime);

//        Intent intent = new Intent(ModuleAssignMentContentMessageActivity.this, CaptureActivity.class);
//        intent.putExtra("position", -1);
//        startActivityForResult(intent, REQUST_QRCODE);
    }

    /**
     * 访问下面recycleView数据
     */
    public void BottomAction(){
        ApiWebService.Get_Production_order_Mould_bypoid_Json(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {

                ArrayList<ModuleAssignmentBean> loadingcarbean = App.getAppGson().fromJson(result, new TypeToken<List<ModuleAssignmentBean>>() {
                }.getType());

                if (loadingcarbean.isEmpty()) {
                    T.showShort(ModuleAssignMentContentMessageActivity.this, getResources().getString(R.string.Qrcode_result));
                }

                for (ModuleAssignmentBean bean : loadingcarbean) {
                    bean.hNumbe = headerProperties.get("receiptHnumber");
                    bean.requireDate = headerProperties.get("requireDate");
                    bean.description = headerProperties.get("description");
                    bean.entryPeople = headerProperties.get("enterPeople");
                }

                //清空 二维码为空的
                for (ModuleAssignmentBean receBean : comTaskBeans) {
                    if (TextUtils.isEmpty(receBean.Qrcode)) {
                        comTaskBeans.remove(receBean);
                    }
                }
                if (!(loadingcarbean.size() == 0)) {
                    comTaskBeans.addAll(loadingcarbean);
                }
                ArrayList<ModuleAssignmentBean> clone = (ArrayList<ModuleAssignmentBean>) comTaskBeans.clone();
                comTaskBeans.clear();
                comTaskBeans.addAll(removeDupliById(clone));
                ClearAssignMentSectionArrayList();
                moduleAssignmentSectionAdapter.notifyDataSetChanged();
            }

            @Override
            public void ErrorBack(String error) {

            }
        }, orderId, componentQr);
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
            T.showShort(this, "分录为空,不能提交");
            return;
        }
        for (int i=0;i<comTaskBeans.size();i++) {
            if (TextUtils.isEmpty(this.comTaskBeans.get(i).getModuleID())) {
                T.showShort(this, "第"+(i+1)+"行的模具为空不能提交");
                return;
            }
        }
        int size = this.comTaskBeans.size();
        String[][] billtable = null;
        billtable = new String[size][15];
        for (int i = 0; i < size; i++) {
            ModuleAssignmentBean confirmationReceBean = comTaskBeans.get(i);

            billtable[i][0] = confirmationReceBean.hNumbe;
            billtable[i][1] = App.menname;
            billtable[i][2] = confirmationReceBean.orderID;
            billtable[i][3] = confirmationReceBean.materialID;
            billtable[i][4] = confirmationReceBean.Qrcode;
            billtable[i][5] = confirmationReceBean.materialCoding;
            billtable[i][6] = confirmationReceBean.materialName;
            billtable[i][7] = confirmationReceBean.specification;
            billtable[i][8] = confirmationReceBean.measureUnitID;
            billtable[i][9] = confirmationReceBean.measureUnit;
            billtable[i][10] = confirmationReceBean.moduleID;
            billtable[i][11] = confirmationReceBean.startTime;
            billtable[i][12] = confirmationReceBean.endTime;
            billtable[i][13] = confirmationReceBean.number + "";
            billtable[i][14] = confirmationReceBean.sourceID;
        }
        if (mStatus.isNewStatus()) {

            ApiWebService.Get_Production_order_Mould_Add(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    T.showShort(ModuleAssignMentContentMessageActivity.this, result);

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
            ApiWebService.Get_Production_order_Mould_Eedit(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    T.showShort(ModuleAssignMentContentMessageActivity.this, result);
                    if (!result.contains("失败")) {
//                        onBackPressed();
//                        sureDataRefresh("tvSubmitAction");
                    }
                }

                @Override
                public void ErrorBack(String error) {

                }
            }, billtable,HId);
        }
    }

    @Override
    protected void tvCheckAction(TextView tvCheck) {

        ApiWebService.Get_Production_order_Mould_sh(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(ModuleAssignMentContentMessageActivity.this, result);
//                onBackPressed();
//                sureDataRefresh("tvCheckAction");
            }

            @Override
            public void ErrorBack(String error) {

            }
        }, comTaskBeans.get(0).getID());
    }

    @Override
    protected void tvFanCheckAction(TextView tvFanCheck) {
        ApiWebService.Get_Production_order_Mould_shf(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(ModuleAssignMentContentMessageActivity.this, result);
//                onBackPressed();
//                sureDataRefresh("tvFanCheckAction");
            }

            @Override
            public void ErrorBack(String error) {

            }
        }, comTaskBeans.get(0).getID());
    }

    @Override
    protected void tvDeleteAction(TextView tvDelete) {
        ApiWebService.Get_Production_order_Mould_Del(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(ModuleAssignMentContentMessageActivity.this, result);
                onBackPressed();
                sureDataRefresh("tvDeleteAction");
            }

            @Override
            public void ErrorBack(String error) {

            }
        }, comTaskBeans.get(0).ID);
    }

    protected void sureDataRefresh(String type) {
        EventBus.getDefault().post("刷新" + type + "数据");
    }


    public static class DataBean {
        public String wuLiao;
        public String countUnit;
        public String count;
    }


    @OptionsItem
    protected final void action_operat_status() {
        Observable<Object> objectObservable = Observable.create(subscriber -> {
            if (!ClickUtils.isFastDoubleClickTime(1000))
            show1(mStatus);
            setTvQrcodeContext("智能分配模具");
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


