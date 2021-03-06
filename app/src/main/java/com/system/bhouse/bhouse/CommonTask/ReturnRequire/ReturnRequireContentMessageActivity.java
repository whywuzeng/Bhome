package com.system.bhouse.bhouse.CommonTask.ReturnRequire;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
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
import com.system.bhouse.bean.ConfirmationReceBean;
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
import com.system.bhouse.ui.sectioned.SectionedRecyclerViewAdapter;
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
import java.util.Set;
import java.util.TreeSet;

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
public class ReturnRequireContentMessageActivity extends BaseContentMessageActivity implements ReturnRequireContentItemSection.OnItemClickListener,GroupItem.onChildItemClickListener,LabelNumPickerDialog.OnDateSetListener{

    public static final String TAG="comtaskcontentmessageactivity";

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
    private ArrayList<ConfirmationReceBean> comTaskBeans=new ArrayList<>();
    private TreeRecyclerAdapter treeRecyclerAdapter;
    private boolean isDeleteAble=true;
    public static final int RESULT_SORTITEM_SELECTPROJECT = 1001;
    public static final int REQUST_QRCODE=1008;
    private HashMap<Integer,String> extraHIds=new HashMap<>();

    private String[] strTypes = {"吊装需求-项目选择", "吊装需求-栋选择", "吊装需求-层选择"};

    /**
     * //选择保存 表头的 数据  idValue
     */
    private HashMap<String, String> idValue;
    private HashMap<String, String> idNames;

    private ReturnRequireContentItemSection workflowSection;
    private Dialog bottomDialog;
    private HashMap<String,String> headerProperties=new HashMap<>();

