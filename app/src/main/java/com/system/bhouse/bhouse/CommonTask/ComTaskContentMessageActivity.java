package com.system.bhouse.bhouse.CommonTask;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.system.bhouse.Custom.ShowDeviceMessageCustomDialog;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.base.StatusBean;
import com.system.bhouse.bean.BProBOM;
import com.system.bhouse.bean.ComTaskBean;
import com.system.bhouse.bhouse.CommonTask.BaseTaskFragment.BaseContentMessageActivity;
import com.system.bhouse.bhouse.CommonTask.adapter.ComTaskContentItemSection;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.TreeRecyclerAdapter;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.base.ViewHolder;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.item.GroupItem;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.item.SortChildItem;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.item.TreeItem;
import com.system.bhouse.bhouse.CommonTask.common.CommonDateTimePickerFragment;
import com.system.bhouse.bhouse.CommonTask.common.CommonPickerActivity_;
import com.system.bhouse.bhouse.CommonTask.utils.ComTaskContentItemSectionItemTouchHelper;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.utils.LabelNumPickerDialog;
import com.system.bhouse.utils.TenUtils.L;
import com.system.bhouse.utils.TenUtils.T;

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
public class ComTaskContentMessageActivity extends BaseContentMessageActivity implements ComTaskContentItemSection.OnItemClickListener,GroupItem.onChildItemClickListener,LabelNumPickerDialog.OnDateSetListener{

    public static final String TAG="comtaskcontentmessageactivity";

    @ViewById
    RecyclerView listView;
    @ViewById
    RecyclerView topListView;

    @Extra
    String HId;

    @Extra
    StatusBean mStatusBean;

    private MyTaskContentAdapter mRecyclerViewAdapter;
    private ArrayList<ComTaskBean> comTaskBeans=new ArrayList<>();
    private TreeRecyclerAdapter treeRecyclerAdapter;
    private boolean isDeleteAble=true;
    public static final int RESULT_SORTITEM_SELECTPROJECT = 1001;
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
    private ComTaskContentItemSection workflowSection;

    @AfterViews
    public void initComTaskActivity(){

        mRecyclerViewAdapter=new MyTaskContentAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        listView.setLayoutManager(linearLayoutManager);

        workflowSection = new ComTaskContentItemSection(comTaskBeans,mStatusBean);
        new ItemTouchHelper(new ComTaskContentItemSectionItemTouchHelper(mRecyclerViewAdapter)).attachToRecyclerView(listView);
        workflowSection.setOnItemClickListener(this);

        mRecyclerViewAdapter.addSection(workflowSection);

        listView.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewAdapter.notifyDataSetChanged();

        testData();
        TopListViewInit(this.comTaskBeans);

        if (mStatusBean.isNewStatus())
        {
            setActionBarMidlleTitle("新增吊装需求");
        }else {
            setActionBarMidlleTitle("吊装需求");
        }
    }

    /**
     * 初始布局  为列表布局
     * param comTaskBeans
     */
    private void TopListViewInit(ArrayList<ComTaskBean> comTaskBeans) {

        ComTaskBean comTaskBean=new ComTaskBean();
        ComTaskBean comTaskBean1 = null;
        try {
            comTaskBean1 = comTaskBeans.size()==0 ? comTaskBean : comTaskBeans.get(0);
        } catch (Exception e) {
            comTaskBean1=comTaskBean;
            e.printStackTrace();
        }
        idNames = comTaskBean1.getIdNames();
        idValue = comTaskBean1.getIdValue();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        String[] LETTERS = new String[]{"单据信息","录入人信息","审核人信息"};
        String[] key={"8","2","2"};

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
            sortGroupItem.setData(makeChlidData(keyTypes.get(i),mStatusBean));
            groupItems.add(sortGroupItem);
        }

