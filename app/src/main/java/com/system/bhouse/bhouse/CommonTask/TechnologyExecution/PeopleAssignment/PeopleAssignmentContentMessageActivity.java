package com.system.bhouse.bhouse.CommonTask.TechnologyExecution.PeopleAssignment;

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
import com.system.bhouse.base.CheckStatusBeanImpl;
import com.system.bhouse.base.StatusBean;
import com.system.bhouse.base.SubmitStatusBeanImpl;
import com.system.bhouse.bean.BProBOM;
import com.system.bhouse.bhouse.CommonTask.BaseTaskFragment.BaseContentMessageActivity;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.PeopleAssignment.Bean.PeopleAssignmentBean;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.PeopleAssignment.Bean.PeopleAssignmentBeanSection;
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
 *     人工分配界面
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask
 */
@EActivity(R.layout.activity_comtask_content_layout)
@OptionsMenu(R.menu.menu_comtask)
public class PeopleAssignmentContentMessageActivity extends BaseContentMessageActivity implements  GroupItem.onChildItemClickListener, onMutiDataSetListener ,BaseQuickAdapter.OnItemChildClickListener{

    public static final String TAG = "comtaskcontentmessageactivity";
    private static final String module_name = "人工分配";


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

    private ArrayList<PeopleAssignmentBean> comTaskBeans = new ArrayList<>();
    private ArrayList<PeopleAssignmentBeanSection> assignmentBeanSectionArrayList=new ArrayList<>() ;
    private TreeRecyclerAdapter treeRecyclerAdapter;
    private boolean isDeleteAble = true;
    public static final int RESULT_SORTITEM_SELECTPROJECT = 1001;
    public static final int REQUST_QRCODE = 1008;

    private String STATE_COMTASK = "state_comtask";
    private HashMap<String,String> headerProperties=new HashMap<>();
    private PeopleAssignmentSectionAdapter peopleAssignmentSectionAdapter;
    private ArrayList<SortChildItem.ViewModel> TopViewHolders;