    @AfterViews
    public void initComTaskActivity(){
        if (mStatus.isNewStatus())
        {
            setActionBarMidlleTitle("新增退货需求");
        }else {
            setActionBarMidlleTitle("退货需求");
        }
        tv_title_live_layout.setText("退货需求分录");

        mRecyclerViewAdapter=new MyTaskContentAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        GridLayoutManager manager=new GridLayoutManager(this,1);
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

        workflowSection = new ReturnRequireContentItemSection(comTaskBeans,mStatus);
        String[] stringArray = getResources().getStringArray(R.array.confirmation_itemsection_return);
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

        ConfirmationReceBean comTaskBean=new ConfirmationReceBean();
        ConfirmationReceBean comTaskBean1 = null;
        try {
            comTaskBean1 = comTaskBeans.size()==0 ? comTaskBean : comTaskBeans.get(0);
        } catch (Exception e) {
            comTaskBean1=comTaskBean;
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(receiptHnumber))
        {
            comTaskBean1.receiptHnumber=receiptHnumber;
            if (getComtaskSize()) {
                for (ConfirmationReceBean bean:comTaskBeans) {
                    bean.receiptHnumber = receiptHnumber;
                }
            }
        }

        idNames = comTaskBean1.getIdNames();
        idValue = comTaskBean1.getIdValue();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        String[] LETTERS = new String[]{"单据信息","录入人信息","审核人信息"};
        String[] key={"4","2","2"};

        ArrayList<KeyType> keyTypes=new ArrayList<>();
        KeyType keyType =null;
        for (int i=0;i<LETTERS.length;i++)
        {
            keyType = new KeyType();
            keyType.key=Integer.valueOf(key[i]);
            keyType.type=LETTERS[i];
            keyTypes.add(keyType);
        }

        final List<TreeItem> groupItems = new ArrayList<>();

        for (int i = 0; i < LETTERS.length; i++) {
            GroupItem sortGroupItem = new GroupItem();
            sortGroupItem.TitleKey=LETTERS[i];
            sortGroupItem.setmOnChildItemClickListener(this);
            sortGroupItem.setData(makeChlidData(comTaskBean1,keyTypes.get(i),mStatus));
            groupItems.add(sortGroupItem);
        }

        if (mStatus.isNewStatus())
        this.comTaskBeans.add(comTaskBean1);
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
    protected  ArrayList<SortChildItem.ViewModel> makeChlidData(ConfirmationReceBean comTaskBean1, KeyType data, StatusBean mStatus) {
        ArrayList<SortChildItem.ViewModel> viewModels=new ArrayList<>();
        SortChildItem.ViewModel viewModel;
        if (data.key==4) {
            viewModel=new SortChildItem.ViewModel();
            viewModel.name="退货单编号";
            viewModel.value= idValue.get("receiptHnumber");
            viewModel.key="receiptHnumber";
            viewModel.isClick=false;
            viewModels.add(viewModel);
            comTaskBean1.receiptHnumber=idValue.get("receiptHnumber");
            headerProperties.put(viewModel.key,viewModel.value);

            viewModel=new SortChildItem.ViewModel();
            viewModel.name=idNames.get("requireData");
            viewModel.value= idValue.get("requireData");
            viewModel.key="requireData";
            if (mStatus.isNewStatus()||mStatus.isModifyStatus()) {
                viewModel.isClick=true;
            }
            viewModels.add(viewModel);
            comTaskBean1.requireDate=idValue.get("requireData");
            headerProperties.put(viewModel.key,viewModel.value);

            viewModel=new SortChildItem.ViewModel();
            viewModel.name=idNames.get("status");
            viewModel.value= idValue.get("status");
            viewModel.key="status";
            viewModel.isClick=false;
            viewModels.add(viewModel);
            comTaskBean1.status=idValue.get("status");

            viewModel=new SortChildItem.ViewModel();
            viewModel.name="管理单元";
            viewModel.value= App.Mancompany==null?"": App.Mancompany;
            viewModel.key="Mancompany";
            viewModel.isClick=false;
            viewModels.add(viewModel);

            viewModel=new SortChildItem.ViewModel();
            viewModel.name=idNames.get("description");
            viewModel.value= idValue.get("description");
            viewModel.key="description";
            if (mStatus.isNewStatus()||mStatus.isModifyStatus()) {
                viewModel.isClick=true;
            }
            viewModels.add(viewModel);
            comTaskBean1.description=idValue.get("description");
            headerProperties.put(viewModel.key,viewModel.value);


        }else if (data.key==2&&data.type.equals("录入人信息"))
        {

            viewModel=new SortChildItem.ViewModel();
            viewModel.name=idNames.get("enterPeople");
            viewModel.value= idValue.get("enterPeople");
            viewModel.key="enterPeople";
            viewModel.isClick=false;
            viewModels.add(viewModel);
            comTaskBean1.entryPeople=idValue.get("enterPeople");
            headerProperties.put(viewModel.key,viewModel.value);

            viewModel=new SortChildItem.ViewModel();
            viewModel.name=idNames.get("entryTime");
            viewModel.value= idValue.get("entryTime");
            viewModel.key="entryTime";
            viewModel.isClick=false;
            viewModels.add(viewModel);
            comTaskBean1.entryTime=idValue.get("entryTime");
            headerProperties.put(viewModel.key,viewModel.value);

        }else if (data.key==2&&data.type.equals("审核人信息"))
        {

            viewModel=new SortChildItem.ViewModel();
            viewModel.name=idNames.get("checkPeople");
            viewModel.value= mStatus.isNewStatus()?"":idValue.get("checkPeople");
            viewModel.key="checkPeople";
            viewModel.isClick=false;
            viewModels.add(viewModel);
            comTaskBean1.checkPeople=idValue.get("checkPeople");

            viewModel=new SortChildItem.ViewModel();
            viewModel.name=idNames.get("checkTime");
            viewModel.value= mStatus.isNewStatus()?"":idValue.get("checkTime");
            viewModel.key="checkTime";
            viewModel.isClick=false;
            viewModels.add(viewModel);
            comTaskBean1.checkTime=idValue.get("checkTime");

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
        String type = "";

        if (data.name.equals("车次")) {

        }
        else if (data.name.equals("业务日期"))
        {
            CommonDateTimePickerFragment commonDateTimePickerFragment = new CommonDateTimePickerFragment();
            Bundle bundle = new Bundle();
            bundle.putString(CommonDateTimePickerFragment.PARAM_DATA, data.value);
            commonDateTimePickerFragment.setCallBack(this);
            commonDateTimePickerFragment.setArguments(bundle);
            commonDateTimePickerFragment.setCancelable(true);
            commonDateTimePickerFragment.show(getSupportFragmentManager(), "datePicker");
            getSupportFragmentManager().executePendingTransactions();

        }else if (data.name.equals("描述"))
        {
            ShowDeviceMessageCustomDialog.Builder builder = new ShowDeviceMessageCustomDialog.Builder(this);
            builder.setMessage("您的描述是:");
            builder.setTitle("提示");
            builder.setEdittextcontent(data.value);

            View inflate = LayoutInflater.from(this).inflate(R.layout.beizhu_edittext, null, false);
            builder.setContentView(inflate);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    EditText viewEditText = (EditText)inflate.findViewById(R.id.edt_domian);
                    if (viewEditText.getText() != null) {
                        String text = viewEditText.getText().toString();
                        if (TextUtils.isEmpty(text)) {
                            T.showfunShort(ReturnRequireContentMessageActivity.this,"备注不能为空");
                        }
                        else {
                            getDateRefresh(text,5,"描述");
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
    void resultGetRequstQrcode(int result, Intent data){
        if (result==RESULT_OK){
            Bundle bundle = data.getBundleExtra("bundle");
            String resultQr = bundle.getString("result");
            int extraPosition = bundle.getInt("position");

            ApiWebService.Get_Sale_Order_Car_qr_rn_prid_QR_Code_Json(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {


                    ArrayList<ConfirmationReceBean> ntomTaskBeans = App.getAppGson().fromJson(result, new TypeToken<List<ConfirmationReceBean>>() {
                    }.getType());

                    if (ntomTaskBeans.isEmpty())
                    {
//                        T.showShort(ReturnRequireContentMessageActivity.this,getResources().getString(R.string.Qrcode_result));
                        CustomToast.showWarning();
                        return;
                    }

                    //把原来静态的数据添加进去，描述 和 业务日期
                    for (int i = 0; i < ntomTaskBeans.size(); i++) {
                        ntomTaskBeans.get(i).setID(HId);
                        ntomTaskBeans.get(i).setReceiptHnumber(headerProperties.get("receiptHnumber"));
                        ntomTaskBeans.get(i).setRequireDate(headerProperties.get("requireData"));
                        ntomTaskBeans.get(i).setDescription(headerProperties.get("description"));
                        ntomTaskBeans.get(i).setEntryPeople(headerProperties.get("enterPeople"));
                        ntomTaskBeans.get(i).setEntryTime(headerProperties.get("entryTime"));
                    }
                    //清空 二维码为空的
                    for (ConfirmationReceBean receBean:comTaskBeans)
                    {
                        if (TextUtils.isEmpty(receBean.getQrcode()))
                        {
                            comTaskBeans.remove(receBean);
                        }
                    }
                    //为空就不要添加
                    if (!(ntomTaskBeans.size()==0))
                    comTaskBeans.addAll(ntomTaskBeans);
                    comTaskBeans=removeDupliById(comTaskBeans);

                    //可以一个一个添加二维码
                    workflowSection.setSearchHistroyBeans(comTaskBeans);
                    mRecyclerViewAdapter.notifyDataSetChanged();

                }

                @Override
                public void ErrorBack(String error) {

                }
            },resultQr, App.GSMID, App.Property, App.IsSub);

        }
    }

    public static ArrayList<ConfirmationReceBean> removeDupliById(List<ConfirmationReceBean> persons) {
//        Set<ConfirmationReceBean> personSet = new TreeSet<>((o1, o2) -> o1.getQrcode().equals(o2.getQrcode()));
        Set<ConfirmationReceBean> personSet = new TreeSet<>((o1, o2) -> {
            if (o1.getQrcode().equals(o2.getQrcode()))
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
    private void setChlidItemEmpty(int position){
        List<TreeItem> datas = treeRecyclerAdapter.getDatas();

        SortChildItem treeItem =(SortChildItem)datas.get(position);
        SortChildItem.ViewModel viewModel = treeItem.getData();
        viewModel.value="";
        treeItem.setData(viewModel);
    }

    /**
     * 设置 position 的值
     * param position
     */
    private void setChlidItemValue(int position,String name,String value){
        List<TreeItem> datas = treeRecyclerAdapter.getDatas();

        SortChildItem treeItem =(SortChildItem)datas.get(position);
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
        getDateRefresh(date,2,"业务日期");
    }

    /**
     * 更新data位置,更新ItemChild数据
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
            for (ConfirmationReceBean receBean : comTaskBeans) {
                receBean.requireDate = viewModel.value;
            }
        }
        else if (viewModel.name.equals("描述")) {
            if (!viewModel.value.equals(date))
                viewModel.value = date;

            for (int i = 0; i < comTaskBeans.size(); i++) {
                comTaskBeans.get(i).description = viewModel.value;
            }
        }
        headerProperties.put(viewModel.key,viewModel.value);
        treeItem.setData(viewModel);
        treeRecyclerAdapter.notifyDataSetChanged();
    }


    public static class KeyType{
        public int key;
        public String type;
    }


    /**
     * 请求数据
     */
    private void testData() {
        comTaskBeans.clear();
        ApiWebService.Get_Sale_Order_Car_qr_rnView_Json(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                ArrayList<ConfirmationReceBean> tomTaskBeans = App.getAppGson().fromJson(result, new TypeToken<List<ConfirmationReceBean>>() {
                }.getType());
                comTaskBeans.addAll(tomTaskBeans);
                mRecyclerViewAdapter.notifyDataSetChanged();
                ifStateForOrderId();
                TopListViewInit();

            }

            @Override
            public void ErrorBack(String error) {

            }
        },HId, App.GSMID, App.Property, App.IsSub);
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

    private int ikey=0;
    @Override
    public void onImgItemDelete(int position,int positionAdapter) {

        if (isDeleteAble) {//此时为增加动画效果，刷新部分数据源，防止删除错乱
            isDeleteAble = false;//初始值为true,当点击删除按钮以后，休息0.5秒钟再让他为
            //true,起到让数据源刷新完成的作用

            // 删除数据
            mRecyclerViewAdapter.onItemRemove(positionAdapter);
            ikey++;
            L.e("点击了一次"+ikey);
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
            if (position==comTaskBeans.size())
                return;
                if (!TextUtils.isEmpty(comTaskBeans.get(0).status)&&comTaskBeans.get(0).status.equals("审核")) {
                    T.showShort(this, "该单据正在审核状态，不能删除");
                    return;
                }

            comTaskBeans.remove(position);

            mRecyclerViewAdapter.notifyItemRemoved(positionAdapter);
            //网上说这个方式刷新 position正确
//            mRecyclerViewAdapter.notifyItemRangeChanged(0,comTaskBeans.size());//刷新被删除数据，以及其后面的数据
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRecyclerViewAdapter.notifyDataSetChanged();
                }
            },400);
    }
}



    //点击增加的按钮
    @Override
    public void onImgItemAdd(View view, int position, RecyclerView.ViewHolder holder) {
        if (comTaskBeans==null)
            return;
        ConfirmationReceBean dataBean = new ConfirmationReceBean();
        comTaskBeans.add(dataBean);

        mRecyclerViewAdapter.notifyItemInserted(comTaskBeans.size());
        if (!isVisBottom(listView)) {
            listView.smoothScrollToPosition(comTaskBeans.size()+1);
        }
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    private class MyTaskContentAdapter extends ComTaskContentItemSectionItemTouchHelper.ItemTouchAdapterImpl
    {

        @Override
        public void onItemMove(int fromPosition, int toPosition) {

        }

        @Override
        public void onItemRemove(int position) {

        }
    }

    //构件选择 点击事件
    protected void tvQrcodeAction(TextView tvQrcode) {
        Intent intent = new Intent(ReturnRequireContentMessageActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUST_QRCODE);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private boolean getComtaskSize(){
        if (this.comTaskBeans==null||this.comTaskBeans.size()<=0)
        {
            return false;
        }else {
            return true;
        }
    }


    protected void tvSubmitActionforList(TextView tvSubmit){
        if (!getComtaskSize())
        {
            T.showShort(this, "数据有误,不能提交");
            return;
        }
        if (TextUtils.isEmpty(this.comTaskBeans.get(0).Qrcode)) {
            T.showShort(this, "分录为空不能提交");
            return;
        }
        int size = this.comTaskBeans.size();
        String[][] billtable=null;
        billtable=new  String[size][17];
        for (int i=0;i<size;i++)
        {
            ConfirmationReceBean confirmationReceBean = comTaskBeans.get(i);

                    billtable[i][0]=confirmationReceBean.receiptHnumber;
                    billtable[i][1]=confirmationReceBean.requireDate;
                    billtable[i][2]=confirmationReceBean.description;
                    billtable[i][3]=confirmationReceBean.entryPeople;
                    billtable[i][4]=confirmationReceBean.Qrcode;
                    billtable[i][5]=confirmationReceBean.materialsID;
                    billtable[i][6]=confirmationReceBean.materialsNumber;
                    billtable[i][7]=confirmationReceBean.materialsNames;
                    billtable[i][8]=confirmationReceBean.specification;
                    billtable[i][9]=confirmationReceBean.measureID;
                    billtable[i][10]=confirmationReceBean.measure;
                    billtable[i][11]=confirmationReceBean.amount+"";
                    billtable[i][12]=confirmationReceBean.requireAmount+"";
                    billtable[i][13]=confirmationReceBean.sourceTableID;
                    billtable[i][14]=confirmationReceBean.projectID;
                    billtable[i][15]=confirmationReceBean.dongID;
                    billtable[i][16]=confirmationReceBean.cengID;
        }
        if (mStatus.isNewStatus()) {

            ApiWebService.Get_Sale_Order_Car_qr_rn_Add(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    T.showShort(ReturnRequireContentMessageActivity.this,result);
                    onBackPressed();
                    sureDataRefresh("tvSubmitAction");
                }

                @Override
                public void ErrorBack(String error) {

                }
            },billtable, App.GSMID, App.Property, App.IsSub, App.MobileKey, App.KeyTimestring, App.USER_INFO);

        }
        else if (mStatus.isModifyStatus()) {
            ApiWebService.Get_Sale_Order_Car_qr_rn_Eedit(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    T.showShort(ReturnRequireContentMessageActivity.this, result);
//                    onBackPressed();
//                    sureDataRefresh("tvSubmitAction");
                    testData();
                }

                @Override
                public void ErrorBack(String error) {

                }
            }, billtable, comTaskBeans.get(0).ID, App.GSMID, App.Property, App.IsSub, App.MobileKey, App.KeyTimestring, App.USER_INFO);
        }
    }


    protected void tvCheckAction(TextView tvCheck){

        ApiWebService.Get_Sale_Order_Car_qr_rn_sh(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(ReturnRequireContentMessageActivity.this,result);
//                onBackPressed();
//                sureDataRefresh("tvCheckAction");
                testData();
            }

            @Override
            public void ErrorBack(String error) {

            }
        },comTaskBeans.get(0).getID(), App.USER_INFO, App.GSMID, App.Property, App.IsSub, App.MobileKey, App.KeyTimestring, App.USER_INFO);
    }

