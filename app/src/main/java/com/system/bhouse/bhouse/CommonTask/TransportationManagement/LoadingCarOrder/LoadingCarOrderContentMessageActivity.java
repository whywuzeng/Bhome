package com.system.bhouse.bhouse.CommonTask.TransportationManagement.LoadingCarOrder;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.system.bhouse.Custom.ShowDeviceMessageCustomDialog;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.base.CheckStatusBeanImpl;
import com.system.bhouse.base.StatusBean;
import com.system.bhouse.base.SubmitStatusBeanImpl;
import com.system.bhouse.bean.BProBOM;
import com.system.bhouse.bean.LoadingCarBean;
import com.system.bhouse.bhouse.CommonTask.BaseTaskFragment.BaseContentMessageActivity;
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
import com.system.bhouse.ui.sectioned.SectionedRecyclerViewAdapter;
import com.system.bhouse.utils.TenUtils.L;
import com.system.bhouse.utils.TenUtils.T;
import com.system.bhouse.utils.ValueUtils;
import com.system.bhouse.utils.custom.CustomToast;

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

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2018-03-05.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask
 */
@EActivity(R.layout.activity_comtask_content_layout)
@OptionsMenu(R.menu.menu_comtask)
public class LoadingCarOrderContentMessageActivity extends BaseContentMessageActivity implements LoadingCarOrderContentItemSection.OnItemClickListener, GroupItem.onChildItemClickListener, onMutiDataSetListener, LoadingCarOrderContentItemSection.onCBItemClickListener {

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

    private MyTaskContentAdapter mRecyclerViewAdapter;
    private ArrayList<LoadingCarBean> comTaskBeans = new ArrayList<>();
    private TreeRecyclerAdapter treeRecyclerAdapter;
    private boolean isDeleteAble = true;
    public static final int RESULT_SORTITEM_SELECTPROJECT = 1001;
    public static final int REQUST_QRCODE = 1008;

    private String[] strTypes = {"吊装需求-项目选择", "吊装需求-栋选择", "吊装需求-层选择"};


    private LoadingCarOrderContentItemSection workflowSection;
    private Dialog bottomDialog;
    private String STATE_COMTASK = "state_comtask";
    private HashMap<String, String> headerProperties = new HashMap<>();
    private CheckBox posBox;

    @AfterViews
    public void initComTaskActivity() {
        if (mStatus.isNewStatus()) {
            setActionBarMidlleTitle("新增装车订单");
            initCheckBox();
        }
        else {
            setActionBarMidlleTitle("装车订单");
        }
        tv_title_live_layout.setText("装车订单分录");

        mRecyclerViewAdapter = new MyTaskContentAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        GridLayoutManager manager = new GridLayoutManager(this, 1);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mRecyclerViewAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 0;
                    default:
                        return 1;
                }
            }
        });


        listView.setLayoutManager(linearLayoutManager);

        workflowSection = new LoadingCarOrderContentItemSection(comTaskBeans, mStatus);
        String[] stringArray = getResources().getStringArray(R.array.loadingcar_itemsection_order);
        workflowSection.setTVIDContent(stringArray);
        new ItemTouchHelper(new ComTaskContentItemSectionItemTouchHelper(mRecyclerViewAdapter)).attachToRecyclerView(listView);
        workflowSection.setOnItemClickListener(this);
        workflowSection.setmOnCBItemClickListener(this);

        mRecyclerViewAdapter.addSection(workflowSection);
        listView.setNestedScrollingEnabled(false);
        listView.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewAdapter.notifyDataSetChanged();

        testData();
        setScrollViewFirst();

        setOnBackPressedListener(new setOnBackPressedListener() {
            @Override
            public void onBackPressedListener() {
                sureDataRefresh("tvDeleteAction");
            }
        });
    }

    //处理单选框方法
    private void initCheckBox() {
        //生产外包
        posBox = findViewById(R.id.cb_produce_pos);
        posBox.setVisibility(View.VISIBLE);
        //取消外包
        CheckBox nevBox = findViewById(R.id.cb_produce_nev);

        posBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!posBox.isChecked()) {
                    for (LoadingCarBean bean :
                            comTaskBeans) {
                        if (bean.isIsProduce()) {
                            bean.Is_OutPur = false;
                        }
                    }
                }
                else if (posBox.isChecked()) {
                    for (LoadingCarBean bean :
                            comTaskBeans) {
                        if (bean.isIsProduce()) {
                            bean.Is_OutPur = true;
                        }
                    }
                }
                ArrayList<LoadingCarBean> clone = (ArrayList<LoadingCarBean>) comTaskBeans.clone();
                comTaskBeans.clear();
                comTaskBeans.addAll(clone);
                mRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

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

