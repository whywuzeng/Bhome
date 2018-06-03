package com.system.bhouse.bhouse.CommonTask.ReplenishmentRequire;

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
import java.util.Set;
import java.util.TreeSet;
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
public class ReplenishmentRequireContentMessageActivity extends WWBackActivity implements ReplenishmentRequireContentItemSection.OnItemClickListener,GroupItem.onChildItemClickListener,LabelNumPickerDialog.OnDateSetListener{

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

    private ReplenishmentRequireContentItemSection workflowSection;
    private Dialog bottomDialog;
    private String STATE_COMTASK="state_comtask";
    private HashMap<String,String> headerProperties=new HashMap<>();

    @AfterViews
    public void initComTaskActivity(){
        if (mStatus.isNewStatus())
        {
            setActionBarMidlleTitle("新增补货需求");
        }else {
            setActionBarMidlleTitle("补货需求");
        }
        tv_title_live_layout.setText("补货需求分录");

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

        workflowSection = new ReplenishmentRequireContentItemSection(comTaskBeans,mStatus);
        String[] stringArray = getResources().getStringArray(R.array.confirmation_itemsection_replenishment);
        workflowSection.setTVIDContent(stringArray);
        new ItemTouchHelper(new ComTaskContentItemSectionItemTouchHelper(mRecyclerViewAdapter)).attachToRecyclerView(listView);
        workflowSection.setOnItemClickListener(this);

        mRecyclerViewAdapter.addSection(workflowSection);

        listView.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewAdapter.notifyDataSetChanged();

        testData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 检查是否正在重新创建一个以前销毁的实例
        if (savedInstanceState != null) {
            // 从已保存状态恢复成员的值
            this.comTaskBeans = savedInstanceState.getParcelableArrayList(STATE_COMTASK);
        } else {
            // 可能初始化一个新实例的默认值的成员
        }
    }

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
            viewModel.name="补货单编号";
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

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putParcelableArrayList(STATE_COMTASK,this.comTaskBeans);
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
                            T.showfunShort(ReplenishmentRequireContentMessageActivity.this,"备注不能为空");
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

            ApiWebService.Get_Sale_Order_Car_qr_In_prid_QR_Code_Json(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {

                    ArrayList<ConfirmationReceBean> ntomTaskBeans = App.getAppGson().fromJson(result, new TypeToken<List<ConfirmationReceBean>>() {
                    }.getType());

                    if (ntomTaskBeans.isEmpty()){
                        T.showShort(ReplenishmentRequireContentMessageActivity.this,getResources().getString(R.string.Qrcode_result));
                        return;
                    }

                    for (int i = 0; i < ntomTaskBeans.size(); i++) {
                        ntomTaskBeans.get(i).setID(HId);
                        ntomTaskBeans.get(i).setReceiptHnumber(headerProperties.get("receiptHnumber"));
                        ntomTaskBeans.get(i).setRequireDate(headerProperties.get("requireData"));
                        ntomTaskBeans.get(i).setDescription(headerProperties.get("description"));
                        ntomTaskBeans.get(i).setEntryPeople(headerProperties.get("enterPeople"));
                        ntomTaskBeans.get(i).setEntryTime(headerProperties.get("entryTime"));
                    }

                    for (ConfirmationReceBean receBean:comTaskBeans)
                    {
                        if (TextUtils.isEmpty(receBean.getQrcode()))
                        {
                            comTaskBeans.remove(receBean);
                        }
                    }
                    if (!(ntomTaskBeans.size()==0))
                    comTaskBeans.addAll(ntomTaskBeans);
                    ArrayList<ConfirmationReceBean> clone = (ArrayList<ConfirmationReceBean>)comTaskBeans.clone();
                    comTaskBeans.clear();
                    comTaskBeans.addAll(removeDupliById(clone));
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
        getDateRefresh(date, 2, "业务日期");
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

        SortChildItem treeItem = (SortChildItem) datas.get(position);
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

        ApiWebService.Get_Sale_Order_Car_qr_InView_Json(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                ArrayList<ConfirmationReceBean> tomTaskBeans = App.getAppGson().fromJson(result, new TypeToken<List<ConfirmationReceBean>>() {
                }.getType());
                comTaskBeans.addAll(tomTaskBeans);
                mRecyclerViewAdapter.notifyDataSetChanged();
                TopListViewInit(comTaskBeans);
            }

            @Override
            public void ErrorBack(String error) {

            }
        },HId, App.GSMID, App.Property, App.IsSub);
        //上个result id 值
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
//            //网上说这个方式刷新 position正确
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
        tvQrcode.setText("构件扫码");