    @AfterViews
    public void initComTaskActivity() {
        tv_title_live_layout.setText(module_name + "分录");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(linearLayoutManager);
        recycleViewAddEmptySection(listView);
        String[] stringArray = getResources().getStringArray(R.array.people_itemsection_order);

        peopleAssignmentSectionAdapter = new PeopleAssignmentSectionAdapter(R.layout.activity_comtask_content_layout_item, R.layout.layout_home_recommend_empty_noheight, R.layout.comtask_content_item_footer,assignmentBeanSectionArrayList,stringArray,mStatus);

        listView.setNestedScrollingEnabled(false);

        listView.setAdapter(peopleAssignmentSectionAdapter);
        listView.addItemDecoration(new TimeLineItemTopBottomDecoration());
        peopleAssignmentSectionAdapter.setOnItemChildClickListener(this);

        testData();

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

    private void initStatusData(){

        //得到初始数据，处理单据状态
        if (mStatus.isNewStatus()) {
            setActionBarMidlleTitle("新增" + module_name);
            BottomAction();
        }
        else {
            setActionBarMidlleTitle(module_name);
        }
        //查看 状态 也会传值 receiptHnumber  但是comTaskBeans 保存状态也有值
        if (!TextUtils.isEmpty(receiptHnumber)) {
            if (getComtaskSize()&&comTaskBeans.get(0).hNumbe.isEmpty()) {
                for (PeopleAssignmentBean bean : comTaskBeans) {
                    bean.hNumbe = receiptHnumber;
                }
            }else if (ValueUtils.IsFirstValueExist(this.comTaskBeans)){
                receiptHnumber=this.comTaskBeans.get(0).hNumbe;
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

        ArrayList<PeopleAssignmentContentMessageActivity.KeyType> keyTypes = new ArrayList<>();
        PeopleAssignmentContentMessageActivity.KeyType keyType = null;
        for (int i = 0; i < Const.LETTERS.length; i++) {
            keyType = new PeopleAssignmentContentMessageActivity.KeyType();
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
        if (comTaskBeans.get(0).getStatus().equals(Const.SAVE_STATUS)) {
            /**
             * 请求有数据,就是
             */
                //保存状态
                SubmitStatusBeanImpl submitStatusBean = new SubmitStatusBeanImpl();
                submitStatusBean.setVisCheckBtn(true).setVisDeleteBtn(true).setVisModifyBtn(true);
                mStatus.setBean(submitStatusBean);
                mStatus.setLookStatus(true);
                return;
        }else if (comTaskBeans.get(0).getStatus().equals(Const.CHECK_STATUS))
        {
                //审核状态
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
    protected ArrayList<SortChildItem.ViewModel> makeChlidData(PeopleAssignmentContentMessageActivity.KeyType data, StatusBean mStatus) {
        ArrayList<SortChildItem.ViewModel> viewModels = new ArrayList<>();
        SortChildItem.ViewModel viewModel=null;
        if (data.key == 4) {

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "单据编号";
            viewModel.value = TextUtils.isEmpty(receiptHnumber) ? App.receiptHnumber : receiptHnumber;
            viewModel.key = "receiptHnumber";
            viewModel.isClick = false;
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key,viewModel.value);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "构件";
            viewModel.value = this.comTaskBeans.get(0).Qrcode;
            viewModel.key = "Qrcode";
            viewModel.isClick = false;
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key,viewModel.value);


            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "工序";
            viewModel.value = this.comTaskBeans.get(0).getProcessName();
            viewModel.key = "processName";
            viewModel.isClick = false;
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key,viewModel.value);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "计划开始日期";
            viewModel.value = this.comTaskBeans.get(0).plannedStartDate;
            viewModel.key = "plannedStartDate";
            viewModel.isClick = false;
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key,viewModel.value);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "计划结束日期";
            viewModel.value = this.comTaskBeans.get(0).plannedEndDate;
            viewModel.key = "plannedEndDate";
            viewModel.isClick = false;
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key,viewModel.value);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "实际结束日期";
            viewModel.value = this.comTaskBeans.get(0).actualEndTime+"";
            viewModel.key = "actualEndTime";
            viewModel.isClick = false;
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key,viewModel.value);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "定额人数";
            viewModel.value = this.comTaskBeans.get(0).quotaNumber+"";
            viewModel.key = "quotaNumber";
            viewModel.isClick = false;
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key,viewModel.value);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "实际人数";
            viewModel.value = this.comTaskBeans.get(0).actualNumber+"";
            viewModel.key = "actualNumber";
            viewModel.isClick = false;
            viewModels.add(viewModel);
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
            viewModel.name = "描述";
            viewModel.value = this.comTaskBeans.get(0).getDescription();
            viewModel.key = "description";
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
                        T.showfunShort(PeopleAssignmentContentMessageActivity.this, "备注不能为空");
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

                        ArrayList<PeopleAssignmentBean> loadingcarbean = App.getAppGson().fromJson(result, new TypeToken<List<PeopleAssignmentBean>>() {
                        }.getType());

                        if (loadingcarbean.isEmpty())
                        {
                            T.showShort(PeopleAssignmentContentMessageActivity.this,getResources().getString(R.string.Qrcode_result));
                        }

                        for (PeopleAssignmentBean bean : loadingcarbean) {
                            bean.hNumbe= headerProperties.get("receiptHnumber");
                            bean.requireDate= headerProperties.get("requireDate");
                            bean.description=headerProperties.get("description");
                            bean.entryPeople= headerProperties.get("enterPeople");
                        }

                        //清空 二维码为空的
                        for (PeopleAssignmentBean receBean : comTaskBeans) {
                            if (TextUtils.isEmpty(receBean.Qrcode)) {
                                comTaskBeans.remove(receBean);
                            }
                        }
                        if (!(loadingcarbean.size() == 0)) {
                            comTaskBeans.addAll(loadingcarbean);
                        }
                        ArrayList<PeopleAssignmentBean> clone =(ArrayList<PeopleAssignmentBean>)comTaskBeans.clone();
                        comTaskBeans.clear();
                        comTaskBeans.addAll(removeDupliById(clone));
                        ClearAssignMentSectionArrayList();
                        peopleAssignmentSectionAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void ErrorBack(String error) {

                    }
                },HId,resultQr);
            }
        }
    }

    /**
     * pickActivity 结束回调  职员信息回调
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

            int i=0;
            for (PeopleAssignmentBean bean :comTaskBeans)
            {
                if (extraBOMID.equals(String.valueOf(bean.getStaffID())))
                {
                    showMiddleToast("此职员已选择");
                    return;
                }
            }

            comTaskBeans.get(extraPosition).setStaffCoding(extraCoding);
            comTaskBeans.get(extraPosition).setStaffName(extrasName);
            comTaskBeans.get(extraPosition).setStaffID(Integer.valueOf(extraBOMID));

            for (PeopleAssignmentBean bean :comTaskBeans)
            {
                if (bean.getStaffID()>0)
                {
                    i++;
                    getDateRefresh(String.valueOf(i),8,"实际人数");
                }
            }

            ClearAssignMentSectionArrayList();
            peopleAssignmentSectionAdapter.notifyItemChanged(extraPosition);
        }
    }

    public static ArrayList<PeopleAssignmentBean> removeDupliById(List<PeopleAssignmentBean> persons) {
        Set<PeopleAssignmentBean> personSet = new TreeSet<>((o1, o2) -> {
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
            for (PeopleAssignmentBean receBean : comTaskBeans) {
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
        else if (viewModel.name.equals("实际人数")) {
            if (viewModel.value==null||!viewModel.value.equals(date))
                viewModel.value = date;

            for (int i = 0; i < comTaskBeans.size(); i++) {
                comTaskBeans.get(i).actualNumber = Integer.valueOf(viewModel.value);
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

                getStaffData(position);

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

                CommonPickerActivity_.intent(PeopleAssignmentContentMessageActivity.this).title("职员信息").bProBOMs(bProBOMs).Position(position).LayoutID(R.layout.commonpicker_item).startForResult(RESULT_SORTITEM_SELECTPROJECT);

            }

            @Override
            public void ErrorBack(String error) {

            }
        }, 9999, "");
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
        ApiWebService.Get_Production_order_PerView_Json_poidprid_QR_Code(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                ArrayList<PeopleAssignmentBean> tomTaskBeans = App.getAppGson().fromJson(result, new TypeToken<List<PeopleAssignmentBean>>() {
                }.getType());
                //为空就创建一个新的空对象
                if (tomTaskBeans.isEmpty()) {
                    //为头recycleView 设置空数据
                    comTaskBeans.add(new PeopleAssignmentBean());
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
                peopleAssignmentSectionAdapter.notifyDataSetChanged();
            }

            @Override
            public void ErrorBack(String error) {
                //设置error字段
                setErrorViewContext(error);
                //设置Empty  ErrorView
                peopleAssignmentSectionAdapter.setEmptyView(errorView);
            }
        },orderId,componentQr,workOrderID);
    }

    private void CleartreeRecyclerAdapter(){
        List<TreeItem> datas = treeRecyclerAdapter.getDatas();
        KeyType keyType = new KeyType();
//        for (TreeItem treeItem:datas)
//        {
//            if (treeItem instanceof GroupItem){
//                if (((GroupItem)treeItem).TitleKey.equals(LETTERS[0]))
//                {
//                    keyType.key=4;
//                    ((ArrayList<SortChildItem.ViewModel>)datas.get(0).getData()).addAll(makeChlidData(keyType,mStatus));
//                }else if (((GroupItem)treeItem).TitleKey.equals(LETTERS[1]))
//                {
//                    keyType.key=2;
//                    keyType.type=LETTERS[1];
//                    ((ArrayList<SortChildItem.ViewModel>)datas.get(12).getData()).addAll(makeChlidData(keyType,mStatus));
//                }else if (((GroupItem)treeItem).TitleKey.equals(LETTERS[2]))
//                {
//                    keyType.key=2;
//                    keyType.type=LETTERS[2];
//                    ((ArrayList<SortChildItem.ViewModel>)datas.get(15).getData()).addAll(makeChlidData(keyType,mStatus));
//                }
//            }
//        }

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
        for(PeopleAssignmentBean bean : comTaskBeans)
        {
            assignmentBeanSectionArrayList.add(new PeopleAssignmentBeanSection(bean));
        }
        assignmentBeanSectionArrayList.add(new PeopleAssignmentBeanSection(false,"",false,true,""));
    }


    private int ikey = 0;

    public void onItemClick(View view, View textView, int position) {
//        ComponentIntoWareHouseBean searchHistroyBeans = workflowSection.getSearchHistroyBeans(position);
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
            peopleAssignmentSectionAdapter.notifyItemRemoved(position-1);
            peopleAssignmentSectionAdapter.notifyDataSetChanged();
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
        PeopleAssignmentBean bean;
        bean=comTaskBeans.get(0);

        PeopleAssignmentBean bean1 = new PeopleAssignmentBean();
        bean1.sethNumbe(bean.gethNumbe());
        bean1.setOrderId(bean.getOrderId());
        bean1.setQrcode(bean.getQrcode());
        bean1.setProcessResourceId(bean.getProcessResourceId());
        bean1.setProcessName(bean.getProcessName());
        bean1.setQuotaNumber(bean.getQuotaNumber());
        bean1.setActualNumber(bean.getActualNumber());
        bean1.setPlannedStartDate(bean.getPlannedStartDate());
        bean1.setPlannedEndDate(bean.getPlannedEndDate());
        bean1.setActualEndTime(bean.getActualEndTime());
        bean1.setDescription(bean.getDescription());

        comTaskBeans.add(bean1);

        if (!isVisBottom(listView)) {
            listView.smoothScrollToPosition(comTaskBeans.size() + 1);
        }

        ClearAssignMentSectionArrayList();
        peopleAssignmentSectionAdapter.notifyItemInserted(comTaskBeans.size());
        peopleAssignmentSectionAdapter.notifyDataSetChanged();
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
            peopleAssignmentSectionAdapter.notifyDataSetChanged();
            bottomDialog.dismiss();
        }
    }

    //智能分配模具
    @Override
    protected void tvQrcodeAction(TextView tvQrcode) {

        if (!getComtaskSize()) {
            T.showShort(this, Const.Entry_is_empty);
            return;
        }

//        ApiWebService.Get_Production_order_Mould_Mouldfp_Json(this, new ApiWebService.SuccessCall() {
//            @Override
//            public void SuccessBack(String result) {
//                //[{"模具ID":"11a5b5ccb081467ab80af1da6b05082f","模具名称":"PC构件003"}]
//
//                ArrayList<ComponentIntoWareHouseBean> loadingcarbean = App.getAppGson().fromJson(result, new TypeToken<List<ComponentIntoWareHouseBean>>() {
//                }.getType());
//
//                if (!ValueUtils.IsFirstValueExist(loadingcarbean))
//                    return;
//                comTaskBeans.get(0).setModuleID(loadingcarbean.get(0).moduleID);
//                comTaskBeans.get(0).setModuleName(loadingcarbean.get(0).moduleName);
//
//                ClearAssignMentSectionArrayList();
//                peopleAssignmentSectionAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void ErrorBack(String error) {
//
//            }
//        }, orderId, componentQr, comTaskBeans.get(0).materialID, comTaskBeans.get(0).startTime, comTaskBeans.get(0).endTime);

//        Intent intent = new Intent(ModuleAssignMentContentMessageActivity.this, CaptureActivity.class);
//        intent.putExtra("position", -1);
//        startActivityForResult(intent, REQUST_QRCODE);
    }

    /**
     * 访问下面recycleView数据
     */
    public void BottomAction(){

        ApiWebService.Get_Production_order_Per_bypoid_Json(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {

                ArrayList<PeopleAssignmentBean> loadingcarbean = App.getAppGson().fromJson(result, new TypeToken<List<PeopleAssignmentBean>>() {
                }.getType());

                if (loadingcarbean.isEmpty()) {
                    T.showShort(PeopleAssignmentContentMessageActivity.this, getResources().getString(R.string.Qrcode_result));
                    return;
                }

                for (PeopleAssignmentBean bean : loadingcarbean) {
                    bean.hNumbe = headerProperties.get("receiptHnumber");
                    bean.requireDate = headerProperties.get("requireDate");
                    bean.description = headerProperties.get("description");
                    bean.entryPeople = headerProperties.get("enterPeople");
                }

                //清空 二维码为空的
                for (PeopleAssignmentBean receBean : comTaskBeans) {
                    if (TextUtils.isEmpty(receBean.Qrcode)) {
                        comTaskBeans.remove(receBean);
                    }
                }
                if (!(loadingcarbean.size() == 0)) {
                    comTaskBeans.addAll(loadingcarbean);
                }
                ArrayList<PeopleAssignmentBean> clone = (ArrayList<PeopleAssignmentBean>) comTaskBeans.clone();
                comTaskBeans.clear();
                comTaskBeans.addAll(removeDupliById(clone));

                /**
                 * 刷新 toplistView数据
                 */
                CleartreeRecyclerAdapter();
//                TopListViewInit();
//                ClearAssignMentSectionArrayList();
//                peopleAssignmentSectionAdapter.notifyDataSetChanged();
            }

            @Override
            public void ErrorBack(String error) {

            }
        }, orderId, componentQr,workOrderID);

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
        billtable = new String[size][15];
        for (int i = 0; i < size; i++) {
            PeopleAssignmentBean confirmationReceBean = comTaskBeans.get(i);

            billtable[i][0] = confirmationReceBean.hNumbe;
            billtable[i][1] = App.menname;
            billtable[i][2] = confirmationReceBean.orderId;
            billtable[i][3] = confirmationReceBean.Qrcode;
            billtable[i][4] = confirmationReceBean.processResourceId;
            billtable[i][5] = confirmationReceBean.processName;
            billtable[i][6] = confirmationReceBean.quotaNumber+"";
            billtable[i][7] = confirmationReceBean.actualNumber+"";
            billtable[i][8] = confirmationReceBean.plannedStartDate;
            billtable[i][9] = confirmationReceBean.plannedEndDate;
            billtable[i][10] = confirmationReceBean.actualEndTime;
            billtable[i][11] = confirmationReceBean.description;
            billtable[i][12] = confirmationReceBean.StaffID+"";
            billtable[i][13] = confirmationReceBean.StaffCoding;
            billtable[i][14] = confirmationReceBean.StaffName;
        }
        if (mStatus.isNewStatus()) {

            ApiWebService.Get_Production_order_Per_Add(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    T.showShort(PeopleAssignmentContentMessageActivity.this, result);

                    if (!result.contains("失败")) {
//                        onBackPressed();
//                        sureDataRefresh("tvSubmitAction");
                        testData();
                    }
                }

                @Override
                public void ErrorBack(String error) {

                }
            }, billtable);
        }
        else if (mStatus.isModifyStatus()) {
            ApiWebService.Get_Production_order_Per_Eedit(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    T.showShort(PeopleAssignmentContentMessageActivity.this, result);
                    if (!result.contains("失败")) {
//                        onBackPressed();
//                        sureDataRefresh("tvSubmitAction");
                        testData();
                    }
                }

                @Override
                public void ErrorBack(String error) {

                }
            }, billtable,comTaskBeans.get(0).ID);
        }
    }

    @Override
    protected void tvCheckAction(TextView tvCheck) {

        ApiWebService.Get_Production_order_Per_sh(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(PeopleAssignmentContentMessageActivity.this, result);
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
        ApiWebService.Get_Production_order_Per_shf(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(PeopleAssignmentContentMessageActivity.this, result);
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
        ApiWebService.Get_Production_order_Per_Del(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(PeopleAssignmentContentMessageActivity.this, result);
                onBackPressed();
//                sureDataRefresh("tvDeleteAction");
                testData();
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
            if (!ClickUtils.isFastDoubleClick())
            show1(mStatus);
            setTvQrcodeContext("智能分配模具",View.GONE);
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