        treeRecyclerAdapter = new TreeRecyclerAdapter();
        topListView.setLayoutManager(linearLayoutManager);
        topListView.setAdapter(treeRecyclerAdapter);
        treeRecyclerAdapter.getItemManager().replaceAllItem(groupItems);
    }


    /**
     * 初始化  childItem 子布局所需的数据
     * @param
     * @param
     * @return
     */
    protected  ArrayList<SortChildItem.ViewModel> makeChlidData(KeyType data, StatusBean mStatusBean) {
        ArrayList<SortChildItem.ViewModel> viewModels=new ArrayList<>();
        SortChildItem.ViewModel viewModel;
        if (data.key==8) {

            viewModel=new SortChildItem.ViewModel();
            viewModel.name=idNames.get("hNumbe");
            viewModel.value= idValue.get("hNumbe");
            viewModel.key="hNumbe";
            viewModel.isClick=false;
            viewModels.add(viewModel);

            viewModel=new SortChildItem.ViewModel();
            viewModel.name=idNames.get("projectName");
            viewModel.value= idValue.get("projectName");
            viewModel.key="projectName";
            if (mStatusBean.isNewStatus())
                viewModel.isClick=true;
            viewModels.add(viewModel);

            viewModel=new SortChildItem.ViewModel();
            viewModel.name=idNames.get("dong");
            viewModel.value= idValue.get("dong");
            viewModel.key="dong";
            if (mStatusBean.isNewStatus())
                viewModel.isClick=true;
            viewModels.add(viewModel);

            viewModel=new SortChildItem.ViewModel();
            viewModel.name=idNames.get("ceng");
            viewModel.value= idValue.get("ceng");
            viewModel.key="ceng";
            if (mStatusBean.isNewStatus())
                viewModel.isClick=true;
            viewModels.add(viewModel);

            viewModel=new SortChildItem.ViewModel();
            viewModel.name=idNames.get("requireData");
            viewModel.value= idValue.get("requireData");
            viewModel.key="requireData";
            if (mStatusBean.isNewStatus())
                viewModel.isClick=true;
            viewModels.add(viewModel);

            viewModel=new SortChildItem.ViewModel();
            viewModel.name=idNames.get("description");
            viewModel.value= idValue.get("description");
            viewModel.key="description";
            if (mStatusBean.isNewStatus())
                viewModel.isClick=true;
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
            viewModel.value= mStatusBean.isNewStatus()?"":idValue.get("checkPeople");
            viewModel.key="checkPeople";
            viewModel.isClick=false;
            viewModels.add(viewModel);

            viewModel=new SortChildItem.ViewModel();
            viewModel.name=idNames.get("checkTime");
            viewModel.value= mStatusBean.isNewStatus()?"":idValue.get("checkTime");
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

        if (data.name.equals("项目名称")) {
            type = strTypes[0];
            if (TextUtils.isEmpty(type))
                return;
            ApiWebService.B_Pro_BOM_Select(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    ArrayList<BProBOM> bProBOMs = App.getAppGson().fromJson(result, new TypeToken<List<BProBOM>>() {
                    }.getType());

                    CommonPickerActivity_.intent(ComTaskContentMessageActivity.this).title("项目").bProBOMs(bProBOMs).LayoutID(R.layout.commonpicker_item).Position(holder.getAdapterPosition()).startForResult(RESULT_SORTITEM_SELECTPROJECT);

                }

                @Override
                public void ErrorBack(String error) {

                }
            },9999, type, HId, null, App.GSMID, App.Property, App.IsSub);
        }
        else if (data.name.equals("栋")) {
//            a3296cf71c1d46b0963f9f45bfd58382
            type = strTypes[1];
            if (TextUtils.isEmpty(type))
                return;
            if (TextUtils.isEmpty(extraHIds.get(holder.getAdapterPosition() - 1))) {
                T.showShort(ComTaskContentMessageActivity.this, "请选择项目名称");
                return;
            }
            ApiWebService.B_Pro_BOM_Select(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    if (TextUtils.isEmpty(result)) {
                        T.showShort(ComTaskContentMessageActivity.this, "请选择项目名称");
                    }

                    ArrayList<BProBOM> bProBOMs = App.getAppGson().fromJson(result, new TypeToken<List<BProBOM>>() {
                    }.getType());

                    CommonPickerActivity_.intent(ComTaskContentMessageActivity.this).title("项目").bProBOMs(bProBOMs).Position(holder.getAdapterPosition()).LayoutID(R.layout.commonpicker_item).startForResult(RESULT_SORTITEM_SELECTPROJECT);

                }

                @Override
                public void ErrorBack(String error) {

                }
            },9999, type, HId, extraHIds.get(holder.getAdapterPosition() - 1), App.GSMID, App.Property, App.IsSub);
        }
        else if (data.name.equals("层")) {
            type = strTypes[2];
            if (TextUtils.isEmpty(type))
                return;

            if (TextUtils.isEmpty(extraHIds.get(holder.getAdapterPosition() - 1))) {
                T.showShort(ComTaskContentMessageActivity.this, "请选择栋数");
                return;
            }
            ApiWebService.B_Pro_BOM_Select(this, new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {

                    ArrayList<BProBOM> bProBOMs = App.getAppGson().fromJson(result, new TypeToken<List<BProBOM>>() {
                    }.getType());

                    CommonPickerActivity_.intent(ComTaskContentMessageActivity.this).title("项目").bProBOMs(bProBOMs).LayoutID(R.layout.commonpicker_item).Position(holder.getAdapterPosition()).startForResult(RESULT_SORTITEM_SELECTPROJECT);

                }

                @Override
                public void ErrorBack(String error) {

                }
            },9999, type, HId, extraHIds.get(holder.getAdapterPosition() - 1), App.GSMID, App.Property, App.IsSub);
        }else if (data.name.equals("需求日期"))
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
                            T.showfunShort(ComTaskContentMessageActivity.this,"备注不能为空");
                        }
                        else {

                            getDateRefresh(text,holder.getAdapterPosition(),data.name);

//                            List<TreeItem> datas = treeRecyclerAdapter.getDatas();
//                            SortChildItem treeItem =(SortChildItem)datas.get(6); //需求日期
//                            SortChildItem.ViewModel viewModel = treeItem.getData();
//                            if (viewModel.name.equals("描述")) {
//                                if (!viewModel.value.equals(text))
//                                    viewModel.value = text;
//                                if (hashMaps==null||hashMaps.size()<0) {
//                                    idValue.put(viewModel.key, viewModel.value);
//                                }else {
//                                    for (int i=0;i<hashMaps.size();i++)
//                                    {
//                                        hashMaps.get(i).put(viewModel.key,viewModel.value);
//                                    }
//                                }
//                            }
//                            treeItem.setData(viewModel);
//                            treeRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });

            builder.setNegativeButton("取消",
                    new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            builder.create().show();
        }

    }

    /**
     * pickActivity 结束回调
     * param result
     * param data
     */
    @OnActivityResult(RESULT_SORTITEM_SELECTPROJECT)
    void resultProjcetName(int result, Intent data) {
        if (result==RESULT_OK){
            String extrasName = data.getStringExtra("projectname");
            int extraPosition = data.getIntExtra("position",0);
            String extra = data.getStringExtra("HId");
            String extraCoding = data.getStringExtra("coding");
           String extraBOMID= data.getStringExtra("BOMID");
            extraHIds.put(extraPosition,extra);

            List<TreeItem> datas = treeRecyclerAdapter.getDatas();

            SortChildItem treeItem =(SortChildItem)datas.get(extraPosition);
            SortChildItem.ViewModel viewModel = treeItem.getData();
            if (viewModel.name.equals("项目名称")) {
                viewModel.value = extrasName;
                idValue.put("Pro_id",extra);
                setChlidItemEmpty(extraPosition+1);
                setChlidItemEmpty(extraPosition+2);

            }else if (viewModel.name.contains("层")){
                viewModel.value=extraCoding;
                idValue.put("Pro_ceng_id",extra);
                idValue.put("BOMID",extraBOMID);
                getBOMIDData();
            }else if (viewModel.name.contains("栋")){
                viewModel.value=extraCoding;
                idValue.put("Pro_dong_id",extra);
                setChlidItemEmpty(extraPosition+1);
            }
            idValue.put(viewModel.key, viewModel.value);
            treeItem.setData(viewModel);
            treeRecyclerAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 数据带出 方法.
     */
    private void getBOMIDData(){
        ApiWebService.Get_Hois_Req_cengBom_Json(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {

                ArrayList<ComTaskBean> ntomTaskBeans = App.getAppGson().fromJson(result, new TypeToken<List<ComTaskBean>>() {
                }.getType());

                hashMaps=new ArrayList<>();
                for (int i=0;i<ntomTaskBeans.size();i++) {
                    idValue.put("goodsID", ntomTaskBeans.get(i).getGoodsID());
                    idValue.put("goodsCoding",ntomTaskBeans.get(i).getGoodsCoding());
                    idValue.put("goodsName",ntomTaskBeans.get(i).getGoodsName());
                    idValue.put("Specification",ntomTaskBeans.get(i).getSpecification());
                    idValue.put("measureID",ntomTaskBeans.get(i).getMeasureID());
                    idValue.put("measure",ntomTaskBeans.get(i).getMeasure());
                    idValue.put("amount",ntomTaskBeans.get(i).getAmount()+"");
                    HashMap<String, String> idValue1=new HashMap<>();
                    idValue1.putAll(idValue);
                    hashMaps.add(idValue1);

                }

                for (int i=0;i<ntomTaskBeans.size();i++) {
                    if (comTaskBeans.size()==0||comTaskBeans.get(0)==null)
                        break;
                    ntomTaskBeans.get(i).setID(comTaskBeans.get(0).getID());
                    ntomTaskBeans.get(i).setHNumbe(comTaskBeans.get(0).getHNumbe());
                    ntomTaskBeans.get(i).setProjectName(comTaskBeans.get(0).getProjectName());
                    ntomTaskBeans.get(i).setDong(comTaskBeans.get(0).getDong());
                    ntomTaskBeans.get(i).setCeng(comTaskBeans.get(0).getCeng());
                    ntomTaskBeans.get(i).setRequireData(comTaskBeans.get(0).getRequireData());
                    ntomTaskBeans.get(i).setDescription(comTaskBeans.get(0).getDescription());
                    ntomTaskBeans.get(i).setStatus(comTaskBeans.get(0).getStatus());
                    ntomTaskBeans.get(i).setEnterPeople(comTaskBeans.get(0).getEnterPeople());
                    ntomTaskBeans.get(i).setEntryTime(comTaskBeans.get(0).getEntryTime());
                    ntomTaskBeans.get(i).setChildTableID(comTaskBeans.get(0).getChildTableID());
                }
                comTaskBeans.clear();
                comTaskBeans.addAll(ntomTaskBeans);
                workflowSection.setSearchHistroyBeans(comTaskBeans);
                mRecyclerViewAdapter.notifyDataSetChanged();

//                ComTaskBean comTaskBeanChild = new ComTaskBean();
//                comTaskBeanChild.childTableID = "599d49d6ad2c4eabb43c7314cd68a467";
//                comTaskBeanChild.goodsID = "acb6fd62b2f0405292fe8c0de0737f2f";
//                comTaskBeanChild.goodsCoding = "1002.1084.0100.003";
//                comTaskBeanChild.goodsName = "PC构件";
//                comTaskBeanChild.Specification = "";
//                comTaskBeanChild.measureID = "c8e082b5f5f34d5f934f071e6b464238";
//                comTaskBeanChild.measure = "块";
//                comTaskBeanChild.amount = 30;
            }
            @Override
            public void ErrorBack(String error) {

            }
        },idValue.get("BOMID"), App.GSMID, App.Property, App.IsSub);
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
     * 日期 click的回调
     * param date
     */
    @Override
    public void onDateSet(String date) {

        getDateRefresh(date,5,"需求日期");

//        List<TreeItem> datas = treeRecyclerAdapter.getDatas();

//        SortChildItem treeItem =(SortChildItem)datas.get(5); //需求日期
//        SortChildItem.ViewModel viewModel = treeItem.getData();
//        if (viewModel.name.equals("需求日期")) {
//            if (!viewModel.value.equals(date))
//                viewModel.value = date;
//            if (hashMaps==null||hashMaps.size()<0) {
//                idValue.put(viewModel.key, viewModel.value);
//            }else {
//                for (int i=0;i<hashMaps.size();i++)
//                {
//                    hashMaps.get(i).put(viewModel.key,viewModel.value);
//                }
//            }
//        }
//        treeItem.setData(viewModel);
//        treeRecyclerAdapter.notifyDataSetChanged();
    }


    public static class KeyType{
        public int key;
        public String type;
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
        if (viewModel.name.equals(typestring) && typestring.equals("需求日期")) {
            if (!viewModel.value.equals(date))
                viewModel.value = date;
//            for (int i = 0; i < comTaskBeans.size(); i++) {
//                comTaskBeans.get(i).requireData = viewModel.value;
//            }
        }
        else if (viewModel.name.equals("车牌号")) {
            if (!viewModel.value.equals(date))
                viewModel.value = date;

//            for (int i = 0; i < comTaskBeans.size(); i++) {
//                comTaskBeans.get(i).Licenseplate = viewModel.value;
//            }
        }
        else if (viewModel.name.equals("描述")) {
            if (!viewModel.value.equals(date))
                viewModel.value = date;

//            for (int i = 0; i < comTaskBeans.size(); i++) {
//                comTaskBeans.get(i).description = viewModel.value;
//            }
        }
        else if (viewModel.name.equals("车次")) {
            if (!viewModel.value.equals(date))
                viewModel.value = date;
//            for (int i = 0; i < comTaskBeans.size(); i++) {
//                comTaskBeans.get(i).cartrips = viewModel.value;
//            }
        }
        if (hashMaps == null || hashMaps.size() < 0) {
            idValue.put(viewModel.key, viewModel.value);
        }
        else {
            for (int i = 0; i < hashMaps.size(); i++) {
                hashMaps.get(i).put(viewModel.key, viewModel.value);
            }
        }
        treeItem.setData(viewModel);
        treeRecyclerAdapter.notifyDataSetChanged();
    }

    /**
     * 请求数据
     */
    //        [{"ID":"9dc7b4b3882048f5bccea193ccdf6fc3","订单编号":"DZXQ-7-201801-0001","项目名称":"麓谷一期项目","栋":"1","层":"2","需求日期":"2018/1/18 16:54:38","描述":"测试单据","状态":"审核","录入人":"管理员","录入时间":"2018/1/18 16:55:03"}]
    private void testData() {

        ApiWebService.Get_Hois_ReqView_Json(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                ArrayList<ComTaskBean> tomTaskBeans = App.getAppGson().fromJson(result, new TypeToken<List<ComTaskBean>>() {
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
            comTaskBeans.remove(position);
            mRecyclerViewAdapter.notifyItemRemoved(positionAdapter);
            //网上说这个方式刷新 position正确
            mRecyclerViewAdapter.notifyItemRangeChanged(position,comTaskBeans.size());//刷新被删除数据，以及其后面的数据

    }
}

    //点击增加的按钮
    @Override
    public void onImgItemAdd(View view, int position, RecyclerView.ViewHolder holder) {
        if (comTaskBeans==null)
            return;
        ComTaskBean dataBean = new ComTaskBean();
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


    protected void tvQrcodeAction() {
        ApiWebService.Get_Hois_Req_QR_Code_Create(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(ComTaskContentMessageActivity.this,result);
                onBackPressed();
                sureDataRefresh("tvQrcodeAction");
            }

            @Override
            public void ErrorBack(String error) {

            }
        },idValue.get("ID"), App.USER_INFO, App.GSMID, App.Property, App.IsSub, App.MobileKey, App.KeyTimestring, App.USER_INFO);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void tvSubmitAction(){
        if (TextUtils.isEmpty(idValue.get("projectName"))) {
            T.showShort(this, "项目为空不能提交");
            return;
        }else  if (TextUtils.isEmpty(idValue.get("dong"))) {
            T.showShort(this, "栋为空不能提交");
            return;
        }else  if (TextUtils.isEmpty(idValue.get("ceng"))) {
            T.showShort(this, "层为空不能提交");
            return;
        }
        else if (hashMaps.size()==0) {
            T.showShort(this, "分录为空不能提交");
            return;
        }
        int size = hashMaps.size();
        String[][] billtable=null;
        billtable=new  String[size][14];
        for (int i=0;i<size;i++)
        {
            HashMap<String, String> stringStringHashMap = hashMaps.get(i);

            for (String key : stringStringHashMap.keySet()) {
                 if ("hNumbe".equals(key))
                 {
                     billtable[i][0]=stringStringHashMap.get(key);
                 }else if ("Pro_id".equals(key))
                 {
                     billtable[i][1]=stringStringHashMap.get(key);
                 }else if ("Pro_dong_id".equals(key))
                 {
                     billtable[i][2]=stringStringHashMap.get(key);
                 }else if ("Pro_ceng_id".equals(key))
                 {
                     billtable[i][3]=stringStringHashMap.get(key);
                 }else if ("requireData".equals(key))
                 {
                     billtable[i][4]=stringStringHashMap.get(key);
                 }else if ("description".equals(key))
                 {
                     billtable[i][5]=stringStringHashMap.get(key);
                 }else if ("enterPeople".equals(key))
                 {
                     billtable[i][6]=stringStringHashMap.get(key);
                 }
                 else if ("goodsID".equals(key))
                 {
                     billtable[i][7]=stringStringHashMap.get(key);
                 }else if ("goodsCoding".equals(key))
                 {
                     billtable[i][8]=stringStringHashMap.get(key);
                 }else if ("goodsName".equals(key))
                 {
                     billtable[i][9]=stringStringHashMap.get(key);
                 }else if ("Specification".equals(key))
                 {
                     billtable[i][10]=stringStringHashMap.get(key);
                 }else if ("measureID".equals(key))
                 {
                     billtable[i][11]=stringStringHashMap.get(key);
                 }else if ("measure".equals(key))
                 {
                     billtable[i][12]=stringStringHashMap.get(key);
                 }else if ("amount".equals(key))
                 {
                     billtable[i][13]=stringStringHashMap.get(key);
                 }
            }
        }
        ApiWebService.Get_Hois_Req_Add(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(ComTaskContentMessageActivity.this,result);
                onBackPressed();
                sureDataRefresh("tvSubmitAction");
            }

            @Override
            public void ErrorBack(String error) {

            }
        },billtable, App.GSMID, App.Property, App.IsSub, App.MobileKey, App.KeyTimestring, App.USER_INFO);
    }

    protected void tvCheckAction(){

        ApiWebService.Get_Hois_Req_sh(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(ComTaskContentMessageActivity.this,result);
                onBackPressed();
                sureDataRefresh("tvCheckAction");
            }

            @Override
            public void ErrorBack(String error) {

            }
        },idValue.get("ID"), App.USER_INFO, App.GSMID, App.Property, App.IsSub, App.MobileKey, App.KeyTimestring, App.USER_INFO);
    }

    protected void tvFanCheckAction(){
        ApiWebService.Get_Hois_Req_shf(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(ComTaskContentMessageActivity.this,result);
                onBackPressed();
                sureDataRefresh("tvFanCheckAction");
            }

            @Override
            public void ErrorBack(String error) {

            }
        },idValue.get("ID"), App.USER_INFO, App.GSMID, App.Property, App.IsSub, App.MobileKey, App.KeyTimestring, App.USER_INFO);
    }

    @Override
    protected void tvModifyCheckAction() {

    }

    protected void tvDeleteAction(){
        ApiWebService.Get_Hois_Req_Del(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                T.showShort(ComTaskContentMessageActivity.this,result);
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



    @OptionsItem
    protected final void action_operat_status(){
        Observable<Object> objectObservable = Observable.create(subscriber -> {
            show1(mStatusBean);
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


