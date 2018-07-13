package com.system.bhouse.bhouse.CommonTask.ConfirmationReceip;

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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.system.bhouse.Custom.ShowDeviceMessageCustomDialog;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.base.StatusBean;
import com.system.bhouse.base.SubmitStatusBeanImpl;
import com.system.bhouse.bean.ConfirmationReceBean;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.TreeRecyclerAdapter;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.base.ViewHolder;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.item.GroupItem;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.item.SortChildItem;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.item.TreeItem;
import com.system.bhouse.bhouse.CommonTask.common.CommonDateTimePickerFragment;
import com.system.bhouse.bhouse.CommonTask.utils.ComTaskContentItemSectionItemTouchHelper;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;
import com.system.bhouse.bhouse.setup.utils.LabelNumPickerDialog;
import com.system.bhouse.ui.sectioned.SectionedRecyclerViewAdapter;
import com.system.bhouse.utils.TenUtils.L;
import com.system.bhouse.utils.TenUtils.T;
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
import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2018-03-05.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask
 */
@EActivity(R.layout.activity_comtask_content_layout)
@OptionsMenu(R.menu.menu_comtask)
public class ConfirmationContentMessageActivity extends WWBackActivity implements ConfirmationContentItemSection.OnItemClickListener,GroupItem.onChildItemClickListener,LabelNumPickerDialog.OnDateSetListener{

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
    /**
     * //分录 bom 出来，多个bom来保存  hashMaps
     */
    ArrayList<HashMap<String, String>> hashMaps;
    private ConfirmationContentItemSection workflowSection;
    private Dialog bottomDialog;

    @AfterViews
    public void initComTaskActivity(){
        if (mStatus.isNewStatus())
        {
            setActionBarMidlleTitle("新增收货确认");
        }else {
            setActionBarMidlleTitle("收货确认");
        }
        tv_title_live_layout.setText("收货确认分录");

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

        workflowSection = new ConfirmationContentItemSection(comTaskBeans,mStatus);
        String[] stringArray = getResources().getStringArray(R.array.confirmation_itemsection);
        workflowSection.setTVIDContent(stringArray);
        new ItemTouchHelper(new ComTaskContentItemSectionItemTouchHelper(mRecyclerViewAdapter)).attachToRecyclerView(listView);
        workflowSection.setOnItemClickListener(this);
        listView.setNestedScrollingEnabled(false);
        mRecyclerViewAdapter.addSection(workflowSection);

        listView.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewAdapter.notifyDataSetChanged();

        testData();
        TopListViewInit(this.comTaskBeans);
        setScrollViewFirst();

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
    private void TopListViewInit(ArrayList<ConfirmationReceBean> comTaskBeans) {
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
        String[] key={"7","2","2"};

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
            sortGroupItem.setData(makeChlidData(keyTypes.get(i),mStatus));
            groupItems.add(sortGroupItem);
        }

        treeRecyclerAdapter = new TreeRecyclerAdapter();
        topListView.setLayoutManager(linearLayoutManager);
        topListView.setAdapter(treeRecyclerAdapter);
        treeRecyclerAdapter.getItemManager().replaceAllItem(groupItems);
    }