        if (mStatus.isNewStatus()) {
            llCheck.setVisibility(View.GONE);
            llModify.setVisibility(View.GONE);
            llFanCheck.setVisibility(View.GONE);
            tvDelete.setVisibility(View.GONE);
        }else if (mStatus.isModifyStatus())
        {
            llCheck.setVisibility(View.GONE);
            llModify.setVisibility(View.GONE);
            llFanCheck.setVisibility(View.GONE);
            tvDelete.setVisibility(View.GONE);
            llSubmit.setVisibility(View.VISIBLE);
        }
        else if (mStatus.isLookStatus()){
            llQrcode.setVisibility(View.GONE);
            llSubmit.setVisibility(View.GONE);
        }


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
            mStatus.setModifyStatus(true);
            if (mStatus.isModifyStatus()) {
                setActionBarMidlleTitle("修改补货需求");
                TopListViewInit(this.comTaskBeans);

                workflowSection = new ReplenishmentRequireContentItemSection(comTaskBeans,mStatus);
                String[] stringArray = getResources().getStringArray(R.array.confirmation_itemsection_return);
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
                tvSubmitActionforList();
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

    //构件选择 点击事件
    private void tvQrcodeAction() {
        Intent intent = new Intent(ReplenishmentRequireContentMessageActivity.this, CaptureActivity.class);
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


    private void tvSubmitActionforList(){
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
        billtable=new  String[size][18];
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
                    billtable[i][17]=confirmationReceBean.qualityType;

        }
        if (mStatus.isNewStatus()) {

            ApiWebService.Get_Sale_Order_Car_qr_In_Add(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    T.showShort(ReplenishmentRequireContentMessageActivity.this,result);
                    boolean ssss = result.contains(String.valueOf("失"));

                    if (!result.contains("失")) {
                        onBackPressed();
                        sureDataRefresh("tvSubmitAction");
                    }
                }

                @Override
                public void ErrorBack(String error) {

                }
            },billtable, App.GSMID, App.Property, App.IsSub, App.MobileKey, App.KeyTimestring, App.USER_INFO);

        }
        else if (mStatus.isModifyStatus()) {
            ApiWebService.Get_Sale_Order_Car_qr_In_Eedit(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    T.showShort(ReplenishmentRequireContentMessageActivity.this, result);
                    if (!result.contains("失败")) {
                        onBackPressed();
                        sureDataRefresh("tvSubmitAction");
                    }
                }

                @Override
                public void ErrorBack(String error) {

                }
            }, billtable, comTaskBeans.get(0).ID, App.GSMID, App.Property, App.IsSub, App.MobileKey, App.KeyTimestring, App.USER_INFO);
        }
    }


    private void tvCheckAction(){

        ApiWebService.Get_Sale_Order_Car_qr_In_sh(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(ReplenishmentRequireContentMessageActivity.this,result);
                onBackPressed();
                sureDataRefresh("tvCheckAction");
            }

            @Override
            public void ErrorBack(String error) {

            }
        },comTaskBeans.get(0).getID(), App.USER_INFO, App.GSMID, App.Property, App.IsSub, App.MobileKey, App.KeyTimestring, App.USER_INFO);
    }

    private void tvFanCheckAction(){
        ApiWebService.Get_Sale_Order_Car_qr_In_shf(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(ReplenishmentRequireContentMessageActivity.this,result);
                onBackPressed();
                sureDataRefresh("tvFanCheckAction");
            }

            @Override
            public void ErrorBack(String error) {

            }
        },comTaskBeans.get(0).getID(), App.USER_INFO, App.GSMID, App.Property, App.IsSub, App.MobileKey, App.KeyTimestring, App.USER_INFO);
    }

    private void tvDeleteAction(){
        ApiWebService.Get_Sale_Order_Car_qr_In_Del(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(ReplenishmentRequireContentMessageActivity.this,result);
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


