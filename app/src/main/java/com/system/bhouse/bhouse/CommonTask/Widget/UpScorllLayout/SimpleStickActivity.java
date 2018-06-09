package com.system.bhouse.bhouse.CommonTask.Widget.UpScorllLayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewTreeObserver;

import com.google.gson.reflect.TypeToken;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.base.StatusBean;
import com.system.bhouse.bean.LoadingCarBean;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.LoadingCarOrder.LoadingCarOrderContentItemSection;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.LoadingCarOrder.LoadingCarOrderContentMessageActivity;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.TreeRecyclerAdapter;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.item.GroupItem;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.item.SortChildItem;
import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.item.TreeItem;
import com.system.bhouse.bhouse.CommonTask.utils.ComTaskContentItemSectionItemTouchHelper;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.ui.sectioned.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author 顾修忠-guxiuzhong@youku.com/gfj19900401@163.com
 * @Title: BaseFragment
 * @Package com.gxz.stickynavlayout.fragments
 * @Description: 基类Fragment
 * @date 16/1/1
 * @time 下午13:17
 */
public class SimpleStickActivity extends AppCompatActivity {

    @Bind(R.id.id_stick)
    StickyNavLayout stickyNavLayout;
    private MyTaskContentAdapter mRecyclerViewAdapter;
    private LoadingCarOrderContentItemSection workflowSection;
    private ArrayList<LoadingCarBean> comTaskBeans = new ArrayList<>();

    @Bind(R.id.id_stickynavlayout_innerscrollview)
    RecyclerView listView;
    private StatusBean statusBean;
    @Bind(R.id.topListView)
    RecyclerView topListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_stick);
        ButterKnife.bind(this);


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
        statusBean = new StatusBean();
        statusBean.setNewStatus(true);
        workflowSection = new LoadingCarOrderContentItemSection(comTaskBeans, statusBean);
        String[] stringArray = getResources().getStringArray(R.array.loadingcar_itemsection_order);
        workflowSection.setTVIDContent(stringArray);
        new ItemTouchHelper(new ComTaskContentItemSectionItemTouchHelper(mRecyclerViewAdapter)).attachToRecyclerView(listView);
//        workflowSection.setOnItemClickListener(this);

        mRecyclerViewAdapter.addSection(workflowSection);

        listView.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewAdapter.notifyDataSetChanged();

        testData();

        stickyNavLayout.setOnStickStateChangeListener(onStickStateChangeListener);
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

    private void TopListViewInit() {
        LoadingCarBean comTaskBean1 = null;

        comTaskBean1 = this.comTaskBeans.get(0);

//        if (!TextUtils.isEmpty(receiptHnumber)) {
//            comTaskBean1.hNumbe = receiptHnumber;
//            if (getComtaskSize()) {
//                for (LoadingCarBean bean : comTaskBeans) {
//                    bean.hNumbe = receiptHnumber;
//                }
//            }
//        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        String[] LETTERS = new String[]{"单据信息", "录入人信息", "审核人信息"};
        String[] key = {"4", "2", "2"};

        ArrayList<LoadingCarOrderContentMessageActivity.KeyType> keyTypes = new ArrayList<>();
        LoadingCarOrderContentMessageActivity.KeyType keyType = null;
        for (int i = 0; i < LETTERS.length; i++) {
            keyType = new LoadingCarOrderContentMessageActivity.KeyType();
            keyType.key = Integer.valueOf(key[i]);
            keyType.type = LETTERS[i];
            keyTypes.add(keyType);
        }

        final List<TreeItem> groupItems = new ArrayList<>();

        for (int i = 0; i < LETTERS.length; i++) {
            GroupItem sortGroupItem = new GroupItem();
            sortGroupItem.TitleKey = LETTERS[i];
//            sortGroupItem.setmOnChildItemClickListener(this);
            sortGroupItem.setData(makeChlidData(comTaskBean1, keyTypes.get(i), statusBean));
            groupItems.add(sortGroupItem);
        }

//        if (mStatus.isNewStatus())
//        this.comTaskBeans.add(comTaskBean1);
        treeRecyclerAdapter = new TreeRecyclerAdapter();
        topListView.setLayoutManager(linearLayoutManager);
        treeRecyclerAdapter.setDatas(groupItems);
        topListView.setAdapter(treeRecyclerAdapter);
//        treeRecyclerAdapter.getItemManager().replaceAllItem(groupItems);
        topListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                View childAt = linearLayoutManager.getChildAt(0);
                if (childAt !=null){
                    int height = childAt.getHeight();
                    int height1 = topListView.getHeight();
                    topListView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }else{
                    treeRecyclerAdapter.notifyDataSetChanged();
                }

            }
        });
    }
    private TreeRecyclerAdapter treeRecyclerAdapter;
    private HashMap<String, String> headerProperties = new HashMap<>();

    /**
     * 初始化  childItem 子布局所需的数据
     *
     * @param comTaskBean1
     * @param data
     * @param mStatus
     * @return
     */
    protected ArrayList<SortChildItem.ViewModel> makeChlidData(LoadingCarBean comTaskBean1, LoadingCarOrderContentMessageActivity.KeyType data, StatusBean mStatus) {
        ArrayList<SortChildItem.ViewModel> viewModels = new ArrayList<>();
        SortChildItem.ViewModel viewModel;
        if (data.key == 4) {

            viewModel = new SortChildItem.ViewModel();
            viewModel.name = "单据编号";
            viewModel.value = "111111111111111";
            viewModel.key = "receiptHnumber";
            viewModel.isClick = false;
            viewModels.add(viewModel);
            comTaskBean1.hNumbe = App.receiptHnumber;
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
            headerProperties.put("containerID",this.comTaskBeans.get(0).getContainerID());

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

    private void testData() {

        ApiWebService.Get_Sale_OrderView_Json(this, new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                ArrayList<LoadingCarBean> tomTaskBeans = App.getAppGson().fromJson(result, new TypeToken<List<LoadingCarBean>>() {
                }.getType());
                if (tomTaskBeans.isEmpty()) {
                    LoadingCarBean bean = new LoadingCarBean();
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
        }, "71a11b59bfce444986f5b91891f9d0cb");
        //上个result id 值   1ea66b7bb9674a18a8794bd943c212bb
    }

    private boolean lastIsTopHidden;//记录上次是否悬浮
    private StickyNavLayout.onStickStateChangeListener onStickStateChangeListener = new StickyNavLayout.onStickStateChangeListener() {
        @Override
        public void isStick(boolean isStick) {
            if (lastIsTopHidden != isStick) {
                lastIsTopHidden = isStick;
                if (isStick) {
//                    Toast.makeText(SimpleStickActivity.this, "本宝宝悬浮了", Toast.LENGTH_LONG).show();
                } else {
//                    Toast.makeText(SimpleStickActivity.this, "本宝宝又不悬浮了", Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void scrollPercent(float percent) {
            //动画
//            ViewHelper.setAlpha(floatingActionButton, percent);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