    /**
     * 初始化  childItem 子布局所需的数据
     * @param data
     * @param mStatus
     * @return
     */
    protected  ArrayList<SortChildItem.ViewModel> makeChlidData(KeyType data, StatusBean mStatus) {
        ArrayList<SortChildItem.ViewModel> viewModels=new ArrayList<>();
        SortChildItem.ViewModel viewModel;
        if (data.key==7) {

            viewModel=new SortChildItem.ViewModel();
            viewModel.name=idNames.get("receiptHnumber");
            viewModel.value= idValue.get("receiptHnumber");
            viewModel.key="receiptHnumber";
            viewModel.isClick=false;
            viewModels.add(viewModel);

            viewModel=new SortChildItem.ViewModel();
            viewModel.name=idNames.get("cartrips");
            viewModel.value= idValue.get("cartrips");
            viewModel.key="cartrips";
            if (mStatus.isNewStatus()||mStatus.isModifyStatus())
                viewModel.isClick=true;
            viewModels.add(viewModel);

            viewModel=new SortChildItem.ViewModel();
            viewModel.name=idNames.get("licensePlate");
            viewModel.value= idValue.get("licensePlate");
            viewModel.key="licensePlate";
            viewModel.isClick=false;
            viewModels.add(viewModel);

            viewModel=new SortChildItem.ViewModel();
            viewModel.name=idNames.get("requireData");
            viewModel.value= idValue.get("requireData");
            viewModel.key="requireData";
            if (mStatus.isNewStatus()||mStatus.isModifyStatus()) {
                viewModel.isClick=true;
            }
            viewModels.add(viewModel);


            viewModel=new SortChildItem.ViewModel();
            viewModel.name=idNames.get("status");
            viewModel.value= idValue.get("status");
            viewModel.key="status";
                viewModel.isClick=false;
            viewModels.add(viewModel);

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


        }else if (data.key==2&&data.type.equals("录入人信息"))
        {

            viewModel=new SortChildItem.ViewModel();
            viewModel.name=idNames.get("enterPeople");
            viewModel.value= idValue.get("enterPeople");
            viewModel.key="enterPeople";
            viewModel.isClick=false;
            viewModels.add(viewModel);

            viewModel=new SortChildItem.ViewModel();
            viewModel.name=idNames.get("entryTime");
            viewModel.value= idValue.get("entryTime");
            viewModel.key="entryTime";
            viewModel.isClick=false;
            viewModels.add(viewModel);


        }else if (data.key==2&&data.type.equals("审核人信息"))
        {

            viewModel=new SortChildItem.ViewModel();
            viewModel.name=idNames.get("checkPeople");
            viewModel.value= mStatus.isNewStatus()?"":idValue.get("checkPeople");
            viewModel.key="checkPeople";
            viewModel.isClick=false;
            viewModels.add(viewModel);

            viewModel=new SortChildItem.ViewModel();
            viewModel.name=idNames.get("checkTime");
            viewModel.value= mStatus.isNewStatus()?"":idValue.get("checkTime");
            viewModel.key="checkTime";
            viewModel.isClick=false;
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
        String type = "";

        if (data.name.equals("车次")) {

            Intent intent = new Intent(ConfirmationContentMessageActivity.this, CaptureActivity.class);
            intent.putExtra("position",holder.getAdapterPosition());
            startActivityForResult(intent, REQUST_QRCODE);


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
                            T.showfunShort(ConfirmationContentMessageActivity.this,"备注不能为空");
                        }
                        else {
                            List<TreeItem> datas = treeRecyclerAdapter.getDatas();
                            SortChildItem treeItem =(SortChildItem)datas.get(7); //需求日期
                            SortChildItem.ViewModel viewModel = treeItem.getData();
                            if (viewModel.name.equals("描述")) {
                                if (!viewModel.value.equals(text))
                                    viewModel.value = text;
                                if (hashMaps==null||hashMaps.size()<0) {
                                    idValue.put(viewModel.key, viewModel.value);
                                }else {
                                    for (int i=0;i<hashMaps.size();i++)
                                    {
                                        hashMaps.get(i).put(viewModel.key,viewModel.value);
                                    }
                                }

                                for (int i=0;i<comTaskBeans.size();i++)
                                {
                                    comTaskBeans.get(i).description=viewModel.value;
                                }
                            }
                            treeItem.setData(viewModel);
                            treeRecyclerAdapter.notifyDataSetChanged();
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

            ApiWebService.Get_Sale_Order_Car_qrCHE_Json(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {

//                    if (TextUtils.isEmpty(result)) {
//                        T.showShort(ConfirmationContentMessageActivity.this, "请选择项目名称");
//                    }

                    ArrayList<ConfirmationReceBean> ntomTaskBeans = App.getAppGson().fromJson(result, new TypeToken<List<ConfirmationReceBean>>() {
                    }.getType());


                    hashMaps=new ArrayList<>();
                    for (int i=0;i<ntomTaskBeans.size();i++) {
                        idValue.put("containerID",ntomTaskBeans.get(i).containerID);
                        idValue.put("goodsID",ntomTaskBeans.get(i).materialsID);

                        idValue.put("container", ntomTaskBeans.get(i).container);
                        idValue.put("Qrcode",ntomTaskBeans.get(i).Qrcode);
                        idValue.put("goodsCoding",ntomTaskBeans.get(i).materialsNumber);
                        idValue.put("goodsName",ntomTaskBeans.get(i).materialsNames);
                        idValue.put("Specification",ntomTaskBeans.get(i).specification);

                        idValue.put("measureID",ntomTaskBeans.get(i).measureID);
                        idValue.put("amount",ntomTaskBeans.get(i).amount+""); //在这边 返回的是 amountChild
                        idValue.put("requireAmount",ntomTaskBeans.get(i).requireAmount+"");

                        idValue.put("sourceTableID",ntomTaskBeans.get(i).sourceTableID);
                        idValue.put("projectID",ntomTaskBeans.get(i).projectID);
                        idValue.put("measure",ntomTaskBeans.get(i).measure);
                        idValue.put("dongID",ntomTaskBeans.get(i).dongID);
                        idValue.put("cengID",ntomTaskBeans.get(i).cengID);
                        idValue.put("requireID",ntomTaskBeans.get(i).requireID);
                        idValue.put("requireType",ntomTaskBeans.get(i).requireType);

                        idValue.put("licensePlate",ntomTaskBeans.get(i).licensePlate);
                        idValue.put("cartrips",ntomTaskBeans.get(i).cartrips);

                        HashMap<String, String> idValue1=new HashMap<>();
                        idValue1.putAll(idValue);
                        hashMaps.add(idValue1);

                    }

                    for (int i=0;i<ntomTaskBeans.size();i++) {
                        if (comTaskBeans.size()==0||comTaskBeans.get(0)==null)
                            break;
                    ntomTaskBeans.get(i).setID(comTaskBeans.get(0).getID());
                    ntomTaskBeans.get(i).setReceiptHnumber(comTaskBeans.get(0).receiptHnumber);
                    ntomTaskBeans.get(i).setRequireDate(comTaskBeans.get(0).getRequireDate());
                    ntomTaskBeans.get(i).setDescription(comTaskBeans.get(0).getDescription());
                    ntomTaskBeans.get(i).setEntryPeople(comTaskBeans.get(0).entryPeople);
                    ntomTaskBeans.get(i).setEntryTime(comTaskBeans.get(0).getEntryTime());
                    }
                    comTaskBeans.clear();
                    comTaskBeans.addAll(ntomTaskBeans);
                    workflowSection.setSearchHistroyBeans(comTaskBeans);
                    mRecyclerViewAdapter.notifyDataSetChanged();


                    setChlidItemValue(extraPosition,"车次",ntomTaskBeans==null?"":idValue.get("cartrips"));
                    setChlidItemValue(extraPosition+1,"车牌号",idValue.get("licensePlate"));

                }

                @Override
                public void ErrorBack(String error) {

                }
            },resultQr, App.GSMID, App.Property, App.IsSub);

//[{"需求ID":"01160fb733f743228e4a9b0791736a66","需求数量":1,"需求类型":"吊装需求"}]
        }
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

//    @Override
//    public void dateSetResult(String date, boolean clear) {
//
//        List<TreeItem> datas = treeRecyclerAdapter.getDatas();
//
//        SortChildItem treeItem =(SortChildItem)datas.get(5); //需求日期
//        SortChildItem.ViewModel viewModel = treeItem.getData();
//        if (viewModel.name.equals("需求日期")) {
//            if (!viewModel.value.equals(date))
//              viewModel.value = date;
//            idValue.put(viewModel.key, viewModel.value);
//        }
//        treeItem.setData(viewModel);
//        treeRecyclerAdapter.notifyDataSetChanged();
//    }

    /**
     * 日期 click的回调
     * param date
     */
    @Override
    public void onDateSet(String date) {
        List<TreeItem> datas = treeRecyclerAdapter.getDatas();

        SortChildItem treeItem =(SortChildItem)datas.get(4); //需求日期
        SortChildItem.ViewModel viewModel = treeItem.getData();
        if (viewModel.name.equals("业务日期")) {
            if (!viewModel.value.equals(date))
                viewModel.value = date;
            if (hashMaps==null||hashMaps.size()<0) {
                idValue.put(viewModel.key, viewModel.value);
            }else {
                for (int i=0;i<hashMaps.size();i++)
                {
                    hashMaps.get(i).put(viewModel.key,viewModel.value);
                }
            }
            for (ConfirmationReceBean receBean: comTaskBeans)
            {
                receBean.requireDate=viewModel.value;
            }
        }
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
    //        [{"ID":"9dc7b4b3882048f5bccea193ccdf6fc3","订单编号":"DZXQ-7-201801-0001","项目名称":"麓谷一期项目","栋":"1","层":"2","需求日期":"2018/1/18 16:54:38","描述":"测试单据","状态":"审核","录入人":"管理员","录入时间":"2018/1/18 16:55:03"}]
    private void testData() {

        ApiWebService.Get_Sale_Order_Car_qrView_Json(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                ArrayList<ConfirmationReceBean> tomTaskBeans = App.getAppGson().fromJson(result, new TypeToken<List<ConfirmationReceBean>>() {
                }.getType());
                comTaskBeans.addAll(tomTaskBeans);
                mRecyclerViewAdapter.notifyDataSetChanged();
                TopListViewInit(tomTaskBeans);
            }

            @Override
            public void ErrorBack(String error) {

            }
        },HId, App.GSMID, App.Property, App.IsSub);
        //上个result id 值   1ea66b7bb9674a18a8794bd943c212bb
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

            if (hashMaps != null) {
                hashMaps.remove(position);
            }

            mRecyclerViewAdapter.notifyItemRemoved(positionAdapter);
            //网上说这个方式刷新 position正确
//            mRecyclerViewAdapter.notifyItemRangeChanged(0,comTaskBeans.size());//刷新被删除数据，以及其后面的数据
            new Handler().postDelayed(() -> mRecyclerViewAdapter.notifyDataSetChanged(),400);
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
        TextView tvDelete = (TextView)contentView.findViewById(R.id.tv_delete);
        TextView tvQrcode = (TextView)contentView.findViewById(R.id.tv_qrcode);
        tvQrcode.setText("构件需求匹配");

//        if (mStatus.isNewStatus()) {
//            llCheck.setVisibility(View.GONE);
//            llModify.setVisibility(View.GONE);
//            llFanCheck.setVisibility(View.GONE);
//            tvDelete.setVisibility(View.GONE);
//        }else if (mStatus.isModifyStatus())
//        {
//            llCheck.setVisibility(View.GONE);
//            llModify.setVisibility(View.GONE);
//            llFanCheck.setVisibility(View.GONE);
//            tvDelete.setVisibility(View.GONE);
//            llSubmit.setVisibility(View.VISIBLE);
//        }
//        else if (mStatus.isLookStatus()){
//            llQrcode.setVisibility(View.GONE);
//            llSubmit.setVisibility(View.GONE);
//        }

        llCheck.setVisibility(mStatus.getBean().visCheckBtn?View.VISIBLE:View.GONE);
        llModify.setVisibility(mStatus.getBean().visModifyBtn?View.VISIBLE:View.GONE);
        llFanCheck.setVisibility(mStatus.getBean().visCheckFBtn?View.VISIBLE:View.GONE);
        tvDelete.setVisibility(mStatus.getBean().visDeleteBtn?View.VISIBLE:View.GONE);
        llQrcode.setVisibility(mStatus.getBean().visQRBtn?View.VISIBLE:View.GONE);
        llSubmit.setVisibility(mStatus.getBean().visSubmitBtn?View.VISIBLE:View.GONE);


        Observable.create(subscriber -> {
            tvQrcode.setOnClickListener(v ->{subscriber.onNext(v);
            });
        }).debounce(350, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(V -> {
            L.e("double click");
            bottomDialog.dismiss();
            tvQrcodeAction();
        });


        Observable.create(subscriber -> {
            tvDelete.setOnClickListener(v ->{subscriber.onNext(v);
            });
        }).debounce(350, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(V -> {
            L.e("double click");
            bottomDialog.dismiss();
            tvDeleteAction();
        });


        Observable.create(subscriber -> {
            tvFanCheck.setOnClickListener(v ->{subscriber.onNext(v);
            });
        }).debounce(350, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(V -> {
            L.e("double click");
            bottomDialog.dismiss();
            tvFanCheckAction();
        });


        tvModify.setOnClickListener(v -> {
            mStatus.setBean(new SubmitStatusBeanImpl().setVisSubmitBtn(true).setVisQRBtn(true));
            mStatus.setLookStatus(true);
            mStatus.setModifyStatus(true);
            if (mStatus.isModifyStatus()) {
                setActionBarMidlleTitle("修改收货确认");
                TopListViewInit(this.comTaskBeans);

                workflowSection = new ConfirmationContentItemSection(comTaskBeans,mStatus);
                String[] stringArray = getResources().getStringArray(R.array.confirmation_itemsection);
                workflowSection.setTVIDContent(stringArray);
                workflowSection.setOnItemClickListener(this);
                mRecyclerViewAdapter.removeAllSections();
                mRecyclerViewAdapter.addSection(workflowSection);
                mRecyclerViewAdapter.notifyDataSetChanged();

                bottomDialog.dismiss();
            }
        });

        Observable.create(subscriber -> {
            tvSubmit.setOnClickListener(v ->{subscriber.onNext(v);
            });
        }).debounce(350, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(V -> {
            L.e("double click");
            bottomDialog.dismiss();
            if (mStatus.isNewStatus()) {
                tvSubmitAction();
            }else if (mStatus.isModifyStatus()) {
                tvSubmitActionforList();
            }
        });


        Observable.create(subscriber -> {
            tvCheck.setOnClickListener(v ->{subscriber.onNext(v);
            });
        }).debounce(350, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(V -> {
            L.e("double click");
            bottomDialog.dismiss();
            tvCheckAction();
        });


        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

    //吊装需求匹配
    private void tvQrcodeAction() {

        for (int i=0;i<comTaskBeans.size();i++) {
            int finalI = i;
            ApiWebService.Get_Sale_Order_Car_qr_hrq_Type_Json(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    ArrayList<ConfirmationReceBean> tomTaskBeans = App.getAppGson().fromJson(result, new TypeToken<List<ConfirmationReceBean>>() {
                    }.getType());
                        if (tomTaskBeans!=null&&tomTaskBeans.size()>0)
                        {
                            comTaskBeans.get(finalI).requireAmount = tomTaskBeans.get(0).requireAmount;
                            comTaskBeans.get(finalI).requireID = tomTaskBeans.get(0).requireID;
                            comTaskBeans.get(finalI).requireType = tomTaskBeans.get(0).requireType;
                            hashMaps.get(finalI).put("requireAmount", tomTaskBeans.get(0).requireAmount + "");
                            hashMaps.get(finalI).put("requireID", tomTaskBeans.get(0).requireID);
                            hashMaps.get(finalI).put("requireType", tomTaskBeans.get(0).requireType);
                                mRecyclerViewAdapter.notifyDataSetChanged();
                        }

                }

                @Override
                public void ErrorBack(String error) {

                }
            }, comTaskBeans.get(i).Qrcode, App.GSMID, App.Property, App.IsSub);

        }
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


    private void tvSubmitActionforList(){
        if (!getComtaskSize())
        {
            T.showShort(this, "数据有误,不能提交");
            return;
        }
        if (TextUtils.isEmpty(this.comTaskBeans.get(0).cartrips)) {
            T.showShort(this, "车次为空不能提交");
            return;
        }else {
            for (int i=0;i<comTaskBeans.size();i++)
            {
                if (comTaskBeans.get(i).requireAmount<=0) {
                    T.showShort(this, "第"+(i+1)+"行需求数量为空不能提交");
                    return;
                }
            }
        }
        int size = this.comTaskBeans.size();
        String[][] billtable=null;
        billtable=new  String[size][22];
        for (int i=0;i<size;i++)
        {
            ConfirmationReceBean confirmationReceBean = comTaskBeans.get(i);

                    billtable[i][0]=confirmationReceBean.receiptHnumber;
                    billtable[i][1]=confirmationReceBean.cartrips;
                    billtable[i][2]=confirmationReceBean.licensePlate;
                    billtable[i][3]=confirmationReceBean.requireDate;
                    billtable[i][4]=confirmationReceBean.description;
                    billtable[i][5]=confirmationReceBean.entryPeople;
                    billtable[i][6]=confirmationReceBean.containerID;
                    billtable[i][7]=confirmationReceBean.Qrcode;
                    billtable[i][8]=confirmationReceBean.materialsID;
                    billtable[i][9]=confirmationReceBean.materialsNumber;
                    billtable[i][10]=confirmationReceBean.materialsNames;
                    billtable[i][11]=confirmationReceBean.specification;
                    billtable[i][12]=confirmationReceBean.measureID;
                    billtable[i][13]=confirmationReceBean.measure;
                    billtable[i][14]=confirmationReceBean.amount+"";
                    billtable[i][15]=confirmationReceBean.requireAmount+"";
                    billtable[i][16]=confirmationReceBean.sourceTableID;
                    billtable[i][17]=confirmationReceBean.projectID;
                    billtable[i][18]=confirmationReceBean.dongID;
                    billtable[i][19]=confirmationReceBean.cengID;
                    billtable[i][20]=confirmationReceBean.requireID;
                    billtable[i][21]=confirmationReceBean.requireType;
        }
        ApiWebService.Get_Sale_Order_Car_qr_Eedit(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(ConfirmationContentMessageActivity.this,result);
                onBackPressed();
                sureDataRefresh("tvSubmitAction");
            }

            @Override
            public void ErrorBack(String error) {

            }
        },billtable,comTaskBeans.get(0).ID, App.GSMID, App.Property, App.IsSub, App.MobileKey, App.KeyTimestring, App.USER_INFO);
    }


    private void tvSubmitAction(){
        if (TextUtils.isEmpty(idValue.get("cartrips"))) {
            T.showShort(this, "车次为空不能提交");
            return;
        }else {
            for (int i=0;i<hashMaps.size();i++)
            {
                if (hashMaps.get(i).get("requireAmount").equals("0")) {
                    T.showShort(this, "第"+(i+1)+"行需求数量为空不能提交");
                    return;
                }
            }
        }
        int size = hashMaps.size();
        String[][] billtable=null;
        billtable=new  String[size][22];
        for (int i=0;i<size;i++)
        {
            HashMap<String, String> stringStringHashMap = hashMaps.get(i);

            for (String key : stringStringHashMap.keySet()) {
                 if ("receiptHnumber".equals(key))
                 {
                     billtable[i][0]=stringStringHashMap.get(key);
                 }else if ("cartrips".equals(key))
                 {
                     billtable[i][1]=stringStringHashMap.get(key);
                 }else if ("licensePlate".equals(key))
                 {
                     billtable[i][2]=stringStringHashMap.get(key);
                 }else if ("requireData".equals(key))
                 {
                     billtable[i][3]=stringStringHashMap.get(key);
                 }else if ("description".equals(key))
                 {
                     billtable[i][4]=stringStringHashMap.get(key);
                 }else if ("enterPeople".equals(key))
                 {
                     billtable[i][5]=stringStringHashMap.get(key);
                 }else if ("containerID".equals(key))
                 {
                     billtable[i][6]=stringStringHashMap.get(key);
                 }
                 else if ("Qrcode".equals(key))
                 {
                     billtable[i][7]=stringStringHashMap.get(key);
                 }else if ("goodsID".equals(key))
                 {
                     billtable[i][8]=stringStringHashMap.get(key);
                 }else if ("goodsCoding".equals(key))
                 {
                     billtable[i][9]=stringStringHashMap.get(key);
                 }else if ("goodsName".equals(key))
                 {
                     billtable[i][10]=stringStringHashMap.get(key);
                 }else if ("Specification".equals(key))
                 {
                     billtable[i][11]=stringStringHashMap.get(key);
                 }else if ("measureID".equals(key))
                 {
                     billtable[i][12]=stringStringHashMap.get(key);
                 }else if ("measure".equals(key))
                 {
                     billtable[i][13]=stringStringHashMap.get(key);
                 }else if ("amount".equals(key))
                 {
                     billtable[i][14]=stringStringHashMap.get(key);
                 }else if ("requireAmount".equals(key))
                 {
                     billtable[i][15]=stringStringHashMap.get(key);
                 }else if ("sourceTableID".equals(key)) //sourceTableID
                 {
                     billtable[i][16]=stringStringHashMap.get(key);
                 }else if ("projectID".equals(key))
                 {
                     billtable[i][17]=stringStringHashMap.get(key);
                 }else if ("dongID".equals(key))
                 {
                     billtable[i][18]=stringStringHashMap.get(key);
                 }else if ("cengID".equals(key))
                 {
                     billtable[i][19]=stringStringHashMap.get(key);
                 }else if ("requireID".equals(key))
                 {
                     billtable[i][20]=stringStringHashMap.get(key);
                 }else if ("requireType".equals(key))
                 {
                     billtable[i][21]=stringStringHashMap.get(key);
                 }
            }
        }
        ApiWebService.Get_Sale_Order_Car_qr_Add(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(ConfirmationContentMessageActivity.this,result);
                onBackPressed();
                sureDataRefresh("tvSubmitAction");
            }

            @Override
            public void ErrorBack(String error) {

            }
        },billtable, App.GSMID, App.Property, App.IsSub, App.MobileKey, App.KeyTimestring, App.USER_INFO);
    }

    private void tvCheckAction(){

        ApiWebService.Get_Sale_Order_Car_qr_sh(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(ConfirmationContentMessageActivity.this,result);
                onBackPressed();
                sureDataRefresh("tvCheckAction");
            }

            @Override
            public void ErrorBack(String error) {

            }
        },idValue.get("ID"), App.USER_INFO, App.GSMID, App.Property, App.IsSub, App.MobileKey, App.KeyTimestring, App.USER_INFO);
    }

    private void tvFanCheckAction(){
        ApiWebService.Get_Sale_Order_Car_qr_shf(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(ConfirmationContentMessageActivity.this,result);
                onBackPressed();
                sureDataRefresh("tvFanCheckAction");
            }

            @Override
            public void ErrorBack(String error) {

            }
        },idValue.get("ID"), App.USER_INFO, App.GSMID, App.Property, App.IsSub, App.MobileKey, App.KeyTimestring, App.USER_INFO);
    }

    private void tvDeleteAction(){
        ApiWebService.Get_Sale_Order_Car_qr_Del(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(ConfirmationContentMessageActivity.this,result);
                onBackPressed();
                sureDataRefresh("tvDeleteAction");
            }

            @Override
            public void ErrorBack(String error) {

            }
        },idValue.get("ID"), App.USER_INFO, App.GSMID, App.Property, App.IsSub, App.MobileKey, App.KeyTimestring, App.USER_INFO);
    }

    protected void sureDataRefresh(String type) {
        EventBus.getDefault().post("刷新"+type+"数据");
    }


    public static class DataBean{
        public String wuLiao;
        public String countUnit;
        public String count;
    }

    private boolean isVisBottom(RecyclerView recyclerView){
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if(visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE){
            return true;
        }else {
            return false;
        }
    }


    @OptionsItem
    protected final void action_operat_status(){
        Observable<Object> objectObservable = Observable.create(subscriber -> {
            show1();
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
               App.KeyTimestring= o.toString();
            }
        });
    }

}


