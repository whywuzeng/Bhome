package com.system.bhouse.bhouse.CommonTask.TransportationManagement.ContainerRecycle;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import com.system.bhouse.bean.ContainerRecycleBean;
import com.system.bhouse.bean.wareHouseBean;
import com.system.bhouse.bhouse.CommonTask.BaseTaskFragment.BaseContentMessageActivity;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.TreeRecyclerAdapter;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.base.ViewHolder;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.item.GroupItem;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.item.SortChildItem;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.item.TreeItem;
import com.system.bhouse.bhouse.CommonTask.common.CommonDateTimePickerFragment;
import com.system.bhouse.bhouse.CommonTask.utils.ComTaskContentItemSectionItemTouchHelper;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.utils.LabelNumPickerDialog;
import com.system.bhouse.config.Const;
import com.system.bhouse.utils.ClickUtils;
import com.system.bhouse.utils.TenUtils.L;
import com.system.bhouse.utils.TenUtils.T;
import com.system.bhouse.utils.ValueUtils;
import com.system.bhouse.utils.custom.CustomToast;
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
public class ContainerRecycleContentMessageActivity extends BaseContentMessageActivity implements ContainerRecycleContentItemSection.OnItemClickListener, GroupItem.onChildItemClickListener, LabelNumPickerDialog.OnDateSetListener {

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
    private ArrayList<ContainerRecycleBean> comTaskBeans = new ArrayList<>();
    private TreeRecyclerAdapter treeRecyclerAdapter;
    private boolean isDeleteAble = true;
    public static final int RESULT_SORTITEM_SELECTPROJECT = 1001;
    public static final int REQUST_QRCODE = 1008;

    private String[] strTypes = {"吊装需求-项目选择", "吊装需求-栋选择", "吊装需求-层选择"};

    private ContainerRecycleContentItemSection workflowSection;
    private Dialog bottomDialog;
    private String STATE_COMTASK = "state_comtask";

    private HashMap<String,String> headerProperties=new HashMap<>();