//    ID = "1ea66b7bb9674a18a8794bd943c212bb"
//    Specification = "测试" 规格型号
//    amount = 30            需求数量
//    ceng = "2"             层
//    childTableID = "599d49d6ad2c4eabb43c7314cd68a467"
//    description = ""
//    dong = "1"
//    enterPeople = "管理员"
//    entryTime = "2018/3/8 11:12:00"
//    goodsCoding = "1002.1084.0100.003"    物料编码
//    goodsID = "acb6fd62b2f0405292fe8c0de0737f2f"
//    goodsName = "PC构件"                  物料名称
//    hNumbe = "DZXQ-7-201803-0001"         单据编号
//    measure = "块"
//    measureID = "c8e082b5f5f34d5f934f071e6b464238"
//    projectName = "麓谷一期项目"
//    requireData = "2018/3/8 11:11:40"
//    status = "审核"

    /**
     * 初始布局  为列表布局
     * param comTaskBeans
     */
    private void TopListViewInit() {

        mRecyclerViewAdapter.removeAllSections();
        mRecyclerViewAdapter.addSection(workflowSection);
        mRecyclerViewAdapter.notifyDataSetChanged();

        LoadingCarBean comTaskBean1 = null;
        if (ValueUtils.IsFirstValueExist(comTaskBeans)) {
            comTaskBean1 = this.comTaskBeans.get(0);
        }else {
            comTaskBean1=new LoadingCarBean();
            comTaskBean1.setDisableDelete(true);
            this.comTaskBeans.add(comTaskBean1);
        }

        if (!TextUtils.isEmpty(receiptHnumber)) {
            comTaskBean1.hNumbe = receiptHnumber;
            if (getComtaskSize()) {
                for (LoadingCarBean bean : comTaskBeans) {
                    bean.hNumbe = receiptHnumber;
                }
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        String[] LETTERS = new String[]{"单据信息", "录入人信息", "审核人信息"};
        String[] key = {"4", "2", "2"};

        ArrayList<KeyType> keyTypes = new ArrayList<>();
        KeyType keyType = null;
        for (int i = 0; i < LETTERS.length; i++) {
            keyType = new KeyType();
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

//        if (mStatus.isNewStatus())
//        this.comTaskBeans.add(comTaskBean1);
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
    protected ArrayList<SortChildItem.ViewModel> makeChlidData(LoadingCarBean comTaskBean1, KeyType data, StatusBean mStatus) {
        ArrayList<SortChildItem.ViewModel> viewModels = new ArrayList<>();
        SortChildItem.ViewModel viewModel;
        if (data.key == 4) {

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "单据编号";
            viewModel.value = TextUtils.isEmpty(receiptHnumber) ? App.receiptHnumber : receiptHnumber;
            viewModel.key = "receiptHnumber";
            viewModel.isClick = false;
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key, viewModel.value);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "车次";
            viewModel.value = this.comTaskBeans.get(0).getCartrips();
            viewModel.key = "cartrips" + SortChildItem.NoQRcode;
            if (mStatus.isNewStatus() || mStatus.isModifyStatus()) {
                viewModel.isClick = true;
            }
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key, viewModel.value);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "货柜";
            viewModel.value = this.comTaskBeans.get(0).getContainerName();
            viewModel.key = "containerName" + SortChildItem.NoQRcode;
            if (mStatus.isNewStatus() || mStatus.isModifyStatus()) {
                viewModel.isClick = true;
            }
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key, viewModel.value);
            headerProperties.put("containerID", this.comTaskBeans.get(0).getContainerID());

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "业务日期";
            viewModel.value = this.comTaskBeans.get(0).getRequireDate();
            viewModel.key = "requireDate";
            if (mStatus.isNewStatus() || mStatus.isModifyStatus()) {
                viewModel.isClick = true;
            }
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key, viewModel.value);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "装柜日期";
            viewModel.value = this.comTaskBeans.get(0).getLoadingContainerDate();
            viewModel.key = "LoadingContainerDate";
            if (mStatus.isNewStatus() || mStatus.isModifyStatus()) {
                viewModel.isClick = true;
            }
            viewModels.add(viewModel);
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
            viewModel.name = "描述";
            viewModel.value = this.comTaskBeans.get(0).getDescription();
            viewModel.key = "description";
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

        if (data.name.equals("车次")) {

            ApiWebService.Get_Sale_Order_Car_listJson(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    ArrayList<BProBOM> bProBOMs = App.getAppGson().fromJson(result, new TypeToken<List<BProBOM>>() {
                    }.getType());

                    CommonPickerActivity_.intent(LoadingCarOrderContentMessageActivity.this).title("车次").bProBOMs(bProBOMs).Position(holder.getAdapterPosition()).LayoutID(R.layout.commonpicker_item).startForResult(RESULT_SORTITEM_SELECTPROJECT);

                }

                @Override
                public void ErrorBack(String error) {

                }
            }, 9999, "");


        }
        else if (data.name.equals("货柜")) {
            ApiWebService.Get_Sale_Order_Container_listJson(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    ArrayList<BProBOM> bProBOMs = App.getAppGson().fromJson(result, new TypeToken<List<BProBOM>>() {
                    }.getType());

                    CommonPickerActivity_.intent(LoadingCarOrderContentMessageActivity.this).title("货柜").bProBOMs(bProBOMs).Position(holder.getAdapterPosition()).LayoutID(R.layout.commonpicker_item).startForResult(RESULT_SORTITEM_SELECTPROJECT);

                }

                @Override
                public void ErrorBack(String error) {

                }
            }, 9999, "", comTaskBeans.get(0).getLoadingContainerDate());
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
        else if (data.name.equals("装柜日期")) {
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
            ShowDeviceMessageCustomDialog.Builder builder = new ShowDeviceMessageCustomDialog.Builder(this);
            builder.setMessage("您的描述是:");
            builder.setTitle("提示");
            builder.setEdittextcontent(data.value);
            int adapterPosition = holder.getAdapterPosition();

            View inflate = LayoutInflater.from(this).inflate(R.layout.beizhu_edittext, null, false);
            builder.setContentView(inflate);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    EditText viewEditText = (EditText) inflate.findViewById(R.id.edt_domian);
                    if (viewEditText.getText() != null) {
                        String text = viewEditText.getText().toString();
                        if (TextUtils.isEmpty(text)) {
                            T.showfunShort(LoadingCarOrderContentMessageActivity.this, "备注不能为空");
                        }
                        else {
                            getDateRefresh(text, adapterPosition, data.name);
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
    }

    //0ca7f7e080ea4f3eb1c3bb242db34fd4  //扫描回调 带出信息
    @OnActivityResult(REQUST_QRCODE)
    void resultGetRequstQrcode(int result, Intent data) {
        if (result == RESULT_OK) {
            Bundle bundle = data.getBundleExtra("bundle");
            String resultQr = bundle.getString("result");
            int extraPosition = bundle.getInt("position");

        }
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
            for (LoadingCarBean receBean : comTaskBeans) {
                receBean.requireDate = viewModel.value;
            }
        }
        else if (viewModel.name.equals(typestring) && typestring.equals("装柜日期")) {
            if (viewModel.value == null || !viewModel.value.equals(date))
                viewModel.value = date;
            for (LoadingCarBean receBean : comTaskBeans) {
                receBean.LoadingContainerDate = viewModel.value;
            }
        }
        else if (viewModel.name.equals("货柜")) {
            if (viewModel.value == null || !viewModel.value.equals(date))
                viewModel.value = date;

            for (LoadingCarBean receBean : comTaskBeans) {
                receBean.containerName = viewModel.value;
            }
        }
        else if (viewModel.name.equals("描述")) {
            if (viewModel.value == null || !viewModel.value.equals(date))
                viewModel.value = date;

            for (int i = 0; i < comTaskBeans.size(); i++) {
                comTaskBeans.get(i).description = viewModel.value;
            }
        }
        else if (viewModel.name.equals("车次")) {
            if (viewModel.value == null || !viewModel.value.equals(date))
                viewModel.value = date;
            for (LoadingCarBean receBean : comTaskBeans) {
                receBean.cartrips = viewModel.value;
            }
        }
        headerProperties.put(viewModel.key, viewModel.value);
        treeItem.setData(viewModel);
        treeRecyclerAdapter.notifyDataSetChanged();
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
            String title = data.getStringExtra("title");
            if (extraCoding.contains("DZXQ") || extraCoding.contains("BHXQ")) {
                getEntriesData(extra);
                return;
            }
            if (extrasName != null && extrasName.contains("货柜")) {
                for (LoadingCarBean receBean : comTaskBeans) {
                    receBean.containerID = extra;
                }
                headerProperties.put("containerID", extra);
            }
            getDateRefresh(extraCoding, extraPosition, title);

        }
    }

    //得到分录的数据
    private void getEntriesData(String extra) {
        ApiWebService.Get_Sale_Order_Hois_Req_Json(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {

                ArrayList<LoadingCarBean> loadingcarbean = App.getAppGson().fromJson(result, new TypeToken<List<LoadingCarBean>>() {
                }.getType());

                for (LoadingCarBean bean : loadingcarbean) {
                    bean.hNumbe = headerProperties.get("receiptHnumber");
                    bean.cartrips = headerProperties.get("cartripsNoQRcode");
                    bean.containerName = headerProperties.get("containerNameNoQRcode");
                    bean.containerID = headerProperties.get("containerID");
                    bean.requireDate = headerProperties.get("requireDate");
                    bean.LoadingContainerDate = headerProperties.get("LoadingContainerDate");
                    bean.description = headerProperties.get("description");
                    bean.entryPeople = headerProperties.get("enterPeople");
                }
                comTaskBeans.clear();
                comTaskBeans.addAll(loadingcarbean);
                mRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void ErrorBack(String error) {

            }
        }, extra);
    }