    protected void tvFanCheckAction(TextView tvFanCheck){
        ApiWebService.Get_Sale_Order_Car_qr_rn_shf(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(ReturnRequireContentMessageActivity.this,result);
//                onBackPressed();
//                sureDataRefresh("tvFanCheckAction");
                testData();
            }

            @Override
            public void ErrorBack(String error) {

            }
        },comTaskBeans.get(0).getID(), App.USER_INFO, App.GSMID, App.Property, App.IsSub, App.MobileKey, App.KeyTimestring, App.USER_INFO);
    }

    protected void tvModifyAction(TextView tvModify){
        mStatus.setBean(new SubmitStatusBeanImpl().setVisSubmitBtn(true).setVisQRBtn(true));
        mStatus.setLookStatus(true);
        mStatus.setModifyStatus(true);
        if (mStatus.isModifyStatus()) {
            setActionBarMidlleTitle("修改退货需求");
            TopListViewInit();

            workflowSection = new ReturnRequireContentItemSection(comTaskBeans,mStatus);
            String[] stringArray = getResources().getStringArray(R.array.confirmation_itemsection_return);
            workflowSection.setTVIDContent(stringArray);
            workflowSection.setOnItemClickListener(this);
            mRecyclerViewAdapter.removeAllSections();
            mRecyclerViewAdapter.addSection(workflowSection);

            mRecyclerViewAdapter.notifyDataSetChanged();
            bottomDialog.dismiss();
        }
    }

    protected void tvDeleteAction(TextView tvDelete){
        ApiWebService.Get_Sale_Order_Car_qr_rn_Del(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(ReturnRequireContentMessageActivity.this,result);
                onBackPressed();
                sureDataRefresh("tvDeleteAction");
            }

            @Override
            public void ErrorBack(String error) {

            }
        },comTaskBeans.get(0).getID(), App.USER_INFO, App.GSMID, App.Property, App.IsSub, App.MobileKey, App.KeyTimestring, App.USER_INFO);
    }

    protected void sureDataRefresh(String type) {
        EventBus.getDefault().post("刷新"+type+"数据");
    }


    public static class DataBean{
        public String wuLiao;
        public String countUnit;
        public String count;
    }

    @OptionsItem
    protected final void action_operat_status(){
        show1(mStatus);
        setTvQrcodeContext("构件扫码");
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
//               App.KeyTimestring= o.toString();
//            }
//        });
    }

}