    @AfterViews
    public void initComTaskActivity() {
        if (mStatus.isNewStatus()) {
            setActionBarMidlleTitle("新增货柜回收");
        }
        else {
            setActionBarMidlleTitle("货柜回收");
        }
        tv_title_live_layout.setText("货柜回收分录");

        mRecyclerViewAdapter = new MyTaskContentAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        listView.setLayoutManager(linearLayoutManager);

        workflowSection = new ContainerRecycleContentItemSection(comTaskBeans, mStatus);
        String[] stringArray = getResources().getStringArray(R.array.ContainerRecycle_itemsection_order);
        workflowSection.setTVIDContent(stringArray);
        new ItemTouchHelper(new ComTaskContentItemSectionItemTouchHelper(mRecyclerViewAdapter)).attachToRecyclerView(listView);
        workflowSection.setOnItemClickListener(this);

        mRecyclerViewAdapter.addSection(workflowSection);
        listView.setNestedScrollingEnabled(false);
        listView.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewAdapter.notifyDataSetChanged();

        testData();
//        TopListViewInit(this.comTaskBeans);
        setScrollViewFirst();
        setOnBackPressedListener(new setOnBackPressedListener() {
            @Override
            public void onBackPressedListener() {
                sureDataRefresh("tvDeleteAction");
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

        ContainerRecycleBean comTaskBean1 = null;

        if (ValueUtils.IsFirstValueExist(comTaskBeans)) {
            comTaskBean1 = this.comTaskBeans.get(0);
        }else {
            comTaskBean1=new ContainerRecycleBean();
            this.comTaskBeans.add(comTaskBean1);
        }

        if (!TextUtils.isEmpty(receiptHnumber)) {
            comTaskBean1.hNumbe = receiptHnumber;
            if (getComtaskSize()) {
                for (ContainerRecycleBean bean : comTaskBeans) {
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
    protected ArrayList<SortChildItem.ViewModel> makeChlidData(ContainerRecycleBean comTaskBean1, KeyType data, StatusBean mStatus) {
        ArrayList<SortChildItem.ViewModel> viewModels = new ArrayList<>();
        SortChildItem.ViewModel viewModel;
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
            viewModel.name = "车次";
            viewModel.value = this.comTaskBeans.get(0).getCartrips();
            viewModel.key = "cartrips";
            if (mStatus.isNewStatus() || mStatus.isModifyStatus()) {
                viewModel.isClick = true;
            }
            viewModels.add(viewModel);
            headerProperties.put(viewModel.key,viewModel.value);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "货柜";
            viewModel.value = this.comTaskBeans.get(0).getContainer();
            viewModel.key = "container";
            if (mStatus.isNewStatus() || mStatus.isModifyStatus()) {
                viewModel.isClick = true;
            }
            viewModels.add(viewModel);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "仓库";
            viewModel.value = this.comTaskBeans.get(0).Wid;
            viewModel.isQrcodeBtn=true;
            viewModel.key = "Wid";
            if (mStatus.isNewStatus() || mStatus.isModifyStatus()) {
                viewModel.isClick = true;
            }
            viewModels.add(viewModel);

            viewModel=new SortChildItem.ViewModel();
            viewModel.name="车牌号";
            viewModel.value= this.comTaskBeans.get(0).getLicensePlate();
            viewModel.key="licenseplate";
            viewModel.isClick = false;
            viewModels.add(viewModel);

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "业务日期";
            viewModel.value = this.comTaskBeans.get(0).getRequireDate();
            viewModel.key = "requireData";
            if (mStatus.isNewStatus() || mStatus.isModifyStatus()) {
                viewModel.isClick = true;
            }
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

        if (data.name.equals("车次")) {

            Intent intent = new Intent(ContainerRecycleContentMessageActivity.this, CaptureActivity.class);
            intent.putExtra("position",holder.getAdapterPosition());
            startActivityForResult(intent, REQUST_QRCODE);
        }
        else if (data.name.equals("货柜")) {

            if(TextUtils.isEmpty(headerProperties.get("cartrips")))
            {
                T.showShort(ContainerRecycleContentMessageActivity.this,"车次不能为空");
                return;
            }
            Intent intent = new Intent(ContainerRecycleContentMessageActivity.this, CaptureActivity.class);
            intent.putExtra("position",holder.getAdapterPosition());
            startActivityForResult(intent,REQUST_QRCODE);

        }else if (data.name.equals("仓库"))
        {
            if(TextUtils.isEmpty(headerProperties.get("container")))
            {
                T.showShort(ContainerRecycleContentMessageActivity.this,"货柜不能为空");
                return;
            }
            Intent intent = new Intent(ContainerRecycleContentMessageActivity.this, CaptureActivity.class);
            intent.putExtra("position",holder.getAdapterPosition());
            startActivityForResult(intent, REQUST_QRCODE);
        }
        else if (data.name.equals("业务日期")) {
            CommonDateTimePickerFragment commonDateTimePickerFragment = new CommonDateTimePickerFragment();
            Bundle bundle = new Bundle();
            bundle.putString(CommonDateTimePickerFragment.PARAM_DATA, data.value);
            commonDateTimePickerFragment.setCallBack(this);
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
                            T.showfunShort(ContainerRecycleContentMessageActivity.this, "备注不能为空");
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
    }

    //扫描回调 带出信息
    @OnActivityResult(REQUST_QRCODE)
    void resultGetRequstQrcode(int result, Intent data) {
        if (result == RESULT_OK) {
            Bundle bundle = data.getBundleExtra("bundle");
            String resultQr = bundle.getString("result");
            int extraPosition = bundle.getInt("position");

            if (extraPosition == 2) {
                ApiWebService.Get_Sale_Order_Car_ContainerCHE_Json(this, new ApiWebService.SuccessCall() {
                    @Override
                    public void SuccessBack(String result) {

//                        ArrayList<ContainerRecycleBean> loadingcarbean = App.getAppGson().fromJson(result, new TypeToken<List<ContainerRecycleBean>>() {
//                        }.getType());

                        ArrayList<ContainerRecycleBean> carNo = App.getAppGson().fromJson(result, new TypeToken<List<ContainerRecycleBean>>() {
                        }.getType());
                        if (carNo.isEmpty())
                        {
//                            T.showShort(ContainerRecycleContentMessageActivity.this,getResources().getString(R.string.Qrcode_result));
                            CustomToast.showWarning();
                            getDateRefresh("", extraPosition, "车次");
                            getDateRefresh("", extraPosition+1, "货柜");
                            return;
                        }
                        headerProperties.put("cartrips",carNo.get(0).cartrips);

                        if (!carNo.isEmpty()) {
                            getDateRefresh(carNo.get(0).cartrips, extraPosition, "车次");
                            getDateRefresh("", extraPosition+1, "货柜");
                        }
                    }

                    @Override
                    public void ErrorBack(String error) {

                    }
                }, resultQr);
            }
            else if (extraPosition == 3) {

                ApiWebService.Get_Sale_Order_Car_Containerprid_QR_Code_Json(this, new ApiWebService.SuccessCall() {
                    @Override
                    public void SuccessBack(String result) {

                        ArrayList<ContainerRecycleBean> loadingcarbean = App.getAppGson().fromJson(result, new TypeToken<List<ContainerRecycleBean>>() {
                        }.getType());

                        if (loadingcarbean.isEmpty())
                        {
//                            T.showShort(ContainerRecycleContentMessageActivity.this,getResources().getString(R.string.Qrcode_result));
                            CustomToast.showWarning();
                            getDateRefresh("", extraPosition, "货柜");
                            getDateRefresh("",extraPosition+2,"车牌号");
                            comTaskBeans.clear();
                            mRecyclerViewAdapter.notifyDataSetChanged();
                            return;
                        }

                        for (ContainerRecycleBean bean : loadingcarbean) {
                            bean.hNumbe = headerProperties.get("receiptHnumber");
                            bean.cartrips = headerProperties.get("cartrips");
                            bean.requireDate = headerProperties.get("requireData");
                            bean.description = headerProperties.get("description");
                            bean.entryPeople = headerProperties.get("enterPeople");
                        }
                        comTaskBeans.clear();
                        comTaskBeans.addAll(loadingcarbean);
                        mRecyclerViewAdapter.notifyDataSetChanged();

                        getDateRefresh(comTaskBeans.get(0).container, extraPosition, "货柜");
                        getDateRefresh(comTaskBeans.get(0).licensePlate,extraPosition+2,"车牌号");
                    }

                    @Override
                    public void ErrorBack(String error) {

                    }
                }, resultQr, headerProperties.get("cartrips"));
            }else if (extraPosition ==4)
            {
                //获取仓库位数据
                ApiWebService.B_Get_Ware_house(this, new ApiWebService.SuccessCall() {
                    @Override
                    public void SuccessBack(String result) {

                        ArrayList<wareHouseBean> carNo = App.getAppGson().fromJson(result, new TypeToken<List<wareHouseBean>>() {
                        }.getType());
                        if (carNo.isEmpty()) {
//                            T.showShort(ContainerRecycleContentMessageActivity.this, getResources().getString(R.string.Qrcode_result));
                            CustomToast.showWarning();
                            getDateRefresh("", extraPosition, "仓库");
                            return;
                        }
                        comTaskBeans.get(0).Wid = carNo.get(0).getWareHouseID();

                        getDateRefresh(carNo.get(0).getWareHouseName(), extraPosition, "仓库");
                    }

                    @Override
                    public void ErrorBack(String error) {

                    }
                },resultQr);

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
    private void getEntriesData(String extra) {
        ApiWebService.Get_Sale_Order_Hois_Req_Json(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {

                ArrayList<ContainerRecycleBean> loadingcarbean = App.getAppGson().fromJson(result, new TypeToken<List<ContainerRecycleBean>>() {
                }.getType());

                for (ContainerRecycleBean bean : loadingcarbean) {
                    bean.hNumbe = comTaskBeans.get(0).getHNumbe();
                    bean.cartrips = comTaskBeans.get(0).getCartrips();
                    bean.container = comTaskBeans.get(0).getContainer();
                    bean.containerID = comTaskBeans.get(0).getContainerID();
                    bean.requireDate = comTaskBeans.get(0).getRequireDate();
                    bean.description = comTaskBeans.get(0).getDescription();
                    bean.entryPeople = comTaskBeans.get(0).getEntryPeople();
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

//    public static ArrayList<ContainerRecycleBean> removeDupliById(List<ContainerRecycleBean> persons) {
//        Set<ContainerRecycleBean> personSet = new TreeSet<>((o1, o2) -> o1.getQrcode().equals(o2.getQrcode()));
//        Set<ContainerRecycleBean> personSet = new TreeSet<>((o1, o2) -> {
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
    public void onDateSet(String date) {

        getDateRefresh(date, 5, "业务日期");
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
            if (!viewModel.value.equals(date))
                viewModel.value = date;
            for (ContainerRecycleBean receBean : comTaskBeans) {
                receBean.requireDate = viewModel.value;
            }
        }
        else if (viewModel.name.equals("货柜")) {
            if (viewModel.value==null||!viewModel.value.equals(date))
                viewModel.value = date;

            for (int i = 0; i < comTaskBeans.size(); i++) {
                comTaskBeans.get(i).container = viewModel.value;
            }
        }
        else if (viewModel.name.equals("描述")) {
            if (!viewModel.value.equals(date))
                viewModel.value = date;

            for (int i = 0; i < comTaskBeans.size(); i++) {
                comTaskBeans.get(i).description = viewModel.value;
            }
        }
        else if (viewModel.name.equals("车次")) {
            if (viewModel.value==null||!viewModel.value.equals(date))
                viewModel.value = date;
            for (ContainerRecycleBean receBean : comTaskBeans) {
                receBean.cartrips = viewModel.value;
            }
        }else if (viewModel.name.equals("车牌号"))
        {
            if (viewModel.value==null||!viewModel.value.equals(date))
                viewModel.value = date;
            for (ContainerRecycleBean receBean : comTaskBeans) {
                receBean.licensePlate = viewModel.value;
            }
        }else if (viewModel.name.equals("仓库"))
        {
            if (viewModel.value==null||!viewModel.value.equals(date))
                viewModel.value = date;
//            for (ContainerRecycleBean receBean : comTaskBeans) {
//                receBean.Wid = viewModel.value;
//            }
        }
        headerProperties.put(viewModel.key,viewModel.value);
        treeItem.setData(viewModel);
        treeRecyclerAdapter.notifyDataSetChanged();
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
        ApiWebService.Get_Sale_Order_Car_ContainerView_Json(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                ArrayList<ContainerRecycleBean> tomTaskBeans = App.getAppGson().fromJson(result, new TypeToken<List<ContainerRecycleBean>>() {
                }.getType());
//                if (tomTaskBeans.isEmpty()) {
//                    comTaskBeans.add(new ContainerRecycleBean());
//                }
//                else {
//                }
                comTaskBeans.addAll(tomTaskBeans);
                mRecyclerViewAdapter.notifyDataSetChanged();
                ifStateForOrderId();
                TopListViewInit();
            }

            @Override
            public void ErrorBack(String error) {
                comTaskBeans.add(new ContainerRecycleBean());
                mRecyclerViewAdapter.notifyDataSetChanged();
                TopListViewInit();
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

//            if (hashMaps != null) {
//                hashMaps.remove(position);
//            }

            mRecyclerViewAdapter.notifyItemRemoved(positionAdapter);
//            //网上说这个方式刷新 position正确
//            mRecyclerViewAdapter.notifyItemRangeChanged(0,comTaskBeans.size());//刷新被删除数据，以及其后面的数据
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRecyclerViewAdapter.notifyDataSetChanged();
                }
            }, 400);

        }
    }


    //点击增加的按钮
    @Override
    public void onImgItemAdd(View view, int position, RecyclerView.ViewHolder holder) {
        if (comTaskBeans == null)
            return;
        ContainerRecycleBean dataBean = new ContainerRecycleBean();
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
    protected void tvQrcodeAction(TextView tvQrcode) {
//        ApiWebService.Get_Sale_Order_Hois_Req_listJson(this, new ApiWebService.SuccessCall() {
//            @Override
//            public void SuccessBack(String result) {
//
//                ArrayList<BProBOM> bProBOMs = App.getAppGson().fromJson(result, new TypeToken<List<BProBOM>>() {
//                }.getType());
//
//                CommonPickerActivity_.intent(ComponentReturnsContentMessageActivity.this).title("吊装需求拉取").bProBOMs(bProBOMs).Position(0).startForResult(RESULT_SORTITEM_SELECTPROJECT);
//            }
//
//            @Override
//            public void ErrorBack(String error) {
//
//            }
//        },9999,"");
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


    protected void tvSubmitActionforList(TextView tvSubmit) {
        if (!getComtaskSize()) {
            T.showShort(this, "数据有误,不能提交");
            return;
        }
        if (TextUtils.isEmpty(this.headerProperties.get("cartrips"))) {
            T.showShort(this, "车次为空不能提交");
            return;
        }
        if (TextUtils.isEmpty(this.headerProperties.get("licenseplate"))) {
            T.showShort(this, "车牌为空不能提交");
            return;
        }
        if (TextUtils.isEmpty(this.headerProperties.get("Wid"))) {
            T.showShort(this, "仓库为空不能提交");
            return;
        }
        int size = this.comTaskBeans.size();
        String[][] billtable = null;
        billtable = new String[size][20];
        for (int i = 0; i < size; i++) {
            ContainerRecycleBean confirmationReceBean = comTaskBeans.get(i);

            billtable[i][0] = confirmationReceBean.hNumbe;
            billtable[i][1] = confirmationReceBean.cartrips;
            billtable[i][2] = confirmationReceBean.licensePlate;
            billtable[i][3] = confirmationReceBean.containerID;
            billtable[i][4] = confirmationReceBean.requireDate;
            billtable[i][5] = confirmationReceBean.description;
            billtable[i][6] = confirmationReceBean.entryPeople;
            billtable[i][7] = confirmationReceBean.Qrcode;
            billtable[i][8] = confirmationReceBean.materialsID;
            billtable[i][9] = confirmationReceBean.materialsNumber;
            billtable[i][10] = confirmationReceBean.materialsNames;
            billtable[i][11] = confirmationReceBean.Specification;
            billtable[i][12] = confirmationReceBean.measureUnitID;
            billtable[i][13] = confirmationReceBean.measureUnit;
            billtable[i][14] = confirmationReceBean.amount + "";
            billtable[i][15] = confirmationReceBean.sourceTableID;
            billtable[i][16] = confirmationReceBean.projectID;
            billtable[i][17] = confirmationReceBean.dongID;
            billtable[i][18] = confirmationReceBean.cengID;
            billtable[i][19] = confirmationReceBean.Wid;
        }
        if (mStatus.isNewStatus()) {

            ApiWebService.Get_Sale_Order_Car_Container_Add(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    T.showShort(ContainerRecycleContentMessageActivity.this, result);

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
            ApiWebService.Get_Sale_Order_Car_Container_Eedit(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    T.showShort(ContainerRecycleContentMessageActivity.this, result);
                    if (!result.contains("失败")) {
//                        onBackPressed();
//                        sureDataRefresh("tvSubmitAction");
                        testData();
                    }
                }

                @Override
                public void ErrorBack(String error) {

                }
            }, billtable, comTaskBeans.get(0).getID());
        }
    }

    protected void tvCheckAction(TextView tvCheck) {

        ApiWebService.Get_Sale_Order_Car_Container_sh(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(ContainerRecycleContentMessageActivity.this, result);
//                onBackPressed();
//                sureDataRefresh("tvCheckAction");
                testData();
            }

            @Override
            public void ErrorBack(String error) {

            }
        }, comTaskBeans.get(0).getID());
    }

    protected void tvFanCheckAction(TextView tvFanCheck) {
        ApiWebService.Get_Sale_Order_Car_Container_shf(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(ContainerRecycleContentMessageActivity.this, result);
//                onBackPressed();
//                sureDataRefresh("tvFanCheckAction");
                testData();
            }

            @Override
            public void ErrorBack(String error) {

            }
        }, comTaskBeans.get(0).getID());
    }

    protected void tvModifyAction(TextView tvModify)
    {

    }

    protected void tvDeleteAction(TextView tvDelete) {
        ApiWebService.Get_Sale_Order_Car_Container_Del(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(ContainerRecycleContentMessageActivity.this, result);
                onBackPressed();
                sureDataRefresh("tvDeleteAction");
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

    @OptionsItem
    protected final void action_operat_status() {
        if (!ClickUtils.isFastDoubleClick())
            show1(mStatus);

//        Observable<Object> objectObservable = Observable.create(subscriber -> {
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
    }

}