//    public static ArrayList<LoadingCarBean> removeDupliById(List<LoadingCarBean> persons) {
////        Set<LoadingCarBean> personSet = new TreeSet<>((o1, o2) -> o1.getQrcode().equals(o2.getQrcode()));
//        Set<LoadingCarBean> personSet = new TreeSet<>((o1, o2) -> {
//            if (o1.getQrcode().equals(o2.getQrcode()))
//            {
//                return 0;
//            }else {
//                return 1;
//            }
//        });
//        personSet.addAll(persons);
//        return new ArrayList<>(personSet);
//    }

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

    //checkbox 是否外包的点击回调
    @Override
    public void onItemClick(View view, int position) {
        if (!comTaskBeans.get(position).isIsProduce())
        {
            CustomToast.showDefault("没有参与生产", Toast.LENGTH_SHORT);
            return;
        }
        if (((CheckBox) view).isChecked()) {
            comTaskBeans.get(position).Is_OutPur = true;
        }
        else {
            comTaskBeans.get(position).Is_OutPur = false;
        }
        posBox.setChecked(true);
        for (LoadingCarBean bean:comTaskBeans) {
            if (!bean.Is_OutPur)
            {
                posBox.setChecked(false);
            }
        }
    }

    public static class KeyType {
        public int key;
        public String type;
    }


    /**
     * 请求数据
     */
    private void testData() {
        comTaskBeans.clear();
        ApiWebService.Get_Sale_OrderView_Json(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                ArrayList<LoadingCarBean> tomTaskBeans = App.getAppGson().fromJson(result, new TypeToken<List<LoadingCarBean>>() {
                }.getType());
                comTaskBeans.addAll(tomTaskBeans);
                mRecyclerViewAdapter.notifyDataSetChanged();
                ifStateForOrderId();
                TopListViewInit();
            }

            @Override
            public void ErrorBack(String error) {

            }
        }, HId);
        //上个result id 值
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
        if (comTaskBeans.get(0).getStatus().equals(Const.SUBMIT_STATUS)) {
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




    @Override
    public void onItemClick(View view, View textView, int position) {

    }

    private int ikey = 0;

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
//            //网上说这个方式刷新 position正确
//            mRecyclerViewAdapter.notifyItemRangeChanged(0,comTaskBeans.size());//刷新被删除数据，以及其后面的数据
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRecyclerViewAdapter.notifyDataSetChanged();
                }
            }, 200);
        }
    }


    //点击增加的按钮
    @Override
    public void onImgItemAdd(View view, int position, RecyclerView.ViewHolder holder) {
        if (comTaskBeans == null)
            return;
        LoadingCarBean dataBean = new LoadingCarBean();
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

    //吊装需求拉取 点击事件
    @Override
    protected void tvQrcodeAction(TextView tvQrcode) {
        tvQrcode.setText("吊装需求拉取");
        ApiWebService.Get_Sale_Order_Hois_Req_listJson(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {

                ArrayList<BProBOM> bProBOMs = App.getAppGson().fromJson(result, new TypeToken<List<BProBOM>>() {
                }.getType());

                CommonPickerActivity_.intent(LoadingCarOrderContentMessageActivity.this).title("吊装需求拉取").bProBOMs(bProBOMs).Position(0).LayoutID(R.layout.loadcar_order_picker_item).startForResult(RESULT_SORTITEM_SELECTPROJECT);
            }

            @Override
            public void ErrorBack(String error) {

            }
        }, 9999, "");
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
    protected void tvSubmitActionforList(TextView tvSubmit){
        if (!getComtaskSize()) {
            T.showShort(this, "数据有误,不能提交");
            return;
        }
        if (TextUtils.isEmpty(this.headerProperties.get("cartripsNoQRcode"))) {
            T.showShort(this, "车次为空不能提交");
            return;
        }
        if (TextUtils.isEmpty(this.headerProperties.get("containerNameNoQRcode"))) {
            T.showShort(this, "货柜为空不能提交");
            return;
        }
//        if (TimeUtils.string2Millis(this.comTaskBeans.get(0).requireDate)<TimeUtils.string2Millis(this.comTaskBeans.get(0).getLoadingContainerDate()))
//        {
//            T.showShort(this, "业务日期不能小于装柜日期");
//            return;
//        }
        if (TextUtils.isEmpty(this.comTaskBeans.get(0).Qrcode)) {
            T.showShort(this, "分录为空不能提交");
            return;
        }
        int size = this.comTaskBeans.size();
        String[][] billtable = null;
        billtable = new String[size][22];
        for (int i = 0; i < size; i++) {
            LoadingCarBean confirmationReceBean = comTaskBeans.get(i);

            billtable[i][0] = confirmationReceBean.hNumbe;
            billtable[i][1] = confirmationReceBean.projectID;
            billtable[i][2] = confirmationReceBean.dongID;
            billtable[i][3] = confirmationReceBean.cengID;
            billtable[i][4] = confirmationReceBean.cartrips;
            billtable[i][5] = confirmationReceBean.description;
            billtable[i][6] = confirmationReceBean.requireDate;
            billtable[i][7] = confirmationReceBean.LoadingContainerDate;
            billtable[i][8] = confirmationReceBean.containerID;
            billtable[i][9] = confirmationReceBean.entryPeople;
            billtable[i][10] = confirmationReceBean.Qrcode;
            billtable[i][11] = confirmationReceBean.materialsID;
            billtable[i][12] = confirmationReceBean.materialsNumber;
            billtable[i][13] = confirmationReceBean.materialsNames;
            billtable[i][14] = confirmationReceBean.Specification;
            billtable[i][15] = confirmationReceBean.measureUnitID;
            billtable[i][16] = confirmationReceBean.measureUnit;
            billtable[i][17] = confirmationReceBean.amount + "";
            billtable[i][18] = confirmationReceBean.sourceTableID;
            billtable[i][19] = confirmationReceBean.sourceType;
            billtable[i][20] = confirmationReceBean.isIsProduce() + "";
            billtable[i][21] = confirmationReceBean.Is_OutPur+ "";

        }
        if (mStatus.isNewStatus()) {

            ApiWebService.Get_Sale_Order_Add(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    T.showShort(LoadingCarOrderContentMessageActivity.this, result);

                    if (!result.contains("失败")) {
                        onBackPressed();
                        sureDataRefresh("tvSubmitAction");
                    }
                }

                @Override
                public void ErrorBack(String error) {

                }
            }, billtable);
        }
        else if (mStatus.isModifyStatus()) {
            ApiWebService.Get_Sale_Order_Eedit(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    T.showShort(LoadingCarOrderContentMessageActivity.this, result);
                    if (!result.contains("失败")) {
                      testData();
                    }
                }

                @Override
                public void ErrorBack(String error) {

                }
            }, billtable, HId);
        }
    }

    @Override
    protected void tvCheckAction(TextView tvCheck){

        ApiWebService.Get_Sale_Order_sh(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(LoadingCarOrderContentMessageActivity.this, result);
                testData();
            }

            @Override
            public void ErrorBack(String error) {

            }
        }, comTaskBeans.get(0).getID(), App.USER_INFO);
    }
    @Override
    protected  void tvModifyAction(TextView tvModify){
        mStatus.setBean(new SubmitStatusBeanImpl().setVisSubmitBtn(true).setVisQRBtn(true));
        //保证提交 不能有修改。不然这里会出问题
        mStatus.setLookStatus(true);
        mStatus.setModifyStatus(true);
        if (mStatus.isModifyStatus()) {
            setActionBarMidlleTitle("修改装车订单");
            TopListViewInit();
            initCheckBox();

            workflowSection = new LoadingCarOrderContentItemSection(comTaskBeans, mStatus);
            String[] stringArray = getResources().getStringArray(R.array.loadingcar_itemsection_order);
            workflowSection.setTVIDContent(stringArray);
            workflowSection.setOnItemClickListener(this);
            workflowSection.setmOnCBItemClickListener(this);
            mRecyclerViewAdapter.removeAllSections();
            mRecyclerViewAdapter.addSection(workflowSection);

            mRecyclerViewAdapter.notifyDataSetChanged();
            bottomDialog.dismiss();
        }
    }

    @Override
    protected void tvFanCheckAction(TextView tvFanCheck){
        ApiWebService.Get_Sale_Order_shf(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(LoadingCarOrderContentMessageActivity.this, result);
                testData();
            }

            @Override
            public void ErrorBack(String error) {

            }
        }, comTaskBeans.get(0).getID(), App.USER_INFO);
    }

    @Override
    protected void tvDeleteAction(TextView tvDelete){
        ApiWebService.Get_Sale_Order_Del(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(LoadingCarOrderContentMessageActivity.this, result);
                onBackPressed();
                sureDataRefresh("tvDeleteAction");
            }

            @Override
            public void ErrorBack(String error) {

            }
        }, comTaskBeans.get(0).getID(), App.USER_INFO);
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
            show1(mStatus);
        setTvQrcodeContext("吊装需求匹配");
    }

}


